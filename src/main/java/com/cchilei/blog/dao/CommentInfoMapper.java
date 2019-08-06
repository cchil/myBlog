package com.cchilei.blog.dao;

import com.cchilei.blog.pojo.CommentInfo;
import com.cchilei.blog.vo.CommentVo;

import java.util.List;

/**
 * @author Chen
 */
public interface CommentInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CommentInfo record);

    int insertSelective(CommentInfo record);

    CommentInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CommentInfo record);

    int updateByPrimaryKey(CommentInfo record);

    /**
     * 查询出所有的未处理的消息
     * @return
     */
    List<CommentVo> selectUnHandlerInfos();

    /**
     * 查询未处理的消息总数
     * @return
     */
    int selectCount();

    /**
     * 删除所有的消息
     * @return
     */
    int deleteAll();

}