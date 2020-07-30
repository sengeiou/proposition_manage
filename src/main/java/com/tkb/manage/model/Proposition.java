package com.tkb.manage.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Proposition extends Base {
	
	private String id;						//流水號
	private String uuid;					//UUID
	private String contract_id;				//合約序號
	private String proposition_number;		//命題編號
	private String name;					//名稱
	private String education_id;			//學制流水號
	private String subject_id;				//學科流水號
	private String course_length;			//教學時間
	private String author;					//設計者
	private String author_school;			//設計者學校
	private String proofreader;				//指導者
	private String literacy;				//核心素養
	private String content;					//學習內容
	private String performance;				//學習表現
	private String goal;					//學習目標
	private String text;					//試題文本
	private String question;				//題目(題幹敘述)
	private String question_no;				//題號
	private String question_type;			//題型(1：基本題，2：題組題)
	private String question_category;		//題類
	private String subject;					//學習科目
	private String situation;				//情境範疇
	private String issue;					//議題範疇
	private String source;					//資料來源(引用出處)
	private String why;						//取材說明(why)
	private String display;					//前台顯示(0：不顯示，1：顯示)
	private String tag;						//關鍵字
	private String ctr;						//點擊率
	private String auditor;					//審核人(校長)
	private String auditor2;				//審核人(審議委員)
	private String file_status;				//檔案狀態(A：初審中，B：初審待修正，C：初審通過 / 審核中，D：審核待修正，E：審核通過 / 完稿確認，F：完稿)
	private String education_name;			//學制名稱
	private String subject_name;			//學科名稱
	
}
