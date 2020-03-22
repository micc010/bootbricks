
package com.github.rogerli.config.redis;

import com.github.rogerli.framework.exception.RestException;
import com.github.rogerli.utils.MessageUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Redis切面处理类
 *
 * @author roger.li
 * @date 2018-03-30 23:30
 */
@Slf4j
@Aspect
@Component
public class RedisAspect {

    //是否开启redis缓存  true开启   false关闭
    @Value("${iboast.redis.open: true}")
    private boolean open;

    /**
     * 获取缓存数据
     *
     * @param point
     * @return
     * @throws Throwable
     */
    @Around("execution(* com.github.rogerli.utils.RedisUtils.*(..))")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        Object result = null;
        if (open) {
            try {
                result = point.proceed();
            } catch (Exception e) {
                log.error("redis.error:", e.getMessage());
                throw new RestException(MessageUtils.get("redis.error"));
            }
        }
        return result;
    }

}
