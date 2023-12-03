package com.siam.package_common.rabbitmq_config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

@Slf4j
public class MsgSendReturnsCallback implements RabbitTemplate.ReturnCallback {

    /**
     * 当消息从交换机到队列失败时，该方法被调用。（若成功，则不调用）
     * 需要注意的是：该方法调用后，MsgSendConfirmCallBack中的confirm方法也会被调用，且ack = true
     *
     * @return
     */
    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        log.debug("\nMsgSendReturnCallback [消息从交换机到队列失败]  message："+message);

        // TODO 消息从交换机到队列失败，重新发送
    }
}
