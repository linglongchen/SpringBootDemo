package com.chunqiu.mrjuly.common.vo;

import java.util.Map;

public class GridParam {
	/**
	 * 起始行数
	 */
	private int offset;
	/**
	 * 每页显示的数量
	 */
    private int limit;
    /**
     * 排序字段
     */
    private String sort;
    /**
     * 排序规则（asc,desc）
     */
    private String order = "asc";
    private Map<String,String> params;//查询条件
	
	public int getOffset() {
		return offset;
	}
	public void setOffset(int offset) {
		this.offset = offset;
	}
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	public Map<String, String> getParams() {
		return params;
	}
	public void setParams(Map<String, String> params) {
		this.params = params;
	}
}
