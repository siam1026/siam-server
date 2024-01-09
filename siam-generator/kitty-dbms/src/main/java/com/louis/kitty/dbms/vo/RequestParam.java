package com.louis.kitty.dbms.vo;

/**
 * 查询参数封装
 * @author Louis
 * @date Nov 10, 2018
 */
public class RequestParam extends ConnParam {

	private static final long serialVersionUID = 1L;
	
	private String tableName;

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
}
