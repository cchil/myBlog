package com.cchilei.blog.service.impl;

import com.cchilei.blog.common.Const;
import com.cchilei.blog.common.ServerResponse;
import com.cchilei.blog.dao.*;
import com.cchilei.blog.pojo.*;
import com.cchilei.blog.dao.*;
import com.cchilei.blog.pojo.*;
import com.cchilei.blog.service.FrontService;
import com.cchilei.blog.util.ParseTextUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.Email;
import java.io.File;
import java.util.*;

/**
 * @Author
 * @Create 2018-05-09 20:35
 */
@Transactional(rollbackFor = Exception.class)
@Service
public class FrontServiceImpl implements FrontService {

    /**
     * 最多显示多少条评论
     */
    private static final Integer COMMENT_MAX_SIZE_PAGE = 10;

    /**
     * 每页最多显示多少条文章
     */
    private static final Integer CONTENT_MAX_SIZE_PAGE = 6;

    private Logger logger = LoggerFactory.getLogger(getClass());

    private static CacheManager cacheManager = CacheManager.newInstance();

    /**
     * 记录第一次的时间
     */
    private static Long viewArticleTime = System.currentTimeMillis();

    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private LabelsMapper labelsMapper;
    @Autowired
    private ContentMapper contentMapper;
    @Autowired
    private ThemeMapper themeMapper;
    @Autowired
    private ResourcesMapper resourcesMapper;
    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private CommentInfoMapper commentInfoMapper;

    @Override
    public ServerResponse createComment(@Email String email, String name, String detail, Integer infoId) {
        if (StringUtils.isNotBlank(email) && StringUtils.isNotBlank(name)
                && StringUtils.isNotBlank(detail) && infoId != null) {
            Comment comment = new Comment();
            comment.setDetail(detail);
            comment.setContentId(infoId);
            comment.setCreateTime(new Date());
            comment.setEmail(email);
            comment.setName(name);
            int row = commentMapper.insertSelective(comment);
            if (row <= 0) {
                return ServerResponse.createErrorMessage("评论失败");
            }
            //进行插入未读消息的数据库中去
            CommentInfo info = new CommentInfo();
            if (comment.getId() == null) {
                throw new NullPointerException("Comment：获取ID为空");
            }
            info.setCommentId(comment.getId());
            commentInfoMapper.insertSelective(info);
            return ServerResponse.createBySuccess("评论成功", comment);
        }
        return ServerResponse.createErrorMessage("参数错误");
    }


    @Override
    public ServerResponse<PageInfo> findComment(Integer contentId, int num) {
        if (contentId != null) {
            PageHelper.startPage(num, COMMENT_MAX_SIZE_PAGE);
            List<Comment> list = commentMapper.selectCommentByContentId(contentId);
            PageInfo pageInfo = new PageInfo(list);
            return ServerResponse.createBySuccess(pageInfo);
        }
        return ServerResponse.createErrorMessage("参数错误");
    }

    @Override
    public ServerResponse<List<Labels>> findLabelsAll() {
        return ServerResponse.createBySuccess(labelsMapper.selectAll());
    }

    @Override
    public ServerResponse<Labels> findLabelsById(Integer labelId) {
        if (labelId != null) {
            Labels labels = labelsMapper.selectByPrimaryKey(labelId);
            if (labelId == null) {
                return ServerResponse.createErrorMessage("没有这个标签");
            }
            return ServerResponse.createBySuccess(labels);
        }
        return ServerResponse.createErrorMessage("参数错误");
    }


    @Override
    public ServerResponse<List<Labels>> findLabelsByParentId(Integer parentId) {
        if (parentId != null) {
            return ServerResponse.createBySuccess(labelsMapper.selectLabelsByParentId(parentId));
        }
        return ServerResponse.createErrorMessage("参数错误");
    }

    @Override
    public ServerResponse<List<Labels>> findLabelsByRootForAll() {
        return ServerResponse.createBySuccess(labelsMapper.selectLabelsByParentId(0));
    }

    @Override
    public ServerResponse<Content> findContentById(Integer id) {
        if (id != null) {
            Content content = contentMapper.selectByPrimaryKey(id);
            if (content == null) {
                return ServerResponse.createErrorMessage("找不到这篇文章了!");
            }
            return ServerResponse.createBySuccess(content);
        }
        return ServerResponse.createErrorMessage("参数错误");
    }

    @Override
    public ServerResponse<PageInfo> findContentByLabelsId(Integer labelsId, int num) {
        if (labelsId != null) {
            PageHelper.startPage(num, CONTENT_MAX_SIZE_PAGE);
            List<Content> contents = contentMapper.selectContentByLabelsId(labelsId);
            for (Content content : contents) {
                content.setDetail(ParseTextUtil.parseHtmlForText(content.getDetail(), 150));
            }
            return ServerResponse.createBySuccess(new PageInfo(contents));
        }
        return ServerResponse.createErrorMessage("参数错误");
    }

    @Override
    public ServerResponse<PageInfo<Content>> findContentAll(int num) {
        PageHelper.startPage(num, CONTENT_MAX_SIZE_PAGE);
        List<Content> list = contentMapper.selectContentForAll();
        for (Content content : list) {
            content.setDetail(ParseTextUtil.parseHtmlForText(content.getDetail(), 220));
        }
        PageInfo<Content> pageInfo = new PageInfo<>(list);
        return ServerResponse.createBySuccess(pageInfo);
    }

    @Override
    public ServerResponse<PageInfo<Content>> searchContentByDetail(String msg, int num) {
        if (msg != null) {
            PageHelper.startPage(num, CONTENT_MAX_SIZE_PAGE);
            msg = new StringBuilder().append("%").append(msg).append("%").toString();
            List<Content> contents = contentMapper.searchContentByDetail(msg);
            Set<Content> set = new HashSet<>();
            set.addAll(contents);
            for (Content content : set) {
                content.setDetail(ParseTextUtil.parseHtmlForText(content.getDetail(), 150));
            }
            return ServerResponse.createBySuccess(new PageInfo<Content>(new ArrayList<>(set)));
        }
        return ServerResponse.createErrorMessage("参数错误");
    }

    @Override
    public void plusContentReadCount(Integer contentId) {
        if (contentId != null) {
            contentMapper.updateForReadCount(contentId);
        }
    }


    @Override
    public ServerResponse<PageInfo<Resources>> findResourcesAll(int num) {
        PageHelper.startPage(num, COMMENT_MAX_SIZE_PAGE);
        List<Resources> list = resourcesMapper.selectResourceAll();
        return ServerResponse.createBySuccess(new PageInfo(list));
    }

    @Override
    public ServerResponse<Theme> findThemeConfig() {
        return ServerResponse.createBySuccess(themeMapper.select());
    }

    @Override
    public ServerResponse<Admin> findAdminMessage() {
        Admin admin = adminMapper.selectByPrimaryKey("admin");
        Admin ad = new Admin();
        ad.setHead(Const.FILE_ROOT_URL + Const.FileCommon.ADMIN_DIR + File.separator + admin.getHead());
        ad.setSign(admin.getSign());
        ad.setName(admin.getName());
        return ServerResponse.createBySuccess(ad);
    }

    @Override
    public ServerResponse<Integer> readContentAutoPlus(Integer contentId) {
        if (contentId == null) {
            return ServerResponse.createError();
        }
        int readCount = contentMapper.selectReadCountById(contentId);
        Ehcache ehcache = cacheManager.getEhcache("dayHits");
        Element element = ehcache.get(contentId + "_Content");
        int newCount = 0;
        if (element == null) {
            newCount = readCount;
        } else {
            newCount = (int) element.getObjectValue();
        }
        newCount++;
        ehcache.put(new Element(contentId + "_Content", newCount));
        Long time = System.currentTimeMillis();
        /**
         * 最大的过期时间
         */
        long maxTime = 300000;
        if (time > (viewArticleTime + maxTime)) {
            viewArticleTime = time;
            contentMapper.updateReadCountByID(newCount, contentId);
            ehcache.removeAll();
        }
        return ServerResponse.createBySuccess(newCount);
    }
}
