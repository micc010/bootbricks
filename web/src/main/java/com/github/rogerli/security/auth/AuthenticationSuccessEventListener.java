package com.github.rogerli.security.auth;

import com.github.rogerli.security.verifier.LoginAttemptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

/**
 * @author roger.li
 * @since 2018/11/9
 */
@Component
public class AuthenticationSuccessEventListener
        implements ApplicationListener<AuthenticationSuccessEvent> {

    @Autowired
    private LoginAttemptService loginAttemptService;

    /**
     * @param e
     */
    public void onApplicationEvent(AuthenticationSuccessEvent e) {
        String username = (String) e.getAuthentication().getPrincipal();
        loginAttemptService.loginSucceeded(username);
    }

}  