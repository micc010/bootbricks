package com.github.rogerli.system.role.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.rogerli.framework.entity.Entity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 角色
 *
 * @author roger.li
 * @date 2015/8/24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Table(name = "sys_role")
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel("SYS系统角色")
public class Role implements Entity<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    @ApiModelProperty("角色标识")
    private String role;
    @ApiModelProperty("角色名称")
    private String roleName;
    @ApiModelProperty("描述")
    private String descriptions;
    @ApiModelProperty("图标")
    private String icon;
    @ApiModelProperty("是否有效")
    private Integer valid;
    @ApiModelProperty("序号")
    private Integer sortNum;
    @ApiModelProperty("单位id")
    private String organId;
    @ApiModelProperty("单位名称")
    private String organName;

}