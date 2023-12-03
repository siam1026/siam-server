package com.siam.system.modular.package_goods.service_impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.package_common.util.BeanUtils;
import com.siam.system.modular.package_goods.entity.Goods;
import com.siam.system.modular.package_goods.entity.GoodsRawmaterialRelation;
import com.siam.system.modular.package_goods.mapper.GoodsMapper;
import com.siam.system.modular.package_goods.mapper.GoodsRawmaterialRelationMapper;
import com.siam.system.modular.package_goods.mapper.RawmaterialMapper;
import com.siam.system.modular.package_goods.model.example.GoodsRawmaterialRelationExample;
import com.siam.system.modular.package_goods.service.GoodsRawmaterialRelationService;
import com.siam.system.modular.package_goods.entity.Goods;
import com.siam.system.modular.package_goods.entity.GoodsRawmaterialRelation;
import com.siam.system.modular.package_goods.mapper.GoodsMapper;
import com.siam.system.modular.package_goods.mapper.GoodsRawmaterialRelationMapper;
import com.siam.system.modular.package_goods.mapper.RawmaterialMapper;
import com.siam.system.modular.package_goods.model.example.GoodsRawmaterialRelationExample;
import com.siam.system.modular.package_goods.service.GoodsRawmaterialRelationService;
import com.siam.system.modular.package_order.entity.OrderDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class GoodsRawmaterialRelationServiceImpl implements GoodsRawmaterialRelationService {

    @Autowired
    private GoodsRawmaterialRelationMapper goodsRawmaterialRelationMapper;

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private RawmaterialMapper rawmaterialMapper;

    @Override
    public int countByExample(GoodsRawmaterialRelationExample example) {
        return goodsRawmaterialRelationMapper.countByExample(example);
    }

    @Override
    public void deleteByPrimaryKey(Integer id) {
        goodsRawmaterialRelationMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void insertSelective(GoodsRawmaterialRelation goodsRawmaterialRelation) {
        goodsRawmaterialRelationMapper.insertSelective(goodsRawmaterialRelation);
    }

    @Override
    public GoodsRawmaterialRelation selectByPrimaryKey(Integer id) {
        return goodsRawmaterialRelationMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<GoodsRawmaterialRelation> selectByExample(GoodsRawmaterialRelationExample example){
        return goodsRawmaterialRelationMapper.selectByExample(example);
    }

    @Override
    public void updateRawmaterialConsumedQuantityByOrderDetailList(List<OrderDetail> orderDetailList) {
        for (OrderDetail orderDetail : orderDetailList) {
            List<Map<String, Object>> rawmaterialRelationList = goodsRawmaterialRelationMapper.selectByGoodsId(new Page(1, 1000), orderDetail.getGoodsId()).getRecords();
            if (rawmaterialRelationList != null) {
                for (Map<String, Object> map : rawmaterialRelationList) {
                    BigDecimal consumeAmount = ((BigDecimal) map.get("consumedQuantity")).multiply(BigDecimal.valueOf(orderDetail.getNumber()));
                    rawmaterialMapper.consumeRawmaterial(consumeAmount, (Integer) map.get("rawmaterialId"));
                }
            }
        }
    }

    @Override
    public void updateByPrimaryKeySelective(GoodsRawmaterialRelation goodsRawmaterialRelation) {
        goodsRawmaterialRelationMapper.updateByPrimaryKeySelective(goodsRawmaterialRelation);
    }

    @Override
    public Page getListByPage(int pageNo, int pageSize, GoodsRawmaterialRelation goodsRawmaterialRelation) {
        Page<Map<String, Object>> page = goodsRawmaterialRelationMapper.getListByPage(new Page(pageNo, pageSize), goodsRawmaterialRelation);
        return page;
    }

    @Override
    public void deleteByGoodsId(int goodsId) {
        goodsRawmaterialRelationMapper.deleteByGoodsId(goodsId);
    }

    @Override
    public Page findGoodsRawmaterialRelation(int pageNo, int pageSize, String goodsName) {
        //分页查询商品数据
        Goods queryGoods = new Goods();
        queryGoods.setName(goodsName);
        Page<Goods> page = goodsMapper.getListByPage(new Page(pageNo, pageSize), queryGoods);

        //将List<Goods>转化成List<Map<String, Object>>
        List<Map<String, Object>> goodsMapList = page.getRecords().stream().map(BeanUtils::beanToMap).collect(Collectors.toList());

        //循环商品集合，查询其关联的原料配比数据-全查不分页
        for (int i = 0; i < goodsMapList.size(); i++) {
            Map<String, Object> goodsMap = goodsMapList.get(i);
            List<Map<String, Object>> goodsRawmaterialRelations = goodsRawmaterialRelationMapper.selectByGoodsId(new Page(pageNo, pageSize), (int) goodsMap.get("id")).getRecords();
            //拼接原料名称
            String str = "";
            for (Map<String, Object> map : goodsRawmaterialRelations) {
                str += map.get("rawmaterialName") + ", ";
            }
            str = (str != "") ? str.substring(0, str.length() - 2) : str;
            goodsMap.put("rawmaterialName", str);
            //修改商品id、商品名称的键名称
            goodsMap.put("goodsId", goodsMap.get("id"));
            goodsMap.put("goodsName", goodsMap.get("name"));
        }
        return page;
    }

    @Override
    public Page selectByGoodsId(int pageNo, int pageSize, int goodsId) {
        Page<Map<String, Object>> page = goodsRawmaterialRelationMapper.selectByGoodsId(new Page(pageNo, pageSize), goodsId);
        return page;
    }
}
