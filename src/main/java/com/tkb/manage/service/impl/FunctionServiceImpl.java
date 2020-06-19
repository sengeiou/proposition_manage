package com.tkb.manage.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tkb.manage.dao.FunctionDao;
import com.tkb.manage.model.Function;
import com.tkb.manage.service.FunctionService;

@Service
public class FunctionServiceImpl implements FunctionService {
	
	@Autowired
	private FunctionDao functionDao;
	
	public List<Map<String, Object>> list(Function function) {
		return functionDao.list(function);
	}
	
	public Integer count(Function function) {
		return functionDao.count(function);
	}
	
	public Function data(Function function) {
		return functionDao.data(function);
	}
	
	public int add(Function function) {
		return functionDao.add(function);
	}
	
	public void update(Function function) {
		functionDao.update(function);
	}
	
	public void delete(Function function) {
		functionDao.delete(function);
	}
	
	public List<Map<String, Object>> getChild(Function function) {
		return functionDao.getChild(function);
	}
	
	public Map<String, Object> backData(Function function) {
		return functionDao.backData(function);
	}
	
	public Integer maxSort(Function function) {
		return functionDao.maxSort(function);
	}
	
	public void updateSortByDelete(Function function) {
		functionDao.updateSortByDelete(function);
	}
	
	public List<Map<String, Object>> getMenu(Function function) {
		return functionDao.getMenu(function);
	}
	
}
