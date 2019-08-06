package com.cchilei.blog.pojo.viewconfig;

import java.util.List;

/**
 * @Author
 * @Create 2018-05-18 20:30
 * 展示在右侧关于我中的标签云内容
 */
public class ViewTagCloud {

    private List<TagItem> tagItems;

    public List<TagItem> getTagItems() {
        return tagItems;
    }

    public void setTagItems(List<TagItem> tagItems) {
        this.tagItems = tagItems;
    }

    public static class TagItem {
        private String title;
        private String href;

        public TagItem(String title, String href) {
            this.title = title;
            this.href = href;
        }

        public TagItem() {
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getHref() {
            return href;
        }

        public void setHref(String href) {
            this.href = href;
        }

        @Override
        public String toString() {
            return "ViewTagCloud{" +
                    "title='" + title + '\'' +
                    ", href='" + href + '\'' +
                    '}';
        }
    }

}
