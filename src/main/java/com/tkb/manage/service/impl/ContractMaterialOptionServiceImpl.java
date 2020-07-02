package com.tkb.manage.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tkb.manage.dao.ContractMaterialOptionDao;
import com.tkb.manage.model.ContractMaterialOption;
import com.tkb.manage.service.ContractMaterialOptionService;

@Service
public class ContractMaterialOptionServiceImpl implements ContractMaterialOptionService {
	
	@Autowired
	private ContractMaterialOptionDao contractMaterialOptionDao;
	
	public void add(ContractMaterialOption contractMaterialOption) {
		contractMaterialOptionDao.add(contractMaterialOption);
	}
	
	public void delete(ContractMaterialOption contractMaterialOption) {
		contractMaterialOptionDao.delete(contractMaterialOption);
	}
	
	public Map<String, Object> option(ContractMaterialOption contractMaterialOption) {
		return contractMaterialOptionDao.option(contractMaterialOption);
	}
	
	public Map<String, Object> subjectOption(ContractMaterialOption contractMaterialOption) {
		return contractMaterialOptionDao.subjectOption(contractMaterialOption);
	}
	
	public Map<String, Object> gradeOption(ContractMaterialOption contractMaterialOption) {
		return contractMaterialOptionDao.gradeOption(contractMaterialOption);
	}
	
}
