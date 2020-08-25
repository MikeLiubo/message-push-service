package com.message.push.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @Author： MikeLiubo
 * Description：
 * @date Create on 2020/4/25 16:32
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class OrderDetailDto {

    long currentTime;
    int payOTMinutes;
    ViewOrderDetailDto orderDetailDto;
    List<OrderGoods> orderGoods;
}
