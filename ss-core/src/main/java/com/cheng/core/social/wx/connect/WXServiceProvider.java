package com.cheng.core.social.wx.connect;

import com.cheng.core.social.wx.api.WX;
import com.cheng.core.social.wx.api.WXImpl;
import com.cheng.core.social.wx.convert.WXOauth2Template;
import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;
import org.springframework.social.oauth2.OAuth2Operations;

/**
 * @Auther: cheng
 * @Date: 2020/1/2 21:10
 * @Description:
 */
public class WXServiceProvider extends AbstractOAuth2ServiceProvider<WX>{

    //获取微信授权码的url
    private static final String URL_AUTHORIZE = "https://open.weixin.qq.com/connect/qrconnect";

    //获取微信accessToken的url
    private static final String URL_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token";

    public WXServiceProvider(String appId,String appSecret){
        super(new WXOauth2Template(appId,appSecret,URL_AUTHORIZE,URL_ACCESS_TOKEN_URL));
    }

    @Override
    public WX getApi(String s) {
        return new WXImpl(s);
    }
}
