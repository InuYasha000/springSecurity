package com.cheng.core.social.qq.convert;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Template;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;

/**
 * @Auther: cheng
 * @Date: 2020/1/1 13:52
 * @Description:
 */
@Slf4j
public class QQOauth2Template extends OAuth2Template{
    public QQOauth2Template(String clientId, String clientSecret, String authorizeUrl, String accessTokenUrl){
        super(clientId, clientSecret, authorizeUrl, (String)null, accessTokenUrl);
        //ss原生代码就已经实现了oauth2协议自带的五个参数，只是这个参数为true时才会携带
        setUseParametersForClientAuthentication(true);
    }

    @Override
    protected AccessGrant postForAccessGrant(String accessTokenUrl, MultiValueMap<String, String> parameters) {
        String response = getRestTemplate().postForObject(accessTokenUrl,parameters,String.class);
        log.info("response:{}",response);
        String[] items = StringUtils.splitByWholeSeparatorPreserveAllTokens(response,"%");
        String accessToken = StringUtils.substringAfter(items[0],"=");
        Long expireTime = new Long(StringUtils.substringAfter(items[1],"="));
        String refreshToken = StringUtils.substringAfter(items[2],"=");
        return new AccessGrant(accessToken,null,refreshToken,expireTime);
    }

    @Override
    protected RestTemplate createRestTemplate() {
        RestTemplate restTemplate = super.createRestTemplate();
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
        return restTemplate;
    }
}
