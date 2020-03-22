package com.github.rogerli.system.login.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 用户角色关联
 *
 * @author roger.li
 * @date 2015/8/24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Table(name = "sys_login_role")
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel("SYS用户角色关联")
public class LoginRole implements Serializable {

    @NotBlank
    @ApiModelProperty("用户id")
    private String loginId;
    @NotBlank
    @ApiModelProperty("角色id")
    private String roleId;
    @NotBlank
    @ApiModelProperty("用户名")
    private String username;

}
