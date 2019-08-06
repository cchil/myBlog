package com.cchilei.blog.pojo.viewconfig;

import java.util.List;

/**
 * @Author
 * @Create 2018-05-18 20:23
 * 内容列表中的图片配置
 */
public class ViewContentItem {

    private List<String> href;

    private Integer size = 0;

    public Integer getSize() {
        return size;
    }

    public List<String> getHref() {
        return href;
    }

    public void setHref(List<String> href) {
        this.href = href;
        this.size = href.size();
    }
}
