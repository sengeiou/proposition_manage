package com.tkb.manage.dao;

import java.util.List;
import java.util.Map;

import com.tkb.manage.model.Subject;

public interface SubjectDao {
	
	public List<Map<String, Object>> list(Subject subject);
	public Integer count(Subject subject);
	public Subject data(Subject subject);
	public int add(Subject subject);
	public void update(Subject subject);
	public void delete(Subject subject);
	public List<Map<String, Object>> getChild(Subject subject);
	public Map<String, Object> backData(Subject subject);
	public Integer maxSort(Subject subject);
	public void updateSortByDelete(Subject subject);
	public List<Map<String, Object>> getList(Subject subject);
	public Map<String, Object> searchName(Subject subject);
	public List<Map<String, Object>> getListInId(Subject subject);
	public String getName(Subject subject);
	
}
