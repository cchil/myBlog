package com.cchilei.blog.vo;

import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @Author
 * @Create 2018-05-25 17:47
 */
public class MainVo {
    private PageInfo<CommentVo> commentInfo;
    private List<ContentVo> contentList;
    private Integer peopleCount;
    private Integer resourcesCount;
    private Integer contentCount;
    private Integer infoCount;

    public PageInfo<CommentVo> getCommentInfo() {
        return commentInfo;
    }

    public void setCommentInfo(PageInfo<CommentVo> commentInfo) {
        this.commentInfo = commentInfo;
    }

    public List<ContentVo> getContentList() {
        return contentList;
    }

    public void setContentList(List<ContentVo> contentList) {
        this.contentList = contentList;
    }

    public Integer getPeopleCount() {
        return peopleCount;
    }

    public void setPeopleCount(Integer peopleCount) {
        this.peopleCount = peopleCount;
    }

    public Integer getResourcesCount() {
        return resourcesCount;
    }

    public void setResourcesCount(Integer resourcesCount) {
        this.resourcesCount = resourcesCount;
    }

    public Integer getContentCount() {
        return contentCount;
    }

    public void setContentCount(Integer contentCount) {
        this.contentCount = contentCount;
    }

    public Integer getInfoCount() {
        return infoCount;
    }

    public void setInfoCount(Integer infoCount) {
        this.infoCount = infoCount;
    }

    @Override
    public String toString() {
        return "MainVo{" +
                "commentInfo=" + commentInfo +
                ", contentList=" + contentList +
                ", peopleCount=" + peopleCount +
                ", resourcesCount=" + resourcesCount +
                ", contentCount=" + contentCount +
                ", infoCount=" + infoCount +
                '}';
    }
}
