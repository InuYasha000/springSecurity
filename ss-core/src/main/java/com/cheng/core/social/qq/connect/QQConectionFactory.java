package com.cheng.core.social.qq.connect;


import com.cheng.core.social.qq.api.QQ;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;
import org.springframework.social.oauth2.OAuth2ServiceProvider;

/**
 * @Auther: cheng
 * @Date: 2019/12/27 11:26
 * @Description:
 */
public class QQConectionFactory extends OAuth2ConnectionFactory<QQ> {

    //构造函数传参需要根据实际所需要什么，并不是一味的跟着父类去传参
    public QQConectionFactory(String providerId, String appId,String appSecret) {
        super(providerId, new QQServiceProvider(appId,appSecret), new QQAdapter());
    }
}
