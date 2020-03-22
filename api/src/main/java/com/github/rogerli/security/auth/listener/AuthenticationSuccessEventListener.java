package com.github.rogerli.security.auth.listener;

import com.github.rogerli.security.verifier.LoginAttemptService;
import com.github.rogerli.system.login.model.BoastUserDetail;
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
        String username = ((BoastUserDetail) e.getAuthentication().getPrincipal()).getUsername();
        loginAttemptService.loginSucceeded(username);
    }

}  