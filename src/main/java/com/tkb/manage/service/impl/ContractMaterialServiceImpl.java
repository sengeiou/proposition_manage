package com.tkb.manage.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tkb.manage.dao.ContractMaterialDao;
import com.tkb.manage.model.Account;
import com.tkb.manage.model.ContractMaterial;
import com.tkb.manage.service.ContractMaterialService;

@Service
public class ContractMaterialServiceImpl implements ContractMaterialService {
	
	@Autowired
	private ContractMaterialDao contractMaterialDao;
	
	public List<Map<String, Object>> list(ContractMaterial contractMaterial) {
		return contractMaterialDao.list(contractMaterial);
	}
	
	public Integer count(ContractMaterial contractMaterial) {
		return contractMaterialDao.count(contractMaterial);
	}
	
	public ContractMaterial data(ContractMaterial contractMaterial) {
		return contractMaterialDao.data(contractMaterial);
	}
	
	public int add(ContractMaterial contractMaterial) {
		return contractMaterialDao.add(contractMaterial);
	}
	
	public void update(ContractMaterial contractMaterial) {
		contractMaterialDao.update(contractMaterial);
	}
	
	public void delete(ContractMaterial contractMaterial) {
		contractMaterialDao.delete(contractMaterial);
	}
	
	public void updateTeacherId(ContractMaterial contractMaterial) {
		contractMaterialDao.updateTeacherId(contractMaterial);
	}
	
	public Map<String, Object> uploadNum(ContractMaterial contractMaterial) {
		return contractMaterialDao.uploadNum(contractMaterial);
	}
	
	public Integer undoneCount(ContractMaterial contractMaterial) {
		return contractMaterialDao.undoneCount(contractMaterial);
	}
	
	public List<Map<String, Object>> getList(ContractMaterial contractMaterial) {
		return contractMaterialDao.getList(contractMaterial);
	}
	
	public Map<String, Object> callNum(ContractMaterial contractMaterial, Account account) {
		return contractMaterialDao.callNum(contractMaterial, account);
	}
	
	public Integer allUndoneNum(ContractMaterial contractMaterial) {
		return contractMaterialDao.allUndoneNum(contractMaterial);
	}
	
	public Integer expired(ContractMaterial contractMaterial) {
		return contractMaterialDao.expired(contractMaterial);
	}
	
	public Map<String, Object> contractNum() {
		return contractMaterialDao.contractNum();
	}
	
}
