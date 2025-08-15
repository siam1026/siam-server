package com.siam.system.modular.package_goods.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.system.modular.package_goods.entity.Menu;

import java.util.List;
import java.util.Map;

/**
 *  暹罗
 */
public interface MenuService {

    void deleteByPrimaryKey(Integer id);

    void insert(Menu menu);

    Menu selectByPrimaryKey(Integer id);

    void updateByPrimaryKeySelective(Menu menu);

    /**
     * 菜单列表
     *
     * @return
     * @author 暹罗
     */
    List<Map<String, Object>> getList(Menu menu);

    /**
     * 菜单列表
     *
     * @return
     * @author 暹罗
     */
    List<Map<String, Object>> getListByRedis(Menu menu) throws InterruptedException;

    /**
     * 菜单列表(一次性返回菜单 + 商品数据)
     *
     * @return
     * @author 暹罗
     */
    List<Map<String, Object>> getListJoinGoods(Menu menu);

    /**
     * 菜单列表
     *
     * @return
     * @author 暹罗
     */
    Page<Menu> getListByAdmin(Menu menu);
}