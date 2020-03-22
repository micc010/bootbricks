package com.github.rogerli.framework.interfaces;

import java.time.LocalDateTime;

/**
 * DataInterface 数据接口
 *
 * @author roger.li
 * @date 2015/8/24
 */
public interface DataInterface<K> extends IdInterface<K> {

    LocalDateTime getCreateTime();

    void setCreateTime(LocalDateTime createTime);

    String getCreator();

    void setCreator(String creator);

    Boolean getAvailable();

    void setAvailable(Boolean available);

}
