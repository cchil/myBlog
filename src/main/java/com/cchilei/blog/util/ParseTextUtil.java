package com.cchilei.blog.util;

import org.jsoup.Jsoup;

/**
 * @Author
 * @Create 2018-05-10 9:59
 */
public class ParseTextUtil {

    /**
     * 提取Html中的文本内容
     *
     * @param html
     * @param length 要截取的长度
     * @return
     */
    public static String parseHtmlForText(String html, int length) {
        String text = Jsoup.parse(html).text();
        if (length == -1) {
            return text;
        }
        text = text.replaceAll("#", "").replaceAll("[^0-9a-zA-Z\u4e00-\u9fa5.，,。？“”]+", "");
        return length > text.length() ? text : text.substring(0, length) + "...";
    }

    /**
     * 字符串的截取操作
     *
     * @param str
     * @param length
     * @return
     */
    public static String stringCut(String str, int length) {
        if (str == null) {
            return "";
        }
        return str.length() < length ? str : str.substring(0, length) + "...";
    }

}
