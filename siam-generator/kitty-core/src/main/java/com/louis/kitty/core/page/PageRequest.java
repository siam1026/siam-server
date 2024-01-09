package com.louis.kitty.core.page;

import java.util.HashMap;
import java.util.Map;

/**
 * 分页请求
 * @author Louis
 * @date Aug 19, 2018
 */
public class PageRequest {
	/**
	 * 当前页码
	 */
	private int pageNum = 1;
	/**
	 * 每页数量
	 */
	private int pageSize = 10;
	/**
	 * 每页数量
	 */
	private Map<String, ColumnFilter> columnFilters = new HashMap<String, ColumnFilter>();
	
	public int getPageNum() {
		return pageNum;
	}
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public Map<String, ColumnFilter> getColumnFilters() {
		return columnFilters;
	}
	public void setColumnFilters(Map<String, ColumnFilter> columnFilters) {
		this.columnFilters = columnFilters;
	}
	public ColumnFilter getColumnFilter(String name) {
		return columnFilters.get(name);
	}
}
