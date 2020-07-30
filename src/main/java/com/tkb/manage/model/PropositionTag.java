package com.tkb.manage.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PropositionTag extends Base {
	
	private String id;					//流水號
	private String proposition_id;		//命題流水號
	private String name;				//名稱
	
}
