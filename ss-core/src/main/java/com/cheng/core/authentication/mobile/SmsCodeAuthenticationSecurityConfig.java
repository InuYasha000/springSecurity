package com.cheng.core.authentication.mobile;

import com.cheng.core.common.CoreSecurityProperties;
import com.cheng.core.validatecode.ValidateCodeFilter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Auther: cheng
 * @Date: 2019/12/30 17:26
 * @Description:
 */
@Component("smsCodeAuthenticationSecurityConfig")
public class SmsCodeAuthenticationSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain,HttpSecurity>{

    @Resource
    private AuthenticationFailureHandler customerAuthenticationFailureHandler;

    @Resource
    private AuthenticationSuccessHandler customerAuthenticationSuccessHandler;

    @Resource
    private UserDetailsService customerUserDetails;

    @Override
    public void configure(HttpSecurity builder) throws Exception {


        SmsCodeAuthenticationFilter smsCodeAuthenticationFilter = new SmsCodeAuthenticationFilter();
        //配置manage，需要调用manage来认证
        smsCodeAuthenticationFilter.setAuthenticationManager(builder.getSharedObject(AuthenticationManager.class));
        //和其他成功失败的处理器统一起来
        smsCodeAuthenticationFilter.setAuthenticationSuccessHandler(customerAuthenticationSuccessHandler);
        smsCodeAuthenticationFilter.setAuthenticationFailureHandler(customerAuthenticationFailureHandler);

        SmsCodeAuthenticationProvider smsCodeAuthenticationProvider = new SmsCodeAuthenticationProvider();
        smsCodeAuthenticationProvider.setUserDetailsService(customerUserDetails);
        builder.authenticationProvider(smsCodeAuthenticationProvider).addFilterAfter(smsCodeAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
