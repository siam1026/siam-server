package com.siam.system.modular.package_goods.service_impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.package_common.exception.StoneCustomerException;
import com.siam.system.modular.package_goods.entity.GoodsSpecification;
import com.siam.system.modular.package_goods.entity.GoodsSpecificationOption;
import com.siam.system.modular.package_goods.mapper.GoodsSpecificationMapper;
import com.siam.system.modular.package_goods.mapper.GoodsSpecificationOptionMapper;
import com.siam.system.modular.package_goods.model.dto.GoodsSpecificationDto;
import com.siam.system.modular.package_goods.model.dto.GoodsSpecificationOptionDto;
import com.siam.system.modular.package_goods.model.example.GoodsSpecificationExample;
import com.siam.system.modular.package_goods.service.GoodsSpecificationService;
import com.siam.system.modular.package_goods.entity.GoodsSpecification;
import com.siam.system.modular.package_goods.model.example.GoodsSpecificationExample;
import com.siam.system.modular.package_goods.model.dto.GoodsSpecificationDto;
import com.siam.system.modular.package_goods.mapper.GoodsSpecificationMapper;
import com.siam.system.modular.package_goods.service.GoodsSpecificationService;
import com.siam.system.modular.package_goods.entity.GoodsSpecificationOption;
import com.siam.system.modular.package_goods.model.dto.GoodsSpecificationOptionDto;
import com.siam.system.modular.package_goods.mapper.GoodsSpecificationOptionMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class GoodsSpecificationServiceImpl implements GoodsSpecificationService {

    @Autowired
    private GoodsSpecificationMapper goodsSpecificationMapper;

    @Autowired
    private GoodsSpecificationOptionMapper goodsSpecificationOptionMapper;


    public int countByExample(GoodsSpecificationExample example){
        return goodsSpecificationMapper.countByExample(example);
    }

    public void deleteByPrimaryKey(Integer id){
        goodsSpecificationMapper.deleteByPrimaryKey(id);
    }

    public void insertSelective(GoodsSpecification record){
        goodsSpecificationMapper.insertSelective(record);
    }

    public List<GoodsSpecification> selectByExample(GoodsSpecificationExample example){
        return goodsSpecificationMapper.selectByExample(example);
    }

    public GoodsSpecification selectByPrimaryKey(Integer id){
        return goodsSpecificationMapper.selectByPrimaryKey(id);
    }

    public void updateByExampleSelective(GoodsSpecification record, GoodsSpecificationExample example){
        goodsSpecificationMapper.updateByExampleSelective(record, example);
    }

    public void updateByPrimaryKeySelective(GoodsSpecification record){
        goodsSpecificationMapper.updateByPrimaryKeySelective(record);
    }

    public Page<GoodsSpecification> getListByPage(int pageNo, int pageSize, GoodsSpecification goodsSpecification) {
        Page<GoodsSpecification> page = goodsSpecificationMapper.getListByPage(new Page(pageNo, pageSize), goodsSpecification);
        return page;
    }

    @Override
    public Page<Map<String, Object>> getListByPageJoinGoods(int pageNo, int pageSize, GoodsSpecificationDto goodsSpecificationDto) {
        Page<Map<String, Object>> page = goodsSpecificationMapper.getListByPageJoinGoods(goodsSpecificationDto);
        return page;
    }

    @Override
    public int selectMaxSortNumberByGoodsId(Integer goodsId) {
        return goodsSpecificationMapper.selectMaxSortNumberByGoodsId(goodsId);
    }

    @Override
    public GoodsSpecification selectByGoodsIdAndName(Integer goodsId, String name) {
        return goodsSpecificationMapper.selectByGoodsIdAndName(goodsId, name);
    }

    @Override
    public void insertPublicGoodsSpecification(int goodsId) {
        //如果商品已经有规格，则不能生成，避免重复插入
        GoodsSpecificationExample goodsSpecificationExample = new GoodsSpecificationExample();
        goodsSpecificationExample.createCriteria().andGoodsIdEqualTo(goodsId);
        int result = goodsSpecificationMapper.countByExample(goodsSpecificationExample);
        if(result > 0){
            throw new StoneCustomerException("商品已存在其他规格，生成失败");
        }

        //获取商品公共规格，插入数据
        List<GoodsSpecificationOptionDto> list = this.getPublicGoodsSpecification();
        list.forEach(goodsSpecificationOptionDto -> {
            goodsSpecificationOptionDto.setGoodsId(goodsId);

            /*//判断商品规格选项是否重复  只要名字相同，不管父类规格是什么，都视为重复
            GoodsSpecificationOptionExample example = new GoodsSpecificationOptionExample();
            example.createCriteria().andGoodsIdEqualTo(goodsId).andNameEqualTo(goodsSpecificationOptionDto.getName());
            int result_1 = goodsSpecificationOptionMapper.countByExample(example);
            if(result_1 > 0){
                throw new StoneCustomerException("奶茶口味名称重复，添加失败");
            }*/

            //判断规格名称是否存在
            GoodsSpecification dbSpecification = goodsSpecificationMapper.selectByGoodsIdAndName(goodsSpecificationOptionDto.getGoodsId(), goodsSpecificationOptionDto.getSpecificationName());
            if(dbSpecification == null){
                //查询商品规格的最大排序号
                int maxSortNumber = goodsSpecificationMapper.selectMaxSortNumberByGoodsId(goodsSpecificationOptionDto.getGoodsId());
                //先添加商品规格
                GoodsSpecification insertSpecification = new GoodsSpecification();
                insertSpecification.setGoodsId(goodsSpecificationOptionDto.getGoodsId());
                insertSpecification.setName(goodsSpecificationOptionDto.getSpecificationName());
                insertSpecification.setSortNumber(maxSortNumber + 1);
                insertSpecification.setCreateTime(new Date());
                insertSpecification.setUpdateTime(new Date());
                goodsSpecificationMapper.insertSelective(insertSpecification);
                dbSpecification = insertSpecification;
            }

            //查询商品规格选项的最大排序号
            int maxSortNumber = goodsSpecificationOptionMapper.selectMaxSortNumberByGoodsSpecificationId(dbSpecification.getId());
            //再添加商品规格选项
            GoodsSpecificationOption insertGoodsSpecificationOption = new GoodsSpecificationOption();
            BeanUtils.copyProperties(goodsSpecificationOptionDto, insertGoodsSpecificationOption);
            insertGoodsSpecificationOption.setGoodsSpecificationId(dbSpecification.getId());
            insertGoodsSpecificationOption.setSortNumber(maxSortNumber + 1);
            insertGoodsSpecificationOption.setCreateTime(new Date());
            insertGoodsSpecificationOption.setUpdateTime(new Date());
            goodsSpecificationOptionMapper.insertSelective(insertGoodsSpecificationOption);
        });
    }

    /**
     * 获取商品公共规格
     * @return
     */
    public List<GoodsSpecificationOptionDto> getPublicGoodsSpecification(){
        List<GoodsSpecificationOptionDto> list = new ArrayList<>();

        GoodsSpecificationOptionDto dto_1 = new GoodsSpecificationOptionDto("加料", "常规", BigDecimal.ZERO);

        GoodsSpecificationOptionDto dto_2 = new GoodsSpecificationOptionDto("温度", "热", BigDecimal.ZERO);
        GoodsSpecificationOptionDto dto_3 = new GoodsSpecificationOptionDto("温度", "温", BigDecimal.ZERO);
        GoodsSpecificationOptionDto dto_4 = new GoodsSpecificationOptionDto("温度", "常规冰", BigDecimal.ZERO);
        GoodsSpecificationOptionDto dto_5 = new GoodsSpecificationOptionDto("温度", "多冰", BigDecimal.ZERO);
        GoodsSpecificationOptionDto dto_6 = new GoodsSpecificationOptionDto("温度", "少冰", BigDecimal.ZERO);
        GoodsSpecificationOptionDto dto_7 = new GoodsSpecificationOptionDto("温度", "去冰", BigDecimal.ZERO);

        GoodsSpecificationOptionDto dto_8 = new GoodsSpecificationOptionDto("糖度", "常规糖", BigDecimal.ZERO);
        GoodsSpecificationOptionDto dto_9 = new GoodsSpecificationOptionDto("糖度", "半糖", BigDecimal.ZERO);
        GoodsSpecificationOptionDto dto_10 = new GoodsSpecificationOptionDto("糖度", "微糖", BigDecimal.ZERO);
        GoodsSpecificationOptionDto dto_11 = new GoodsSpecificationOptionDto("糖度", "不另外加糖", BigDecimal.ZERO);

        /*GoodsSpecificationOptionDto dto_12 = new GoodsSpecificationOptionDto("规格", "小杯", BigDecimal.ZERO);*/
        GoodsSpecificationOptionDto dto_13 = new GoodsSpecificationOptionDto("规格", "中杯", new BigDecimal(0));
        GoodsSpecificationOptionDto dto_14 = new GoodsSpecificationOptionDto("规格", "大杯", new BigDecimal(2));

        list.add(dto_1);
        list.add(dto_2);
        list.add(dto_3);
        list.add(dto_4);
        list.add(dto_5);
        list.add(dto_6);
        list.add(dto_7);
        list.add(dto_8);
        list.add(dto_9);
        list.add(dto_10);
        list.add(dto_11);
        /*list.add(dto_12);*/
        list.add(dto_13);
        list.add(dto_14);

        return list;
    }

    @Override
    public void deleteByGoodsId(int goodsId) {
        goodsSpecificationMapper.deleteByGoodsId(goodsId);
    }
}