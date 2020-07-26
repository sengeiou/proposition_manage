package com.tkb.manage.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LessonPlanTag extends Base {
	
	private String id;					//流水號
	private String lesson_plan_id;		//教案流水號
	private String name;				//名稱
	
}
