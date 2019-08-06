package com.cchilei.blog.config;

import com.cchilei.blog.controller.FrontController;
import com.cchilei.blog.util.MyAtomicInteger;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.HashSet;

/**
 * @Author
 * @Create 2018-05-25 17:17
 */
@WebListener
public class FrontSessionListener implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        //添加统计的集合
        se.getSession().setAttribute(FrontController.KEY_READ_RECORD, new HashSet<Integer>());
        MyAtomicInteger.plus();
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        MyAtomicInteger.sub();
    }
}
