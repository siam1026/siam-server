package com.siam.system.modular.package_goods.service.internal;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.system.modular.package_goods.entity.internal.Printer;

import java.util.Map;

/**
 *  暹罗
 */
public interface PrinterService {

    void deleteByPrimaryKey(Integer id);

    void insertSelective(Printer printer);

    Printer selectByPrimaryKey(Integer id);

    void updateByPrimaryKeySelective(Printer printer);

    /**
     * 条件查询集合
     * @param printer 查询条件对象
     * @param pageNo 分页所在页
     * @param pageSize 单页数据量大小
     * @return
     */
    Page getList(int pageNo, int pageSize, Printer printer);

    /**
     * 条件查询集合
     * @param printer 查询条件对象
     * @param pageNo 分页所在页
     * @param pageSize 单页数据量大小
     * @return
     */
    Page<Map<String, Object>> getListJoinShop(int pageNo, int pageSize, Printer printer);
}