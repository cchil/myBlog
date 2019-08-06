package com.cchilei.blog.dao;

import com.cchilei.blog.pojo.Admin;

public interface AdminMapper {
    int deleteByPrimaryKey(String accountId);

    int insert(Admin record);

    int insertSelective(Admin record);

    Admin selectByPrimaryKey(String accountId);

    int updateByPrimaryKeySelective(Admin record);

    int updateByPrimaryKey(Admin record);


}