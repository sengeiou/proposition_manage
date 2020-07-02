package com.tkb.manage.dao;

import java.util.Map;

import com.tkb.manage.model.ContractMaterialOption;

public interface ContractMaterialOptionDao {
	
	public void add(ContractMaterialOption contractMaterialOption);
	public void delete(ContractMaterialOption contractMaterialOption);
	public Map<String, Object> option(ContractMaterialOption contractMaterialOption);
	public Map<String, Object> subjectOption(ContractMaterialOption contractMaterialOption);
	public Map<String, Object> gradeOption(ContractMaterialOption contractMaterialOption);
	
}
