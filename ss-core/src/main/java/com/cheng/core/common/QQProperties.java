package com.cheng.core.common;

import lombok.Data;
import org.springframework.boot.autoconfigure.social.SocialProperties;

/**
 * @Auther: cheng
 * @Date: 2019/12/27 14:46
 * @Description:
 */
@Data
public class QQProperties extends SocialProperties{
    private String providerId = "qq";
}
