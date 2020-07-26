package com.tkb.manage.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tkb.manage.dao.MaterialTypeDao;
import com.tkb.manage.model.MaterialType;
import com.tkb.manage.service.MaterialTypeService;

@Service
public class MaterialTypeServiceImpl implements MaterialTypeService {
	
	@Autowired
	private MaterialTypeDao materialTypeDao;
	
	public List<Map<String, Object>> list(MaterialType materialType) {
		return materialTypeDao.list(materialType);
	}
	
	public Integer count(MaterialType materialType) {
		return materialTypeDao.count(materialType);
	}
	
	public MaterialType data(MaterialType materialType) {
		return materialTypeDao.data(materialType);
	}
	
	public int add(MaterialType materialType) {
		return materialTypeDao.add(materialType);
	}
	
	public void update(MaterialType materialType) {
		materialTypeDao.update(materialType);
	}
	
	public void delete(MaterialType materialType) {
		materialTypeDao.delete(materialType);
	}
	
	public List<Map<String, Object>> getChild(MaterialType materialType) {
		return materialTypeDao.getChild(materialType);
	}
	
	public Map<String, Object> backData(MaterialType materialType) {
		return materialTypeDao.backData(materialType);
	}
	
	public Integer maxSort(MaterialType materialType) {
		return materialTypeDao.maxSort(materialType);
	}
	
	public void updateSortByDelete(MaterialType materialType) {
		materialTypeDao.updateSortByDelete(materialType);
	}
	
	public List<Map<String, Object>> getList(MaterialType materialType) {
		return materialTypeDao.getList(materialType);
	}
	
	public Map<String, Object> searchName(MaterialType materialType) {
		return materialTypeDao.searchName(materialType);
	}
	
}
