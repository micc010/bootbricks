package com.github.rogerli.security.verifier;

import org.springframework.security.core.AuthenticationException;

/**
 *
 *
 * @author roger.li
 * @since 2018/11/16
 */
public class ValidCodeException extends AuthenticationException {

    public ValidCodeException(String msg, Throwable t) {
        super(msg, t);
    }

    public ValidCodeException(String msg) {
        super(msg);
    }
}
