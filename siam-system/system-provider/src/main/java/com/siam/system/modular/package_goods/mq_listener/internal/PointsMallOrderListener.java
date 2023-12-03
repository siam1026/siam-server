package com.siam.system.modular.package_goods.mq_listener.internal;

import com.alibaba.fastjson.JSON;
import com.siam.system.modular.package_goods.service.internal.PointsMallShoppingCartService;
import com.siam.system.modular.package_goods.service.internal.PointsMallShoppingCartService;
import com.siam.system.modular.package_order.model.param.internal.PointsMallOrderParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class PointsMallOrderListener implements MessageListenerConcurrently {

    @Autowired
    private PointsMallShoppingCartService shoppingCartService;

    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
        log.info("[商品服务-处理下单逻辑] 消费者线程监听到消息。");
        for (MessageExt messageExt : list) {
            if(!process(messageExt)){
                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
            }
        }
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }

    /**
     * 消息处理，第3次处理失败后，发送邮件通知人工介入
     * @param messageExt
     * @return
     */
    public boolean process(MessageExt messageExt) {
        try{
            String body = new String(messageExt.getBody());
            log.info("消息处理...{}", body);
            log.info("开始处理订单数据，准备扣减购物车数量....");

            //此处需要考虑幂等性
            //4、如果是从购物车下单的 那么下单成功后需要删除购物车数据  注意用批量删除
            PointsMallOrderParam param = JSON.parseObject(body, PointsMallOrderParam.class);
            if(param.getShoppingCartIdList()!=null && param.getShoppingCartIdList().size()>0){
                shoppingCartService.batchDeleteByIdList(param.getShoppingCartIdList());
            }

            // 模拟异常
            //int k = 1/0;

        }catch (Exception e){
            if(messageExt.getReconsumeTimes() >= 3){
                log.error("消息重试已达最大次数，将通知业务人员排查问题。{}", messageExt.getMsgId());
                // 发送邮件或者报警
                // sendMail(messageExt);
                return true;
            }
            return false;
        }
        return true;
    }
}