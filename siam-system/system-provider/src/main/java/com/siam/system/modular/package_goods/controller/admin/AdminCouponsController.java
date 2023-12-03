package com.siam.system.modular.package_goods.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.package_common.annoation.AdminPermission;
import com.siam.system.modular.package_goods.service.CouponsShopRelationService;
import com.siam.system.modular.package_goods.service.ShopService;
import com.siam.package_common.entity.BasicData;
import com.siam.package_common.entity.BasicResult;
import com.siam.package_common.constant.Quantity;
import com.siam.system.modular.package_goods.service.CouponsService;
import com.siam.system.modular.package_goods.model.example.ShopExample;
import com.siam.system.modular.package_goods.entity.Coupons;
import com.siam.system.modular.package_goods.entity.CouponsShopRelation;
import com.siam.system.modular.package_goods.entity.Shop;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/rest/admin/coupons")
@Transactional(rollbackFor = Exception.class)
@Api(tags = "后台优惠卷接口", description = "AdminCouponsController")
public class AdminCouponsController {

    @Autowired
    private CouponsService couponsService;

    @Autowired
    private ShopService shopService;

    @Autowired
    private CouponsShopRelationService couponsShopRelationService;

    @ApiOperation(value = "新增优惠卷")
    @PostMapping(value = "/insert")
    public BasicResult insert(@RequestBody @Validated(value = {}) Coupons param) {
        BasicResult basicResult = new BasicResult();

        couponsService.insertSelective(param);

        //所有状态的店铺(包括审核未通过的)都查出来
        ShopExample shopExample = new ShopExample();
        List<Shop> shopList = shopService.selectByExample(shopExample);
        //添加优惠券与店铺的关系，默认关联所有店铺
        CouponsShopRelation couponsShopRelation = new CouponsShopRelation();
        for (Shop shop : shopList) {
            couponsShopRelation.setId(null);
            couponsShopRelation.setCouponsId(param.getId());
            couponsShopRelation.setShopId(shop.getId());
            couponsShopRelation.setCreateTime(new Date());
            couponsShopRelationService.insertSelective(couponsShopRelation);
        }

        return BasicResult.success();
    }

    @AdminPermission
    @ApiOperation(value = "修改优惠卷-TODO")
    @PutMapping(value = "/update")
    public BasicResult update(@RequestBody @Validated(value = {}) Coupons param) {
        BasicResult basicResult = new BasicResult();

        //todo jiangyang
        couponsService.updateByPrimaryKeySelective(param);

        return BasicResult.success();
    }

    @AdminPermission
    @ApiOperation(value = "删除优惠卷")
    @DeleteMapping(value = "/delete")
    public BasicResult delete(@RequestBody @Validated(value = {}) Coupons param) {
        BasicResult basicResult = new BasicResult();

        couponsService.deleteByPrimaryKey(param.getId());

        return BasicResult.success();
    }

    @ApiOperation(value = "查看优惠卷详情（包含关联商品）")
    @PostMapping(value = "/selectById")
    public BasicResult selectById(@RequestBody @Validated(value = {}) Coupons param) {
        BasicData basicResult = new BasicData();

        Map map = couponsService.selectCouponsAndGoodsByPrimaryKey(param.getId());

        return BasicResult.success(map);
    }

    @ApiOperation(value = "满优惠卷列表")
    @PostMapping(value = "/list")
    public BasicResult list(@RequestBody @Validated(value = {}) Coupons param) {
        BasicData basicResult = new BasicData();

        param.setIsDelete(false);
        param.setSource(Quantity.INT_2);
        Page<Map<String, Object>> page = couponsService.getMapListByPage(param.getPageNo(), param.getPageSize(), param);

        return BasicResult.success(page);
    }

    @ApiOperation(value = "优惠卷时间修改（延长）")
    @PostMapping(value = "/updateEndTime")
    public BasicResult updateEndTime(@RequestBody @Validated(value = {}) Coupons param) {
        BasicResult basicResult = new BasicResult();

        //修改时间service
        couponsService.updateCouponsEndTime(param);

        return BasicResult.success();
    }
}
