package com.siam.system.modular.package_user.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.package_common.entity.BasicResult;
import com.siam.package_common.model.valid_group.ValidGroupOfAudit;
import com.siam.package_common.model.valid_group.ValidGroupOfId;
import com.siam.system.modular.package_user.model.param.MemberWithdrawRecordParam;
import com.siam.system.modular.package_user.service.MemberWithdrawRecordService;
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
@RequestMapping(value = "/rest/admin/memberWithdrawRecord")
@Transactional(rollbackFor = Exception.class)
@Api(tags = "后台用户提现记录模块相关接口", description = "AdminMemberWithdrawRecordController")
public class AdminMemberWithdrawRecordController {

    @Autowired
    private MemberWithdrawRecordService memberWithdrawRecordService;

    @ApiOperation(value = "用户提现记录列表")
    @PostMapping(value = "/list")
    public BasicResult list(@RequestBody @Validated(value = {}) MemberWithdrawRecordParam param) {
        Page<Map<String, Object>> page = memberWithdrawRecordService.getListByPageJoinMember(param);
        return BasicResult.success(page);
    }

    @ApiOperation(value = "用户提现记录-统计金额")
    @PostMapping(value = "/statisticalAmount")
    public BasicResult statisticalAmount(@RequestBody @Validated(value = {}) MemberWithdrawRecordParam param) {
        Map<String, Object> map = memberWithdrawRecordService.statisticalAmount(param);
        return BasicResult.success(map);
    }

    /**
     * 审核申请体现用户信息
     *
     * @return
     * @author 暹罗
     */
    @PostMapping(value = "/auditApplyWithdraw")
    public BasicResult auditApplyWithdraw(@RequestBody @Validated(value = {ValidGroupOfId.class, ValidGroupOfAudit.class}) MemberWithdrawRecordParam param){
        this.memberWithdrawRecordService.auditApplyWithdraw(param);
        return BasicResult.success();
    }
}