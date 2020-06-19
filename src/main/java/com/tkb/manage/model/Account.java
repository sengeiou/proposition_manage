package com.tkb.manage.model;

public class Account extends Base {
	
	private String id;				//流水號
	private String uuid;			//uuid
	private String account;			//帳號
	private String password;		//密碼
	private String name;			//姓名
	private String email;			//電子信箱
	private String identity_id;		//身份流水號
	private String status;			//帳號狀態(0：關閉，1：開啟)
	private String create_by;		//創建者
	private String create_time;		//創建時間
	private String update_by;		//編輯者
	private String update_time;		//編輯時間
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
	private String content_provision;	//內容提供(0：否，1：是)
	private String content_audit;		//內容審核(0：否，1：是)
	
	private String level;				//等級
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getIdentity_id() {
		return identity_id;
	}
	public void setIdentity_id(String identity_id) {
		this.identity_id = identity_id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCreate_by() {
		return create_by;
	}
	public void setCreate_by(String create_by) {
		this.create_by = create_by;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getUpdate_by() {
		return update_by;
	}
	public void setUpdate_by(String update_by) {
		this.update_by = update_by;
	}
	public String getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}
	public String getDept_no() {
		return dept_no;
	}
	public void setDept_no(String dept_no) {
		this.dept_no = dept_no;
	}
	public String getDept_name() {
		return dept_name;
	}
	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}
	public String getUnit_no() {
		return unit_no;
	}
	public void setUnit_no(String unit_no) {
		this.unit_no = unit_no;
	}
	public String getUnit_name() {
		return unit_name;
	}
	public void setUnit_name(String unit_name) {
		this.unit_name = unit_name;
	}
	public String getCompany_no() {
		return company_no;
	}
	public void setCompany_no(String company_no) {
		this.company_no = company_no;
	}
	public String getTeacher_status() {
		return teacher_status;
	}
	public void setTeacher_status(String teacher_status) {
		this.teacher_status = teacher_status;
	}
	public String getSchool_master_id() {
		return school_master_id;
	}
	public void setSchool_master_id(String school_master_id) {
		this.school_master_id = school_master_id;
	}
	public String getId_no() {
		return id_no;
	}
	public void setId_no(String id_no) {
		this.id_no = id_no;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getBank() {
		return bank;
	}
	public void setBank(String bank) {
		this.bank = bank;
	}
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
	public String getRemittance_account() {
		return remittance_account;
	}
	public void setRemittance_account(String remittance_account) {
		this.remittance_account = remittance_account;
	}
	public String getField_id() {
		return field_id;
	}
	public void setField_id(String field_id) {
		this.field_id = field_id;
	}
	public String getContent_provision() {
		return content_provision;
	}
	public void setContent_provision(String content_provision) {
		this.content_provision = content_provision;
	}
	public String getContent_audit() {
		return content_audit;
	}
	public void setContent_audit(String content_audit) {
		this.content_audit = content_audit;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	
}
