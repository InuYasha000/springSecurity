package com.cheng.core.validatecode;

import com.cheng.core.common.CoreSecurityProperties;
import com.cheng.core.validatecode.image.ImageCodeGenerator;
import com.cheng.core.validatecode.sms.DefaultISmsCodeSender;
import com.cheng.core.validatecode.sms.ISmsCodeSender;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * @Auther: cheng
 * @Date: 2019/12/28 16:09
 * @Description:
 */
@Configuration
public class ValidateCodeBeanConfig {

    @Resource
    private CoreSecurityProperties coreSecurityProperties;
    //也可以ImageCodeGenerator @Configure
    //如果有更好的图形验证码代码直接实现ValidateCodeGenerator 并@Configure
    //这样做目的在于更好的方法出现的时候针对原先代码不是修改,而是增加代码
    @Bean
    @ConditionalOnMissingBean(name = "imageCodeGenerator")
    public ValidateCodeGenerator imageCodeGenerator(){
        ImageCodeGenerator imageCodeGenerator = new ImageCodeGenerator();
        imageCodeGenerator.setCoreSecurityProperties(coreSecurityProperties);
        return imageCodeGenerator;
    }

    @Bean
    @ConditionalOnMissingBean(ISmsCodeSender.class)
    public ISmsCodeSender smsCodeSender(){
        return new DefaultISmsCodeSender();
    }
}
