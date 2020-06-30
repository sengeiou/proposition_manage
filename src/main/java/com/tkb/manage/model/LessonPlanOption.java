package com.tkb.manage.model;

public class LessonPlanOption {
	
	private String id;					//流水號
	private String lesson_plan_id;		//教案流水號
	private String type;				//類別(1：學習領域，2：學科，3：學制，4：年級)
	private String code;				//選項流水號
	private String create_by;			//創建者
	private String create_time;			//創建時間
	
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
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
    
}
