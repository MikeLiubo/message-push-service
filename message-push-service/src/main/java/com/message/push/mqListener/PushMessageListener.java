package com.message.push.mqListener;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * @Author： MikeLiubo
 * Description：
 * @date Create on 2020/4/23 19:38
 **/
interface PushMessageListener {
    /**
     * 监听来自订单成功支付的通知 — 订单处理
     * */
   String ORDER_FROM_ALL = "messagePushGroup";

    @Input(ORDER_FROM_ALL)
    SubscribableChannel dealPaySuccessOrderGroup();
}
