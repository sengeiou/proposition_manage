package com.tkb.manage.dao;

import java.util.List;
import java.util.Map;

import com.tkb.manage.model.Field;

public interface FieldDao {
	
	public List<Map<String, Object>> list(Field field);
	public Integer count(Field field);
	public Field data(Field field);
	public int add(Field field);
	public void update(Field field);
	public void delete(Field field);
	public List<Map<String, Object>> getChild(Field field);
	public Map<String, Object> backData(Field field);
	public Integer maxSort(Field field);
	public void updateSortByDelete(Field field);
	public List<Map<String, Object>> getList(Field field);
	public Map<String, Object> searchName(Field field);
	public List<Map<String, Object>> getListInId(Field field);
	
}
