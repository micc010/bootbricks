package com.github.rogerli.security.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.rogerli.security.form.LoginForm;
import com.github.rogerli.security.verifier.ValidCodeException;
import com.github.rogerli.utils.MessageUtils;
import com.github.rogerli.utils.RestUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author roger.li
 * @since 2018/11/8
 */
public class BoastUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    /**
     * 重写父类登录方法
     *
     * @param request
     * @param response
     * @return
     * @throws AuthenticationException
     */
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {

        String validCodeInSession = (String) request.getSession().getAttribute("validCode");
        //attempt Authentication when Content-Type is json
        if (RestUtils.isAjax(request) || RestUtils.isContentTypeJson(request)) {
            //use jackson to deserialize json
            ObjectMapper objectMapper = new ObjectMapper();
            UsernamePasswordAuthenticationToken authRequest = null;
            try {
                LoginForm authenticationBean = objectMapper.readValue(request.getInputStream(), LoginForm.class);
                if (!validateCode(validCodeInSession, authenticationBean.getValidCode())) {
                    throw new ValidCodeException(MessageUtils.get("valid.error"));
                }
                authRequest = new UsernamePasswordAuthenticationToken(
                        authenticationBean.getUsername(), authenticationBean.getPassword());
            } catch (IOException e) {
                e.printStackTrace();
                authRequest = new UsernamePasswordAuthenticationToken(
                        "", "");
            } finally {
                setDetails(request, authRequest);
                return this.getAuthenticationManager().authenticate(authRequest);
            }
        }

        //transmit it to UsernamePasswordAuthenticationFilter
        else {
            String validCode = request.getParameter("validCode");
            if (!validateCode(validCodeInSession, validCode)) {
                throw new ValidCodeException(MessageUtils.get("valid.error"));
            }
            return super.attemptAuthentication(request, response);
        }
    }

    /**
     * @param validCodeInSession
     * @param validCode
     */
    private boolean validateCode(String validCodeInSession, String validCode) {
        if (StringUtils.isEmpty(validCodeInSession) || !validCodeInSession.equals(validCode)) {
            return false;
        }
        return true;
    }
}
