package com.github.rogerli.framework.interfaces;

import com.github.rogerli.framework.model.QueryItem;
import com.github.rogerli.framework.model.RangeItem;

import java.util.List;

/**
 * QueryInterface 查询接口
 *
 * @author roger.li
 * @date 2015/8/24
 */
public interface QueryInterface extends SortedInterface {

    /**
     * get 返回字段
     *
     * @return
     */
    List<QueryItem> getDataFields();

    /**
     * set 返回字段
     *
     * @param dataFields
     */
    void setDataFields(List<QueryItem> dataFields);

    /**
     * get like条件
     *
     * @return
     */
    List<QueryItem> getLikeFields();

    /**
     * set like条件
     *
     * @param likeFields
     */
    void setLikeFields(List<QueryItem> likeFields);

    /**
     * get range条件
     *
     * @return
     */
    List<RangeItem> getRangeFields();

    /**
     * set range条件
     *
     * @param rangeFields
     */
    void setRangeFields(List<RangeItem> rangeFields);

}
