package com.siam.system.modular.package_goods.controller.admin.internal;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.system.modular.package_goods.service.internal.PointsMallShoppingCartService;
import com.siam.package_common.entity.BasicData;
import com.siam.package_common.entity.BasicResult;
import com.siam.package_common.constant.BasicResultCode;
import com.siam.system.modular.package_goods.entity.internal.PointsMallShoppingCart;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/rest/admin/pointsMall/shoppingCart")
@Transactional(rollbackFor = Exception.class)
@Api(tags = "后台购物车模块相关接口", description = "AdminPointsMallShoppingCartController")
public class AdminPointsMallShoppingCartController {
    @Autowired
    private PointsMallShoppingCartService shoppingCartService;

    @ApiOperation(value = "购物车列表")
    @PostMapping(value = "/list")
    public BasicResult list(@RequestBody @Validated(value = {}) PointsMallShoppingCart shoppingCart){
        BasicData basicResult = new BasicData();

        Page page = shoppingCartService.getListByPage(shoppingCart.getPageNo(), shoppingCart.getPageSize(), shoppingCart);

        return BasicResult.success(page);
    }


    @ApiOperation(value = "新增购物车")
    @PostMapping(value = "/insert")
    public BasicResult insert(@RequestBody @Validated(value = {}) PointsMallShoppingCart shoppingCart){
        BasicResult basicResult = new BasicResult();

        shoppingCartService.insertSelective(shoppingCart);

        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("新增成功");
        return basicResult;
    }

    @ApiOperation(value = "修改购物车")
    @PostMapping(value = "/update")
    public BasicResult update(@RequestBody @Validated(value = {}) PointsMallShoppingCart shoppingCart){
        BasicResult basicResult = new BasicResult();

        shoppingCartService.updateByPrimaryKeySelective(shoppingCart);

        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("修改成功");
        return basicResult;
    }

    @ApiOperation(value = "删除购物车(含批量操作)")
    @PostMapping(value = "/delete")
    public BasicResult delete(@RequestBody @Validated(value = {}) PointsMallShoppingCart param){
        BasicResult basicResult = new BasicResult();

        for(Integer id : param.getIds()){
            shoppingCartService.deleteByPrimaryKey(Integer.valueOf(id));
        }

        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("删除成功");
        return basicResult;
    }
}