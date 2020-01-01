package com.cheng.core.validatecode;

import com.cheng.core.common.CoreSecurityProperties;
import com.cheng.core.exception.ValidateCodeException;
import com.cheng.core.validatecode.image.ImageCode;
import lombok.Data;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @Auther: cheng
 * @Date: 2019/12/28 13:59
 * @Description:
 */
//OncePerRequestFilter保证每次只被调一次
@Data
//实现InitializingBean是在其他参数都完成后,再初始化url的值
@Component("validateCodeFilter")
//启动时将配置信息读取到mao里面
public class ValidateCodeFilter extends OncePerRequestFilter implements InitializingBean{

    private AuthenticationFailureHandler authenticationFailureHandler;

    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    private CoreSecurityProperties coreSecurityProperties;

    private Set<String> urls = new HashSet<>();

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    private Map<String,ValidateCodeType> urlMap = new HashMap<>();
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain)throws ServletException, IOException{
        boolean flag = false;
        for (String url:urls){
            if(antPathMatcher.match(url,httpServletRequest.getRequestURI())){
                flag = true;
            }
        }
        if(flag){
            try {
                validate(new ServletWebRequest(httpServletRequest));
            }catch (ValidateCodeException e){
                logger.error("ValidateCodeFilter doFilterInternal error:{}",e);
                authenticationFailureHandler.onAuthenticationFailure(httpServletRequest,httpServletResponse,e);
                return;
            }
        }
        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }

    private void validate(ServletWebRequest servletWebRequest) throws ServletRequestBindingException {
        //从sessionStrategy拿出ImageCode,生成验证码时放进去的
        ImageCode codeInSession = (ImageCode) sessionStrategy.getAttribute(servletWebRequest, ValidateCodeController.SESSION_KEY);
        //页面parameter是imageCode
        String codeInRequest = ServletRequestUtils.getStringParameter(servletWebRequest.getRequest(),"imageCode");

        if(StringUtils.isBlank(codeInRequest)){
            throw new ValidateCodeException("验证码的值不能为空");
        }
        if(codeInRequest==null){
            throw new ValidateCodeException("验证码不存在");
        }
        if(codeInSession.isExpired()){
            sessionStrategy.removeAttribute(servletWebRequest, ValidateCodeController.SESSION_KEY);
            throw new ValidateCodeException("验证码已过期");
        }
        if(!StringUtils.equals(codeInSession.getCode(),codeInRequest)){
            throw new ValidateCodeException("验证码不匹配");
        }
        sessionStrategy.removeAttribute(servletWebRequest, ValidateCodeController.SESSION_KEY);
    }

    @Override
    public void afterPropertiesSet() throws ServletException {
        super.afterPropertiesSet();
        //逗号切割
        if(!StringUtils.isBlank(coreSecurityProperties.getCode().getImage().getUrl())){
            String[] configurls = StringUtils.splitByWholeSeparatorPreserveAllTokens(coreSecurityProperties.getCode().getImage().getUrl(),",");
            for (String str:configurls){
                urls.add(str);
            }
        }
        urls.add("/authentication/form");
    }

    /**
     *  
     * @Description 根据系统配置中的需要校验验证码的url 根据校验的类型放入map
     * @author Cheng
     * @date 2019/12/31
     */
    
    protected void addUrlToMap(String urlString,ValidateCodeType type){
        if(!StringUtils.isBlank(urlString)){
            String[] urls = StringUtils.splitByWholeSeparatorPreserveAllTokens(urlString,",");
            for (String str:urls){
                urlMap.put(str,type);
            }
        }
    }
}
