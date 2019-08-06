package com.cchilei.blog.realm;

import com.cchilei.blog.dao.AdminMapper;
import com.cchilei.blog.pojo.Admin;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author
 * @Create 2018-05-08 14:55
 */
public class AdminRealm extends AuthorizingRealm {

    @Autowired
    private AdminMapper adminMapper;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return null;
    }

    /**
     * 登录用的
     *
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken userToken = (UsernamePasswordToken) token;
        Admin result = adminMapper.selectByPrimaryKey(userToken.getUsername());
        if (result == null) {
            throw new UnknownAccountException("没有这个用户");
        }
        Admin admin = result.clonAdmin();
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(admin, admin.getPassword(), ByteSource.Util.bytes(admin.getAccountId()), getClass().getName());
        return info;
    }
}
