package com.siam.system.modular.package_goods.service.internal;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.system.modular.package_goods.entity.internal.PointsMallCouponsMemberRelation;

import java.util.Map;

public interface PointsMallCouponsMemberRelationService {

    /**
     * 创建
     * @param couponsMemberRelation
     */
    void insertSelective(PointsMallCouponsMemberRelation couponsMemberRelation);

    /**
     * 通过id查询
     * @param id
     * @return
     */
    PointsMallCouponsMemberRelation selectPointsMallCouponsMemberRelationByPrimaryKey(Integer id);

    /**
     * 使用优惠卷
     */
    void updateCouponsUsed(Integer id, Boolean isUsed);

    Page<Map<String, Object>> getListByPage(int pageNo, int pageSize, PointsMallCouponsMemberRelation couponsMemberRelation);

    void updateOverdue();

    Integer getCountsByMemberId(Integer memberId);

    void overdueSMSReminder();

    /**
     * 通过用户id获取是否含有可用优惠卷
     * @param memberId
     * @return
     */
    Boolean hasPointsMallCouponsByMemberId(Integer memberId);



}
