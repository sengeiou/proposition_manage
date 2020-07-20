package com.tkb.manage.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BankMaster extends Base {
	
	private String id;				//流水號
	private String uuid;			//uuid
	private String code;			//代碼
	private String name;			//名稱
	private String address;			//地址
	private String sort;			//排序
	private String display;			//顯示(0：否，1：是)
	
}
