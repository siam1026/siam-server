package com.siam.system.modular.package_user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.system.modular.package_user.entity.Member;
import com.siam.system.modular.package_user.model.example.MemberExample;
import com.siam.system.modular.package_user.model.param.MemberParam;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface MemberMapper extends BaseMapper<Member> {
    int countByExample(MemberExample example);

    int deleteByExample(MemberExample example);

    int deleteByPrimaryKey(Integer id);

    int insertSelective(Member record);

    List<Member> selectByExample(MemberExample example);

    Member selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Member record, @Param("example") MemberExample example);

    int updateByExample(@Param("record") Member record, @Param("example") MemberExample example);

    int updateByPrimaryKeySelective(Member record);

    int updateByPrimaryKey(Member record);

    @ResultMap("BaseResultMap")
    @Select("select * from tb_member where mobile = #{mobile} order by id desc limit 1 ")
    Member selectByMobile(@Param("mobile") String mobile);

    @ResultMap("BaseResultMap")
    @Select("select * from tb_member where username = #{username} or mobile = #{username} order by id desc limit 1 ")
    Member selectByUsernameOrMobile(@Param("username") String username);

    @ResultMap("BaseResultMap")
    @Select("<script>select m.* from tb_member m" +
            "<where> 1=1 " +
            "<if test=\"member.id != null\"> AND m.id = #{member.id} </if>" +
            "<if test=\"member.username != null and member.username !=''\"> AND m.username like '%${member.username}%' </if>" +
            "<if test=\"member.mobile != null and member.mobile !=''\"> AND m.mobile like '%${member.mobile}%' </if>" +
            "<if test=\"member.password != null and member.password !=''\"> AND m.password like '%${member.password}%' </if>" +
            "<if test=\"member.passwordSalt != null and member.passwordSalt !=''\"> AND m.password_salt like '%${member.passwordSalt}%' </if>" +
            "<if test=\"member.nickname != null and member.nickname !=''\"> AND m.nickname like '%${member.nickname}%' </if>" +
            "<if test=\"member.balance != null\"> AND m.balance = #{member.balance} </if>" +
            "<if test=\"member.loginCount != null\"> AND m.login_count = #{member.loginCount} </if>" +
            "<if test=\"member.inviteCode != null and member.inviteCode !=''\"> AND m.invite_code like '%${member.inviteCode}%' </if>" +
            "<if test=\"member.headImg != null and member.headImg !=''\"> AND m.head_img like '%${member.headImg}%' </if>" +
            "<if test=\"member.roles != null and member.roles !=''\"> AND m.roles like '%${member.roles}%' </if>" +
            "<if test=\"member.sex != null\"> AND m.sex = #{member.sex} </if>" +
            "<if test=\"member.email != null and member.email !=''\"> AND m.email like '%${member.email}%' </if>" +
            "<if test=\"member.isDisabled != null\"> AND m.isDisabled = #{member.isDisabled} </if>" +
            "<if test=\"member.isDeleted != null\"> AND m.is_deleted = #{member.isDeleted} </if>" +
            "<if test=\"member.openId != null\"> AND m.open_id = #{member.openId} </if>"+
            "<if test=\"member.isBindWx != null\"> AND m.is_bind_wx = #{member.isBindWx} </if>"+
            "<if test=\"member.points != null\"> AND m.points = #{member.points} </if>"+
            "<if test=\"member.vipStatus != null\"> AND m.vip_status = #{member.vipStatus} </if>"+
            "<if test=\"member.vipType != null\"> AND m.vip_type = #{member.vipType} </if>"+
            "<if test=\"member.type != null\"> AND m.type = #{member.type} </if>"+
            "<if test=\"member.vipNo != null\"> AND m.vip_no = #{member.vipNo} </if>"+
            "<if test=\"member.isNewPeople != null\"> AND m.is_new_people = #{member.isNewPeople} </if>"+
            "<if test=\"member.startCreateTime != null\"> AND DATE_FORMAT(m.create_time, '%Y/%m/%d') &gt;= #{member.startCreateTime} </if>" +
            "<if test=\"member.endCreateTime != null\"> AND DATE_FORMAT(m.create_time, '%Y/%m/%d') &lt;= #{member.endCreateTime} </if>" +
            "</where> order by m.id asc" +
            "</script>")
    Page<Map<String, Object>> getListByPage(@Param("page") Page page, @Param("member") MemberParam member);

    //add by chenqu 微信OpenId快捷登录,必须绑定了微信才可以快捷登录
    @Select("SELECT id FROM tb_member  WHERE open_id=#{openId} AND is_deleted = 0 and is_bind_wx='1'")
    Integer  findMemberByOpenId(@Param("openId") String openId);

    //微信一键登录
    @Select("SELECT id FROM tb_member  WHERE mobile=#{mobile} AND is_deleted = 0 and is_bind_wx=1 ")
    Integer  findMemberByMobile(@Param("mobile") String mobile);

    //add by chenqu 微信OpenId快捷登录
    @Update("update tb_member set is_bind_wx='1' WHERE mobile=#{mobile} AND is_deleted = 0")
    Integer  updateMemberByMobile(@Param("mobile") String mobile);

    //通过手机号码查询该用户的token
    @Select("SELECT mt.token FROM tb_member m LEFT JOIN tb_member_token mt ON m.id = mt.member_id WHERE m.mobile =#{mobile}  AND is_deleted = 0 limit 1")
    String findMemberTokenByMobile(@Param("mobile") String mobile);

    /**
     * 查询最大的会员卡号
     * @return
     */
    @Select("SELECT max(vip_no) from tb_member")
    String findMaxVipNo();

    @ResultMap("BaseResultMap")
    @Select("select * from tb_member where id not in(" +
            "select m.id from tb_member as m,tb_coupons_member_relation as cmr " +
            "where m.id=cmr.member_id and cmr.is_used=0 and cmr.is_expired=0 and cmr.is_valid=1" +
            ") ")
    List<Member> selectAllMemberNoneCoupons();

    @ResultMap("BaseResultMap")
    @Select("select * from tb_member where id not in(" +
            "select m.id from tb_member as m,tb_points_mall_coupons_member_relation as cmr " +
            "where m.id=cmr.member_id and cmr.is_used=0 and cmr.is_expired=0 and cmr.is_valid=1" +
            ") ")
    List<Member> selectAllMemberNoneCouponsByPointsMall();

    @Update("update tb_member set is_remind_new_people = 1 where is_new_people = 1 and is_remind_new_people = 0")
    int updateIsRemindNewPeople();

    @ResultMap("BaseResultMap")
    @Select("select DISTINCT m.* from tb_member as m,tb_coupons_member_relation as cmr " +
            "             where m.id=cmr.member_id and cmr.is_used=0 and cmr.is_expired=0 and cmr.is_valid=1  " +
            "             and cmr.end_time<#{overdueTime} and cmr.end_time>now()")
    List<Member> selectHasOverdueCoupons(@Param("overdueTime") Date overdueTime);

    @ResultMap("BaseResultMap")
    @Select("select m.* from tb_member_invite_relation as mir, tb_member as m where m.id=mir.member_id and mir.inviter_id=#{inviterId}")
    Page<Member> getMemberListByInviterId(@Param("page") Page page, @Param("inviterId") Integer inviterId);

    @ResultMap("BaseResultMap")
    @Select("<script>select m.* from tb_member m " +
            "<where> 1=1 and m.id in (SELECT DISTINCT member_id FROM tb_order WHERE STATUS NOT IN (1, 10)) " +
            "<if test=\"member.id != null\"> AND m.id = #{member.id} </if>" +
            "<if test=\"member.username != null and member.username !=''\"> AND m.username like '%${member.username}%' </if>" +
            "<if test=\"member.mobile != null and member.mobile !=''\"> AND m.mobile like '%${member.mobile}%' </if>" +
            "<if test=\"member.password != null and member.password !=''\"> AND m.password like '%${member.password}%' </if>" +
            "<if test=\"member.passwordSalt != null and member.passwordSalt !=''\"> AND m.password_salt like '%${member.passwordSalt}%' </if>" +
            "<if test=\"member.nickname != null and member.nickname !=''\"> AND m.nickname like '%${member.nickname}%' </if>" +
            "<if test=\"member.balance != null\"> AND m.balance = #{member.balance} </if>" +
            "<if test=\"member.loginCount != null\"> AND m.login_count = #{member.loginCount} </if>" +
            "<if test=\"member.inviteCode != null and member.inviteCode !=''\"> AND m.invite_code like '%${member.inviteCode}%' </if>" +
            "<if test=\"member.headImg != null and member.headImg !=''\"> AND m.head_img like '%${member.headImg}%' </if>" +
            "<if test=\"member.roles != null and member.roles !=''\"> AND m.roles like '%${member.roles}%' </if>" +
            "<if test=\"member.sex != null\"> AND m.sex = #{member.sex} </if>" +
            "<if test=\"member.email != null and member.email !=''\"> AND m.email like '%${member.email}%' </if>" +
            "<if test=\"member.isDisabled != null\"> AND m.isDisabled = #{member.isDisabled} </if>" +
            "<if test=\"member.isDeleted != null\"> AND m.is_deleted = #{member.isDeleted} </if>" +
            "<if test=\"member.openId != null\"> AND m.open_id = #{member.openId} </if>"+
            "<if test=\"member.isBindWx != null\"> AND m.is_bind_wx = #{member.isBindWx} </if>"+
            "<if test=\"member.points != null\"> AND m.points = #{member.points} </if>"+
            "<if test=\"member.vipStatus != null\"> AND m.vip_status = #{member.vipStatus} </if>"+
            "<if test=\"member.vipType != null\"> AND m.vip_type = #{member.vipType} </if>"+
            "<if test=\"member.type != null\"> AND m.type = #{member.type} </if>"+
            "<if test=\"member.vipNo != null\"> AND m.vip_no = #{member.vipNo} </if>"+
            "<if test=\"member.isNewPeople != null\"> AND m.is_new_people = #{member.isNewPeople} </if>"+
            "<if test=\"member.startCreateTime != null\"> AND DATE_FORMAT(m.create_time, '%Y/%m/%d') &gt;= #{member.startCreateTime} </if>" +
            "<if test=\"member.endCreateTime != null\"> AND DATE_FORMAT(m.create_time, '%Y/%m/%d') &lt;= #{member.endCreateTime} </if>" +
            "</where> order by m.id asc" +
            "</script>")
    Page<Member> purchasedList(@Param("page") Page page, @Param("member") MemberParam member);

    @ResultMap("BaseResultMap")
    @Select("<script>select m.* from tb_member m " +
            "<where> 1=1 and m.id not in (SELECT DISTINCT member_id FROM tb_order WHERE STATUS NOT IN (1, 10)) " +
            "<if test=\"member.id != null\"> AND m.id = #{member.id} </if>" +
            "<if test=\"member.username != null and member.username !=''\"> AND m.username like '%${member.username}%' </if>" +
            "<if test=\"member.mobile != null and member.mobile !=''\"> AND m.mobile like '%${member.mobile}%' </if>" +
            "<if test=\"member.password != null and member.password !=''\"> AND m.password like '%${member.password}%' </if>" +
            "<if test=\"member.passwordSalt != null and member.passwordSalt !=''\"> AND m.password_salt like '%${member.passwordSalt}%' </if>" +
            "<if test=\"member.nickname != null and member.nickname !=''\"> AND m.nickname like '%${member.nickname}%' </if>" +
            "<if test=\"member.balance != null\"> AND m.balance = #{member.balance} </if>" +
            "<if test=\"member.loginCount != null\"> AND m.login_count = #{member.loginCount} </if>" +
            "<if test=\"member.inviteCode != null and member.inviteCode !=''\"> AND m.invite_code like '%${member.inviteCode}%' </if>" +
            "<if test=\"member.headImg != null and member.headImg !=''\"> AND m.head_img like '%${member.headImg}%' </if>" +
            "<if test=\"member.roles != null and member.roles !=''\"> AND m.roles like '%${member.roles}%' </if>" +
            "<if test=\"member.sex != null\"> AND m.sex = #{member.sex} </if>" +
            "<if test=\"member.email != null and member.email !=''\"> AND m.email like '%${member.email}%' </if>" +
            "<if test=\"member.isDisabled != null\"> AND m.isDisabled = #{member.isDisabled} </if>" +
            "<if test=\"member.isDeleted != null\"> AND m.is_deleted = #{member.isDeleted} </if>" +
            "<if test=\"member.openId != null\"> AND m.open_id = #{member.openId} </if>"+
            "<if test=\"member.isBindWx != null\"> AND m.is_bind_wx = #{member.isBindWx} </if>"+
            "<if test=\"member.points != null\"> AND m.points = #{member.points} </if>"+
            "<if test=\"member.vipStatus != null\"> AND m.vip_status = #{member.vipStatus} </if>"+
            "<if test=\"member.vipType != null\"> AND m.vip_type = #{member.vipType} </if>"+
            "<if test=\"member.type != null\"> AND m.type = #{member.type} </if>"+
            "<if test=\"member.vipNo != null\"> AND m.vip_no = #{member.vipNo} </if>"+
            "<if test=\"member.isNewPeople != null\"> AND m.is_new_people = #{member.isNewPeople} </if>"+
            "<if test=\"member.startCreateTime != null\"> AND DATE_FORMAT(m.create_time, '%Y/%m/%d') &gt;= #{member.startCreateTime} </if>" +
            "<if test=\"member.endCreateTime != null\"> AND DATE_FORMAT(m.create_time, '%Y/%m/%d') &lt;= #{member.endCreateTime} </if>" +
            "</where> order by m.id asc" +
            "</script>")
    Page<Member> unPurchasedList(@Param("page") Page page, @Param("member") MemberParam member);

    @Select("<script>select count(*) from tb_member m "+
            "<where> 1=1 and (m.create_time between #{startTime} and #{endTime})" +
            "<if test=\"registerWay != null\"> AND m.register_way = #{registerWay} </if>" +
            "</where>"+
            "</script>")
    int selectCountRegister(@Param("registerWay") Integer registerWay, @Param("startTime") Date startTime, @Param("endTime") Date endTime);

    @Update("UPDATE tb_member SET is_request_wx_notify = 1 WHERE is_request_wx_notify = 0 AND TIMESTAMPDIFF(DAY, last_request_wx_notify_time, NOW()) > 14")
    int updateIsRequestWxNotify();
}