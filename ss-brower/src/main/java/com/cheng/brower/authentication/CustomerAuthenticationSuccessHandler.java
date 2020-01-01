package com.cheng.brower.authentication;

import com.cheng.core.common.CoreSecurityProperties;
import com.cheng.core.model.enums.LoginResponseType;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Auther: cheng
 * @Date: 2019/12/26 16:49
 * @Description:
 */
@Component("customerAuthenticationSuccessHandler")
@Slf4j
public class CustomerAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler{

    //springmvc启动的时候自动注册
    @Resource
    private ObjectMapper objectMapper;

    @Resource
    private CoreSecurityProperties coreSecurityProperties;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication)  throws IOException, ServletException{
        log.info("登录成功");
        if(coreSecurityProperties.getBrower().getLoginType().equals(LoginResponseType.JSON)){
            httpServletResponse.setContentType("application/json;charset=UTF-8");
            httpServletResponse.getWriter().write(objectMapper.writeValueAsString(authentication));
        }else {
            super.onAuthenticationSuccess(httpServletRequest,httpServletResponse,authentication);
        }
    }
}
