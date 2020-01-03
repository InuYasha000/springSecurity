package com.cheng.core.social.wx.connect;

import com.cheng.core.social.wx.api.WX;
import com.cheng.core.social.wx.api.WXUserInfo;
import lombok.Data;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;

/**
 * @Auther: cheng
 * @Date: 2020/1/2 20:52
 * @Description:微信 api适配器，将微信 api的数据模型转为spring social的标准模型。
 */
@Data
public class WXAdapter implements ApiAdapter<WX>{

    private String openid;

    public WXAdapter(){}

    public WXAdapter(String openid){
        this.openid = openid;
    }

    @Override
    public void setConnectionValues(WX wx, ConnectionValues connectionValues) {
        WXUserInfo wxUserInfo = wx.getUserInfo(openid);
        connectionValues.setProviderUserId(wxUserInfo.getOpenid());
        connectionValues.setDisplayName(wxUserInfo.getNickname());
        connectionValues.setImageUrl(wxUserInfo.getHeadimgurl());
    }

    @Override
    public boolean test(WX wx) {
        return true;
    }

    @Override
    public UserProfile fetchUserProfile(WX wx) {
        return null;
    }

    @Override
    public void updateStatus(WX wx, String s) {

    }
}
