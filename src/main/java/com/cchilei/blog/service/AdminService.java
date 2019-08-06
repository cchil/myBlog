package com.cchilei.blog.service;

import com.cchilei.blog.common.ServerResponse;
import com.cchilei.blog.pojo.Admin;
import com.cchilei.blog.pojo.Content;
import com.cchilei.blog.pojo.Labels;
import com.cchilei.blog.pojo.Resources;
import com.cchilei.blog.pojo.viewconfig.ViewConfiguration;
import com.cchilei.blog.vo.CommentVo;
import com.cchilei.blog.vo.LabelsManageVo;
import com.cchilei.blog.vo.MainVo;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Set;

/**
 * @Author
 * @Create 2018-05-08 19:37
 */
public interface AdminService {
    /**
     * 登录操作
     *
     * @param name
     * @param password
     * @return
     */
    ServerResponse login(String name, String password);

    /**
     * 发布一篇文章
     *
     * @param detail
     * @param title
     * @param labelId
     * @param admin
     * @return
     */
    ServerResponse<Content> publishInfo(String detail, String title, Integer labelId, Integer contentType);

    /**
     * 插入一个新的标签
     *
     * @param parentId
     * @param name
     * @return
     */
    ServerResponse addLabel(Integer parentId, String name);

    /**
     * 更新一个标签名称 通过它的id
     *
     * @param labelId
     * @param newName
     * @return
     */
    ServerResponse updateLabel(Labels labels);

    /**
     * 删除标签 但不能删除默认标签ID为1的,这个删除操作会把旗下的子标签也删除掉
     *
     * @param id
     * @return
     */
    ServerResponse deleteLabels(Integer id);

    /**
     * 更新我的文章
     *
     * @param content
     * @return
     */
    ServerResponse<Content> updateInfo(Content content);

    /**
     * 删除一篇文章
     *
     * @param id
     * @return
     */
    ServerResponse deleteInfo(Integer id);

    /**
     * 更新我的主题
     *
     * @param bg
     * @param css
     * @param js
     * @return
     */
    ServerResponse updateTheme(String bg, String css, String js);

    /**
     * 更新我的个人信息
     * 首先从session中获取当前的Admin对象的ID
     *
     * @param admin
     * @return
     */
    ServerResponse<Admin> updateMyAccount(Admin admin);

    /**
     * 修改评论
     *
     * @param id
     * @param detail
     * @return
     */
    ServerResponse updateComment(Integer id, String detail);

    /**
     * 删除一个评论根据id
     *
     * @param id
     * @return
     */
    ServerResponse deleteComment(Integer id);

    /**
     * 更新资源的信息
     *
     * @param resources
     * @return
     */
    ServerResponse<Resources> updateResource(Resources resources);

    /**
     * 删除一个资源通过ID进行删除
     *
     * @param id
     * @return
     */
    ServerResponse deleteResource(Integer id);

    /**
     * 查询出所有没有子标签的标签
     *
     * @return
     */
    ServerResponse<List<Labels>> findLabelsByFinalChildForAll();

    /**
     * 发布一个资源
     *
     * @param resources
     * @return
     */
    ServerResponse<Resources> insertResource(Resources resources);

    /**
     * 递归算出 标签的所有层级关系数据结构
     * @return
     */
    ServerResponse<Set<LabelsManageVo>> getTreeViewDatas();

    /**
     * 获取出所有的配置信息
     * @return
     */
    ServerResponse<List<ViewConfiguration>> findSettingsForAll();

    /**
     * 删除一个配置类
     * @param id
     * @return
     */
    ServerResponse deleteSettingConfig(Integer id);

    /**
     * 获取当前激活的主题信息
     * @return
     */
    ServerResponse<ViewConfiguration> findSettingByActive();


    /**
     * 更新设置 通过id来更新它
     * @param id
     * @param configuration
     * @return
     */
    ServerResponse updateSettingsById(Integer id, ViewConfiguration configuration);

    /**
     * 通过id查询一个配置文件
     * @param id
     * @return
     */
    ServerResponse<ViewConfiguration> findSettingById(Integer id);

    /**
     * 更新一个Setting 为激活状态
     * @param id
     * @return
     */
    ServerResponse updateSettingToActive(Integer id);

    /**
     * 插入一个新的模板
     * @param config
     * @return
     */
    ServerResponse addSettingConfig(ViewConfiguration config);

    /**
     * 取消所有的激活状态
     * @return
     */
    ServerResponse cancelSettingActive();

    /**
     * 根据一定的条件查询出所有的数据
     * @param search
     * @param sort
     * @param page
     * @return
     */
    ServerResponse<PageInfo<CommentVo>> findCommentAll(String search, String sort, Integer page);

    /**
     * 获取最新未读的评论信息
     * @param page
     * @return
     */
    ServerResponse<PageInfo<CommentVo>> findCommentInfo(Integer page);

    /**
     * 通过ID 删除一个未读消息
     * @param id
     * @return
     */
    ServerResponse deleteCommentInfoById(Integer id);

    /**
     * 清空info 表所有的数据
     * @return
     */
    ServerResponse deleteCommentInfoAll();

    /**
     * 仪表板所需的主页数据
     * @param page
     * @return
     */
    MainVo getMainVoAndInitData(Integer page);
}
