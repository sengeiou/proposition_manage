package com.tkb.manage.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.tkb.manage.dao.LessonPlanTagDao;
import com.tkb.manage.model.LessonPlanTag;

@Repository
public class LessonPlanTagDaoImpl implements LessonPlanTagDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private NamedParameterJdbcTemplate jdbcNameTemplate;
	
	public Integer add(LessonPlanTag lessonPlanTag) {
		
		SqlParameterSource paramSource = new BeanPropertySqlParameterSource(lessonPlanTag);
		
		String sql = " INSERT INTO proposition_manage.lesson_plan "
				   + " (LESSON_PLAN_ID, NAME, "
				   + " CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) "
				   + " VALUES(:lesson_plan_id, :name, "
				   + " :create_by, NOW(), :update_by, NOW()) ";
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		
		jdbcNameTemplate.update(sql, paramSource, keyHolder);
		
		return keyHolder.getKey().intValue();
		
	}
	
	public void delete(LessonPlanTag lessonPlanTag) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " DELETE FROM proposition_manage.lesson_plan_option "
				   + " WHERE LESSON_PLAN_ID = ? ";
		
		args.add(lessonPlanTag.getLesson_plan_id());
		
		jdbcTemplate.update(sql, args.toArray());
		
	}
	
}
