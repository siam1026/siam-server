package com.siam.system.modular.package_goods.service_impl.internal;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.system.modular.package_goods.entity.internal.MemberGoodsCollect;
import com.siam.system.modular.package_goods.mapper.internal.MemberGoodsCollectMapper;
import com.siam.system.modular.package_goods.model.dto.internal.MemberGoodsCollectDto;
import com.siam.system.modular.package_goods.model.example.internal.MemberGoodsCollectExample;
import com.siam.system.modular.package_goods.service.internal.MemberGoodsCollectService;
import com.siam.system.modular.package_goods.entity.internal.MemberGoodsCollect;
import com.siam.system.modular.package_goods.mapper.internal.MemberGoodsCollectMapper;
import com.siam.system.modular.package_goods.model.example.internal.MemberGoodsCollectExample;
import com.siam.system.modular.package_goods.service.internal.MemberGoodsCollectService;
import com.siam.system.modular.package_goods.model.dto.internal.MemberGoodsCollectDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class MemberGoodsCollectServiceImpl implements MemberGoodsCollectService {
    @Autowired
    private MemberGoodsCollectMapper memberGoodsCollectMapper;

    public int countByExample(MemberGoodsCollectExample example){
        return memberGoodsCollectMapper.countByExample(example);
    }

    public void deleteByPrimaryKey(Integer id){
        memberGoodsCollectMapper.deleteByPrimaryKey(id);
    }

    public void insertSelective(MemberGoodsCollect record){
        memberGoodsCollectMapper.insertSelective(record);
    }

    public List<MemberGoodsCollect> selectByExample(MemberGoodsCollectExample example){
        return memberGoodsCollectMapper.selectByExample(example);
    }

    public MemberGoodsCollect selectByPrimaryKey(Integer id){
        return memberGoodsCollectMapper.selectByPrimaryKey(id);
    }

    public void updateByExampleSelective(MemberGoodsCollect record, MemberGoodsCollectExample example){
        memberGoodsCollectMapper.updateByExampleSelective(record, example);
    }

    public void updateByPrimaryKeySelective(MemberGoodsCollect record){
        memberGoodsCollectMapper.updateByPrimaryKeySelective(record);
    }

    public Page getListByPage(int pageNo, int pageSize, MemberGoodsCollect MemberGoodsCollect) {
        Page<Map<String, Object>> page = memberGoodsCollectMapper.getListByPage(new Page(pageNo, pageSize), MemberGoodsCollect);
        return page;
   }

    public Page<Map<String, Object>> getListByPageJoinGoods(int pageNo, int pageSize, MemberGoodsCollectDto memberGoodsCollectDto) {
        Page<Map<String, Object>> page = memberGoodsCollectMapper.getListByPageJoinGoods(new Page(pageNo, pageSize), memberGoodsCollectDto);
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
    public void updateIsGoodsExistsTo0ByGoodsId(int goodsId) {
        memberGoodsCollectMapper.updateIsGoodsExistsTo0ByGoodsId(goodsId);
    }

    @Override
    public int selectCountGoodsNumber(Integer shopId, Date startTime, Date endTime) {
        return memberGoodsCollectMapper.selectCountGoodsNumber(shopId, startTime, endTime);
    }

    @Override
    public void updateIsBuy() {
        memberGoodsCollectMapper.updateIsBuy();
    }
}