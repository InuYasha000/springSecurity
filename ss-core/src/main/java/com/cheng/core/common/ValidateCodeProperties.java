package com.cheng.core.common;

import lombok.Data;

/**
 * @Auther: cheng
 * @Date: 2019/12/28 15:16
 * @Description:
 */
@Data
public class ValidateCodeProperties {

    private ImageCodeProperties image = new ImageCodeProperties();

    private SmsCodeProperties sms = new SmsCodeProperties();
}
