package com.cchilei.blog.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author
 * @Create 2018-05-25 18:32
 */
public class ContentVo implements Serializable {
    private String id;
    private String detail;
    private String title;
    private String labelsName;
    private Date time;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLabelsName() {
        return labelsName;
    }

    public void setLabelsName(String labelsName) {
        this.labelsName = labelsName;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "ContentVo{" +
                "id='" + id + '\'' +
                ", detail='" + detail + '\'' +
                ", title='" + title + '\'' +
                ", labelsName='" + labelsName + '\'' +
                ", time=" + time +
                '}';
    }
}
