package com.github.rogerli.framework.model;

import com.github.rogerli.framework.interfaces.Field;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * RangeItem range对象
 *
 * @author roger.li
 * @date 2015/8/24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel("range对象")
public class RangeItem implements Field, Serializable {

    @ApiModelProperty("属性")
    private String field;
    @ApiModelProperty("前值")
    private String front;
    @ApiModelProperty("后值")
    private String back;

}
