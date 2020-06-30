package com.tkb.manage.model;

public class PropositionFile {
	
	private String id;					//流水號
	private String proposition_id;		//命題流水號
	private String type;				//檔案類型(1：教案，2：意見回饋)
	private String pdf_page;			//完整版教案頁數
	private String upload_name;			//上傳檔名
	private String file_name;			//下載檔名
	private String downloads;			//下載次數
	private String display;				//前台顯示(0：不顯示，1：顯示)
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
	public String getProposition_id() {
		return proposition_id;
	}
	public void setProposition_id(String proposition_id) {
		this.proposition_id = proposition_id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getPdf_page() {
		return pdf_page;
	}
	public void setPdf_page(String pdf_page) {
		this.pdf_page = pdf_page;
	}
	public String getUpload_name() {
		return upload_name;
	}
	public void setUpload_name(String upload_name) {
		this.upload_name = upload_name;
	}
	public String getFile_name() {
		return file_name;
	}
	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}
	public String getDownloads() {
		return downloads;
	}
	public void setDownloads(String downloads) {
		this.downloads = downloads;
	}
	public String getDisplay() {
		return display;
	}
	public void setDisplay(String display) {
		this.display = display;
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
