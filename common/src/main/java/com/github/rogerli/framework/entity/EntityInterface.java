package com.github.rogerli.framework.entity;

import com.github.rogerli.framework.interfaces.DataInterface;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * EntityInterface 实体接口
 *
 * @author roger.li
 * @date 2015/8/24
 */
public interface EntityInterface<K> extends DataInterface<K>, Serializable {

    LocalDateTime getModifyTime();

    void setModifyTime(LocalDateTime modifyTime);

    String getModifier();

    void setModifier(String modifier);

}
