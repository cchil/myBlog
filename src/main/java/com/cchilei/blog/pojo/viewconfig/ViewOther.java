package com.cchilei.blog.pojo.viewconfig;

import java.util.List;

/**
 * @Author
 * @Create 2018-05-18 20:34
 * 展示在右侧关于我中的最底部一栏中的内容
 */
public class ViewOther {
    private String title;

    private List<Item> items;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    /**
     * 每一个item的信息
     */
    public static class Item {
        private String text;
        private String href;

        public Item(String text, String href) {
            this.text = text;
            this.href = href;
        }

        public Item() {
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getHref() {
            return href;
        }

        public void setHref(String href) {
            this.href = href;
        }
    }
}
