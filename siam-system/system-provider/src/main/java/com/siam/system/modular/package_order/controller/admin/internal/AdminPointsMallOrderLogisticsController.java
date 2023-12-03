package com.siam.system.modular.package_order.controller.admin.internal;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.system.modular.package_order.service.internal.PointsMallOrderLogisticsService;
import com.siam.package_common.entity.BasicData;
import com.siam.package_common.entity.BasicResult;
import com.siam.system.modular.package_order.entity.internal.PointsMallOrderLogistics;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/rest/admin/pointsMall/orderLogistics")
@Transactional(rollbackFor = Exception.class)
@Api(tags = "后台订单物流跟踪信息模块相关接口", description = "AdminPointsMallOrderLogisticsController")
public class AdminPointsMallOrderLogisticsController {

    @Autowired
    private PointsMallOrderLogisticsService orderLogisticsService;

    @ApiOperation(value = "订单物流跟踪信息列表")
    @PostMapping(value = "/list")
    public BasicResult list(@RequestBody @Validated(value = {}) PointsMallOrderLogistics orderLogistics){
        BasicData basicResult = new BasicData();

        Page page = orderLogisticsService.getListByPage(orderLogistics.getPageNo(), orderLogistics.getPageSize(), orderLogistics);

        return BasicResult.success(page);
    }
}