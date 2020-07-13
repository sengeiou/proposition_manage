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

import com.tkb.manage.dao.LessonPlanFileDao;
import com.tkb.manage.model.LessonPlanFile;

@Repository
public class LessonPlanFileDaoImpl implements LessonPlanFileDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private NamedParameterJdbcTemplate jdbcNameTemplate;
	
	public int add(LessonPlanFile lessonPlanFile) {
		
		SqlParameterSource paramSource = new BeanPropertySqlParameterSource(lessonPlanFile);
		
		String sql = " INSERT INTO proposition_manage.lesson_plan_file"
				   + " (LESSON_PLAN_ID, TYPE, UPLOAD_NAME, FILE_NAME, "
				   + " CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) "
				   + " VALUES(:lesson_plan_id, :type, :upload_name, :file_name, "
				   + " :create_by, NOW(), :update_by, NOW()) ";
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
				
		jdbcNameTemplate.update(sql, paramSource, keyHolder);
		
		return keyHolder.getKey().intValue();
		
	}
	
	public List<Map<String, Object>> historyList(LessonPlanFile lessonPlanFile) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " SELECT *, DATE_FORMAT(CREATE_TIME, '%Y/%m/%d') AS CREATE_DATE "
				   + " FROM proposition_manage.lesson_plan_file "
				   + " WHERE LESSON_PLAN_ID = ? "
//				   + " AND TYPE = ? "
				   + " ORDER BY CREATE_TIME DESC ";
		
		args.add(lessonPlanFile.getLesson_plan_id());
//		args.add(lessonPlanFile.getType());
		
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, args.toArray());
		if(list!=null && list.size()>0) {
			return list;
		} else {
			return null;
		}
		
	}
	
	
}
