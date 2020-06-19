package com.tkb.manage.model;

public class Base {
	
	private Integer page;			//當前頁碼
	private Integer total_page;		//總頁數
	private Integer count;			//總筆數
	private Integer page_count;		//每頁筆數
	private String keyword;
	
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public Integer getTotal_page() {
		return total_page;
	}
	public void setTotal_page(Integer total_page) {
		this.total_page = total_page;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public Integer getPage_count() {
		return page_count;
	}
	public void setPage_count(Integer page_count) {
		this.page_count = page_count;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	
}
