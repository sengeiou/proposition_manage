package com.tkb.manage.model;

public class Education {
	
	private String id;				//流水號
	private String uuid;			//UUID
	private String name;			//名稱
	private String parent_id;		//父層流水號
	private String layer;			//層級
	private String sort;			//排序
	private String create_by;		//創建者
	private String create_time;		//創建時間
	private String update_by;		//編輯者
	private String update_time;		//編輯日期
	
	private String parent_name;		//父層名稱
	
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getParent_id() {
		return parent_id;
	}
	public void setParent_id(String parent_id) {
		this.parent_id = parent_id;
	}
	public String getLayer() {
		return layer;
	}
	public void setLayer(String layer) {
		this.layer = layer;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
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
	public String getParent_name() {
		return parent_name;
	}
	public void setParent_name(String parent_name) {
		this.parent_name = parent_name;
	}
	
}
