package com.siam.system;

import org.apache.rocketmq.spring.autoconfigure.RocketMQAutoConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication(scanBasePackages = "com.siam", exclude = {RocketMQAutoConfiguration.class})
@MapperScan(basePackages = {"com.siam.**.mapper"})
public class SiamApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(SiamApplication.class, args);
    }
}