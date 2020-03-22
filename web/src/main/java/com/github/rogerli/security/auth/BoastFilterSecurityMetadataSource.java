package com.github.rogerli.security.auth;

import com.github.rogerli.system.purview.entity.Purview;
import com.github.rogerli.system.role.entity.Role;
import com.github.rogerli.system.role.service.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@Component
public class BoastFilterSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    private final Logger LOGGER = LoggerFactory
            .getLogger(BoastFilterSecurityMetadataSource.class);

    @Autowired
    private RoleService roleService;

    /**
     * 资源所需要的权限
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object)
            throws IllegalArgumentException {
        FilterInvocation fi = (FilterInvocation) object;
        LOGGER.info("Full request URL: " + fi.getFullRequestUrl());
        LOGGER.info("Request URL: " + fi.getRequestUrl());
        LOGGER.info("HTTP Method: " + fi.getRequest().getMethod());
        LOGGER.info("Context path: " + fi.getRequest().getContextPath());

        Collection<ConfigAttribute> securityConfigList = new ArrayList<ConfigAttribute>();
        //在Resource表找到该资源对应的角色
        Purview query = new Purview();
        query.setUrl(fi.getRequest().getServletPath());
        List<Role> roleList = roleService.findRoleListByPurview(query);

        if (roleList == null || roleList.size() == 0) {
            LOGGER.info("url not need role");
        } else {
            for (Role role :
                    roleList) {
                //以角色名称来存放
                SecurityConfig securityConfig = new SecurityConfig(role.getRole());
                securityConfigList.add(securityConfig);
                LOGGER.info("url need role " + role.getRole());
            }
        }

        return securityConfigList;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    public boolean supports(Class<?> arg0) {
        return true;
    }

}
