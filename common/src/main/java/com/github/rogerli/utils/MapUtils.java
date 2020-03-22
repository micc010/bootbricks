
package com.github.rogerli.utils;

import java.util.HashMap;


/**
 * Map工具
 *
 * @author roger.li
 * @since 2018-03-30
 */
public class MapUtils extends HashMap<String, Object> {

    /**
     * 添加键值
     *
     * @param key
     * @param value
     * @return
     */
    @Override
    public MapUtils put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
