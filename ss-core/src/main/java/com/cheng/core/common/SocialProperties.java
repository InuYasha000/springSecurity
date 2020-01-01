package com.cheng.core.common;

import lombok.Data;

/**
 * @Auther: cheng
 * @Date: 2019/12/27 14:50
 * @Description:
 */
@Data
public class SocialProperties {
    private QQProperties qq = new QQProperties();

    private String filterProcessesUrl = "/auth";
}
