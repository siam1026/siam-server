package com.siam.system.modular.package_goods.service_impl.internal;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.system.modular.package_goods.entity.internal.PointsMallGoodsSpecification;
import com.siam.system.modular.package_goods.entity.internal.PointsMallGoodsSpecificationOption;
import com.siam.system.modular.package_goods.mapper.internal.PointsMallGoodsSpecificationMapper;
import com.siam.system.modular.package_goods.mapper.internal.PointsMallGoodsSpecificationOptionMapper;
import com.siam.system.modular.package_goods.model.dto.internal.PointsMallGoodsSpecificationDto;
import com.siam.system.modular.package_goods.model.dto.internal.PointsMallGoodsSpecificationOptionDto;
import com.siam.system.modular.package_goods.model.example.internal.PointsMallGoodsSpecificationExample;
import com.siam.system.modular.package_goods.service.internal.PointsMallGoodsSpecificationService;
import com.siam.system.modular.package_goods.mapper.internal.PointsMallGoodsSpecificationMapper;
import com.siam.system.modular.package_goods.mapper.internal.PointsMallGoodsSpecificationOptionMapper;
import com.siam.system.modular.package_goods.service.internal.PointsMallGoodsSpecificationService;
import com.siam.package_common.exception.StoneCustomerException;
import com.siam.system.modular.package_goods.model.dto.internal.PointsMallGoodsSpecificationDto;
import com.siam.system.modular.package_goods.model.dto.internal.PointsMallGoodsSpecificationOptionDto;
import com.siam.system.modular.package_goods.entity.internal.PointsMallGoodsSpecification;
import com.siam.system.modular.package_goods.entity.internal.PointsMallGoodsSpecificationOption;
import com.siam.system.modular.package_goods.model.example.internal.PointsMallGoodsSpecificationExample;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class PointsMallGoodsSpecificationServiceImpl implements PointsMallGoodsSpecificationService {

    @Autowired
    private PointsMallGoodsSpecificationMapper goodsSpecificationMapper;

    @Autowired
    private PointsMallGoodsSpecificationOptionMapper goodsSpecificationOptionMapper;


    public int countByExample(PointsMallGoodsSpecificationExample example){
        return goodsSpecificationMapper.countByExample(example);
    }

    public void deleteByPrimaryKey(Integer id){
        goodsSpecificationMapper.deleteByPrimaryKey(id);
    }

    public void insertSelective(PointsMallGoodsSpecification record){
        goodsSpecificationMapper.insertSelective(record);
    }

    public List<PointsMallGoodsSpecification> selectByExample(PointsMallGoodsSpecificationExample example){
        return goodsSpecificationMapper.selectByExample(example);
    }

    public PointsMallGoodsSpecification selectByPrimaryKey(Integer id){
        return goodsSpecificationMapper.selectByPrimaryKey(id);
    }

    public void updateByExampleSelective(PointsMallGoodsSpecification record, PointsMallGoodsSpecificationExample example){
        goodsSpecificationMapper.updateByExampleSelective(record, example);
    }

    public void updateByPrimaryKeySelective(PointsMallGoodsSpecification record){
        goodsSpecificationMapper.updateByPrimaryKeySelective(record);
    }

    public Page<PointsMallGoodsSpecification> getListByPage(int pageNo, int pageSize, PointsMallGoodsSpecification goodsSpecification) {
        Page<PointsMallGoodsSpecification> page = goodsSpecificationMapper.getListByPage(new Page(pageNo, pageSize), goodsSpecification);
        return page;
    }

    @Override
    public Page<Map<String, Object>> getListByPageJoinPointsMallGoods(int pageNo, int pageSize, PointsMallGoodsSpecificationDto goodsSpecificationDto) {
        Page<Map<String, Object>> page = goodsSpecificationMapper.getListByPageJoinPointsMallGoods(goodsSpecificationDto);
        return page;
    }

    @Override
    public int selectMaxSortNumberByPointsMallGoodsId(Integer goodsId) {
        return goodsSpecificationMapper.selectMaxSortNumberByPointsMallGoodsId(goodsId);
    }

    @Override
    public PointsMallGoodsSpecification selectByPointsMallGoodsIdAndName(Integer goodsId, String name) {
        return goodsSpecificationMapper.selectByPointsMallGoodsIdAndName(goodsId, name);
    }

    @Override
    public void insertPublicGoodsSpecification(int goodsId) {
        //如果商品已经有规格，则不能生成，避免重复插入
        PointsMallGoodsSpecificationExample goodsSpecificationExample = new PointsMallGoodsSpecificationExample();
        goodsSpecificationExample.createCriteria().andPointsMallGoodsIdEqualTo(goodsId);
        int result = goodsSpecificationMapper.countByExample(goodsSpecificationExample);
        if(result > 0){
            throw new StoneCustomerException("商品已存在其他规格，生成失败");
        }

        //获取商品公共规格，插入数据
        List<PointsMallGoodsSpecificationOptionDto> list = this.getPublicPointsMallGoodsSpecification();
        list.forEach(goodsSpecificationOptionDto -> {
            goodsSpecificationOptionDto.setGoodsId(goodsId);

            /*//判断商品规格选项是否重复  只要名字相同，不管父类规格是什么，都视为重复
            PointsMallGoodsSpecificationOptionExample example = new PointsMallGoodsSpecificationOptionExample();
            example.createCriteria().andPointsMallGoodsIdEqualTo(goodsId).andNameEqualTo(goodsSpecificationOptionDto.getName());
            int result_1 = goodsSpecificationOptionMapper.countByExample(example);
            if(result_1 > 0){
                throw new StoneCustomerException("奶茶口味名称重复，添加失败");
            }*/

            //判断规格名称是否存在
            PointsMallGoodsSpecification dbSpecification = goodsSpecificationMapper.selectByPointsMallGoodsIdAndName(goodsSpecificationOptionDto.getGoodsId(), goodsSpecificationOptionDto.getSpecificationName());
            if(dbSpecification == null){
                //查询商品规格的最大排序号
                int maxSortNumber = goodsSpecificationMapper.selectMaxSortNumberByPointsMallGoodsId(goodsSpecificationOptionDto.getGoodsId());
                //先添加商品规格
                PointsMallGoodsSpecification insertSpecification = new PointsMallGoodsSpecification();
                insertSpecification.setGoodsId(goodsSpecificationOptionDto.getGoodsId());
                insertSpecification.setName(goodsSpecificationOptionDto.getSpecificationName());
                insertSpecification.setSortNumber(maxSortNumber + 1);
                insertSpecification.setCreateTime(new Date());
                insertSpecification.setUpdateTime(new Date());
                goodsSpecificationMapper.insertSelective(insertSpecification);
                dbSpecification = insertSpecification;
            }

            //查询商品规格选项的最大排序号
            int maxSortNumber = goodsSpecificationOptionMapper.selectMaxSortNumberByPointsMallGoodsSpecificationId(dbSpecification.getId());
            //再添加商品规格选项
            PointsMallGoodsSpecificationOption insertPointsMallGoodsSpecificationOption = new PointsMallGoodsSpecificationOption();
            BeanUtils.copyProperties(goodsSpecificationOptionDto, insertPointsMallGoodsSpecificationOption);
            insertPointsMallGoodsSpecificationOption.setGoodsSpecificationId(dbSpecification.getId());
            insertPointsMallGoodsSpecificationOption.setSortNumber(maxSortNumber + 1);
            insertPointsMallGoodsSpecificationOption.setCreateTime(new Date());
            insertPointsMallGoodsSpecificationOption.setUpdateTime(new Date());
            goodsSpecificationOptionMapper.insertSelective(insertPointsMallGoodsSpecificationOption);
        });
    }

    /**
     * 获取商品公共规格
     * @return
     */
    public List<PointsMallGoodsSpecificationOptionDto> getPublicPointsMallGoodsSpecification(){
        List<PointsMallGoodsSpecificationOptionDto> list = new ArrayList<>();

        PointsMallGoodsSpecificationOptionDto dto_1 = new PointsMallGoodsSpecificationOptionDto("加料", "常规", BigDecimal.ZERO);

        PointsMallGoodsSpecificationOptionDto dto_2 = new PointsMallGoodsSpecificationOptionDto("温度", "热", BigDecimal.ZERO);
        PointsMallGoodsSpecificationOptionDto dto_3 = new PointsMallGoodsSpecificationOptionDto("温度", "温", BigDecimal.ZERO);
        PointsMallGoodsSpecificationOptionDto dto_4 = new PointsMallGoodsSpecificationOptionDto("温度", "常规冰", BigDecimal.ZERO);
        PointsMallGoodsSpecificationOptionDto dto_5 = new PointsMallGoodsSpecificationOptionDto("温度", "多冰", BigDecimal.ZERO);
        PointsMallGoodsSpecificationOptionDto dto_6 = new PointsMallGoodsSpecificationOptionDto("温度", "少冰", BigDecimal.ZERO);
        PointsMallGoodsSpecificationOptionDto dto_7 = new PointsMallGoodsSpecificationOptionDto("温度", "去冰", BigDecimal.ZERO);

        PointsMallGoodsSpecificationOptionDto dto_8 = new PointsMallGoodsSpecificationOptionDto("糖度", "常规糖", BigDecimal.ZERO);
        PointsMallGoodsSpecificationOptionDto dto_9 = new PointsMallGoodsSpecificationOptionDto("糖度", "半糖", BigDecimal.ZERO);
        PointsMallGoodsSpecificationOptionDto dto_10 = new PointsMallGoodsSpecificationOptionDto("糖度", "微糖", BigDecimal.ZERO);
        PointsMallGoodsSpecificationOptionDto dto_11 = new PointsMallGoodsSpecificationOptionDto("糖度", "不另外加糖", BigDecimal.ZERO);

        /*PointsMallGoodsSpecificationOptionDto dto_12 = new PointsMallGoodsSpecificationOptionDto("规格", "小杯", BigDecimal.ZERO);*/
        PointsMallGoodsSpecificationOptionDto dto_13 = new PointsMallGoodsSpecificationOptionDto("规格", "中杯", new BigDecimal(0));
        PointsMallGoodsSpecificationOptionDto dto_14 = new PointsMallGoodsSpecificationOptionDto("规格", "大杯", new BigDecimal(2));

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
    public void deleteByPointsMallGoodsId(int goodsId) {
        goodsSpecificationMapper.deleteByPointsMallGoodsId(goodsId);
    }
}