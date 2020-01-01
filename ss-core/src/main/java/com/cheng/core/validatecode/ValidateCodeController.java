package com.cheng.core.validatecode;

import com.cheng.core.validatecode.image.ImageCode;
import com.cheng.core.validatecode.image.ImageCodeGenerator;
import com.cheng.core.validatecode.sms.ISmsCodeSender;
import com.cheng.core.validatecode.sms.SmsCodeGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @Auther: cheng
 * @Date: 2019/12/28 13:04
 * @Description:
 */
@RestController
@Slf4j
public class ValidateCodeController {

    public static final String SESSION_KEY = "SESSION_KEY_IMAGE_CODE";

    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    @Resource
    private ImageCodeGenerator imageCodeGenerator;

    @Resource
    private SmsCodeGenerator smsCodeGenerator;

    @Resource
    private ISmsCodeSender smsCodeSender;

    @Resource
    private Map<String,ValidateCodeProcessor> validateCodeProcessorMap;
    //1：生成随机数
    //2：将随机数存到session中
    //3：在将生成的图片写到接口的响应中
    @GetMapping("/createCode/imageCode")
    public void createImageCode(HttpServletRequest request, HttpServletResponse response){
        try{
            ImageCode imageCode =(ImageCode) imageCodeGenerator.createCode(new ServletWebRequest(request));
            //第一个参数把请求放进去，sessionStrategy从请求拿session
            sessionStrategy.setAttribute(new ServletWebRequest(request),SESSION_KEY,imageCode);
            ImageIO.write(imageCode.getImage(),"JPEG",response.getOutputStream());
            log.info("ImageCodeController createCode begin");
        }catch (Exception e){
            log.error("ImageCodeController createCode error:{}",e.getMessage(),e);
        }
    }

    /*@GetMapping("/createCode/smsCode")
    public void createSmsCode(HttpServletRequest request, HttpServletResponse response){
        try{
            ValidateCode validateCode = smsCodeGenerator.createCode(new ServletWebRequest(request));
            //第一个参数把请求放进去，sessionStrategy从请求拿session
            sessionStrategy.setAttribute(new ServletWebRequest(request),SESSION_KEY,validateCode);
            String mobile = ServletRequestUtils.getRequiredStringParameter(request,"mobile");
            log.info("ImageCodeController createCode begin");
            smsCodeSender.send(mobile,validateCode.getCode());
        }catch (Exception e){
            log.error("ImageCodeController createCode error:{}",e.getMessage(),e);
        }
    }*/

    @GetMapping("/createCode/{type}")
    public void createCode(HttpServletRequest request, HttpServletResponse response, @PathVariable String type){
        try {
            log.info("ImageCodeController createCode begin");
            validateCodeProcessorMap.get(type+"Processor").create(new ServletWebRequest(request,response));
        }catch (Exception e){
            log.error("ImageCodeController createCode error:{}",e.getMessage(),e);
        }

    }
}

