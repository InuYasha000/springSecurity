package com.cheng.core.common;

import lombok.Data;
import org.springframework.boot.autoconfigure.social.SocialProperties;

/**
 * @Auther: cheng
 * @Date: 2020/1/2 18:16
 * @Description:
 */
@Data
public class WXProperties extends SocialProperties{

    private String providerId = "wx";
}
