package com.siam.system.modular.package_goods.controller.admin.internal;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.system.modular.package_goods.service.internal.PointsMallCouponsGoodsRelationService;
import com.siam.package_common.entity.BasicData;
import com.siam.package_common.entity.BasicResult;
import com.siam.package_common.constant.BasicResultCode;
import com.siam.package_common.util.GsonUtils;
import com.siam.system.modular.package_goods.entity.internal.PointsMallCouponsGoodsRelation;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/rest/admin/pointsMall/couponsGoodsRelation")
@Transactional(rollbackFor = Exception.class)
@Api(tags = "后台优惠卷接口", description = "AdminPointsMallCouponsController")
public class AdminPointsMallCouponsGoodsRelationController {

    @Autowired
    private PointsMallCouponsGoodsRelationService pointsMallCouponsGoodsRelationService;

    @ApiOperation(value = "新增优惠卷商品关系")
    @PostMapping(value = "/insert")
    public BasicResult insert(@RequestBody @Validated(value = {}) PointsMallCouponsGoodsRelation param) {
        BasicResult basicResult = new BasicResult();

        List<Integer> goodsIdList = GsonUtils.toList(param.getGoodsIdListStr(), Integer.class);
        pointsMallCouponsGoodsRelationService.insertSelective(param.getCouponsId(), goodsIdList);

        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("新增成功");
        return basicResult;
    }

    @ApiOperation(value = "优惠卷商品关系列表查询")
    @PostMapping(value = "/list")
    public BasicResult list(@RequestBody @Validated(value = {}) PointsMallCouponsGoodsRelation couponsGoodsRelation) {
        BasicData basicResult = new BasicData();

        Page<PointsMallCouponsGoodsRelation> page = pointsMallCouponsGoodsRelationService.getListByPage(couponsGoodsRelation.getPageNo(), couponsGoodsRelation.getPageSize(), couponsGoodsRelation);

        return BasicResult.success(page);
    }


}
