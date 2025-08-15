package com.siam.system.config;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RocketMQConfig {

    @Value(value = "${rocketmq.switch:false}")
    private boolean mqSwitch;

    @Bean
    public RocketMQTemplate rocketMQTemplate() {
        RocketMQTemplate rocketMQTemplate = new RocketMQTemplate();
        if(mqSwitch){
            DefaultMQProducer producer = new DefaultMQProducer("your_producer_group");
            producer.setNamesrvAddr("your_nameserver_address");
            try {
                producer.start();
            } catch (Exception e) {
                throw new RuntimeException("Failed to start RocketMQ producer", e);
            }
            rocketMQTemplate.setProducer(producer);
        }
        return rocketMQTemplate;
    }
}