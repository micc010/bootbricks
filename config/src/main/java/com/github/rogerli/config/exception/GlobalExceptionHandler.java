package com.github.rogerli.config.exception;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.rogerli.framework.model.Result;
import com.github.rogerli.utils.MessageUtils;
import com.github.rogerli.utils.RestUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * @author Roger
 * @create 2016/12/2 16:58
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * @param exception
     *
     * @return
     */
    @ExceptionHandler
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ResponseBody
    public String requestHandlingNoHandlerFound(HttpServletResponse response, NoHandlerFoundException exception) {
        log.error("exceptionHandler", exception);
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        try {
            objectMapper.writeValue(response.getOutputStream(), new Result().of(HttpStatus.NOT_FOUND.value(), MessageUtils.get("not.found"), null, null).toString());
            return null;
        } catch (IOException e) {
            log.error("IOException", e);
            return null;
        }
    }

    /**
     * 文件超大
     *
     * @return
     *
     * @throws Exception
     */
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    @ResponseBody
    public String handleAll(HttpServletResponse response, MaxUploadSizeExceededException e) throws Exception {
        log.info("Upload File Max Size Exceeded Exception:", e.getMessage());
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        objectMapper.writeValue(response.getOutputStream(), new Result().error(MessageUtils.get("file.size.limit")));
        return null;
    }

    /**
     * 统一错误处理
     *
     * @param request
     *         http请求
     * @param response
     *         http响应
     * @param exception
     *         异常
     *
     * @return 异常页面
     */
    @ExceptionHandler()
    public String exceptionHandler(HttpServletRequest request, HttpServletResponse response,
                                   Exception exception) {
        log.error("exceptionHandler", exception);
        if (RestUtils.isAjax(request) || RestUtils.isContentTypeJson(request)) {
            return jsonExceptionHandler(request, response, exception);
        } else {
            return htmlExceptionHandler(request, response, exception);
        }
    }

    /**
     * @param response
     *         响应
     *
     * @return 页面
     */
    private String jsonExceptionHandler(HttpServletRequest request, HttpServletResponse response,
                                        Exception exception) {
        try {
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            log.error("Internal Server Error", exception.getMessage());
            objectMapper.writeValue(response.getOutputStream(), new Result().error(MessageUtils.get("server.error")));
            return null;
        } catch (JsonGenerationException var9) {
            log.error("JsonGenerationException", var9);
            var9.printStackTrace();
            return null;
        } catch (JsonMappingException var10) {
            log.error("JsonMappingException", var10);
            var10.printStackTrace();
            return null;
        } catch (IOException var11) {
            log.error("IOException", var11);
            var11.printStackTrace();
            return null;
        }
    }

    protected HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request
                .getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        try {
            return HttpStatus.valueOf(statusCode);
        } catch (Exception ex) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

    /**
     * @param request
     *         请求
     * @param exception
     *         异常
     *
     * @return 页面
     */
    private String htmlExceptionHandler(HttpServletRequest request, HttpServletResponse response,
                                        Exception exception) {
        HttpStatus status = getStatus(request);
        request.setAttribute("status", status.value());
        request.setAttribute("message", exception.getMessage());
        request.setAttribute("timestamp", new Date());
        return "error";
    }

}
