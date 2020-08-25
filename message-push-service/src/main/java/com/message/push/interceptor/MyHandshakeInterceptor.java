package com.message.push.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

/**
 * @Author： MikeLiubo
 * Description：
 * @date Create on 2020/4/23 16:47
 **/
@Component
@Slf4j
public class MyHandshakeInterceptor implements HandshakeInterceptor {
    /**
     * 握手之前，若返回false，则不建立链接
     *
     * @param request
     * @param response
     * @param wsHandler
     * @param attributes
     * @return
     * @throws Exception
     */
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse
            response, WebSocketHandler wsHandler, Map<String, Object> attributes) {
        //将用户id放入socket处理器的会话(WebSocketSession)中
        if (null == request.getPrincipal().getName())
            return false;
        log.info("开始握手：name = {}-- request = {}—— response = {}——wsHandler = {}——attributes = {}", request.getPrincipal().getName(), request, response, wsHandler, attributes);
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse
            response, WebSocketHandler wsHandler, Exception exception) {
        log.info("握手成功：{}——{}——{}——{}", request, response, wsHandler, exception);
    }
}
