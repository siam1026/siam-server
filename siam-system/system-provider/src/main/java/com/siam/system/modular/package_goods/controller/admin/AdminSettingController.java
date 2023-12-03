package com.siam.system.modular.package_goods.controller.admin;

import com.siam.package_common.annoation.AdminPermission;
import com.siam.package_common.entity.BasicData;
import com.siam.package_common.entity.BasicResult;
import com.siam.package_common.constant.BasicResultCode;
import com.siam.system.modular.package_goods.service.GoodsSpecificationOptionService;
import com.siam.system.modular.package_goods.entity.Setting;
import com.siam.system.modular.package_goods.service.SettingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping(value = "/rest/admin/setting")
@Transactional(rollbackFor = Exception.class)
@Api(tags = "后台基础数据设置模块相关接口", description = "AdminSettingController")
public class AdminSettingController {

    @Autowired
    private SettingService settingService;

    @Autowired
    private GoodsSpecificationOptionService goodsSpecificationOptionService;

    /*@ApiOperation(value = "基础数据设置列表")
    @PostMapping(value = "/list")
    public BasicResult list(int pageNo, int pageSize, Setting setting){
        BasicData basicResult = new BasicData();
        Page<Setting> page = settingService.getListByPage(param.getPageNo(), param.getPageSize(), setting);

        return BasicResult.success(page);
    }*/

    @ApiOperation(value = "基础数据设置列表")
    @PostMapping(value = "/selectCurrent")
    public BasicResult selectCurrent(@RequestBody @Validated(value = {}) Setting setting){
        BasicData basicResult = new BasicData();

        Setting current = settingService.selectCurrent();

        basicResult.setData(current);
        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("查询成功");
        return basicResult;
    }

    @AdminPermission
    @ApiOperation(value = "修改基础数据设置")
    @PutMapping(value = "/update")
    public BasicResult update(@RequestBody @Validated(value = {}) Setting setting){
        BasicResult basicResult = new BasicResult();

        setting.setUpdateTime(new Date());
        settingService.updateByPrimaryKeySelective(setting);

        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("修改成功");
        return basicResult;
    }
}