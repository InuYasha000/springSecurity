package com.cheng.core.social.wx.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.CharSet;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;

import javax.annotation.Resource;
import java.nio.charset.Charset;
import java.util.List;

/**
 * @Auther: cheng
 * @Date: 2020/1/2 18:36
 * @Description:
 */
@Slf4j
public class WXImpl extends AbstractOAuth2ApiBinding implements WX{

    @Resource
    private ObjectMapper objectMapper;

    private static final String URL_GET_USER_INFO = "https://api.weixin.qq.com/sns/userinfo?openid=";

    @Override
    public WXUserInfo getUserInfo(String openId) {
        String url = URL_GET_USER_INFO + openId;
        String response = getRestTemplate().getForObject(url,String.class);
        if(StringUtils.contains(response,"errcode")){
            return null;
        }
        WXUserInfo wxUserInfo = null;
        try{
            wxUserInfo = objectMapper.readValue(response,WXUserInfo.class);
        }catch (Exception e){
            log.error("WXImpl getUserInfo error{}",e.getMessage(),e);
        }
        return wxUserInfo;
    }

    /**
     *
     * @Description 默认注册的StringHttpMessageConverter字符集为ISO-8859-1，
     * 而微信返回的是UTF-8的，所以覆盖了原来的方法。
     * @author Cheng
     * @date 2020/1/2
     */

    @Override
    protected List<HttpMessageConverter<?>> getMessageConverters() {
        List<HttpMessageConverter<?>> messageConverters = super.getMessageConverters();
        messageConverters.remove(0);
        messageConverters.add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
        return messageConverters;
    }

    public WXImpl(String accessToken){
        super(accessToken, TokenStrategy.ACCESS_TOKEN_PARAMETER);
    }
}
