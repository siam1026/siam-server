package com.siam.system.modular.package_goods.service_impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.system.modular.package_goods.entity.Rawmaterial;
import com.siam.system.modular.package_goods.mapper.RawmaterialMapper;
import com.siam.system.modular.package_goods.service.RawmaterialService;
import com.siam.system.modular.package_goods.entity.Rawmaterial;
import com.siam.system.modular.package_goods.mapper.RawmaterialMapper;
import com.siam.system.modular.package_goods.service.RawmaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class RawmaterialServiceImpl implements RawmaterialService {

    @Autowired
    private RawmaterialMapper rawmaterialMapper;

    @Override
    public void deleteByPrimaryKey(Integer id) {
        rawmaterialMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void insertSelective(Rawmaterial rawmaterial) {
        rawmaterialMapper.insertSelective(rawmaterial);
    }

    @Override
    public Rawmaterial selectByPrimaryKey(Integer id) {
        return rawmaterialMapper.selectByPrimaryKey(id);
    }

    @Override
    public void updateByPrimaryKeySelective(Rawmaterial rawmaterial) {
        rawmaterialMapper.updateByPrimaryKeySelective(rawmaterial);
    }

    @Override
    public Page getListByPage(int pageNo, int pageSize, Rawmaterial rawmaterial) {
        Page<Map<String, Object>> page = rawmaterialMapper.getListByPage(new Page(pageNo, pageSize), rawmaterial);
        return page;
    }
}