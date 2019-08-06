package com.cchilei.blog.pojo.viewconfig;

/**
 * @Author
 * @Create 2018-05-18 20:28
 * 关于本站的内容
 */
public class ViewAboutStation {
    private String title;
    private String detail;

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
        return "ViewAboutStation{" +
                "title='" + title + '\'' +
                ", detail='" + detail + '\'' +
                '}';
    }
}
