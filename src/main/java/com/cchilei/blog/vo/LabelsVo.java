package com.cchilei.blog.vo;

import com.cchilei.blog.pojo.Labels;

import java.util.Set;

/**
 * @Author
 * @Create 2018-05-11 9:34
 */
public class LabelsVo {

    private Labels root;

    private Set<Labels> child;

    public Labels getRoot() {
        return root;
    }

    public void setRoot(Labels root) {
        this.root = root;
    }

    public Set<Labels> getChild() {
        return child;
    }

    public void setChild(Set<Labels> child) {
        this.child = child;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LabelsVo labelsVo = (LabelsVo) o;

        if (!root.equals(labelsVo.root)) return false;
        return child.equals(labelsVo.child);
    }

    @Override
    public int hashCode() {
        int result = root.hashCode();
        result = 31 * result + child.hashCode();
        return result;
    }
}
