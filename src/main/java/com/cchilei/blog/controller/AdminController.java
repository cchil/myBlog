package com.cchilei.blog.controller;

import com.baidu.ueditor.ActionEnter;
import com.cchilei.blog.common.Const;
import com.cchilei.blog.common.ServerResponse;
import com.cchilei.blog.pojo.*;
import com.cchilei.blog.pojo.*;
import com.cchilei.blog.pojo.viewconfig.ViewConfiguration;
import com.cchilei.blog.service.AdminService;
import com.cchilei.blog.service.FileUploadService;
import com.cchilei.blog.service.FrontService;
import com.cchilei.blog.util.MD5Util;
import com.cchilei.blog.util.MyActionEnter;
import com.cchilei.blog.util.ParseTextUtil;
import com.cchilei.blog.vo.CommentVo;
import com.cchilei.blog.vo.ContentManageVo;
import com.cchilei.blog.vo.ContentViewVO;
import com.cchilei.blog.vo.LabelsManageVo;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 * @Author
 * @Create 2018-05-12 22:31
 */
@RequestMapping("/admin")
@Controller
public class AdminController {

    /**
     * 默认的返回结果的Key值
     */
    public static final String DEFAULT_RESULT = "defaultData";

    public static final String DATA = "data";

    @Autowired
    private AdminService adminService;
    @Autowired
    private FrontService frontService;
    @Autowired
    private FileUploadService fileUploadService;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping("/index")
    public String index() {
        return "admin/admin";
    }

    @RequestMapping("/login")
    public String login() {
        return "admin/login";
    }

    @RequestMapping("/logout")
    public void logout() {
        SecurityUtils.getSubject().logout();
    }

    @PostMapping("/login-from")
    public String loginFrom(String name, String pwd, RedirectAttributes attributes) {
        ServerResponse res = adminService.login(name, pwd);
        if (res.isSuccess()) {
            return "redirect:/admin/index";
        }
        attributes.addFlashAttribute("login_msg", res.getMsg());
        return "redirect:/admin/login";
    }

    ///////////////////////////////////////////////////////////////////////////
    // 管理页面的获取 和逻辑处理
    ///////////////////////////////////////////////////////////////////////////

    @RequestMapping("/resources")
    public String finResources(@RequestParam(value = "page", required = false, defaultValue = "1") Integer num, Model model) {
        ServerResponse<PageInfo<Resources>> resourcesAll = frontService.findResourcesAll(num);
        model.addAttribute(DATA, resourcesAll);
        return "admin/resource-tem";
    }

    @PutMapping("/update-resource")
    @ResponseBody
    public ServerResponse<Resources> updateResource(Resources resources) {
        return adminService.updateResource(resources);
    }

    @RequestMapping(value = "/delete-resource", method = RequestMethod.DELETE)
    @ResponseBody
    public ServerResponse deleteResource(Integer id) {
        ServerResponse result = adminService.deleteResource(id);
        return result;
    }

    @RequestMapping(value = "/handle-content")
    public String handleContent(@RequestParam(value = "type", defaultValue = "1") Integer type, @RequestParam(value = "id", required = false) Integer id, Model model) {

        ContentViewVO contentVo = new ContentViewVO();

        contentVo.setLabels(adminService.findLabelsByFinalChildForAll().getData());
        if (id != null) {
            contentVo.setId(id);
            Content data = frontService.findContentById(id).getData();
            contentVo.setLabelId(data.getLabelId());
            if (data.getContentType() == 1) {
                contentVo.setDetail(Jsoup.parse(data.getDetail()).select("textarea").text());
            } else {
                contentVo.setDetail(data.getDetail());
            }
            contentVo.setTitle(data.getTitle());
            type = data.getContentType();
        }

        model.addAttribute(DATA, contentVo);


        if (type.equals(Const.ContentTypeCommon.EDITOR)) {
            return "admin/ueditor-tem";
        } else if (type.equals(Const.ContentTypeCommon.MARKDOWN)) {
            return "admin/markdown-tem";
        }
        model.addAttribute(DEFAULT_RESULT, "未知的异常");
        return "error";
    }

    @PutMapping("/create-update-content")
    @ResponseBody
    public ServerResponse createAndUpdateContent(Content content) {
        if (content.getId() != null) {
            ServerResponse<Content> data = frontService.findContentById(content.getId());
            if (data.isSuccess()) {
                //如果找到了 有这个文章说明是更新操作
                return adminService.updateInfo(content);
            } else {
                //否则就是插入的操作
                return adminService.publishInfo(content.getDetail(), content.getTitle()
                        , content.getLabelId(), content.getContentType());
            }
        }
        return ServerResponse.createErrorMessage("参数错误");
    }

    @PostMapping("/mark-upload")
    @ResponseBody
    public Map<String, Object> markdownUploadImage(@RequestParam(value = "editormd-image-file") MultipartFile imgFile) {
        ServerResponse<UploadResult> response = fileUploadService.upload(imgFile, Const.FileCommon.IMG_DIR, "mark");
        /*
        * {
            success : 0 | 1, //0表示上传失败;1表示上传成功
            message : "提示的信息",
            url     : "图片地址" //上传成功时才返回
            }
        * */
        Map<String, Object> map = new HashMap<>(3);
        int status;
        String msg;
        String url = "";
        if (response.isSuccess()) {
            status = 1;
            url = response.getData().getAbsoluteUrl();
        } else {
            status = 0;
        }
        msg = response.getMsg();
        map.put("success", status);
        map.put("message", msg);
        map.put("url", url);
        return map;
    }


    /**
     * 初始化uEditor的
     *
     * @param response
     * @param request
     */
    @RequestMapping("/editorinit")
    public void initUEdit(HttpServletResponse response, HttpServletRequest request) throws IOException {
        response.setContentType("application/json");
        String rootPath = new ClassPathResource("config.json").getFile().getAbsolutePath();
        try {
            ActionEnter actionEnter = new MyActionEnter(request, rootPath);
            String exec = actionEnter.exec();
            PrintWriter writer = response.getWriter();
            writer.write(exec);
            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * UEditor上传文件用的
     *
     * @param files
     * @return
     */
    @PostMapping("/ueditor-upload")
    @ResponseBody
    public Map<String, Object> ueditorUpload(@RequestParam("upfile") MultipartFile[] files) {
        Map<String, Object> map = new HashMap<>(4);
        if (files != null && files.length != 0) {
            for (MultipartFile file : files) {
                ServerResponse<UploadResult> response = fileUploadService.upload(file, Const.FileCommon.IMG_DIR, "ueditor");
                if (response.isSuccess()) {
                    //成功的
                    map.put("original", response.getData().getOriginalName());
                    map.put("name", response.getData().getNewName());
                    map.put("url", response.getData().getAbsoluteUrl());
                    map.put("state", "SUCCESS");
                } else {
                    //失败的
                    map.put("original", "");
                    map.put("name", "");
                    map.put("url", "");
                    map.put("state", response.getMsg());
                }
            }
        }
        return map;
    }

    @RequestMapping("/content-manage")
    public String contentManage(@RequestParam(value = "labelId", required = false) Integer labelId,
                                @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                @RequestParam(value = "search", required = false) String search, Model model, HttpServletRequest request) {
        if (labelId == null && search == null) {
            //错误了 不能都为空
            model.addAttribute(DEFAULT_RESULT, "参数错误啦！！");
            return "error";
        }
        ContentManageVo manageVo = new ContentManageVo();

        String requestUrl = "";

        //添加自定义的labels
        Labels labels = new Labels();
        labels.setName("全部");
        labels.setParentId(Integer.MIN_VALUE);
        labels.setId(-1);
        Set<Labels> selectList = new HashSet<>();
        selectList.add(labels);
        selectList.addAll(adminService.findLabelsByFinalChildForAll().getData());
        manageVo.setLabels(selectList);

        if (labelId != null) {
            requestUrl = request.getContextPath() + "/admin/content-manage?labelId=" + labelId;
            if (labelId < 0) {
                manageVo.setContents(frontService.findContentAll(page).getData());
            } else {
                manageVo.setContents(frontService.findContentByLabelsId(labelId, page).getData());
            }
            manageVo.setCurrentLabelsId(labelId);
        } else if (search != null) {
            requestUrl = request.getContextPath() + "/admin/content-manage?search=" + search;
            manageVo.setContents(frontService.searchContentByDetail(search, page).getData());
        }
        manageVo.setRequestUrl(requestUrl);
        model.addAttribute(DATA, manageVo);
        return "admin/contentManage-tem";
    }

    @GetMapping("/content-top")
    @ResponseBody
    public ServerResponse contentTop(Integer id, HttpSession session) {
        if (id == null) {
            return ServerResponse.createErrorMessage("参数错误");
        }

        ViewConfiguration config = (ViewConfiguration) session.getServletContext().getAttribute(Const.KEY_SETTINGS);
        if (config.getSetting() != null && config.getSetting().getId() > 0) {
            //这里就是用户自定的配置信息了
            config.setContentTopId(id);
            ServerResponse response = adminService.updateSettingsById(config.getSetting().getId(), config);
            if (response.isSuccess()) {
                return ServerResponse.createBySuccessMessage("置顶成功");
            } else {
                return ServerResponse.createErrorMessage("置顶失败");
            }
        } else {
            return ServerResponse.createErrorMessage("默认的配置是不能更新它的,请在页面管理模块中创建新的配置信息");
        }
    }

    @DeleteMapping("/delete-content")
    @ResponseBody
    public ServerResponse deleteContent(Integer id) {
        return adminService.deleteInfo(id);
    }


    @ResponseBody
    @PutMapping("/update-admin")
    public ServerResponse updateAdmin(Admin admin, @RequestParam(value = "old-pwd", required = false) String oldPwd, @RequestParam(value = "head", required = false) MultipartFile head) {
        Admin sysAdmin = (Admin) SecurityUtils.getSubject().getPrincipal();
        Admin ad = new Admin();
        ad.setAccountId(sysAdmin.getAccountId());
        if (oldPwd != null) {
            //那么就是更新密码啦
            if (MD5Util.encrypt(oldPwd, sysAdmin.getAccountId()).equals(sysAdmin.getPassword())) {
                ad.setPassword(MD5Util.encrypt(admin.getPassword(), sysAdmin.getAccountId()));
                return adminService.updateMyAccount(ad);
            } else {
                //原始密码不匹配
                return ServerResponse.createErrorMessage("原始密码不匹配");
            }
        }

        //否则就是除了密码 更新传来的不为null的值
        if (StringUtils.isNotBlank(admin.getName())) {
            ad.setName(admin.getName());
        }
        if (head != null && !head.isEmpty()) {
            ServerResponse<UploadResult> response = fileUploadService.upload(head, Const.FileCommon.ADMIN_DIR, sysAdmin.getAccountId());
            if (response.isSuccess()) {
                ad.setHead(response.getData().getNewName());
            }
        }
        if (StringUtils.isNotBlank(admin.getSign())) {
            ad.setSign(admin.getSign());
        }

        return adminService.updateMyAccount(ad);
    }

    @RequestMapping("/account-manage")
    public String accountManage() {
        return "admin/account-tem";
    }


    @RequestMapping("/resources-tem")
    public String getResourcesTem() {
        return "admin/pushResources-tem";
    }

    @PutMapping("/insert-resources")
    @ResponseBody
    public ServerResponse<Resources> insertResource(Resources resources, @RequestParam(value = "file", required = false) MultipartFile file) {
        if (file != null && !file.isEmpty()) {
            ServerResponse<UploadResult> res = fileUploadService.upload(file, Const.FileCommon.DATA_DIR, "resource");
            if (res.isSuccess()) {
                resources.setUrl(res.getData().getAbsoluteUrl());
            } else {
                return ServerResponse.createErrorMessage("文件上传失败");
            }
        }
        if (!resources.getUrl().contains("http")) {
            resources.setUrl("http://" + resources.getUrl());
        }
        ServerResponse<Resources> result = adminService.insertResource(resources);
        return result;
    }

    @RequestMapping("/labels-tem")
    public String getLabelsManageTem(Model model) {
        List<Labels> data = frontService.findLabelsAll().getData();
        List<Labels> result = new ArrayList<>();
        result.add(new Labels(0, 0, "根节点"));
        for (Labels labels : data) {
            result.add(labels.getClone());
        }
        model.addAttribute(DATA, result);
        return "admin/labels-tem";
    }

    /**
     * 請求labels數據
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/labels-data", headers = {"content-Type=application/json"})
    public Set<LabelsManageVo> getLabelsData() {
        ServerResponse<Set<LabelsManageVo>> response = adminService.getTreeViewDatas();
        return response.getData();
    }

    @RequestMapping(value = "/update-label", method = RequestMethod.PUT)
    @ResponseBody
    public ServerResponse updateLabel(Labels labels) {
        if (labels.getId() != null && labels.getParentId() != null) {
            if (labels.getId().equals(labels.getParentId())) {
                return ServerResponse.createErrorMessage("自己不能当自己的父节点哦！");
            }
        }
        if (labels.getId() != null && labels.getId() == 1) {
            return ServerResponse.createErrorMessage("默认标签不可更新哦!");
        }
        return adminService.updateLabel(labels);
    }

    @RequestMapping(value = "/delete-label", method = RequestMethod.DELETE)
    @ResponseBody
    public ServerResponse deleteLabel(Integer id) {
        if (id == 1) {
            return ServerResponse.createErrorMessage("默认标签不可删除");
        }
        return adminService.deleteLabels(id);
    }

    @RequestMapping(value = "/add-label", method = RequestMethod.PUT)
    @ResponseBody
    public ServerResponse addLabels(Labels labels) {
        return adminService.addLabel(labels.getParentId(), labels.getName());
    }


    ///////////////////////////////////////////////////////////////////////////
    // 页面管理模块
    ///////////////////////////////////////////////////////////////////////////

    @Autowired
    private ViewConfiguration configeTemplate;

    /**
     * 通用的文件上传方法
     *
     * @param files
     * @param prefix 文件前缀
     * @return
     */
    @PostMapping("/file-upload")
    @ResponseBody
    public ServerResponse<UploadResult> fileUpload(MultipartFile[] files, String prefix) {
        if (files == null || files.length == 0 || StringUtils.isBlank(prefix)) {
            return ServerResponse.createErrorMessage("参数错误");
        }
        ServerResponse<UploadResult> response = ServerResponse.createErrorMessage("ERROR:未知异常");
        for (MultipartFile mf : files) {
            ServerResponse<UploadResult> upload = fileUploadService.upload(mf, Const.FileCommon.ADMIN_DIR, prefix);
            if (upload.isSuccess()) {
                response = upload;
            }
        }
        return response;
    }

    @GetMapping("/get-config")
    @ResponseBody
    public ServerResponse<ViewConfiguration> getConfigSetting(Integer id, HttpSession session) {
        if (id != null) {
            if (id < 0) {
                //定义小于0为获取默认的配置
                return ServerResponse.createBySuccess(configeTemplate);
            } else {
                //否则就是从数据库中查询这个配置信息
                return adminService.findSettingById(id);
            }
        }
        return ServerResponse.createErrorMessage("参数错误");
    }

    @RequestMapping("/view-manage")
    public String getViewManageTem(Model model) {
        ServerResponse<List<ViewConfiguration>> response = adminService.findSettingsForAll();
        if (response.isSuccess()) {
            response.getData().add(configeTemplate);
        }
        Collections.sort(response.getData(), (o1, o2) -> !o1.getSetting().getStatus() ? 1 : -1);
        model.addAttribute(DATA, response);
        return "admin/viewManage-tem";
    }

    @PostMapping("/save-config")
    @ResponseBody
    public ServerResponse saveConfig(@RequestBody ViewConfiguration config, HttpSession session) {
        if (config.getSetting() != null && config.getSetting().getId() < 0) {
            return ServerResponse.createErrorMessage("默认配置是无法更新的!");
        }
        ServerResponse response = adminService.updateSettingsById(config.getSetting().getId(), config);
        if (response.isSuccess()) {
            ServerResponse<ViewConfiguration> result = adminService.findSettingByActive();
            if (result.isSuccess()) {
                session.getServletContext().setAttribute(Const.KEY_SETTINGS, result.getData());
            }
        }
        return response;
    }

    @PostMapping(value = {"/delete-config", "/set-active"})
    @ResponseBody
    public ServerResponse handlerConfig(HttpServletRequest request, Integer id) {
        if (id == null) {
            return ServerResponse.createErrorMessage("参数错误");
        }
        String requestUrl = request.getRequestURI();
        if (requestUrl.contains("delete-config")) {
            if (id < 0) {
                return ServerResponse.createErrorMessage("默认配置是不能删除的");
            }
            //如果删除的配置是当前激活的配置的话
            if (((ViewConfiguration) request.getSession().getServletContext().getAttribute(Const.KEY_SETTINGS))
                    .getSetting().getStatus() == true) {
                configeTemplate.getSetting().setStatus(true);
                request.getSession().getServletContext().setAttribute(Const.KEY_SETTINGS, configeTemplate);
            }
            //删除的操作
            return adminService.deleteSettingConfig(id);
        } else {
            if (id < 0) {
                //说明更新的是默认的配置
                ServerResponse isSuccess = adminService.cancelSettingActive();
                if (isSuccess.isSuccess()) {
                    configeTemplate.getSetting().setStatus(true);
                    request.getSession().getServletContext().setAttribute(Const.KEY_SETTINGS, configeTemplate);
                }
                return isSuccess;
            } else {
                //更新为激活的操作
                ServerResponse response = adminService.updateSettingToActive(id);
                if (response.isSuccess()) {
                    //如果更新成功的话就更新所有内存中的配置
                    ServerResponse<ViewConfiguration> result = adminService.findSettingByActive();
                    if (result.isSuccess()) {
                        request.getSession().getServletContext().setAttribute(Const.KEY_SETTINGS, result.getData());
                        configeTemplate.getSetting().setStatus(false);
                    }
                }
                return response;
            }
        }
    }

    /**
     * 插入一个新的模板到数据库
     *
     * @return
     */
    @PutMapping("/add-config")
    @ResponseBody
    public ServerResponse insertNewConfigForTemplate() {
        return adminService.addSettingConfig(configeTemplate);
    }

    ///////////////////////////////////////////////////////////////////////////
    // 评论管理
    ///////////////////////////////////////////////////////////////////////////

    /**
     * 返回渲染完成的页面
     *
     * @param search 查询
     * @param sort   分类 0代表降序 1代表升序 //防止SQL注入
     * @param page   页码
     * @param model
     * @return
     */
    @GetMapping("/comment")
    public String getCommentTem(@RequestParam(value = "search", required = false) String search,
                                @RequestParam(value = "sort", defaultValue = "0") Integer sort,
                                @RequestParam(value = "page", defaultValue = "1") Integer page, Model model) {
        if (!sort.equals(0) && !sort.equals(1)) {
            sort = 0;
        }
        String order = "desc";
        if (sort.equals(1)) {
            order = "asc";
        }
        ServerResponse<PageInfo<CommentVo>> response = adminService.findCommentAll(search, order, page);
        for (CommentVo vo : response.getData().getList()) {
            vo.setContentTitle(ParseTextUtil.stringCut(vo.getContentTitle(), 10));
            vo.setDetail(ParseTextUtil.stringCut(vo.getDetail(), 10));
        }

        String param = "?sort=" + sort;
        if (search != null) {
            param += "&search=" + search;
        }

        model.addAttribute("urlParam", param);
        model.addAttribute("search", search);
        model.addAttribute("sort", sort);
        model.addAttribute(DATA, response);
        return "admin/comment-tem";
    }


    @DeleteMapping("/comment")
    @ResponseBody
    public ServerResponse deleteComment(Integer id) {
        return adminService.deleteComment(id);
    }

    ///////////////////////////////////////////////////////////////////////////
    // 首页模块
    ///////////////////////////////////////////////////////////////////////////

    @DeleteMapping("/delete-comment-info")
    @ResponseBody
    public ServerResponse deleteCommentInfo(@RequestParam(value = "id") Integer id) {
        if (id == null) {
            return ServerResponse.createErrorMessage("参数错误");
        }
        if (id < 0) {
            //删除所有
            return adminService.deleteCommentInfoAll();
        } else {
            //删除指定的ID
            return adminService.deleteCommentInfoById(id);
        }
    }

    @GetMapping("/main")
    public String getMainTem(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page, Model model) {
        model.addAttribute(DATA, adminService.getMainVoAndInitData(page));
        return "admin/main-tem";
    }

    ///////////////////////////////////////////////////////////////////////////
    // 得到最后一个说明书的方法
    ///////////////////////////////////////////////////////////////////////////

    @RequestMapping("/explain-tem")
    public String getExplain(){
        return "admin/explain-tem";
    }
}
