package com.cheng.core.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @Auther: cheng
 * @Date: 2019/12/28 14:06
 * @Description:
 */
//身份认证中抛出异常的基类AuthenticationException
public class ValidateCodeException extends AuthenticationException{
    public ValidateCodeException(String msg){
        super(msg);
    }
}
