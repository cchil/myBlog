package com.cchilei.blog.pojo;

import java.io.Serializable;
import java.util.Date;

public class Resources implements Serializable {
    private Integer id;

    private String title;

    private Date createTime;

    private String detail;

    private String url;

    private Integer clickCount;

    public Resources(Integer id, String title, Date createTime, String detail, String url, Integer clickCount) {
        this.id = id;
        this.title = title;
        this.createTime = createTime;
        this.detail = detail;
        this.url = url;
        this.clickCount = clickCount;
    }

    public Resources() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail == null ? null : detail.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public Integer getClickCount() {
        return clickCount;
    }

    public void setClickCount(Integer clickCount) {
        this.clickCount = clickCount;
    }

    @Override
    public String toString() {
        return "Resources{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", createTime=" + createTime +
                ", detail='" + detail + '\'' +
                ", url='" + url + '\'' +
                ", clickCount=" + clickCount +
                '}';
    }
}