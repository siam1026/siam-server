package com.siam.system.modular.package_goods.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.system.modular.package_goods.entity.ShoppingCart;
import com.siam.system.modular.package_goods.model.example.ShoppingCartExample;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface ShoppingCartService {
    int countByExample(ShoppingCartExample example);

    void deleteByPrimaryKey(Integer id);

    void insertSelective(ShoppingCart record);

    List<ShoppingCart> selectByExample(ShoppingCartExample example);

    ShoppingCart selectByPrimaryKey(Integer id);

    void updateByExampleSelective(ShoppingCart record, ShoppingCartExample example);

    void updateByPrimaryKeySelective(ShoppingCart record);

    Page getListByPage(int pageNo, int pageSize, ShoppingCart shoppingCart);

    Page<Map<String, Object>> getListByPageJoinGoods(int pageNo, int pageSize, ShoppingCart shoppingCart);

    /**
     * 根据id集合与用户id统计购物车数量
     * 用于校验id是否存在、以及该购物车数据是否属于当前登录用户
     * @param idList
     * @param memberId
     * @return
     */
    int countByIdListAndMemberId(List<Integer> idList, Integer memberId);

    /**
     * 根据id集合进行批量删除
     * @param idList
     * @return
     */
    int batchDeleteByIdList(List<Integer> idList);

    void updateIsGoodsExistsTo0ByGoodsId(int goodsId);

    /**
     * 查询加购商品数量
     */
    int selectCountGoodsNumber(Integer shopId, Date startTime, Date endTime);

}