package com.siam.system.modular.package_user.controller.merchant;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.package_common.entity.BasicResult;
import com.siam.system.modular.package_user.entity.MerchantBillingRecord;
import com.siam.system.modular.package_user.model.param.MerchantBillingRecordParam;
import com.siam.system.modular.package_user.service.MerchantBillingRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(value = "/rest/merchant/merchantBillingRecord")
@Transactional(rollbackFor = Exception.class)
@Api(tags = "商家账单记录模块相关接口", description = "merchantBillingRecordController")
public class MerchantBillingRecordController {

    @Autowired
    private MerchantBillingRecordService merchantBillingRecordService;

    @ApiOperation(value = "商家账单记录列表")
    @PostMapping(value = "/list")
    public BasicResult list(@RequestBody @Validated(value = {}) MerchantBillingRecordParam param) {
        Page<MerchantBillingRecord> page = merchantBillingRecordService.getListByPage(param);
        return BasicResult.success(page);
    }

    @ApiOperation(value = "商家账单记录-统计金额")
    @PostMapping(value = "/statisticalAmount")
    public BasicResult statisticalAmount(@RequestBody @Validated(value = {}) MerchantBillingRecordParam param) {
        Map<String, Object> map = merchantBillingRecordService.statisticalAmount(param);
        return BasicResult.success(map);
    }
}