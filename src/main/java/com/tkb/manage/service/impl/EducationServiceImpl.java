package com.tkb.manage.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tkb.manage.dao.EducationDao;
import com.tkb.manage.model.Education;
import com.tkb.manage.service.EducationService;

@Service
public class EducationServiceImpl implements EducationService {
	
	@Autowired
	private EducationDao educationDao;
	
	public List<Map<String, Object>> list(Education education) {
		return educationDao.list(education);
	}
	
	public Integer count(Education education) {
		return educationDao.count(education);
	}
	
	public Education data(Education education) {
		return educationDao.data(education);
	}
	
	public int add(Education education) {
		return educationDao.add(education);
	}
	
	public void update(Education education) {
		educationDao.update(education);
	}
	
	public void delete(Education education) {
		educationDao.delete(education);
	}
	
	public List<Map<String, Object>> getChild(Education education) {
		return educationDao.getChild(education);
	}
	
	public Map<String, Object> backData(Education education) {
		return educationDao.backData(education);
	}
	
	public Integer maxSort(Education education) {
		return educationDao.maxSort(education);
	}
	
	public void updateSortByDelete(Education education) {
		educationDao.updateSortByDelete(education);
	}
	
	public List<Map<String, Object>> getList(Education education) {
		return educationDao.getList(education);
	}
	
	public Map<String, Object> searchName(Education education) {
		return educationDao.searchName(education);
	}
	
}
