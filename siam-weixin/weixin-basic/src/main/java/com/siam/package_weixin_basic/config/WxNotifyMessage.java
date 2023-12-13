package com.siam.package_weixin_basic.config;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author 暹罗
 * @className SubscribeDataBean
 * @Description 订阅消息具体内容对象 根据对象不同key不同
 * @Date 2020/2/24-11:23
 **/
@Data
public class WxNotifyMessage {
    //具体的订阅消息的key {{thing4.DATA}} 则key为thing4
    private Thing2 thing2;
    private Thing5 thing5;
    private Thing11 thing11;
    private CharacterString4 character_string4;

    @Data
    @AllArgsConstructor
    public static class Thing2{
        private String value;
    }

    @Data
    @AllArgsConstructor
    public static class Thing5{
        private String value;
    }

    @Data
    @AllArgsConstructor
    public static class Thing11{
        private String value;
    }

    @Data
    @AllArgsConstructor
    public static class CharacterString4{
        private String value;
    }
}