package com.tkb.manage.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tkb.manage.dao.SubjectDao;
import com.tkb.manage.model.Subject;
import com.tkb.manage.service.SubjectService;

@Service
public class SubjectServiceImpl implements SubjectService {
	
	@Autowired
	private SubjectDao subjectDao;
	
	public List<Map<String, Object>> list(Subject subject) {
		return subjectDao.list(subject);
	}
	
	public Integer count(Subject subject) {
		return subjectDao.count(subject);
	}
	
	public Subject data(Subject subject) {
		return subjectDao.data(subject);
	}
	
	public int add(Subject subject) {
		return subjectDao.add(subject);
	}
	
	public void update(Subject subject) {
		subjectDao.update(subject);
	}
	
	public void delete(Subject subject) {
		subjectDao.delete(subject);
	}
	
	public List<Map<String, Object>> getChild(Subject subject) {
		return subjectDao.getChild(subject);
	}
	
	public Map<String, Object> backData(Subject subject) {
		return subjectDao.backData(subject);
	}
	
	public Integer maxSort(Subject subject) {
		return subjectDao.maxSort(subject);
	}
	
	public void updateSortByDelete(Subject subject) {
		subjectDao.updateSortByDelete(subject);
	}
	
	public List<Map<String, Object>> getList(Subject subject) {
		return subjectDao.getList(subject);
	}
	
	public Map<String, Object> searchName(Subject subject) {
		return subjectDao.searchName(subject);
	}
	
	public List<Map<String, Object>> getListInId(Subject subject) {
		return subjectDao.getListInId(subject);
	}
	
}
