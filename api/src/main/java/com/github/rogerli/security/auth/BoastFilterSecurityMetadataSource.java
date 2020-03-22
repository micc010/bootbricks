package com.github.rogerli.security.auth;

import com.github.rogerli.system.purview.entity.Purview;
import com.github.rogerli.system.role.entity.Role;
import com.github.rogerli.system.role.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 加载权限对应的角色
 *
 * @author roger.li
 */
@Slf4j
@Component
public class BoastFilterSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    @Autowired
    private RoleService roleService;

    /**
     * 资源所需要的权限
     *
     * @param object
     *         请求
     *
     * @return 所需权限
     *
     * @throws IllegalArgumentException
     *         异常
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object)
            throws IllegalArgumentException {
        FilterInvocation fi = (FilterInvocation) object;
        log.info("Full request URL: " + fi.getFullRequestUrl());
        log.info("Request URL: " + fi.getRequestUrl());
        log.info("HTTP Method: " + fi.getRequest().getMethod());
        log.info("Context path: " + fi.getRequest().getContextPath());

        Collection<ConfigAttribute> securityConfigList = new ArrayList<>();
        //在Resource表找到该资源对应的角色
        Purview query = new Purview();
        query.setUrl(fi.getRequest().getServletPath());
        List<Role> roleList = roleService.findRoleListByPurview(query);

        if (roleList == null || roleList.size() == 0) {
            log.info("URL At Least Need Role:User");
        } else {
            for (Role role :
                    roleList) {
                //以角色名称来存放
                SecurityConfig securityConfig = new SecurityConfig(role.getRole());
                securityConfigList.add(securityConfig);
                log.info("url need role " + role.getRole());
            }
        }

        return securityConfigList;
    }

    /**
     * @return collection
     */
    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    /**
     * @param arg0
     *         classType
     *
     * @return boolean
     */
    public boolean supports(Class<?> arg0) {
        return true;
    }

}
