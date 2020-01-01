package com.cheng.brower.config;

import com.cheng.brower.authentication.CustomerAuthenticationFailureHandler;
import com.cheng.brower.authentication.CustomerAuthenticationSuccessHandler;
import com.cheng.core.authentication.mobile.SmsCodeAuthenticationSecurityConfig;
import com.cheng.core.authentication.mobile.SmsCodeFilter;
import com.cheng.core.common.CoreSecurityProperties;
import com.cheng.core.common.SocialProperties;
import com.cheng.core.validatecode.ValidateCodeFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.social.security.SpringSocialConfigurer;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * @Auther: cheng
 * @Date: 2019/12/21 16:14
 * @Description:
 */
@Configuration
public class BrowerSecurityConfig extends WebSecurityConfigurerAdapter{

    @Resource
    private CoreSecurityProperties coreSecurityProperties;

    @Resource
    private CustomerAuthenticationSuccessHandler customerAuthenticationSuccessHandler;

    @Resource
    private CustomerAuthenticationFailureHandler customerAuthenticationFailureHandler;

    @Resource
    private SpringSocialConfigurer springSocialConfigurer;

    @Resource
    private DataSource dataSource;

    @Resource
    private UserDetailsService userDetailsService;

    @Resource
    private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;

    @Bean
    public PersistentTokenRepository persistentTokenRepository(){
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
        //可以在源码里面拿到sql语句去数据库执行
        //也可以设置true在启动的时候去执行
//        tokenRepository.setCreateTableOnStartup(true);
        return tokenRepository;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        ValidateCodeFilter filter = new ValidateCodeFilter();
        filter.setAuthenticationFailureHandler(customerAuthenticationFailureHandler);
        filter.setCoreSecurityProperties(coreSecurityProperties);
        filter.afterPropertiesSet();

        SmsCodeFilter smsCodeFilter = new SmsCodeFilter();
        smsCodeFilter.setAuthenticationFailureHandler(customerAuthenticationFailureHandler);
        smsCodeFilter.setCoreSecurityProperties(coreSecurityProperties);
        smsCodeFilter.afterPropertiesSet();
        //表单登录验证
        //任何请求都要授权
        http.addFilterBefore(smsCodeFilter,UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
                .formLogin()
                //不然走默认的springsecurity登录页面
                .loginPage("/authentication/require")
                //走登录成功或失败处理逻辑，否则直接走controller
                .loginProcessingUrl("/authentication/form")
                .successHandler(customerAuthenticationSuccessHandler)
                .failureHandler(customerAuthenticationFailureHandler )
                .and().rememberMe()
                .tokenRepository(persistentTokenRepository())
                .tokenValiditySeconds(coreSecurityProperties.getBrower().getRememberMeTime())
                .userDetailsService(userDetailsService)
                .and().authorizeRequests()
                //登录页面不需要授权，不然一直死循环
                .antMatchers("/authentication/**",
                        coreSecurityProperties.getBrower().getLoginPage(),
                        "/createCode/*").permitAll()
                .anyRequest().authenticated()
                //解决跨域问题
                .and().csrf().disable()
                .apply(springSocialConfigurer)
                .and()
                .apply(smsCodeAuthenticationSecurityConfig);
    }
}
