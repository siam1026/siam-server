package com.siam.system.modular.package_order.controller.member.internal;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.package_common.entity.BasicResult;
import com.siam.system.modular.package_order.model.param.internal.PointsMallOrderParam;
import com.siam.system.modular.package_order.model.vo.internal.PointsMallOrderVo;
import com.siam.system.modular.package_order.entity.internal.PointsMallOrder;
import com.siam.system.modular.package_order.service.internal.PointsMallOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;

@Slf4j
@RestController
@RequestMapping(value = "/rest/member/pointsMall/order")
@Transactional(rollbackFor = Exception.class)
@Api(tags = "订单模块相关接口", description = "PointsMallOrderController")
public class PointsMallOrderController {

    @Resource(name = "pointsMallOrderServiceImpl")
    private PointsMallOrderService orderService;

    /**
     * 订单列表
     *
     * @return
     * @author 暹罗
     */
    @PostMapping(value = "/list")
    public BasicResult listPointsMallOrder(@RequestBody @Validated(value = {}) PointsMallOrderParam param){
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
    public BasicResult selectById(@RequestBody @Validated(value = {}) PointsMallOrderParam param){
        PointsMallOrderVo pointsMallOrderVo = orderService.selectById(param);
        return BasicResult.success(pointsMallOrderVo);
    }

    /**
     * 生成防重令牌
     *
     * @return
     * @author 暹罗
     */
    @PostMapping(value = "/createOrderToken")
    public BasicResult createOrderToken(@RequestBody @Validated(value = {}) PointsMallOrderParam param) throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
        String token = orderService.createOrderToken(param);
        return BasicResult.success(token);
    }

    /**
     * 新增订单/确认下单(在发起微信支付前，前端得先请求该接口做订单校验，校验完成后接口会返回订单信息)
     *
     * @return
     * @author 暹罗
     */
    @PostMapping(value = "/insert")
    public BasicResult insertPointsMallOrder(@RequestBody @Validated(value = {}) PointsMallOrderParam param) throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
        PointsMallOrder pointsMallOrder = orderService.insert(param);
        return BasicResult.success(pointsMallOrder);
    }

    /**
     * 取消订单(五分钟内未付款，自动取消)
     *
     * @return
     * @author 暹罗
     */
    @PostMapping(value = "/cancelOrder")
    public BasicResult cancelPointsMallOrder(@RequestBody @Validated(value = {}) PointsMallOrderParam param){
        orderService.cancelOrder(param);
        return BasicResult.success();
    }

    /**
     * 未发货申请退款
     *
     * @return
     * @author 暹罗
     */
    @PostMapping(value = "/applyRefundUndelivered")
    public BasicResult applyRefundUndelivered(@RequestBody @Validated(value = {}) PointsMallOrderParam param){
        orderService.applyRefundUndelivered(param);
        return BasicResult.success();
    }

    /**
     * 已发货申请售后-仅退款
     *
     * @return
     * @author 暹罗
     */
    @PostMapping(value = "/applyAfterSalesDelivered/onlyRefund")
    public BasicResult onlyRefund(@RequestBody @Validated(value = {}) PointsMallOrderParam param){
        orderService.onlyRefund(param);
        return BasicResult.success();
    }

    /**
     * 已发货申请售后-退货退款
     *
     * @return
     * @author 暹罗
     */
    @PostMapping(value = "/applyAfterSalesDelivered/returnGoodsWithRefund")
    public BasicResult returnGoodsWithRefund(@RequestBody @Validated(value = {}) PointsMallOrderParam param){
        orderService.returnGoodsWithRefund(param);
        return BasicResult.success();
    }

    @ApiOperation(value = "修改订单状态为已确认收货")
    @PostMapping(value = "/confirmReceipt")
    public BasicResult confirmReceipt(@RequestBody @Validated(value = {}) PointsMallOrderParam param){
        orderService.confirmReceipt(param);
        return BasicResult.success();
    }

    @ApiOperation(value = "删除订单")
    @PostMapping(value = "/delete")
    public BasicResult deletePointsMallOrder(@RequestBody @Validated(value = {}) PointsMallOrderParam param){
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
    public BasicResult selectRefundProcess(@RequestBody @Validated(value = {}) PointsMallOrderParam param){
        Map map = orderService.selectRefundProcess(param);
        return BasicResult.success(map);
    }

    /**
     * 查询标签页的记录数量
     *
     * @return
     * @author 暹罗
     */
    @PostMapping(value = "/selectTabNumber")
    public BasicResult selectTabNumber(@RequestBody @Validated(value = {}) PointsMallOrderParam param){
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
    public BasicResult selectCommissionReward(@RequestBody @Validated(value = {}) PointsMallOrderParam param){
        String text = orderService.selectCommissionReward(param);
        return BasicResult.success(text);
    }
}