package com.github.rogerli.framework.interfaces;


/**
 * PageInterface 分页查询接口
 *
 * @author roger.li
 * @date 2015/8/24
 */
public interface PageInterface extends QueryInterface {

    Integer getPageSize();

    void setPageSize(Integer pageSize);

    Integer getPageNum();

    void setPageNum(Integer pageNum);

}
