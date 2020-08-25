package com.message.push;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author MikeLiubo
 * @description
 * @time 2020/04/13 19:29
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "cook.admin.token")
public class TokenConfig {

    String httpType;
    String host;
    String port;
    String AdminUsername;
    String AdminPassword;
}
