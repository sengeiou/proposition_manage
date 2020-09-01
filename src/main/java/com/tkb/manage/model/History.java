package com.tkb.manage.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class History {
	
	private String id;				//流水號
	private String url;				//URL
	private String data;			//資料
	private String method;			//方法
	private String table_name;		//表名
	private String ip;				//IP
	private String create_by;		//創建者
	private String create_time;		//創建時間
	
	public static final String add = "ADD";			//新增
	public static final String delete = "DELETE";	//刪除
	public static final String update = "UPDATE";	//修改
    
}
