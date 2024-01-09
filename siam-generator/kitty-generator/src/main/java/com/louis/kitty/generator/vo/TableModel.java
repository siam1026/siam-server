package com.louis.kitty.generator.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * 代码生成模型-表
 * @author Louis
 * @date Nov 10, 2018
 */
public class TableModel {

	/** Model包名 */
	private String modelPackageName;
	/** Model-param包名 */
	private String modelParamPackageName;
	/** Model-result包名 */
	private String modelResultPackageName;
	/** Dao包名 */
	private String daoPackageName;
	/** SqlMap xml包名 */
	private String sqlMapPackageName;
	/** Servcie包名 */
	private String servicePackageName;
	/** ServcieImpl包名 */
	private String serviceImplPackageName;
	/** Controller包名 */
	private String controllerPackageName;
	/** View目录名 */
	private String viewPackageName;
	/** 类名 */
	private String className;
	/** 实例名 */
	private String objectName;
	/** 主键列 */
	private ColumnModel primaryKey;
	
    /** 表名 */
    private String name;
    /** 描述 */
    private String description;
    /** 表空间 */
    private String tablespace;
    /** 索引字段列表 */
    private List<ColumnModel> columns = new ArrayList<ColumnModel>();

    public String getModelPackageName() {
		return modelPackageName;
	}

	public void setModelPackageName(String modelPackageName) {
		this.modelPackageName = modelPackageName;
	}

	public String getModelParamPackageName() {
		return modelParamPackageName;
	}

	public void setModelParamPackageName(String modelParamPackageName) {
		this.modelParamPackageName = modelParamPackageName;
	}

	public String getModelResultPackageName() {
		return modelResultPackageName;
	}

	public void setModelResultPackageName(String modelResultPackageName) {
		this.modelResultPackageName = modelResultPackageName;
	}

	public String getDaoPackageName() {
		return daoPackageName;
	}

	public void setDaoPackageName(String daoPackageName) {
		this.daoPackageName = daoPackageName;
	}

	public String getSqlMapPackageName() {
		return sqlMapPackageName;
	}

	public void setSqlMapPackageName(String sqlMapPackageName) {
		this.sqlMapPackageName = sqlMapPackageName;
	}

	public String getServicePackageName() {
		return servicePackageName;
	}

	public void setServicePackageName(String servicePackageName) {
		this.servicePackageName = servicePackageName;
	}

	public String getServiceImplPackageName() {
		return serviceImplPackageName;
	}

	public void setServiceImplPackageName(String serviceImplPackageName) {
		this.serviceImplPackageName = serviceImplPackageName;
	}

	public String getControllerPackageName() {
		return controllerPackageName;
	}

	public void setControllerPackageName(String controllerPackageName) {
		this.controllerPackageName = controllerPackageName;
	}

	public String getViewPackageName() {
		return viewPackageName;
	}

	public void setViewPackageName(String viewPackageName) {
		this.viewPackageName = viewPackageName;
	}

	public String getClassName() {
		return className;
	}
    
	public void setClassName(String className) {
		this.className = className;
	}
	
	public String getObjectName() {
		return objectName;
	}
	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}
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

    public String getTablespace() {
        return tablespace;
    }

    public void setTablespace(String tablespace) {
        this.tablespace = tablespace;
    }

    public List<ColumnModel> getColumns() {
        return columns;
    }

    public void setColumns(List<ColumnModel> columns) {
        this.columns = columns;
    }

    public void addColumn(ColumnModel clolumn) {
        this.columns.add(clolumn);
    }

	public ColumnModel getPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(ColumnModel primaryKey) {
		this.primaryKey = primaryKey;
	}

}
