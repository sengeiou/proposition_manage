package com.tkb.manage.service;

import java.util.List;
import java.util.Map;

import com.tkb.manage.model.MaterialType;

public interface MaterialTypeService {
	
	public List<Map<String, Object>> list(MaterialType materialType);
	public Integer count(MaterialType materialType);
	public MaterialType data(MaterialType materialType);
	public int add(MaterialType materialType);
	public void update(MaterialType materialType);
	public void delete(MaterialType materialType);
	public List<Map<String, Object>> getChild(MaterialType materialType);
	public Map<String, Object> backData(MaterialType materialType);
	public Integer maxSort(MaterialType materialType);
	public void updateSortByDelete(MaterialType materialType);
	public List<Map<String, Object>> getList(MaterialType materialType);
	public Map<String, Object> searchName(MaterialType materialType);
	
}
