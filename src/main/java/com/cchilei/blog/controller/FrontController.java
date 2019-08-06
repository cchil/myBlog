package com.cchilei.blog.controller;

import com.cchilei.blog.common.Const;
import com.cchilei.blog.common.ServerResponse;
import com.cchilei.blog.pojo.Content;
import com.cchilei.blog.pojo.Labels;
import com.cchilei.blog.pojo.Resources;
import com.cchilei.blog.pojo.viewconfig.ViewAboutStation;
import com.cchilei.blog.pojo.viewconfig.ViewConfiguration;
import com.cchilei.blog.service.FrontService;
import com.cchilei.blog.util.ParseTextUtil;
import com.cchilei.blog.util.RandomValidateCodeUtil;
import com.cchilei.blog.vo.LabelsVo;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.Email;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * @Author
 * @Create 2018-05-09 21:43
 * 前端展示层
 */
@Controller
@RequestMapping("/main")
public class FrontController {

    private static final String ERROR_HTML = "error";
    private static final String INDEX_HTML = "index";

    /**
     * 记录着Session中存储着的阅读统计集合 的Key名
     */
    public static final String KEY_READ_RECORD = "contentReadList";

    /**
     * 默认的返回结果的Key名
     */
    public static final String DEFAULT_RESULT = "defaultData";

    /**
     * 默认的返回结果代表成功后  KEY名
     */
    public static final String DATA = "data";

    /**
     * 使用session域 取出我的个人数据所用Key名
     */
    public static final String ADMIN_MESSAGE = "myAccount";

    @Autowired
    private FrontService frontService;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping(value = {"/index", "/index/{page}"})
    public String index(Model model, @PathVariable(value = "page", required = false) Integer pageNum, HttpSession session) {
        if (pageNum == null) {
            pageNum = 1;
        }
        ServerResponse<PageInfo<Content>> contentAll = frontService.findContentAll(pageNum);
        ViewConfiguration config = (ViewConfiguration) session.getAttribute(Const.KEY_SETTINGS);
        if (config.getContentTopId() != null) {
            ServerResponse<Content> response = frontService.findContentById(config.getContentTopId());
            LinkedList<Content> linkedList = new LinkedList<>(contentAll.getData().getList());
            if (response.isSuccess()) {
                linkedList.remove(response.getData());
                response.getData().setDetail(ParseTextUtil.parseHtmlForText(response.getData().getDetail(),150));
                linkedList.addFirst(response.getData());
                contentAll.getData().setList(linkedList);
            }
        }
        model.addAttribute(DATA, contentAll);
        return INDEX_HTML;
    }

    @RequestMapping("/detail/{index}")
    public String getDetail(@PathVariable(value = "index", required = false) Integer index, Model model, HttpSession session) {
        if (index == null) {
            model.addAttribute(DEFAULT_RESULT, "你是谁,我不记得了 我喝了瓶忘崽牛奶");
            return ERROR_HTML;
        }
        ServerResponse<Content> content = frontService.findContentById(index);
        if (!content.isSuccess()) {
            model.addAttribute(DEFAULT_RESULT, "这篇文章可能去了火星!");
            return ERROR_HTML;
        }

        /**
         * 这里进行阅读量的统计 判断的逻辑
         */
        HashSet<Integer> set = (HashSet<Integer>) session.getAttribute(FrontController.KEY_READ_RECORD);
        if (!set.contains(index)) {
            ServerResponse<Integer> autoPlus = frontService.readContentAutoPlus(index);
            if (autoPlus.isSuccess()) {
                content.getData().setReadCount(autoPlus.getData());
                set.add(index);
            }
        }

        model.addAttribute(DATA, content);
        return "front/detail";
    }


    @RequestMapping("/labels")
    public String getLabels(Model model) {
        //todo 需要缓存结果 不能总是递归
        List<Labels> secondRoot = frontService.findLabelsByParentId(0).getData();

        Set<LabelsVo> child = new HashSet<>();

        for (Labels labels : secondRoot) {
            LabelsVo looper = new LabelsVo();
            looper.setRoot(labels);
            Set<Labels> voList = new HashSet<>();
            looperFindChild(labels, voList);
            looper.setChild(voList);
            child.add(looper);
        }
        model.addAttribute(DATA, child);

        return "/front/label";
    }

    /**
     * 模糊查询用的
     *
     * @param model
     * @param msg
     * @param num
     * @return
     */
    @RequestMapping("/search")
    public String search(Model model, String msg, @RequestParam(value = "page", required = false, defaultValue = "1") Integer num) {
        ServerResponse<PageInfo<Content>> content = frontService.searchContentByDetail(msg, num);
        if (!content.isSuccess()) {
            model.addAttribute(DEFAULT_RESULT, content.getMsg());
            return ERROR_HTML;
        }
        model.addAttribute("localUrl", "/main/search?msg=" + msg + "&page=");
        model.addAttribute(DATA, content);
        return "/front/search";
    }


    @RequestMapping(value = {"/search-la/{id}/{page}", "/search-la/{id}"})
    public String search(Model model, @PathVariable(value = "id", required = false) Integer labelId, @PathVariable(value = "page", required = false) Integer num) {
        if (num == null) {
            num = 1;
        }
        ServerResponse<PageInfo> content = frontService.findContentByLabelsId(labelId, num);
        if (!content.isSuccess()) {
            model.addAttribute(DEFAULT_RESULT, content.getMsg());
            return ERROR_HTML;
        }
        model.addAttribute("localUrl", "/main/search-la/" + labelId + "/");
        model.addAttribute(DATA, content);
        return "/front/search";
    }

    @PostMapping("/get-comment")
    @ResponseBody
    public ServerResponse<PageInfo> getCommentByContentId(Integer id, @RequestParam(value = "page", defaultValue = "1") Integer num) {
        if (id == null) {
            return ServerResponse.createErrorMessage("ERROR:参数错误");
        }
        ServerResponse<PageInfo> comment = frontService.findComment(id, num);
        return comment;
    }

    @PostMapping("/put-comment")
    @ResponseBody
    public ServerResponse createComment(@Email String email, String name, String detail, Integer contentid, String code, HttpSession session) {
        if (StringUtils.isBlank(code)) {
            return ServerResponse.createErrorMessage("验证码不能为空哦");
        }
        String val = (String) session.getAttribute(RandomValidateCodeUtil.RANDOMCODEKEY);
        if (!val.equalsIgnoreCase(code)) {
            return ServerResponse.createErrorMessage("验证码输入不正确哦");
        }
        ServerResponse comment = frontService.createComment(email, name, detail, contentid);
        if (!comment.isSuccess()) {
            return ServerResponse.createErrorMessage("发表失败咯");
        }
        return comment;
    }

    /**
     * 生成验证码
     */
    @RequestMapping(value = "/getVerify")
    public void getVerify(HttpServletRequest request, HttpServletResponse response) {
        try {
            //设置相应类型,告诉浏览器输出的内容为图片
            response.setContentType("image/jpeg");
            //设置响应头信息，告诉浏览器不要缓存此内容
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expire", 0);
            RandomValidateCodeUtil randomValidateCode = new RandomValidateCodeUtil();
            //输出验证码图片方法
            randomValidateCode.getRandcode(request, response);
        } catch (Exception e) {
            logger.error("输出验证码失败:{}", e.getMessage());
        }
    }

    @RequestMapping("/resources")
    public String finResources(@RequestParam(value = "page", required = false, defaultValue = "1") Integer num, Model model) {
        ServerResponse<PageInfo<Resources>> resourcesAll = frontService.findResourcesAll(num);
        model.addAttribute(DATA, resourcesAll);
        return "/front/resource.html";
    }

    @RequestMapping("/adboutme")
    public String getAboutMe(Model model, HttpSession session) {
        ViewConfiguration config = (ViewConfiguration) session.getAttribute(Const.KEY_SETTINGS);
        ViewAboutStation data = config.getViewAboutStation();
        model.addAttribute(DATA, data);
        return "front/aboutme";
    }

    /**
     * 递归算法,算出路径下所有的子分类
     *
     * @param root
     * @param list
     */
    private void looperFindChild(Labels root, Set<Labels> list) {
        List<Labels> data = frontService.findLabelsByParentId(root.getId()).getData();
        for (Labels labels : data) {
            looperFindChild(labels, list);
            list.add(labels);
        }
    }

}
