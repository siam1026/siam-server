package com.siam.system.modular.package_goods.controller.admin.internal;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.package_common.constant.BasicResultCode;
import com.siam.package_common.constant.Quantity;
import com.siam.package_common.entity.BasicData;
import com.siam.package_common.entity.BasicResult;
import com.siam.system.modular.package_goods.entity.internal.VipRechargeRecord;
import com.siam.system.modular.package_goods.service.internal.VipRechargeRecordService;
import com.siam.system.modular.package_user.entity.Member;
import com.siam.system.modular.package_user.service.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping(value = "/rest/admin/vipRechargeRecord")
@Transactional(rollbackFor = Exception.class)
@Api(tags = "后台会员充值记录模块相关接口", description = "AdminVipRechargeRecordController")
public class AdminVipRechargeRecordController {

    @Autowired
    private VipRechargeRecordService vipRechargeRecordService;

    @Autowired
    private MemberService memberService;

    @ApiOperation(value = "会员充值记录列表")
    @PostMapping(value = "/list")
    public BasicResult list(@RequestBody @Validated(value = {}) VipRechargeRecord vipRechargeRecord){
        BasicData basicResult = new BasicData();

        vipRechargeRecord.setStatus(Quantity.INT_2);
        Page<VipRechargeRecord> page = vipRechargeRecordService.getListByPageJoinMember(vipRechargeRecord.getPageNo(), vipRechargeRecord.getPageSize(), vipRechargeRecord);

        return BasicResult.success(page);
    }

    @ApiOperation(value = "会员充值记录-统计金额")
    @PostMapping(value = "/statisticalAmount")
    public BasicResult statisticalAmount(@RequestBody @Validated(value = {}) VipRechargeRecord vipRechargeRecord, HttpServletRequest request){
        BasicData basicResult = new BasicData();

        Map<String, Object> map = vipRechargeRecordService.statisticalAmount(vipRechargeRecord);

        basicResult.setData(map);
        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("查询成功");
        return basicResult;
    }

    @ApiOperation(value = "后台管理员充值会员")
    @PostMapping(value = "/updateVip")
    public BasicResult update(@RequestBody @Validated(value = {}) VipRechargeRecord vipRechargeRecord){
        log.info("请求参数 -> " + vipRechargeRecord);
        BasicResult basicResult = new BasicResult();

        Member dbMember = memberService.selectByPrimaryKey(vipRechargeRecord.getMemberId());
        log.info("dbMember -> " + dbMember);
        if(dbMember == null){
            basicResult.setSuccess(false);
            basicResult.setCode(BasicResultCode.ERR);
            basicResult.setMessage("该用户不存在，充值失败");
            return basicResult;
        }

        //默认充值12个月会员
        //int rechargeTime = 12;

        //判断是开通会员 还是 续费会员
        int recordType = (dbMember.getType() == Quantity.INT_1) ? Quantity.INT_1 : Quantity.INT_2;
        if(recordType == Quantity.INT_1){
            //修改用户信息 开通会员
            Member updateMember = new Member();
            updateMember.setId(dbMember.getId());
            updateMember.setVipStatus(Quantity.INT_1);
            updateMember.setVipType(Quantity.INT_1);
            updateMember.setVipStartTime(new Date());
            updateMember.setVipEndTime(DateUtils.addMonths(updateMember.getVipStartTime(), vipRechargeRecord.getRechargeTime()));
            updateMember.setType(Quantity.INT_2);
            memberService.updateByPrimaryKeySelective(updateMember);
        }else{
            //修改用户信息 续费会员
            Member updateMember = new Member();
            updateMember.setId(dbMember.getId());
            updateMember.setVipEndTime(DateUtils.addMonths(dbMember.getVipEndTime(), vipRechargeRecord.getRechargeTime()));
            memberService.updateByPrimaryKeySelective(updateMember);
        }

        //添加充值记录
        VipRechargeRecord insertVipRechargeRecord = new VipRechargeRecord();
        insertVipRechargeRecord.setMemberId(dbMember.getId());
        insertVipRechargeRecord.setChannel(Quantity.INT_2);
        insertVipRechargeRecord.setRechargeTime(vipRechargeRecord.getRechargeTime());
        insertVipRechargeRecord.setAmount(vipRechargeRecord.getAmount());
        insertVipRechargeRecord.setCreateTime(new Date());
        vipRechargeRecordService.insertSelective(insertVipRechargeRecord);

        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("充值成功");
        return basicResult;
    }

//    @ApiOperation(value = "会员充值记录创建")
//    @PostMapping(value = "/insert")
//    public BasicResult insert(VipRechargeRecord vipRechargeRecord){
//        BasicResult basicResult = new BasicResult();
//
//        vipRechargeRecord.setCreateTime(new Date());
//        vipRechargeRecordService.insertSelective(vipRechargeRecord);
//
//        basicResult.setSuccess(true);
//        basicResult.setCode(BasicResultCode.SUCCESS);
//        basicResult.setMessage("创建成功");
//        return basicResult;
//    }
}
