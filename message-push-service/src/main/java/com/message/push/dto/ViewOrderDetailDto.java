package com.message.push.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Author： liubo1
 * Description：
 * @date Create on 2020/8/25 16:32
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class ViewOrderDetailDto implements Serializable {
    private static final long serialVersionUID = -4602087486020543677L;
    String orderId;
    int orderType;
    String storeId;
}
