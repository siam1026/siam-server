package com.louis.kitty.dbms.model;

/**
 * 触发器
 * @author Louis
 * @date Nov 10, 2018
 */
public class Trigger {
    
    /** 触发器名称 */
    private String name;
    
    /** 触发器类型 */
    private String triggerType;
    
    /** 触发器事件类型 */
    private String eventType;
    
    /** 触发器事件描述 */
    private String description;
    
    /** 触发器事件内容定义 */
    private String definition;

    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTriggerType() {
        return triggerType;
    }

    public void setTriggerType(String type) {
        this.triggerType = type;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }
    
}
