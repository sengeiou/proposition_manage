package com.tkb.manage.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.tkb.manage.dao.LessonPlanAuditDao;
import com.tkb.manage.model.LessonPlanAudit;

@Repository
public class LessonPlanAuditDaoImpl implements LessonPlanAuditDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private NamedParameterJdbcTemplate jdbcNameTemplate;
	
	public int add(LessonPlanAudit lessonPlanAudit) {
		
		SqlParameterSource paramSource = new BeanPropertySqlParameterSource(lessonPlanAudit);
		
		String sql = " INSERT INTO proposition_manage.lesson_plan_audit"
				   + " (LESSON_PLAN_ID, AUDITOR, VERSION, FILE_STATUS, AUDIT_FEEDBACK, "
				   + " CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) "
				   + " VALUES(:lesson_plan_id, :auditor, "
				   + " (SELECT CONCAT(:version, LPAD(COUNT, 2, 0)) FROM (SELECT COUNT(*)+1 AS COUNT FROM proposition_manage.lesson_plan_audit WHERE LESSON_PLAN_ID = :lesson_plan_id AND INSTR(VERSION, :version) > 0) L1), "
				   + " :file_status, :audit_feedback, "
				   + " :create_by, NOW(), :update_by, NOW()) ";
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
				
		jdbcNameTemplate.update(sql, paramSource, keyHolder);
		
		return keyHolder.getKey().intValue();
		
	}
	
	public List<Map<String, Object>> historyList(LessonPlanAudit lessonPlanAudit) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " SELECT *, DATE_FORMAT(CREATE_TIME, '%Y-%m-%d') AS CREATE_DATE "
				   + " FROM proposition_manage.lesson_plan_audit "
				   + " WHERE LESSON_PLAN_ID = ? "
				   + " ORDER BY CREATE_TIME DESC ";
		
		args.add(lessonPlanAudit.getLesson_plan_id());
		
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, args.toArray());
		if(list!=null && list.size()>0) {
			return list;
		} else {
			return null;
		}
		
	}
	
}
