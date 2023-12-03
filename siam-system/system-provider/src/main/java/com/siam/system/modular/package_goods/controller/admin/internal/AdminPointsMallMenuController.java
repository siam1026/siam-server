package com.siam.system.modular.package_goods.controller.admin.internal;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.package_common.annoation.AdminPermission;
import com.siam.system.modular.package_goods.service.internal.PointsMallMenuGoodsRelationService;
import com.siam.system.modular.package_goods.service.internal.PointsMallMenuService;
import com.siam.package_common.entity.BasicData;
import com.siam.package_common.entity.BasicResult;
import com.siam.package_common.constant.BasicResultCode;
import com.siam.system.modular.package_goods.entity.internal.PointsMallMenu;
import com.siam.system.modular.package_goods.model.example.internal.PointsMallMenuGoodsRelationExample;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/rest/admin/pointsMall/menu")
@Transactional(rollbackFor = Exception.class)
@Api(tags = "后台菜单分类模块相关接口", description = "AdminPointsMallMenuController")
public class AdminPointsMallMenuController {

    @Autowired
    private PointsMallMenuService menuService;

    @Autowired
    private PointsMallMenuGoodsRelationService menuPointsMallGoodsRelationService;

    @ApiOperation(value = "菜单列表")
    @PostMapping(value = "/list")
    public BasicResult list(@RequestBody @Validated(value = {}) PointsMallMenu menu){
        BasicData basicResult = new BasicData();
        Page<PointsMallMenu> page = menuService.getList(menu.getPageNo(), menu.getPageSize(), menu);

        return BasicResult.success(page);
    }

    @ApiOperation(value = "菜单创建")
    @PostMapping(value = "/insert")
    public BasicResult insert(@RequestBody @Validated(value = {}) PointsMallMenu menu){
        BasicResult basicResult = new BasicResult();

        menu.setCreateTime(new Date());
        menu.setUpdateTime(new Date());
        menuService.insertSelective(menu);

        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("创建成功");
        return basicResult;
    }

    @AdminPermission
    @ApiOperation(value = "菜单修改")
    @PutMapping(value = "/update")
    public BasicResult update(@RequestBody @Validated(value = {}) PointsMallMenu menu){
        BasicResult basicResult = new BasicResult();

        menu.setUpdateTime(new Date());
        menuService.updateByPrimaryKeySelective(menu);

        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("修改成功");
        return basicResult;
    }

    @AdminPermission
    @ApiOperation(value = "菜单删除")
    @DeleteMapping(value = "/delete")
    public BasicResult delete(@RequestBody @Validated(value = {}) PointsMallMenu param){
        BasicResult basicResult = new BasicResult();
        for (Integer id : param.getIds()) {
            //判断菜单下面有关联商品，则不能删除
            PointsMallMenuGoodsRelationExample example = new PointsMallMenuGoodsRelationExample();
            example.createCriteria().andPointsMallMenuIdEqualTo(id);
            int result = menuPointsMallGoodsRelationService.countByExample(example);
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

    @ApiOperation(value = "获取单个菜单分类详情信息")
    @PostMapping(value = "/getById")
    public BasicResult getById(@RequestBody @Validated(value = {}) PointsMallMenu param){
        BasicData basicResult = new BasicData();

        PointsMallMenu menu = new PointsMallMenu();
        menu.setId(param.getId());

        Page<Map<String, Object>> page = menuService.getList(-1, 10, menu);
        List<Map<String, Object>> list = page.getRecords();
        if(list==null || list.size()==0){
            basicResult.setSuccess(false);
            basicResult.setCode(BasicResultCode.ERR);
            basicResult.setMessage("不存在该菜单信息");
            return basicResult;
        }

        basicResult.setData(list.get(0));
        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("获取成功");
        return basicResult;
    }
}
