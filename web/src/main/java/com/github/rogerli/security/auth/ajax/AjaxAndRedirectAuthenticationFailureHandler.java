package com.github.rogerli.security.auth.ajax;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.rogerli.framework.model.Result;
import com.github.rogerli.security.verifier.ValidCodeException;
import com.github.rogerli.utils.MessageUtils;
import com.github.rogerli.utils.RestUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.util.Assert;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author roger.li
 * @since 2018/11/9
 */
public class AjaxAndRedirectAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    private final String defaultTargetUrl;

    public AjaxAndRedirectAuthenticationFailureHandler(String defaultFailureUrl) {
        Assert.isTrue(UrlUtils.isValidRedirectUrl(defaultFailureUrl), "url must start with '/' or with 'http(s)'");
        this.defaultTargetUrl = defaultFailureUrl;
    }

    /**
     * @param request
     * @param response
     * @param exception
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        if (RestUtils.isAjax(request) || RestUtils.isContentTypeJson(request)) {
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            if (exception.getCause() instanceof LockedException) {
                new ObjectMapper().writeValue(response.getOutputStream(),
                        new Result().of(401, exception.getMessage(), null, null));
            } else if (exception.getCause() instanceof ValidCodeException) {
                new ObjectMapper().writeValue(response.getOutputStream(),
                        new Result().of(401, exception.getMessage(), null, null));
            } else {
                new ObjectMapper().writeValue(response.getOutputStream(),
                        new Result().of(401, MessageUtils.get("login.failed"), null, null));
            }
        } else {
            request.setAttribute("message", MessageUtils.get("unauthorized"));
            redirectStrategy.sendRedirect(request, response, defaultTargetUrl);
        }
    }
}
