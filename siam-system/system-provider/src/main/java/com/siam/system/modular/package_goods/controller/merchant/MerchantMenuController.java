package com.siam.system.modular.package_goods.controller.merchant;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.package_common.annoation.MerchantPermission;
import com.siam.package_common.constant.BasicResultCode;
import com.siam.package_common.entity.BasicData;
import com.siam.package_common.entity.BasicResult;
import com.siam.package_common.exception.StoneCustomerException;
import com.siam.system.modular.package_goods.entity.Menu;
import com.siam.system.modular.package_goods.model.example.MenuGoodsRelationExample;
import com.siam.system.modular.package_goods.service.MenuGoodsRelationService;
import com.siam.system.modular.package_goods.service.MenuService;
import com.siam.system.modular.package_user.auth.cache.MerchantSessionManager;
import com.siam.system.modular.package_user.entity.Merchant;
import com.siam.system.util.TokenUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RestController
@RequestMapping(value = "/rest/merchant/menu")
@Transactional(rollbackFor = Exception.class)
@Api(tags = "商家端菜单分类模块相关接口", description = "MerchantMenuController")
public class MerchantMenuController {

    @Autowired
    private MenuService menuService;

    @Autowired
    private MenuGoodsRelationService menuGoodsRelationService;

//    @Autowired
//    private MerchantService merchantService;

    @Autowired
    private MerchantSessionManager merchantSessionManager;

    @ApiOperation(value = "菜单列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "商品表主键id", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "name", value = "商品名称", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "sortNumber", value = "排序", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "isDisabled", value = "是否启用 0-启用、1-禁用", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "detail", value = "菜单分类详情描述", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "pageNo", value = "页码(值为-1不分页)", required = true, paramType = "query", dataType = "int", defaultValue = "1"),
            @ApiImplicitParam(name = "pageSize", value = "页数", required = true, paramType = "query", dataType = "int", defaultValue = "20")
    })
    @PostMapping(value = "/list")
    public BasicResult list(@RequestBody @Validated(value = {}) Menu menu, HttpServletRequest request){
        BasicData basicResult = new BasicData();

        //获取当前登录用户绑定的门店编号
        Merchant loginMerchant = merchantSessionManager.getSession(TokenUtil.getToken());

        menu.setShopId(loginMerchant.getShopId());
        Page<Menu> page = menuService.getListByAdmin(menu);

        return BasicResult.success(page);
    }

    @ApiOperation(value = "菜单创建")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "菜单主键id", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "name", value = "菜单名称", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "sortNumber", value = "排序", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "isDisabled", value = "是否启用 0-启用、1-禁用", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "detail", value = "菜单分类详情描述", required = false, paramType = "query", dataType = "string")
    })
    @PostMapping(value = "/insert")
    public BasicResult insert(@RequestBody @Validated(value = {}) Menu menu, HttpServletRequest request){
        BasicResult basicResult = new BasicResult();

        //获取当前登录用户绑定的门店编号
        Merchant loginMerchant = merchantSessionManager.getSession(TokenUtil.getToken());

        menu.setShopId(loginMerchant.getShopId());
        menu.setCreateTime(new Date());
        menu.setUpdateTime(new Date());
        menuService.insertSelective(menu);

        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("创建成功");
        return basicResult;
    }

    @MerchantPermission
    @ApiOperation(value = "菜单修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "菜单主键id", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "name", value = "菜单名称", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "sortNumber", value = "排序", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "isDisabled", value = "是否启用 0-启用、1-禁用", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "detail", value = "菜单分类详情描述", required = false, paramType = "query", dataType = "string")
    })
    @PutMapping(value = "/update")
    public BasicResult update(@RequestBody @Validated(value = {}) Menu menu, HttpServletRequest request){
        BasicResult basicResult = new BasicResult();

        //获取当前登录用户绑定的门店编号
        Merchant loginMerchant = merchantSessionManager.getSession(TokenUtil.getToken());

        Menu dbMenu = menuService.selectByPrimaryKey(menu.getId());
        if (dbMenu == null){
            throw new StoneCustomerException("该菜单不存在");
        } else if (loginMerchant.getShopId() != dbMenu.getShopId()){
            throw new StoneCustomerException("您没有权限操作该菜单");
        }

        menu.setUpdateTime(new Date());
        menuService.updateByPrimaryKeySelective(menu);

        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("修改成功");
        return basicResult;
    }

    @MerchantPermission
    @ApiOperation(value = "菜单删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "菜单主键id集合(批量删除时id以逗号分隔)", required = true, paramType = "query", dataType = "String")
    })
    @DeleteMapping(value = "/delete")
    public BasicResult delete(@RequestBody @Validated(value = {}) Menu param                   , HttpServletRequest request){
        BasicResult basicResult = new BasicResult();

        //获取当前登录用户绑定的门店编号
        Merchant loginMerchant = merchantSessionManager.getSession(TokenUtil.getToken());

        for (Integer id : param.getIds()) {
            Menu dbMenu = menuService.selectByPrimaryKey(id);
            if (dbMenu == null){
                throw new StoneCustomerException("该菜单不存在");
            } else if (loginMerchant.getShopId() != dbMenu.getShopId()){
                throw new StoneCustomerException("您没有权限操作该菜单");
            }

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