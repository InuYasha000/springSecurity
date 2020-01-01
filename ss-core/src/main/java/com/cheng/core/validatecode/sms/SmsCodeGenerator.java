package com.cheng.core.validatecode.sms;


import com.cheng.core.common.CoreSecurityProperties;
import com.cheng.core.validatecode.ValidateCode;
import com.cheng.core.validatecode.ValidateCodeGenerator;
import lombok.Data;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import javax.annotation.Resource;

/**
 * @Auther: cheng
 * @Date: 2019/12/29 13:29
 * @Description:
 */
@Data
@Component("smsCodeGenerator")
public class SmsCodeGenerator implements ValidateCodeGenerator {

    @Resource
    private CoreSecurityProperties coreSecurityProperties;
    @Override
    public ValidateCode createCode(ServletWebRequest request) {
        String code = RandomStringUtils.randomNumeric(coreSecurityProperties.getCode().getSms().getLength());
        return new ValidateCode(code,coreSecurityProperties.getCode().getSms().getExpireTime());
    }
}
