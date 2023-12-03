package com.siam.system.modular.package_order;

import com.alibaba.fastjson.JSON;
import com.siam.package_common.constant.Quantity;
import com.siam.package_weixin_basic.service.WxPublicPlatformNotifyService;
import com.siam.package_common.util.AliyunExpressUtils;
import com.siam.package_common.util.DateUtilsExtend;
import com.siam.package_common.util.GsonUtils;
import com.siam.system.modular.package_goods.service.ShopService;
import com.siam.system.modular.package_user.service.MemberService;
import com.siam.system.modular.package_user.service.MerchantService;
import com.siam.system.modular.package_order.entity.Order;
import com.siam.system.modular.package_order.entity.OrderDetail;
import com.siam.system.modular.package_order.mapper.OrderDetailMapper;
import com.siam.system.modular.package_order.model.param.OrderParam;
import com.siam.system.modular.package_order.service.OrderService;
import com.siam.system.modular.package_goods.entity.Shop;
import com.siam.system.modular.package_user.entity.Member;
import com.siam.system.modular.package_user.entity.Merchant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableConfigurationProperties
public class OrderApplicationTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Autowired
    private ShopService shopService;

    @Autowired
    private MerchantService merchantService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private WxPublicPlatformNotifyService wxPublicPlatformNotifyService;

    @Autowired
    private AliyunExpressUtils aliyunExpressUtils;

    /**
     * 测试发送微信服务通知、微信公众号消息
     */
    @Test
    public void testSendWxMessage() throws IOException {
//        String wxOpenId = "o8Dqn5C5YhXn5IrMhIZPcyx7BVLA";
//        String wxPublicPlatformOpenId = "ocYTLtxbT_Hlidv4MjZRSVhgqa8I";
//        wxNotifyService.sendPickUpOrderCompleteMessage(wxOpenId, "order001", "荔枝奶茶 * 3", BigDecimal.valueOf(33.00), new Date(), "订单已付款");
//        wxPublicPlatformNotifyService.sendNewOrderMessageForMerchant("ocYTLtxbT_Hlidv4MjZRSVhgqa8I", "荔枝奶茶 * 3", new Date(), "麓谷小镇", "张先生");

        Order dbOrder = orderService.selectByPrimaryKey(953);
        List<OrderDetail> orderDetailList = orderDetailMapper.selectByOrderId(dbOrder.getId());
        Shop dbShop = shopService.selectByPrimaryKey(dbOrder.getShopId());

        //获取该订单对应的商家信息
        Merchant merchant = merchantService.selectByPrimaryKey(dbShop.getMerchantId());
        Member bindMember = memberService.selectByPrimaryKey(merchant.getMemberId());
        if(bindMember != null){
            //如果商品明细内容过长，则需要分多次发送公众号消息
            //字数：38*4=152 -> 112
            String goodsDescription = "";
            boolean isFirstSend = true; //是否为第一次发送

            for (OrderDetail orderDetail : orderDetailList) {
                //处理商品规格
                String specText = "";
                Map<String, Object> map = GsonUtils.toMap(orderDetail.getSpecList());
                for(String key : map.keySet()){
                    if(specText.isEmpty()){
                        specText += (String) map.get(key);
                    }else{
                        specText += "/" + (String) map.get(key);
                    }
                }

                String str = orderDetail.getGoodsName() + "  " + specText + "  *  " + orderDetail.getNumber() + "件\r\n";
                if(goodsDescription.length() + str.length() > 112){
                    //内容超过112个字数，需要重新拼接商品明细内容，将消息发送掉
                    goodsDescription = goodsDescription.substring(0, goodsDescription.length()-2);
                    //给商家发送微信公众号消息
                    String title = isFirstSend
                            ? "新订单服务通知 - 取餐号" + dbOrder.getQueueNo()
                            : "订单商品明细服务通知 - 取餐号" + dbOrder.getQueueNo();
                    if(isFirstSend){
                        String addressStr = dbOrder.getContactProvince() + dbOrder.getContactCity() + dbOrder.getContactArea() + dbOrder.getContactStreet() + dbOrder.getContactHouseNumber();
                        String deliveryAddress = dbOrder.getShoppingWay() == Quantity.INT_1
                                ? ("自取订单 - 取餐号" + dbOrder.getQueueNo())
                                : ("配送订单 - 取餐号" + dbOrder.getQueueNo() + " - " + addressStr);
                        String contacts = dbOrder.getContactRealname().concat(" - ").concat(dbOrder.getContactPhone());
                        String amountDescription = "已付款".concat(" - ").concat(dbOrder.getActualPrice()+"元");
                        String remark = org.apache.commons.lang3.StringUtils.isNotBlank(dbOrder.getRemark()) ? dbOrder.getRemark() : "无";
                        wxPublicPlatformNotifyService.sendNewOrderMessageForMerchant(bindMember.getWxPublicPlatformOpenId(), title, goodsDescription, dbOrder.getCreateTime(), deliveryAddress, contacts, amountDescription, remark);
                    }else{
                        String addressStr = "";
                        String deliveryAddress = "";
                        String contacts = "";
                        String amountDescription = "";
                        String remark = "";
                        wxPublicPlatformNotifyService.sendNewOrderMessageForMerchant(bindMember.getWxPublicPlatformOpenId(), title, goodsDescription, dbOrder.getCreateTime(), deliveryAddress, contacts, amountDescription, remark);
                    }
                    goodsDescription = "";
                    isFirstSend = false;
                }
                goodsDescription += str;

                //当前为列表末尾位置，需要将消息发送掉
                if(orderDetailList.indexOf(orderDetail) == orderDetailList.size()-1){
                    //内容超过112个字数，需要重新拼接商品明细内容，将消息发送掉
                    goodsDescription = goodsDescription.substring(0, goodsDescription.length()-2);
                    //给商家发送微信公众号消息
                    String title = isFirstSend
                            ? "新订单服务通知 - 取餐号" + dbOrder.getQueueNo()
                            : "订单商品明细服务通知 - 取餐号" + dbOrder.getQueueNo();
                    if(isFirstSend){
                        String addressStr = dbOrder.getContactProvince() + dbOrder.getContactCity() + dbOrder.getContactArea() + dbOrder.getContactStreet() + dbOrder.getContactHouseNumber();
                        String deliveryAddress = dbOrder.getShoppingWay() == Quantity.INT_1
                                ? ("自取订单 - 取餐号" + dbOrder.getQueueNo())
                                : ("配送订单 - 取餐号" + dbOrder.getQueueNo() + " - " + addressStr);
                        String contacts = dbOrder.getContactRealname().concat(" - ").concat(dbOrder.getContactPhone());
                        String amountDescription = "已付款".concat(" - ").concat(dbOrder.getActualPrice()+"元");
                        String remark = StringUtils.isNotBlank(dbOrder.getRemark()) ? dbOrder.getRemark() : "无";
                        wxPublicPlatformNotifyService.sendNewOrderMessageForMerchant(bindMember.getWxPublicPlatformOpenId(), title, goodsDescription, dbOrder.getCreateTime(), deliveryAddress, contacts, amountDescription, remark);
                    }else{
                        String addressStr = "";
                        String deliveryAddress = "";
                        String contacts = "";
                        String amountDescription = "";
                        String remark = "";
                        wxPublicPlatformNotifyService.sendNewOrderMessageForMerchant(bindMember.getWxPublicPlatformOpenId(), title, goodsDescription, dbOrder.getCreateTime(), deliveryAddress, contacts, amountDescription, remark);
                    }
                    goodsDescription = "";
                    isFirstSend = false;
                }
            }
        }else{
            log.debug(dbOrder.getShopName() + "还未绑定小程序账号，发送新订单通知失败");
        }
    }

    /**
     * 测试订单退款
     */
    @Test
    public void testOrderRefund() throws Exception {
        /*wxPayService.refund();*/

        /*// 获得加密信息
        String reqInfo = "4K/yo1B305WHkQbiQuf6vcddMrzXzDZhNXooQ/zWfe80ioasjWJulz9WD8dO4Rzme0W+c1oqCclvs3iGalx/tnzlj5E3xOSkWUTgoMvDWAwq+hKq7PwkUB8gUIlIl+27buv+tUsus+QTwfI5QlhcDGqWCvy76FQLnnTlGPoBqpRFY9F7xiBhGtPcp+X4e6uiSfpq8SPlwkFBpfCBY90tQtQUsMlMEFhmUdUIZMUvb+jcfsWc5EhIqYoRRgVBDcIQZegtosbB1NlP6wmZSPmPjyUj6GAtJ9To0x5+e4kIP89IaCxZ96x12qkxaUHsYG/WlTSgLn5ziwBEYqJXgZrjJjIzCuZwdG+uO2HuDU3hwwJKRaEG2I9NMk3DFdUtM5bcC6krer58kPR8+sm6NlQtot7bETfDGXTM9WgaHaAwamhAR6xC0jb6z3DiMOjWU7/JGQimmf30adx9VpnViKU5io1svUlrIJTo4bILgxM1G7Zs1xZqTbctMj4ocz8h4/HzGuOiUVEjxTrNliKrVae+oBvwUM/jySdkvnyNSQ4jBmCCO41EIxvVtOHor+gOh6pJLB08+/ZQb8yNrS2oo+flrXrBk00fmO8PsJRsglV1xnUkhIgqi0F2d4t7Qb0e/3woN76ajwr/uk1BDmuYV+6eDUg/tCXJPThlj+PIGne3NHKSoC1JSy5fDbQHxd1IyROHIkrfbJCc6I4eabtz8pNtaEKZXWuSQ2+ZQ0A/POSkhHxhcpgMCjTvALcud+3jMcGtlpX5SPcWQMeFW7wYlUZxZ1kpKBcn6V6hXoTbvnGCzJ66wfygEoU0hWMwHwBO7RYHNsTNp3YwPsbnTnf+iJqD04lOrv5RF2/OL+cVNBuyV+D5mEbztPidk1tfYj2L8WZa5p5VlMXrq2t/yF/nVrcOdwKjB4fmL2M/+OUbIGaoHCgsZS+7byVqo1eVswDxZir9r0hvCa7t5Z5ZHwpPzpmKkOY635ccB4q3Wpd7kxiL1+yZTL4TIWNoW/oZxz4ebAgDXdZcSvNb1Dw29jfU5Bg56TAM4a4bLBkLmoirDMB7bpk6leIsG7FN1SC+GJcngZmG";
        // 进行AES解密 获取req_info中包含的相关信息(解密失败会抛出异常)
        String refundDecryptedData = wxdecodeUtils.decryptData(reqInfo);
        Map<String, String> reqInfoMap = PayUtil.doXMLParse(refundDecryptedData);
        log.info("[refundAsyncNotify] [reqInfo解密成功] [reqInfoMap:{}]", reqInfoMap);*/

        /*transaction_id=4200000687202009094606165812,
        refund_status=SUCCESS,
        out_refund_no=7f114d96d94d4ae0ba85c88cb0c34292,
        settlement_refund_fee=1,
        success_time=2020-09-10 11:39:59,
        refund_recv_accout=支付用户零钱,
        refund_id=50300405492020091002596546481,
        out_trade_no=214748364775123914,
        refund_account=REFUND_SOURCE_RECHARGE_FUNDS,
        refund_fee=1,
        total_fee=10,
        settlement_total_fee=10,
        refund_request_source=API*/
    }

    /**
     * 测试订单物流跟踪信息
     */
    @Test
    public void testOrderExpress() throws Exception {
        aliyunExpressUtils.query("780098068058");
    }

    @Test
    public void testGetNextQueueNo(){
        int i= orderService.getNextQueueNo();
        System.out.println(i);
    }

    @Test
    public void test(){
//        orderService.selectByOrderNo("1");
//        orderService.selectByPrimaryKey(1);
//        orderService.selectById(1);
//        orderService.getListByPageWithAsc(new OrderDto());

        OrderParam order = new OrderParam();
        order.setShopId(1);
        order.setStartTime(DateUtilsExtend.getDayBegin());
        order.setEndTime(DateUtilsExtend.getDayEnd());
        Integer dayCountPaid = orderService.selectCountCompleted(order);

        orderDetailMapper.selectByOrderId(1);
    }

    @Test
    public void test02() throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
        //超时未支付的订单加到MQ延时队列，5分钟
        Order order = new Order();
        order.setId(2);
        String tags = "CLOSE_OVERDUE_ORDER";
        Message message = new Message("TID_COMMON", tags, JSON.toJSONString(order).getBytes());
        //delaytime的值: messageDelayLevel=1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h
        message.setDelayTimeLevel(3);
        rocketMQTemplate.getProducer().send(message);
    }
}