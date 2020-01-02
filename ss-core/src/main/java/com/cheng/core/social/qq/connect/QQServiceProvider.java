package com.cheng.core.social.qq.connect;

import com.cheng.core.social.qq.api.QQ;
import com.cheng.core.social.qq.api.QQimpl;
import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Template;

/**
 * @Auther: cheng
 * @Date: 2019/12/27 09:36
 * @Description:
 */
//实现前五步
//泛型指的是QQ的实现类
public class QQServiceProvider extends AbstractOAuth2ServiceProvider<QQ>{

    //https://wiki.connect.qq.com/%E4%BD%BF%E7%94%A8authorization_code%E8%8E%B7%E5%8F%96access_token
    //导向认证服务器url
    private static final String URL_AUTHORIZE = "https://graph.qq.com/oauth2.0/authorize";
    //导向申请token url
    private static final String URL_ACCESSTOKEN = "https://graph.qq.com/oauth2.0/token";

    public QQServiceProvider(String appId,String appSecret){
        super(new OAuth2Template(appId,appSecret,URL_AUTHORIZE,URL_ACCESSTOKEN));
        this.appId = appId;
    }

    //整个应用对于qq来说只有一个
    private String appId;

    //accessToken--s  父类传过来的
    @Override
    public QQ getApi(String s) {
        //不能注册为component，所以每次需要new
        return new QQimpl(s,appId);
    }
}
