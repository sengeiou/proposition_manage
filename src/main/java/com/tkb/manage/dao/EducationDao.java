package com.tkb.manage.dao;

import java.util.List;
import java.util.Map;

import com.tkb.manage.model.Education;

public interface EducationDao {
	
	public List<Map<String, Object>> list(Education education);
	public Integer count(Education education);
	public Education data(Education education);
	public int add(Education education);
	public void update(Education education);
	public void delete(Education education);
	public List<Map<String, Object>> getChild(Education education);
	public Map<String, Object> backData(Education education);
	public Integer maxSort(Education education);
	public void updateSortByDelete(Education education);
	public List<Map<String, Object>> getList(Education education);
	public Map<String, Object> searchName(Education education);
	
}
