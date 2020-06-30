package com.tkb.manage.model;

public class LessonPlan extends Base {
	
	private String id;						//流水號
	private String uuid;					//UUID
	private String contract_id;				//合約序號
	private String name;					//名稱
	private String field_id;				//學習領域流水號
	private String education_id;			//學制流水號
	private String course_length;			//教學時間
	private String author;					//設計者
	private String proofreader;				//指導者
	private String literacy;				//核心素養
	private String performance;				//學習重點-學習表現
	private String situation;				//學習重點-學習情境
	private String resource;				//學習重點-教學資源
	private String preparation;				//學習重點-教學準備
	private String capability;				//學習重點-先備知能
	private String content;					//學習重點-學習內容
	private String goal;					//學習目標
	private String display;					//前台顯示(0：不顯示，1：顯示)
	private String tag;						//關鍵字
	private String ctr;						//點擊率
	private String author_school;			//設計者學校
	private String proofreader_school;		//指導者學校
	private String course_count;			//教學堂數
	private String material;				//教材來源
	private String remark;					//備註
	private String text;					//範文篇目
	private String top;						//置頂(0：否，1：是)
	private String auditor;					//審核人
	private String file_status;				//檔案狀態(Y：已上傳，N：待修訂，C：完稿)
	private String upload_status;			//上傳狀態(Y：待審核，N：未通過，C：通過)
	private String create_by;				//創建者
	private String create_time;				//創建時間
	private String update_by;				//編輯者
	private String update_time;				//編輯時間
	
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
	public String getContract_id() {
		return contract_id;
	}
	public void setContract_id(String contract_id) {
		this.contract_id = contract_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getField_id() {
		return field_id;
	}
	public void setField_id(String field_id) {
		this.field_id = field_id;
	}
	public String getEducation_id() {
		return education_id;
	}
	public void setEducation_id(String education_id) {
		this.education_id = education_id;
	}
	public String getCourse_length() {
		return course_length;
	}
	public void setCourse_length(String course_length) {
		this.course_length = course_length;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getProofreader() {
		return proofreader;
	}
	public void setProofreader(String proofreader) {
		this.proofreader = proofreader;
	}
	public String getLiteracy() {
		return literacy;
	}
	public void setLiteracy(String literacy) {
		this.literacy = literacy;
	}
	public String getPerformance() {
		return performance;
	}
	public void setPerformance(String performance) {
		this.performance = performance;
	}
	public String getSituation() {
		return situation;
	}
	public void setSituation(String situation) {
		this.situation = situation;
	}
	public String getResource() {
		return resource;
	}
	public void setResource(String resource) {
		this.resource = resource;
	}
	public String getPreparation() {
		return preparation;
	}
	public void setPreparation(String preparation) {
		this.preparation = preparation;
	}
	public String getCapability() {
		return capability;
	}
	public void setCapability(String capability) {
		this.capability = capability;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getGoal() {
		return goal;
	}
	public void setGoal(String goal) {
		this.goal = goal;
	}
	public String getDisplay() {
		return display;
	}
	public void setDisplay(String display) {
		this.display = display;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public String getCtr() {
		return ctr;
	}
	public void setCtr(String ctr) {
		this.ctr = ctr;
	}
	public String getAuthor_school() {
		return author_school;
	}
	public void setAuthor_school(String author_school) {
		this.author_school = author_school;
	}
	public String getProofreader_school() {
		return proofreader_school;
	}
	public void setProofreader_school(String proofreader_school) {
		this.proofreader_school = proofreader_school;
	}
	public String getCourse_count() {
		return course_count;
	}
	public void setCourse_count(String course_count) {
		this.course_count = course_count;
	}
	public String getMaterial() {
		return material;
	}
	public void setMaterial(String material) {
		this.material = material;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getTop() {
		return top;
	}
	public void setTop(String top) {
		this.top = top;
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
