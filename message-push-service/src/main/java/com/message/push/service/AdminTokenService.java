package com.message.push.service;

import com.message.push.TokenConfig;
import com.message.push.TokenRet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

/**
 * @author MikeLiubo
 * @description
 * @time 2018/12/13 19:27
 */
@Component
@Slf4j
public class AdminTokenService {

    @Autowired
    private TokenConfig tokenConfig;
    @Autowired
    private RestTemplate restTemplate;

    private String adminToken;

    public String getAdminUserTokenBearer() {
        return "bearer " + getAdminToken();
    }


    private String getAdminToken() {
        if (org.springframework.util.StringUtils.isEmpty(adminToken)) {
            adminToken = login(tokenConfig.getAdminUsername(), tokenConfig.getAdminPassword());
        }
        return adminToken;
    }



    /**
     * 每一个小时获取一次token
     */
    @Scheduled(cron = "0 0 * * * ?")
    public void setToken() {
        adminToken = login(tokenConfig.getAdminUsername(), tokenConfig.getAdminPassword());
        log.info("push#login 定时获取token结束");
    }

    public String login(String username, String password) {

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.put("username", Collections.singletonList(username));
        map.put("password", Collections.singletonList(password));
        map.put("grant_type", Collections.singletonList("password"));
        map.put("scope", Collections.singletonList("read"));

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Authorization", "Basic c3NvLWdhdGV3aHgauuhyttfre7YGddhGV3");
        requestHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity<MultiValueMap> httpEntity = new HttpEntity<>(map, requestHeaders);
        String url = tokenConfig.getHttpType() + "://" + tokenConfig.getHost() + ":" + tokenConfig.getPort() +
                "/uaa/oauth/token";
        try {
            ResponseEntity<TokenRet> responseEntity = restTemplate.postForEntity
                    (url, httpEntity, TokenRet.class);
            return responseEntity.getBody().getAccess_token();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }
}
