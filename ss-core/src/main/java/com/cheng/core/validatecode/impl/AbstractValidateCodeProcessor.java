package com.cheng.core.validatecode.impl;

import com.cheng.core.validatecode.ValidateCode;
import com.cheng.core.validatecode.ValidateCodeGenerator;
import com.cheng.core.validatecode.ValidateCodeProcessor;
import org.apache.commons.lang.StringUtils;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.context.request.ServletWebRequest;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import java.util.Map;

/**
 * @Auther: cheng
 * @Date: 2019/12/29 14:04
 * @Description:
 */
public abstract class AbstractValidateCodeProcessor<C extends ValidateCode> implements ValidateCodeProcessor{
    
    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();
    /**
     *  
     * @Description spring在启动时会去查找所有ValidateCodeGenerator的实现，并构建成一个Map,bean的名字是key
     * @author Cheng
     * @date 2019/12/29
     */
    
    @Resource
    private Map<String,ValidateCodeGenerator> validateCodeGeneratorMap;
    
    @Override
    public void create(ServletWebRequest request) throws Exception{
        //生成
        //spring常见开发方法,依赖搜索
        C validateCode =  generator(request);
        //保存
        save(request,validateCode);
        //发送
        send(request,validateCode);
    }

    private void save(ServletWebRequest request, C validateCode){
        sessionStrategy.setAttribute(request,SESSION_KEY_PREFIX+"_"+getProcessorType(request).toUpperCase(), validateCode);
    }


    /**
     *  
     * @Description 生成验证码
     * @author Cheng
     * @date 2019/12/29
     */
    
    private C generator(ServletWebRequest request){
        //根据请求后缀来匹配要获取的validateCodeGenerator
        String type = getProcessorType(request);
        ValidateCodeGenerator validateCodeGenerator = validateCodeGeneratorMap.get(type+"Generator");
        return (C) validateCodeGenerator.createCode(request);
    }

    /**
     *  
     * @Description 根据发送请求的url的后缀来获取验证码的类型
     * @author Cheng
     * @date 2019/12/29
     */
    
    private String getProcessorType(ServletWebRequest request){
        return StringUtils.substringAfter(request.getRequest().getRequestURI(),"/createCode/");
    };

    protected abstract void send(ServletWebRequest request,C validateCode) throws Exception;
}
