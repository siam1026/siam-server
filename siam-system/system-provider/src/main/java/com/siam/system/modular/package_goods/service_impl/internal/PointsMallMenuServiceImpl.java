package com.siam.system.modular.package_goods.service_impl.internal;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.system.modular.package_goods.entity.internal.PointsMallMenu;
import com.siam.system.modular.package_goods.mapper.internal.PointsMallMenuMapper;
import com.siam.system.modular.package_goods.service.internal.PointsMallMenuService;
import com.siam.system.modular.package_goods.mapper.internal.PointsMallMenuMapper;
import com.siam.system.modular.package_goods.service.internal.PointsMallMenuService;
import com.siam.system.modular.package_goods.entity.internal.PointsMallMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class PointsMallMenuServiceImpl implements PointsMallMenuService {

    @Autowired
    private PointsMallMenuMapper menuMapper;

    @Override
    public void deleteByPrimaryKey(Integer id) {
        menuMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void insertSelective(PointsMallMenu menu) {
        menuMapper.insertSelective(menu);
    }

    @Override
    public PointsMallMenu selectByPrimaryKey(Integer id) {
        return menuMapper.selectByPrimaryKey(id);
    }

    @Override
    public void updateByPrimaryKeySelective(PointsMallMenu menu) {
        menuMapper.updateByPrimaryKeySelective(menu);
    }

    @Override
    public Page getList(int pageNo, int pageSize, PointsMallMenu menu) {
        Page<Map<String, Object>> page = menuMapper.getListByPage(new Page(pageNo, pageSize), menu);
        return page;
    }

    @Override
    public List<Map<String, Object>> getListByPageJoinPointsMallGoods(PointsMallMenu menu) {
        List<Map<String, Object>> list = menuMapper.getListByPageJoinPointsMallGoods(menu);
        return list;
    }
}