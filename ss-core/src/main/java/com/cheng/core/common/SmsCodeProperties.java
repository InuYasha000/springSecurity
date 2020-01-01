package com.cheng.core.common;

import lombok.Data;

/**
 * @Auther: cheng
 * @Date: 2019/12/29 13:51
 * @Description:
 */
@Data
public class SmsCodeProperties {

    private int length = 6 ;
    private int expireTime = 60;

    private String url ;
}
