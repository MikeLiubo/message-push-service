package com.message.push;

import com.alibaba.fastjson.JSONObject;
import com.message.push.pojo.Message;
import com.message.push.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.net.URI;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;


/**
 * @Author： MikeLiubo
 * Description：
 * @date Create on 2020/4/23 10:18
 **/
@Slf4j
@Component
public class MyHandler extends TextWebSocketHandler {

    @Autowired
    private MessageService messageService;

    private WebSocketSession wsSession;

    @Autowired
    private WSSessionService wsSessionService;

    /**
     * 处理客户端发来的消息,并在客户登录时推送订单消息
     *
     * @param session
     * @param message
     * @throws IOException
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void handleTextMessage(WebSocketSession session, TextMessage message)
            throws IOException {
        String storeId = wsSessionService.getSession2StoreIdMap().get(session);
        List<Message> beReadFalse = messageService.findMessageList(storeId);
        for (Message m : beReadFalse) {
            // web登录后，主动send message，服务端响应将未推送消息推送
            m.setBeRead(true);
            LocalDateTime localDateTime = LocalDateTime.now();
            m.setPushTime(Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant()));
            messageService.save(m);
            JSONObject toClient = new JSONObject();
            toClient.put("type", 0);
            toClient.put("data", m.getOrderId());
            session.sendMessage(new TextMessage(JSONObject.toJSONString(toClient)));
            log.info("您的店铺{}有新的订单{}，请及时处理！{}", storeId, m.getOrderId(), JSONObject.toJSONString(toClient));
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        this.wsSession = session;
        log.info("建立连接：wsSession:{}", wsSession);
        /** 从session的uri中拿storeId **/
        URI uri = session.getUri();
        String path = uri.toString();
        String url[] = StringUtils.split(path, "/");
        if (url.length != 3) {
            session.close();
            log.error("客户端uri有误！{}", session.getUri());
            return;
        }
        String username = url[1];
        String storeId = url[2];
        if (StringUtils.isBlank(storeId) || StringUtils.isBlank(username)) {
            session.close();
            log.error("客户端uri传入参数有误！{}", session.getUri());
            return;
        }
        wsSessionService.addSession(session, username);
        wsSessionService.addStoreId2username(storeId, username);
        wsSessionService.addSession2StoreId(session, storeId);
        wsSessionService.addSessions(session);
        // 响应建立连接
        session.sendMessage(new TextMessage("欢迎使用 catering-push-service 服务，即将为您推送客户成功下单消息!"));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        /** 断开连接时清除map中的客户端 **/
        wsSessionService.removeSession(session);
        String storeId = wsSessionService.getSession2StoreIdMap().get(session);
        wsSessionService.removeStoreId2Username(storeId);
        wsSessionService.removeSession2StoreId(session);
        wsSessionService.SESSIONS.remove(session);
        log.info("客户端{}断开连接!!! status = {}", session, status);
    }

    @Override
    public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable) throws Exception {
        if (webSocketSession.isOpen()) webSocketSession.close();
        log.error("transport error:", throwable.getMessage());
        wsSessionService.SESSIONS.remove(webSocketSession);
        wsSessionService.removeSession(webSocketSession);
        String storeId = wsSessionService.getSession2StoreIdMap().get(webSocketSession);
        wsSessionService.removeStoreId2Username(storeId);
        wsSessionService.removeSession2StoreId(webSocketSession);
        log.info("发生错误:  {}", throwable.getMessage());
    }

}
