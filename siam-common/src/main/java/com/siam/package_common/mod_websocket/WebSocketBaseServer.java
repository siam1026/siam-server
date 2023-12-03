package com.siam.package_common.mod_websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
 
/**
 * @Auther: liaoshiyao
 * @Date: 2019/1/11 11:48
 * @Description: websocket 服务类
 */
@Slf4j
@Component
@ServerEndpoint("/websocket/{name}")
public class WebSocketBaseServer {

    //与某个客户端的连接对话，需要通过它来给客户端发送消息
    private Session session;
 
    //标识当前连接客户端的用户名
    private String name;

    //用于存所有的连接服务的客户端，这个对象存储是安全的
    private static ConcurrentHashMap<String, WebSocketBaseServer> webSocketSet = new ConcurrentHashMap<>();

    /**
     * 监听客户端的连接
     * @param session
     * @param name 唯一客户端，如果需要指定发送，需要指定发送通过name来区分
     */
    @OnOpen
    public void OnOpen(Session session, @PathParam(value = "name") String name){
        this.session = session;
        this.name = name;
        webSocketSet.put(name, this);
        log.info("[WebSocket] 连接成功，当前连接人数为：{}人", webSocketSet.size());

        //TODO - 查询消息队列中是否有当前客户端未消费内容，如果有则进行发送

    }

    @OnClose
    public void OnClose(){
        webSocketSet.remove(this.name);
        log.info("[WebSocket] 退出成功，当前连接人数为：{}人", webSocketSet.size());
    }

    @OnMessage
    public void OnMessage(String message) throws IOException {
        log.info("[WebSocket] 收到消息：{}", message);
        //判断是否需要指定发送，具体规则自定义
        if(message.indexOf("TOUSER") == 0){
            String name = message.substring(message.indexOf("TOUSER")+6,message.indexOf(";"));
            AppointSending(name, message.substring(message.indexOf(";") + 1, message.length()));
        }else{
            GroupSending(message);
        }
    }

    /**
     * 群发
     * @param message
     */
    public void GroupSending(String message){
        for (String name : webSocketSet.keySet()){
            try {
                message = "[WebSocket] send first message";
                webSocketSet.get(name).session.getBasicRemote().sendText(message);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    /**
     * 指定发送
     * @param name
     * @param message
     */
    public void AppointSending(String name, String message) throws IOException {
        if (webSocketSet.get(name) == null){
            log.error("[WebSocket] " + name + "客户端未连接，消息发送失败");
            return;
            /*throw new RuntimeException();*/
        }
        webSocketSet.get(name).session.getBasicRemote().sendText(message);
    }
}