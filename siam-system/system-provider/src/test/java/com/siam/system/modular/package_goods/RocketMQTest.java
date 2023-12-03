package com.siam.system.modular.package_goods;

import com.siam.system.SiamApplication;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SiamApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableConfigurationProperties
public class RocketMQTest {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Test
    public void test() throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
        //1、
//        rocketMQTemplate.convertAndSend("goods-topic", "fist message");

        //2、
        String id = "1";
        Message message = new Message("goods-topic", "goods-tag", id, "msg body".getBytes());
        rocketMQTemplate.getProducer().send(message);

        System.out.println("\n\n\n11111111111111");
    }
}