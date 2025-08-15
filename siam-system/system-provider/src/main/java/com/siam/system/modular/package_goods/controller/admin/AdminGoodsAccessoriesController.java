package com.siam.system.modular.package_goods.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.package_common.entity.BasicData;
import com.siam.package_common.entity.BasicResult;
import com.siam.package_common.constant.BasicResultCode;
import com.siam.package_common.exception.StoneCustomerException;
import com.siam.system.modular.package_goods.entity.GoodsAccessories;
import com.siam.system.modular.package_goods.service.GoodsAccessoriesService;
import com.siam.system.modular.package_goods.model.example.GoodsSpecificationOptionExample;
import com.siam.system.modular.package_goods.service.GoodsSpecificationOptionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping(value = "/rest/admin/goodsAccessories")
@Transactional(rollbackFor = Exception.class)
@Api(tags = "后台商品辅料模块相关接口", description = "AdminGoodsAccessoriesController")
public class AdminGoodsAccessoriesController {

    @Autowired
    private GoodsAccessoriesService goodsAccessoriesService;

    @Autowired
    private GoodsSpecificationOptionService goodsSpecificationOptionService;

    @ApiOperation(value = "商品辅料列表")
    @PostMapping(value = "/list")
    public BasicResult list(@RequestBody @Validated(value = {}) GoodsAccessories param){
        BasicData basicResult = new BasicData();
        Page<GoodsAccessories> page = goodsAccessoriesService.getListByPage(param.getPageNo(), param.getPageSize(), param);

        return BasicResult.success(page);
    }

    @ApiOperation(value = "新增商品辅料")
    @PostMapping(value = "/insert")
    public BasicResult insert(@RequestBody @Validated(value = {}) GoodsAccessories param){
        BasicResult basicResult = new BasicResult();

        param.setCreateTime(new Date());
        param.setUpdateTime(new Date());
        goodsAccessoriesService.insertSelective(param);

        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("创建成功");
        return basicResult;
    }


    @ApiOperation(value = "修改商品辅料")
    @PutMapping(value = "/update")
    public BasicResult update(@RequestBody @Validated(value = {}) GoodsAccessories param){
        BasicResult basicResult = new BasicResult();

        param.setUpdateTime(new Date());
        goodsAccessoriesService.updateByPrimaryKeySelective(param);

        //级联修改商品规格选项的价格、库存
        GoodsAccessories dbGoodsAccessories = goodsAccessoriesService.selectByPrimaryKey(param.getId());
        goodsSpecificationOptionService.updateByGoodsAccessories(dbGoodsAccessories);

        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("修改成功");
        return basicResult;
    }


    @ApiOperation(value = "删除商品辅料")
    @DeleteMapping(value = "/delete")
    public BasicResult delete(@RequestBody @Validated(value = {}) GoodsAccessories param){
        BasicResult basicResult = new BasicResult();

        GoodsAccessories dbGoodsAccessories = goodsAccessoriesService.selectByPrimaryKey(param.getId());
        if(dbGoodsAccessories == null){
            throw new StoneCustomerException("该商品辅料不存在，删除失败");
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