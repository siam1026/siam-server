package com.siam.system.modular.package_goods.controller.member;

import com.siam.system.modular.package_goods.service.SystemUsageRecordService;
import com.siam.package_common.entity.BasicResult;
import com.siam.package_common.constant.BasicResultCode;
import com.siam.system.modular.package_goods.entity.SystemUsageRecord;
import com.siam.system.modular.package_user.auth.cache.MemberSessionManager;
import com.siam.system.modular.package_user.entity.Member;
import com.siam.system.util.TokenUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RestController
@RequestMapping(value = "/rest/systemUsageRecord")
@Transactional(rollbackFor = Exception.class)
@Api(tags = "后台系统使用记录模块相关接口", description = "SystemUsageRecordController")
public class SystemUsageRecordController {

    @Autowired
    private SystemUsageRecordService systemUsageRecordService;

    @Autowired
    private MemberSessionManager memberSessionManager;

    @ApiOperation(value = "新增系统使用记录")
    @PostMapping(value = "/insert")
    public BasicResult insert(@RequestBody @Validated(value = {}) SystemUsageRecord systemUsageRecord, HttpServletRequest request){
        BasicResult basicResult = new BasicResult();

        String token = request.getHeader("token");
        //用户不一定处于登录状态，所以要做判断
        if(StringUtils.isNotBlank(token)){
            Member loginMember = memberSessionManager.getSession(TokenUtil.getToken());
            if(loginMember != null){
                systemUsageRecord.setMemberId(loginMember.getId());
            }
        }

        systemUsageRecord.setCreateTime(new Date());
        systemUsageRecordService.insertSelective(systemUsageRecord);

        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("创建成功");
        return basicResult;
    }
}