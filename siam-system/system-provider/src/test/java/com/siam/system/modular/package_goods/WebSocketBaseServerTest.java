package com.siam.system.modular.package_goods;

import com.siam.package_common.mod_websocket.WebSocketBaseServer;
import com.siam.system.SiamApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SiamApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableConfigurationProperties
public class WebSocketBaseServerTest {

    @Autowired
    private WebSocketBaseServer webSocketBaseServer;

    @Test
    public void test() throws IOException {
        webSocketBaseServer.AppointSending("1", "newOrder");
        System.out.println("\n\n11111111");
    }
}