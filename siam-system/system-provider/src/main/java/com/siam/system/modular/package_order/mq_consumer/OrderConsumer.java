package com.siam.system.modular.package_order.mq_consumer;

import com.siam.package_common.util.JsonUtils;
import com.siam.system.modular.package_order.entity.Order;
import com.siam.system.modular.package_order.service.OrderService;
import lombok.SneakyThrows;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RocketMQMessageListener(consumerGroup = "orderConsumerGroup", topic = "TID_COMMON")
public class OrderConsumer implements RocketMQListener<MessageExt> {

    @Autowired
    private OrderService orderService;

    @SneakyThrows
    @Override
    public void onMessage(MessageExt messageExt) {
        switch (messageExt.getTags()){
            case "CLOSE_OVERDUE_ORDER" :
                //关闭超时未支付的订单
                Order order = (Order) JsonUtils.toObject(new String(messageExt.getBody()), Order.class);
                orderService.closeOverdueOrder(order.getId());
                break;

            case "AUTO_COMPLETED_ORDER" :
                //订单支付超过1个小时 且 订单未取消/未申请退款，则将订单修改为已完成
                order = (Order) JsonUtils.toObject(new String(messageExt.getBody()), Order.class);
                orderService.autoCompletedOrder(order.getId());
                break;

            case "REMIND_OVERTIME_ORDER" :
                //订单支付超过10分钟，状态还是处于待处理、待配送，则给与商家中心PC端订单即将超时语音提醒
                order = (Order) JsonUtils.toObject(new String(messageExt.getBody()), Order.class);
                orderService.remindOvertimeOrder(order.getId());
                break;
        }
    }
}