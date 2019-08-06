package com.cchilei.blog.vo;

import com.cchilei.blog.pojo.Labels;

import java.util.List;

/**
 * @Author
 * @Create 2018-05-15 10:11
 */
public class ContentViewVO {
    private List<Labels> labels;
    private String detail;
    private Integer id;

    private String title;
    private Integer labelId;

    public List<Labels> getLabels() {
        return labels;
    }

    public void setLabels(List<Labels> labels) {
        this.labels = labels;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
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
        this.title = title;
    }

    public Integer getLabelId() {
        return labelId;
    }

    public void setLabelId(Integer labelId) {
        this.labelId = labelId;
    }

    @Override
    public String toString() {
        return "ContentViewVO{" +
                "labels=" + labels +
                ", detail='" + detail + '\'' +
                ", id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", labelId=" + labelId +
                '}';
    }
}
