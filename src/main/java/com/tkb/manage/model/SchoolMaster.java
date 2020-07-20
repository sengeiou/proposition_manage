package com.tkb.manage.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SchoolMaster extends Base {
	
	private String id;				//流水號
	private String uuid;			//uuid
	private String type;			//學制(1：國小、2：國中、3：高中、4：大專院校)
	private String code;			//代碼
	private String name;			//名稱
	private String school_type;		//公/私立(1：公立，2：私立)
	private String country_name;	//縣市名稱
	private String address;			//地址
	private String telphone;		//電話
	private String website;			//網址
	private String system_type;		//體系別(0：無、1：一般、2：技職、3：師範)
	private String display;			//顯示(0：否，1：是)
	
}
