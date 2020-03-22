package com.github.rogerli.framework.model;

import com.github.rogerli.utils.MessageUtils;
import com.google.common.collect.ImmutableMap;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * Result 请求响应返回对象
 *
 * @author roger.li
 * @since 2018-03-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel("请求响应返回对象")
public class Result<T> implements Serializable {

    @ApiModelProperty("响应状态")
    private Integer status;
    @ApiModelProperty("校验信息")
    private ImmutableMap<String, Object> valid;
    @ApiModelProperty("消息")
    private String message;
    @ApiModelProperty("返回数据")
    private T data;
    @ApiModelProperty("时间戳")
    private Long timestamp;

    /**
     * 构建返回failed
     *
     * @return
     */
    public Result error() {
        return error(MessageUtils.get("server.error"));
    }

    /**
     * 构建返回failed
     *
     * @param msg
     * @return
     */
    public Result error(String msg) {
        return error(msg, null);
    }

    /**
     * 构建返回failed
     *
     * @param msg
     * @param valid
     * @return
     */
    public Result error(String msg, ImmutableMap<String, Object> valid) {
        return error(msg, valid, null);
    }

    /**
     * 构建返回failed
     *
     * @param msg
     * @param valid
     * @param data
     * @return
     */
    public Result error(String msg, ImmutableMap<String, Object> valid, T data) {
        return of(-1, msg, data, valid);
    }

    /**
     * 构建返回ok
     *
     * @return
     */
    public Result ok() {
        return ok(MessageUtils.get("server.ok"));
    }

    /**
     * 构建返回ok
     *
     * @param msg
     * @return
     */
    public Result ok(String msg) {
        return ok(msg, null);
    }

    /**
     * 构建返回ok
     *
     * @param msg
     * @param data
     * @return
     */
    public Result ok(String msg, T data) {
        return ok(msg, data, null);
    }

    /**
     * 构建返回ok
     *
     * @param msg
     * @param data
     * @param valid
     * @return
     */
    public Result ok(String msg, T data, ImmutableMap<String, Object> valid) {
        return of(0, msg, data, valid);
    }

    /**
     * 构建返回result
     *
     * @param status
     * @param msg
     * @param valid
     * @param data
     * @return
     */
    public Result of(Integer status, String msg, T data, ImmutableMap<String, Object> valid) {
        return this.setStatus(status)
                .setValid(valid)
                .setMessage(msg)
                .setData(data)
                .setTimestamp(new Date().getTime());
    }

}
