package com.siam.system.modular.package_goods.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.package_common.entity.BasicData;
import com.siam.package_common.entity.BasicResult;
import com.siam.package_common.constant.BasicResultCode;
import com.siam.system.modular.package_goods.model.example.GoodsRawmaterialRelationExample;
import com.siam.system.modular.package_goods.service.GoodsRawmaterialRelationService;
import com.siam.system.modular.package_goods.entity.Rawmaterial;
import com.siam.system.modular.package_goods.service.RawmaterialService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping(value = "/rest/admin/rawmaterial")
@Transactional(rollbackFor = Exception.class)
@Api(tags = "后台原料模块相关接口", description = "AdminRawmaterialController")
public class AdminRawmaterialController {
    @Autowired
    private RawmaterialService rawmaterialService;

    @Autowired
    private GoodsRawmaterialRelationService goodsRawmaterialRelationService;

    @ApiOperation(value = "原料列表")
    @PostMapping(value = "/list")
    public BasicResult list(@RequestBody @Validated(value = {}) Rawmaterial rawmaterial){
        BasicData basicResult = new BasicData();
        Page<Rawmaterial> page = rawmaterialService.getListByPage(rawmaterial.getPageNo(), rawmaterial.getPageSize(), rawmaterial);

        return BasicResult.success(page);
    }

    @ApiOperation(value = "新增原料")
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