package com.siam.system.modular.package_goods.service.internal;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.system.modular.package_goods.entity.internal.MemberGoodsCollect;
import com.siam.system.modular.package_goods.model.dto.internal.MemberGoodsCollectDto;
import com.siam.system.modular.package_goods.model.example.internal.MemberGoodsCollectExample;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface MemberGoodsCollectService {
    int countByExample(MemberGoodsCollectExample example);

    void deleteByPrimaryKey(Integer id);

    void insertSelective(MemberGoodsCollect record);

    List<MemberGoodsCollect> selectByExample(MemberGoodsCollectExample example);

    MemberGoodsCollect selectByPrimaryKey(Integer id);

    void updateByExampleSelective(MemberGoodsCollect record, MemberGoodsCollectExample example);

    void updateByPrimaryKeySelective(MemberGoodsCollect record);

  Page getListByPage(int pageNo, int pageSize, MemberGoodsCollect MemberGoodsCollect);

    Page<Map<String, Object>> getListByPageJoinGoods(int pageNo, int pageSize, MemberGoodsCollectDto memberGoodsCollectDto);

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

    void updateIsBuy();
}