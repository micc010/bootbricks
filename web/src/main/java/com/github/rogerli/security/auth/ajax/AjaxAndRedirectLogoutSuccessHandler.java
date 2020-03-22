package com.github.rogerli.security.auth.ajax;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.rogerli.framework.model.Result;
import com.github.rogerli.utils.MessageUtils;
import com.github.rogerli.utils.RestUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
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
public class AjaxAndRedirectLogoutSuccessHandler implements LogoutSuccessHandler {

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    private final String defaultTargetUrl;

    public AjaxAndRedirectLogoutSuccessHandler(String logoutUrl) {
        Assert.isTrue(UrlUtils.isValidRedirectUrl(logoutUrl), "url must start with '/' or with 'http(s)'");
        this.defaultTargetUrl = logoutUrl;
    }

    /**
     * @param request
     * @param response
     * @param authentication
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        if (RestUtils.isAjax(request) || RestUtils.isContentTypeJson(request)) {
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            response.setStatus(HttpStatus.OK.value());
            new ObjectMapper().writeValue(response.getWriter(),
                    new Result().ok(MessageUtils.get("logout.ok")));
        } else {
            request.setAttribute("message", MessageUtils.get("logout.ok"));
            redirectStrategy.sendRedirect(request, response, defaultTargetUrl);
        }
    }
}
