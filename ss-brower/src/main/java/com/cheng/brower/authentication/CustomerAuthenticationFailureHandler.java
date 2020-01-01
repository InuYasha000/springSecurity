package com.cheng.brower.authentication;

import com.cheng.core.common.CoreSecurityProperties;
import com.cheng.core.common.Response;
import com.cheng.core.model.enums.LoginResponseType;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Auther: cheng
 * @Date: 2019/12/26 18:25
 * @Description:
 */
@Slf4j
@Component("customerAuthenticationFailureHandler")
public class CustomerAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler{
    @Resource
    private ObjectMapper objectMapper;

    @Resource
    private CoreSecurityProperties coreSecurityProperties;
    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException{
        log.info("登录失败");
        if(LoginResponseType.JSON.equals(coreSecurityProperties.getBrower().getLoginType())){
            httpServletResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            httpServletResponse.getWriter().write(objectMapper.writeValueAsString(new Response(e.getMessage())));
        }else {
            super.onAuthenticationFailure(httpServletRequest,httpServletResponse,e);
        }

    }
}
