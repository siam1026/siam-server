package com.siam.system.modular.package_user.controller.member;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.package_common.entity.BasicResult;
import com.siam.system.modular.package_user.entity.Member;
import com.siam.system.modular.package_user.model.param.MemberInviteRelationParam;
import com.siam.system.modular.package_user.service.MemberInviteRelationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/rest/memberInviteRelation")
@Transactional(rollbackFor = Exception.class)
@Api(tags = "用户邀请关系模块相关接口", description = "MemberInviteRelationController")
public class MemberInviteRelationController {

    @Autowired
    private MemberInviteRelationService memberInviteRelationService;

    @ApiOperation(value = "通过邀请者id获取邀请的用户")
    @PostMapping(value = "/getMemberListByInviterId")
    public BasicResult getUserListByInviterId(@RequestBody @Validated(value = {}) MemberInviteRelationParam param) {
        Page<Member> result = memberInviteRelationService.getMemberListByInviterId(param);
        return BasicResult.success(result);
    }

    /*@ApiOperation(value = "新用户被邀请接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mobile", value = "用户手机号", required = true, paramType = "query", dataType = "int", defaultValue = "null"),
            @ApiImplicitParam(name = "inviterId", value = "邀请者id", required = true, paramType = "query", dataType = "int", defaultValue = "null")
    })
    @PostMapping(value = "/newMemberInvite")
    public BasicResult getLoginMemberInfo(@RequestParam String mobile,@RequestParam Integer inviterId) {
        BasicResult basicResult = new BasicResult();

        memberInviteRelationService.insertSelective(mobile, inviterId);

        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("注册成功");
        return basicResult;
    }*/
}