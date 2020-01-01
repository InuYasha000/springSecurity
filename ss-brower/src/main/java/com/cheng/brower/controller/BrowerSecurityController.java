package com.cheng.brower.controller;

import com.cheng.core.common.Response;
import com.cheng.core.common.CoreSecurityProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Auther: cheng
 * @Date: 2019/12/25 17:18
 * @Description:
 */
@RestController
@Slf4j
public class BrowerSecurityController {

    //请求缓存
    private RequestCache requestCache = new HttpSessionRequestCache();

    @Resource
    private CoreSecurityProperties coreSecurityProperties;

    //跳转
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    /*@GetMapping(value = "/user")
    public String hello(){
        return "hello";
    }*/

    @GetMapping("/authentication/require")
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public Response require(HttpServletRequest request, HttpServletResponse response) {
        log.info("BrowerSecurityController require begin");
        try{
            SavedRequest savedRequest = requestCache.getRequest(request,response);
            if(savedRequest!=null){
                String targetUrl = savedRequest.getRedirectUrl();
                log.info("跳转链接:{}",targetUrl);
                if(StringUtils.endsWithIgnoreCase(targetUrl,"html")){
                    redirectStrategy.sendRedirect(request,response, coreSecurityProperties.getBrower().getLoginPage());
                }
            }
        }catch (Exception e){
            log.error("BrowerSecurityController require error",e.getMessage(),e);
        }
        return new Response("需要身份认证");
    }

    @PostMapping("/authentication/form")
    public String authentication() {
        return "hello";
    }

    @GetMapping("/me")
    public Object getCurrentUser(@AuthenticationPrincipal UserDetails user) {
        return user;
    }
}
