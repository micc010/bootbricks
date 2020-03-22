package com.github.rogerli.system.login.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Id;

/**
 * 用户参数
 *
 * @author roger.li
 * @since 2019/5/27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel("SYS用户请求参数对象")
public class LoginQuery extends Query<String> {

    @ApiModelProperty("用户名")
    private String username;
    @ApiModelProperty("真实姓名")
    private String fullName;
    @ApiModelProperty("是否被锁定")
    private Integer locked;
    @ApiModelProperty("单位id")
    private String organId;
    @ApiModelProperty("单位名称")
    private String organName;
    @ApiModelProperty("角色id")
    private String roleId;

}
