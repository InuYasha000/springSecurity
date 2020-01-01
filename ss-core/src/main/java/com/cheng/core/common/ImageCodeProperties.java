package com.cheng.core.common;

import lombok.Data;

/**
 * @Auther: cheng
 * @Date: 2019/12/28 15:14
 * @Description:
 */
@Data
public class ImageCodeProperties extends SmsCodeProperties{

    public ImageCodeProperties(){
        setLength(4);
    }

    private int width = 67;
    private int height = 23;
    private String url ;
}
