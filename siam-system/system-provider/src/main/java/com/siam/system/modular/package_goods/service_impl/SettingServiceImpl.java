package com.siam.system.modular.package_goods.service_impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.system.modular.package_goods.entity.Setting;
import com.siam.system.modular.package_goods.mapper.SettingMapper;
import com.siam.system.modular.package_goods.service.SettingService;
import com.siam.system.modular.package_goods.entity.Setting;
import com.siam.system.modular.package_goods.mapper.SettingMapper;
import com.siam.system.modular.package_goods.service.SettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class SettingServiceImpl implements SettingService {

    @Autowired
    private SettingMapper settingMapper;

    @Override
    public void deleteByPrimaryKey(Integer id) {
        settingMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void insertSelective(Setting setting) {
        settingMapper.insertSelective(setting);
    }

    @Override
    public Setting selectByPrimaryKey(Integer id) {
        return settingMapper.selectByPrimaryKey(id);
    }

    @Override
    public void updateByPrimaryKeySelective(Setting setting) {
        settingMapper.updateByPrimaryKeySelective(setting);
    }

    @Override
    public Page getListByPage(int pageNo, int pageSize, Setting setting) {
        Page<Map<String, Object>> page = settingMapper.getListByPage(new Page(pageNo, pageSize), setting);
        return page;
    }

    @Override
    public Setting selectCurrent() {
        return settingMapper.selectCurrent();
    }
}
