package com.siam.system.modular.package_goods.controller.admin.internal;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.package_common.annoation.AdminPermission;
import com.siam.system.modular.package_goods.service.internal.PointsMallGoodsSpecificationOptionService;
import com.siam.system.modular.package_goods.service.internal.PointsMallGoodsSpecificationService;
import com.siam.package_common.entity.BasicData;
import com.siam.package_common.entity.BasicResult;
import com.siam.package_common.constant.BasicResultCode;
import com.siam.system.modular.package_goods.model.dto.internal.PointsMallGoodsSpecificationOptionDto;
import com.siam.system.modular.package_goods.entity.internal.PointsMallGoodsSpecification;
import com.siam.system.modular.package_goods.entity.internal.PointsMallGoodsSpecificationOption;
import com.siam.system.modular.package_goods.model.example.internal.PointsMallGoodsSpecificationOptionExample;
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
@RequestMapping(value = "/rest/admin/pointsMall/goodsSpecificationOption")
@Transactional(rollbackFor = Exception.class)
@Api(tags = "后台商品规格选项模块相关接口", description = "AdminPointsMallGoodsSpecificationOptionController")
public class AdminPointsMallGoodsSpecificationOptionController {
    @Autowired
    private PointsMallGoodsSpecificationOptionService goodsSpecificationOptionService;

    @Autowired
    private PointsMallGoodsSpecificationService goodsSpecificationService;

    @ApiOperation(value = "商品规格选项列表")
    @PostMapping(value = "/list")
    public BasicResult list(@RequestBody @Validated(value = {}) PointsMallGoodsSpecificationOptionDto goodsSpecificationOptionDto){
        BasicData basicResult = new BasicData();

        Page<Map<String, Object>> page = goodsSpecificationOptionService.getListByPageJoinPointsMallGoods(goodsSpecificationOptionDto.getPageNo(), goodsSpecificationOptionDto.getPageSize(), goodsSpecificationOptionDto);

        return BasicResult.success(page);
    }

    @ApiOperation(value = "添加商品规格选项")
    @PostMapping(value = "/insert")
    public BasicResult insert(@RequestBody @Validated(value = {}) PointsMallGoodsSpecificationOptionDto goodsSpecificationOptionDto){
        BasicData basicResult = new BasicData();

        //判断商品规格选项是否重复  只要名字相同，不管父类规格是什么，都视为重复
        PointsMallGoodsSpecificationOptionExample example = new PointsMallGoodsSpecificationOptionExample();
        example.createCriteria().andPointsMallGoodsIdEqualTo(goodsSpecificationOptionDto.getGoodsId()).andNameEqualTo(goodsSpecificationOptionDto.getName());
        int result = goodsSpecificationOptionService.countByExample(example);
        if(result > 0){
            basicResult.setSuccess(false);
            basicResult.setCode(BasicResultCode.ERR);
            basicResult.setMessage("奶茶口味名称重复，添加失败");
            return basicResult;
        }

        //判断规格名称是否存在
        PointsMallGoodsSpecification dbSpecification = goodsSpecificationService.selectByPointsMallGoodsIdAndName(goodsSpecificationOptionDto.getGoodsId(), goodsSpecificationOptionDto.getSpecificationName());
        if(dbSpecification == null){
            //查询商品规格的最大排序号
            int maxSortNumber = goodsSpecificationService.selectMaxSortNumberByPointsMallGoodsId(goodsSpecificationOptionDto.getGoodsId());
            //先添加商品规格
            PointsMallGoodsSpecification insertSpecification = new PointsMallGoodsSpecification();
            insertSpecification.setGoodsId(goodsSpecificationOptionDto.getGoodsId());
            insertSpecification.setName(goodsSpecificationOptionDto.getSpecificationName());
            insertSpecification.setSortNumber(maxSortNumber + 1);
            insertSpecification.setCreateTime(new Date());
            insertSpecification.setUpdateTime(new Date());
            goodsSpecificationService.insertSelective(insertSpecification);
            dbSpecification = insertSpecification;
        }

        //查询商品规格选项的最大排序号
        int maxSortNumber = goodsSpecificationOptionService.selectMaxSortNumberByPointsMallGoodsSpecificationId(dbSpecification.getId());
        //再添加商品规格选项
        PointsMallGoodsSpecificationOption insertPointsMallGoodsSpecificationOption = new PointsMallGoodsSpecificationOption();
        BeanUtils.copyProperties(goodsSpecificationOptionDto, insertPointsMallGoodsSpecificationOption);
        insertPointsMallGoodsSpecificationOption.setGoodsSpecificationId(dbSpecification.getId());
        insertPointsMallGoodsSpecificationOption.setSortNumber(maxSortNumber + 1);
        insertPointsMallGoodsSpecificationOption.setCreateTime(new Date());
        insertPointsMallGoodsSpecificationOption.setUpdateTime(new Date());
        goodsSpecificationOptionService.insertSelective(insertPointsMallGoodsSpecificationOption);

        //回写刚创建的id
        int id = insertPointsMallGoodsSpecificationOption.getId();

        basicResult.setData(id);
        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("添加成功");
        return basicResult;
    }

    @AdminPermission
    @ApiOperation(value = "修改商品规格选项")
    @PostMapping(value = "/update")
    public BasicResult update(@RequestBody @Validated(value = {}) PointsMallGoodsSpecificationOptionDto goodsSpecificationOptionDto){
        BasicResult basicResult = new BasicResult();

        PointsMallGoodsSpecificationOption dbPointsMallGoodsSpecificationOption = goodsSpecificationOptionService.selectByPrimaryKey(goodsSpecificationOptionDto.getId());
        if(dbPointsMallGoodsSpecificationOption == null){
            basicResult.setSuccess(false);
            basicResult.setCode(BasicResultCode.ERR);
            basicResult.setMessage("该奶茶口味不存在，修改失败");
            return basicResult;
        }

        //判断商品规格选项是否重复  只要名字相同，不管父类规格是什么，都视为重复
        PointsMallGoodsSpecificationOptionExample example = new PointsMallGoodsSpecificationOptionExample();
        example.createCriteria().andPointsMallGoodsIdEqualTo(dbPointsMallGoodsSpecificationOption.getGoodsId()).andNameEqualTo(goodsSpecificationOptionDto.getName()).andIdNotEqualTo(goodsSpecificationOptionDto.getId());
        int result = goodsSpecificationOptionService.countByExample(example);
        if(result > 0){
            basicResult.setSuccess(false);
            basicResult.setCode(BasicResultCode.ERR);
            basicResult.setMessage("奶茶口味名称重复，修改失败");
            return basicResult;
        }

        //查询原规格名称
        PointsMallGoodsSpecification goodsSpecification = goodsSpecificationService.selectByPrimaryKey(dbPointsMallGoodsSpecificationOption.getGoodsSpecificationId());
        //判断规格名称是否被修改 以及 新规格名称是否存在
        if(dbPointsMallGoodsSpecificationOption.equals(goodsSpecification.getName())){
            //规格名称未被修改
        }else{
            //规格名称被修改
            //判断新规格名称是否存在
            PointsMallGoodsSpecification dbSpecification = goodsSpecificationService.selectByPointsMallGoodsIdAndName(dbPointsMallGoodsSpecificationOption.getGoodsId(), goodsSpecificationOptionDto.getSpecificationName());
            if(dbSpecification == null){
                //查询商品规格的最大排序号
                int maxSortNumber = goodsSpecificationService.selectMaxSortNumberByPointsMallGoodsId(goodsSpecificationOptionDto.getGoodsId());
                //先添加商品规格
                PointsMallGoodsSpecification insertSpecification = new PointsMallGoodsSpecification();
                insertSpecification.setGoodsId(goodsSpecificationOptionDto.getGoodsId());
                insertSpecification.setName(goodsSpecificationOptionDto.getSpecificationName());
                insertSpecification.setSortNumber(maxSortNumber + 1);
                insertSpecification.setCreateTime(new Date());
                insertSpecification.setUpdateTime(new Date());
                goodsSpecificationService.insertSelective(insertSpecification);
                dbSpecification = insertSpecification;
            }
            goodsSpecificationOptionDto.setGoodsSpecificationId(dbSpecification.getId());
        }

        //修改商品规格选项记录
        //再添加商品规格选项
        PointsMallGoodsSpecificationOption updatePointsMallGoodsSpecificationOption = new PointsMallGoodsSpecificationOption();
        updatePointsMallGoodsSpecificationOption.setId(goodsSpecificationOptionDto.getId());
        updatePointsMallGoodsSpecificationOption.setGoodsSpecificationId(goodsSpecificationOptionDto.getGoodsSpecificationId());
        updatePointsMallGoodsSpecificationOption.setName(goodsSpecificationOptionDto.getName());
        updatePointsMallGoodsSpecificationOption.setPrice(goodsSpecificationOptionDto.getPrice());
        goodsSpecificationOptionService.updateByPrimaryKeySelective(updatePointsMallGoodsSpecificationOption);

        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("修改成功");
        return basicResult;
    }

    @AdminPermission
    @ApiOperation(value = "删除商品规格选项(含批量操作)")
    @PostMapping(value = "/delete")
    public BasicResult delete(@RequestBody @Validated(value = {}) PointsMallGoodsSpecificationOption param){
        BasicResult basicResult = new BasicResult();

        for(int id : param.getIds()){
            PointsMallGoodsSpecificationOption dbPointsMallGoodsSpecificationOption = goodsSpecificationOptionService.selectByPrimaryKey(id);
            if(dbPointsMallGoodsSpecificationOption == null){
                basicResult.setSuccess(false);
                basicResult.setCode(BasicResultCode.ERR);
                basicResult.setMessage("该奶茶口味不存在，删除失败");
                return basicResult;
            }

            //删除商品规格选项记录
            goodsSpecificationOptionService.deleteByPrimaryKey(id);

            //判断对应的父类规格 是否有其他商品规格选项 如果没有则一并删除
            PointsMallGoodsSpecificationOptionExample example = new PointsMallGoodsSpecificationOptionExample();
            example.createCriteria().andPointsMallGoodsIdEqualTo(dbPointsMallGoodsSpecificationOption.getGoodsId()).andPointsMallGoodsSpecificationIdEqualTo(dbPointsMallGoodsSpecificationOption.getGoodsSpecificationId());
            int result = goodsSpecificationOptionService.countByExample(example);
            if(result == 0){
                goodsSpecificationService.deleteByPrimaryKey(dbPointsMallGoodsSpecificationOption.getGoodsSpecificationId());
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