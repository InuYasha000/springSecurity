package com.cheng.core.social.wx.connect;

import com.cheng.core.social.wx.api.WX;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.connect.support.OAuth2Connection;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2ServiceProvider;

/**
 * @Auther: cheng
 * @Date: 2020/1/2 21:18
 * @Description:
 */
public class WXConnectionFactory extends OAuth2ConnectionFactory<WX>{

    public WXConnectionFactory(String providerId, String appId, String appSecret){
        super(providerId,new WXServiceProvider(appId,appSecret),new WXAdapter());
    }

    /**
     *  
     * @Description 由于微信的openId是和accessToken一起返回的，所以在这里直接根据accessToken设置providerUserId即可，不用像QQ那样通过QQAdapter来获取
     * @author Cheng
     * @date 2020/1/2
     */
    
    @Override
    protected String extractProviderUserId(AccessGrant accessGrant) {
        if(accessGrant instanceof WXAccessGrant) {
            return ((WXAccessGrant)accessGrant).getOpenId();
        }
        return null;
    }

    @Override
    public Connection<WX> createConnection(AccessGrant accessGrant) {
        return new OAuth2Connection<WX>(getProviderId(), extractProviderUserId(accessGrant), accessGrant.getAccessToken(),
                accessGrant.getRefreshToken(), accessGrant.getExpireTime(), getOAuth2ServiceProvider(), getApiAdapter(extractProviderUserId(accessGrant)));
    }

    /* (non-Javadoc)
     * @see org.springframework.social.connect.support.OAuth2ConnectionFactory#createConnection(org.springframework.social.connect.ConnectionData)
     */
    @Override
    public Connection<WX> createConnection(ConnectionData data) {
        return new OAuth2Connection<WX>(data, getOAuth2ServiceProvider(), getApiAdapter(data.getProviderUserId()));
    }

    private OAuth2ServiceProvider<WX> getOAuth2ServiceProvider() {
        return (OAuth2ServiceProvider<WX>) getServiceProvider();
    }

    private ApiAdapter<WX> getApiAdapter(String providerUserId) {
        return new WXAdapter(providerUserId);
    }
}
