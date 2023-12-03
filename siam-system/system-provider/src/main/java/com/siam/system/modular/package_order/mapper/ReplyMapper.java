package com.siam.system.modular.package_order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.siam.system.modular.package_order.entity.Reply;
import com.siam.system.modular.package_order.model.example.ReplyExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.Map;

public interface ReplyMapper extends BaseMapper<Reply> {
    int countByExample(ReplyExample example);

    int deleteByExample(ReplyExample example);

    int deleteByPrimaryKey(Integer id);

    int insertSelective(Reply record);

    List<Reply> selectByExample(ReplyExample example);

    Reply selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Reply record, @Param("example") ReplyExample example);

    int updateByExample(@Param("record") Reply record, @Param("example") ReplyExample example);

    int updateByPrimaryKeySelective(Reply record);

    int updateByPrimaryKey(Reply record);

    @ResultMap("BaseResultMap")
    @Select("<script>select a.* from tb_reply a" +
            "<where> 1=1 " +
            "<if test=\"reply.id != null\"> AND a.id = #{reply.id} </if>" +
            "<if test=\"reply.memberId != null\"> AND a.member_id = #{reply.memberId} </if>" +
            "<if test=\"reply.merchantId != null\"> AND a.merchant_id = #{reply.merchantId} </if>" +
            "<if test=\"reply.appraiseId != null\"> AND a.appraise_id = #{reply.appraiseId} </if>" +
            "<if test=\"reply.replyId != null\"> AND a.reply_id = #{reply.replyId} </if>" +
            "<if test=\"reply.replyType != null\"> AND a.reply_type = #{reply.replyType} </if>" +
            "<if test=\"reply.replierType != null\"> AND a.replier_type = #{reply.replierType} </if>" +
            "</where> order by a.id desc" +
            "</script>")
    Page<Reply> getListByPage(@Param("page") Page page, @Param("reply") Reply reply);

    @ResultMap("CustomResultMap")
    @Select("<script>select a.*, m.username, m.head_img as headImg, " +
            "(SELECT if(COUNT(*)>0, true, false) FROM tb_give_like WHERE TYPE = 2 AND member_id = #{reply.memberId} AND reply_id = a.id) as isGiveLike " +
            "from tb_reply a " +
            "left join tb_member m on m.id = a.member_id " +
            "<where> 1=1 " +
            "<if test=\"reply.id != null\"> AND a.id = #{reply.id} </if>" +
            /*"<if test=\"reply.memberId != null\"> AND a.member_id = #{reply.memberId} </if>" +*/
            "<if test=\"reply.merchantId != null\"> AND a.merchant_id = #{reply.merchantId} </if>" +
            "<if test=\"reply.appraiseId != null\"> AND a.appraise_id = #{reply.appraiseId} </if>" +
            "<if test=\"reply.replyId != null\"> AND a.reply_id = #{reply.replyId} </if>" +
            "<if test=\"reply.replyType != null\"> AND a.reply_type = #{reply.replyType} </if>" +
            "<if test=\"reply.replierType != null\"> AND a.replier_type = #{reply.replierType} </if>" +
            "</where> order by a.id desc" +
            "</script>")
    Page<Map<String, Object>> getMapListByPage(@Param("page") Page page, @Param("reply") Reply reply);
}