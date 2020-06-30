package com.tkb.manage.model;

public class LessonPlanAudit {
	
	private String id;					//流水號
	private String lesson_plan_id;		//教案流水號
	private String auditor;				//審核人
	private String file_status;			//檔案狀態(y：已上傳，n：待修訂，c：完稿)
	private String upload_status;		//上傳狀態(y：待審核，n：未通過，c：通過)
	private String create_by;			//創建者
	private String create_time;			//創建時間
	private String update_by;			//編輯者
	private String update_time;			//編輯時間
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLesson_plan_id() {
		return lesson_plan_id;
	}
	public void setLesson_plan_id(String lesson_plan_id) {
		this.lesson_plan_id = lesson_plan_id;
	}
	public String getAuditor() {
		return auditor;
	}
	public void setAuditor(String auditor) {
		this.auditor = auditor;
	}
	public String getFile_status() {
		return file_status;
	}
	public void setFile_status(String file_status) {
		this.file_status = file_status;
	}
	public String getUpload_status() {
		return upload_status;
	}
	public void setUpload_status(String upload_status) {
		this.upload_status = upload_status;
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
	
}
