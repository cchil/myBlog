package com.cchilei.blog.config;

import com.cchilei.blog.realm.AdminRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Author
 * @Create 2018-05-08 14:54
 */
@Configuration
public class ShiroConfiguration {

    @Bean
    public AdminRealm createAdminRealm() {
        AdminRealm adminRealm = new AdminRealm();
        adminRealm.setCacheManager(new MemoryConstrainedCacheManager());
        adminRealm.setCredentialsMatcher(new HashedCredentialsMatcher("MD5"));
        return adminRealm;
    }

    @Bean
    public SecurityManager securityManager(AdminRealm adminRealm){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(adminRealm);
        return securityManager;
    }

    /**
     * 实例化过滤器
     *
     * @param securityManager
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean filter = new ShiroFilterFactoryBean();
        filter.setSecurityManager(securityManager);
        /**
         * 下面就是进行页面的认证操作的过滤器
         * 过滤的条件
         */
        Map<String, String> map = new LinkedHashMap<>(1);
        map.put("/admin/login", "anon");
        map.put("/admin/login-from", "anon");
        map.put("/druid/**", "authc");
        map.put("/admin/logout", "logout");

        map.put("/admin/**", "authc");

        filter.setLoginUrl("/admin/login");
        filter.setSuccessUrl("/admin/index");
        filter.setUnauthorizedUrl("/unauthorized");

        //这一步是讲设置的过滤规则添加到
        filter.setFilterChainDefinitionMap(map);
        return filter;
    }

    /**
     * 开启注解代理功能
     *
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }

    /**
     * 为了使注解生效
     * @return
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator advisor = new DefaultAdvisorAutoProxyCreator();
        return advisor;
    }

}
