package com.siam.system.modular.package_goods.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.system.modular.package_goods.entity.CouponsMemberRelation;

import java.util.Map;

public interface CouponsMemberRelationService {

    /**
     * 创建
     * @param couponsMemberRelation
     */
    void insertSelective(CouponsMemberRelation couponsMemberRelation);

    /**
     * 通过id查询
     * @param id
     * @return
     */
    CouponsMemberRelation selectCouponsMemberRelationByPrimaryKey(Integer id);

    /**
     * 使用优惠卷
     */
    void updateCouponsUsed(Integer id, Boolean isUsed);

    Page<Map<String, Object>> getListByPage(int pageNo, int pageSize, CouponsMemberRelation couponsMemberRelation);

    void updateOverdue();

    Integer getCountsByMemberId(Integer memberId);

    void overdueSMSReminder();

    /**
     * 通过用户id获取是否含有可用优惠卷
     * @param memberId
     * @return
     */
    Boolean hasCouponsByMemberId(Integer memberId);



}
