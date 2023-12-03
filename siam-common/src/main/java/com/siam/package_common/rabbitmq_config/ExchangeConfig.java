package com.siam.package_common.rabbitmq_config;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 交换机配置
 */
@Configuration
public class ExchangeConfig {

    public static final String EXCHANGE_01 = "first_exchange";

    /**
     * 1、durable="true" 持久化交换机， rabbitmq重启的时候不需要创建新的交换机
     * 2、direct交换器相对来说比较简单，匹配规则为：如果路由键匹配，消息就被投送到相关的队列
     * fanout交换器中没有路由键的概念，他会把消息发送到所有绑定在此交换器上面的队列中。
     * topic交换器你采用模糊匹配路由键的原则进行转发消息到队列中
     *
     * @return
     */
    @Bean
    public DirectExchange directExchange(){
        DirectExchange directExchange = new DirectExchange(EXCHANGE_01, true, false);
        return directExchange;
    }
}