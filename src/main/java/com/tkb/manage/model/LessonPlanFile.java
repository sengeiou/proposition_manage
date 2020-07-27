package com.tkb.manage.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LessonPlanFile extends Base {
	
	private String id;					//流水號
	private String lesson_plan_id;		//教案流水號
	private String data_type;			//資料類型(1：Word檔案，2：PDF檔案，3：附件，4：文本授權檔案，5：審核回饋檔案)
	private String material_type_id;	//素材分類流水號
	private String material_link;		//素材超連結
	private String version;				//版本
	private String file_status;			//檔案狀態(A：初審中，B：初審待修正，C：初審通過 / 審核中，D：審核待修正，E：審核通過 / 完稿確認，F：完稿)
	private String pdf_page;			//完整版教案頁數
	private String upload_name;			//上傳檔名
	private String file_name;			//下載檔名
	private String downloads;			//下載次數
	private String display;				//前台顯示(0：不顯示，1：顯示)
	
}
