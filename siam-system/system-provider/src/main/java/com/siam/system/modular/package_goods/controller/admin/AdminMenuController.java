package com.siam.system.modular.package_goods.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.package_common.entity.BasicData;
import com.siam.package_common.entity.BasicResult;
import com.siam.package_common.constant.BasicResultCode;
import com.siam.system.modular.package_goods.entity.Menu;
import com.siam.system.modular.package_goods.service.MenuService;
import com.siam.system.modular.package_goods.model.example.MenuGoodsRelationExample;
import com.siam.system.modular.package_goods.service.MenuGoodsRelationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping(value = "/rest/admin/menu")
@Transactional(rollbackFor = Exception.class)
@Api(tags = "后台菜单分类模块相关接口", description = "AdminMenuController")
public class AdminMenuController {

    @Autowired
    private MenuService menuService;

    @Autowired
    private MenuGoodsRelationService menuGoodsRelationService;

    @ApiOperation(value = "菜单列表")
    @PostMapping(value = "/list")
    public BasicResult list(@RequestBody @Validated(value = {}) Menu param){
        BasicData basicResult = new BasicData();
        Page<Menu> page = menuService.getListByAdmin(param);

        return BasicResult.success(page);
    }

    @ApiOperation(value = "菜单创建")
    @PostMapping(value = "/insert")
    public BasicResult insert(@RequestBody @Validated(value = {}) Menu param){
        BasicResult basicResult = new BasicResult();

        param.setCreateTime(new Date());
        param.setUpdateTime(new Date());
        menuService.insertSelective(param);

        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("创建成功");
        return basicResult;
    }


    @ApiOperation(value = "菜单修改")
    @PutMapping(value = "/update")
    public BasicResult update(@RequestBody @Validated(value = {}) Menu param){
        BasicResult basicResult = new BasicResult();

        param.setUpdateTime(new Date());
        menuService.updateByPrimaryKeySelective(param);

        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("修改成功");
        return basicResult;
    }


    @ApiOperation(value = "菜单删除")
    @DeleteMapping(value = "/delete")
    public BasicResult delete(@RequestBody @Validated(value = {}) Menu param){
        BasicResult basicResult = new BasicResult();
        for (Integer id : param.getIds()) {
            //判断菜单下面有关联商品，则不能删除
            MenuGoodsRelationExample example = new MenuGoodsRelationExample();
            example.createCriteria().andMenuIdEqualTo(id);
            int result = menuGoodsRelationService.countByExample(example);
            if(result > 0){
                basicResult.setSuccess(false);
                basicResult.setCode(BasicResultCode.ERR);
                basicResult.setMessage("该类别下有关联奶茶信息，不允许删除");
                return basicResult;
            }

            menuService.deleteByPrimaryKey(id);
        }
        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("删除成功");
        return basicResult;
    }
}
