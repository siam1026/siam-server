package com.louis.kitty.dbms.model;

/**
 * 存储过程
 * @author Louis
 * @date Nov 10, 2018
 */
public class Procedure {
    
    /** 名称 */
    private String name;
    
    /** 描述 */
    private String description;
    
    /** 内容定义 */
    private String definition;

    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
