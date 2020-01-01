package com.cheng.core.validatecode.image;

import com.cheng.core.validatecode.ValidateCode;
import com.cheng.core.validatecode.impl.AbstractValidateCodeProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;

/**
 * @Auther: cheng
 * @Date: 2019/12/29 14:11
 * @Description:
 */
@Slf4j
@Component("imageCodeProcessor")
public class ImageCodeProcessor extends AbstractValidateCodeProcessor<ImageCode>{
    @Override
    protected void send(ServletWebRequest request, ImageCode validateCode) throws Exception{
        ImageIO.write(validateCode.getImage(), "JPEG", request.getResponse().getOutputStream());
    }
}
