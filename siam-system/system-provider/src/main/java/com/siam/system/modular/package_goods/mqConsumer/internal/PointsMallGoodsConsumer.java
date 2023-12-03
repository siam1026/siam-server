package com.siam.system.modular.package_goods.mqConsumer.internal;

import com.siam.system.modular.package_goods.mq_listener.internal.PointsMallOrderListener;
import com.siam.system.modular.package_goods.mq_listener.internal.PointsMallOrderListener;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class PointsMallGoodsConsumer {

    private static final String GROUP_NAME = "goods-consumer-center";

    @Autowired
    private PointsMallOrderListener pointsMallOrderListener;

    @PostConstruct
    public void init() throws MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(GROUP_NAME);
        consumer.setNamesrvAddr("127.0.0.1:9876");
        consumer.subscribe("order", "*");
        consumer.registerMessageListener(pointsMallOrderListener);
        consumer.start();
    }
}