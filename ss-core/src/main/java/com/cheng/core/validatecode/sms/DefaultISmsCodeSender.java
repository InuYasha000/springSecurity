package com.cheng.core.validatecode.sms;

import lombok.extern.slf4j.Slf4j;

/**
 * @Auther: cheng
 * @Date: 2019/12/29 13:41
 * @Description:
 */
@Slf4j
public class DefaultISmsCodeSender implements ISmsCodeSender {
    @Override
    public void send(String mobile, String code) {
        log.info("想手机发送验证码Mobile:{},code:{}",mobile,code);
    }
}
