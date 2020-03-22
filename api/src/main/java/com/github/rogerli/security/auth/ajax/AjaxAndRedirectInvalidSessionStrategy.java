package com.github.rogerli.security.auth.ajax;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.rogerli.framework.model.Result;
import com.github.rogerli.utils.MessageUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.session.InvalidSessionStrategy;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author roger.li
 * @since 2018/11/9
 */
public class AjaxAndRedirectInvalidSessionStrategy implements InvalidSessionStrategy {

    private boolean createNewSession = true;

    /**
     * @param request
     * @param response
     *
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void onInvalidSessionDetected(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
//        if (createNewSession) {
//            request.getSession();
//        }
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        response.setStatus(HttpStatus.FOUND.value());
        new ObjectMapper().writeValue(response.getOutputStream(),
                new Result().ok(MessageUtils.get("session.expired")));
    }

    /**
     * Determines whether a new session should be created before redirecting (to avoid possible looping issues where the
     * same session ID is sent with the redirected request). Alternatively, ensure that the configured URL does not pass
     * through the {@code SessionManagementFilter}.
     *
     * @param createNewSession
     *         defaults to {@code true}.
     */
    public void setCreateNewSession(boolean createNewSession) {
        this.createNewSession = createNewSession;
    }
}  