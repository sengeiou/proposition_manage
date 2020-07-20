package com.tkb.manage.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Identity extends Base {
	
	private String id;				//流水號
	private String uuid;			//uuid
	private String name;			//名稱
	private String parent_id;		//父層流水號
	private String layer;			//層級
	private String sort;			//排序
	private String level;			//等級
	
	private String parent_name;		//父層名稱
	
}
