package com.cchilei.blog.config;

import com.cchilei.blog.service.FileUploadService;
import com.cchilei.blog.service.impl.FileUploadServiceImpl;
import com.cchilei.blog.service.impl.RemoteFileUploadImpl;
import com.cchilei.blog.common.Const;
import com.qiniu.common.Zone;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.Arrays;

/**
 * @Author
 * @Create 2018-05-08 15:00
 */
@Configuration
public class WebMvcConfiguration extends WebMvcConfigurationSupport {

    /**
     * 跳转主页
     *
     * @param registry
     */
    @Override
    protected void addViewControllers(ViewControllerRegistry registry) {
        super.addViewControllers(registry);
        registry.addRedirectViewController("/", "/main/index");
    }

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    }

    @Bean
    public FilterRegistrationBean myFilter() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new HiddenHttpMethodFilter());
        registrationBean.setUrlPatterns(Arrays.asList("/*"));
        return registrationBean;
    }

    @Bean
    public FileUploadService fileUploadService(@Autowired CustomServerConfig config) {
        Const.FILE_ROOT_URL = config.getUrl();
        Const.DIRECTORY_URI = config.getDirectory();
        if (config.isRemote()) {
            if (StringUtils.isBlank(config.getBucket()) ||
                    StringUtils.isBlank(config.getAccessKey()) ||
                    StringUtils.isBlank(config.getSecretKey()) || config.getZoneIndex() == null || StringUtils.isBlank(config.getUrl())) {
                throw new NullPointerException("七牛云服务器的密匙没有配置，请检查您的配置");
            }
            Zone zone = null;
            switch (config.getZoneIndex()) {
                case 0:
                    zone = Zone.zone0();
                    break;
                case 1:
                    zone = Zone.zone1();
                    break;
                case 2:
                    zone = Zone.zone2();
                    break;
            }
            return new RemoteFileUploadImpl(config, zone);
        } else {
            if (StringUtils.isBlank(config.getDirectory()) || StringUtils.isBlank(config.getUrl())) {
                throw new NullPointerException("本地文件服务器的初始化参数没有配置，请检查您的配置");
            }
            return new FileUploadServiceImpl();
        }
    }

}
