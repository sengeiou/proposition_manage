package com.tkb.manage.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tkb.manage.dao.TeacherAccountDao;
import com.tkb.manage.model.Account;
import com.tkb.manage.service.TeacherAccountService;

@Service
public class TeacherAccountServiceImpl implements TeacherAccountService {
	
	@Autowired
	private TeacherAccountDao teacherAccountDao;
	
	public Account login(Account account) {
		return teacherAccountDao.login(account);
	}
	
	public List<Map<String, Object>> list(Account account) {
		return teacherAccountDao.list(account);
	}
	
	public Integer count(Account account) {
		return teacherAccountDao.count(account);
	}
	
	public Account data(Account account) {
		return teacherAccountDao.data(account);
	}
	
	public int add(Account account) {
		return teacherAccountDao.add(account);
	}
	
	public void update(Account account) {
		teacherAccountDao.update(account);
	}
	
	public void delete(Account account) {
		teacherAccountDao.delete(account);
	}
	
	public void updateIdentity(Account account) {
		teacherAccountDao.updateIdentity(account);
	}
	
	public Map<String, Object> getDataByAccount(Account account) {
		return teacherAccountDao.getDataByAccount(account);
	}
	
	public Map<String, Object> callNum(Account account) {
		return teacherAccountDao.callNum(account);
	}
	
	public Map<String, Object> getAuditor(Account account) {
		return teacherAccountDao.getAuditor(account);
	}
	
	public List<Map<String, Object>> verifyList(Account account) {
		return teacherAccountDao.verifyList(account);
	}
	
	public List<Map<String, Object>> getAuditorByEduSub(Account account) {
		return teacherAccountDao.getAuditorByEduSub(account);
	}
	
	public Integer checkAccount(String id_no,String id) {
		return teacherAccountDao.checkAccount(id_no, id);
	}
	
	public void audit(Account account) {
		teacherAccountDao.audit(account);
	}
	
	public Account dataByAccount(Account account) {
		return teacherAccountDao.dataByAccount(account);
	}
	
	public void updateVerify(Account account) {
		teacherAccountDao.updateVerify(account);
	}
	
	public void updatePassword(Account account) {
		teacherAccountDao.updatePassword(account);
	}
	
	public List<Map<String, Object>> managerList(Account account) {
		return teacherAccountDao.managerList(account);
	}
	
}
