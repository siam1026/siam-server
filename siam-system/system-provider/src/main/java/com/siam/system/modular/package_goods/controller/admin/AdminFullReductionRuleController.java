package com.siam.system.modular.package_goods.controller.admin;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.package_common.entity.BasicData;
import com.siam.package_common.entity.BasicResult;
import com.siam.package_common.constant.BasicResultCode;
import com.siam.system.modular.package_goods.entity.FullReductionRule;
import com.siam.system.modular.package_goods.service.FullReductionRuleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/rest/admin/fullReductionRule")
@Transactional(rollbackFor = Exception.class)
@Api(tags = "后台满减规则模块相关接口", description = "AdminFullReductionRuleController")
public class AdminFullReductionRuleController {

    @Autowired
    private FullReductionRuleService fullReductionRuleService;


    @ApiOperation(value = "新增满减规则")
    @PostMapping(value = "/insert")
    public BasicResult insert(@RequestBody @Validated(value = {}) FullReductionRule param){
        BasicResult basicResult = new BasicResult();

        fullReductionRuleService.insertSelective(param);

        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("新增成功");
        return basicResult;
    }

    @ApiOperation(value = "修改满减规则")
    @PutMapping(value = "/update")
    public BasicResult update(@RequestBody @Validated(value = {}) FullReductionRule param){
        BasicResult basicResult = new BasicResult();

        fullReductionRuleService.updateByPrimaryKeySelective(param);

        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("修改成功");
        return basicResult;
    }

    @ApiOperation(value = "删除满减规则")
    @DeleteMapping(value = "/delete")
    public BasicResult delete(@RequestBody @Validated(value = {}) FullReductionRule param){
        BasicResult basicResult = new BasicResult();

        fullReductionRuleService.deleteByPrimaryKey(param.getId());

        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("删除成功");
        return basicResult;
    }

    @ApiOperation(value = "查看满减规则详情")
    @PostMapping(value = "/selectById")
    public BasicResult selectById(@RequestBody @Validated(value = {}) FullReductionRule param){
        BasicData basicResult = new BasicData();

        FullReductionRule fullReductionRule=fullReductionRuleService.selectByPrimaryKey(param.getId());

        basicResult.setData(fullReductionRule);
        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("获取成功");
        return basicResult;
    }


    @ApiOperation(value = "满减规则列表")
    @PostMapping(value = "/list")
    public BasicResult list(@RequestBody @Validated(value = {}) FullReductionRule param){
        BasicData basicResult = new BasicData();

        Page<FullReductionRule> page = fullReductionRuleService.getListByPage(param.getPageNo(), param.getPageSize(), param);

        return BasicResult.success(page);
    }

}
