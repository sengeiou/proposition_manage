package com.tkb.manage.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tkb.manage.dao.BankMasterDao;
import com.tkb.manage.model.BankMaster;
import com.tkb.manage.service.BankMasterService;

@Service
public class BankMasterServiceImpl implements BankMasterService {
	
	@Autowired
	private BankMasterDao bankMasterDao;
	
	public List<Map<String, Object>> list(BankMaster bankMaster) {
		return bankMasterDao.list(bankMaster);
	}
	
	public Integer count(BankMaster bankMaster) {
		return bankMasterDao.count(bankMaster);
	}
	
	public BankMaster data(BankMaster bankMaster) {
		return bankMasterDao.data(bankMaster);
	}
	
	public int add(BankMaster bankMaster) {
		return bankMasterDao.add(bankMaster);
	}
	
	public void update(BankMaster bankMaster) {
		bankMasterDao.update(bankMaster);
	}
	
	public void delete(BankMaster bankMaster) {
		bankMasterDao.delete(bankMaster);
	}
	
	public List<Map<String, Object>> getList(BankMaster bankMaster) {
		return bankMasterDao.getList(bankMaster);
	}
	
	public Integer nextSort() {
		return bankMasterDao.nextSort();
	}
	
}
