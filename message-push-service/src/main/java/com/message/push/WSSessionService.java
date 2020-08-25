package com.message.push;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @Author： MikeLiubo
 * Description：
 * @date Create on 2020/4/23 10:40
 **/
@Service
@Slf4j
@Data
public class WSSessionService {

    Map<WebSocketSession, String> session2UsernameMap = new ConcurrentHashMap<>();

    Map<WebSocketSession, String> session2StoreIdMap = new ConcurrentHashMap<>();

    Map<String, String> storeId2UsernameMap = new ConcurrentHashMap<>();

    CopyOnWriteArraySet<WebSocketSession> SESSIONS = new CopyOnWriteArraySet<>();

    public void addSessions(WebSocketSession session) {
        SESSIONS.add(session);
        log.info("wsSessionService & Session:{} " , SESSIONS);
    }

    public void addSession(WebSocketSession session, String username) {
        session2UsernameMap.put(session, username);
    }

    public void addSession2StoreId(WebSocketSession session, String storeId) {
        session2StoreIdMap.put(session, storeId);
    }
    public void removeSession2StoreId(WebSocketSession session) {
        session2StoreIdMap.remove(session);
    }

    public void removeSession(WebSocketSession session) {
        session2UsernameMap.remove(session);
    }

    public void removeStoreId2Username(String storeId) {
        storeId2UsernameMap.remove(storeId);
    }

    public void addStoreId2username(String storeId, String username) {
        storeId2UsernameMap.put(storeId, username);
    }

}
