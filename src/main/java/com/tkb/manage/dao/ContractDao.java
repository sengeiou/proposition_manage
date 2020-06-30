package com.tkb.manage.dao;

import java.util.List;
import java.util.Map;

import com.tkb.manage.model.Account;
import com.tkb.manage.model.Contract;

public interface ContractDao {
	
	public List<Map<String, Object>> list(Contract contract);
	public Integer count(Contract contract);
	public Contract data(Contract contract);
	public int add(Contract contract);
	public void update(Contract contract);
	public void delete(Contract contract);
	public void updateTeacherId(Contract contract);
	public Map<String, Object> uploadNum(Contract contract);
	public Integer undoneCount(Contract contract);
	public List<Map<String, Object>> getList(Contract contract);
	public Map<String, Object> callNum(Contract contract, Account account);
	public Integer allUndoneNum(Contract contract);
	public Integer expired(Contract contract);
	
}
