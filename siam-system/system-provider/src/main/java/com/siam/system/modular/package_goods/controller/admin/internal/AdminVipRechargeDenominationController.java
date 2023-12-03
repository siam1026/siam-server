package com.siam.system.modular.package_goods.controller.admin.internal;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.package_common.annoation.AdminPermission;
import com.siam.system.modular.package_goods.service.internal.VipRechargeDenominationCouponsRelationService;
import com.siam.system.modular.package_goods.service.internal.VipRechargeDenominationService;
import com.siam.package_common.entity.BasicData;
import com.siam.package_common.entity.BasicResult;
import com.siam.package_common.constant.BasicResultCode;
import com.siam.system.modular.package_goods.entity.internal.VipRechargeDenomination;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping(value = "/rest/admin/vipRechargeDenomination")
@Transactional(rollbackFor = Exception.class)
@Api(tags = "后台会员充值面额模块相关接口", description = "AdminVipRechargeDenominationController")
public class AdminVipRechargeDenominationController {

    @Autowired
    private VipRechargeDenominationService vipRechargeDenominationService;

    @Autowired
    private VipRechargeDenominationCouponsRelationService vipRechargeDenominationCouponsRelationService;

    @ApiOperation(value = "会员充值面额列表")
    @PostMapping(value = "/list")
    public BasicResult list(@RequestBody @Validated(value = {}) VipRechargeDenomination vipRechargeDenomination){
        BasicData basicResult = new BasicData();
        Page<VipRechargeDenomination> page = vipRechargeDenominationService.getListByPage(vipRechargeDenomination.getPageNo(), vipRechargeDenomination.getPageSize(), vipRechargeDenomination);

        return BasicResult.success(page);
    }

    @ApiOperation(value = "新增会员充值面额")
    @PostMapping(value = "/insert")
    public BasicResult insert(@RequestBody @Validated(value = {}) VipRechargeDenomination vipRechargeDenomination){
        BasicResult basicResult = new BasicResult();

        vipRechargeDenomination.setCreateTime(new Date());
        vipRechargeDenomination.setUpdateTime(new Date());
        vipRechargeDenominationService.insertSelective(vipRechargeDenomination);

        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("创建成功");
        return basicResult;
    }

    @AdminPermission
    @ApiOperation(value = "修改会员充值面额")
    @PutMapping(value = "/update")
    public BasicResult update(@RequestBody @Validated(value = {}) VipRechargeDenomination vipRechargeDenomination){
        BasicResult basicResult = new BasicResult();

        vipRechargeDenomination.setUpdateTime(new Date());
        vipRechargeDenominationService.updateByPrimaryKeySelective(vipRechargeDenomination);

        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("修改成功");
        return basicResult;
    }

    @AdminPermission
    @ApiOperation(value = "删除会员充值面额")
    @DeleteMapping(value = "/delete")
    public BasicResult delete(@RequestBody @Validated(value = {}) VipRechargeDenomination param){
        BasicResult basicResult = new BasicResult();

        VipRechargeDenomination dbVipRechargeDenomination = vipRechargeDenominationService.selectByPrimaryKey(param.getId());
        if(dbVipRechargeDenomination == null){
            basicResult.setSuccess(false);
            basicResult.setCode(BasicResultCode.ERR);
            basicResult.setMessage("该会员充值面额不存在，删除失败");
            return basicResult;
        }

        //删除会员充值面额
        vipRechargeDenominationService.deleteByPrimaryKey(param.getId());

        //级联删除与优惠券的关联
        vipRechargeDenominationCouponsRelationService.deleteByVipRechargeDenominationId(param.getId());

        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("删除成功");
        return basicResult;
    }
}