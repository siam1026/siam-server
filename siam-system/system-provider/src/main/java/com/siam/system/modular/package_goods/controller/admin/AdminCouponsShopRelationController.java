package com.siam.system.modular.package_goods.controller.admin;

import com.siam.package_common.constant.BasicResultCode;
import com.siam.package_common.entity.BasicResult;
import com.siam.package_common.util.GsonUtils;
import com.siam.system.modular.package_goods.entity.CouponsShopRelation;
import com.siam.system.modular.package_goods.service.CouponsShopRelationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/rest/admin/couponsShopRelation")
@Transactional(rollbackFor = Exception.class)
@Api(tags = "后台优惠卷接口", description = "AdminCouponsShopRelationController")
public class AdminCouponsShopRelationController {

    @Autowired
    private CouponsShopRelationService couponsShopRelationService;

    @ApiOperation(value = "新增优惠卷店铺关系")
    @PostMapping(value = "/insert")
    public BasicResult insert(@RequestBody @Validated(value = {}) CouponsShopRelation param) {
        BasicResult basicResult = new BasicResult();

        List<Integer> shopIdList = GsonUtils.toList(param.getShopIdListStr(), Integer.class);
        couponsShopRelationService.insertSelective(param.getCouponsId(), shopIdList);

        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("新增成功");
        return basicResult;
    }

    @ApiOperation(value = "优惠卷店铺关系列表查询")
    @PostMapping(value = "/list")
    public BasicResult list(@RequestBody @Validated(value = {}) CouponsShopRelation param) {
        BasicResult basicResult = new BasicResult();

        couponsShopRelationService.getListByPage(param.getPageNo(), param.getPageSize(), param);

        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("新增成功");
        return basicResult;
    }
}