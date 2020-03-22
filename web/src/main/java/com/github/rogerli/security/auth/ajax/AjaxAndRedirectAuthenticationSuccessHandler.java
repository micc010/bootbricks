package com.github.rogerli.security.auth.ajax;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.rogerli.framework.model.Result;
import com.github.rogerli.utils.MessageUtils;
import com.github.rogerli.utils.RestUtils;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
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
public class AjaxAndRedirectAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    private final String defaultTargetUrl;

    public AjaxAndRedirectAuthenticationSuccessHandler(String defaultSuccessUrl) {
        Assert.isTrue(UrlUtils.isValidRedirectUrl(defaultSuccessUrl), "url must start with '/' or with 'http(s)'");
        this.defaultTargetUrl = defaultSuccessUrl;
    }

    /**
     * @param request
     * @param response
     * @param authentication
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        request.getSession().removeAttribute("validCode");
        if (RestUtils.isAjax(request) || RestUtils.isContentTypeJson(request)) {
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            new ObjectMapper().writeValue(response.getOutputStream(),
                    new Result().ok(MessageUtils.get("login.ok")));
        } else {
            request.setAttribute("message", MessageUtils.get("login.ok"));
            redirectStrategy.sendRedirect(request, response, defaultTargetUrl);
        }
    }
}
