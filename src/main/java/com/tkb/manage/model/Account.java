package com.tkb.manage.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Account extends Base {
	
	private String id;				//流水號
	private String uuid;			//uuid
	private String account;			//帳號
	private String password;		//密碼
	private String name;			//姓名
	private String email;			//電子信箱
	private String identity_id;		//身份流水號
	private String status;			//帳號狀態(0：關閉，1：開啟)
	//管理者
	private String dept_no;			//部門代號
	private String dept_name;		//部門名稱
	private String unit_no;			//單位代號
	private String unit_name;		//單位名稱
	private String company_no;		//公司別
	//老師
	private String teacher_status;		//老師狀態(0：退休，1：在職)
	private String school_master_id;	//服務學校流水號
	private String id_no;				//身份證字號
	private String phone;				//手機號碼
	private String address;				//地址
	private String bank;				//匯款銀行
	private String branch;				//匯款分行
	private String remittance_account;	//匯款帳號
	private String field_id;			//領域流水號
	private String position;			//老師身份(1：一般老師，2：組長，3：校長)
	private String content_provision;	//內容提供(0：否，1：是)
	private String content_audit;		//內容審核(0：否，1：是)
	
	private String level;				//等級
	private String field_name;			//領域名稱
	private String school_master_name;	//學校名稱
	private String content_provision_name;	//內容提供名稱
	private String content_audit_name;		//內容審核名稱
	private String field_list;				//領域選取清單
	private String education_list;			//學制選取清單
	
}
