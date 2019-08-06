package com.cchilei.blog.dao;

import com.cchilei.blog.pojo.Setting;

import java.util.List;

public interface SettingMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Setting record);

    int insertSelective(Setting record);

    Setting selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Setting record);

    int updateByPrimaryKey(Setting record);

    List<Setting> selectForAll();

    Setting selectSettingByActive();

    /**
     * 取消所有的激活状态
     *
     * @return
     */
    int cancelActiveAll();

    /**
     * 设置为激活状态 这个ID
     * @param id
     * @return
     */
    int setSettingToActive(Integer id);

}