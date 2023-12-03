package com.siam.system.modular.package_goods.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.package_common.service.AliyunSms;
import com.siam.system.modular.package_goods.service.PaperworkPushService;
import com.siam.package_common.entity.BasicData;
import com.siam.package_common.entity.BasicResult;
import com.siam.package_common.constant.BasicResultCode;
import com.siam.system.modular.package_goods.entity.PaperworkPush;
import com.siam.system.modular.package_user.model.example.MemberExample;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping(value = "/rest/admin/paperworkPush")
@Transactional(rollbackFor = Exception.class)
@Api(tags = "后台文案推送模块相关接口", description = "AdminPaperworkPushController")
public class AdminPaperworkPushController {

    @Autowired
    private PaperworkPushService paperworkPushService;

//    @Autowired
//    private MemberService memberService;

    @Autowired
    private AliyunSms aliyunSms;

    @ApiOperation(value = "文案列表")
    @PostMapping(value = "/list")
    public BasicResult list(@RequestBody @Validated(value = {}) PaperworkPush paperworkPush){
        BasicData basicResult = new BasicData();
        Page<PaperworkPush> page = paperworkPushService.getList(paperworkPush.getPageNo(), paperworkPush.getPageSize(), paperworkPush);

        return BasicResult.success(page);
    }

    @ApiOperation(value = "文案创建")
    @PostMapping(value = "/insert")
    public BasicResult insert(@RequestBody @Validated(value = {}) PaperworkPush paperworkPush){
        BasicResult basicResult = new BasicResult();

        paperworkPush.setCreateTime(new Date());
        paperworkPush.setUpdateTime(new Date());
        paperworkPushService.insertSelective(paperworkPush);

        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("创建成功");
        return basicResult;
    }


    @ApiOperation(value = "文案修改")
    @PutMapping(value = "/update")
    public BasicResult update(@RequestBody @Validated(value = {}) PaperworkPush paperworkPush){
        BasicResult basicResult = new BasicResult();

        paperworkPush.setUpdateTime(new Date());
        paperworkPushService.updateByPrimaryKeySelective(paperworkPush);

        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("修改成功");
        return basicResult;
    }


    @ApiOperation(value = "文案删除")
    @DeleteMapping(value = "/delete")
    public BasicResult delete(@RequestBody @Validated(value = {}) PaperworkPush param){
        BasicResult basicResult = new BasicResult();
        for (Integer id : param.getIds()) {
            paperworkPushService.deleteByPrimaryKey(id);
        }
        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("删除成功");
        return basicResult;
    }

    @ApiOperation(value = "推送")
    @PutMapping(value = "/push")
    public BasicResult push(@RequestBody @Validated(value = {}) PaperworkPush paperworkPush){
        BasicResult basicResult = new BasicResult();

        PaperworkPush dbPaperworkPush = paperworkPushService.selectByPrimaryKey(paperworkPush.getId());

        //以短信方式推送给系统中所有的用户
        //查询出所有的用户
        MemberExample queryMemberExample = new MemberExample();
//        List<Member> memberList = memberService.selectByExample(queryMemberExample);
//
//        //给所有用户派发优惠卷
//        for (Member member : memberList) {
//            //TODO-发送短信
//            //aliyunSms.sendPointsMallCouponsDistributeReminderMessage(member.getMobile(), coupons.getName());
//        }

        //修改该条文案推送的相关信息
        PaperworkPush updatePaperworkPush = new PaperworkPush();
        updatePaperworkPush.setId(dbPaperworkPush.getId());
        updatePaperworkPush.setPushedNumber(dbPaperworkPush.getPushedNumber() + 1);
        updatePaperworkPush.setLastPushedTime(new Date());
        paperworkPushService.updateByPrimaryKeySelective(updatePaperworkPush);

        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("推送成功");
        return basicResult;
    }
}