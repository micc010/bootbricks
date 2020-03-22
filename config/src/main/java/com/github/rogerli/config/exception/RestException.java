
package com.github.rogerli.config.exception;

import lombok.Data;

/**
 * Created by lt on 2017/6/20.
 */
@Data
public class RestException extends RuntimeException {

    private String message;
    private int code = -1;

    /**
     *
     * @param msg
     */
    public RestException(String msg) {
        super(msg);
        this.message = msg;
    }

    /**
     *
     * @param msg
     * @param e
     */
    public RestException(String msg, Throwable e) {
        super(msg, e);
        this.message = msg;
    }

    /**
     *
     * @param msg
     * @param code
     */
    public RestException(String msg, int code) {
        super(msg);
        this.message = msg;
        this.code = code;
    }

    /**
     *
     * @param msg
     * @param code
     * @param e
     */
    public RestException(String msg, int code, Throwable e) {
        super(msg, e);
        this.message = msg;
        this.code = code;
    }

}
