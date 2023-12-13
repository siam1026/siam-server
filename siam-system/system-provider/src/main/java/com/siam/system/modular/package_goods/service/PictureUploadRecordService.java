package com.siam.system.modular.package_goods.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.system.modular.package_goods.entity.PictureUploadRecord;
import com.siam.system.modular.package_goods.model.example.PictureUploadRecordExample;

/**
 *  暹罗
 */
public interface PictureUploadRecordService {

    int countByExample(PictureUploadRecordExample example);

    void deleteByPrimaryKey(Integer id);

    void insertSelective(PictureUploadRecord pictureUploadRecord);

    PictureUploadRecord selectByPrimaryKey(Integer id);

    void updateByPrimaryKeySelective(PictureUploadRecord pictureUploadRecord);

    /**
     * 条件查询集合
     * @param pictureUploadRecord 查询条件对象
     * @param pageNo 分页所在页
     * @param pageSize 单页数据量大小
     * @return
     */
    Page getList(int pageNo, int pageSize, PictureUploadRecord pictureUploadRecord);
}