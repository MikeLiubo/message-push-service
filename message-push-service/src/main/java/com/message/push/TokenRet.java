package com.message.push;

import lombok.Data;

/**
 * @author MikeLiubo
 * @description
 * @time 2020/04/13 19:25
 */
@Data
public class TokenRet {

    String access_token;
    String refresh_token;
    String scope;
    String token_type;
    int expires_in;
    String jti;
}
