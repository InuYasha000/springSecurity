package com.cheng.core.validatecode.image;

import com.cheng.core.validatecode.ValidateCode;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

/**
 * @Auther: cheng
 * @Date: 2019/12/28 12:59
 * @Description:
 */
@NoArgsConstructor
@Data
public class ImageCode extends ValidateCode {

    //展现给用户观看
    private BufferedImage image;
    //随机数,存储到session
    private String code;
    //过期时间
    private LocalDateTime expireTime;
    public boolean isExpired(){
        return LocalDateTime.now().isAfter(expireTime);
    }
    public ImageCode(BufferedImage image,String code,int expireTime){
        super(code,expireTime);
        this.image = image;
        this.code = code;
        this.expireTime = LocalDateTime.now().plusSeconds(expireTime);
    }
}
