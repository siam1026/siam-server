package com.siam.system.modular.package_user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("tb_member")
public class Member {

    //注册方式 1=微信一键登录 2=手机验证码 3=邀请注册
    public static final int REGISTER_WAY_OF_WECHAT = 1;
    public static final int REGISTER_WAY_OF_MOBILECODE = 2;
    public static final int REGISTER_WAY_OF_INVITE = 3;

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String username;

    private String mobile;

    private String password;

    private String passwordSalt;

    private String nickname;

    private BigDecimal balance;

    private Integer loginCount;

    private String inviteCode;

    private String headImg;

    private String roles;

    private Integer sex;

    private String email;

    private Boolean isDisabled;

    private Boolean isDeleted;

    private String openId;

    private Boolean isBindWx;

    private BigDecimal points;

    private Integer vipStatus;

    private Integer vipType;

    private Date vipStartTime;

    private Date vipEndTime;

    private Integer type;

    private String vipNo;

    private Boolean isNewPeople;

    private Boolean isRemindNewPeople;

    private Date lastUseTime;

    private String lastUseAddress;

    private Integer registerWay;

    private String wxPublicPlatformOpenId;

    private Boolean isRequestWxNotify;

    private Date lastRequestWxNotifyTime;

    private BigDecimal inviteRewardAmount;

    private String realName;

    private BigDecimal totalBalance;

    private BigDecimal totalConsumeBalance;

    private BigDecimal totalPoints;

    private BigDecimal totalConsumePoints;

    private BigDecimal totalWithdrawInviteRewardAmount;

    private String paymentPassword;

    private String paymentPasswordSalt;

    private String inviteSuncode;

    private BigDecimal unreceivedPoints;

    private BigDecimal unreceivedInviteRewardAmount;

    private Date createTime;

    private Date updateTime;

    private Date lastLoginTime;
}