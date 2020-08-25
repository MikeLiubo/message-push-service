package com.message.push.pojo;

import com.message.push.common.entity.BaseJpaEntity;
import lombok.Data;

import javax.persistence.Entity;
import java.util.Date;

/**
 * @Author： MikeLiubo
 * Description：
 * @date Create on 2020/4/23 10:42
 **/
@Data
@Entity
public class Message extends BaseJpaEntity {
    String sender;
    String receiver;
    String playLoad;
    Date pushTime;
    boolean broadcasting = false;
    boolean beRead = false;
    String orderId;
    String storeId;
}
