package com.siam.system.modular.package_user.service_impl;

import com.siam.system.modular.package_user.entity.MemberToken;
import com.siam.system.modular.package_user.model.example.MemberTokenExample;
import com.siam.system.modular.package_user.mapper.MemberTokenMapper;
import com.siam.system.modular.package_user.service.MemberTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberTokenServiceImpl implements MemberTokenService {

    @Autowired
    private MemberTokenMapper memberTokenMapper;

    public int countByExample(MemberTokenExample example){
        return memberTokenMapper.countByExample(example);
    }

    public void deleteByExample(MemberTokenExample example){
        memberTokenMapper.deleteByExample(example);
    }

    public void insertSelective(MemberToken record){
        memberTokenMapper.insertSelective(record);
    }

    public List<MemberToken> selectByExample(MemberTokenExample example){
        return memberTokenMapper.selectByExample(example);
    }

    public MemberToken selectByPrimaryKey(Integer id){
        return memberTokenMapper.selectByPrimaryKey(id);
    }

    public void updateByExampleSelective(MemberToken record, MemberTokenExample example){
        memberTokenMapper.updateByExampleSelective(record, example);
    }

    public void updateByPrimaryKeySelective(MemberToken record){
        memberTokenMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public void deleteByToken(String token) {
        MemberTokenExample example = new MemberTokenExample();
        MemberTokenExample.Criteria criteria = example.createCriteria();
        criteria.andTokenEqualTo(token);
        memberTokenMapper.deleteByExample(example);
    }

    @Override
    public MemberToken getLoginMemberInfo(String token){
        return memberTokenMapper.getLoginMemberInfo(token);
    }
}
