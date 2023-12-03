package com.siam.system.modular.package_goods.controller.merchant;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.package_common.entity.BasicData;
import com.siam.package_common.entity.BasicResult;
import com.siam.package_common.constant.BasicResultCode;
import com.siam.system.modular.package_goods.entity.GoodsAccessories;
import com.siam.system.modular.package_goods.service.GoodsAccessoriesService;
import com.siam.system.modular.package_goods.model.example.GoodsSpecificationOptionExample;
import com.siam.system.modular.package_goods.service.GoodsSpecificationOptionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping(value = "/rest/merchant/goodsAccessories")
@Transactional(rollbackFor = Exception.class)
@Api(tags = "商家端商品辅料模块相关接口", description = "MerchantGoodsAccessoriesController")
public class MerchantGoodsAccessoriesController {

    @Autowired
    private GoodsAccessoriesService goodsAccessoriesService;

    @Autowired
    private GoodsSpecificationOptionService goodsSpecificationOptionService;

    @ApiOperation(value = "商品辅料列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "商品辅料表主键id", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "name", value = "商品辅料名称", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "sortNumber", value = "排序", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "isDisabled", value = "是否启用 0-启用、1-禁用", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "detail", value = "商品辅料详情描述", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "pageNo", value = "页码(值为-1不分页)", required = true, paramType = "query", dataType = "int", defaultValue = "1"),
            @ApiImplicitParam(name = "pageSize", value = "页数", required = true, paramType = "query", dataType = "int", defaultValue = "20")
    })
    @PostMapping(value = "/list")
    public BasicResult list(@RequestBody @Validated(value = {}) GoodsAccessories goodsAccessories){
        BasicData basicResult = new BasicData();
        Page<GoodsAccessories> page = goodsAccessoriesService.getListByPage(goodsAccessories.getPageNo(), goodsAccessories.getPageSize(), goodsAccessories);

        return BasicResult.success(page);
    }

    @ApiOperation(value = "新增商品辅料")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "商品辅料主键id", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "name", value = "商品辅料名称", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "sortNumber", value = "排序", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "isDisabled", value = "是否启用 0-启用、1-禁用", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "detail", value = "商品辅料详情描述", required = false, paramType = "query", dataType = "string")
    })
    @PostMapping(value = "/insert")
    public BasicResult insert(@RequestBody @Validated(value = {}) GoodsAccessories goodsAccessories){
        BasicResult basicResult = new BasicResult();

        goodsAccessories.setCreateTime(new Date());
        goodsAccessories.setUpdateTime(new Date());
        goodsAccessoriesService.insertSelective(goodsAccessories);

        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("创建成功");
        return basicResult;
    }


    @ApiOperation(value = "修改商品辅料")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "商品辅料主键id", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "name", value = "商品辅料名称", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "sortNumber", value = "排序", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "isDisabled", value = "是否启用 0-启用、1-禁用", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "detail", value = "商品辅料详情描述", required = false, paramType = "query", dataType = "string")
    })
    @PutMapping(value = "/update")
    public BasicResult update(@RequestBody @Validated(value = {}) GoodsAccessories goodsAccessories){
        BasicResult basicResult = new BasicResult();

        goodsAccessories.setUpdateTime(new Date());
        goodsAccessoriesService.updateByPrimaryKeySelective(goodsAccessories);

        //级联修改商品规格选项的价格、库存
        GoodsAccessories dbGoodsAccessories = goodsAccessoriesService.selectByPrimaryKey(goodsAccessories.getId());
        goodsSpecificationOptionService.updateByGoodsAccessories(dbGoodsAccessories);

        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("修改成功");
        return basicResult;
    }


    @ApiOperation(value = "删除商品辅料")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "商品辅料主键id", required = true, paramType = "query", dataType = "int")
    })
    @DeleteMapping(value = "/delete")
    public BasicResult delete(@RequestBody @Validated(value = {}) GoodsAccessories param){
        BasicResult basicResult = new BasicResult();

        GoodsAccessories dbGoodsAccessories = goodsAccessoriesService.selectByPrimaryKey(param.getId());
        if(dbGoodsAccessories == null){
            basicResult.setSuccess(false);
            basicResult.setCode(BasicResultCode.ERR);
            basicResult.setMessage("该商品辅料不存在，删除失败");
            return basicResult;
        }

        //暂时不让他删除，后续可能会采取级联删除商品规格选项
        GoodsSpecificationOptionExample example = new GoodsSpecificationOptionExample();
        example.createCriteria().andNameEqualTo(dbGoodsAccessories.getName());
        int result = goodsSpecificationOptionService.countByExample(example);
        if(result > 0){
            basicResult.setSuccess(false);
            basicResult.setCode(BasicResultCode.ERR);
            basicResult.setMessage("该商品辅料关联了商品规格选项，不允许删除");
            return basicResult;
        }

        //删除商品辅料
        goodsAccessoriesService.deleteByPrimaryKey(param.getId());

        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("删除成功");
        return basicResult;
    }
}