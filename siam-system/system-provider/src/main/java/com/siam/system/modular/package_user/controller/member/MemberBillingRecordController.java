package com.siam.system.modular.package_user.controller.member;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.package_common.entity.BasicResult;
import com.siam.system.modular.package_user.entity.MemberBillingRecord;
import com.siam.system.modular.package_user.model.param.MemberBillingRecordParam;
import com.siam.system.modular.package_user.service.MemberBillingRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户账单记录模块相关接口
 *
 * @author 暹罗
 */
@RestController
@RequestMapping(value = "/rest/member/billingRecord")
@Transactional(rollbackFor = Exception.class)
public class MemberBillingRecordController {

    @Autowired
    private MemberBillingRecordService memberBillingRecordService;

    /**
     * 用户账单记录列表
     *
     * @author 暹罗
     */
    @PostMapping(value = "/list")
    public BasicResult list(@RequestBody @Validated(value = {}) MemberBillingRecordParam param){
        Page<MemberBillingRecord> page = memberBillingRecordService.getListByPage(param);
        return BasicResult.success(page);
    }

    /**
     * 查看用户账单记录详情
     *
     * @author 暹罗
     */
    @PostMapping(value = "/selectById")
    public BasicResult selectById(@RequestBody @Validated(value = {}) MemberBillingRecordParam param){
        MemberBillingRecord memberBillingRecord = memberBillingRecordService.selectByPrimaryKey(param);
        return BasicResult.success(memberBillingRecord);
    }
}
