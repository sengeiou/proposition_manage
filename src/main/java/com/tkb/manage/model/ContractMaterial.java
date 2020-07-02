package com.tkb.manage.model;

public class ContractMaterial extends Base {
	
	private String id;						//流水號
	private String uuid;					//UUID
	private String contract_id;				//合約序號
	private String teacher_id;				//老師帳號流水號
	private String tkb_contract_num;		//合約編號(台灣知識庫合約)
	private String tkb_contract_file;		//合約檔案(台灣知識庫合約)
	private String tkb_contract_name;		//合約檔案名稱(台灣知識庫合約)
	private String tkb_partya;				//甲方(台灣知識庫合約)
	private String tkb_partyb;				//乙方(台灣知識庫合約)
	private String csofe_contract_num;		//合約編號(中華未來教育協會合約)
	private String csofe_contract_file;		//合約檔案(中華未來教育協會合約)
	private String csofe_contract_name;		//合約檔案名稱(中華未來教育協會合約)
	private String csofe_partya;			//甲方(中華未來教育協會合約)
	private String csofe_partyb;			//乙方(中華未來教育協會合約)
	private String lp_id;					//教案命題流水號
	private String lp_type;					//教案命題類別(1：教案，2：基本題，3：題組題)
	private String field_id;				//領域流水號
	private String education_id;			//學制流水號
	private String begin_date;				//合約生效日期
	private String end_date;				//合約結束日期
	private String lesson_num;				//教案授權數量
	private String basic_num;				//命題基本題授權數量
	private String questions_group_num;		//命題題組題授權數量
	private String create_by;				//創建者
	private String create_time;				//創建時間
	private String update_by;				//編輯者
	private String update_time;				//編輯時間
	
	private String teacher_name;			//老師名稱
	private String field_name;				//領域名稱
	private String education_name;			//學制名稱
	private String name;					//教案命題名稱
	private String lp_type_name;			//教案命題類別名稱
	private String material_type_name;		//素材類別名稱
	
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
	public String getTeacher_id() {
		return teacher_id;
	}
	public void setTeacher_id(String teacher_id) {
		this.teacher_id = teacher_id;
	}
	public String getTkb_contract_num() {
		return tkb_contract_num;
	}
	public void setTkb_contract_num(String tkb_contract_num) {
		this.tkb_contract_num = tkb_contract_num;
	}
	public String getTkb_contract_file() {
		return tkb_contract_file;
	}
	public void setTkb_contract_file(String tkb_contract_file) {
		this.tkb_contract_file = tkb_contract_file;
	}
	public String getTkb_contract_name() {
		return tkb_contract_name;
	}
	public void setTkb_contract_name(String tkb_contract_name) {
		this.tkb_contract_name = tkb_contract_name;
	}
	public String getTkb_partya() {
		return tkb_partya;
	}
	public void setTkb_partya(String tkb_partya) {
		this.tkb_partya = tkb_partya;
	}
	public String getTkb_partyb() {
		return tkb_partyb;
	}
	public void setTkb_partyb(String tkb_partyb) {
		this.tkb_partyb = tkb_partyb;
	}
	public String getCsofe_contract_num() {
		return csofe_contract_num;
	}
	public void setCsofe_contract_num(String csofe_contract_num) {
		this.csofe_contract_num = csofe_contract_num;
	}
	public String getCsofe_contract_file() {
		return csofe_contract_file;
	}
	public void setCsofe_contract_file(String csofe_contract_file) {
		this.csofe_contract_file = csofe_contract_file;
	}
	public String getCsofe_contract_name() {
		return csofe_contract_name;
	}
	public void setCsofe_contract_name(String csofe_contract_name) {
		this.csofe_contract_name = csofe_contract_name;
	}
	public String getCsofe_partya() {
		return csofe_partya;
	}
	public void setCsofe_partya(String csofe_partya) {
		this.csofe_partya = csofe_partya;
	}
	public String getCsofe_partyb() {
		return csofe_partyb;
	}
	public void setCsofe_partyb(String csofe_partyb) {
		this.csofe_partyb = csofe_partyb;
	}
	public String getLp_id() {
		return lp_id;
	}
	public void setLp_id(String lp_id) {
		this.lp_id = lp_id;
	}
	public String getLp_type() {
		return lp_type;
	}
	public void setLp_type(String lp_type) {
		this.lp_type = lp_type;
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
	public String getBegin_date() {
		return begin_date;
	}
	public void setBegin_date(String begin_date) {
		this.begin_date = begin_date;
	}
	public String getEnd_date() {
		return end_date;
	}
	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}
	public String getLesson_num() {
		return lesson_num;
	}
	public void setLesson_num(String lesson_num) {
		this.lesson_num = lesson_num;
	}
	public String getBasic_num() {
		return basic_num;
	}
	public void setBasic_num(String basic_num) {
		this.basic_num = basic_num;
	}
	public String getQuestions_group_num() {
		return questions_group_num;
	}
	public void setQuestions_group_num(String questions_group_num) {
		this.questions_group_num = questions_group_num;
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
	public String getTeacher_name() {
		return teacher_name;
	}
	public void setTeacher_name(String teacher_name) {
		this.teacher_name = teacher_name;
	}
	public String getField_name() {
		return field_name;
	}
	public void setField_name(String field_name) {
		this.field_name = field_name;
	}
	public String getEducation_name() {
		return education_name;
	}
	public void setEducation_name(String education_name) {
		this.education_name = education_name;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLp_type_name() {
		return lp_type_name;
	}
	public void setLp_type_name(String lp_type_name) {
		this.lp_type_name = lp_type_name;
	}
	public String getMaterial_type_name() {
		return material_type_name;
	}
	public void setMaterial_type_name(String material_type_name) {
		this.material_type_name = material_type_name;
	}
	
}
