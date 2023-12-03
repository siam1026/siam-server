package com.siam.system.modular.package_goods.controller.member;

import com.siam.package_common.constant.BasicResultCode;
import com.siam.package_common.entity.BasicResult;
import com.siam.package_common.exception.StoneCustomerException;
import com.siam.package_common.service.AliyunSms;
import com.siam.package_common.util.CommonUtils;
import com.siam.package_common.util.RedisUtils;
import com.siam.system.modular.package_goods.entity.SmsLog;
import com.siam.system.modular.package_goods.service.SmsLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping(value = "/rest/smsLog")
@Transactional(rollbackFor = Exception.class)
@Api(tags = "短信验证码记录模块相关接口", description = "SmsLogController")
public class SmsLogController {

    @Autowired
    private SmsLogService smsLogService;

    @Autowired
    private AliyunSms aliyunSms;

    @Autowired
    private RedisUtils redisUtils;

    @PostMapping(value = "/sendMobileCode")
    @ApiOperation(value = "发送手机验证码")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "mobile", value = "手机号", required = true, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "type", value = "短信类型 注册=register;登录=login;验证手机号=verification;找回密码=findpwd", required = true, paramType = "query", dataType = "string")
    })
    public BasicResult sendMobileCode(@RequestBody @Validated(value = {}) SmsLog smsLog){
        BasicResult basicResult = new BasicResult();
        if(!CommonUtils.isMobile(smsLog.getMobile())){
            basicResult.setSuccess(false);
            basicResult.setCode(BasicResultCode.ERR);
            basicResult.setMessage("手机号不合法");
            return basicResult;
        }

        String smsSwitch = (String) redisUtils.get("smsSwitch");
        if(smsSwitch != null && smsSwitch.equals("false")){
            throw new StoneCustomerException("暂不支持获取验证码，请用临时验证码 123456 进行操作");
        }

        // 发送短信验证码
        String mobileCode = aliyunSms.sendVerificationCodeMessage(smsLog.getMobile());

        // 添加smslog记录
        SmsLog insertSmsLog = new SmsLog();
        insertSmsLog.setMemberId(null);
        insertSmsLog.setMobile(smsLog.getMobile());
        insertSmsLog.setType(smsLog.getType());
        insertSmsLog.setCreateTime(new Date());
        insertSmsLog.setIp(CommonUtils.getServerIP());
        insertSmsLog.setVerifyCode(mobileCode);
        insertSmsLog.setDescription(null);
        /*insertSmsLog.setStates(Quantity.INT_1);*/
        smsLogService.insertSelective(insertSmsLog);

        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("发送成功");
        return basicResult;
    }
}