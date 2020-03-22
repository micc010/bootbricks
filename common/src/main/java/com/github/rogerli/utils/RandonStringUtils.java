
package com.github.rogerli.utils;

import java.util.Random;

/**
 * RandonString工具
 *
 * @author roger.li
 * @since 2018-03-30
 */
public class RandonStringUtils {

    /**
     * 随机字符串
     *
     * @param length
     * @return
     */
    public static String generateRandomString(int length) {
        String base = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

}
