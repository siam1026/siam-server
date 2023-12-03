package com.siam.system.modular.package_goods.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.system.modular.package_goods.service.GoodsSpecificationService;
import com.siam.package_common.constant.BasicResultCode;
import com.siam.package_common.entity.BasicData;
import com.siam.package_common.entity.BasicResult;
import com.siam.system.modular.package_goods.entity.GoodsSpecification;
import com.siam.system.modular.package_goods.model.example.GoodsSpecificationExample;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/rest/admin/goodsSpecification")
@Transactional(rollbackFor = Exception.class)
@Api(tags = "后台商品规格模块相关接口", description = "AdminGoodsSpecificationController")
public class AdminGoodsSpecificationController {
    @Autowired
    private GoodsSpecificationService goodsSpecificationService;

    @ApiOperation(value = "商品规格列表")
    @PostMapping(value = "/list")
    public BasicResult list(@RequestBody @Validated(value = {}) GoodsSpecification param){
        BasicData basicResult = new BasicData();

        Page page = goodsSpecificationService.getListByPage(param.getPageNo(), param.getPageSize(), param);

        return BasicResult.success(page);
    }


    @ApiOperation(value = "修改商品规格")
    @PostMapping(value = "/update")
    public BasicResult update(@RequestBody @Validated(value = {}) GoodsSpecification param){
        BasicResult basicResult = new BasicResult();

        GoodsSpecification dbSpecification = goodsSpecificationService.selectByPrimaryKey(param.getId());
        if(dbSpecification == null){
            basicResult.setSuccess(false);
            basicResult.setCode(BasicResultCode.ERR);
            basicResult.setMessage("该奶茶口味类别不存在，修改失败");
            return basicResult;
        }

        //判断商品规格选项是否重复
        GoodsSpecificationExample example = new GoodsSpecificationExample();
        example.createCriteria().andGoodsIdEqualTo(dbSpecification.getGoodsId()).andNameEqualTo(param.getName()).andIdNotEqualTo(param.getId());
        int result = goodsSpecificationService.countByExample(example);
        if(result > 0){
            basicResult.setSuccess(false);
            basicResult.setCode(BasicResultCode.ERR);
            basicResult.setMessage("奶茶口味类别重复，修改失败");
            return basicResult;
        }

        //修改规格记录
        goodsSpecificationService.updateByPrimaryKeySelective(param);

        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("修改成功");
        return basicResult;
    }

    /*@ApiOperation(value = "新增商品规格")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "规格名称", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "sortNumber", value = "排序号", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "status", value = "状态 1=启用 0=禁用 -1=删除", required = false, paramType = "query", dataType = "int"),
    })
    @PostMapping(value = "/insert")
    public BasicResult insert(Specification specification){
        BasicResult basicResult = new BasicResult();

        specificationService.insertSelective(specification);

        basicResult.setSuccess(true);
        basicResult.setCode(BaseCode.SUCCESS);
        basicResult.setMessage("新增成功");
        return basicResult;
    }*/

    /*@ApiOperation(value = "删除商品规格(含批量操作)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "商品规格表主键id(批量删除时id以逗号分隔)", required = true, paramType = "query", dataType = "string"),
    })
    @PostMapping(value = "/delete")
    public BasicResult delete(@RequestParam(value = "ids", required = true) List<String> ids){
        BasicResult basicResult = new BasicResult();

        for(String id : ids){
            specificationService.deleteByPrimaryKey(Integer.valueOf(id));
        }

        basicResult.setSuccess(true);
        basicResult.setCode(BaseCode.SUCCESS);
        basicResult.setMessage("删除成功");
        return basicResult;
    }*/
}