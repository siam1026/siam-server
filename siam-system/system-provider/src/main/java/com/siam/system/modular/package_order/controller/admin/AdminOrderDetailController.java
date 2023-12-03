package com.siam.system.modular.package_order.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.package_common.entity.BasicData;
import com.siam.package_common.entity.BasicResult;
import com.siam.package_common.constant.BasicResultCode;
import com.siam.system.modular.package_order.entity.OrderDetail;
import com.siam.system.modular.package_order.service.OrderDetailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/rest/admin/orderDetail")
@Transactional(rollbackFor = Exception.class)
@Api(tags = "后台订单商品详情模块相关接口", description = "AdminOrderDetailController")
public class AdminOrderDetailController {
    @Autowired
    private OrderDetailService orderDetailService;

    @ApiOperation(value = "订单商品详情列表")
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
    public BasicResult list(@RequestBody @Validated(value = {}) OrderDetail orderDetail){
        BasicData basicResult = new BasicData();

        Page page = orderDetailService.getListByPage(orderDetail.getPageNo(), orderDetail.getPageSize(), orderDetail);

        return BasicResult.success(page);
    }


    @ApiOperation(value = "新增订单商品详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderId", value = "订单id", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "goodsId", value = "商品id", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "goodsName", value = "商品名称", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "specList", value = "商品规格 JSON格式", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "goodsSpecs", value = "规格组合 JSON格式", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "price", value = "价格", required = true, paramType = "query", dataType = "BigDecimal"),
            @ApiImplicitParam(name = "number", value = "购买数量", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "subtotal", value = "小计", required = true, paramType = "query", dataType = "BigDecimal"),
            @ApiImplicitParam(name = "remark", value = "备注", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "status", value = "状态 1=启用 0=禁用 -1=删除", required = false, paramType = "query", dataType = "int"),
    })
    @PostMapping(value = "/insert")
    public BasicResult insert(@RequestBody @Validated(value = {}) OrderDetail orderDetail){
        BasicResult basicResult = new BasicResult();

        orderDetailService.insertSelective(orderDetail);

        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("新增成功");
        return basicResult;
    }

    @ApiOperation(value = "修改订单商品详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "订单商品详情表主键id", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "orderId", value = "订单id", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "goodsId", value = "商品id", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "goodsName", value = "商品名称", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "specList", value = "商品规格 JSON格式", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "goodsSpecs", value = "规格组合 JSON格式", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "price", value = "价格", required = true, paramType = "query", dataType = "BigDecimal"),
            @ApiImplicitParam(name = "number", value = "购买数量", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "subtotal", value = "小计", required = true, paramType = "query", dataType = "BigDecimal"),
            @ApiImplicitParam(name = "remark", value = "备注", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "status", value = "状态 1=启用 0=禁用 -1=删除", required = false, paramType = "query", dataType = "int"),
    })
    @PostMapping(value = "/update")
    public BasicResult update(@RequestBody @Validated(value = {}) OrderDetail orderDetail){
        BasicResult basicResult = new BasicResult();

        orderDetailService.updateByPrimaryKeySelective(orderDetail);

        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("修改成功");
        return basicResult;
    }

    @ApiOperation(value = "删除订单商品详情(含批量操作)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "订单商品详情表主键id(批量删除时id以逗号分隔)", required = true, paramType = "query", dataType = "string"),
    })
    @PostMapping(value = "/delete")
    public BasicResult delete(@RequestBody @Validated(value = {}) OrderDetail param){
        BasicResult basicResult = new BasicResult();

        for(String id : param.getIds()){
            orderDetailService.deleteByPrimaryKey(Integer.valueOf(id));
        }

        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("删除成功");
        return basicResult;
    }


}