package com.tkb.manage.model;

public class ContractMaterialOption {
	
	private String id;							//流水號
	private String contract_material_id;		//素材授權流水號
	private String material_type;				//素材類型(1：圖片，2：文本，3：影片，4：音檔)
	private String create_by;					//創建者
	private String create_time;					//創建時間
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getContract_material_id() {
		return contract_material_id;
	}
	public void setContract_material_id(String contract_material_id) {
		this.contract_material_id = contract_material_id;
	}
	public String getMaterial_type() {
		return material_type;
	}
	public void setMaterial_type(String material_type) {
		this.material_type = material_type;
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
