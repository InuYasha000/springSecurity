package com.cheng.core.validatecode.sms;

import com.cheng.core.validatecode.ValidateCode;
import com.cheng.core.validatecode.image.ImageCodeProcessor;
import com.cheng.core.validatecode.impl.AbstractValidateCodeProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;

/**
 * @Auther: cheng
 * @Date: 2019/12/29 14:12
 * @Description:
 */
@Slf4j
@Component("smsCodeProcessor")
public class SmsCodeProcessor extends AbstractValidateCodeProcessor<ValidateCode> {

    @Resource
    private ISmsCodeSender smsCodeSender;
    @Override
    protected void send(ServletWebRequest request, ValidateCode validateCode) throws Exception{
        String paramName = "mobile";
        String mobile = ServletRequestUtils.getRequiredStringParameter(request.getRequest(), paramName);
        smsCodeSender.send(mobile, validateCode.getCode());
    }
}
