package com.cchilei.blog.dao;

import com.cchilei.blog.pojo.Content;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ContentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Content record);

    int insertSelective(Content record);

    Content selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Content record);

    int updateByPrimaryKey(Content record);

    /**
     * 查询文章通过 标签的id查找
     *
     * @param id
     * @return
     */
    List<Content> selectContentByLabelsId(Integer id);

    /**
     * 查询所有的
     *
     * @return
     */
    List<Content> selectContentForAll();

    /**
     * 增加文章的阅读量
     *
     * @param id 通过id
     * @return
     */
    int updateForReadCount(Integer id);

    /**
     * 模糊查询通过 内容进行查询
     *
     * @param msg
     * @return
     */
    List<Content> searchContentByDetail(String msg);

    /**
     * 统计总数
     *
     * @return
     */
    int selectCount();

    /**
     * 根据ID查询出这个文章的阅读量
     *
     * @param id
     * @return
     */
    int selectReadCountById(Integer id);

    /**
     * 更新一个文字的阅读量通过ID
     * @param count
     * @param id
     * @return
     */
    int updateReadCountByID(@Param("count") Integer count,@Param("id")Integer id);
}