package com.cchilei.blog.dao;


import com.cchilei.blog.pojo.Resources;

import java.util.List;

public interface ResourcesMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Resources record);

    int insertSelective(Resources record);

    Resources selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Resources record);

    int updateByPrimaryKey(Resources record);

    /**
     * 查找所有的
     * @return
     */
    List<Resources> selectResourceAll();

    /**
     * 统计当前表中的总数量
     * @return
     */
    int selectCount();
}