package com.tkb.manage.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tkb.manage.dao.FieldDao;
import com.tkb.manage.model.Field;
import com.tkb.manage.service.FieldService;

@Service
public class FieldServiceImpl implements FieldService {
	
	@Autowired
	private FieldDao fieldDao;
	
	public List<Map<String, Object>> list(Field field) {
		return fieldDao.list(field);
	}
	
	public Integer count(Field field) {
		return fieldDao.count(field);
	}
	
	public Field data(Field field) {
		return fieldDao.data(field);
	}
	
	public int add(Field field) {
		return fieldDao.add(field);
	}
	
	public void update(Field field) {
		fieldDao.update(field);
	}
	
	public void delete(Field field) {
		fieldDao.delete(field);
	}
	
	public List<Map<String, Object>> getChild(Field field) {
		return fieldDao.getChild(field);
	}
	
	public Map<String, Object> backData(Field field) {
		return fieldDao.backData(field);
	}
	
	public Integer maxSort(Field field) {
		return fieldDao.maxSort(field);
	}
	
	public void updateSortByDelete(Field field) {
		fieldDao.updateSortByDelete(field);
	}
	
	public List<Map<String, Object>> getList(Field field) {
		return fieldDao.getList(field);
	}
	
	public Map<String, Object> searchName(Field field) {
		return fieldDao.searchName(field);
	}
	
	public List<Map<String, Object>> getListInId(Field field) {
		return fieldDao.getListInId(field);
	}
	
}
