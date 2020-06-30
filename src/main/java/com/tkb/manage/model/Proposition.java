package com.tkb.manage.model;

public class Proposition extends Base {
	
	private String id;					//流水號
	private String uuid;				//UUID
	private String contract_id;			//合約序號
	private String name;				//名稱
	private String field_id;			//學習領域流水號
	private String education_id;		//學制流水號
	private String course_length;		//教學時間
	private String author;				//設計者
	private String author_school;		//設計者學校
	private String proofreader;			//指導者
	private String literacy;			//核心素養
	private String content;				//學習內容
	private String performance;			//學習表現
	private String goal;				//學習目標
	private String text;				//試題文本
	private String question;			//題目(題幹敘述)
	private String question_no;			//題號
	private String question_type;		//題型(1：基本題，2：題組題)
	private String question_category;	//題類
	private String subject;				//學習科目
	private String situation;			//情境範疇
	private String issue;				//議題範疇
	private String source;				//資料來源(引用出處)
	private String why;					//取材說明(why)
	private String tag;					//關鍵字
	private String display;				//前台顯示(0：不顯示，1：顯示)
	private String auditor;				//審核人
	private String file_status;			//檔案狀態(Y：已上傳，N：待修訂，C：完稿)
	private String upload_status;		//上傳狀態(Y：待審核，N：未通過，C：通過)
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
	public String getAuthor_school() {
		return author_school;
	}
	public void setAuthor_school(String author_school) {
		this.author_school = author_school;
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
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getPerformance() {
		return performance;
	}
	public void setPerformance(String performance) {
		this.performance = performance;
	}
	public String getGoal() {
		return goal;
	}
	public void setGoal(String goal) {
		this.goal = goal;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getQuestion_no() {
		return question_no;
	}
	public void setQuestion_no(String question_no) {
		this.question_no = question_no;
	}
	public String getQuestion_type() {
		return question_type;
	}
	public void setQuestion_type(String question_type) {
		this.question_type = question_type;
	}
	public String getQuestion_category() {
		return question_category;
	}
	public void setQuestion_category(String question_category) {
		this.question_category = question_category;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getSituation() {
		return situation;
	}
	public void setSituation(String situation) {
		this.situation = situation;
	}
	public String getIssue() {
		return issue;
	}
	public void setIssue(String issue) {
		this.issue = issue;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getWhy() {
		return why;
	}
	public void setWhy(String why) {
		this.why = why;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public String getDisplay() {
		return display;
	}
	public void setDisplay(String display) {
		this.display = display;
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
