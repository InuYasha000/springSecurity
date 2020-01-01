package com.cheng.core.validatecode.sms;

/**
 * @Auther: cheng
 * @Date: 2019/12/29 13:40
 * @Description:
 */
public interface ISmsCodeSender {
    public void send(String mobile,String code);
}
