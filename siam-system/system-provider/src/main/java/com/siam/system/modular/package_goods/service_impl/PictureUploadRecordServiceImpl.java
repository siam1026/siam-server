package com.siam.system.modular.package_goods.service_impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.system.modular.package_goods.entity.PictureUploadRecord;
import com.siam.system.modular.package_goods.mapper.PictureUploadRecordMapper;
import com.siam.system.modular.package_goods.model.example.PictureUploadRecordExample;
import com.siam.system.modular.package_goods.service.PictureUploadRecordService;
import com.siam.system.modular.package_goods.mapper.PictureUploadRecordMapper;
import com.siam.system.modular.package_goods.service.PictureUploadRecordService;
import com.siam.system.modular.package_goods.entity.PictureUploadRecord;
import com.siam.system.modular.package_goods.model.example.PictureUploadRecordExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PictureUploadRecordServiceImpl implements PictureUploadRecordService {

    @Autowired
    private PictureUploadRecordMapper pictureUploadRecordMapper;

    @Override
    public int countByExample(PictureUploadRecordExample example) {
        return pictureUploadRecordMapper.countByExample(example);
    }

    @Override
    public void deleteByPrimaryKey(Integer id) {
        pictureUploadRecordMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void insertSelective(PictureUploadRecord pictureUploadRecord) {
        pictureUploadRecordMapper.insertSelective(pictureUploadRecord);
    }

    @Override
    public PictureUploadRecord selectByPrimaryKey(Integer id) {
        return pictureUploadRecordMapper.selectByPrimaryKey(id);
    }

    @Override
    public void updateByPrimaryKeySelective(PictureUploadRecord pictureUploadRecord) {
        pictureUploadRecordMapper.updateByPrimaryKeySelective(pictureUploadRecord);
    }

    @Override
    public Page getList(int pageNo, int pageSize, PictureUploadRecord pictureUploadRecord) {
        Page<Map<String, Object>> page = pictureUploadRecordMapper.getListByPage(new Page(pageNo, pageSize), pictureUploadRecord);
        return page;
    }
}