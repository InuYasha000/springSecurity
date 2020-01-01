package com.cheng.core.social.qq.config;

import com.cheng.core.common.CoreSecurityProperties;
import com.cheng.core.common.QQProperties;
import com.cheng.core.common.SocialProperties;
import com.cheng.core.social.qq.connect.QQConectionFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.social.SocialAutoConfigurerAdapter;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.connect.ConnectionFactory;

import javax.annotation.Resource;

/**
 * @Auther: cheng
 * @Date: 2019/12/27 14:52
 * @Description:
 */


@Configuration
//qq连接工厂
@ConditionalOnProperty(value = "com.cheng.social.qq",name = "app-id")
public class QQAutoConfig extends SocialAutoConfigurerAdapter{

    @Resource
    private CoreSecurityProperties coreSecurityProperties;
    @Override
    protected ConnectionFactory<?> createConnectionFactory() {
        QQProperties qqProperties = coreSecurityProperties.getSocial().getQq();
        return new QQConectionFactory(qqProperties.getProviderId(),qqProperties.getAppId(),qqProperties.getAppSecret());
    }
}
