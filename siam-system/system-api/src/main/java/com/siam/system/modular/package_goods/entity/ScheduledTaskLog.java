package com.siam.system.modular.package_goods.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@TableName("tb_scheduled_task_log")
@ApiModel(value = "定时任务执行日志表")
public class ScheduledTaskLog {

    @TableId(type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(notes = "任务代码")
    private String scheduledTaskCode;

    @ApiModelProperty(notes = "执行状态 1=执行成功 2=执行出错")
    private Integer state;

    @ApiModelProperty(notes = "错误描述")
    private String error;

    @ApiModelProperty(notes = "执行主机名称")
    private String hostName;

    @ApiModelProperty(notes = "执行主机ip地址")
    private String hostIpAddress;

    @ApiModelProperty(notes = "创建时间")
    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getScheduledTaskCode() {
        return scheduledTaskCode;
    }

    public void setScheduledTaskCode(String scheduledTaskCode) {
        this.scheduledTaskCode = scheduledTaskCode == null ? null : scheduledTaskCode.trim();
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error == null ? null : error.trim();
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName == null ? null : hostName.trim();
    }

    public String getHostIpAddress() {
        return hostIpAddress;
    }

    public void setHostIpAddress(String hostIpAddress) {
        this.hostIpAddress = hostIpAddress == null ? null : hostIpAddress.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}