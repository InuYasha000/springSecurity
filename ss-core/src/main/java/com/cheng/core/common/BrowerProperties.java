package com.cheng.core.common;

import com.cheng.core.model.enums.LoginResponseType;
import lombok.Data;

/**
 * @Auther: cheng
 * @Date: 2019/12/26 14:34
 * @Description:
 */
@Data
public class BrowerProperties {
    private String loginPage = "/demo-login.html";

    private LoginResponseType LoginType = LoginResponseType.JSON;

    private int rememberMeTime = 3600;
}
