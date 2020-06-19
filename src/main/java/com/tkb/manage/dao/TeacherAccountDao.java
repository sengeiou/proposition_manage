package com.tkb.manage.dao;

import java.util.List;
import java.util.Map;

import com.tkb.manage.model.Account;

public interface TeacherAccountDao {
	
	public Account login(Account account);
	public List<Map<String, Object>> list(Account account);
	public Integer count(Account account);
	public Account data(Account account);
	public int add(Account account);
	public void update(Account account);
	public void delete(Account account);
	
}
