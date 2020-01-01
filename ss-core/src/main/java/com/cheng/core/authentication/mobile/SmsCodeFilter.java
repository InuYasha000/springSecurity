package com.cheng.core.authentication.mobile;

import com.cheng.core.common.CoreSecurityProperties;
import com.cheng.core.exception.ValidateCodeException;
import com.cheng.core.validatecode.ValidateCode;
import com.cheng.core.validatecode.ValidateCodeController;
import com.cheng.core.validatecode.ValidateCodeProcessor;
import com.cheng.core.validatecode.image.ImageCode;
import lombok.Data;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
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
import java.util.HashSet;
import java.util.Set;

/**
 * @Auther: cheng
 * @Date: 2019/12/30 17:16
 * @Description:
 */
@Data
public class SmsCodeFilter extends OncePerRequestFilter implements InitializingBean {

    private AuthenticationFailureHandler authenticationFailureHandler;

    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    private CoreSecurityProperties coreSecurityProperties;

    private Set<String> urls = new HashSet<>();

    private AntPathMatcher antPathMatcher = new AntPathMatcher();
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain)throws ServletException, IOException {
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
        ValidateCode validateCodeInSession = (ValidateCode) sessionStrategy.getAttribute(servletWebRequest, ValidateCodeProcessor.SESSION_KEY_PREFIX+"_SMSCODE");
        //页面parameter是imageCode
        String codeInRequest = ServletRequestUtils.getStringParameter(servletWebRequest.getRequest(),"smsCode");

        if(StringUtils.isBlank(codeInRequest)){
            throw new ValidateCodeException("短信验证码的值不能为空");
        }
        if(codeInRequest==null){
            throw new ValidateCodeException("短信验证码不存在");
        }
        if(validateCodeInSession.isExpired()){
            sessionStrategy.removeAttribute(servletWebRequest, ValidateCodeController.SESSION_KEY);
            throw new ValidateCodeException("短信验证码已过期");
        }
        if(!StringUtils.equals(validateCodeInSession.getCode(),codeInRequest)){
            throw new ValidateCodeException("短信验证码不匹配");
        }
        sessionStrategy.removeAttribute(servletWebRequest, ValidateCodeController.SESSION_KEY);
    }

    @Override
    public void afterPropertiesSet() throws ServletException {
        super.afterPropertiesSet();
        //逗号切割
        if(!StringUtils.isBlank(coreSecurityProperties.getCode().getSms().getUrl())){
            String[] configurls = StringUtils.splitByWholeSeparatorPreserveAllTokens(coreSecurityProperties.getCode().getSms().getUrl(),",");
            for (String str:configurls){
                urls.add(str);
            }
        }
        urls.add("/authentication/mobile");
    }
}

