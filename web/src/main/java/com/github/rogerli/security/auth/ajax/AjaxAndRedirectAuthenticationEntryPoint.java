package com.github.rogerli.security.auth.ajax;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.rogerli.framework.model.Result;
import com.github.rogerli.utils.MessageUtils;
import com.github.rogerli.utils.RestUtils;
import com.google.common.collect.Maps;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author roger.li
 * @since 2018/11/11
 */
@Component
public class AjaxAndRedirectAuthenticationEntryPoint extends LoginUrlAuthenticationEntryPoint {

    /**
     * @param loginFormUrl URL where the login page can be found. Should either be relative to the web-app context path (include a
     *                     leading {@code /}) or an absolute URL.
     */
    public AjaxAndRedirectAuthenticationEntryPoint(String loginFormUrl) {
        super(loginFormUrl);
    }

    /**
     * Performs the redirect (or forward) to the login form URL.
     */
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        if (RestUtils.isAjax(request) || RestUtils.isContentTypeJson(request)) {
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            response.setStatus(HttpStatus.FOUND.value());
            new ObjectMapper().writeValue(response.getWriter(),
                    new Result().of(302, MessageUtils.get("expired"), null,
                            Maps.newHashMap().put("url", super.getLoginFormUrl())));
        } else {
            request.setAttribute("message", MessageUtils.get("expired"));
            super.commence(request, response, authException);
        }
    }
}
