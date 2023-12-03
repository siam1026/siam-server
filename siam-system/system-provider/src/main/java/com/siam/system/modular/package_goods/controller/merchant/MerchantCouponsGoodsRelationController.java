package com.siam.system.modular.package_goods.controller.merchant;

import com.siam.package_common.entity.BasicResult;
import com.siam.package_common.constant.BasicResultCode;
import com.siam.package_common.util.GsonUtils;
import com.siam.system.modular.package_goods.entity.CouponsGoodsRelation;
import com.siam.system.modular.package_goods.service.CouponsGoodsRelationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/rest/merchant/couponsGoodsRelation")
@Transactional(rollbackFor = Exception.class)
@Api(tags = "商家端优惠卷接口", description = "MerchantCouponsGoodsRelationController")
public class MerchantCouponsGoodsRelationController {

    @Autowired
    private CouponsGoodsRelationService couponsGoodsRelationService;

    @ApiOperation(value = "新增优惠卷商品关系")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "couponsId", value = "优惠卷id", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "goodsIdList", value = "商品id集合（list）", required = true, paramType = "body", dataType = "string"),

    })
    @PostMapping(value = "/insert")
    public BasicResult insert(@RequestBody @Validated(value = {}) CouponsGoodsRelation param) {
        BasicResult basicResult = new BasicResult();

        List<Integer> goodsIdList = GsonUtils.toList(param.getGoodsIdListStr(), Integer.class);
        couponsGoodsRelationService.insertSelective(param.getCouponsId(), goodsIdList);

        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("新增成功");
        return basicResult;
    }

    @ApiOperation(value = "优惠卷商品关系列表查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键id", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "goodsId", value = "商品id", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "couponsId", value = "优惠卷id", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "pageNo", value = "页码(值为-1不分页)", required = true, paramType = "query", dataType = "int", defaultValue = "1"),
            @ApiImplicitParam(name = "pageSize", value = "页数", required = true, paramType = "query", dataType = "int", defaultValue = "20")

    })
    @PostMapping(value = "/list")
    public BasicResult list(@RequestBody @Validated(value = {}) CouponsGoodsRelation couponsGoodsRelation) {
        BasicResult basicResult = new BasicResult();

        couponsGoodsRelationService.getListByPage(couponsGoodsRelation.getPageNo(), couponsGoodsRelation.getPageSize(), couponsGoodsRelation);

        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("新增成功");
        return basicResult;
    }
}