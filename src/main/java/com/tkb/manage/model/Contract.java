package com.tkb.manage.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Contract extends Base {
	
	private String id;						//流水號
	private String uuid;					//UUID
	private String contract_id;				//合約序號
	private String teacher_id;				//老師帳號流水號
	private String tkb_contract_num;		//合約編號(台灣知識庫合約)
	private String tkb_contract_file;		//合約檔案(台灣知識庫合約)
	private String tkb_contract_name;		//合約檔案名稱(台灣知識庫合約)
	private String tkb_partya;				//甲方(台灣知識庫合約)
	private String tkb_partyb;				//乙方(台灣知識庫合約)
	private String csofe_contract_num;		//合約編號(中華未來教育協會合約)
	private String csofe_contract_file;		//合約檔案(中華未來教育協會合約)
	private String csofe_contract_name;		//合約檔案名稱(中華未來教育協會合約)
	private String csofe_partya;			//甲方(中華未來教育協會合約)
	private String csofe_partyb;			//乙方(中華未來教育協會合約)
	private String education_id;			//學制流水號
	private String subject_id;				//學科流水號
	private String begin_date;				//合約生效日期
	private String end_date;				//合約結束日期
	private String lesson_num;				//教案授權數量
	private String basic_num;				//命題基本題授權數量
	private String questions_group_num;		//命題題組題授權數量
	
	private String education_name;			//學制名稱
	private String subject_name;			//學科名稱
	
}
