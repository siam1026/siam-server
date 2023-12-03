package com.siam.system.modular.package_order.controller.member;

import com.siam.package_common.constant.BasicResultCode;
import com.siam.package_common.entity.BasicData;
import com.siam.package_common.entity.BasicResult;
import com.siam.package_common.exception.StoneCustomerException;
import com.siam.system.modular.package_order.entity.DeliveryAddress;
import com.siam.system.modular.package_order.model.param.CommonParam;
import com.siam.system.modular.package_order.service.CommonService;
import com.siam.system.modular.package_order.service.DeliveryAddressService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
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
import java.math.BigDecimal;

@Slf4j
@RestController
@RequestMapping(value = "/rest/common")
@Transactional(rollbackFor = Exception.class)
@Api(tags = "通用模块相关接口", description = "CommonController")
public class CommonController {

    @Autowired
    private CommonService commonService;

    @Autowired
    private DeliveryAddressService deliveryAddressService;

//    @Autowired
//    private SettingService settingService;

//    @Autowired
//    private ShopService shopService;

    @ApiOperation(value = "查询订单配送费")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "deliveryAddressId", value = "收货地址id", required = false, paramType = "query", dataType = "int")
    })
    @PostMapping(value = "/selectDeliveryFee")
    public BasicResult selectDeliveryFee(@RequestBody @Validated(value = {}) CommonParam param) {
        BasicData basicResult = new BasicData();

        DeliveryAddress dbDeliveryAddress = deliveryAddressService.selectByPrimaryKey(param.getDeliveryAddressId());
        if(dbDeliveryAddress == null){
            throw new StoneCustomerException("该收货地址不存在");
        }

        //拼接省、市、区、街道
        /*String addressA = dbDeliveryAddress.getProvince() + dbDeliveryAddress.getCity() + dbDeliveryAddress.getArea() + dbDeliveryAddress.getStreet();*/
        BigDecimal deliveryFee = commonService.selectDeliveryFee(dbDeliveryAddress.getLongitude(), dbDeliveryAddress.getLatitude(), param.getShopId());

        return BasicResult.success(deliveryFee);
    }

    /*@ApiOperation(value = "查询是否有门店可以配送")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "deliveryAddressId", value = "收货地址id", required = false, paramType = "query", dataType = "int")
    })
    @PostMapping(value = "/selectHasShopDelivery")
    public BasicResult selectHasShopDelivery(@RequestParam(required = false) Integer deliveryAddressId){
        BasicData basicResult = new BasicData();

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

        //获取默认门店
        Setting setting = settingService.selectCurrent();
        int shopId = setting.getDefaultShopId();
        Shop shop = shopService.selectByPrimaryKey(shopId);

        //返回是否营业时间
        Boolean result = false;
        Date date = new Date();
        String time = sdf.format(date);
        String[] timeArray = time.split(":");
        String[] startTimeArray = shop.getStartTime().split(":");
        String[] endTimeArray = shop.getEndTime().split(":");

        Integer timeArrayHour = Integer.valueOf(timeArray[0]);
        Integer timeArrayMinute = Integer.valueOf(timeArray[1]);
        Integer startTimeArrayHour = Integer.valueOf(startTimeArray[0]);
        Integer startTimeArrayMinute = Integer.valueOf(startTimeArray[1]);
        Integer endTimeArrayHour = Integer.valueOf(endTimeArray[0]);
        Integer endTimeArrayMinute = Integer.valueOf(endTimeArray[1]);

        if(endTimeArrayHour>startTimeArrayHour||(endTimeArrayHour==startTimeArrayHour&&endTimeArrayMinute>startTimeArrayMinute)){
            //不跨天
            if (timeArrayHour>startTimeArrayHour||(timeArrayHour==startTimeArrayHour&&timeArrayMinute>startTimeArrayMinute)) {
                if(timeArrayHour<endTimeArrayHour||(timeArrayHour==endTimeArrayHour&&timeArrayMinute<endTimeArrayMinute)){
                    result = true;
                }
            }
        }else{
            //跨天
            if(timeArrayHour>startTimeArrayHour||(timeArrayHour==startTimeArrayHour&&timeArrayMinute>startTimeArrayMinute)){
                //跨天1-当天大于开始时间
                result = true;
            }else if(timeArrayHour<endTimeArrayHour||(timeArrayHour==endTimeArrayHour&&timeArrayMinute<startTimeArrayMinute)){
                //跨天2-第二天小于结束时间
                result = true;
            }
        }

        basicResult.setData(result);
        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("查询成功");
        return basicResult;
    }*/

//    @Autowired
//    private WebSocket webSocket;
//
//    @ApiOperation(value = "websocket推送消息给前端")
//    @GetMapping(value = "/websocketPush")
//    public BasicResult websocketPush(String name, String mark){
//        BasicResult basicResult = new BasicResult();
//
//        /*webSocket.AppointSending("admin", "我是gpx'father");*/
//        webSocket.AppointSending(name, mark);
//        /*webSocket.AppointSending("admin", "newOrder");
//        webSocket.AppointSending("admin", "orderOvertime");
//        webSocket.AppointSending("admin", "orderApplyRefund");
//        webSocket.AppointSending("admin", "orderChangeToDelivery");*/
//
//        basicResult.setSuccess(true);
//        basicResult.setCode(BasicResultCode.SUCCESS);
//        basicResult.setMessage("查询成功");
//        return basicResult;
//    }
}