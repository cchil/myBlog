package com.cchilei.blog.vo;

import com.cchilei.blog.pojo.Content;
import com.cchilei.blog.pojo.Labels;
import com.github.pagehelper.PageInfo;

import java.util.Collection;

/**
 * @Author
 * @Create 2018-05-16 12:06
 */
public class ContentManageVo {
    private Collection<Labels> labels;
    private PageInfo<Content> contents;
    private Integer currentLabelsId;

    private String requestUrl;

    public Collection<Labels> getLabels() {
        return labels;
    }

    public void setLabels(Collection<Labels> labels) {
        this.labels = labels;
    }

    public PageInfo<Content> getContents() {
        return contents;
    }

    public void setContents(PageInfo<Content> contents) {
        this.contents = contents;
    }

    public Integer getCurrentLabelsId() {
        return currentLabelsId;
    }

    public void setCurrentLabelsId(Integer currentLabelsId) {
        this.currentLabelsId = currentLabelsId;
    }


    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    @Override
    public String toString() {
        return "ContentManageVo{" +
                "labels=" + labels +
                ", contents=" + contents +
                '}';
    }
}
