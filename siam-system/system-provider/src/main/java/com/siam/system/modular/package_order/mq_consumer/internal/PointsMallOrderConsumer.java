package com.siam.system.modular.package_order.mq_consumer.internal;

import com.alibaba.fastjson.JSON;
import com.siam.package_common.util.JsonUtils;
import com.siam.system.modular.package_order.entity.internal.PointsMallOrder;
import com.siam.system.modular.package_order.service.internal.PointsMallOrderService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Slf4j
@Component
@RocketMQMessageListener(consumerGroup = "mallOrderConsumerGroup", topic = "TID_COMMON_MALL")
public class PointsMallOrderConsumer implements RocketMQListener<MessageExt> {

    @Resource(name = "pointsMallOrderServiceImpl")
    private PointsMallOrderService mallOrderService;

    @SneakyThrows
    @Override
    public void onMessage(MessageExt messageExt) {
        switch (messageExt.getTags()){
            case "CLOSE_OVERDUE_ORDER" :
                //关闭超时未支付的订单
                PointsMallOrder mallOrder = (PointsMallOrder) JsonUtils.toObject(new String(messageExt.getBody()), PointsMallOrder.class);
                mallOrderService.closeOverdueOrder(mallOrder.getId());
                log.debug("\n\n》》》 CLOSE_OVERDUE_ORDER - id = " + JSON.toJSONString(mallOrder.getId()));
                break;
        }
    }
}