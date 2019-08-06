package com.cchilei.blog.pojo;

import java.io.Serializable;
import java.util.Date;

public class Content implements Serializable {
    private Integer id;

    private String detail;

    private Integer labelId;

    private Integer readCount;

    private Date createTime;

    private String title;

    private Integer contentType;

    public Content(Integer id, String detail, Integer labelId, Integer readCount, Date createTime, String title,Integer contentType) {
        this.id = id;
        this.detail = detail;
        this.labelId = labelId;
        this.readCount = readCount;
        this.createTime = createTime;
        this.title = title;
        this.contentType = contentType;
    }

    public Content() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail == null ? null : detail.trim();
    }

    public Integer getLabelId() {
        return labelId;
    }

    public void setLabelId(Integer labelId) {
        this.labelId = labelId;
    }

    public Integer getReadCount() {
        return readCount;
    }

    public void setReadCount(Integer readCount) {
        this.readCount = readCount;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public Integer getContentType() {
        return contentType;
    }

    public void setContentType(Integer contentType) {
        this.contentType = contentType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Content content = (Content) o;

        return id.equals(content.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "Content{" +
                "id=" + id +
                ", detail='" + detail + '\'' +
                ", labelId=" + labelId +
                ", readCount=" + readCount +
                ", createTime=" + createTime +
                ", title='" + title + '\'' +
                ", contentType=" + contentType +
                '}';
    }
}