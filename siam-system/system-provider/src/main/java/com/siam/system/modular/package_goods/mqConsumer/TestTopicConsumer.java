package com.siam.system.modular.package_goods.mqConsumer;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

@Component
@RocketMQMessageListener(consumerGroup = "goodsConsumerGroup", topic = "goods-topic")
public class TestTopicConsumer implements RocketMQListener<String> {

    @Override
    public void onMessage(String message) {
        System.out.println("消费消息：" + message);
    }
}