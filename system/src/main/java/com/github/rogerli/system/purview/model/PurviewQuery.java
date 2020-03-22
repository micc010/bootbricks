package com.github.rogerli.system.purview.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 *
 *
 * @author roger.li
 * @since 2019/5/30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel("SYS权限参数对象")
public class PurviewQuery extends Query<Integer> {

    @ApiModelProperty("权限名称")
    private String name;
    @ApiModelProperty("权限类型")
    private String type;
    @ApiModelProperty("图标")
    private String icon;
    @ApiModelProperty("权限地址")
    private String url;
    @ApiModelProperty("是否有效")
    private Integer valid;
    @ApiModelProperty("序号")
    private Integer sortNum;
    @ApiModelProperty("父权限id")
    private Integer parentId;

}
