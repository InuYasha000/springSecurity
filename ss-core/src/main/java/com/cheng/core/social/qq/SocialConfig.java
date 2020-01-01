package com.cheng.core.social.qq;

import com.cheng.core.common.CoreSecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.security.SpringSocialConfigurer;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * @Auther: cheng
 * @Date: 2019/12/27 11:32
 * @Description:
 */
@Configuration
@EnableSocial
public class SocialConfig extends SocialConfigurerAdapter{

    @Resource
    private DataSource dataSource;

    @Resource
    private CoreSecurityProperties coreSecurityProperties;

    @Override
    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
        //Encryptors.noOpText() 加密传参  这个不做任何加密
        //connectionFactoryLocator去查找ConnectionFactory
        return new JdbcUsersConnectionRepository(dataSource,connectionFactoryLocator, Encryptors.noOpText());
        //设置前缀
//        new JdbcUsersConnectionRepository(.setTablePrefix("");)
    }

    //SpringSocialConfigurer的configure方法可以修改/auth
    @Bean
    public SpringSocialConfigurer springSocialConfigurer(){
        String processesUrl = coreSecurityProperties.getSocial().getFilterProcessesUrl();
        CustomerSpringSocialConfigure configure = new CustomerSpringSocialConfigure(processesUrl);
        return configure;
    }
}
