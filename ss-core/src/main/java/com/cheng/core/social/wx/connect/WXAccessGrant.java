package com.cheng.core.social.wx.connect;

import lombok.Data;
import org.springframework.social.oauth2.AccessGrant;

/**
 * @Auther: cheng
 * @Date: 2020/1/2 20:19
 * @Description:微信的accessToken信息，与标准Oauth2协议不同，微信在获取Access会返回openId，
 *没有单独通过openid获取accessToken的操作，所以继承标准AccessGrant，添加了openId字段，
 * 作为对微信AccessToken的封装
 */
@Data
public class WXAccessGrant extends AccessGrant{

    private String openId;

    public WXAccessGrant(String accessToken, String scope, String refreshToken, Long expiresIn){
        super(accessToken,scope,refreshToken,expiresIn);
    }
}
