package com.tkb.manage.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ContractMaterialOption {
	
	private String id;							//流水號
	private String contract_material_id;		//素材授權流水號
	private String material_type;				//素材類型(1：圖片，2：文本，3：影片，4：音檔)
	private String create_by;					//創建者
	private String create_time;					//創建時間
	
}
