package com.tkb.manage.service;

import java.util.List;
import java.util.Map;

import com.tkb.manage.model.BankMaster;

public interface BankMasterService {
	
	public List<Map<String, Object>> list(BankMaster bankMaster);
	public Integer count(BankMaster bankMaster);
	public BankMaster data(BankMaster bankMaster);
	public int add(BankMaster bankMaster);
	public void update(BankMaster bankMaster);
	public void delete(BankMaster bankMaster);
	public List<Map<String, Object>> getList(BankMaster bankMaster);
	public Integer nextSort();
	
}
