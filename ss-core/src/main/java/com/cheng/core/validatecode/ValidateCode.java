package com.cheng.core.validatecode;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @Auther: cheng
 * @Date: 2019/12/29 13:33
 * @Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValidateCode {
    //随机数,存储到session
    private String code;
    //过期时间
    private LocalDateTime expireTime;
    public boolean isExpired(){
        return LocalDateTime.now().isAfter(expireTime);
    }
    public ValidateCode(String code, int expireTime){
        this.code = code;
        this.expireTime = LocalDateTime.now().plusSeconds(expireTime);
    }
}
