package com.cchilei.blog.pojo;

import java.io.Serializable;

public class Labels implements Serializable ,Cloneable{
    private Integer id;

    private Integer parentId;

    private String name;

    public Labels(Integer id, Integer parentId, String name) {
        this.id = id;
        this.parentId = parentId;
        this.name = name;
    }

    public Labels(Integer parentId, String name) {
        this.parentId = parentId;
        this.name = name;
    }

    public Labels() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Labels labels = (Labels) o;

        if (!id.equals(labels.id)) {
            return false;
        }
        if (!parentId.equals(labels.parentId)) {
            return false;
        }
        return name.equals(labels.name);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + parentId.hashCode();
        result = 31 * result + name.hashCode();
        return result;
    }

    public Labels getClone(){
        try {
            return (Labels) super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }
}