package com.siam.system.modular.package_goods.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 系统消息表实例类
 *
 * @author 暹罗
 * @date 2021/10/21 12:37
 */
@Data
@TableName("tb_message")
public class SysMessage implements Serializable {

    //消息用户类型 1=用户 2=商家 3=管理员
    public static final int USER_TYPE_MEMBER = 1;
    public static final int USER_TYPE_MERCHANT = 2;
    public static final int USER_TYPE_ADMIN = 3;

    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 用户id
     */
    @TableField("user_id")
    private Integer userId;

    /**
     * 消息用户类型 1=用户 2=商家 3=管理员
     */
    @TableField("user_type")
    private Integer userType;

    /**
     * 标题
     */
    @TableField("title")
    private String title;

    /**
     * 内容
     */
    @TableField("content")
    private String content;

    /**
     * 是否已读 0=未读 1=已读
     */
    @TableField("is_read")
    private Boolean isRead;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 修改时间
     */
    @TableField(value = "update_time", fill = FieldFill.UPDATE)
    private Date updateTime;

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

}