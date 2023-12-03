package com.siam.system.modular.package_goods.controller.member.internal;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.system.modular.package_goods.service.internal.PointsMallFullReductionRuleService;
import com.siam.package_common.entity.BasicData;
import com.siam.package_common.entity.BasicResult;
import com.siam.system.modular.package_goods.entity.internal.PointsMallFullReductionRule;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/rest/pointsMall/fullReductionRule")
@Transactional(rollbackFor = Exception.class)
@Api(tags = "满减规则模块相关接口", description = "PointsMallFullReductionRuleController")
public class PointsMallFullReductionRuleController {

    @Autowired
    private PointsMallFullReductionRuleService fullReductionRuleService;

    @ApiOperation(value = "满减规则列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键id", required = true, paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "name", value = "满减规则名称", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "status", value = "状态", required = true, paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "limitedPrice", value = "分类名称", required = false, paramType = "query", dataType = "BigDecimal"),
            @ApiImplicitParam(name = "reducedPrice", value = "品牌id", required = true, paramType = "query", dataType = "BigDecimal"),
            @ApiImplicitParam(name = "pageNo", value = "页码(值为-1不分页)", required = true, paramType = "query", dataType = "int", defaultValue = "1"),
            @ApiImplicitParam(name = "pageSize", value = "页数", required = true, paramType = "query", dataType = "int", defaultValue = "20")
    })
    @PostMapping(value = "/list")
    public BasicResult list(@RequestBody @Validated(value = {}) PointsMallFullReductionRule fullReductionRule){
        BasicData basicResult = new BasicData();

        Page<PointsMallFullReductionRule> page = fullReductionRuleService.getListByPage(fullReductionRule.getPageNo(), fullReductionRule.getPageSize(), fullReductionRule);

        return BasicResult.success(page);
    }

    /*@ApiOperation(value = "查看满减规则详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键id", required = true, paramType = "query", dataType = "Integer"),
    })
    @PostMapping(value = "/selectById")
    public BasicResult selectById(@RequestParam(value = "id", required = true) int id){
        BasicData basicResult = new BasicData();

        PointsMallFullReductionRule fullReductionRule=fullReductionRuleService.selectByPrimaryKey(id);

        basicResult.setData(fullReductionRule);
        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("获取成功");
        return basicResult;
    }*/


}
