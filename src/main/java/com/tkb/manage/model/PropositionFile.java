package com.tkb.manage.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PropositionFile extends Base {
	
	private String id;					//流水號
	private String proposition_id;		//命題流水號
	private String type;				//檔案類型(1：教案，2：意見回饋)
	private String pdf_page;			//完整版教案頁數
	private String upload_name;			//上傳檔名
	private String file_name;			//下載檔名
	private String downloads;			//下載次數
	private String display;				//前台顯示(0：不顯示，1：顯示)
	
}
