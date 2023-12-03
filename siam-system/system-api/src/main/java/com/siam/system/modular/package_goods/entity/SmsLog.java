package com.siam.system.modular.package_goods.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@TableName("tb_sms_log")
@ApiModel(value = "短信验证码记录表")
public class SmsLog {

    @TableId(type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(notes = "用户id")
    private Integer memberId;

    @ApiModelProperty(notes = "手机号")
    private String mobile;

    @ApiModelProperty(notes = "短信类型 注册=register;登录=login;验证手机号=verification;找回密码=findpwd")
    private String type;

    @ApiModelProperty(notes = "创建时间")
    private Date createTime;

    @ApiModelProperty(notes = "请求ip")
    private String ip;

    @ApiModelProperty(notes = "短信验证码")
    private String verifyCode;

    @ApiModelProperty(notes = "描述")
    private String description;

    @ApiModelProperty(notes = "发送状态 1-发送成功，2-发送失败")
    private Boolean states;

    //页码
    private Integer pageNo = 1;

    //页面大小
    private Integer pageSize = 20;

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode == null ? null : verifyCode.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Boolean getStates() {
        return states;
    }

    public void setStates(Boolean states) {
        this.states = states;
    }
}