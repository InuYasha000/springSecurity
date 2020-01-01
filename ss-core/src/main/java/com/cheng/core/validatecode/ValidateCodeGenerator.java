package com.cheng.core.validatecode;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * @Auther: cheng
 * @Date: 2019/12/28 16:05
 * @Description:
 */
public interface ValidateCodeGenerator {

    public ValidateCode createCode(ServletWebRequest request);
}
