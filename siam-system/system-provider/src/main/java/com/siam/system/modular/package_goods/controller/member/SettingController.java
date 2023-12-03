package com.siam.system.modular.package_goods.controller.member;

import com.siam.package_common.constant.BasicResultCode;
import com.siam.package_common.entity.BasicData;
import com.siam.package_common.entity.BasicResult;
import com.siam.system.modular.package_goods.entity.Setting;
import com.siam.system.modular.package_goods.service.SettingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/rest/setting")
@Transactional(rollbackFor = Exception.class)
@Api(tags = "基础数据设置模块相关接口", description = "SettingController")
public class SettingController {
    @Autowired
    private SettingService settingService;

    @ApiOperation(value = "查询基础数据设置")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "基础数据设置表主键id", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "purchaseRewardPoints", value = "购买一杯奶茶赠送积分数量", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "registrationRewardPoints", value = "新用户注册奖励积分数量", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "pageNo", value = "页码(值为-1不分页)", required = true, paramType = "query", dataType = "int", defaultValue = "1"),
            @ApiImplicitParam(name = "pageSize", value = "页数", required = true, paramType = "query", dataType = "int", defaultValue = "20"),
    })
    @PostMapping(value = "/selectCurrent")
    public BasicResult selectCurrent(@RequestBody @Validated(value = {}) Setting setting){
        BasicData basicResult = new BasicData();

        Setting currentSetting = settingService.selectCurrent();

        basicResult.setData(currentSetting);
        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("查询成功");
        return basicResult;
    }
}