package com.siam.system.modular.package_goods.service.internal;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.system.modular.package_goods.entity.internal.PointsMallMemberGoodsCollect;
import com.siam.system.modular.package_goods.model.dto.internal.PointsMallMemberGoodsCollectDto;
import com.siam.system.modular.package_goods.model.example.internal.PointsMallMemberGoodsCollectExample;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface PointsMallMemberGoodsCollectService {
    int countByExample(PointsMallMemberGoodsCollectExample example);

    void deleteByPrimaryKey(Integer id);

    void insertSelective(PointsMallMemberGoodsCollect record);

    List<PointsMallMemberGoodsCollect> selectByExample(PointsMallMemberGoodsCollectExample example);

    PointsMallMemberGoodsCollect selectByPrimaryKey(Integer id);

    void updateByExampleSelective(PointsMallMemberGoodsCollect record, PointsMallMemberGoodsCollectExample example);

    void updateByPrimaryKeySelective(PointsMallMemberGoodsCollect record);

    Page getListByPage(int pageNo, int pageSize, PointsMallMemberGoodsCollect PointsMallMemberGoodsCollect);

    Page<Map<String, Object>> getListByPageJoinPointsMallGoods(int pageNo, int pageSize, PointsMallMemberGoodsCollectDto pointsMallMemberGoodsCollectDto);

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

    void updateIsGoodsExistsTo0ByPointsMallGoodsId(int goodsId);

    /**
     * 查询加购商品数量
     */
    int selectCountPointsMallGoodsNumber(Integer shopId, Date startTime, Date endTime);

    void updateIsBuy();
}