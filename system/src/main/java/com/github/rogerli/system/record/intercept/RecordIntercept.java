package com.github.rogerli.system.record.intercept;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.rogerli.framework.entity.Entity;
import com.github.rogerli.system.login.model.BoastUserDetail;
import com.github.rogerli.system.record.annotation.RecordTransient;
import com.github.rogerli.system.record.model.RecordData;
import com.github.rogerli.system.record.model.RecordItem;
import com.github.rogerli.system.record.service.RecordService;
import com.github.rogerli.utils.IPUtils;
import com.github.rogerli.utils.MessageUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.security.core.context.SecurityContextHolder;
import tk.mybatis.mapper.util.Assert;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
//@Aspect
//@Component
@ConditionalOnBean(RecordService.class)
public class RecordIntercept<K> {

    @Autowired
    private RecordService recordService;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private ObjectMapper objectMapper;

    private RecordData recordData = new RecordData();

    @Pointcut("(execution(public * com.github.rogerli..*Mapper.update*(..)) " +
            "|| execution(public * com.github.rogerli..*Mapper.insert*(..)) " +
            "|| execution(public * com.github.rogerli..*Mapper.delete*(..))) " +
            "&& !execution(public * com.github.rogerli.system.record.dao.RecordMapper.*(..)) " +
            "&& !execution(public * com.github.rogerli.system.log.dao.LogMapper.*(..))")
    public void logOperate() {

    }

//    @Around("logOperate()")
//    public Object process(ProceedingJoinPoint point) throws Throwable {
//        log.debug("@Around：执行目标方法之前");
//        //访问目标方法的参数：
//        Object[] args = point.getArgs();
//        for (Object arg :
//                args) {
//            log.debug("参数对象：" + arg.getClass());
//        }
//        //用改变后的参数执行目标方法
//        Object returnValue = point.proceed(args);
//        log.debug("@Around：执行目标方法之后");
//        log.debug("@Around：被织入的目标对象为：" + point.getTarget());
//        return returnValue;
//    }

    @Before("logOperate()")
    public void permissionCheck(JoinPoint point) throws Exception {
        log.debug("@Before：模拟权限检查");
        log.debug("@Before：目标方法为：" +
                point.getSignature().getDeclaringTypeName() +
                "." + point.getSignature().getName());
        log.debug("@Before：参数为：" + Arrays.toString(point.getArgs()));
        log.debug("@Before：被织入的目标对象为：" + point.getTarget());

        Method originMethod = ((MethodSignature) point.getSignature()).getMethod();
        if (originMethod.isAnnotationPresent(RecordTransient.class)) {
            return;
        }

        BoastUserDetail details = (BoastUserDetail) SecurityContextHolder.getContext()
                .getAuthentication().getDetails();

        recordData.setLoginId(details.getId())
                .setUsername(details.getUsername())
                .setModifyTime(new Date())
                .setLogIp(IPUtils.getIpAddr(request));

        Object oldObj = null, newObj = null;
        Object param = point.getArgs()[0];
        Object target = point.getTarget();
        if (param != null && param instanceof Entity) {
            Object id = ((Entity) param).getId();
            if (id != null) {
                Method method = target.getClass().getDeclaredMethod("selectByPrimaryKey", Object.class);
                oldObj = method.invoke(target, id);
            }
            newObj = param;
        } else if (param != null && (param instanceof String || param instanceof Long || param instanceof Integer)) {
            Method method = target.getClass().getDeclaredMethod("selectByPrimaryKey", Object.class);
            oldObj = method.invoke(target, param);
        } else {
            Method method = target.getClass().getDeclaredMethod("selectOne", Object.class);
            oldObj = method.invoke(target, param);
            newObj = param;
        }

        recordData.setOldObj(oldObj).setNewObj(newObj);
    }

    @AfterReturning(pointcut = "logOperate()", returning = "result")
    public void afterReturning(JoinPoint point, Object result) throws IllegalAccessException, NoSuchFieldException, ClassNotFoundException, JsonProcessingException, NoSuchMethodException {
        log.debug("@AfterReturning：模拟日志记录功能");
        log.debug("@AfterReturning：目标方法为：" +
                point.getSignature().getDeclaringTypeName() +
                "." + point.getSignature().getName());
        log.debug("@AfterReturning：参数为：" +
                Arrays.toString(point.getArgs()));
        log.debug("@AfterReturning：返回值为：" + result);
        log.debug("@AfterReturning：被织入的目标对象为：" + point.getTarget());

        Method originMethod = ((MethodSignature) point.getSignature()).getMethod();
        if (originMethod.isAnnotationPresent(RecordTransient.class)) {
            return;
        }

        if (point.getSignature().getName().startsWith("update")) {
            recordData.setClassId(((Entity) recordData.getNewObj()).getId().toString())
                    .setClassName(recordData.getNewObj().getClass().getName())
                    .setModifyType(RecordType.update.getCode())
                    .setContent(objectMapper.writeValueAsString(contrastObject(recordData.getOldObj(), recordData.getNewObj())));
        } else if (point.getSignature().getName().startsWith("insert")) {
            recordData.setClassId(((Entity) recordData.getNewObj()).getId().toString())
                    .setClassName(recordData.getNewObj().getClass().getName())
                    .setModifyType(RecordType.insert.getCode())
                    .setContent(objectMapper.writeValueAsString(recordData.getNewObj()));
        } else if (point.getSignature().getName().startsWith("delete")) {
            recordData.setClassId(((Entity) recordData.getOldObj()).getId().toString())
                    .setClassName(recordData.getOldObj().getClass().getName())
                    .setModifyType(RecordType.del.getCode())
                    .setContent(objectMapper.writeValueAsString(recordData.getOldObj()));
        } else {
            return;
        }

        recordService.insert(recordData);
    }

//    @After("logOperate()")
//    public void releaseResource(JoinPoint point) {
//        log.debug("@After：模拟释放资源");
//        log.debug("@After：目标方法为：" +
//                point.getSignature().getDeclaringTypeName() +
//                "." + point.getSignature().getName());
//        log.debug("@After：参数为：" + Arrays.toString(point.getArgs()));
//        log.debug("@After：被织入的目标对象为：" + point.getTarget());
//    }

//    /**
//     * 在目标方法非正常执行完成, 抛出异常的时候会走此方法
//     *
//     * @param point
//     * @param ex
//     */
//    @AfterThrowing(pointcut = "logOperate()", throwing = "ex")
//    public void doAfterThrowing(JoinPoint point, Exception ex) {
//        log.error("@Exception：执行异常通知");
//        log.error("@Exception：目标方法为：" +
//                point.getSignature().getDeclaringTypeName() +
//                "." + point.getSignature().getName());
//        log.error("@Exception：参数为：" + Arrays.toString(point.getArgs()));
//        log.error("@Exception：被织入的目标对象为：" + point.getTarget());
//        log.error("@Exception：异常信息为：" + ex.getMessage());
//    }

    /**
     * 获取两个对象同名属性内容不相同的列表
     *
     * @param oldObject
     *         旧对象
     * @param object
     *         对象
     *
     * @return
     *
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     */
    public List<RecordItem> contrastObject(Object oldObject, Object object)
            throws ClassNotFoundException, IllegalAccessException, NoSuchFieldException {
        //获取对象的class
        Class<?> clazz1 = oldObject.getClass();
        Class<?> clazz2 = object.getClass();
        Assert.isTrue(clazz1.equals(clazz2), MessageUtils.get("class.notEquals"));
//        List<RecordData> list = new ArrayList<RecordData>();

        //获取对象的属性列表
//        Field[] fields = clazz1.getDeclaredFields();
//        //遍历属性列表field1
//        for (Field oldField : fields) {
//            String fieldName = oldField.getName();
//            Field field = clazz2.getDeclaredField(fieldName);
//            oldField.setAccessible(true);
//            field.setAccessible(true);
//            //如果oldField属性值与field属性值内容不相同
//            if (!compareTwo(oldField.get(oldObject), field.get(object))) {
//                list.add(new RecordData(fieldName, oldField.get(oldObject), field.get(object)));
//            }
//        }

        return Arrays.asList(clazz1.getDeclaredFields()).stream().map(
                oldField -> {
                    String fieldName = oldField.getName();
                    Field field;
                    try {
                        field = clazz2.getDeclaredField(fieldName);
                        oldField.setAccessible(true);
                        field.setAccessible(true);
                        //如果oldField属性值与field属性值内容不相同
                        if (!compareTwo(oldField.get(oldObject), field.get(object))) {
                            return new com.github.rogerli.system.record.model.RecordItem(fieldName, oldField.get(oldObject), field.get(object));
                        }
                    } catch (NoSuchFieldException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    return null;
                }
        ).filter(fi -> fi != null).collect(Collectors.toList());

//        return list;
    }

    /**
     * @param object1
     * @param object2
     *
     * @return
     */
    public boolean compareTwo(Object object1, Object object2) {
        if (object1 == null && object2 == null) {
            return true;
        }
        if (object1 == null && object2 != null) {
            return false;
        }
        if (object1.equals(object2)) {
            return true;
        }
        return false;
    }
}