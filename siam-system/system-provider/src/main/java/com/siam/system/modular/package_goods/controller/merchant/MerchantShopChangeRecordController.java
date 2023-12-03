package com.siam.system.modular.package_goods.controller.merchant;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.system.modular.package_goods.service.ShopChangeRecordService;
import com.siam.system.modular.package_goods.service.SettingService;
import com.siam.package_common.entity.BasicData;
import com.siam.package_common.entity.BasicResult;
import com.siam.system.modular.package_user.auth.cache.MerchantSessionManager;
import com.siam.system.modular.package_user.entity.Merchant;
import com.siam.system.util.TokenUtil;
import com.siam.system.modular.package_goods.entity.ShopChangeRecord;
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
@RequestMapping(value = "/rest/merchant/shopChangeRecord")
@Transactional(rollbackFor = Exception.class)
@Api(tags = "商家端门店重要信息变更记录模块相关接口", description = "ShopChangeRecordController")
public class MerchantShopChangeRecordController {
    @Autowired
    private ShopChangeRecordService shopChangeRecordService;

//    @Autowired
//    private MerchantService merchantService;

    @Autowired
    private SettingService settingService;

    @Autowired
    private MerchantSessionManager merchantSessionManager;

    @ApiOperation(value = "门店重要信息变更记录列表")
    @PostMapping(value = "/list")
    public BasicResult list(@RequestBody @Validated(value = {}) ShopChangeRecord shopChangeRecord, HttpServletRequest request){
        BasicData basicResult = new BasicData();

        //获取当前登录用户绑定的门店编号
        Merchant loginMerchant = merchantSessionManager.getSession(TokenUtil.getToken());

        shopChangeRecord.setShopId(loginMerchant.getShopId());
        Page<ShopChangeRecord> page = shopChangeRecordService.getListByPage(shopChangeRecord.getPageNo(), shopChangeRecord.getPageSize(), shopChangeRecord);

        return BasicResult.success(page);
    }

    /*@ApiOperation(value = "新增门店重要信息变更记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "门店重要信息变更记录名称", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "mainImage", value = "门店重要信息变更记录主图", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "description", value = "门店重要信息变更记录描述", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "unit", value = "采购单位", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "price", value = "采购单价", required = true, paramType = "query", dataType = "BigDecimal"),
            @ApiImplicitParam(name = "stock", value = "库存", required = true, paramType = "query", dataType = "BigDecimal"),
            @ApiImplicitParam(name = "stockLowerLimit", value = "库存过低线/库存下限", required = true, paramType = "query", dataType = "BigDecimal"),
            @ApiImplicitParam(name = "stockUpperLimit", value = "库存超出线/库存上限", required = true, paramType = "query", dataType = "BigDecimal"),
    })
    @PostMapping(value = "/insert")
    public BasicResult insert(ShopChangeRecord shopChangeRecord, HttpServletRequest request){
        BasicResult basicResult = new BasicResult();

        //获取当前登录用户绑定的门店编号
        String token = request.getHeader("token");
        MerchantToken merchantToken = merchantTokenService.getLoginMerchantInfo(token);
        Merchant dbMerchant = merchantService.selectByPrimaryKey(merchantToken.getMerchantId());

        //自动计算平台手续费
        Setting setting = settingService.selectCurrent();
        BigDecimal merchantWithdrawFee = setting.getMerchantWithdrawFee().divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_HALF_UP);
        BigDecimal platformFee = shopChangeRecord.getWithdrawAmount().multiply(merchantWithdrawFee).setScale(2, BigDecimal.ROUND_HALF_UP);
        *//*BigDecimal actualAmount = shopChangeRecord.getWithdrawAmount().subtract(platformFee).setScale(2, BigDecimal.ROUND_HALF_UP);*//*
        BigDecimal totalAmount = shopChangeRecord.getWithdrawAmount().add(platformFee).setScale(2, BigDecimal.ROUND_HALF_UP);

        //判断余额是否充足
        if(totalAmount.compareTo(dbMerchant.getWithdrawableBalance()) > 0){
            throw new StoneCustomerException("余额不足，请重新填写提现金额");
        }

        // 获取订单编号
        int i = 0;
        String orderNo = GenerateNo.getOrderNo();
        while (true){
            if(i == 99){
                throw new StoneCustomerException("无法生成订单编号");
            }
            log.debug("\n获取订单编号...");
            ShopChangeRecordExample shopChangeRecordExample = new ShopChangeRecordExample();
            shopChangeRecordExample.createCriteria().andOrderNoEqualTo(orderNo);
            int result = shopChangeRecordService.countByExample(shopChangeRecordExample);
            if(result > 0){
                orderNo = GenerateNo.getOrderNo();
            }else{
                break;
            }
            i++;
        }

        shopChangeRecord.setMerchantId(merchantToken.getMerchantId());
        shopChangeRecord.setOrderNo(orderNo);
        shopChangeRecord.setPlatformFee(platformFee);
        *//*shopChangeRecord.setActualAmount(actualAmount);*//*
        shopChangeRecord.setCreateTime(new Date());
        shopChangeRecord.setUpdateTime(new Date());
        shopChangeRecordService.insertSelective(shopChangeRecord);

        //减少商家的余额
        MerchantBillingRecord merchantBillingRecord = new MerchantBillingRecord();
        merchantBillingRecord.setMerchantId(dbMerchant.getId());
        merchantBillingRecord.setType(Quantity.INT_2);
        merchantBillingRecord.setOperateType(Quantity.INT_2);
        merchantBillingRecord.setCoinType(Quantity.INT_1);
        merchantBillingRecord.setNumber(totalAmount);
        merchantBillingRecord.setServiceFee(shopChangeRecord.getPlatformFee());
        merchantBillingRecord.setMessage("商家提现 -- 订单号" + shopChangeRecord.getOrderNo());
        merchantService.updateBalance(merchantBillingRecord);

        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("创建成功");
        return basicResult;
    }*/


    /*@ApiOperation(value = "修改门店重要信息变更记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "门店重要信息变更记录主键id", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "name", value = "门店重要信息变更记录名称", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "mainImage", value = "门店重要信息变更记录主图", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "description", value = "门店重要信息变更记录描述", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "unit", value = "采购单位", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "price", value = "采购单价", required = true, paramType = "query", dataType = "BigDecimal"),
            @ApiImplicitParam(name = "stock", value = "库存", required = true, paramType = "query", dataType = "BigDecimal"),
            @ApiImplicitParam(name = "stockLowerLimit", value = "库存过低线/库存下限", required = true, paramType = "query", dataType = "BigDecimal"),
            @ApiImplicitParam(name = "stockUpperLimit", value = "库存超出线/库存上限", required = true, paramType = "query", dataType = "BigDecimal"),
    })
    @PutMapping(value = "/update")
    public BasicResult update(ShopChangeRecord shopChangeRecord, HttpServletRequest request){
        BasicResult basicResult = new BasicResult();

        //获取当前登录用户绑定的门店编号
        String token = request.getHeader("token");
        MerchantToken merchantToken = merchantTokenService.getLoginMerchantInfo(token);

        ShopChangeRecord dbShopChangeRecord = shopChangeRecordService.selectByPrimaryKey(shopChangeRecord.getId());
        if(dbShopChangeRecord == null){
            basicResult.setSuccess(false);
            basicResult.setCode(BasicResultCode.ERR);
            basicResult.setMessage("该门店重要信息变更记录不存在，修改失败");
            return basicResult;
        }
        if(merchantToken.getMerchantId() != dbShopChangeRecord.getMerchantId()){
            throw new StoneCustomerException("您没有权限操作该记录");
        }

        //自动计算平台手续费
        Setting setting = settingService.selectCurrent();
        BigDecimal merchantWithdrawFee = setting.getMerchantWithdrawFee().divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_HALF_UP);
        BigDecimal platformFee = shopChangeRecord.getWithdrawAmount().multiply(merchantWithdrawFee).setScale(2, BigDecimal.ROUND_HALF_UP);
        BigDecimal actualAmount = shopChangeRecord.getWithdrawAmount().subtract(platformFee).setScale(2, BigDecimal.ROUND_HALF_UP);

        shopChangeRecord.setPlatformFee(platformFee);
        shopChangeRecord.setActualAmount(actualAmount);
        shopChangeRecord.setUpdateTime(new Date());
        shopChangeRecordService.updateByPrimaryKeySelective(shopChangeRecord);

        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("修改成功");
        return basicResult;
    }*/


    /*@ApiOperation(value = "删除门店重要信息变更记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "门店重要信息变更记录主键id", required = true, paramType = "query", dataType = "int")
    })
    @DeleteMapping(value = "/delete")
    public BasicResult delete(int id, HttpServletRequest request){
        BasicResult basicResult = new BasicResult();

        //获取当前登录用户绑定的门店编号
        String token = request.getHeader("token");
        MerchantToken merchantToken = merchantTokenService.getLoginMerchantInfo(token);

        ShopChangeRecord dbShopChangeRecord = shopChangeRecordService.selectByPrimaryKey(id);
        if(dbShopChangeRecord == null){
            basicResult.setSuccess(false);
            basicResult.setCode(BasicResultCode.ERR);
            basicResult.setMessage("该门店重要信息变更记录不存在，删除失败");
            return basicResult;
        }
        if(merchantToken.getMerchantId() != dbShopChangeRecord.getMerchantId()){
            throw new StoneCustomerException("您没有权限操作该记录");
        }

        shopChangeRecordService.deleteByPrimaryKey(id);

        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("删除成功");
        return basicResult;
    }*/
}