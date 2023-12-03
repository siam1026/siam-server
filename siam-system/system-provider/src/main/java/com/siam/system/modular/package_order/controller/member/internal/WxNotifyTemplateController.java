package com.siam.system.modular.package_order.controller.member.internal;

import com.siam.package_common.entity.BasicData;
import com.siam.package_common.entity.BasicResult;
import com.siam.package_common.constant.BasicResultCode;
import com.siam.system.modular.package_order.service.OrderService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@Slf4j
@RestController
@RequestMapping(value = "/rest/wxNotifyTemplate")
@Transactional(rollbackFor = Exception.class)
@Api(tags = "微信服务通知模板模块相关接口", description = "WxNotifyTemplateController")
public class WxNotifyTemplateController {

    @Autowired
    private OrderService orderService;

    /**
     * 订单模块模板
     *
     * @return
     * @author 暹罗
     */
    @PostMapping(value = "/orderModule/list")
    public BasicResult listOfOrderModule(){
        BasicData basicResult = new BasicData();

        List list = new ArrayList();
        list.add("eF29ugG7ZKKKHCDH2Nk48Q2JCH1qKDLBMX2LnAzCz-w"); //自取消息提醒
        list.add("YUNE7UPxeqqZZpVFzLWz51fKq3i045SzVwOJKZ_pEiw"); //订单配送通知
        list.add("iclsEo9mCyiVNDT4lZjb3NI6_H6cgZaC3e7IiI2XnxY"); //退款成功通知

        return BasicResult.success(list);
    }
}