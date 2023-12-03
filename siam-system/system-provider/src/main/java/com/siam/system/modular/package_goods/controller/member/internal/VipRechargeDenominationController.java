package com.siam.system.modular.package_goods.controller.member.internal;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.system.modular.package_goods.service.internal.VipRechargeDenominationService;
import com.siam.package_common.entity.BasicData;
import com.siam.package_common.entity.BasicResult;
import com.siam.system.modular.package_goods.entity.internal.VipRechargeDenomination;
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
@RequestMapping(value = "/rest/vipRechargeDenomination")
@Transactional(rollbackFor = Exception.class)
@Api(tags = "会员充值面额模块相关接口", description = "VipRechargeDenominationController")
public class VipRechargeDenominationController {

    @Autowired
    private VipRechargeDenominationService vipRechargeDenominationService;

    @ApiOperation(value = "会员充值面额列表")
    @PostMapping(value = "/list")
    public BasicResult list(@RequestBody @Validated(value = {}) VipRechargeDenomination vipRechargeDenomination){
        BasicData basicResult = new BasicData();
        Page<VipRechargeDenomination> page = vipRechargeDenominationService.getListByPage(vipRechargeDenomination.getPageNo(), vipRechargeDenomination.getPageSize(), vipRechargeDenomination);

        return BasicResult.success(page);
    }
}