package com.tkb.manage.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LessonPlanOption {
	
	private String id;					//流水號
	private String lesson_plan_id;		//教案流水號
	private String type;				//類別(1：學科，2：跨科，3：學制，4：年級)
	private String code;				//選項流水號
	private String create_by;			//創建者
	private String create_time;			//創建時間
	
}
