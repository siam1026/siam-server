package com.siam;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@EnableAdminServer
@SpringBootApplication(scanBasePackages = "com.siam", exclude = {DataSourceAutoConfiguration.class})
public class MonitorApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(MonitorApplication.class, args);
    }
}