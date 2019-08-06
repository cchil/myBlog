package com.cchilei.blog.config;

import com.cchilei.blog.pojo.viewconfig.*;
import com.cchilei.blog.service.AdminService;
import com.cchilei.blog.common.Const;
import com.cchilei.blog.common.ServerResponse;
import com.cchilei.blog.pojo.Setting;
import com.cchilei.blog.pojo.viewconfig.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author
 * @Create 2018-05-20 13:50
 */
@WebListener(value = "myContextListener")
public class ContextListener implements ServletContextListener {

    private AdminService adminService;
    private ViewConfiguration configuration;
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(sce.getServletContext());
        adminService = webApplicationContext.getBean(AdminService.class);
        configuration = webApplicationContext.getBean(ViewConfiguration.class);


        ServletContext context = sce.getServletContext();
        ServerResponse<ViewConfiguration> active = adminService.findSettingByActive();
        try {
            getDefaultConfig(context.getContextPath());
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("获取Setting出错了--ContextListener", e);
        }
        if (!active.isSuccess()) {
            configuration.getSetting().setStatus(true);
            context.setAttribute(Const.KEY_SETTINGS, configuration);
        } else {
            configuration.getSetting().setStatus(false);
            context.setAttribute(Const.KEY_SETTINGS, active.getData());
        }
    }

    /**
     * 初始化默认的配置信息
     *
     * @param contextPath
     * @return
     * @throws IOException
     */
    public ViewConfiguration getDefaultConfig(String contextPath) throws IOException {
        ViewConfiguration rootConfig = configuration;
        rootConfig.setMainTitle("Blog");
        rootConfig.setMainHeadImage(contextPath + "/static/img/head.png");
        rootConfig.setShowFooter(true);

        ViewAboutStation viewAboutStation = new ViewAboutStation();
        viewAboutStation.setTitle("关于本站");
        viewAboutStation.setDetail("本站的详细内容");
        rootConfig.setViewAboutStation(viewAboutStation);

        ViewCarousel carousel = new ViewCarousel();
        List<ViewCarousel.OneImg> images = new ArrayList();
        images.add(new ViewCarousel.OneImg(contextPath + "/static/i/b1.jpg", "标题1", "内容1"));
        images.add(new ViewCarousel.OneImg(contextPath + "/static/i/b2.jpg", "标题2", "内容3"));
        images.add(new ViewCarousel.OneImg(contextPath + "/static/i/b3.jpg", "标题3", "内容3"));
        carousel.setViewCarousels(images);
        rootConfig.setViewCarousel(carousel);

        ViewContactMe viewContactMe = new ViewContactMe();
        viewContactMe.setGithub("#");
        viewContactMe.setOther("#");
        viewContactMe.setQq("#");
        viewContactMe.setWeibo("#");
        viewContactMe.setWeixin("#");
        rootConfig.setViewContactMe(viewContactMe);

        ViewContentItem viewContentItem = new ViewContentItem();
        ArrayList<String> itemHref = new ArrayList<>();
        File file = new ClassPathResource("static/img/").getFile();
        File[] imgs = file.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.getName().contains("img_");
            }
        });
        for (File img : imgs) {
            itemHref.add(contextPath + "/static/img/" + img.getName());
        }
        viewContentItem.setHref(itemHref);
        rootConfig.setViewContentItem(viewContentItem);

        ViewOther viewOther = new ViewOther();
        viewOther.setTitle("我的记录");
        viewOther.setItems(Arrays.asList(new ViewOther.Item("Uychen博客系统", "#")));
        rootConfig.setViewOther(viewOther);

        ViewTagCloud viewTagCloud = new ViewTagCloud();
        viewTagCloud.setTagItems(Arrays.asList(new ViewTagCloud.TagItem("Spring-Boot", "#")));
        rootConfig.setViewTagCloud(viewTagCloud);

        Setting setting = new Setting();
        setting.setId(Integer.MIN_VALUE);
        rootConfig.setSetting(setting);

        return rootConfig;
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
