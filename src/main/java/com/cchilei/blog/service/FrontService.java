package com.cchilei.blog.service;

import com.cchilei.blog.common.ServerResponse;
import com.cchilei.blog.pojo.*;
import com.cchilei.blog.pojo.*;
import com.github.pagehelper.PageInfo;

import javax.validation.constraints.Email;
import java.util.List;

/**
 * @Author
 * @Create 2018-05-09 20:35
 */
public interface FrontService {

    /**
     * 创建一个评论
     * @param email
     * @param name
     * @param detail
     * @param infoId
     * @return
     */
    ServerResponse createComment(@Email String email, String name, String detail, Integer infoId);

    /**
     * 查新评论 通过文章的id查询
     * @param contentId
     * @param num 分页 页码
     * @return
     */
    ServerResponse<PageInfo> findComment(Integer contentId, int num);

    /**
     * 查询所有的标签
     * @return
     */
    ServerResponse<List<Labels>> findLabelsAll();

    /**
     * 查询一个标签通过这个标签的id号
     * @param labelId
     * @return
     */
    ServerResponse<Labels> findLabelsById(Integer labelId);

    /**
     * 查询标签通过它的父标签id
     * @param parentId
     * @return
     */
    ServerResponse<List<Labels>> findLabelsByParentId(Integer parentId);

    /**
     * 查询出所有的根标签
     * @return
     */
    ServerResponse<List<Labels>> findLabelsByRootForAll();

    /**
     * 查询一片文章通过它的id查询
     * @param id
     * @return
     */
    ServerResponse<Content> findContentById(Integer id);


    /**
     * 查找文章通过标签的号码进行查找
     * @param labelsId
     * @param num 分页 页码
     * @return
     */
    ServerResponse<PageInfo> findContentByLabelsId(Integer labelsId, int num);

    /**
     * 查询所有的文章
     * @param num 分页 页码
     * @return
     */
    ServerResponse<PageInfo<Content>> findContentAll(int num);

    /**
     * 模糊查询文章 通过关键字
     * @param msg
     * @param num
     * @return
     */
    ServerResponse<PageInfo<Content>> searchContentByDetail(String msg, int num);

    /**
     * 增加一个文章阅读量
     * @param contentId
     */
    void plusContentReadCount(Integer contentId);

    /**
     * 查询所有的资源
     * @param num 分页 页码
     * @return
     */
    ServerResponse<PageInfo<Resources>> findResourcesAll(int num);

    /**
     * 获取前台页面的样式配置信息
     * @return
     */
    ServerResponse<Theme> findThemeConfig();

    /**
     * 获取管理员的基本信息
     * @return
     */
    ServerResponse<Admin> findAdminMessage();

    /**
     * 操作阅读量的 每隔规定的时间进行一次数据库的更新操作
     * @param contentId
     * @return
     */
    ServerResponse<Integer> readContentAutoPlus(Integer contentId);
}
