package com.github.rogerli.framework.model;

import com.github.rogerli.framework.interfaces.Field;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;


/**
 * SortedItem 排序对象
 *
 * @author roger.li
 * @date 2015/8/24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel("排序对象")
public class SortedItem implements Serializable {

    @ApiModelProperty("排序属性")
    private String sortby;
    @ApiModelProperty("排序方式")
    private String sorted;

}
