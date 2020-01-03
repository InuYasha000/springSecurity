package com.cheng.core.social.qq.connect;

import com.cheng.core.social.qq.api.QQ;
import com.cheng.core.social.qq.api.QQUserInfo;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;

/**
 * @Auther: cheng
 * @Date: 2019/12/27 10:32
 * @Description:个性化服务提供商之间的数据和标准springsocial之间做一个适配
 */
public class QQAdapter implements ApiAdapter<QQ>{

    @Override
    public UserProfile fetchUserProfile(QQ qq) {
        return null;
    }

    //比如微博更新主页
    @Override
    public void updateStatus(QQ qq, String s) {
        //DO NOTHING
    }
    //测试api是否可用,直接返回true
    @Override
    public boolean test(QQ qq) {
        return true;
    }

    //connection和api数据做一个适配
    @Override
    public void setConnectionValues(QQ qq, ConnectionValues connectionValues) {
        QQUserInfo qqUserInfo = qq.getUserInfo();
        //用户名字
        connectionValues.setDisplayName(qqUserInfo.getNickname());
        //头像
        connectionValues.setImageUrl(qqUserInfo.getFigureurl_qq_1());
        //个人主页 qq没有
        connectionValues.setProfileUrl(null);
        //服务商id  openid
        connectionValues.setProviderUserId(qqUserInfo.getOpenId());
    }
}
