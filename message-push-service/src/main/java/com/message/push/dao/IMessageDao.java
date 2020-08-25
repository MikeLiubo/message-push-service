package com.message.push.dao;

import com.message.push.pojo.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Author： MikeLiubo
 * Description：
 * @date Create on 2020/4/24 21:29
 **/
public interface IMessageDao extends JpaRepository<Message, String > {

    List<Message> findAllByStoreIdAndBeReadIsFalse(String storeId);
}
