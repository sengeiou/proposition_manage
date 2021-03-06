package com.tkb.manage.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LessonPlan extends Base {
	
	private String id;						//流水號
	private String uuid;					//UUID
	private String contract_id;				//合約序號
	private String lesson_plan_number;		//教案編號
	private String name;					//名稱
	private String education_id;			//學制流水號
	private String subject_id;				//學科流水號
	private String course_length;			//教學時間
	private String author;					//設計者
	private String proofreader;				//指導者
	private String literacy;				//核心素養
	private String performance;				//學習重點-學習表現
	private String situation;				//學習重點-學習情境
	private String resource;				//學習重點-教學資源
	private String preparation;				//學習重點-教學準備
	private String capability;				//學習重點-先備知能
	private String content;					//學習重點-學習內容
	private String goal;					//學習目標
	private String display;					//前台顯示(0：不顯示，1：顯示)
	private String tag;						//關鍵字
	private String ctr;						//點擊率
	private String author_school;			//設計者學校
	private String proofreader_school;		//指導者學校
	private String course_count;			//教學堂數
	private String material;				//教材來源
	private String remark;					//備註
	private String text;					//範文篇目
	private String top;						//置頂(0：否，1：是)
	private String auditor;					//審核人(校長)
	private String auditor2;				//審核人(審議委員)
	private String file_status;				//檔案狀態(A：初審中，B：初審待修正，C：初審通過 / 審核中，D：審核待修正，E：審核通過 / 完稿確認，F：完稿)
	private String education_name;			//學制名稱
	private String subject_name;			//學科名稱
	
}
