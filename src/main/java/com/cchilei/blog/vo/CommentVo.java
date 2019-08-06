package com.cchilei.blog.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author
 * @Create 2018-05-24 22:28
 */
public class CommentVo implements Serializable {
    private String detail;
    private String email;
    private String contentTitle;
    private Date createTime;
    private Integer commentId;
    private Integer contentId;

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContentTitle() {
        return contentTitle;
    }

    public void setContentTitle(String contentTitle) {
        this.contentTitle = contentTitle;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public Integer getContentId() {
        return contentId;
    }

    public void setContentId(Integer contentId) {
        this.contentId = contentId;
    }

    @Override
    public String toString() {
        return "CommentVo{" +
                "detail='" + detail + '\'' +
                ", email='" + email + '\'' +
                ", contentTitle='" + contentTitle + '\'' +
                ", createTime=" + createTime +
                ", commentId=" + commentId +
                ", contentId=" + contentId +
                '}';
    }
}
