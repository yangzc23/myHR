package com.testin.common.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import com.testin.sys.domain.SysUser;
import com.testin.sys.service.SysUserService;

public class ShiroDbRealm extends AuthorizingRealm {
    @Autowired
    SysUserService userService;

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("=================doGetAuthenticationInfo[登录认证]=====================");
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        System.out.println("当前认证用户：" + token.getUsername());
        SysUser user = userService.getByLoginName(token.getUsername());
        if (user != null) {
            ShiroUser shiroUser = new ShiroUser(user.getLoginName());
            shiroUser.setId(user.getId());
            shiroUser.setName(user.getName());
            //以创建时间作为盐
            String salt = String.valueOf(user.getCreateTime().getTime());
            return new SimpleAuthenticationInfo(shiroUser, user.getPassword(), ByteSource.Util.bytes(salt), getName());
        }
        return null;
    }

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
    	System.out.println(SecurityUtils.getSubject().getSession().getTimeout());
    	SecurityUtils.getSubject().getSession().setTimeout(30000);
    	System.out.println("=================doGetAuthorizationInfo[授权认证]=====================");
        ShiroUser shiroUser = (ShiroUser) principalCollection.getPrimaryPrincipal();

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addRole("admin");
        info.addStringPermission("user:create");
        return info;
    }

}