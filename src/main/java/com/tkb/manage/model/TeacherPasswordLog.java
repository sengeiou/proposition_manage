package com.tkb.manage.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TeacherPasswordLog {
	
	private String id;					//流水號
	private String teacher_account_id;	//老師帳號流水號
	private String account;				//帳號
	private String old_password;		//舊密碼
	private String new_password;		//新密碼
	private String ip;					//ip
	private String verify_code;			//驗證碼
	private String verify_status;		//認證狀態(0：不需驗證，1：待驗證，2：驗證成功)
	private String verify_count;		//認證次數(目前無使用)
	private String verify_time;			//認證時間
	private String create_time;			//創建時間
	private String from_type;			//來源類別(1.修改密碼,2.忘記密碼)
	
}
