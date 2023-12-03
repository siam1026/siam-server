package com.siam.system.modular.package_goods.service_impl.internal;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.system.modular.package_goods.entity.internal.Courier;
import com.siam.system.modular.package_goods.mapper.internal.CourierMapper;
import com.siam.system.modular.package_goods.service.internal.CourierService;
import com.siam.system.modular.package_goods.mapper.internal.CourierMapper;
import com.siam.system.modular.package_goods.service.internal.CourierService;
import com.siam.system.modular.package_goods.entity.internal.Courier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CourierServiceImpl implements CourierService {

    @Autowired
    private CourierMapper courierMapper;

    @Override
    public void deleteByPrimaryKey(Integer id) {
        courierMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void insertSelective(Courier courier) {
        courierMapper.insertSelective(courier);
    }

    @Override
    public Courier selectByPrimaryKey(Integer id) {
        return courierMapper.selectByPrimaryKey(id);
    }

    @Override
    public void updateByPrimaryKeySelective(Courier courier) {
        courierMapper.updateByPrimaryKeySelective(courier);
    }

    @Override
    public Page getList(int pageNo, int pageSize, Courier courier) {
        Page<Map<String, Object>> page = courierMapper.getListByPage(new Page(pageNo, pageSize), courier);
        return page;
    }

    @Override
    public Page<Map<String, Object>> getListJoinShop(int pageNo, int pageSize, Courier courier) {
        Page<Map<String, Object>> page = courierMapper.getListJoinShop(new Page(pageNo, pageSize), courier);
        return page;
    }
}