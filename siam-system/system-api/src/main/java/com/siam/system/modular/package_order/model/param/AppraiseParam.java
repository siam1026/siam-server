package com.siam.system.modular.package_order.model.param;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class AppraiseParam {

    /* ##################################### START 扩展字段 #################################### */

    //页码
    @TableField(select = false)
    private Integer pageNo = 1;

    //页面大小
    @TableField(select = false)
    private Integer pageSize = 20;

    //开始日期
    @TableField(select = false)
    private Date startCreateTime;

    //结束日期
    @TableField(select = false)
    private Date endCreateTime;

    public Date getStartCreateTime() {
        return startCreateTime;
    }

    public void setStartCreateTime(Date startCreateTime) {
        this.startCreateTime = startCreateTime;
    }

    public Date getEndCreateTime() {
        return endCreateTime;
    }

    public void setEndCreateTime(Date endCreateTime) {
        this.endCreateTime = endCreateTime;
    }

    /* ##################################### END 扩展字段 #################################### */

    @TableId(type = IdType.AUTO)
    private Integer id;

    private Integer memberId;

    private Integer orderId;

    private Integer shopId;

    private Integer appraiseType;

    private String content;

    private List<String> imagesUrl;

    private Integer level;

    private Date createTime;

    private Date updateTime;
}