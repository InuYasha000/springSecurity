package com.cheng.core.social.wx.config;

import com.cheng.core.common.CoreSecurityProperties;
import com.cheng.core.common.WXProperties;
import com.cheng.core.social.wx.connect.WXConnectionFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.social.SocialAutoConfigurerAdapter;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.connect.ConnectionFactory;

import javax.annotation.Resource;

/**
 * @Auther: cheng
 * @Date: 2020/1/2 21:29
 * @Description:微信连接工厂
 */
@Configuration
@ConditionalOnProperty(prefix = "com.cheng.social.wx",name = "app-id")
public class WXAutoConfig extends SocialAutoConfigurerAdapter {

    @Resource
    private CoreSecurityProperties coreSecurityProperties;

    @Override
    protected ConnectionFactory<?> createConnectionFactory() {
        WXProperties wxProperties = coreSecurityProperties.getSocial().getWx();
        return new WXConnectionFactory(wxProperties.getProviderId(),wxProperties.getAppId(),wxProperties.getAppSecret());
    }
}
