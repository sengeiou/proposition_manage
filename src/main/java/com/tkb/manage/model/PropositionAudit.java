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
	private String version;				//版本
	private String file_status;			//檔案狀態(A：初審中，B：初審待修正，C：初審通過 / 審核中，D：審核待修正，E：審核通過 / 完稿確認，F：完稿)
	private String audit_feedback;		//審核回饋
	
}
