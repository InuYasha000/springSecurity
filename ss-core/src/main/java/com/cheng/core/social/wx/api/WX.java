package com.cheng.core.social.wx.api;

/**
 * @Auther: cheng
 * @Date: 2020/1/2 18:19
 * @Description:
 */
public interface WX {
    //在QQServiceProvider中接口只有accessToken一个参数
    WXUserInfo getUserInfo(String openId);
}
