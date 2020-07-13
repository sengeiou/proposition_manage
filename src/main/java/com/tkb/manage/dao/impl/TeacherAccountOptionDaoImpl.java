package com.tkb.manage.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.tkb.manage.dao.TeacherAccountOptionDao;
import com.tkb.manage.model.TeacherAccountOption;

@Repository
public class TeacherAccountOptionDaoImpl implements TeacherAccountOptionDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public void add(TeacherAccountOption teacherAccountOption) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " INSERT INTO proposition_manage.teacher_account_option(TEACHER_ACCOUNT_ID, TYPE, "
				   + " CODE, CREATE_BY, CREATE_TIME) "
				   + " VALUES(?, ?, ?, ?, NOW()) ";
		
		args.add(teacherAccountOption.getTeacher_account_id());
		args.add(teacherAccountOption.getType());
		args.add(teacherAccountOption.getCode());
		args.add(teacherAccountOption.getCreate_by());
		
		jdbcTemplate.update(sql, args.toArray());
		
	}
	
	public void delete(TeacherAccountOption teacherAccountOption) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " DELETE FROM proposition_manage.teacher_account_option "
				   + " WHERE TEACHER_ACCOUNT_ID = ? "
				   + " AND TYPE = ? ";
		
		args.add(teacherAccountOption.getTeacher_account_id());
		args.add(teacherAccountOption.getType());
		
		jdbcTemplate.update(sql, args.toArray());
		
	}
	
	public Map<String, Object> option(TeacherAccountOption teacherAccountOption) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " SELECT GROUP_CONCAT(CODE) AS OPTION "
				   + " FROM proposition_manage.teacher_account_option "
				   + " WHERE TEACHER_ACCOUNT_ID = ? "
				   + " AND TYPE = ? ";
		
		args.add(teacherAccountOption.getTeacher_account_id());
		args.add(teacherAccountOption.getType());
		
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, args.toArray());
		
		if(list!=null && list.size()>0 && list.get(0).get("OPTION")!=null) {
			return list.get(0);
		} else {
			return null;
		}
		
	}
	
	public Map<String, Object> educationOption(TeacherAccountOption teacherAccountOption) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " SELECT GROUP_CONCAT(PME.NAME SEPARATOR '、') AS OPTION "
				   + " FROM proposition_manage.teacher_account_option TAO "
				   + " LEFT JOIN proposition_manage.education PME ON PME.ID = TAO.CODE "
				   + " WHERE TAO.TEACHER_ACCOUNT_ID = ? "
				   + " AND TAO.TYPE = ? ";
		
		args.add(teacherAccountOption.getTeacher_account_id());
		args.add(teacherAccountOption.getType());
		
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, args.toArray());
		System.out.println("list:"+list);
		if(list!=null && list.size()>0 && list.get(0).get("OPTION")!=null) {
			return list.get(0);
		} else {
			return null;
		}
		
	}
	
	public Map<String, Object> fieldOption(TeacherAccountOption teacherAccountOption) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " SELECT GROUP_CONCAT(PMF.NAME SEPARATOR '、') AS OPTION "
				   + " FROM proposition_manage.teacher_account_option TAO "
				   + " LEFT JOIN proposition_manage.field PMF ON PMF.ID = TAO.CODE "
				   + " WHERE TAO.TEACHER_ACCOUNT_ID = ? "
				   + " AND TAO.TYPE = ? ";
		
		args.add(teacherAccountOption.getTeacher_account_id());
		args.add(teacherAccountOption.getType());
		
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, args.toArray());
		
		if(list!=null && list.size()>0 && list.get(0).get("OPTION")!=null) {
			return list.get(0);
		} else {
			return null;
		}
		
	}
	
}
