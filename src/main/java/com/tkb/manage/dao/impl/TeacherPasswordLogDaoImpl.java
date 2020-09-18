package com.tkb.manage.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.tkb.manage.dao.TeacherPasswordLogDao;
import com.tkb.manage.model.TeacherPasswordLog;

@Repository
public class TeacherPasswordLogDaoImpl implements TeacherPasswordLogDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private NamedParameterJdbcTemplate jdbcNameTemplate;
	
	public TeacherPasswordLog data(TeacherPasswordLog teacherPasswordLog) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " SELECT * FROM proposition_manage.teacher_password_log "
				   + " WHERE ACCOUNT = ? AND VERIFY_STATUS = ?  "
			       + " AND DATE_FORMAT(NOW(),'%Y-%m-%d') = DATE_FORMAT(CREATE_TIME,'%Y-%m-%d') "
				   + " AND ID = ? "
			       + " ORDER BY CREATE_TIME DESC  ";
		
		args.add(teacherPasswordLog.getAccount());
		args.add(teacherPasswordLog.getVerify_status());
		args.add(teacherPasswordLog.getId());
		
		List<TeacherPasswordLog> list = jdbcTemplate.query(sql, args.toArray(), new BeanPropertyRowMapper<TeacherPasswordLog>(TeacherPasswordLog.class));
		if(list!=null && list.size()>0) {
			return list.get(0);
		} else {
			return null;
		}
		
	}
	
	public int add(TeacherPasswordLog teacherPasswordLog) {
		
		SqlParameterSource paramSource = new BeanPropertySqlParameterSource(teacherPasswordLog);
		
		String sql = " INSERT INTO proposition_manage.teacher_password_log "
				   + " (TEACHER_ACCOUNT_ID, ACCOUNT, OLD_PASSWORD, IP, VERIFY_CODE, "
				   + " VERIFY_STATUS, VERIFY_COUNT, CREATE_TIME, FROM_TYPE) "
				   + " VALUES(:teacher_account_id, :account, :old_password, :ip, "
				   + " :verify_code, :verify_status, :verify_count, NOW(), :from_type) ";
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		
		jdbcNameTemplate.update(sql, paramSource, keyHolder);
		
		return keyHolder.getKey().intValue();
		
	}
	
	public void update(TeacherPasswordLog teacherPasswordLog) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " UPDATE proposition_manage.teacher_password_log "
				   + " SET NEW_PASSWORD = ?, VERIFY_STATUS = ?, VERIFY_TIME = NOW() "
				   + " WHERE ID = ? ";
		
		args.add(teacherPasswordLog.getNew_password());
		args.add(teacherPasswordLog.getVerify_status());
		args.add(teacherPasswordLog.getId());
		
		jdbcTemplate.update(sql, args.toArray());
		
	}
	
	public TeacherPasswordLog dataBy1Hour(TeacherPasswordLog teacherPasswordLog) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " SELECT * FROM proposition_manage.teacher_password_log "
				   + " WHERE CREATE_TIME >= DATE_SUB(NOW(),INTERVAL 1 HOUR)  "
			       + " AND ACCOUNT = ? "
			       + "   ";
		
		args.add(teacherPasswordLog.getAccount());
		
		List<TeacherPasswordLog> list = jdbcTemplate.query(sql, args.toArray(), new BeanPropertyRowMapper<TeacherPasswordLog>(TeacherPasswordLog.class));
		if(list!=null && list.size()>0) {
			return list.get(0);
		} else {
			return null;
		}
		
	}
	
}
