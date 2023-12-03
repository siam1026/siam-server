package com.siam.system.modular.package_user.service_impl;

import com.siam.system.modular.package_user.entity.AdminToken;
import com.siam.system.modular.package_user.mapper.AdminTokenMapper;
import com.siam.system.modular.package_user.model.example.AdminTokenExample;
import com.siam.system.modular.package_user.service.AdminTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminTokenServiceImpl implements AdminTokenService {

    @Autowired
    private AdminTokenMapper adminTokenMapper;

    public int countByExample(AdminTokenExample example){
        return adminTokenMapper.countByExample(example);
    }

    public void deleteByExample(AdminTokenExample example){
        adminTokenMapper.deleteByExample(example);
    }

    public void insertSelective(AdminToken record){
        adminTokenMapper.insertSelective(record);
    }

    public List<AdminToken> selectByExample(AdminTokenExample example){
        return adminTokenMapper.selectByExample(example);
    }

    public AdminToken selectByPrimaryKey(Integer id){
        return adminTokenMapper.selectByPrimaryKey(id);
    }

    public void updateByExampleSelective(AdminToken record, AdminTokenExample example){
        adminTokenMapper.updateByExampleSelective(record, example);
    }

    public void updateByPrimaryKeySelective(AdminToken record){
        adminTokenMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public void deleteByToken(String token) {
        AdminTokenExample example = new AdminTokenExample();
        AdminTokenExample.Criteria criteria = example.createCriteria();
        criteria.andTokenEqualTo(token);
        adminTokenMapper.deleteByExample(example);
    }

    @Override
    public AdminToken getLoginAdminInfo(String token){
        return adminTokenMapper.getLoginAdminInfo(token);
    }
}
