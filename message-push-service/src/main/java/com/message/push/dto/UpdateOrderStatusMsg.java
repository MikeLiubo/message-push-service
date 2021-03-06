package com.message.push.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @Author： MikeLiubo
 * Description：
 * @date Create on 2020/4/30 15:04
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class UpdateOrderStatusMsg {
    String orderId;
    Integer orderSubState;
}
