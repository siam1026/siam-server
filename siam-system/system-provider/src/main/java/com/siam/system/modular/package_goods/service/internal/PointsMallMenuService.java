package com.siam.system.modular.package_goods.service.internal;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.system.modular.package_goods.entity.internal.PointsMallMenu;

import java.util.List;
import java.util.Map;

/**
 *  暹罗
 */
public interface PointsMallMenuService {

    void deleteByPrimaryKey(Integer id);

    void insertSelective(PointsMallMenu menu);

    PointsMallMenu selectByPrimaryKey(Integer id);

    void updateByPrimaryKeySelective(PointsMallMenu menu);

    /**
     * 条件查询集合
     * @param menu 查询条件对象
     * @param pageNo 分页所在页
     * @param pageSize 单页数据量大小
     * @return
     */
    Page getList(int pageNo, int pageSize, PointsMallMenu menu);

    /**
     * 条件查询集合
     * @param menu 查询条件对象
     * @return
     */
    List<Map<String, Object>> getListByPageJoinPointsMallGoods(PointsMallMenu menu);
}