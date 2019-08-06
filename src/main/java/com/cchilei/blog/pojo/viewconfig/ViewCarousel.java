package com.cchilei.blog.pojo.viewconfig;

import java.util.List;

/**
 * @Author
 * @Create 2018-05-18 20:19
 * 首页轮播图的配置类
 */
public class ViewCarousel {

    private List<OneImg> viewCarousels;

    public List<OneImg> getViewCarousels() {
        return viewCarousels;
    }

    public void setViewCarousels(List<OneImg> viewCarousels) {
        this.viewCarousels = viewCarousels;
    }

    /**
     * 对应的是一个轮播图显示的配置信息
     */
    public static class OneImg {
        /**
         * 图片地址 绝对
         */
        private String href;
        /**
         * 标题
         */
        private String title;
        /**
         * 正文
         */
        private String detail;

        public OneImg(String href, String title, String detail) {
            this.href = href;
            this.title = title;
            this.detail = detail;
        }

        public OneImg() {
        }

        public String getHref() {
            return href;
        }

        public void setHref(String href) {
            this.href = href;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDetail() {
            return detail;
        }

        public void setDetail(String detail) {
            this.detail = detail;
        }

        @Override
        public String toString() {
            return "OneImg{" +
                    "href='" + href + '\'' +
                    ", title='" + title + '\'' +
                    ", detail='" + detail + '\'' +
                    '}';
        }
    }


    @Override
    public String toString() {
        return "ViewCarousel{" +
                "viewCarousels=" + viewCarousels +
                '}';
    }
}
