package com.cheng.core.validatecode.image;

import com.cheng.core.common.CoreSecurityProperties;
import com.cheng.core.validatecode.ValidateCode;
import com.cheng.core.validatecode.ValidateCodeGenerator;
import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import javax.annotation.Resource;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * @Auther: cheng
 * @Date: 2019/12/28 16:06
 * @Description:
 */
@Data
@Component("imageCodeGenerator")
public class ImageCodeGenerator implements ValidateCodeGenerator {

    @Resource
    private CoreSecurityProperties coreSecurityProperties;

    @Override
    public ValidateCode createCode(ServletWebRequest request) {
        //图片长
        int width = ServletRequestUtils.getIntParameter(request.getRequest(),"width",coreSecurityProperties.getCode().getImage().getWidth());
        //图片高
        int height = ServletRequestUtils.getIntParameter(request.getRequest(),"height",coreSecurityProperties.getCode().getImage().getHeight());
        //生成图片对象
        BufferedImage image = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);

        Graphics g = image.getGraphics();

        //生成随机条纹
        Random random = new Random();

        g.setColor(getRandColor(200,250));
        g.fillRect(0,0,width,height);
        g.setFont(new Font("Times New Roman",Font.ITALIC,20));
        g.setColor(getRandColor(160,200));
        for (int i = 0; i < 155; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int xl = random.nextInt(12);
            int yl = random.nextInt(12);
            g.drawLine(x,y,x+xl,y+yl);
        }

        //生成四位随机数
        String sRand = "";
        for (int i = 0; i < coreSecurityProperties.getCode().getImage().getLength(); i++) {
            String rand = String.valueOf(random.nextInt(10));
            sRand+=rand;
            g.setColor(new Color(20+random.nextInt(110),20+random.nextInt(110),20+random.nextInt(110)));
            g.drawString(rand,13*i+6,16);
        }
        g.dispose();

        //60秒时间
        return new ImageCode(image,sRand,coreSecurityProperties.getCode().getImage().getExpireTime());
    }

    /**
     *
     * @Description 生成随机背景条纹
     * @author Cheng
     * @date 2019/12/28
     */

    private Color getRandColor(int fc, int bc) {
        Random random = new Random();
        if(fc>255){
            fc = 255;
        }
        if(bc>255){
            bc = 255;
        }
        int r = fc + random.nextInt(bc-fc);
        int g = fc + random.nextInt(bc-fc);
        int b = fc + random.nextInt(bc-fc);
        return new Color(r,g,b);
    }
}
