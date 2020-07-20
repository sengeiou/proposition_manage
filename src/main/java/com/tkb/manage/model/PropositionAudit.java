package com.tkb.manage.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PropositionAudit extends Base {
	
	private String id;					//流水號
	private String proposition_id;		//命題流水號
	private String auditor;				//審核人
	private String file_status;			//檔案狀態(y：已上傳，n：待修訂，c：完稿)
	private String upload_status;		//上傳狀態(y：待審核，n：未通過，c：通過)
	
}
