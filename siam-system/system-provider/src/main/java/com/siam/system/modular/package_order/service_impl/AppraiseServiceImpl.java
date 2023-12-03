package com.siam.system.modular.package_order.service_impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.system.modular.package_order.entity.Reply;
import com.siam.system.modular.package_order.service.GiveLikeService;
import com.siam.system.modular.package_order.service.ReplyService;
import com.siam.system.modular.package_order.entity.Appraise;
import com.siam.system.modular.package_order.mapper.AppraiseMapper;
import com.siam.system.modular.package_order.service.AppraiseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Service
public class AppraiseServiceImpl implements AppraiseService {

    @Autowired
    private AppraiseMapper appraiseMapper;

    @Autowired
    private ReplyService replyService;

    @Autowired
    private GiveLikeService giveLikeService;

    @Override
    public void insertSelective(Appraise appraise) {
        appraise.setCreateTime(new Date());
        appraiseMapper.insertSelective(appraise);
    }

    @Override
    public void deleteByPrimaryKey(Integer id) {
        appraiseMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Page<Appraise> getListByPage(int pageNo, int pageSize, Appraise appraise) {
        Page<Appraise> page = appraiseMapper.getListByPage(new Page(pageNo, pageSize), appraise);
        return page;
    }

    @Override
    public Page<Map<String, Object>> getMapListByPage(int pageNo, int pageSize, Appraise appraise) {
        Page<Map<String, Object>> page = appraiseMapper.getMapListByPage(new Page(pageNo, pageSize), appraise);
        page.getRecords().forEach(map -> {
            //查询评价对应的回复内容(回复内容还有子回复内容)
            Reply replyParam = new Reply();
            replyParam.setAppraiseId((int) map.get("id"));
            Page<Map<String, Object>> replyPage = replyService.getMapListByPage(1, 10, replyParam);
            map.put("replyPage", replyPage);

            //TODO-查询回复内容下子回复内容

            //查询点赞数量
            int giveLikeCount = giveLikeService.countByAppraiseId((int) map.get("id"));
            map.put("giveLikeCount", giveLikeCount);

            //还需要返回本人是否有点赞过
        });

        return page;
    }

    @Override
    public boolean getIsAllowAppraise(Appraise appraise) {
        return appraiseMapper.getIsAllowAppraise(appraise);
    }
}