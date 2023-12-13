package com.siam.system.modular.package_goods.controller.merchant;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.package_common.annoation.MerchantPermission;
import com.siam.package_common.constant.BasicResultCode;
import com.siam.package_common.constant.Quantity;
import com.siam.package_common.entity.BasicData;
import com.siam.package_common.entity.BasicResult;
import com.siam.system.modular.package_goods.entity.Coupons;
import com.siam.system.modular.package_goods.entity.CouponsGoodsRelation;
import com.siam.system.modular.package_goods.entity.CouponsShopRelation;
import com.siam.system.modular.package_goods.entity.Goods;
import com.siam.system.modular.package_goods.model.dto.CouponsDto;
import com.siam.system.modular.package_goods.model.example.GoodsExample;
import com.siam.system.modular.package_goods.service.CouponsGoodsRelationService;
import com.siam.system.modular.package_goods.service.CouponsService;
import com.siam.system.modular.package_goods.service.CouponsShopRelationService;
import com.siam.system.modular.package_goods.service.GoodsService;
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
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/rest/merchant/coupons")
@Transactional(rollbackFor = Exception.class)
@Api(tags = "商家端优惠卷接口", description = "MerchantCouponsController")
public class MerchantCouponsController {

    @Autowired
    private CouponsService couponsService;

    @Autowired
    private CouponsShopRelationService couponsShopRelationService;

//    @Autowired
//    private MerchantService merchantService;

    @Autowired
    private CouponsGoodsRelationService couponsGoodsRelationService;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private MerchantSessionManager merchantSessionManager;

    @ApiOperation(value = "新增优惠卷")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "优惠卷名称", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "preferentialType", value = "优惠类型，1=折扣，2=满减", required = true, paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "discountAmount", value = "折扣额度", required = true, paramType = "query", dataType = "BigDecimal"),
            @ApiImplicitParam(name = "limitedPrice", value = "满足价格（元，满足该价格才能使用）", required = true, paramType = "query", dataType = "BigDecimal"),
            @ApiImplicitParam(name = "reducedPrice", value = "减价额度(元)", required = true, paramType = "query", dataType = "BigDecimal"),
            @ApiImplicitParam(name = "description", value = "使用规则描述", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "validType", value = "时效:1绝对时效（领取后XXX-XXX时间段有效）  2相对时效（领取后N天有效）", required = true, paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "validStartTime", value = "使用开始时间", required = true, paramType = "query", dataType = "Date"),
            @ApiImplicitParam(name = "validEndTime", value = "使用结束时间", required = true, paramType = "query", dataType = "Date"),
            @ApiImplicitParam(name = "validDays", value = "自领取之日起有效天数", required = true, paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "isDelete", value = "是否已删除", required = true, paramType = "query", dataType = "Boolean"),
    })
    @PostMapping(value = "/insert")
    public BasicResult insert(@RequestBody @Validated(value = {}) Coupons coupons, HttpServletRequest request) {
        BasicResult basicResult = new BasicResult();

        //获取当前登录用户绑定的门店编号
        Merchant loginMerchant = merchantSessionManager.getSession(TokenUtil.getToken());

        //添加优惠券
        couponsService.insertSelective(coupons);

        //添加优惠券与店铺的关系
        CouponsShopRelation couponsShopRelation = new CouponsShopRelation();
        couponsShopRelation.setCouponsId(coupons.getId());
        couponsShopRelation.setShopId(loginMerchant.getShopId());
        couponsShopRelation.setCreateTime(new Date());
        couponsShopRelationService.insertSelective(couponsShopRelation);

        //所有状态的商品都查出来
        GoodsExample goodsExample = new GoodsExample();
        goodsExample.createCriteria().andShopIdEqualTo(loginMerchant.getShopId());
        List<Goods> goodsList = goodsService.selectByExample(goodsExample);
        //添加优惠券与商品的关系，默认关联所有商品
        CouponsGoodsRelation couponsGoodsRelation = new CouponsGoodsRelation();
        for (Goods goods : goodsList) {
            couponsGoodsRelation.setId(null);
            couponsGoodsRelation.setCouponsId(coupons.getId());
            couponsGoodsRelation.setGoodsId(goods.getId());
            couponsGoodsRelation.setCreateTime(new Date());
            couponsGoodsRelationService.insertSelective(couponsGoodsRelation);
        }

        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("新增成功");
        return basicResult;
    }

    @MerchantPermission
    @ApiOperation(value = "修改优惠卷-TODO")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键id", required = true, paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "name", value = "优惠卷名称", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "preferentialType", value = "优惠类型，1=折扣，2=满减", required = true, paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "discountAmount", value = "折扣额度", required = true, paramType = "query", dataType = "BigDecimal"),
            @ApiImplicitParam(name = "limitedPrice", value = "满足价格（元，满足该价格才能使用）", required = true, paramType = "query", dataType = "BigDecimal"),
            @ApiImplicitParam(name = "reducedPrice", value = "减价额度(元)", required = true, paramType = "query", dataType = "BigDecimal"),
            @ApiImplicitParam(name = "description", value = "使用规则描述", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "validType", value = "时效:1绝对时效（领取后XXX-XXX时间段有效）  2相对时效（领取后N天有效）", required = true, paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "validStartTime", value = "使用开始时间", required = true, paramType = "query", dataType = "Date"),
            @ApiImplicitParam(name = "validEndTime", value = "使用结束时间", required = true, paramType = "query", dataType = "Date"),
            @ApiImplicitParam(name = "validDays", value = "自领取之日起有效天数", required = true, paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "isDelete", value = "是否已删除", required = true, paramType = "query", dataType = "Boolean"),
    })
    @PutMapping(value = "/update")
    public BasicResult update(@RequestBody @Validated(value = {}) Coupons coupons, HttpServletRequest request){
        BasicResult basicResult = new BasicResult();

        //todo 暹罗
        couponsService.updateByPrimaryKeySelective(coupons);

        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("修改成功");
        return basicResult;
    }

    @MerchantPermission
    @ApiOperation(value = "删除优惠卷")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键id", required = true, paramType = "query", dataType = "Integer"),
    })
    @DeleteMapping(value = "/delete")
    public BasicResult delete(@RequestBody @Validated(value = {}) Coupons param){
        BasicResult basicResult = new BasicResult();

        couponsService.deleteByPrimaryKey(param.getId());

        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("删除成功");
        return basicResult;
    }

    @ApiOperation(value = "查看优惠卷详情（包含关联商品）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键id", required = true, paramType = "query", dataType = "Integer"),
    })
    @PostMapping(value = "/selectById")
    public BasicResult selectById(@RequestBody @Validated(value = {}) Coupons param){
        BasicData basicResult = new BasicData();

        Map map = couponsService.selectCouponsAndGoodsByPrimaryKey(param.getId());

        basicResult.setData(map);
        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("获取成功");
        return basicResult;
    }

    @ApiOperation(value = "优惠卷列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键id", required = false, paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "name", value = "优惠卷名称", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "preferentialType", value = "优惠类型，1=折扣，2=满减", required = false, paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "discountAmount", value = "折扣额度", required = false, paramType = "query", dataType = "BigDecimal"),
            @ApiImplicitParam(name = "limitedPrice", value = "满足价格（元，满足该价格才能使用）", required = false, paramType = "query", dataType = "BigDecimal"),
            @ApiImplicitParam(name = "reducedPrice", value = "减价额度(元)", required = false, paramType = "query", dataType = "BigDecimal"),
            @ApiImplicitParam(name = "description", value = "使用规则描述", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "validType", value = "时效:1绝对时效（领取后XXX-XXX时间段有效）  2相对时效（领取后N天有效）", required = true, paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "validStartTime", value = "使用开始时间", required = true, paramType = "query", dataType = "Date"),
            @ApiImplicitParam(name = "validEndTime", value = "使用结束时间", required = true, paramType = "query", dataType = "Date"),
            @ApiImplicitParam(name = "validDays", value = "自领取之日起有效天数", required = true, paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "isDelete", value = "是否已删除", required = true, paramType = "query", dataType = "Boolean"),
            @ApiImplicitParam(name = "pageNo", value = "页码(值为-1不分页)", required = true, paramType = "query", dataType = "int", defaultValue = "1"),
            @ApiImplicitParam(name = "pageSize", value = "页数", required = true, paramType = "query", dataType = "int", defaultValue = "20")
    })
    @PostMapping(value = "/list")
    public BasicResult list(@RequestBody @Validated(value = {}) CouponsDto couponsDto, HttpServletRequest request) {
        BasicData basicResult = new BasicData();

        //获取当前登录用户绑定的门店编号
        Merchant loginMerchant = merchantSessionManager.getSession(TokenUtil.getToken());

        couponsDto.setShopId(loginMerchant.getShopId());
        couponsDto.setIsDelete(false);
        couponsDto.setSource(Quantity.INT_1);
        Page<Map<String, Object>> page = couponsService.getListJoinCouponsShopRelationByPage(couponsDto.getPageNo(), couponsDto.getPageSize(), couponsDto);

        return BasicResult.success(page);
    }

    @ApiOperation(value = "优惠卷时间修改（延长）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键id", required = false, paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "name", value = "优惠卷名称", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "preferentialType", value = "优惠类型，1=折扣，2=满减", required = false, paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "discountAmount", value = "折扣额度", required = false, paramType = "query", dataType = "BigDecimal"),
            @ApiImplicitParam(name = "limitedPrice", value = "满足价格（元，满足该价格才能使用）", required = false, paramType = "query", dataType = "BigDecimal"),
            @ApiImplicitParam(name = "reducedPrice", value = "减价额度(元)", required = false, paramType = "query", dataType = "BigDecimal"),
            @ApiImplicitParam(name = "description", value = "使用规则描述", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "validType", value = "时效:1绝对时效（领取后XXX-XXX时间段有效）  2相对时效（领取后N天有效）", required = true, paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "validStartTime", value = "使用开始时间", required = true, paramType = "query", dataType = "Date"),
            @ApiImplicitParam(name = "validEndTime", value = "使用结束时间", required = true, paramType = "query", dataType = "Date"),
            @ApiImplicitParam(name = "validDays", value = "自领取之日起有效天数", required = true, paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "isDelete", value = "是否已删除", required = true, paramType = "query", dataType = "Boolean"),
    })
    @PostMapping(value = "/updateEndTime")
    public BasicResult updateEndTime(@RequestBody @Validated(value = {}) Coupons coupons){
        BasicResult basicResult = new BasicResult();

        //修改时间service
        couponsService.updateCouponsEndTime(coupons);

        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("x修改成功");
        return basicResult;
    }
}
