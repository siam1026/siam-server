package com.siam.system.modular.package_order.service;

import com.siam.system.modular.package_order.entity.GiveLike;
import com.siam.system.modular.package_order.model.example.GiveLikeExample;

/**
 *  jianyang
 */
public interface GiveLikeService {

    void deleteByPrimaryKey(Integer id);

    int deleteByExample(GiveLikeExample example);

    void insertSelective(GiveLike giveLike);

    GiveLike selectByPrimaryKey(Integer id);

    void updateByPrimaryKeySelective(GiveLike giveLike);

    int countByAppraiseId(Integer appraiseId);

    int countByReplyId(Integer replyId);

}