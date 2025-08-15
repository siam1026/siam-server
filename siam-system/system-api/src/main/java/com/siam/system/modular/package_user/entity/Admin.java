package com.siam.system.modular.package_user.entity;

import com.baomidou.mybatisplus.annotation.IdType; import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@TableName("tb_admin")
@ApiModel(value = "管理员表")
public class Admin {
    @ApiModelProperty(notes = "主键id")
    @TableId(type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(notes = "管理员用户名")
    private String username;

    @ApiModelProperty(notes = "手机号")
    private String mobile;

    @ApiModelProperty(notes = "密码")
    private String password;

    @ApiModelProperty(notes = "密码盐值")
    private String passwordSalt;

    @ApiModelProperty(notes = "昵称")
    private String nickname;

    @ApiModelProperty(notes = "权限")
    private String roles;

    @ApiModelProperty(notes = "是否禁用 0=启用 1=禁用")
    private Boolean isDisabled;

    @ApiModelProperty(notes = "注册时间")
    private Date createTime;

    @ApiModelProperty(notes = "修改时间")
    private Date updateTime;

    @ApiModelProperty(notes = "最后登录时间")
    private Date lastLoginTime;
}