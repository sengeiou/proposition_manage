package com.tkb.manage.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Education extends Base {
	
	private String id;				//流水號
	private String uuid;			//UUID
	private String name;			//名稱
	private String parent_id;		//父層流水號
	private String layer;			//層級
	private String sort;			//排序
	private String create_by;		//創建者
	private String create_time;		//創建時間
	private String update_by;		//編輯者
	private String update_time;		//編輯日期
	
	private String parent_name;		//父層名稱
	
}
