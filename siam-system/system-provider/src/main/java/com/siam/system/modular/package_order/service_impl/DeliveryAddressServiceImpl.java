package com.siam.system.modular.package_order.service_impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.system.modular.package_order.entity.DeliveryAddress;
import com.siam.system.modular.package_order.mapper.DeliveryAddressMapper;
import com.siam.system.modular.package_order.model.example.DeliveryAddressExample;
import com.siam.system.modular.package_order.service.DeliveryAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DeliveryAddressServiceImpl implements DeliveryAddressService {
    @Autowired
    private DeliveryAddressMapper deliveryAddressMapper;

    public int countByExample(DeliveryAddressExample example){
        return deliveryAddressMapper.countByExample(example);
    }

    public void deleteByPrimaryKey(Integer id){
        deliveryAddressMapper.deleteByPrimaryKey(id);
    }

    public void insertSelective(DeliveryAddress record){
        deliveryAddressMapper.insertSelective(record);
    }

    public List<DeliveryAddress> selectByExample(DeliveryAddressExample example){
        return deliveryAddressMapper.selectByExample(example);
    }

    public DeliveryAddress selectByPrimaryKey(Integer id){
        return deliveryAddressMapper.selectByPrimaryKey(id);
    }

    public void updateByExampleSelective(DeliveryAddress record, DeliveryAddressExample example){
        deliveryAddressMapper.updateByExampleSelective(record, example);
    }

    public void updateByPrimaryKeySelective(DeliveryAddress record){
        deliveryAddressMapper.updateByPrimaryKeySelective(record);
    }

    public Page getListByPage(int pageNo, int pageSize, DeliveryAddress deliveryAddress) {
        Page<Map<String, Object>> page = deliveryAddressMapper.getListByPage(new Page(pageNo, pageSize), deliveryAddress);
        return page;
    }

    @Override
    public void updateIsDefaultExclusion(Integer id, Integer memberId) {
        if(id == null){
            throw new RuntimeException("id不能为空");
        }
        if(memberId == null){
            throw new RuntimeException("memberId不能为空");
        }
        deliveryAddressMapper.updateIsDefaultExclusion(id, memberId);
    }
}
