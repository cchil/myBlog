package com.cchilei.blog.service.impl;

import com.cchilei.blog.dao.*;
import com.cchilei.blog.pojo.*;
import com.cchilei.blog.service.AdminService;
import com.cchilei.blog.common.Const;
import com.cchilei.blog.common.ServerResponse;
import com.cchilei.blog.dao.*;
import com.cchilei.blog.pojo.*;
import com.cchilei.blog.pojo.viewconfig.ViewConfiguration;
import com.cchilei.blog.util.MyAtomicInteger;
import com.cchilei.blog.util.ParseTextUtil;
import com.cchilei.blog.vo.CommentVo;
import com.cchilei.blog.vo.ContentVo;
import com.cchilei.blog.vo.LabelsManageVo;
import com.cchilei.blog.vo.MainVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.*;

/**
 * @Author
 * @Create 2018-05-08 19:37
 */
@Service
public class AdminServiceImpl implements AdminService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public static final Integer MAX_SIZE = 10;

    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private ContentMapper contentMapper;
    @Autowired
    private LabelsMapper labelsMapper;
    @Autowired
    private ThemeMapper themeMapper;
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private ResourcesMapper resourcesMapper;
    @Autowired
    private SettingMapper settingMapper;
    @Autowired
    private CommentInfoMapper commentInfoMapper;

    /**
     * 登录操作
     *
     * @param name
     * @param password
     * @return
     */
    @Override
    public ServerResponse login(String name, String password) {
        if (StringUtils.isNotBlank(name) && StringUtils.isNotBlank(password)) {
            UsernamePasswordToken token = new UsernamePasswordToken(name, password);
            Subject subject = SecurityUtils.getSubject();
            try {
                subject.login(token);
                Admin admin = (Admin) subject.getPrincipal();
                //设置头像路径
                admin.setHead(Const.FILE_ROOT_URL + Const.FileCommon.ADMIN_DIR + File.separator + admin.getHead());
                subject.getSession().setAttribute(Const.CURRENT_ADMIN, admin);
                return ServerResponse.createBySuccessMessage("登录成功");
            } catch (AuthenticationException e) {
                return ServerResponse.createErrorMessage("用户名和密码不匹配");
            }
        }
        return ServerResponse.createErrorMessage("参数错误");
    }

    @Override
    public ServerResponse<Content> publishInfo(String detail, String title, Integer labelId, Integer contentType) {
        if (StringUtils.isNotBlank(detail) && StringUtils.isNotBlank(title) && labelId != null) {
            Content content = new Content();
            content.setLabelId(labelId);
            content.setCreateTime(new Date());
            content.setReadCount(0);
            content.setTitle(title);
            content.setDetail(detail);
            content.setContentType(contentType);
            int rowCount = contentMapper.insertSelective(content);
            if (rowCount <= 0) {
                return ServerResponse.createErrorMessage("插入失败");
            }
            return ServerResponse.createBySuccess("插入成功", content);
        }
        return ServerResponse.createErrorMessage("参数错误");
    }

    /**
     * 添加标签
     *
     * @param parentId
     * @param name
     * @return
     */
    @Override
    public ServerResponse addLabel(Integer parentId, String name) {
        if (parentId != null && StringUtils.isNotBlank(name)) {
            Labels labels = new Labels();
            labels.setName(name);
            labels.setParentId(parentId);

            int rowCount = labelsMapper.insertSelective(labels);
            if (rowCount <= 0) {
                return ServerResponse.createErrorMessage("插入失败");
            }
            return ServerResponse.createBySuccess();
        }
        return ServerResponse.createErrorMessage("参数错误");
    }

    @Override
    public ServerResponse updateLabel(Labels labels) {
        if (labels.getId() != null) {
            int rowCount = labelsMapper.updateByPrimaryKeySelective(labels);
            if (rowCount <= 0) {
                return ServerResponse.createErrorMessage("更新失败");
            }
            return ServerResponse.createBySuccess();
        }
        return ServerResponse.createErrorMessage("参数错误");
    }

    @Override
    public ServerResponse deleteLabels(Integer id) {
        if (id != null && id != 1) {
            int row = labelsMapper.deleteLablesAndChildByKey(id);
            if (row <= 0) {
                return ServerResponse.createErrorMessage("删除失败");
            }
            return ServerResponse.createBySuccess();
        }
        return ServerResponse.createErrorMessage("参数错误");
    }

    @Override
    public ServerResponse<Content> updateInfo(Content content) {
        if (content != null && StringUtils.isNotBlank(content.getDetail())
                && StringUtils.isNotBlank(content.getTitle()) && content.getId() != null) {
            Content update = new Content();
            if (content.getLabelId() != null) {
                update.setLabelId(content.getLabelId());
            }
            update.setDetail(content.getDetail());
            update.setId(content.getId());
            update.setTitle(content.getTitle());

            int row = contentMapper.updateByPrimaryKeySelective(update);
            if (row <= 0) {
                return ServerResponse.createErrorMessage("更新失败");
            }
            return ServerResponse.createBySuccess("更新成功", update);
        }
        return ServerResponse.createErrorMessage("参数错误");
    }

    @Override
    public ServerResponse deleteInfo(Integer id) {
        if (id != null) {
            int row = contentMapper.deleteByPrimaryKey(id);
            if (row <= 0) {
                return ServerResponse.createErrorMessage("删除失败");
            }
            return ServerResponse.createBySuccess("成功");
        }
        return ServerResponse.createErrorMessage("参数错误");
    }

    @Override
    public ServerResponse updateTheme(String bg, String css, String js) {
        Theme theme = new Theme();
        if (StringUtils.isNotBlank(bg)) {
            theme.setMainBg(bg);
        }
        if (StringUtils.isNotBlank(css)) {
            theme.setThemeCss(css);
        }
        if (StringUtils.isNotBlank(js)) {
            theme.setThemeJs(js);
        }
        int row = themeMapper.update(theme);
        if (row <= 0) {
            return ServerResponse.createErrorMessage("更新失败");
        }
        return ServerResponse.createBySuccess();
    }

    @Override
    public ServerResponse<Admin> updateMyAccount(Admin admin) {
        if (admin != null) {
            logger.info("--更新我的个人信息--：" + admin.toString());
            int row = adminMapper.updateByPrimaryKeySelective(admin);
            if (row <= 0) {
                return ServerResponse.createErrorMessage("更新失败");
            }
        }
        Admin newAdmin = adminMapper.selectByPrimaryKey(admin.getAccountId()).clonAdmin();
        newAdmin.setHead(Const.FILE_ROOT_URL + Const.FileCommon.ADMIN_DIR + File.separator + newAdmin.getHead());
        SecurityUtils.getSubject().getSession().setAttribute(Const.CURRENT_ADMIN, newAdmin);
        return ServerResponse.createBySuccess();
    }

    @Override
    public ServerResponse updateComment(Integer id, String detail) {
        if (id != null && StringUtils.isNotBlank(detail)) {
            Comment comment = new Comment();
            comment.setId(id);
            comment.setDetail(detail);
            int row = commentMapper.updateByPrimaryKeySelective(comment);
            if (row <= 0) {
                return ServerResponse.createErrorMessage("更新失败");
            }
            return ServerResponse.createBySuccess();
        }
        return ServerResponse.createErrorMessage("参数错误");
    }

    @Override
    public ServerResponse deleteComment(Integer id) {
        if (id != null) {
            int row = commentMapper.deleteByPrimaryKey(id);
            if (row <= 0) {
                return ServerResponse.createErrorMessage("删除失败");
            }
            return ServerResponse.createBySuccess("删除成功");
        }
        return ServerResponse.createErrorMessage("参数错误");
    }

    @Override
    public ServerResponse<Resources> updateResource(Resources resources) {
        if (resources != null) {
            if (resources.getId() != null) {
                if (StringUtils.isNotBlank(resources.getTitle())) {
                    resourcesMapper.updateByPrimaryKeySelective(resources);
                    return ServerResponse.createBySuccess(resources);
                }
            }
        }
        return ServerResponse.createErrorMessage("参数错误");
    }

    @Override
    public ServerResponse deleteResource(Integer id) {
        if (id != null) {
            int rowCount = resourcesMapper.deleteByPrimaryKey(id);
            if (rowCount <= 0) {
                return ServerResponse.createErrorMessage("删除失败");
            }
            return ServerResponse.createBySuccess("删除OK");
        }
        return ServerResponse.createErrorMessage("参数错误");
    }

    @Override
    public ServerResponse<List<Labels>> findLabelsByFinalChildForAll() {
        return ServerResponse.createBySuccess(labelsMapper.selectLabelsByFinalChild());
    }


    @Override
    public ServerResponse<Resources> insertResource(Resources resources) {
        if (StringUtils.isBlank(resources.getTitle()) && StringUtils.isBlank(resources.getUrl())) {
            return ServerResponse.createErrorMessage("参数错误");
        }
        int rowCount = resourcesMapper.insertSelective(resources);
        if (rowCount <= 0) {
            return ServerResponse.createErrorMessage("插入失败");
        }
        return ServerResponse.createBySuccess("发布成功", resources);
    }

    @Override
    public ServerResponse<Set<LabelsManageVo>> getTreeViewDatas() {
        Set<LabelsManageVo> data = new HashSet<>();
        //循环递归算出来 首先是先给个引导数据 查询出根标签
        Labels labels = new Labels();
        labels.setId(0);
        labels.setParentId(0);
        labels.setName("根节点");
        LabelsManageVo rootVo = new LabelsManageVo();

        looperChild(data, labels, rootVo);
        return ServerResponse.createBySuccess(data);
    }

    /**
     * 循环层级递归求出 树形结构结果
     *
     * @param child
     * @param root
     * @param rootVo
     */
    public void looperChild(Set<LabelsManageVo> child, Labels root, LabelsManageVo rootVo) {
        List<Labels> data = labelsMapper.selectLabelsByParentId(root.getId());
        rootVo.setTags(Arrays.asList(data.size() + ""));
        for (Labels labels : data) {
            LabelsManageVo vo = new LabelsManageVo();
            vo.setText(labels.getName());
            vo.setId(labels.getId() + "");
            vo.setParentId(root.getId() + "");
            vo.setParentName(root.getName());
            Set<LabelsManageVo> lChild = new HashSet<>();
            looperChild(lChild, labels, vo);
            child.add(vo);
            rootVo.setNodes(child);
        }
    }

    /////////////////////////////////
    /// Setting配置信息           ///
    ////////////////////////////////


    @Override
    public ServerResponse<List<ViewConfiguration>> findSettingsForAll() {
        List<Setting> list = settingMapper.selectForAll();
        List<ViewConfiguration> data = new ArrayList<>();
        Gson gson = new Gson();
        for (Setting setting : list) {
            try {
                ViewConfiguration obj = gson.fromJson(setting.getConfig(), ViewConfiguration.class);
                Setting objSet = new Setting();
                objSet.setId(setting.getId());
                objSet.setStatus(setting.getStatus());
                obj.setSetting(objSet);
                data.add(obj);
            } catch (JsonSyntaxException e) {
                logger.error("Gson转换读取Setting表信息出异常:{}" + e.getMessage());
                return ServerResponse.createErrorMessage("读取Setting出现异常");
            }
        }
        return ServerResponse.createBySuccess(data);
    }

    @Override
    public ServerResponse deleteSettingConfig(Integer id) {
        if (id == null) {
            return ServerResponse.createErrorMessage("参数错误");
        }
        int rowCount = settingMapper.deleteByPrimaryKey(id);
        if (rowCount <= 0) {
            return ServerResponse.createErrorMessage("删除失败");
        }
        return ServerResponse.createBySuccessMessage("删除成功");
    }

    @Override
    public ServerResponse<ViewConfiguration> findSettingByActive() {
        Setting setting = settingMapper.selectSettingByActive();
        if (setting != null) {
            if (StringUtils.isNotBlank(setting.getConfig())) {
                Gson gson = new Gson();
                ViewConfiguration obj = null;
                try {
                    obj = gson.fromJson(setting.getConfig(), ViewConfiguration.class);
                    Setting objSet = new Setting();
                    objSet.setId(setting.getId());
                    objSet.setStatus(setting.getStatus());
                    obj.setSetting(objSet);
                } catch (JsonSyntaxException e) {
                    logger.error("Gson转换读取Setting表信息出异常:" + e.getMessage());
                    return ServerResponse.createErrorMessage("读取Setting出现异常");
                }
                return ServerResponse.createBySuccess(obj);
            }
        }
        return ServerResponse.createErrorMessage("参数错误");
    }

    @Override
    public ServerResponse updateSettingsById(Integer id, ViewConfiguration configuration) {
        if (id != null && configuration != null) {
            Setting setting = new Setting();
            setting.setId(id);
            setting.setConfig(new Gson().toJson(configuration));
            int row = settingMapper.updateByPrimaryKeySelective(setting);
            if (row <= 0) {
                return ServerResponse.createErrorMessage("更新失败");
            }
            return ServerResponse.createBySuccess();
        }
        return ServerResponse.createErrorMessage("参数错误");
    }

    @Override
    public ServerResponse<ViewConfiguration> findSettingById(Integer id) {
        if (id == null) {
            return ServerResponse.createErrorMessage("参数错误");
        }
        Setting setting = settingMapper.selectByPrimaryKey(id);
        if (setting == null || StringUtils.isBlank(setting.getConfig())) {
            return ServerResponse.createErrorMessage("获取失败");
        }
        try {
            ViewConfiguration obj = new Gson().fromJson(setting.getConfig(), ViewConfiguration.class);
            Setting objSet = new Setting();
            objSet.setId(setting.getId());
            objSet.setStatus(setting.getStatus());
            obj.setSetting(objSet);
            return ServerResponse.createBySuccess(obj);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            logger.error("获取Setting转化为对象异常", e);
            return ServerResponse.createErrorMessage("获取内容转化失败");
        }
    }

    @Override
    public ServerResponse updateSettingToActive(Integer id) {
        if (id == null) {
            return ServerResponse.createErrorMessage("参数错误");
        }
        settingMapper.cancelActiveAll();
        int row = settingMapper.setSettingToActive(id);
        if (row <= 0) {
            return ServerResponse.createErrorMessage("更新失败");
        }
        return ServerResponse.createBySuccess();
    }

    @Override
    public ServerResponse addSettingConfig(ViewConfiguration config) {
        if (config == null) {
            ServerResponse.createErrorMessage("参数错误");
        }
        Setting setting = new Setting();
        setting.setStatus(false);
        setting.setConfig(new Gson().toJson(config));
        int row = settingMapper.insertSelective(setting);
        if (row <= 0) {
            return ServerResponse.createErrorMessage("插入失败");
        }
        return ServerResponse.createBySuccess();
    }

    @Override
    public ServerResponse cancelSettingActive() {
        int row = settingMapper.cancelActiveAll();
        if (row <= 0) {
            return ServerResponse.createErrorMessage("取消失败");
        }
        return ServerResponse.createBySuccess();
    }

    ///////////////////////////////////////////////////////////////////////////
    // 评论管理
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public ServerResponse<PageInfo<CommentVo>> findCommentAll(String search, String sort, Integer page) {
        if (sort == null || page == null) {
            return ServerResponse.createErrorMessage("参数错误");
        }
        PageHelper.startPage(page, MAX_SIZE);
        if (StringUtils.isNotBlank(search)) {
            search = new StringBuilder().append("%").append(search).append("%").toString();
        }
        List<CommentVo> comments = commentMapper.selectCommentAll(sort, search);
        return ServerResponse.createBySuccess(new PageInfo<>(comments));
    }

    ///////////////////////////////////////////////////////////////////////////
    // 仪表板数据的获取
    ///////////////////////////////////////////////////////////////////////////

    /**
     * 这里查询出所有的未读消息 注意的是这里CommentVo类中字段commentId存储的是info表的ID
     *
     * @param page 页码
     * @return 结果
     */
    @Override
    public ServerResponse<PageInfo<CommentVo>> findCommentInfo(Integer page) {
        if (page == null) {
            return ServerResponse.createErrorMessage("参数错误");
        }
        PageHelper.startPage(page, MAX_SIZE);
        List<CommentVo> result = commentInfoMapper.selectUnHandlerInfos();
        for (CommentVo vo : result) {
            vo.setDetail(ParseTextUtil.stringCut(vo.getDetail(), 10));
            vo.setContentTitle(ParseTextUtil.stringCut(vo.getContentTitle(), 10));
        }
        return ServerResponse.createBySuccess(new PageInfo<>(result));
    }

    @Override
    public ServerResponse deleteCommentInfoById(Integer id) {
        int row = commentInfoMapper.deleteByPrimaryKey(id);
        if (row <= 0) {
            return ServerResponse.createErrorMessage("删除失败");
        }
        return ServerResponse.createBySuccess();
    }

    @Override
    public ServerResponse deleteCommentInfoAll() {
        commentInfoMapper.deleteAll();
        return ServerResponse.createBySuccess();
    }

    @Override
    public MainVo getMainVoAndInitData(Integer page) {
        if (page == null) {
            return null;
        }
        MainVo vo = new MainVo();
        vo.setCommentInfo(findCommentInfo(page).getData());
        List<Content> contents = contentMapper.selectContentForAll();
        List<ContentVo> copy = new ArrayList<>();
        int size = contents.size() < 5 ? contents.size() : 5;
        for (int i = 0; i < size; i++) {
            ContentVo cv = new ContentVo();
            cv.setId(contents.get(i).getId() + "");
            cv.setTitle(ParseTextUtil.stringCut(contents.get(i).getTitle(), 10));
            cv.setTime(contents.get(i).getCreateTime());
            cv.setLabelsName(labelsMapper.selectByPrimaryKey(contents.get(i).getLabelId()).getName());
            cv.setDetail(ParseTextUtil.parseHtmlForText(contents.get(i).getDetail(), 10));
            copy.add(cv);
        }

        vo.setContentList(copy);
        vo.setContentCount(contentMapper.selectCount());
        vo.setInfoCount(commentInfoMapper.selectCount());
        vo.setResourcesCount(resourcesMapper.selectCount());
        int val = MyAtomicInteger.getInstance().intValue();
        vo.setPeopleCount(val > 0 ? val - 1 : 0);
        return vo;
    }

}
