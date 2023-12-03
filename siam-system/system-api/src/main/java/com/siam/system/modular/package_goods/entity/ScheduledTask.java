package com.siam.system.modular.package_goods.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@TableName("tb_scheduled_task")
@ApiModel(value = "定时任务表")
public class ScheduledTask {

    @TableId(type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(notes = "任务名称")
    private String name;

    @ApiModelProperty(notes = "任务代码")
    private String code;

    @ApiModelProperty(notes = "执行频率")
    private String frequency;

    @ApiModelProperty(notes = "执行状态 1=未执行 2=正在执行")
    private Integer state;

    @ApiModelProperty(notes = "最后执行开始时间")
    private Date lastStartTime;

    @ApiModelProperty(notes = "最后执行结束时间")
    private Date lastEndTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency == null ? null : frequency.trim();
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getLastStartTime() {
        return lastStartTime;
    }

    public void setLastStartTime(Date lastStartTime) {
        this.lastStartTime = lastStartTime;
    }

    public Date getLastEndTime() {
        return lastEndTime;
    }

    public void setLastEndTime(Date lastEndTime) {
        this.lastEndTime = lastEndTime;
    }
}