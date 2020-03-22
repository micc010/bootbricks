package com.github.rogerli.system.role.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author roger.li
 * @since 2019/5/30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel("SYS角色请求参数对象")
public class RoleQuery extends Query<String> {

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
