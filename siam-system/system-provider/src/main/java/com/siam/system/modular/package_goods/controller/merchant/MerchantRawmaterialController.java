package com.siam.system.modular.package_goods.controller.merchant;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.package_common.entity.BasicData;
import com.siam.package_common.entity.BasicResult;
import com.siam.package_common.constant.BasicResultCode;
import com.siam.system.modular.package_goods.model.example.GoodsRawmaterialRelationExample;
import com.siam.system.modular.package_goods.service.GoodsRawmaterialRelationService;
import com.siam.system.modular.package_goods.entity.Rawmaterial;
import com.siam.system.modular.package_goods.service.RawmaterialService;
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
@RequestMapping(value = "/rest/merchant/rawmaterial")
@Transactional(rollbackFor = Exception.class)
@Api(tags = "商家端原料模块相关接口", description = "MerchantRawmaterialController")
public class MerchantRawmaterialController {
    @Autowired
    private RawmaterialService rawmaterialService;

    @Autowired
    private GoodsRawmaterialRelationService goodsRawmaterialRelationService;

    @ApiOperation(value = "原料列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "原料表主键id", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "name", value = "原料名称", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "mainImage", value = "原料主图", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "description", value = "原料描述", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "unit", value = "采购单位", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "price", value = "采购单价", required = false, paramType = "query", dataType = "BigDecimal"),
            @ApiImplicitParam(name = "stock", value = "库存", required = false, paramType = "query", dataType = "BigDecimal"),
            @ApiImplicitParam(name = "stockLowerLimit", value = "库存过低线/库存下限", required = false, paramType = "query", dataType = "BigDecimal"),
            @ApiImplicitParam(name = "stockUpperLimit", value = "库存超出线/库存上限", required = false, paramType = "query", dataType = "BigDecimal"),
            @ApiImplicitParam(name = "pageNo", value = "页码(值为-1不分页)", required = true, paramType = "query", dataType = "int", defaultValue = "1"),
            @ApiImplicitParam(name = "pageSize", value = "页数", required = true, paramType = "query", dataType = "int", defaultValue = "20")
    })
    @PostMapping(value = "/list")
    public BasicResult list(@RequestBody @Validated(value = {}) Rawmaterial rawmaterial){
        BasicData basicResult = new BasicData();
        Page<Rawmaterial> page = rawmaterialService.getListByPage(rawmaterial.getPageNo(), rawmaterial.getPageSize(), rawmaterial);

        return BasicResult.success(page);
    }

    @ApiOperation(value = "新增原料")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "原料名称", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "mainImage", value = "原料主图", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "description", value = "原料描述", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "unit", value = "采购单位", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "price", value = "采购单价", required = true, paramType = "query", dataType = "BigDecimal"),
            @ApiImplicitParam(name = "stock", value = "库存", required = true, paramType = "query", dataType = "BigDecimal"),
            @ApiImplicitParam(name = "stockLowerLimit", value = "库存过低线/库存下限", required = true, paramType = "query", dataType = "BigDecimal"),
            @ApiImplicitParam(name = "stockUpperLimit", value = "库存超出线/库存上限", required = true, paramType = "query", dataType = "BigDecimal"),
    })
    @PostMapping(value = "/insert")
    public BasicResult insert(@RequestBody @Validated(value = {}) Rawmaterial rawmaterial){
        BasicResult basicResult = new BasicResult();

        rawmaterial.setCreateTime(new Date());
        rawmaterial.setUpdateTime(new Date());
        rawmaterialService.insertSelective(rawmaterial);

        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("创建成功");
        return basicResult;
    }


    @ApiOperation(value = "修改原料")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "原料主键id", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "name", value = "原料名称", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "mainImage", value = "原料主图", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "description", value = "原料描述", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "unit", value = "采购单位", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "price", value = "采购单价", required = true, paramType = "query", dataType = "BigDecimal"),
            @ApiImplicitParam(name = "stock", value = "库存", required = true, paramType = "query", dataType = "BigDecimal"),
            @ApiImplicitParam(name = "stockLowerLimit", value = "库存过低线/库存下限", required = true, paramType = "query", dataType = "BigDecimal"),
            @ApiImplicitParam(name = "stockUpperLimit", value = "库存超出线/库存上限", required = true, paramType = "query", dataType = "BigDecimal"),
    })
    @PutMapping(value = "/update")
    public BasicResult update(@RequestBody @Validated(value = {}) Rawmaterial rawmaterial){
        BasicResult basicResult = new BasicResult();

        rawmaterial.setUpdateTime(new Date());
        rawmaterialService.updateByPrimaryKeySelective(rawmaterial);

        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("修改成功");
        return basicResult;
    }


    @ApiOperation(value = "删除原料")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "原料主键id", required = true, paramType = "query", dataType = "int")
    })
    @DeleteMapping(value = "/delete")
    public BasicResult delete(@RequestBody @Validated(value = {}) Rawmaterial param){
        BasicResult basicResult = new BasicResult();

        Rawmaterial dbRawmaterial = rawmaterialService.selectByPrimaryKey(param.getId());
        if(dbRawmaterial == null){
            basicResult.setSuccess(false);
            basicResult.setCode(BasicResultCode.ERR);
            basicResult.setMessage("该原料不存在，删除失败");
            return basicResult;
        }

        //暂时不让他删除，后续可能会采取级联删除原料配比数据
        GoodsRawmaterialRelationExample example = new GoodsRawmaterialRelationExample();
        example.createCriteria().andRawmaterialIdEqualTo(dbRawmaterial.getId());
        int result = goodsRawmaterialRelationService.countByExample(example);
        if(result > 0){
            basicResult.setSuccess(false);
            basicResult.setCode(BasicResultCode.ERR);
            basicResult.setMessage("该原料关联了原料配比数据，不允许删除");
            return basicResult;
        }

        //删除原料
        rawmaterialService.deleteByPrimaryKey(param.getId());

        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("删除成功");
        return basicResult;
    }
}