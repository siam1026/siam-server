package com.siam.system.modular.package_goods.service_impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.siam.package_common.mod_websocket.WebSocketService;
import com.siam.system.modular.package_goods.entity.SysMessage;
import com.siam.system.modular.package_goods.mapper.MessageMapper;
import com.siam.system.modular.package_goods.service.MessageService;
import com.siam.system.modular.package_goods.entity.SysMessage;
import com.siam.system.modular.package_goods.mapper.MessageMapper;
import com.siam.system.modular.package_goods.service.MessageService;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 系统消息表业务实现层
 *
 * @author 暹罗
 * @date 2021/10/21 12:37
 */
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, SysMessage> implements MessageService {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Autowired
    private WebSocketService webSocketService;

    @Override
    public void insertSelective(SysMessage sysMessage) {
        //新增系统消息
        this.baseMapper.insert(sysMessage);

        //生成tags
        String tags = "";
        if(sysMessage.getUserType() == SysMessage.USER_TYPE_MEMBER){
            tags = "MALL_MESSAGE_MEMBER_";
        }else if(sysMessage.getUserType() == SysMessage.USER_TYPE_MERCHANT){
            tags = "MALL_MESSAGE_MERCHANT_";
        }else if(sysMessage.getUserType() == SysMessage.USER_TYPE_ADMIN){
            tags = "MALL_MESSAGE_ADMIN_";
        }

        //1、使用RocketMQ实现消息实时推送
        Message message = new Message("TID_WEBSOCKET", tags + sysMessage.getUserId(), JSON.toJSONString(sysMessage).getBytes());
        try {
            rocketMQTemplate.getProducer().send(message);
        } catch (Exception e) {
            log.error("[RocketMQ] topic : " + message.getTopic() + ", tags : " + message.getTags() + " 消息发送失败");
            e.printStackTrace();
        }
    }
}