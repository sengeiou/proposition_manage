package com.tkb.manage.dao;

import java.util.List;
import java.util.Map;

import com.tkb.manage.model.Account;
import com.tkb.manage.model.ContractMaterial;

public interface ContractMaterialDao {
	
	public List<Map<String, Object>> list(ContractMaterial contractMaterial);
	public Integer count(ContractMaterial contractMaterial);
	public ContractMaterial data(ContractMaterial contractMaterial);
	public int add(ContractMaterial contractMaterial);
	public void update(ContractMaterial contractMaterial);
	public void delete(ContractMaterial contractMaterial);
	public void updateTeacherId(ContractMaterial contractMaterial);
	public Map<String, Object> uploadNum(ContractMaterial contractMaterial);
	public Integer undoneCount(ContractMaterial contractMaterial);
	public List<Map<String, Object>> getList(ContractMaterial contractMaterial);
	public Map<String, Object> callNum(ContractMaterial contractMaterial, Account account);
	public Integer allUndoneNum(ContractMaterial contractMaterial);
	public Integer expired(ContractMaterial contractMaterial);
	public Map<String, Object> contractNum();
	
}
