package com.message.push.service;

import com.alibaba.fastjson.JSONObject;
import com.message.push.mqListener.MessageSender;
import com.message.push.pojo.Message;
import com.message.push.WSSessionService;
import com.message.push.dao.IMessageDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @Author： MikeLiubo
 * Description：
 * @date Create on 2020/4/25 9:39
 **/
@Service
@Slf4j
public class MessageService {

    @Autowired
    private IMessageDao messageDao;
    @Autowired
    private MessageSender sender;
    @Autowired
    private WSSessionService wsSessionService;


    @Transactional(rollbackFor = Exception.class)
    public void save(Message message) {
        this.messageDao.save(message);
    }

    public List<Message> findMessageList(String storeId) {
        return this.messageDao.findAllByStoreIdAndBeReadIsFalse(storeId);
    }

    public void sendMQ() {
        for (int i = 0; i < 10000; i++) {
            String storeId = "392567006278885376";
            CopyOnWriteArraySet<WebSocketSession> sessions = wsSessionService.getSESSIONS();
            for (WebSocketSession s : sessions) {
                try {
                    if (null != s && s.isOpen()) {
                        if (storeId.equals(wsSessionService.getSession2StoreIdMap().get(s))) {
                            JSONObject toClient = new JSONObject();
                            toClient.put("type:", 0);
                            toClient.put("data:", i);
                            s.sendMessage(new TextMessage(JSONObject.toJSONString(toClient)));
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }


}
