package com.cheng.core.config;

import com.cheng.core.common.CoreSecurityProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Auther: cheng
 * @Date: 2019/12/26 14:38
 * @Description:
 */
@Configuration
@EnableConfigurationProperties(CoreSecurityProperties.class)
public class SecurityConfig {

}
