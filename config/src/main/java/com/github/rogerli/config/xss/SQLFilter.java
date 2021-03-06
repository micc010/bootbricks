
package com.github.rogerli.config.xss;

import com.github.rogerli.framework.exception.RestException;
import com.github.rogerli.utils.MessageUtils;
import org.springframework.util.StringUtils;

/**
 * SQL过滤
 *
 * @author roger.li
 * @since 2018-03-30
 */
public class SQLFilter {

    /**
     * SQL注入过滤
     *
     * @param str
     *         待验证的字符串
     */
    public static String sqlInject(String str) {
        if (!StringUtils.hasText(str)) {
            return null;
        }
        //去掉'|"|;|\字符
        str = StringUtils.replace(str, "'", "");
        str = StringUtils.replace(str, "\"", "");
        str = StringUtils.replace(str, ";", "");
        str = StringUtils.replace(str, "\\", "");

        //转换成小写
        str = str.toLowerCase();

        //非法字符
        String[] keywords = {"master", "truncate", "insert", "select", "delete", "update", "declare", "alter", "drop"};

        //判断是否包含非法字符
        for (String keyword : keywords) {
            if (str.indexOf(keyword) != -1) {
                throw new RestException(MessageUtils.get("sql.illegal"));
            }
        }

        return str;
    }

}
