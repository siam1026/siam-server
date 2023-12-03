package com.siam.system.modular.package_order.service_impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.package_common.constant.Quantity;
import com.siam.system.modular.package_user.service.MemberService;
import com.siam.system.modular.package_order.service.GiveLikeService;
import com.siam.system.modular.package_order.entity.Reply;
import com.siam.system.modular.package_order.mapper.ReplyMapper;
import com.siam.system.modular.package_order.service.ReplyService;
import com.siam.system.modular.package_user.entity.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Service
public class ReplyServiceImpl implements ReplyService {

    @Autowired
    private ReplyMapper replyMapper;

    @Autowired
    private MemberService memberService;

    @Autowired
    private GiveLikeService giveLikeService;

    @Override
    public void insertSelective(Reply reply) {
        reply.setCreateTime(new Date());
        replyMapper.insertSelective(reply);
    }

    @Override
    public void deleteByPrimaryKey(Integer id) {
        replyMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Page<Reply> getListByPage(int pageNo, int pageSize, Reply reply) {
        Page<Reply> page = replyMapper.getListByPage(new Page(pageNo, pageSize), reply);
        return page;
    }

    public Page<Map<String, Object>> getMapListByPage(int pageNo, int pageSize, Reply reply) {
        /*ReplyExample example = new ReplyExample();
        example.createCriteria().andAppraiseIdEqualTo(appraiseId);
        List<Reply> replyList = replyMapper.selectByExample(example);
        return replyList;*/
        Page<Map<String, Object>> page = replyMapper.getMapListByPage(new Page(pageNo, pageSize), reply);
        page.getRecords().forEach(map -> {
            if(((int) map.get("replyType")) == Quantity.INT_2){
                //查询被回复的用户名
                Reply answeredReply = replyMapper.selectByPrimaryKey((int) map.get("replyId"));
                if(answeredReply.getReplierType() == Quantity.INT_1){
                    Member answeredMember = memberService.selectByPrimaryKey(answeredReply.getMemberId());
                    map.put("answeredUsername", answeredMember.getUsername());
                }else{
                    map.put("answeredUsername", "商家");
                }
            }

            //查询点赞数量
            int giveLikeCount = giveLikeService.countByReplyId((int) map.get("id"));
            map.put("giveLikeCount", giveLikeCount);
        });
        return page;
    }
}