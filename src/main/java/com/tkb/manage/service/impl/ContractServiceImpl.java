package com.tkb.manage.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tkb.manage.dao.ContractDao;
import com.tkb.manage.model.Account;
import com.tkb.manage.model.Contract;
import com.tkb.manage.service.ContractService;

@Service
public class ContractServiceImpl implements ContractService {
	
	@Autowired
	private ContractDao contractDao;
	
	public List<Map<String, Object>> list(Contract contract) {
		return contractDao.list(contract);
	}
	
	public Integer count(Contract contract) {
		return contractDao.count(contract);
	}
	
	public Contract data(Contract contract) {
		return contractDao.data(contract);
	}
	
	public int add(Contract contract) {
		return contractDao.add(contract);
	}
	
	public void update(Contract contract) {
		contractDao.update(contract);
	}
	
	public void delete(Contract contract) {
		contractDao.delete(contract);
	}
	
	public Map<String, Object> uploadNum(Contract contract) {
		return contractDao.uploadNum(contract);
	}
	
	public Integer undoneCount(Contract contract) {
		return contractDao.undoneCount(contract);
	}
	
	public List<Map<String, Object>> getList(Contract contract) {
		return contractDao.getList(contract);
	}
	
	public Map<String, Object> callNum(Contract contract, Account account) {
		return contractDao.callNum(contract, account);
	}
	
//	public Integer allUndoneNum(Contract contract) {
//		return contractDao.allUndoneNum(contract);
//	}
//	
//	public Integer expired(Contract contract) {
//		return contractDao.expired(contract);
//	}
	
	public Map<String, Object> getDataByFieldEducation(Contract contract) {
		return contractDao.getDataByFieldEducation(contract);
	}
	
	public Map<String, Object> getDataByContractId(Contract contract) {
		return contractDao.getDataByContractId(contract);
	}
	
	public Map<String, Object> contractNum(Contract contract) {
		return contractDao.contractNum(contract);
	}
	
	public Map<String, Object> getSubjectEducation(Contract contract) {
		return contractDao.getSubjectEducation(contract);
	}
	
	public List<Map<String, Object>> getSubjectEducationByTeacher(Contract contract) {
		return contractDao.getSubjectEducationByTeacher(contract);
	}
	
	public Map<String, Object> getLessonPlanNum(String teacher, String education, String subject) {
		return contractDao.getLessonPlanNum(teacher, education, subject);
	}
	
	public Map<String, Object> getPropositionNum(String teacher, String education, String subject, String questionType) {
		return contractDao.getPropositionNum(teacher, education, subject, questionType);
	}
	
	public Map<String, Object> getLessonPlanProposition(Contract contract) {
		return contractDao.getLessonPlanProposition(contract);
	}
	
}
