package com.siam.package_common.rabbitmq_config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * 队列配置
 */
@Configuration
public class QueueConfig {

    public static final String QUEUE_NAME1 = "first-queue";
    public static final String QUEUE_NAME2 = "second-queue";
    public static final String QUEUE_NAME3 = "third-queue";

    /**
     * 1、durable="true" 持久化消息队列，rabbitmq重启的时候不需要创建新的队列
     * 2、auto-delete 表示消息队列没有在使用时将被自动删除 默认是false
     * 3、exclusive  表示该消息队列是否只在当前connection生效,默认是false
     *
     * @return
     */
    @Bean
    public Queue firstQueue(){
        return new Queue(QUEUE_NAME1, true, false, false);
    }

    @Bean
    public Queue secondQueue(){
        return new Queue(QUEUE_NAME2, true, false, false);
    }

    @Bean
    public Queue thirdQueue(){
        // 配置自动删除
        Map<String, Object> arguments = new HashMap<>();
        arguments.put("x-message-ttl", 60000);//60秒自动删除
        return new Queue(QUEUE_NAME3, true, false, false);
    }
}