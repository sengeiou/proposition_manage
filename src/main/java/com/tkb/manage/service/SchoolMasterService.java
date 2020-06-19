package com.tkb.manage.service;

import java.util.List;
import java.util.Map;

import com.tkb.manage.model.SchoolMaster;

public interface SchoolMasterService {
	
	public List<Map<String, Object>> list(SchoolMaster schoolMaster);
	public Integer count(SchoolMaster schoolMaster);
	public SchoolMaster data(SchoolMaster schoolMaster);
	public int add(SchoolMaster schoolMaster);
	public void update(SchoolMaster schoolMaster);
	public void delete(SchoolMaster schoolMaster);
	public List<Map<String, Object>> getList(SchoolMaster schoolMaster);
	public Map<String, Object> searchName(SchoolMaster schoolMaster);
	
}
