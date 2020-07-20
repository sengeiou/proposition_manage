package com.tkb.manage.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LoginLog {
	
	private String id;				//流水號
	private String account;			//帳號
	private String password;		//密碼
	private String status;			//登入狀態(0：失敗，1：成功)
	private String msg;				//登入訊息
	private String type;			//類別(1：管理者，2：老師)
	private String ip;				//ip
	private String create_time;		//登入時間
	
}
