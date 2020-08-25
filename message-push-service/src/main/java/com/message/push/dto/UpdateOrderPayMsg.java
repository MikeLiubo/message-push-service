package com.message.push.dto;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author： MikeLiubo
 * Description：
 * @date Create on 2020/4/24 14:11
 **/
@Data
@Accessors(chain = true)
public class UpdateOrderPayMsg {

    private String orderId;
    private String payWay;
    private long payTime;
    private String payNo;
    private String memberId;
    private String reciverName;
    private String reciverPhone;
    private String orderKeywords;

}
