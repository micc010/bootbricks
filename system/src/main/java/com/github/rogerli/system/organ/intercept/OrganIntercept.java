package com.github.rogerli.system.organ.intercept;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * 判断是否根据单位获取数据
 *
 * @author roger.li
 * @since 2019/6/5
 */
@Slf4j
@Aspect
@Component
public class OrganIntercept {

    @Autowired
    private HttpServletRequest request;

    /**
     * 切入点
     */
    @Pointcut("execution(public com.github.rogerli.framework.model.Result com.github.rogerli..*Controller.*(..)) " +
            "&& @annotation(org.springframework.web.bind.annotation.RequestMapping)" +
            "&& @annotation(com.github.rogerli.system.organ.annotation.OrganFilter)")
    public void organCheck() {

    }

    @Before("organCheck()")
    public void organCheck(JoinPoint point) {
        log.debug("@Before：模拟数据检查");
        log.debug("@Before：目标方法为：" +
                point.getSignature().getDeclaringTypeName() +
                "." + point.getSignature().getName());
        log.debug("@Before：参数为：" + Arrays.toString(point.getArgs()));
        log.debug("@Before：被织入的目标对象为：" + point.getTarget());
    }

}