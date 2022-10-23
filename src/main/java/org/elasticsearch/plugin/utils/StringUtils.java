package org.elasticsearch.plugin.utils;

/**
 * @author Jayden
 * @date 2022/8/4
 */
public class StringUtils {

    public static boolean isEmpty(String s) {
        return s == null || "".equals(s);
    }
}
