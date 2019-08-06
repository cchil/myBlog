package com.cchilei.blog.vo;

import java.util.Collection;

/**
 * @Author
 * @Create 2018-05-18 14:58
 */
public class LabelsManageVo {
    private String text;
    private Collection<String> tags;
    private Collection<LabelsManageVo> nodes;
    private String parentId;
    private String parentName;
    private String id;

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public LabelsManageVo() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Collection<String> getTags() {
        return tags;
    }

    public void setTags(Collection<String> tags) {
        this.tags = tags;
    }

    public Collection<LabelsManageVo> getNodes() {
        return nodes;
    }

    public void setNodes(Collection<LabelsManageVo> nodes) {
        this.nodes = nodes;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LabelsManageVo that = (LabelsManageVo) o;

        if (!text.equals(that.text)) return false;
        if (!tags.equals(that.tags)) return false;
        return nodes.equals(that.nodes);
    }

    @Override
    public String toString() {
        return "LabelsManageVo{" +
                "text='" + text + '\'' +
                ", tags=" + tags +
                ", nodes=" + nodes +
                ", parentId='" + parentId + '\'' +
                ", parentName='" + parentName + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
