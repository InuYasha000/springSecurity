package com.cheng.core.social.qq;

import org.springframework.social.security.SocialAuthenticationFilter;
import org.springframework.social.security.SpringSocialConfigurer;

/**
 * @Auther: cheng
 * @Date: 2019/12/31 18:30
 * @Description:
 */
public class CustomerSpringSocialConfigure extends SpringSocialConfigurer{

    private String filterProcessesUrl;

    public CustomerSpringSocialConfigure(String filterProcessesUrl){
        this.filterProcessesUrl = filterProcessesUrl;
    }
    //改变object处理的url
    @Override
    protected <T> T postProcess(T object) {
        SocialAuthenticationFilter filter = (SocialAuthenticationFilter)super.postProcess(object);
        filter.setFilterProcessesUrl(filterProcessesUrl);
        return (T)filter;
    }
}
