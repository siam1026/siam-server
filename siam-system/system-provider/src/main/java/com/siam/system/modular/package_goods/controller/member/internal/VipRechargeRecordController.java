package com.siam.system.modular.package_goods.controller.member.internal;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.package_common.exception.StoneCustomerException;
import com.siam.system.modular.package_user.auth.cache.MemberSessionManager;
import com.siam.system.modular.package_user.entity.Member;
import com.siam.package_common.entity.BasicData;
import com.siam.package_common.entity.BasicResult;
import com.siam.package_common.constant.BasicResultCode;
import com.siam.package_common.constant.Quantity;
import com.siam.system.modular.package_goods.entity.internal.VipRechargeRecord;
import com.siam.system.modular.package_goods.service.internal.VipRechargeRecordService;
import com.siam.system.util.TokenUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping(value = "/rest/member/vipRechargeRecord")
@Transactional(rollbackFor = Exception.class)
@Api(tags = "会员充值记录模块相关接口", description = "VipRechargeRecordController")
public class VipRechargeRecordController {

    @Autowired
    private VipRechargeRecordService vipRechargeRecordService;

    @Autowired
    private MemberSessionManager memberSessionManager;

    @ApiOperation(value = "会员充值记录列表")
    @PostMapping(value = "/list")
    public BasicResult list(@RequestBody @Validated(value = {}) VipRechargeRecord vipRechargeRecord, HttpServletRequest request){
        BasicData basicResult = new BasicData();
        Member loginMember = memberSessionManager.getSession(TokenUtil.getToken());

        //只查询当前登录用户的会员充值记录
        vipRechargeRecord.setMemberId(loginMember.getId());
        vipRechargeRecord.setStatus(Quantity.INT_2);
        Page<VipRechargeRecord> page = vipRechargeRecordService.getListByPage(vipRechargeRecord.getPageNo(), vipRechargeRecord.getPageSize(), vipRechargeRecord);

        return BasicResult.success(page);
    }

    @ApiOperation(value = "查看会员充值记录详情")
    @PostMapping(value = "/detail")
    public BasicResult detail(@RequestBody @Validated(value = {}) VipRechargeRecord vipRechargeRecord, HttpServletRequest request){
        BasicData basicResult = new BasicData();
        Member loginMember = memberSessionManager.getSession(TokenUtil.getToken());

        VipRechargeRecord dbVipRechargeRecord = vipRechargeRecordService.selectByPrimaryKey(vipRechargeRecord.getId());
        log.debug("\n\nvip-memberid: " + dbVipRechargeRecord.getMemberId());
        log.debug("\n\nmembertoken-memberid: " + loginMember.getId());
        log.debug("\n\n==: " + (dbVipRechargeRecord.getMemberId() == loginMember.getId()));
        log.debug("\n\nequals: " + (dbVipRechargeRecord.getMemberId().equals(loginMember.getId())));
        if (!dbVipRechargeRecord.getMemberId().equals(loginMember.getId())){
            throw new StoneCustomerException("您没有权限查看该记录");
        }

        basicResult.setData(dbVipRechargeRecord);
        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("查询成功");
        return basicResult;
    }
}