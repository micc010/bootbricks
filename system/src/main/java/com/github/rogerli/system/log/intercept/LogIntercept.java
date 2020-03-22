package com.github.rogerli.system.log.intercept;

import com.github.rogerli.system.log.entity.Log;
import com.github.rogerli.system.log.service.LogService;
import com.github.rogerli.system.login.model.BoastUserDetail;
import com.github.rogerli.utils.AnnotationUtils;
import com.github.rogerli.utils.IPUtils;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;

@Slf4j
@Aspect
@Component
public class LogIntercept {

    @Autowired
    private LogService logService;
    @Autowired
    private HttpServletRequest request;

    /**
     * 切入点
     */
    @Pointcut("execution(public com.github.rogerli.framework.model.Result com.github.rogerli..*Controller.*(..)) " +
            "&& @annotation(org.springframework.web.bind.annotation.RequestMapping)")
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
//
//        }
//        //用改变后的参数执行目标方法
//        Object returnValue = point.proceed(args);
//        log.debug("@Around：执行目标方法之后");
//        log.debug("@Around：被织入的目标对象为：" + point.getTarget());
//        return returnValue;
//    }

//    @Before("logOperate()")
//    public void permissionCheck(JoinPoint point) {
//        log.debug("@Before：模拟权限检查");
//        log.debug("@Before：目标方法为：" +
//                point.getSignature().getDeclaringTypeName() +
//                "." + point.getSignature().getName());
//        log.debug("@Before：参数为：" + Arrays.toString(point.getArgs()));
//        log.debug("@Before：被织入的目标对象为：" + point.getTarget());
//    }

    /**
     * 方法调用完毕后记录日志
     *
     * @param point
     * @param result
     *
     * @throws Exception
     */
    @AfterReturning(pointcut = "logOperate()", returning = "result")
    public void log(JoinPoint point, Object result) throws Exception {
        log.debug("@AfterReturning：模拟日志记录功能");
        log.debug("@AfterReturning：目标方法为：" +
                point.getSignature().getDeclaringTypeName() +
                "." + point.getSignature().getName());
        log.debug("@AfterReturning：参数为：" +
                Arrays.toString(point.getArgs()));
        log.debug("@AfterReturning：返回值为：" + result);
        log.debug("@AfterReturning：被织入的目标对象为：" + point.getTarget());

        String username;
        String fullName;
        try {
            BoastUserDetail detail = (BoastUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            username = detail.getUsername();
            fullName = detail.getFullname();
        } catch (Exception e) {
            username = SecurityContextHolder.getContext().getAuthentication().getName();
            fullName = null;
        }

        String optName;
        try {
            optName = (String) AnnotationUtils.getSingleMethodValue(
                    ApiOperation.class,
                    point.getSignature().getDeclaringType(),
                    point.getSignature().getName(),
                    point.getArgs(), "value");
        } catch (Exception e) {
            optName = null;
        }

        logService.insertSelective(new Log()
                .setUsername(username)
                .setFullName(fullName)
                .setLogTime(new Date())
                .setLogIp(IPUtils.getIpAddr(request))
                .setOptName(optName)
                .setLogUrl(request.getRequestURI().substring(request.getContextPath().length())));
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

}