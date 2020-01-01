package com.cheng.core.common;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Auther: cheng
 * @Date: 2019/12/26 14:33
 * @Description:
 */
@Data
@ConfigurationProperties(prefix = "com.cheng")
public class CoreSecurityProperties {
    private BrowerProperties brower = new BrowerProperties();

    private SocialProperties social = new SocialProperties();

    private ValidateCodeProperties Code = new ValidateCodeProperties();
}
