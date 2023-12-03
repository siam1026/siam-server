package com.siam.system.modular.package_user.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.package_common.annoation.AdminPermission;
import com.siam.package_common.entity.BasicResult;
import com.siam.package_common.model.valid_group.ValidGroupOfAudit;
import com.siam.package_common.model.valid_group.ValidGroupOfId;
import com.siam.system.modular.package_user.model.param.MerchantWithdrawRecordParam;
import com.siam.system.modular.package_user.service.MerchantWithdrawRecordService;
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
@RequestMapping(value = "/rest/admin/merchantWithdrawRecord")
@Transactional(rollbackFor = Exception.class)
@Api(tags = "后台商家提现记录模块相关接口", description = "AdminMerchantWithdrawRecordController")
public class AdminMerchantWithdrawRecordController {

    @Autowired
    private MerchantWithdrawRecordService merchantWithdrawRecordService;

    @ApiOperation(value = "商家提现记录列表")
    @PostMapping(value = "/list")
    public BasicResult list(@RequestBody @Validated(value = {}) MerchantWithdrawRecordParam param){
        Page<Map<String, Object>> page = merchantWithdrawRecordService.getListByPageJoinShop(param);
        return BasicResult.success(page);
    }

    @ApiOperation(value = "商家提现记录-统计金额")
    @PostMapping(value = "/statisticalAmount")
    public BasicResult statisticalAmount(@RequestBody @Validated(value = {}) MerchantWithdrawRecordParam param){
        Map<String, Object> map = merchantWithdrawRecordService.statisticalAmount(param);
        return BasicResult.success(map);
    }

    /**
     * 审核申请体现商家信息
     *
     * @return
     * @author 暹罗
     */
    @AdminPermission
    @PostMapping(value = "/auditApplyWithdraw")
    public BasicResult auditApplyWithdraw(@RequestBody @Validated(value = {ValidGroupOfId.class, ValidGroupOfAudit.class}) MerchantWithdrawRecordParam param){
        merchantWithdrawRecordService.auditApplyWithdraw(param);
        return BasicResult.success();
    }
}