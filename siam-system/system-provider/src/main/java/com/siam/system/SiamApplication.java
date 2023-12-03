package com.siam.system;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.siam")
@MapperScan(basePackages = {"com.siam.**.mapper"})
public class SiamApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(SiamApplication.class, args);
    }
}