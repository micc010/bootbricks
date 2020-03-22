package com.github.rogerli.framework.interfaces;

/**
 * IdInterface 主键接口
 *
 * @author roger.li
 * @date 2015/8/24
 */
public interface IdInterface<K> {

    K getId();

    void setId(K id);

}
