package com.cchilei.blog.dao;

import com.cchilei.blog.pojo.Comment;
import com.cchilei.blog.vo.CommentVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Chen
 */
public interface CommentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Comment record);

    int insertSelective(Comment record);

    Comment selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Comment record);

    int updateByPrimaryKey(Comment record);

    List<Comment> selectCommentByContentId(Integer id);

    /**
     * 查询所有
     * @param sort
     * @param search
     * @return
     */
    List<CommentVo> selectCommentAll(@Param("sort") String sort, @Param("search") String search);
}