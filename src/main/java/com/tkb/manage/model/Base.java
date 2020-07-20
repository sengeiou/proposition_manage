package com.tkb.manage.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Base {
	
	private Integer page;			//當前頁碼
	private Integer total_page;		//總頁數
	private Integer count;			//總筆數
	private Integer page_count;		//每頁筆數
	private String keyword;
	private String create_by;		//創建者
	private String create_time;		//創建時間
	private String update_by;		//編輯者
	private String update_time;		//編輯日期
	
}
