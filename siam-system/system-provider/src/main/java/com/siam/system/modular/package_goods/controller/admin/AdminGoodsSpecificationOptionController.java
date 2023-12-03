package com.siam.system.modular.package_goods.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.package_common.entity.BasicData;
import com.siam.package_common.entity.BasicResult;
import com.siam.package_common.constant.BasicResultCode;
import com.siam.system.modular.package_goods.entity.GoodsAccessories;
import com.siam.system.modular.package_goods.service.GoodsAccessoriesService;
import com.siam.system.modular.package_goods.entity.GoodsSpecification;
import com.siam.system.modular.package_goods.service.GoodsSpecificationService;
import com.siam.system.modular.package_goods.entity.GoodsSpecificationOption;
import com.siam.system.modular.package_goods.model.example.GoodsSpecificationOptionExample;
import com.siam.system.modular.package_goods.model.dto.GoodsSpecificationOptionDto;
import com.siam.system.modular.package_goods.service.GoodsSpecificationOptionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping(value = "/rest/admin/goodsSpecificationOption")
@Transactional(rollbackFor = Exception.class)
@Api(tags = "后台商品规格选项模块相关接口", description = "AdminGoodsSpecificationOptionController")
public class AdminGoodsSpecificationOptionController {
    @Autowired
    private GoodsSpecificationOptionService goodsSpecificationOptionService;

    @Autowired
    private GoodsSpecificationService goodsSpecificationService;

    @Autowired
    private GoodsAccessoriesService goodsAccessoriesService;

    @ApiOperation(value = "商品规格选项列表")
    @PostMapping(value = "/list")
    public BasicResult list(@RequestBody @Validated(value = {}) GoodsSpecificationOptionDto param){
        BasicData basicResult = new BasicData();

        Page<Map<String, Object>> page = goodsSpecificationOptionService.getListByPageJoinGoods(param.getPageNo(), param.getPageSize(), param);

        return BasicResult.success(page);
    }

    @ApiOperation(value = "添加商品规格选项")
    @PostMapping(value = "/insert")
    public BasicResult insert(@RequestBody @Validated(value = {}) GoodsSpecificationOptionDto param){
        BasicData basicResult = new BasicData();

        //检查商品规格选项 是否属于 商品辅料，如果是，则等于辅料的价格、库存
        GoodsAccessories dbGoodsAccessories = goodsAccessoriesService.selectByName(param.getName());
        if(dbGoodsAccessories != null){
            param.setPrice(dbGoodsAccessories.getPrice());
            param.setStock(dbGoodsAccessories.getStock());
        }

        //判断商品规格选项是否重复  只要名字相同，不管父类规格是什么，都视为重复
        GoodsSpecificationOptionExample example = new GoodsSpecificationOptionExample();
        example.createCriteria().andGoodsIdEqualTo(param.getGoodsId()).andNameEqualTo(param.getName());
        int result = goodsSpecificationOptionService.countByExample(example);
        if(result > 0){
            basicResult.setSuccess(false);
            basicResult.setCode(BasicResultCode.ERR);
            basicResult.setMessage("奶茶口味名称重复，添加失败");
            return basicResult;
        }

        //判断规格名称是否存在
        GoodsSpecification dbSpecification = goodsSpecificationService.selectByGoodsIdAndName(param.getGoodsId(), param.getSpecificationName());
        if(dbSpecification == null){
            //查询商品规格的最大排序号
            int maxSortNumber = goodsSpecificationService.selectMaxSortNumberByGoodsId(param.getGoodsId());
            //先添加商品规格
            GoodsSpecification insertSpecification = new GoodsSpecification();
            insertSpecification.setGoodsId(param.getGoodsId());
            insertSpecification.setName(param.getSpecificationName());
            insertSpecification.setSortNumber(maxSortNumber + 1);
            insertSpecification.setCreateTime(new Date());
            insertSpecification.setUpdateTime(new Date());
            goodsSpecificationService.insertSelective(insertSpecification);
            dbSpecification = insertSpecification;
        }

        //查询商品规格选项的最大排序号
        int maxSortNumber = goodsSpecificationOptionService.selectMaxSortNumberByGoodsSpecificationId(dbSpecification.getId());
        //再添加商品规格选项
        GoodsSpecificationOption insertGoodsSpecificationOption = new GoodsSpecificationOption();
        BeanUtils.copyProperties(param, insertGoodsSpecificationOption);
        insertGoodsSpecificationOption.setGoodsSpecificationId(dbSpecification.getId());
        insertGoodsSpecificationOption.setSortNumber(maxSortNumber + 1);
        insertGoodsSpecificationOption.setCreateTime(new Date());
        insertGoodsSpecificationOption.setUpdateTime(new Date());
        goodsSpecificationOptionService.insertSelective(insertGoodsSpecificationOption);

        //回写刚创建的id
        int id = insertGoodsSpecificationOption.getId();

        basicResult.setData(id);
        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("添加成功");
        return basicResult;
    }

    @ApiOperation(value = "修改商品规格选项")
    @PostMapping(value = "/update")
    public BasicResult update(@RequestBody @Validated(value = {}) GoodsSpecificationOptionDto param){
        BasicResult basicResult = new BasicResult();

        GoodsSpecificationOption dbGoodsSpecificationOption = goodsSpecificationOptionService.selectByPrimaryKey(param.getId());
        if(dbGoodsSpecificationOption == null){
            basicResult.setSuccess(false);
            basicResult.setCode(BasicResultCode.ERR);
            basicResult.setMessage("该奶茶口味不存在，修改失败");
            return basicResult;
        }

        //判断商品规格选项是否重复  只要名字相同，不管父类规格是什么，都视为重复
        GoodsSpecificationOptionExample example = new GoodsSpecificationOptionExample();
        example.createCriteria().andGoodsIdEqualTo(dbGoodsSpecificationOption.getGoodsId()).andNameEqualTo(param.getName()).andIdNotEqualTo(param.getId());
        int result = goodsSpecificationOptionService.countByExample(example);
        if(result > 0){
            basicResult.setSuccess(false);
            basicResult.setCode(BasicResultCode.ERR);
            basicResult.setMessage("奶茶口味名称重复，修改失败");
            return basicResult;
        }

        //查询原规格名称
        GoodsSpecification goodsSpecification = goodsSpecificationService.selectByPrimaryKey(dbGoodsSpecificationOption.getGoodsSpecificationId());
        //判断规格名称是否被修改 以及 新规格名称是否存在
        if(dbGoodsSpecificationOption.equals(goodsSpecification.getName())){
            //规格名称未被修改
        }else{
            //规格名称被修改
            //判断新规格名称是否存在
            GoodsSpecification dbSpecification = goodsSpecificationService.selectByGoodsIdAndName(dbGoodsSpecificationOption.getGoodsId(), param.getSpecificationName());
            if(dbSpecification == null){
                //查询商品规格的最大排序号
                int maxSortNumber = goodsSpecificationService.selectMaxSortNumberByGoodsId(param.getGoodsId());
                //先添加商品规格
                GoodsSpecification insertSpecification = new GoodsSpecification();
                insertSpecification.setGoodsId(param.getGoodsId());
                insertSpecification.setName(param.getSpecificationName());
                insertSpecification.setSortNumber(maxSortNumber + 1);
                insertSpecification.setCreateTime(new Date());
                insertSpecification.setUpdateTime(new Date());
                goodsSpecificationService.insertSelective(insertSpecification);
                dbSpecification = insertSpecification;
            }
            param.setGoodsSpecificationId(dbSpecification.getId());
        }

        //修改商品规格选项记录
        //再添加商品规格选项
        GoodsSpecificationOption updateGoodsSpecificationOption = new GoodsSpecificationOption();
        updateGoodsSpecificationOption.setId(param.getId());
        updateGoodsSpecificationOption.setGoodsSpecificationId(param.getGoodsSpecificationId());
        updateGoodsSpecificationOption.setName(param.getName());
        updateGoodsSpecificationOption.setPrice(param.getPrice());
        goodsSpecificationOptionService.updateByPrimaryKeySelective(updateGoodsSpecificationOption);

        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("修改成功");
        return basicResult;
    }

    @ApiOperation(value = "删除商品规格选项(含批量操作)")
    @PostMapping(value = "/delete")
    public BasicResult delete(@RequestBody @Validated(value = {}) GoodsSpecificationOptionDto param){
        BasicResult basicResult = new BasicResult();

        for(int id : param.getIds()){
            GoodsSpecificationOption dbGoodsSpecificationOption = goodsSpecificationOptionService.selectByPrimaryKey(id);
            if(dbGoodsSpecificationOption == null){
                basicResult.setSuccess(false);
                basicResult.setCode(BasicResultCode.ERR);
                basicResult.setMessage("该奶茶口味不存在，删除失败");
                return basicResult;
            }

            //删除商品规格选项记录
            goodsSpecificationOptionService.deleteByPrimaryKey(id);

            //判断对应的父类规格 是否有其他商品规格选项 如果没有则一并删除
            GoodsSpecificationOptionExample example = new GoodsSpecificationOptionExample();
            example.createCriteria().andGoodsIdEqualTo(dbGoodsSpecificationOption.getGoodsId()).andGoodsSpecificationIdEqualTo(dbGoodsSpecificationOption.getGoodsSpecificationId());
            int result = goodsSpecificationOptionService.countByExample(example);
            if(result == 0){
                goodsSpecificationService.deleteByPrimaryKey(dbGoodsSpecificationOption.getGoodsSpecificationId());
            }
        }

        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("删除成功");
        return basicResult;
    }

    /*@ApiOperation(value = "生成商品公共口味")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "goodsId", value = "商品id", required = true, paramType = "query", dataType = "int")
    })
    @PostMapping(value = "/insertPublicSpecification")
    public BasicResult insertPublicSpecification(int goodsId){
        BasicResult basicResult = new BasicResult();

        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("生成成功");
        return basicResult;
    }*/
}