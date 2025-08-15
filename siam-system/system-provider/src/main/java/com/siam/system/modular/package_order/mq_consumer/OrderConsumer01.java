package com.siam.system.modular.package_order.mq_consumer;

/**
 * 负责接收处理订单服务发送的消息
 */
//@Slf4j
//@Component
//@Transactional(rollbackFor = Exception.class)
//public class OrderConsumer {
//    /*@Autowired*/
//    @Reference
//    private GoodsService goodsService;
//
//    /*@Autowired*/
//    @Reference
//    private GoodsSpecificationService goodsSpecificationService;
//
//    @Autowired
//    private OrderDetailService orderDetailService;
//
//    @Autowired
//    private OrderService orderService;
//
//    @RabbitListener(queues = {QueueConfig.QUEUE_NAME1})
//    public void updateStock(Message message, Channel channel){
//        log.debug("\n=================== 消费者 =====================");
//        log.debug("\n" + message);
//        log.debug("\n" + new String(message.getBody()));
//
//        // 订单支付成功，减少库存（未支付，那库存是否变化，我觉得网站上库存是偏小的）
//        Order order = GsonUtils.toBean(new String(message.getBody()), Order.class);
//        List<OrderDetail> list = orderDetailService.selectByOrderId(order.getId());
//        for(OrderDetail orderDetail : list){
//            // 修改订单状态为--已付款
//            Order updateOrder = new Order();
//            updateOrder.setId(order.getId());
//            updateOrder.setOrderStatus(Quantity.INT_1);
//            orderService.updateById(updateOrder);
//
//            // 减少商品库存
//            Integer number = orderDetail.getNumber();
//            String goodsId = orderDetail.getGoodsId();
//            goodsService.decreaseStock(goodsId, number);
//
//            // 减少商品规格组合库存
//            String goodsSpecificationId = orderDetail.getGoodsSpecificationId();
//            goodsSpecificationService.decreaseStock(goodsSpecificationId, number);
//        }
//    }
//}