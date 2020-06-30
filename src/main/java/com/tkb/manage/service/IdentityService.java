package com.tkb.manage.service;

import java.util.List;
import java.util.Map;

import com.tkb.manage.model.Identity;

public interface IdentityService {
	
	public List<Map<String, Object>> list(Identity identity);
	public Integer count(Identity identity);
	public Identity data(Identity identity);
	public int add(Identity identity);
	public void update(Identity identity);
	public void delete(Identity identity);
	public List<Map<String, Object>> getChild(Identity identity);
	public Map<String, Object> backData(Identity identity);
	public Integer maxSort(Identity identity);
	public void updateSortByDelete(Identity identity);
	public List<Map<String, Object>> getMenu(Identity identity);
	public Map<String, Object> getDataByLevel(Identity identity);
	
}
