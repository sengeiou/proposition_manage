package com.tkb.manage.service;

import java.util.List;
import java.util.Map;

import com.tkb.manage.model.Function;

public interface FunctionService {
	
	public List<Map<String, Object>> list(Function function);
	public Integer count(Function function);
	public Function data(Function function);
	public int add(Function function);
	public void update(Function function);
	public void delete(Function function);
	public List<Map<String, Object>> getChild(Function function);
	public Map<String, Object> backData(Function function);
	public Integer maxSort(Function function);
	public void updateSortByDelete(Function function);
	public List<Map<String, Object>> getMenu(Function function);
	
}
