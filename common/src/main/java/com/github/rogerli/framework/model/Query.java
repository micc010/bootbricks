package com.github.rogerli.framework.model;


import com.github.rogerli.framework.entity.EntityInterface;
import com.github.rogerli.framework.interfaces.QueryInterface;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * Query 请求查询对象
 *
 * @author roger.li
 * @since 2018-03-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel("请求响应返回对象")
public abstract class Query<K> implements QueryInterface, EntityInterface<K> {

    @ApiModelProperty(value = "返回值")
    private List<QueryItem> dataFields;
    @ApiModelProperty(value = "like条件")
    private List<QueryItem> likeFields;
    @ApiModelProperty(value = "range条件")
    private List<RangeItem> rangeFields;
    @ApiModelProperty(value = "排序条件")
    private List<SortedItem> sorted;


}
