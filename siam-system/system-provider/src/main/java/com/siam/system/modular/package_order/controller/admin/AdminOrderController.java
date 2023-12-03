package com.siam.system.modular.package_order.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.package_common.entity.BasicResult;
import com.siam.package_common.exception.StoneCustomerException;
import com.siam.package_common.model.valid_group.ValidGroupOfAudit;
import com.siam.package_common.model.valid_group.ValidGroupOfId;
import com.siam.system.modular.package_order.entity.Order;

import com.siam.system.modular.package_order.model.param.OrderParam;
import com.siam.system.modular.package_order.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.*;

@RestController
@RequestMapping(value = "/rest/admin/order")
@Transactional(rollbackFor = Exception.class)
@Api(tags = "后台订单模块相关接口", description = "AdminOrderController")
public class AdminOrderController {

    @Autowired
    private OrderService orderService;

    @ApiOperation(value = "订单列表")
    @PostMapping(value = "/list")
    public BasicResult list(@RequestBody @Validated(value = {}) OrderParam param){
        Page page = orderService.getListByPageWithAsc(param);
        return BasicResult.success(page);
    }

    @ApiOperation("打印小票")
    public BasicResult printRceceipts(@RequestBody @Validated(value = {}) OrderParam param){
        for(String id : param.getIds()){
            orderService.printRceceipts(Integer.valueOf(id));
        }
        return BasicResult.success();
    }

    @ApiOperation(value = "查询单个订单信息")
    @PostMapping(value = "/selectById")
    public BasicResult selectById(@RequestBody @Validated(value = {}) OrderParam param){
        Order dbOrder = orderService.selectByPrimaryKey(param.getId());
        if(dbOrder == null){
            throw new StoneCustomerException("该订单不存在");
        }
        return BasicResult.success(dbOrder);
    }

    @ApiOperation(value = "批量修改订单的是否已打印状态为已打印")
    @PostMapping(value = "/batchUpdateIsPrintedTrue")
    public BasicResult batchUpdateIsPrintedTrue(@RequestBody @Validated(value = {}) OrderParam param){
        orderService.batchUpdateIsPrintedTrue(param);
        return BasicResult.success();
    }

    @ApiOperation(value = "今日订单列表")
    @PostMapping(value = "/todayOrderList")
    public BasicResult todayOrderList(@RequestBody @Validated(value = {}) OrderParam param){
        Page page = orderService.getListByTodayOrderWithAsc(param);
        return BasicResult.success(page);
    }

    @ApiOperation(value = "查询所有订单标签页的待处理数量")
    @PostMapping(value = "/selectAllTabWaitHandleNum")
    public BasicResult selectAllTabWaitHandleNum(@RequestBody @Validated(value = {}) OrderParam param){
        Map map = this.orderService.selectAllTabWaitHandleNum(param);
        return BasicResult.success(map);
    }

    @ApiOperation(value = "查询所有已付款未打印的订单")
    @PostMapping(value = "/waitPrintOrderList")
    public BasicResult waitPrintOrderList(@RequestBody @Validated(value = {}) OrderParam param){
        List<Order> orders = orderService.waitPrintOrderList(param);
        return BasicResult.success(orders);
    }

    @ApiOperation(value = "订单统计(支付成功订单数量、取消订单数量、退款订单数量，按自取或者外卖分开)")
    @PostMapping(value = "/countOrder")
    public BasicResult countOrder(@RequestBody @Validated(value = {}) OrderParam param){
        Map count=orderService.countOrder(param);
        return BasicResult.success(count);
    }

    @ApiOperation(value = "售后处理订单列表")
    @PostMapping(value = "/afterSalesList")
    public BasicResult afterSalesList(@RequestBody @Validated(value = {}) OrderParam param){
        Page page = orderService.getAfterSalesListByPageWithAsc(param);
        return BasicResult.success(page);
    }

    /**
     * 售后处理订单-处理
     *
     * @return
     * @author 暹罗
     */
    @PostMapping(value = "/auditAfterSalesOrder")
    public BasicResult auditAfterSalesOrder(@RequestBody @Validated(value = {ValidGroupOfId.class, ValidGroupOfAudit.class}) OrderParam param){
        orderService.auditAfterSalesOrder(param);
        return BasicResult.success();
    }

    /**
     * 查询订单统计数据
     * PS；一次性将所有数据全部查出来，而不是每次选择时间段时来实时请求数据
     */
    @PostMapping(value = "/statistic")
    public BasicResult statisticOrder(@RequestBody @Validated(value = {}) OrderParam param) throws ParseException {
        Map map = orderService.statistic(param);
        return BasicResult.success(map);
    }
}