package com.siam.system.modular.package_goods.controller.admin.internal;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.package_common.annoation.AdminPermission;
import com.siam.system.modular.package_goods.service.internal.PointsMallFullReductionRuleService;
import com.siam.package_common.entity.BasicData;
import com.siam.package_common.entity.BasicResult;
import com.siam.package_common.constant.BasicResultCode;
import com.siam.system.modular.package_goods.entity.internal.PointsMallFullReductionRule;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/rest/admin/pointsMall/fullReductionRule")
@Transactional(rollbackFor = Exception.class)
@Api(tags = "后台满减规则模块相关接口", description = "AdminPointsMallFullReductionRuleController")
public class AdminPointsMallFullReductionRuleController {

    @Autowired
    private PointsMallFullReductionRuleService pointsMallFullReductionRuleService;


    @ApiOperation(value = "新增满减规则")
    @PostMapping(value = "/insert")
    public BasicResult insert(@RequestBody @Validated(value = {}) PointsMallFullReductionRule fullReductionRule){
        BasicResult basicResult = new BasicResult();

        pointsMallFullReductionRuleService.insertSelective(fullReductionRule);

        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("新增成功");
        return basicResult;
    }

    @AdminPermission
    @ApiOperation(value = "修改满减规则")
    @PutMapping(value = "/update")
    public BasicResult update(@RequestBody @Validated(value = {}) PointsMallFullReductionRule fullReductionRule){
        BasicResult basicResult = new BasicResult();

        pointsMallFullReductionRuleService.updateByPrimaryKeySelective(fullReductionRule);

        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("修改成功");
        return basicResult;
    }

    @AdminPermission
    @ApiOperation(value = "删除满减规则")
    @DeleteMapping(value = "/delete")
    public BasicResult delete(@RequestBody @Validated(value = {}) PointsMallFullReductionRule param){
        BasicResult basicResult = new BasicResult();

        pointsMallFullReductionRuleService.deleteByPrimaryKey(param.getId());

        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("删除成功");
        return basicResult;
    }

    @ApiOperation(value = "查看满减规则详情")
    @PostMapping(value = "/selectById")
    public BasicResult selectById(@RequestBody @Validated(value = {}) PointsMallFullReductionRule param){
        BasicData basicResult = new BasicData();

        PointsMallFullReductionRule fullReductionRule= pointsMallFullReductionRuleService.selectByPrimaryKey(param.getId());

        basicResult.setData(fullReductionRule);
        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("获取成功");
        return basicResult;
    }


    @ApiOperation(value = "满减规则列表")
    @PostMapping(value = "/list")
    public BasicResult list(@RequestBody @Validated(value = {}) PointsMallFullReductionRule fullReductionRule) {
        BasicData basicResult = new BasicData();

        Page<PointsMallFullReductionRule> page = pointsMallFullReductionRuleService.getListByPage(fullReductionRule.getPageNo(), fullReductionRule.getPageSize(), fullReductionRule);

        return BasicResult.success(page);
    }

}
