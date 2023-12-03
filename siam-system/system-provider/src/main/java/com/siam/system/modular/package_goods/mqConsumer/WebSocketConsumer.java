package com.siam.system.modular.package_goods.mqConsumer;

import com.siam.package_common.mod_websocket.WebSocketService;
import lombok.SneakyThrows;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RocketMQMessageListener(consumerGroup = "webSocketConsumerGroup", topic = "TID_WEBSOCKET")
public class WebSocketConsumer implements RocketMQListener<MessageExt> {

    @Autowired
    private WebSocketService webSocketService;

    @SneakyThrows
    @Override
    public void onMessage(MessageExt messageExt) {
        //使用WebSocket实现消息实时推送
        webSocketService.pushMessage(messageExt.getTags(), new String(messageExt.getBody()));
    }
}