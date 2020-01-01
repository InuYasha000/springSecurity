package com.cheng.core.validatecode;

import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.ServletRequest;

/**
 * @Auther: cheng
 * @Date: 2019/12/29 14:01
 * @Description:
 */
public interface ValidateCodeProcessor {

    /**
     *  
     * @Description 验证码放入session的前缀
     * @author Cheng
     * @date 2019/12/29
     */
    
    String SESSION_KEY_PREFIX = "SESSION_KEY_FOR_CODE";

    /**
     *  
     * @Description 创建校验码
     * @author Cheng
     * @date 2019/12/29
     */
    //封装请求的响应ServletRequest ,request,response都可以放到这个里面
    public void create(ServletWebRequest request) throws Exception;
}
