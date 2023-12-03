package com.siam.package_common.mod_websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
public class WebSocketService {

    @Autowired
    private WebSocketBaseServer webSocketBaseServer;

//    @Autowired
//    private WxPublicPlatformNotifyService wxPublicPlatformNotifyService;

    /**
     * 推送消息
     *
     * @param name 唯一客户端标识
     * @param mark 消息内容/标识
     * @return
     * @author 暹罗
     */
    public void pushMessage(String name, String mark) throws IOException {
        webSocketBaseServer.AppointSending(name, mark);

//        //微信公众号消息通知管理员
//        String description = "WebSocket-商家消息推送失败，name : " + name + "，mark : " + mark;
//        wxPublicPlatformNotifyService.sendFatalErrorMessage(WxPublicPlatformNotifyService.jpOpenId, description, CommonUtils.getFunctionLocation(), "无", new Date(), "请管理员及时处理");
    }
}