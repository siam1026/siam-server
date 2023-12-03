package com.siam.system.modular.package_user.controller.merchant;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.package_common.entity.BasicResult;
import com.siam.system.modular.package_user.entity.MerchantWithdrawRecord;
import com.siam.system.modular.package_user.model.param.MerchantWithdrawRecordParam;
import com.siam.system.modular.package_user.service.MerchantBillingRecordService;
import com.siam.system.modular.package_user.service.MerchantWithdrawRecordService;
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

import java.util.Map;

@Slf4j
@RestController
@RequestMapping(value = "/rest/merchant/merchantWithdrawRecord")
@Transactional(rollbackFor = Exception.class)
@Api(tags = "商家端商家提现记录模块相关接口", description = "MerchantWithdrawRecordController")
public class MerchantWithdrawRecordController {

    @Autowired
    private MerchantWithdrawRecordService merchantWithdrawRecordService;

    @Autowired
    private MerchantBillingRecordService merchantBillingRecordService;

    @ApiOperation(value = "商家提现记录列表")
    @PostMapping(value = "/list")
    public BasicResult list(@RequestBody @Validated(value = {}) MerchantWithdrawRecordParam param) {
        Page<MerchantWithdrawRecord> page = merchantWithdrawRecordService.getListByPage(param);
        return BasicResult.success(page);
    }

    @ApiOperation(value = "商家提现记录-统计金额")
    @PostMapping(value = "/statisticalAmount")
    public BasicResult statisticalAmount(@RequestBody @Validated(value = {}) MerchantWithdrawRecordParam param) {
        Map<String, Object> map = merchantWithdrawRecordService.statisticalAmount(param);
        return BasicResult.success(map);
    }

    @ApiOperation(value = "新增商家提现记录")
    @PostMapping(value = "/insert")
    public BasicResult insert(@RequestBody @Validated(value = {}) MerchantWithdrawRecordParam param) {
        merchantWithdrawRecordService.insert(param);
        return BasicResult.success();
    }



    /*@ApiOperation(value = "修改商家提现记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "商家提现记录主键id", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "name", value = "商家提现记录名称", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "mainImage", value = "商家提现记录主图", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "description", value = "商家提现记录描述", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "unit", value = "采购单位", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "price", value = "采购单价", required = true, paramType = "query", dataType = "BigDecimal"),
            @ApiImplicitParam(name = "stock", value = "库存", required = true, paramType = "query", dataType = "BigDecimal"),
            @ApiImplicitParam(name = "stockLowerLimit", value = "库存过低线/库存下限", required = true, paramType = "query", dataType = "BigDecimal"),
            @ApiImplicitParam(name = "stockUpperLimit", value = "库存超出线/库存上限", required = true, paramType = "query", dataType = "BigDecimal"),
    })
    @PutMapping(value = "/update")
    public BasicResult update(MerchantWithdrawRecord merchantWithdrawRecord, HttpServletRequest request){
        BasicResult basicResult = new BasicResult();

        //获取当前登录用户绑定的门店编号
        String token = request.getHeader("token");
        MerchantToken merchantToken = merchantTokenService.getLoginMerchantInfo(token);

        MerchantWithdrawRecord dbMerchantWithdrawRecord = merchantWithdrawRecordService.selectByPrimaryKey(merchantWithdrawRecord.getId());
        if(dbMerchantWithdrawRecord == null){
            basicResult.setSuccess(false);
            basicResult.setCode(BasicResultCode.ERR);
            basicResult.setMessage("该商家提现记录不存在，修改失败");
            return basicResult;
        }
        if(merchantToken.getMerchantId() != dbMerchantWithdrawRecord.getMerchantId()){
            throw new StoneCustomerException("您没有权限操作该记录");
        }

        //自动计算平台手续费
        Setting setting = settingService.selectCurrent();
        BigDecimal merchantWithdrawFee = setting.getMerchantWithdrawFee().divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_HALF_UP);
        BigDecimal platformFee = merchantWithdrawRecord.getWithdrawAmount().multiply(merchantWithdrawFee).setScale(2, BigDecimal.ROUND_HALF_UP);
        BigDecimal actualAmount = merchantWithdrawRecord.getWithdrawAmount().subtract(platformFee).setScale(2, BigDecimal.ROUND_HALF_UP);

        merchantWithdrawRecord.setPlatformFee(platformFee);
        merchantWithdrawRecord.setActualAmount(actualAmount);
        merchantWithdrawRecord.setUpdateTime(new Date());
        merchantWithdrawRecordService.updateByPrimaryKeySelective(merchantWithdrawRecord);

        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("修改成功");
        return basicResult;
    }*/


    /*@ApiOperation(value = "删除商家提现记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "商家提现记录主键id", required = true, paramType = "query", dataType = "int")
    })
    @DeleteMapping(value = "/delete")
    public BasicResult delete(int id, HttpServletRequest request){
        BasicResult basicResult = new BasicResult();

        //获取当前登录用户绑定的门店编号
        String token = request.getHeader("token");
        MerchantToken merchantToken = merchantTokenService.getLoginMerchantInfo(token);

        MerchantWithdrawRecord dbMerchantWithdrawRecord = merchantWithdrawRecordService.selectByPrimaryKey(id);
        if(dbMerchantWithdrawRecord == null){
            basicResult.setSuccess(false);
            basicResult.setCode(BasicResultCode.ERR);
            basicResult.setMessage("该商家提现记录不存在，删除失败");
            return basicResult;
        }
        if(merchantToken.getMerchantId() != dbMerchantWithdrawRecord.getMerchantId()){
            throw new StoneCustomerException("您没有权限操作该记录");
        }

        merchantWithdrawRecordService.deleteByPrimaryKey(id);

        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("删除成功");
        return basicResult;
    }*/
}