package com.tkb.manage.service;

import java.util.List;
import java.util.Map;

import com.tkb.manage.model.Account;

public interface TeacherAccountService {
	
	public Account login(Account account);
	public List<Map<String, Object>> list(Account account);
	public Integer count(Account account);
	public Account data(Account account);
	public int add(Account account);
	public void update(Account account);
	public void delete(Account account);
	public void updateIdentity(Account account);
	public Map<String, Object> getDataByAccount(Account account);
	public Map<String, Object> callNum(Account account);
	public Map<String, Object> getAuditor(Account account);
	public List<Map<String, Object>> verifyList(Account account);
	public List<Map<String, Object>> getAuditorByEduSub(Account account);
	public Integer checkAccount(String id_no,String id);
	public void audit(Account account);
	public Account dataByAccount(Account account);
	public void updateVerify(Account account);
	public void updatePassword(Account account);
	public List<Map<String, Object>> managerList(Account account);
}
