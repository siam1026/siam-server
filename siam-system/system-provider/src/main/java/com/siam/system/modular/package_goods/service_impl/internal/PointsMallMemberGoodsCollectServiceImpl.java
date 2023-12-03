package com.siam.system.modular.package_goods.service_impl.internal;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.system.modular.package_goods.entity.internal.PointsMallMemberGoodsCollect;
import com.siam.system.modular.package_goods.mapper.internal.PointsMallMemberGoodsCollectMapper;
import com.siam.system.modular.package_goods.model.dto.internal.PointsMallMemberGoodsCollectDto;
import com.siam.system.modular.package_goods.model.example.internal.PointsMallMemberGoodsCollectExample;
import com.siam.system.modular.package_goods.service.internal.PointsMallMemberGoodsCollectService;
import com.siam.system.modular.package_goods.mapper.internal.PointsMallMemberGoodsCollectMapper;
import com.siam.system.modular.package_goods.service.internal.PointsMallMemberGoodsCollectService;
import com.siam.system.modular.package_goods.model.dto.internal.PointsMallMemberGoodsCollectDto;
import com.siam.system.modular.package_goods.entity.internal.PointsMallMemberGoodsCollect;
import com.siam.system.modular.package_goods.model.example.internal.PointsMallMemberGoodsCollectExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class PointsMallMemberGoodsCollectServiceImpl implements PointsMallMemberGoodsCollectService {
    @Autowired
    private PointsMallMemberGoodsCollectMapper memberGoodsCollectMapper;

    public int countByExample(PointsMallMemberGoodsCollectExample example){
        return memberGoodsCollectMapper.countByExample(example);
    }

    public void deleteByPrimaryKey(Integer id){
        memberGoodsCollectMapper.deleteByPrimaryKey(id);
    }

    public void insertSelective(PointsMallMemberGoodsCollect record){
        memberGoodsCollectMapper.insertSelective(record);
    }

    public List<PointsMallMemberGoodsCollect> selectByExample(PointsMallMemberGoodsCollectExample example){
        return memberGoodsCollectMapper.selectByExample(example);
    }

    public PointsMallMemberGoodsCollect selectByPrimaryKey(Integer id){
        return memberGoodsCollectMapper.selectByPrimaryKey(id);
    }

    public void updateByExampleSelective(PointsMallMemberGoodsCollect record, PointsMallMemberGoodsCollectExample example){
        memberGoodsCollectMapper.updateByExampleSelective(record, example);
    }

    public void updateByPrimaryKeySelective(PointsMallMemberGoodsCollect record){
        memberGoodsCollectMapper.updateByPrimaryKeySelective(record);
    }

    public Page getListByPage(int pageNo, int pageSize, PointsMallMemberGoodsCollect PointsMallMemberGoodsCollect) {
        Page<Map<String, Object>> page = memberGoodsCollectMapper.getListByPage(new Page(pageNo, pageSize), PointsMallMemberGoodsCollect);
        return page;
    }

    public Page<Map<String, Object>> getListByPageJoinPointsMallGoods(int pageNo, int pageSize, PointsMallMemberGoodsCollectDto pointsMallMemberGoodsCollectDto) {
        Page<Map<String, Object>> page = memberGoodsCollectMapper.getListByPageJoinPointsMallGoods(new Page(pageNo, pageSize), pointsMallMemberGoodsCollectDto);
        return page;
    }

    @Override
    public int countByIdListAndMemberId(List<Integer> idList, Integer memberId) {
        return memberGoodsCollectMapper.countByIdListAndMemberId(idList, memberId);
    }

    @Override
    public int batchDeleteByIdList(List<Integer> idList) {
        return memberGoodsCollectMapper.batchDeleteByIdList(idList);
    }

    @Override
    public void updateIsGoodsExistsTo0ByPointsMallGoodsId(int goodsId) {
        memberGoodsCollectMapper.updateIsGoodsExistsTo0ByPointsMallGoodsId(goodsId);
    }

    @Override
    public int selectCountPointsMallGoodsNumber(Integer shopId, Date startTime, Date endTime) {
        return memberGoodsCollectMapper.selectCountPointsMallGoodsNumber(shopId, startTime, endTime);
    }

    @Override
    public void updateIsBuy() {
        memberGoodsCollectMapper.updateIsBuy();
    }
}