package com.siam.system.modular.package_goods.controller.admin.internal;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.system.modular.package_goods.service.internal.VipRechargeDenominationCouponsRelationService;
import com.siam.package_common.entity.BasicData;
import com.siam.package_common.entity.BasicResult;
import com.siam.package_common.constant.BasicResultCode;
import com.siam.package_common.util.GsonUtils;
import com.siam.system.modular.package_goods.entity.internal.VipRechargeDenominationCouponsRelation;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/rest/admin/vipRechargeDenominationCouponsRelation")
@Transactional(rollbackFor = Exception.class)
@Api(tags = "后台会员充值面额与赠送优惠券关联接口", description = "AdminCouponsController")
public class AdminVipRechargeDenominationCouponsRelationController {

    @Autowired
    private VipRechargeDenominationCouponsRelationService vipRechargeDenominationCouponsRelationService;

    @ApiOperation(value = "新增会员充值面额与赠送优惠券关联")
    @PostMapping(value = "/insert")
    public BasicResult insert(@RequestBody @Validated(value = {}) VipRechargeDenominationCouponsRelation param) {
        BasicResult basicResult = new BasicResult();

        List<VipRechargeDenominationCouponsRelation> denominationCouponsRelationList = GsonUtils.toList(param.getDenominationCouponsRelationListStr(), VipRechargeDenominationCouponsRelation.class);
        vipRechargeDenominationCouponsRelationService.insertSelective(param.getVipRechargeDenominationId(), denominationCouponsRelationList);

        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("新增成功");
        return basicResult;
    }

    @ApiOperation(value = "会员充值面额与赠送优惠券关联列表查询")
    @PostMapping(value = "/list")
    public BasicResult list(@RequestBody @Validated(value = {}) VipRechargeDenominationCouponsRelation vipRechargeDenominationCouponsRelation) {
        BasicData basicResult = new BasicData();

        Page<VipRechargeDenominationCouponsRelation> page = vipRechargeDenominationCouponsRelationService.getListByPage(vipRechargeDenominationCouponsRelation.getPageNo(), vipRechargeDenominationCouponsRelation.getPageSize(), vipRechargeDenominationCouponsRelation);

        return BasicResult.success(page);
    }
}