package com.siam.system.modular.package_goods.service_impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.system.modular.package_goods.entity.PaperworkPush;
import com.siam.system.modular.package_goods.mapper.PaperworkPushMapper;
import com.siam.system.modular.package_goods.service.PaperworkPushService;
import com.siam.system.modular.package_goods.mapper.PaperworkPushMapper;
import com.siam.system.modular.package_goods.service.PaperworkPushService;
import com.siam.system.modular.package_goods.entity.PaperworkPush;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PaperworkPushServiceImpl implements PaperworkPushService {

    @Autowired
    private PaperworkPushMapper paperworkPushMapper;

    @Override
    public void deleteByPrimaryKey(Integer id) {
        paperworkPushMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void insertSelective(PaperworkPush paperworkPush) {
        paperworkPushMapper.insertSelective(paperworkPush);
    }

    @Override
    public PaperworkPush selectByPrimaryKey(Integer id) {
        return paperworkPushMapper.selectByPrimaryKey(id);
    }

    @Override
    public void updateByPrimaryKeySelective(PaperworkPush paperworkPush) {
        paperworkPushMapper.updateByPrimaryKeySelective(paperworkPush);
    }

    @Override
    public Page getList(int pageNo, int pageSize, PaperworkPush paperworkPush) {
        Page<Map<String, Object>> page = paperworkPushMapper.getListByPage(new Page(pageNo, pageSize), paperworkPush);
        return page;
    }
}