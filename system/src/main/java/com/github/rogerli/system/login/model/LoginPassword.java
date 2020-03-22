package com.github.rogerli.system.login.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 新密码，旧密码
 *
 * @author roger.li
 * @date 2015/8/24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel("SYS修改密码")
public class LoginPassword implements Serializable {

    @ApiModelProperty("账号")
    private String username;
    @ApiModelProperty("新密码")
    private String password;
    @ApiModelProperty("旧密码")
    private String oldPassword;

}
