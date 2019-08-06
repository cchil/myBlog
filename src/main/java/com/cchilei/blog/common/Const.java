package com.cchilei.blog.common;

import org.apache.commons.lang3.StringUtils;

/**
 * @Author
 * @Create 2018-05-08 20:51
 */
public class Const {


    /**
     * 当前登录的对象Key值
     */
    public static final String CURRENT_ADMIN = "CurrentAdmin";

    /**
     * ViewConfiguration配置类的的获取Key名
     */
    public static final String KEY_SETTINGS = "settings";

    /**
     * 文件访问的服务器路径
     */
    public static String FILE_ROOT_URL = StringUtils.EMPTY;
    /**
     * 文件上传的绝对文件路径
     */
    public static String DIRECTORY_URI = StringUtils.EMPTY;

    /**
     * 配置文件路径位置
     */
    public interface FileCommon {
        String ADMIN_DIR = "admin";
        String DATA_DIR = "data";
        String IMG_DIR = "img";
        String CSS_DIR = "css";
        String JS_DIR = "js";
    }

    /**
     * 编辑器类型
     */
    public interface ContentTypeCommon {
        Integer MARKDOWN = 1;
        Integer EDITOR = 0;
    }
}
