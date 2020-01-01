package com.cheng.core.authentication.mobile;

import lombok.Data;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @Auther: cheng
 * @Date: 2019/12/30 16:54
 * @Description:
 */
@Data
public class SmsCodeAuthenticationProvider implements AuthenticationProvider{
    private UserDetailsService userDetailsService;

    //UserDetailService获取用户信息
    //重新读到已认证的Authentication里面去
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        //未认证的token
        SmsCodeAuthenticationToken authenticationToken = (SmsCodeAuthenticationToken) authentication;
        UserDetails user = userDetailsService.loadUserByUsername((String) authenticationToken.getPrincipal());
        if(user==null){
            throw  new InternalAuthenticationServiceException("无法获取用户信息");
        }
        //已认证的的token
        SmsCodeAuthenticationToken smsCodeAuthenticationToken = new SmsCodeAuthenticationToken(user,user.getAuthorities());
        smsCodeAuthenticationToken.setDetails(authenticationToken.getDetails());
        return smsCodeAuthenticationToken;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return SmsCodeAuthenticationToken.class.isAssignableFrom(aClass);
    }
}
