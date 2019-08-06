package com.cchilei.blog.util;

import org.apache.shiro.crypto.hash.SimpleHash;

/**
 * @Author
 * @Create 2018-05-17 9:45
 */
public class MD5Util {

    public static String encrypt(String str, String salt) {
        return new SimpleHash("MD5", str, salt).toString();
    }

}
