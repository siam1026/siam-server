package com.siam.system.modular.package_goods.service_impl.internal;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.system.modular.package_goods.entity.internal.Printer;
import com.siam.system.modular.package_goods.mapper.internal.PrinterMapper;
import com.siam.system.modular.package_goods.service.internal.PrinterService;
import com.siam.system.modular.package_goods.mapper.internal.PrinterMapper;
import com.siam.system.modular.package_goods.service.internal.PrinterService;
import com.siam.system.modular.package_goods.entity.internal.Printer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PrinterServiceImpl implements PrinterService {

    @Autowired
    private PrinterMapper printerMapper;

    @Override
    public void deleteByPrimaryKey(Integer id) {
        printerMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void insertSelective(Printer printer) {
        printerMapper.insertSelective(printer);
    }

    @Override
    public Printer selectByPrimaryKey(Integer id) {
        return printerMapper.selectByPrimaryKey(id);
    }

    @Override
    public void updateByPrimaryKeySelective(Printer printer) {
        printerMapper.updateByPrimaryKeySelective(printer);
    }

    @Override
    public Page getList(int pageNo, int pageSize, Printer printer) {
        Page<Map<String, Object>> page = printerMapper.getListByPage(new Page(pageNo, pageSize), printer);
        return page;
    }

    @Override
    public Page<Map<String, Object>> getListJoinShop(int pageNo, int pageSize, Printer printer) {
        Page<Map<String, Object>> page = printerMapper.getListJoinShop(new Page(pageNo, pageSize), printer);
        return page;
    }
}