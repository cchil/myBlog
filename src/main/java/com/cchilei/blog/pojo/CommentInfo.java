package com.cchilei.blog.pojo;

import java.io.Serializable;

public class CommentInfo implements Serializable {
    private Integer id;

    private Integer commentId;

    public CommentInfo(Integer id, Integer commentId) {
        this.id = id;
        this.commentId = commentId;
    }

    public CommentInfo() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }
}