package com.cheng.core.social.qq.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Auther: cheng
 * @Date: 2019/12/27 08:17
 * @Description:
 */
@Slf4j
//不能注册为spring组件,否则变为单例，此时AbstractOAuth2ApiBinding中accessToken则为定值。错误   @Component
//restTemplate是用来给服务提供商发送http请求,用于最后一步获取用户信息
public class QQimpl extends AbstractOAuth2ApiBinding implements QQ{

    //获取openId的url
    private static final String URL_GET_OPENID = "https://graph.qq.com/oauth2.0/me?access_token=%s";
    //获取userinfo的url
    //三个参数 accessToken oauth_consumer_key openid
    //accessToken父类会带上
    private static final String URL_GET_USERINFO = "https://graph.qq.com/user/get_user_info?oauth_consumer_key=%s&openid=%s";

    private String oauthConsumerKey;

    //通过accessToken拿到的  URL_GET_OPENID,构造函数不用传
    private String openId;

    @Resource
    private ObjectMapper objectMapper;

    public QQimpl(String accessToken,String oauthConsumerKey){
        //父类的构造函数调用TokenStrategy.AUTHORIZATION_HEADER  会放入请求头,但是qq的accessToken是放到url的,所以需要使用TokenStrategy.OAUTH_TOKEN_PARAMETER
        super(accessToken, TokenStrategy.OAUTH_TOKEN_PARAMETER);
        this.oauthConsumerKey = oauthConsumerKey;
        //获取
        String url = String.format(URL_GET_OPENID,accessToken);
        //getRestTemplate()拿到RestTemplate
        String result = getRestTemplate().getForObject(url,String.class);
        log.info("result:{}",result);
        //截取，转义
        this.openId = StringUtils.substringBetween(result,"\"openid\":","}");
    }

    @Override
    public QQUserInfo getUserInfo(){
        try {
            String url = String.format(URL_GET_USERINFO,oauthConsumerKey,openId);
            String result = getRestTemplate().getForObject(url,String.class);
            log.info("result:{}",result);
            //读成对象
            return objectMapper.readValue(result,QQUserInfo.class);
        }catch (Exception e){
            log.error("getUserInfo error:{}",e.getMessage());
            throw new RuntimeException("",e);
        }
    }
}
