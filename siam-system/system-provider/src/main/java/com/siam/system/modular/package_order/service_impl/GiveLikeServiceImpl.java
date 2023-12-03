package com.siam.system.modular.package_order.service_impl;

import com.siam.package_common.constant.Quantity;
import com.siam.system.modular.package_order.entity.GiveLike;
import com.siam.system.modular.package_order.mapper.GiveLikeMapper;
import com.siam.system.modular.package_order.model.example.GiveLikeExample;
import com.siam.system.modular.package_order.service.GiveLikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GiveLikeServiceImpl implements GiveLikeService {

    @Autowired
    private GiveLikeMapper giveLikeMapper;

    @Override
    public void deleteByPrimaryKey(Integer id) {
        giveLikeMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int deleteByExample(GiveLikeExample example) {
        return giveLikeMapper.deleteByExample(example);
    }

    @Override
    public void insertSelective(GiveLike giveLike) {
        giveLikeMapper.insertSelective(giveLike);
    }

    @Override
    public GiveLike selectByPrimaryKey(Integer id) {
        return giveLikeMapper.selectByPrimaryKey(id);
    }

    @Override
    public void updateByPrimaryKeySelective(GiveLike giveLike) {
        giveLikeMapper.updateByPrimaryKeySelective(giveLike);
    }

    @Override
    public int countByAppraiseId(Integer appraiseId) {
        GiveLikeExample example = new GiveLikeExample();
        example.createCriteria().andTypeEqualTo(Quantity.INT_1).andAppraiseIdEqualTo(appraiseId);
        return giveLikeMapper.countByExample(example);
    }

    @Override
    public int countByReplyId(Integer replyId) {
        GiveLikeExample example = new GiveLikeExample();
        example.createCriteria().andTypeEqualTo(Quantity.INT_2).andReplyIdEqualTo(replyId);
        return giveLikeMapper.countByExample(example);
    }
}