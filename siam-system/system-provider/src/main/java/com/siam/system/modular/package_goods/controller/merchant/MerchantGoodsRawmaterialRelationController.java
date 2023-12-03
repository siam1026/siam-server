package com.siam.system.modular.package_goods.controller.merchant;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.package_common.entity.BasicData;
import com.siam.package_common.entity.BasicResult;
import com.siam.package_common.constant.BasicResultCode;
import com.siam.package_common.util.GsonUtils;
import com.siam.package_common.util.StringUtils;
import com.siam.system.modular.package_goods.entity.Goods;
import com.siam.system.modular.package_goods.service.GoodsService;
import com.siam.system.modular.package_goods.entity.GoodsRawmaterialRelation;
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

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/rest/merchant/goodsRawmaterialRelation")
@Transactional(rollbackFor = Exception.class)
@Api(tags = "商家端原料配比模块相关接口", description = "MerchantGoodsRawmaterialRelationController")
public class MerchantGoodsRawmaterialRelationController {

    @Autowired
    private GoodsRawmaterialRelationService goodsRawmaterialRelationService;

    @Autowired
    private RawmaterialService rawmaterialService;

    @Autowired
    private GoodsService goodsService;

    @ApiOperation(value = "原料配比列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "原料配比表主键id", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "name", value = "原料配比名称", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "sortNumber", value = "排序", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "isDisabled", value = "是否启用 0-启用、1-禁用", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "detail", value = "原料配比详情描述", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "pageNo", value = "页码(值为-1不分页)", required = true, paramType = "query", dataType = "int", defaultValue = "1"),
            @ApiImplicitParam(name = "pageSize", value = "页数", required = true, paramType = "query", dataType = "int", defaultValue = "20")
    })
    @PostMapping(value = "/list")
    public BasicResult list(@RequestBody @Validated(value = {}) GoodsRawmaterialRelation goodsRawmaterialRelation){
        BasicData basicResult = new BasicData();
        Page<GoodsRawmaterialRelation> page = goodsRawmaterialRelationService.getListByPage(goodsRawmaterialRelation.getPageNo(), goodsRawmaterialRelation.getPageSize(), goodsRawmaterialRelation);

        return BasicResult.success(page);
    }

    @ApiOperation(value = "设置原料配比")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "goodsListStr", value = "商品id", required = true, paramType = "query", dataType = "List"),
            @ApiImplicitParam(name = "relationListStr", value = "原料", required = true, paramType = "query", dataType = "List"),
    })
    @PostMapping(value = "/insert")
    public BasicResult insert(@RequestBody @Validated(value = {}) GoodsRawmaterialRelation param){
        BasicResult basicResult = new BasicResult();

        if(StringUtils.isEmpty(param.getGoodsIdListStr())){
            basicResult.setSuccess(false);
            basicResult.setCode(BasicResultCode.ERR);
            basicResult.setMessage("请选择要设置原料配比的商品信息");
            return basicResult;
        }
        if(StringUtils.isEmpty(param.getRelationListStr())){
            basicResult.setSuccess(false);
            basicResult.setCode(BasicResultCode.ERR);
            basicResult.setMessage("请选择商品关联的原料");
            return basicResult;
        }
        List<Integer> goodsIdList = GsonUtils.toList(param.getGoodsIdListStr(), Integer.class);
        List<GoodsRawmaterialRelation> relationList = GsonUtils.toList(param.getRelationListStr(), GoodsRawmaterialRelation.class);

        //双层循环插入关联信息
        for (Integer goodsId : goodsIdList) {
            //校验商品信息是否存在，后续优化可以在循环外面批量校验
            Goods dbGoods = goodsService.selectByPrimaryKey(goodsId);
            if(dbGoods == null){
                basicResult.setSuccess(false);
                basicResult.setCode(BasicResultCode.ERR);
                basicResult.setMessage("要设置原料配比的商品信息不存在");
                return basicResult;
            }
            for (GoodsRawmaterialRelation goodsRawmaterialRelation : relationList) {
                int rawmaterialId = goodsRawmaterialRelation.getRawmaterialId();
                BigDecimal consumedQuantity = goodsRawmaterialRelation.getConsumedQuantity();
                //校验原料信息是否存在，后续优化可以在循环外面批量校验
                Rawmaterial dbRawmaterial = rawmaterialService.selectByPrimaryKey(rawmaterialId);
                if(dbRawmaterial == null){
                    basicResult.setSuccess(false);
                    basicResult.setCode(BasicResultCode.ERR);
                    basicResult.setMessage("关联的原料信息不存在");
                    return basicResult;
                }
                //判断该原料配比关系是否已存在，存在则做更新操作
                GoodsRawmaterialRelationExample example = new GoodsRawmaterialRelationExample();
                example.createCriteria().andGoodsIdEqualTo(goodsId).andRawmaterialIdEqualTo(rawmaterialId);
                List<GoodsRawmaterialRelation> goodsRawmaterialRelations = goodsRawmaterialRelationService.selectByExample(example);
                if(goodsRawmaterialRelations!=null && goodsRawmaterialRelations.size()>0){
                    //该原料配比关系存在，做更新操作
                    GoodsRawmaterialRelation updateGoodsRawmaterialRelation = new GoodsRawmaterialRelation();
                    updateGoodsRawmaterialRelation.setId(goodsRawmaterialRelations.get(0).getId());
                    updateGoodsRawmaterialRelation.setConsumedQuantity(consumedQuantity);
                    updateGoodsRawmaterialRelation.setUpdateTime(new Date());
                    goodsRawmaterialRelationService.updateByPrimaryKeySelective(updateGoodsRawmaterialRelation);

                }else{
                    //该原料配比关系不存在，做新增操作
                    GoodsRawmaterialRelation insertGoodsRawmaterialRelation = new GoodsRawmaterialRelation();
                    insertGoodsRawmaterialRelation.setGoodsId(goodsId);
                    insertGoodsRawmaterialRelation.setRawmaterialId(rawmaterialId);
                    insertGoodsRawmaterialRelation.setConsumedQuantity(consumedQuantity);
                    insertGoodsRawmaterialRelation.setCreateTime(new Date());
                    insertGoodsRawmaterialRelation.setUpdateTime(new Date());
                    goodsRawmaterialRelationService.insertSelective(insertGoodsRawmaterialRelation);
                }
            }
        }
        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("设置成功");
        return basicResult;
    }


    @ApiOperation(value = "修改原料配比")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "原料配比主键id", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "name", value = "原料配比名称", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "sortNumber", value = "排序", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "isDisabled", value = "是否启用 0-启用、1-禁用", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "detail", value = "原料配比详情描述", required = false, paramType = "query", dataType = "string")
    })
    @PutMapping(value = "/update")
    public BasicResult update(@RequestBody @Validated(value = {}) GoodsRawmaterialRelation goodsRawmaterialRelation){
        BasicResult basicResult = new BasicResult();

        goodsRawmaterialRelation.setUpdateTime(new Date());
        goodsRawmaterialRelationService.updateByPrimaryKeySelective(goodsRawmaterialRelation);

        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("修改成功");
        return basicResult;
    }


    @ApiOperation(value = "删除原料配比")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "原料配比主键id", required = true, paramType = "query", dataType = "int")
    })
    @DeleteMapping(value = "/delete")
    public BasicResult delete(@RequestBody @Validated(value = {}) GoodsRawmaterialRelation param){
        BasicResult basicResult = new BasicResult();

        GoodsRawmaterialRelation dbGoodsRawmaterialRelation = goodsRawmaterialRelationService.selectByPrimaryKey(param.getId());
        if(dbGoodsRawmaterialRelation == null){
            basicResult.setSuccess(false);
            basicResult.setCode(BasicResultCode.ERR);
            basicResult.setMessage("该原料配比不存在，删除失败");
            return basicResult;
        }

        //删除原料配比
        goodsRawmaterialRelationService.deleteByPrimaryKey(param.getId());

        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("删除成功");
        return basicResult;
    }
    @ApiOperation(value = "查询原料配比信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "原料配比表主键id", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "goodsName", value = "商品名称", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "pageNo", value = "页码(值为-1不分页)", required = true, paramType = "query", dataType = "int", defaultValue = "1"),
            @ApiImplicitParam(name = "pageSize", value = "页数", required = true, paramType = "query", dataType = "int", defaultValue = "20")
    })
    @PostMapping(value = "/find/goodsRawmaterialRelation")
    public BasicResult findGoodsRawmaterialRelation(@RequestBody @Validated(value = {}) GoodsRawmaterialRelation param){
        BasicData basicResult = new BasicData();
        Page<GoodsRawmaterialRelation> page = goodsRawmaterialRelationService.findGoodsRawmaterialRelation(param.getPageNo(), param.getPageSize(), param.getGoodsName());
        return BasicResult.success(page);
    }

    @ApiOperation(value = "通过商品查询原料配比信息详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "goodsId", value = "商品id", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "pageNo", value = "页码(值为-1不分页)", required = true, paramType = "query", dataType = "int", defaultValue = "1"),
            @ApiImplicitParam(name = "pageSize", value = "页数", required = true, paramType = "query", dataType = "int", defaultValue = "20")
    })
    @PostMapping(value = "/find/goodsRawmaterialRelationByGoodsId")
    public BasicResult findGoodsRawmaterialRelationByGoodsId(@RequestBody @Validated(value = {}) GoodsRawmaterialRelation param){
        BasicData basicResult = new BasicData();
        Page<GoodsRawmaterialRelation> page = goodsRawmaterialRelationService.selectByGoodsId(param.getPageNo(), param.getPageSize(), param.getGoodsId());
        return BasicResult.success(page);
    }
}