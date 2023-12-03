package com.siam.system.modular.package_order.controller.member;

import com.siam.system.modular.package_order.service.OrderDetailService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/rest/orderDetail")
@Transactional(rollbackFor = Exception.class)
@Api(tags = "订单商品详情模块相关接口", description = "OrderDetailController")
public class OrderDetailController {
    @Autowired
    private OrderDetailService orderDetailService;

    /*@ApiOperation(value = "订单商品详情列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "订单商品详情表主键id", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "orderId", value = "订单id", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "goodsId", value = "商品id", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "goodsName", value = "商品名称", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "specList", value = "商品规格 JSON格式", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "goodsSpecs", value = "规格组合 JSON格式", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "price", value = "价格", required = false, paramType = "query", dataType = "BigDecimal"),
            @ApiImplicitParam(name = "number", value = "购买数量", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "subtotal", value = "小计", required = false, paramType = "query", dataType = "BigDecimal"),
            @ApiImplicitParam(name = "remark", value = "备注", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "status", value = "状态 1=启用 0=禁用 -1=删除", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "pageNo", value = "页码(值为-1不分页)", required = true, paramType = "query", dataType = "int", defaultValue = "1"),
            @ApiImplicitParam(name = "pageSize", value = "页数", required = true, paramType = "query", dataType = "int", defaultValue = "20"),
    })
    @PostMapping(value = "/list")
    public BasicResult list(int pageNo, int pageSize, OrderDetail orderDetail){
        BasicData basicResult = new BasicData();

        Page page = orderDetailService.getListByPage(param.getPageNo(), param.getPageSize(), orderDetail);

        return BasicResult.success(page);
    }*/
}