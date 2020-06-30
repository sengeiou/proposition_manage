package com.tkb.manage.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
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
	private NamedParameterJdbcTemplate jdbcNameTemplate;
	
	public int add(LessonPlanAudit lessonPlanAudit) {
		
		SqlParameterSource paramSource = new BeanPropertySqlParameterSource(lessonPlanAudit);
		
		String sql = " INSERT INTO proposition_manage.lesson_plan_audit"
				   + " (LESSON_PLAN_ID, AUDITOR, FILE_STATUS, UPLOAD_STATUS, "
				   + " CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) "
				   + " VALUES(:lesson_plan_id, :auditor, :file_status, :upload_status, "
				   + " :create_by, NOW(), :update_by, NOW()) ";
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
				
		jdbcNameTemplate.update(sql, paramSource, keyHolder);
		
		return keyHolder.getKey().intValue();
		
	}
	
}
