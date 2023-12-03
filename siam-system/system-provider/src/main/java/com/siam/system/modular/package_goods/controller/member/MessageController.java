package com.siam.system.modular.package_goods.controller.member;

import com.siam.package_common.constant.BusinessType;
import com.siam.package_common.entity.BasicResult;
import com.siam.system.modular.package_goods.entity.SysMessage;
import com.siam.system.modular.package_goods.service.MessageService;
import com.siam.system.modular.package_user.model.param.MerchantParam;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/rest/message")
@Transactional(rollbackFor = Exception.class)
@Api(tags = "系统消息模块相关接口", description = "MessageController")
public class MessageController {

    @Autowired
    private MessageService messageService;

    /**
     * 发送消息-demo
     *
     * @author 暹罗
     */
    @PostMapping(value = "/demo")
    public BasicResult login(@RequestBody @Validated(value = {}) MerchantParam param) {
        //TODO-给调度后台发送一条系统消息
        SysMessage sysMessage = new SysMessage();
        sysMessage.setUserId(BusinessType.ADMIN_ID);
        sysMessage.setUserType(SysMessage.USER_TYPE_ADMIN);
        sysMessage.setTitle("新商家注册提醒");
        sysMessage.setContent("商家信息：\n账号: " + "mall-merchant-001" + "\n手机号: " + "18711389426");
        messageService.insertSelective(sysMessage);
        return BasicResult.success();
    }
}