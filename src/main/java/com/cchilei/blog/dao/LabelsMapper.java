package com.cchilei.blog.dao;

import com.cchilei.blog.pojo.Labels;

import java.util.List;

public interface LabelsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Labels record);

    int insertSelective(Labels record);

    Labels selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Labels record);

    int updateByPrimaryKey(Labels record);

    /**
     * 删除这个标签通过key 还删除掉它的子标签
     * @param id
     * @return
     */
    int deleteLablesAndChildByKey(Integer id);

    List<Labels> selectAll();

    /**
     * 查询一个标签通过它的父标签的id
     * @param id
     * @return
     */
    List<Labels> selectLabelsByParentId(Integer id);

    /**
     * 查询出所有的可以直接作为类别使用的标签没有字标签的标签
     * @return
     */
    List<Labels> selectLabelsByFinalChild();
}