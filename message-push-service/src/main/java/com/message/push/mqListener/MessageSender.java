package com.message.push.mqListener;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * @Author： MikeLiubo
 * Description：
 * @date Create on 2020/4/23 19:11
 **/
public interface MessageSender {

    /**
     * 订单更新的msgCode：订单支付完成 paySuccess
     */
    String MSG_HEADER_PAY_FOR_ORDER = "payForOrder";
    @Output(MSG_HEADER_PAY_FOR_ORDER)
    MessageChannel orderFromAll();

}
