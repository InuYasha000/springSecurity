package com.cheng.core.social.wx.convert;

import com.cheng.core.social.wx.connect.WXAccessGrant;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Template;
import org.springframework.util.MultiValueMap;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: cheng
 * @Date: 2020/1/2 20:17
 * @Description:
 */
@Slf4j
public class WXOauth2Template extends OAuth2Template{

    private String clientId;

    private String clientSecret;

    private String accessTokenUrl;

    private static final String REFRESH_TOKEN = "https://api.weixin.qq.com/sns/oauth2/refresh_token";
    @Resource
    private ObjectMapper objectMapper;

    public WXOauth2Template(String clientId, String clientSecret, String authorizeUrl, String accessTokenUrl){
        super(clientId,clientSecret,authorizeUrl,accessTokenUrl);
        setUseParametersForClientAuthentication(true);
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.accessTokenUrl = accessTokenUrl;
    }

    /**
     *
     * @Description 微信没有按照oauth2协议来传参，但是oauth2自己已经把传参写死，所以需要在这里覆盖父类方法
     * @author Cheng
     * @date 2020/1/2
     */

    @Override
    public AccessGrant exchangeForAccess(String authorizationCode, String redirectUri, MultiValueMap<String, String> additionalParameters) {
        StringBuffer sb = new StringBuffer(accessTokenUrl);
        sb.append("?appid="+clientId);
        sb.append("&secret="+clientSecret);
        sb.append("&code="+authorizationCode);
        sb.append("&grant_type=authentication_code");
        sb.append("&rediret_uri="+redirectUri);
        return getAccessToken(sb.toString());
    }

    @Override
    public AccessGrant refreshAccess(String refreshToken, MultiValueMap<String, String> additionalParameters) {
        StringBuffer sb = new StringBuffer(REFRESH_TOKEN);
        sb.append("?appid="+clientId);
        sb.append("&grant_type=refresh_token");
        sb.append("&refresh_token="+refreshToken);
        return getAccessToken(sb.toString());
    }

    private AccessGrant getAccessToken(String string) {
        log.info("微信获取accessToken 请求url:{}",string);
        String response = getRestTemplate().getForObject(accessTokenUrl,String.class);
        log.info("微信获取AccessToken返回内容response:{}",response);
        Map<String,String> resultMap = new HashMap<>();
        try {
            resultMap = objectMapper.readValue(response,Map.class);
        }catch (Exception e){
            e.printStackTrace();
            log.error("解析微信获取AccessToken返回内容response出错error：{}",e.getMessage(),e);
        }
        if(StringUtils.isNotBlank(MapUtils.getString(resultMap,"errcode"))){
            String errcode = MapUtils.getString(resultMap,"errcode");
            String errmsg = MapUtils.getString(resultMap,"errmsg");
            throw new RuntimeException("微信获取accessToken失败error:"+errcode+",errmsg:"+errmsg);
        }

        WXAccessGrant wxAccessGrant = new WXAccessGrant(MapUtils.getString(resultMap,"accessToken"),
                MapUtils.getString(resultMap,"scope"),MapUtils.getString(resultMap,"refreshToken"),
                new Long(MapUtils.getString(resultMap,"expiresIn")));
        wxAccessGrant.setOpenId(MapUtils.getString(resultMap,"openid"));
        return wxAccessGrant;
    }


}
