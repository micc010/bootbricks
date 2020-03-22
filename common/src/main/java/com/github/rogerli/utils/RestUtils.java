package com.github.rogerli.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * RestUtils
 *
 * @author roger.li
 * @date 2015/8/24
 */
public class RestUtils {

    private static final String XML_HTTP_REQUEST = "XMLHttpRequest";
    private static final String X_REQUESTED_WITH = "X-Requested-With";

    private static final String CONTENT_TYPE = "Content-type";
    private static final String CONTENT_TYPE_JSON = "application/json";

    public static boolean isAjax(HttpServletRequest request) {
        return XML_HTTP_REQUEST.equals(request.getHeader(X_REQUESTED_WITH));
    }

    public static boolean isContentTypeJson(HttpServletRequest request) {
        return request.getHeader(CONTENT_TYPE) != null
                && request.getHeader(CONTENT_TYPE).contains(CONTENT_TYPE_JSON);
    }
}
