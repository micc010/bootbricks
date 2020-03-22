/**
 * @文件名称： CustomUserDetailService.java
 * @文件描述：
 * @版权所有：(C)2016-2028
 * @公司：
 * @完成日期: 2016/12/8
 * @作者 ： Roger
 */
package com.github.rogerli.security.auth;

import com.github.rogerli.security.verifier.LoginAttemptService;
import com.github.rogerli.system.login.entity.Login;
import com.github.rogerli.system.login.model.BoastUserDetail;
import com.github.rogerli.system.login.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * 根据用户查找权限
 *
 * @author Roger
 * @date 2016/12/8 11:12
 */
@Component
public class BoastUserDetailService implements UserDetailsService {

    @Autowired
    private LoginService loginService;

    @Autowired
    private LoginAttemptService loginAttemptService;

    /**
     * 根据用户名查询用户
     *
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        if (StringUtils.isEmpty(username)) {
            throw new UsernameNotFoundException("用户名为空");
        }

        if (loginAttemptService.isBlocked(username)) {
            throw new LockedException("账号被锁定，无法登陆！10分钟后可再次尝试");
        }

        Login login = loginService.findByUsername(username);
        if (login == null) {
            throw new UsernameNotFoundException("用户不存在");
        }

        Set<GrantedAuthority> authorities = new HashSet<>();
        loginService.findRoleListByLogin(login.getId()).forEach(r -> authorities.add(new SimpleGrantedAuthority(r.getRole())));

        return new BoastUserDetail(login.getId(),
                login.getUsername(),
                login.getFullName(),
                login.getPassword(),
                login.getOrganId(),
                login.getOrganName(),
                new Date(),
                authorities);
    }

}
