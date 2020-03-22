package com.github.rogerli.framework.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * DicItem 字典对象
 *
 * @author roger.li
 * @date 2015/8/24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel("字典对象")
public class DicItem implements Serializable{

    @ApiModelProperty("类型")
    private String type;
    @ApiModelProperty("名称")
    private String name;
    @ApiModelProperty("编码")
    private String code;

}
