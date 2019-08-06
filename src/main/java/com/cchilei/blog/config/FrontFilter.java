package com.cchilei.blog.config;

import com.cchilei.blog.controller.FrontController;
import com.cchilei.blog.service.FrontService;
import com.cchilei.blog.common.Const;
import com.cchilei.blog.pojo.viewconfig.ViewConfiguration;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @Author
 * @Create 2018-05-17 16:45
 */
@WebFilter(urlPatterns = "/main/*", displayName = "FrontFilter")
public class FrontFilter implements Filter {

    private FrontService frontService;


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(filterConfig.getServletContext());
        frontService = webApplicationContext.getBean(FrontService.class);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest res = (HttpServletRequest) request;
        res.getSession().setAttribute(FrontController.ADMIN_MESSAGE, frontService.findAdminMessage().getData());
        ViewConfiguration setting = (ViewConfiguration) res.getSession().getServletContext().getAttribute(Const.KEY_SETTINGS);
        res.getSession().setAttribute(Const.KEY_SETTINGS, setting);
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
