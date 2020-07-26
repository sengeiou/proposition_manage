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
	private String mobile_phone;		//手機號碼
	private String telephone;			//市話
	private String bank;				//匯款銀行
	private String branch;				//匯款分行
	private String remittance_account;	//匯款帳號
	private String field_id;			//領域流水號
	private String education_id;		//學制流水號
	private String subject_id;			//學科流水號
	private String position;			//老師身份(1：一般老師，2：組長，3：校長)
	private String content_provision;	//內容提供(0：否，1：是)
	private String content_audit;		//內容審核(0：否，1：是)
	private String address_zip ;        //地址(郵遞區號)
	private String address_city;        //地址(縣市)
	private String address_area ;       //地址(區)
	private String address_road;        //地址(路)
	private String census_zip;          //戶籍(郵遞區號)
	private String census_city;         //戶籍(縣市)
	private String census_area;         //戶籍(區)
	private String census_road;         //戶籍(路)
	
	private String level;					//等級
	private String subject_name;			//學科名稱
	private String school_master_name;		//學校名稱
	private String education_name;			//學制名稱
	private String content_provision_name;	//內容提供名稱
	private String content_audit_name;		//內容審核名稱
	private String field_list;				//領域選取清單
	private String education_list;			//學制選取清單
	private String subject_list;			//學科選取清單
	private String status_texe;				//帳號狀態文字(0：關閉，1：開啟,2：待審核)
	
}
