package com.github.rogerli.framework.interfaces;


import com.github.rogerli.framework.model.SortedItem;

import java.util.List;

/**
 * SortedInterface 查询排序接口
 *
 * @author roger.li
 * @date 2015/8/24
 */
public interface SortedInterface {

    List<SortedItem> getSorted();

    void setSorted(List<SortedItem> sorted);

}
