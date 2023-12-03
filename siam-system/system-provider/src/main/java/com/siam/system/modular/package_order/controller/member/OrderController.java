package com.siam.system.modular.package_order.controller.member;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.package_common.entity.BasicResult;
import com.siam.system.modular.package_order.entity.Order;
import com.siam.system.modular.package_order.model.param.OrderParam;
import com.siam.system.modular.package_order.model.vo.OrderVo;
import com.siam.system.modular.package_order.model.vo.OrderVo2;
import com.siam.system.modular.package_order.service.*;
import com.siam.system.modular.package_order.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;

@Slf4j
@RestController
@RequestMapping(value = "/rest/member/order")
@Transactional(rollbackFor = Exception.class)
@Api(tags = "订单模块相关接口", description = "OrderController")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Resource(name = "rewardServiceImpl")
    private RewardService rewardService;

    /**
     * 订单列表
     *
     * @return
     * @author 暹罗
     */
    @PostMapping(value = "/list")
    public BasicResult listOrder(@RequestBody @Validated(value = {}) OrderParam param){
        Page<Map<String, Object>> page = orderService.getListByPageWithDesc(param);
        return BasicResult.success(page);
    }

    /**
     * 查看订单详情
     *
     * @return
     * @author 暹罗
     */
    @PostMapping(value = "/selectById")
    public BasicResult selectById(@RequestBody @Validated(value = {}) OrderParam param){
        OrderVo orderVo = orderService.selectById(param);
        return BasicResult.success(orderVo);
    }

    /**
     * 新增订单/确认下单(在发起微信支付前，前端得先请求该接口做订单校验，校验完成后接口会返回订单信息)
     *
     * @return
     * @author 暹罗
     */
    @PostMapping(value = "/insert")
    public BasicResult insertOrder(@RequestBody @Validated(value = {}) OrderParam param) throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
        Order order = orderService.insert(param);
        return BasicResult.success(order);
    }

    /**
     * 取消订单(五分钟内未付款，自动取消)
     *
     * @return
     * @author 暹罗
     */
    @PostMapping(value = "/cancelOrder")
    public BasicResult cancelOrder(@RequestBody @Validated(value = {}) OrderParam param){
        orderService.cancelOrder(param);
        return BasicResult.success();
    }

    /**
     * 取消订单(已支付)/极速退款
     *
     * @return
     * @author 暹罗
     */
    @PostMapping(value = "/cancelOrderNoReason")
    public BasicResult cancelOrderNoReason(@RequestBody @Validated(value = {}) OrderParam param) throws IOException {
        orderService.cancelOrderNoReason(param);
        return BasicResult.success();
    }

    /**
     * 申请退款
     *
     * @return
     * @author 暹罗
     */
    @PostMapping(value = "/applyRefund")
    public BasicResult applyRefund(@RequestBody @Validated(value = {}) OrderParam param) throws IOException {
        orderService.applyRefund(param);
        return BasicResult.success();
    }

    @ApiOperation(value = "修改订单状态为已确认收货")

    @PostMapping(value = "/confirmReceipt")
    public BasicResult confirmReceipt(@RequestBody @Validated(value = {}) OrderParam param){
        orderService.confirmReceipt(param);
        return BasicResult.success();
    }

    @ApiOperation(value = "删除订单")

    @PostMapping(value = "/delete")
    public BasicResult deleteOrder(@RequestBody @Validated(value = {}) OrderParam param){
        orderService.delete(param);
        return BasicResult.success();
    }

    /**
     * 查看订单退款进度
     *
     * @return
     * @author 暹罗
     */
    @PostMapping(value = "/selectRefundProcess")
    public BasicResult selectRefundProcess(@RequestBody @Validated(value = {}) OrderParam param){
        OrderVo2 orderVo2 = orderService.selectRefundProcess(param);
        return BasicResult.success(orderVo2);
    }

    /**
     * 查询标签页的记录数量
     *
     * @return
     * @author 暹罗
     */
    @PostMapping(value = "/selectTabNumber")
    public BasicResult selectTabNumber(@RequestBody @Validated(value = {}) OrderParam param){
        Map map = orderService.selectTabNumber(param);
        return BasicResult.success(map);
    }

    /**
     * 查看佣金奖励信息
     *
     * @return
     * @author 暹罗
     */
    @PostMapping(value = "/selectCommissionReward")
    public BasicResult selectCommissionReward(@RequestBody @Validated(value = {}) OrderParam param){
        String text = rewardService.selectCommissionReward(param);
        return BasicResult.success(text);
    }
}