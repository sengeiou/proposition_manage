package com.tkb.manage.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tkb.manage.dao.SchoolMasterDao;
import com.tkb.manage.model.SchoolMaster;
import com.tkb.manage.service.SchoolMasterService;

@Service
public class SchoolMasterServiceImpl implements SchoolMasterService {
	
	@Autowired
	private SchoolMasterDao schoolMasterDao;
	
	public List<Map<String, Object>> list(SchoolMaster schoolMaster) {
		return schoolMasterDao.list(schoolMaster);
	}
	
	public Integer count(SchoolMaster schoolMaster) {
		return schoolMasterDao.count(schoolMaster);
	}
	
	public SchoolMaster data(SchoolMaster schoolMaster) {
		return schoolMasterDao.data(schoolMaster);
	}
	
	public int add(SchoolMaster schoolMaster) {
		return schoolMasterDao.add(schoolMaster);
	}
	
	public void update(SchoolMaster schoolMaster) {
		schoolMasterDao.update(schoolMaster);
	}
	
	public void delete(SchoolMaster schoolMaster) {
		schoolMasterDao.delete(schoolMaster);
	}
	
	public List<Map<String, Object>> getList(SchoolMaster schoolMaster) {
		return schoolMasterDao.getList(schoolMaster);
	}
	
	public Map<String, Object> searchName(SchoolMaster schoolMaster) {
		return schoolMasterDao.searchName(schoolMaster);
	}
	
}
