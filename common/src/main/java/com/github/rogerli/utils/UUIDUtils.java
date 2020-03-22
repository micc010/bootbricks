package com.github.rogerli.utils;

import java.util.UUID;

/**
 * @author roger.li
 * @since 2018/7/5
 */
public class UUIDUtils {

    /**
     * 替换'-'后的UUID
     *
     * @return
     */
    public static String id() {
        return all().replaceAll("-", "");
    }

    /**
     * 未替换'-'的UUID
     *
     * @return
     */
    public static String all() {
        return UUID.randomUUID().toString();
    }

}
