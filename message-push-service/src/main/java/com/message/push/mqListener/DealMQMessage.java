package com.message.push.mqListener;

import com.alibaba.fastjson.JSONObject;
import com.message.push.WSSessionService;
import com.message.push.dto.OrderDetailDto;
import com.message.push.dto.OrderPaySuccessMessage;
import com.message.push.pojo.Message;
import com.message.push.service.AdminTokenService;
import com.message.push.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @Author： MikeLiubo
 * Description：
 * @date Create on 2020/4/24 19:14
 **/
@Slf4j
@Service
public class DealMQMessage {
    @Autowired
    private AdminTokenService tokenService;
    @Autowired
    private MessageService messageService;
    @Autowired
    private WSSessionService wsSessionService;

    private final static int ORDER_TYPE = 0;

    /**
     * 处理监听明天吃成功支付订单 下单消息头：payForOrder 消息头：paySuccess
     * 仅推送明天吃的订单
     **/
    @Transactional
    @StreamListener(target = PushMessageListener.ORDER_FROM_ALL, condition = "headers['msgCode']=='paySuccess'")
    public void dealPaySuccessOrderGroup(OrderPaySuccessMessage msg) {
        String orderId = msg.getOrderId();
        // 调用订单接口查询订单信息
        OrderDetailDto orderDto = viewOrderByRole(orderId, tokenService.getAdminUserTokenBearer());
        String storeId = orderDto.getOrderDetailDto().getStoreId();
        Message message = new Message();
        message.setOrderId(orderId);
        message.setStoreId(storeId);
        int order_type = orderDto.getOrderDetailDto().getOrderType();
        CopyOnWriteArraySet<WebSocketSession> sessions = wsSessionService.getSESSIONS();
        if (order_type == ORDER_TYPE) {
            for (WebSocketSession s : sessions) {
                try {
                    if (null != s && s.isOpen()) {
                        if (storeId.equals(wsSessionService.getSession2StoreIdMap().get(s))) {
                            JSONObject toClient = new JSONObject();
                            toClient.put("type", 0);
                            toClient.put("data", orderId);
                            s.sendMessage(new TextMessage(JSONObject.toJSONString(toClient)));
                            message.setBeRead(true);
                            log.info("推送服务：{}", JSONObject.toJSONString(toClient));
                            LocalDateTime localDateTime = LocalDateTime.now();
                            message.setPushTime(Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant()));
                            log.info("推送订单成功支付消息！");
                            message.setPlayLoad("paySuccess");
                            messageService.save(message);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    private OrderDetailDto viewOrderByRole(String orderId, String token) {
        // 查询订单详情 调用第三方服务的接口查询订单详情

        OrderDetailDto detailDto = new OrderDetailDto();
        return detailDto;
    }

}
