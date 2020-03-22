package com.github.rogerli.security.auth;

import com.github.rogerli.utils.MessageUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.vote.AbstractAccessDecisionManager;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

/**
 * 使用自定义的访问决策器，在decide方法中判断资源的权限与用户的权限是否相同
 *
 * @author roger.li
 */
@Slf4j
public class BoastAccessDecisionManager extends AbstractAccessDecisionManager {

    /**
     * @param decisionVoters
     *         决定方式
     */
    public BoastAccessDecisionManager(List<AccessDecisionVoter<? extends Object>> decisionVoters) {
        super(decisionVoters);
    }

    /**
     * 权限控制的逻辑
     *
     * @param authentication
     *         用户的认证对象
     * @param object
     *         the secured object
     * @param configAttributes
     *         访问该资源所要的权限
     */
    public void decide(Authentication authentication, Object object,
                       Collection<ConfigAttribute> configAttributes)
            throws AccessDeniedException, InsufficientAuthenticationException {
        log.debug("Decide That This Permission Can Be Used By User: " + authentication.getName());

        //如果资源要的权限为空，则可以访问
        if (configAttributes == null) {
            return;
        }

        for (ConfigAttribute configAttribute :
                configAttributes) {
            for (GrantedAuthority ga : authentication.getAuthorities()) {
                if (configAttribute.getAttribute().equals(ga.getAuthority())) { // 资源要的权限 = 用户的权限
                    logger.debug("Decide Access Success");
                    return;
                }
            }
        }

        logger.debug("Decide Access Dendied");
        throw new AccessDeniedException(MessageUtils.get("access.dendied"));
    }

    /**
     * 支持的权限筛选，默认返回true
     *
     * @param arg0
     *         权限
     *
     * @return boolean
     */
    public boolean supports(ConfigAttribute arg0) {
        return true;
    }

    /**
     * 支持的类
     *
     * @param arg0
     *         classType
     *
     * @return boolean
     */
    public boolean supports(Class<?> arg0) {
        return true;
    }

}
