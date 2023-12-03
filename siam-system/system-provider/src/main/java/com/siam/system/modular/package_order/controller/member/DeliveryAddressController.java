package com.siam.system.modular.package_order.controller.member;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.package_common.entity.BasicData;
import com.siam.package_common.entity.BasicResult;
import com.siam.package_common.constant.BasicResultCode;
import com.siam.package_common.constant.Quantity;
import com.siam.system.modular.package_order.entity.DeliveryAddress;
import com.siam.system.modular.package_order.service.DeliveryAddressService;
import com.siam.system.modular.package_user.auth.cache.MemberSessionManager;
import com.siam.system.modular.package_user.entity.Member;
import com.siam.system.util.TokenUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RestController
@RequestMapping(value = "/rest/member/deliveryAddress")
@Transactional(rollbackFor = Exception.class)
@Api(tags = "收货地址模块相关接口", description = "DeliveryAddressController")
public class DeliveryAddressController {

    @Autowired
    private DeliveryAddressService deliveryAddressService;

    @Autowired
    private MemberSessionManager memberSessionManager;

    @ApiOperation(value = "收货地址列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "收货地址表主键id", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "realname", value = "收件人姓名", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "phone", value = "联系电话", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "sex", value = "收件人性别 0=男 1=女", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "area", value = "地区(收货地址)", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "street", value = "街道/详细收货地址", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "isDefault", value = "是否默认收获地址 0=否 1=是", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "isDisabled", value = "是否禁用 0=启用 1=禁用", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "pageNo", value = "页码(值为-1不分页)", required = true, paramType = "query", dataType = "int", defaultValue = "1"),
            @ApiImplicitParam(name = "pageSize", value = "页数", required = true, paramType = "query", dataType = "int", defaultValue = "20"),
    })
    @PostMapping(value = "/list")
    public BasicResult list(@RequestBody @Validated(value = {}) DeliveryAddress deliveryAddress, HttpServletRequest request){
        BasicData basicResult = new BasicData();
        Member loginMember = memberSessionManager.getSession(TokenUtil.getToken());

        deliveryAddress.setMemberId(loginMember.getId());
        Page page = deliveryAddressService.getListByPage(deliveryAddress.getPageNo(), deliveryAddress.getPageSize(), deliveryAddress);

        // 对手机号码做加密处理 (从这种list中修改数据时，我体会到了按址传递和按值传递的意义所在)
        /*List<DeliveryAddress> list = page.getRecords();
        list.forEach(address -> {
            String phone = address.getPhone();
            if(phone.length() == 11){
                phone = phone.substring(0, 3) + "*****" + phone.substring(phone.length()-3);
                address.setPhone(phone);
            }
        });*/

        return BasicResult.success(page);
    }

    @ApiOperation(value = "新增收货地址")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "memberId", value = "用户id", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "realname", value = "收件人姓名", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "sex", value = "收件人性别 0=男 1=女", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "phone", value = "联系电话", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "province", value = "省份(收货地址)", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "city", value = "市区(收货地址)", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "area", value = "地区(收货地址)", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "street", value = "街道/详细收货地址", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "isDefault", value = "是否默认收获地址 0=否 1=是", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "isDisabled", value = "是否禁用 0=启用 1=禁用", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "isDeleted", value = "是否删除 0=正常 1=已删除", required = false, paramType = "query", dataType = "int")

    })
    @PostMapping(value = "/insert")
    public BasicResult insert(@RequestBody @Validated(value = {}) DeliveryAddress deliveryAddress, HttpServletRequest request){
        BasicData basicResult = new BasicData();
        Member loginMember = memberSessionManager.getSession(TokenUtil.getToken());

        // 添加DeliveryAddress数据
        deliveryAddress.setMemberId(loginMember.getId());
        deliveryAddress.setCreateTime(new Date());
        deliveryAddress.setUpdateTime(new Date());
        deliveryAddressService.insertSelective(deliveryAddress);

        //将不等于当前这个地址，这个用户下其他的地址默认设置为否
        if(deliveryAddress.getIsDefault() == Quantity.INT_1){
            deliveryAddressService.updateIsDefaultExclusion(deliveryAddress.getId(), deliveryAddress.getMemberId());
        }

        basicResult.setData(deliveryAddress.getId());
        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("新增成功");
        return basicResult;
    }

    @ApiOperation(value = "修改收货地址")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "收货地址表主键id", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "realname", value = "收件人姓名", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "phone", value = "联系电话", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "sex", value = "收件人性别 0=男 1=女", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "province", value = "省份(收货地址)", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "city", value = "市区(收货地址)", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "area", value = "地区(收货地址)", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "street", value = "街道/详细收货地址", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "isDisabled", value = "是否禁用 0=启用 1=禁用", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "isDefault", value = "是否默认收获地址 0=否 1=是", required = true, paramType = "query", dataType = "int")
    })
    @PostMapping(value = "/update")
    public BasicResult update(@RequestBody @Validated(value = {}) DeliveryAddress deliveryAddress, HttpServletRequest request){
        BasicResult basicResult = new BasicResult();
        Member loginMember = memberSessionManager.getSession(TokenUtil.getToken());

        DeliveryAddress dbDeliveryAddress = deliveryAddressService.selectByPrimaryKey(deliveryAddress.getId());
        if(dbDeliveryAddress == null){
            basicResult.setSuccess(false);
            basicResult.setCode(BasicResultCode.ERR);
            basicResult.setMessage("该收货地址不存在");
            return basicResult;
        }

        if(!dbDeliveryAddress.getMemberId().equals(loginMember.getId())){
            basicResult.setSuccess(false);
            basicResult.setCode(BasicResultCode.ERR);
            basicResult.setMessage("该收货地址不是你的，不允许修改");
            return basicResult;
        }

        // 更新DeliveryAddress数据
        deliveryAddress.setUpdateTime(new Date());
        deliveryAddressService.updateByPrimaryKeySelective(deliveryAddress);

        //将不等于当前这个地址，这个用户下其他的地址默认设置为否
        if(deliveryAddress.getIsDefault() == Quantity.INT_1){
            deliveryAddressService.updateIsDefaultExclusion(dbDeliveryAddress.getId(), dbDeliveryAddress.getMemberId());
        }

        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("修改成功");
        return basicResult;
    }


    @ApiOperation(value = "删除收货地址")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "收货地址表主键id", required = false, paramType = "query", dataType = "int")
    })
    @PostMapping(value = "/delete")
    public BasicResult delete(@RequestBody @Validated(value = {}) DeliveryAddress param){
        BasicResult basicResult = new BasicResult();
        Member loginMember = memberSessionManager.getSession(TokenUtil.getToken());

        DeliveryAddress dbDeliveryAddress = deliveryAddressService.selectByPrimaryKey(param.getId());
        if(dbDeliveryAddress == null){
            basicResult.setSuccess(false);
            basicResult.setCode(BasicResultCode.ERR);
            basicResult.setMessage("该收货地址不存在");
            return basicResult;
        }

        if(!dbDeliveryAddress.getMemberId().equals(loginMember.getId())){
            basicResult.setSuccess(false);
            basicResult.setCode(BasicResultCode.ERR);
            basicResult.setMessage("该收货地址不是你的，不允许删除");
            return basicResult;
        }

        //删除DeliveryAddress数据
        deliveryAddressService.deleteByPrimaryKey(param.getId());

        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("删除成功");
        return basicResult;
    }


    @ApiOperation(value = "设为默认收货地址")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "收货地址表主键id", required = false, paramType = "query", dataType = "int")
    })
    @PostMapping(value = "/updateIsDefault")
    public BasicResult updateIsDefault(@RequestBody @Validated(value = {}) DeliveryAddress param){
        BasicResult basicResult = new BasicResult();
        Member loginMember = memberSessionManager.getSession(TokenUtil.getToken());

        DeliveryAddress dbDeliveryAddress = deliveryAddressService.selectByPrimaryKey(param.getId());
        if(dbDeliveryAddress == null){
            basicResult.setSuccess(false);
            basicResult.setCode(BasicResultCode.ERR);
            basicResult.setMessage("该收货地址不存在");
            return basicResult;
        }

        if(!dbDeliveryAddress.getMemberId().equals(loginMember.getId())){
            basicResult.setSuccess(false);
            basicResult.setCode(BasicResultCode.ERR);
            basicResult.setMessage("该收货地址不是你的，不允许修改");
            return basicResult;
        }

        // 更新DeliveryAddress数据
        DeliveryAddress updateDeliveryAddress = new DeliveryAddress();
        updateDeliveryAddress.setId(dbDeliveryAddress.getId());
        updateDeliveryAddress.setIsDefault(Quantity.INT_1);
        deliveryAddressService.updateByPrimaryKeySelective(updateDeliveryAddress);

        //将不等于当前这个地址，这个用户下其他的地址默认设置为否
        deliveryAddressService.updateIsDefaultExclusion(dbDeliveryAddress.getId(), dbDeliveryAddress.getMemberId());

        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("设置成功");
        return basicResult;
    }
}