package com.siam.system.modular.package_goods.controller.admin.internal;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.package_common.annoation.AdminPermission;
import com.siam.system.modular.package_goods.service.internal.PointsMallCouponsService;
import com.siam.package_common.entity.BasicData;
import com.siam.package_common.entity.BasicResult;
import com.siam.package_common.constant.BasicResultCode;
import com.siam.system.modular.package_goods.entity.internal.PointsMallCoupons;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = "/rest/admin/pointsMall/coupons")
@Transactional(rollbackFor = Exception.class)
@Api(tags = "后台优惠卷接口", description = "AdminPointsMallCouponsController")
public class AdminPointsMallCouponsController {

    @Autowired
    private PointsMallCouponsService pointsMallCouponsService;

    @ApiOperation(value = "新增优惠卷")
    @PostMapping(value = "/insert")
    public BasicResult insert(@RequestBody @Validated(value = {}) PointsMallCoupons coupons) {
        BasicResult basicResult = new BasicResult();

        pointsMallCouponsService.insertSelective(coupons);

        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("新增成功");
        return basicResult;
    }

    @AdminPermission
    @ApiOperation(value = "修改优惠卷-TODO")
    @PutMapping(value = "/update")
    public BasicResult update(@RequestBody @Validated(value = {}) PointsMallCoupons coupons){
        BasicResult basicResult = new BasicResult();

        //todo jiangyang
        pointsMallCouponsService.updateByPrimaryKeySelective(coupons);

        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("修改成功");
        return basicResult;
    }

    @AdminPermission
    @ApiOperation(value = "删除优惠卷")
    @DeleteMapping(value = "/delete")
    public BasicResult delete(@RequestBody @Validated(value = {}) PointsMallCoupons param){
        BasicResult basicResult = new BasicResult();

        pointsMallCouponsService.deleteByPrimaryKey(param.getId());

        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("删除成功");
        return basicResult;
    }

    @ApiOperation(value = "查看优惠卷详情（包含关联商品）")
    @PostMapping(value = "/selectById")
    public BasicResult selectById(@RequestBody @Validated(value = {}) PointsMallCoupons param){
        BasicData basicResult = new BasicData();

        Map map = pointsMallCouponsService.selectCouponsAndGoodsByPrimaryKey(param.getId());

        basicResult.setData(map);
        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("获取成功");
        return basicResult;
    }

    @ApiOperation(value = "满优惠卷列表")
    @PostMapping(value = "/list")
    public BasicResult list(@RequestBody @Validated(value = {}) PointsMallCoupons coupons) {
        BasicData basicResult = new BasicData();

        coupons.setIsDelete(false);
        Page<Map<String, Object>> page = pointsMallCouponsService.getMapListByPage(coupons.getPageNo(), coupons.getPageSize(), coupons);

        return BasicResult.success(page);
    }

    @ApiOperation(value = "优惠卷时间修改（延长）")
    @PostMapping(value = "/updateEndTime")
    public BasicResult updateEndTime(@RequestBody @Validated(value = {}) PointsMallCoupons coupons){
        BasicResult basicResult = new BasicResult();

        //修改时间service
        pointsMallCouponsService.updatePointsMallCouponsEndTime(coupons);

        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("x修改成功");
        return basicResult;
    }
}
