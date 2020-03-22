package com.github.rogerli.system.role.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by Administrator on 2017/3/28.
 */
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Table(name = "sys_role_purview")
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel("SYS系统角色与权限关联对象")
public class RolePurview implements Serializable {

    @ApiModelProperty("角色id")
    private String roleId;
    @ApiModelProperty("权限id")
    private Integer purviewId;

}
