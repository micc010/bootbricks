package com.github.rogerli.system.organ.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 *
 *
 * @author roger.li
 * @since 2019/5/30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel("SYS单位查询参数")
public class OrganQuery extends Query<String> {

    @ApiModelProperty("单位全称")
    private String name;
    @ApiModelProperty("单位编码")
    private String code;
    @ApiModelProperty("单位简称")
    private String shortName;
    @ApiModelProperty("单位类型")
    private String type;
    @ApiModelProperty("父单位id")
    private String parentId;
    @ApiModelProperty("是否有效")
    private Integer valid;
    @ApiModelProperty("是否可选")
    private Integer checkable;

}
