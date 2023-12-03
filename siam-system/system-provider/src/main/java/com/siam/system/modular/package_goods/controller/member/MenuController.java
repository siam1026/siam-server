package com.siam.system.modular.package_goods.controller.member;

import com.siam.package_common.entity.BasicResult;
import com.siam.system.modular.package_goods.entity.Menu;
import com.siam.system.modular.package_goods.service.MenuService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(value = "/rest/menu")
@Transactional(rollbackFor = Exception.class)
@Api(tags = "菜单分类模块相关接口", description = "MenuController")
public class MenuController{

    @Autowired
    private MenuService menuService;

    /**
     * 菜单列表
     *
     * @return
     * @author 暹罗
     */
    @PostMapping(value = "/list")
    public BasicResult list(@RequestBody @Validated(value = {}) Menu menu){
        List<Map<String, Object>> list = menuService.getList(menu);
        return BasicResult.success(list);
    }

    /**
     * 菜单列表
     *
     * @return
     * @author 暹罗
     */
    @GetMapping(value = "/list")
    public BasicResult listByRedis() throws InterruptedException {
        Menu menu = new Menu();
        menu.setShopId(13);
        List<Map<String, Object>> list = menuService.getListByRedis(menu);
        return BasicResult.success(list);
    }

    /**
     * 菜单列表(一次性返回菜单 + 商品数据)
     *
     * @return
     * @author 暹罗
     */
    @PostMapping(value = "/listWithGoods")
    public BasicResult listWithGoods(@RequestBody @Validated(value = {}) Menu menu){
        List<Map<String, Object>> list = menuService.getListJoinGoods(menu);
        return BasicResult.success(list);
    }
}