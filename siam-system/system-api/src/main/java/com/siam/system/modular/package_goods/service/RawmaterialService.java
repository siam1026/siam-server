package com.siam.system.modular.package_goods.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.system.modular.package_goods.entity.Rawmaterial;

/**
 *  jianyang
 */
public interface RawmaterialService {

    void deleteByPrimaryKey(Integer id);

    void insertSelective(Rawmaterial rawmaterial);

    Rawmaterial selectByPrimaryKey(Integer id);

    void updateByPrimaryKeySelective(Rawmaterial rawmaterial);

    Page getListByPage(int pageNo, int pageSize, Rawmaterial rawmaterial);

}
