package com.cheng.ssdemo.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.security.SocialUser;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collection;

/**
 * @Auther: cheng
 * @Date: 2019/12/24 21:54
 * @Description:
 */
@Component("customerUserDetails")
public class CustomerUserDetails implements UserDetailsService,SocialUserDetailsService{

    @Resource
    private PasswordEncoder passwordEncoder;

    //表单登录时候用的,传的是username
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        //用户名查找用户信息
        Collection<? extends GrantedAuthority> authorities ;
        //用户名,密码,权限集合
        //admin权限的GrantedAuthority
        //加密密码应该放在注册时存入数据库，不应该在这里
        //从数据库取
        //每次加密密码不一样
        return new User(s,passwordEncoder.encode("123456") ,true,true,true,true, AuthorityUtils.commaSeparatedStringToAuthorityList("Admin"));
    }

    //社交登录用的
    //传的是根据网站providerId查询的userId
    @Override
    public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException {
        //根据userid构建UserDetails
        String password = passwordEncoder.encode("123456");
        return new SocialUser(userId,password,true,true,true,true,AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
    }
}
