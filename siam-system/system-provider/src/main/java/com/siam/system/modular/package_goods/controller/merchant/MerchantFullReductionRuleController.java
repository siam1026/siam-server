package com.siam.system.modular.package_goods.controller.merchant;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.package_common.annoation.MerchantPermission;
import com.siam.package_common.constant.BasicResultCode;
import com.siam.package_common.entity.BasicData;
import com.siam.package_common.entity.BasicResult;
import com.siam.package_common.exception.StoneCustomerException;
import com.siam.system.modular.package_goods.entity.FullReductionRule;
import com.siam.system.modular.package_goods.service.FullReductionRuleService;
import com.siam.system.modular.package_user.auth.cache.MerchantSessionManager;
import com.siam.system.modular.package_user.entity.Merchant;
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

@RestController
@RequestMapping(value = "/rest/merchant/fullReductionRule")
@Transactional(rollbackFor = Exception.class)
@Api(tags = "商家端满减规则模块相关接口", description = "MerchantFullReductionRuleController")
public class MerchantFullReductionRuleController {

    @Autowired
    private FullReductionRuleService fullReductionRuleService;

//    @Autowired
//    private MerchantService merchantService;

    @Autowired
    private MerchantSessionManager merchantSessionManager;

    @ApiOperation(value = "新增满减规则")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "满减规则名称", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "status", value = "状态", required = true, paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "limitedPrice", value = "分类名称", required = false, paramType = "query", dataType = "BigDecimal"),
            @ApiImplicitParam(name = "reducedPrice", value = "品牌id", required = true, paramType = "query", dataType = "BigDecimal")
    })
    @PostMapping(value = "/insert")
    public BasicResult insert(@RequestBody @Validated(value = {}) FullReductionRule fullReductionRule, HttpServletRequest request){
        BasicResult basicResult = new BasicResult();

        //获取当前登录用户绑定的门店编号
        Merchant loginMerchant = merchantSessionManager.getSession(TokenUtil.getToken());

        fullReductionRule.setShopId(loginMerchant.getShopId());
        fullReductionRuleService.insertSelective(fullReductionRule);

        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("新增成功");
        return basicResult;
    }

    @MerchantPermission
    @ApiOperation(value = "修改满减规则")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键id", required = true, paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "name", value = "满减规则名称", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "status", value = "状态", required = true, paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "limitedPrice", value = "分类名称", required = false, paramType = "query", dataType = "BigDecimal"),
            @ApiImplicitParam(name = "reducedPrice", value = "品牌id", required = true, paramType = "query", dataType = "BigDecimal")
    })
    @PutMapping(value = "/update")
    public BasicResult update(@RequestBody @Validated(value = {}) FullReductionRule fullReductionRule, HttpServletRequest request){
        BasicResult basicResult = new BasicResult();

        //获取当前登录用户绑定的门店编号
        Merchant loginMerchant = merchantSessionManager.getSession(TokenUtil.getToken());

        if (loginMerchant.getShopId() != fullReductionRule.getShopId()){
            throw new StoneCustomerException("您没有权限操作该满减规则");
        }

        fullReductionRuleService.updateByPrimaryKeySelective(fullReductionRule);

        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("修改成功");
        return basicResult;
    }

    @MerchantPermission
    @ApiOperation(value = "删除满减规则")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键id", required = true, paramType = "query", dataType = "Integer"),
    })
    @DeleteMapping(value = "/delete")
    public BasicResult delete(@RequestBody @Validated(value = {}) FullReductionRule param){
        BasicResult basicResult = new BasicResult();

        //获取当前登录用户绑定的门店编号
        Merchant loginMerchant = merchantSessionManager.getSession(TokenUtil.getToken());

        FullReductionRule dbFullReductionRule = fullReductionRuleService.selectByPrimaryKey(param.getId());
        if (dbFullReductionRule == null){
            throw new StoneCustomerException("该满减规则不存在");
        } else if (loginMerchant.getShopId() != dbFullReductionRule.getShopId()){
            throw new StoneCustomerException("您没有权限操作该满减规则");
        }

        fullReductionRuleService.deleteByPrimaryKey(param.getId());

        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("删除成功");
        return basicResult;
    }



    @ApiOperation(value = "满减规则列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键id", required = true, paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "name", value = "满减规则名称", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "status", value = "状态", required = true, paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "limitedPrice", value = "分类名称", required = false, paramType = "query", dataType = "BigDecimal"),
            @ApiImplicitParam(name = "reducedPrice", value = "品牌id", required = true, paramType = "query", dataType = "BigDecimal"),
            @ApiImplicitParam(name = "pageNo", value = "页码(值为-1不分页)", required = true, paramType = "query", dataType = "int", defaultValue = "1"),
            @ApiImplicitParam(name = "pageSize", value = "页数", required = true, paramType = "query", dataType = "int", defaultValue = "20")
    })
    @PostMapping(value = "/list")
    public BasicResult list(@RequestBody @Validated(value = {}) FullReductionRule fullReductionRule, HttpServletRequest request) {
        BasicData basicResult = new BasicData();

        //获取当前登录用户绑定的门店编号
        Merchant loginMerchant = merchantSessionManager.getSession(TokenUtil.getToken());

        fullReductionRule.setShopId(loginMerchant.getShopId());
        Page<FullReductionRule> page = fullReductionRuleService.getListByPage(fullReductionRule.getPageNo(), fullReductionRule.getPageSize(), fullReductionRule);

        return BasicResult.success(page);
    }

}
