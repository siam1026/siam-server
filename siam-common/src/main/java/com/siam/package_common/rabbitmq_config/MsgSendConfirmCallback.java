package com.siam.package_common.rabbitmq_config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

@Slf4j
public class MsgSendConfirmCallback implements RabbitTemplate.ConfirmCallback {
    /**
     * 当消息发送到交换机（exchange）时，该方法被调用.
     * 1.如果消息没有到exchange,则 ack=false
     * 2.如果消息到达exchange,则 ack=true
     *
     * @return
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        log.debug("\nMsgSendConfirmCallBack  , 回调id:" + correlationData);
        if (ack) {
            log.debug("\n消息发送到exchange成功");
            // TODO 删除 msgId 与 Message 的关系
        } else {
            log.debug("\n消息发送到exchange失败");
            // TODO 消息发送到exchange失败 ， 重新发送
        }
    }
}
