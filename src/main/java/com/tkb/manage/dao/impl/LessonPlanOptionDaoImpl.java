package com.tkb.manage.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.tkb.manage.dao.LessonPlanOptionDao;
import com.tkb.manage.model.LessonPlanOption;

@Repository
public class LessonPlanOptionDaoImpl implements LessonPlanOptionDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public void add(LessonPlanOption lessonPlanOption) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " INSERT INTO proposition_manage.lesson_plan_option(LESSON_PLAN_ID, TYPE, "
				   + " CODE, CREATE_BY, CREATE_TIME) "
				   + " VALUES(?, ?, ?, ?, NOW()) ";
		
		args.add(lessonPlanOption.getLesson_plan_id());
		args.add(lessonPlanOption.getType());
		args.add(lessonPlanOption.getCode());
		args.add(lessonPlanOption.getCreate_by());
		
		jdbcTemplate.update(sql, args.toArray());
		
	}
	
	public void delete(LessonPlanOption lessonPlanOption) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " DELETE FROM proposition_manage.lesson_plan_option "
				   + " WHERE LESSON_PLAN_ID = ? "
				   + " AND TYPE = ? ";
		
		args.add(lessonPlanOption.getLesson_plan_id());
		args.add(lessonPlanOption.getType());
		
		jdbcTemplate.update(sql, args.toArray());
		
	}
	
	public Map<String, Object> option(LessonPlanOption lessonPlanOption) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " SELECT GROUP_CONCAT(CODE) AS OPTION "
				   + " FROM proposition_manage.lesson_plan_option "
				   + " WHERE LESSON_PLAN_ID = ? "
				   + " AND TYPE = ? ";
		
		args.add(lessonPlanOption.getLesson_plan_id());
		args.add(lessonPlanOption.getType());
		
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, args.toArray());
		
		if(list!=null && list.size()>0 && list.get(0).get("OPTION")!=null) {
			return list.get(0);
		} else {
			return null;
		}
		
	}
	
	public Map<String, Object> subjectOption(LessonPlanOption lessonPlanOption) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " SELECT GROUP_CONCAT(PMS.NAME SEPARATOR '、') AS OPTION "
				   + " FROM proposition_manage.lesson_plan_option LPO "
				   + " LEFT JOIN proposition_manage.subject PMS ON PMS.ID = LPO.CODE "
				   + " WHERE LPO.LESSON_PLAN_ID = ? "
				   + " AND LPO.TYPE = ? ";
		
		args.add(lessonPlanOption.getLesson_plan_id());
		args.add(lessonPlanOption.getType());
		
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, args.toArray());
		
		if(list!=null && list.size()>0 && list.get(0).get("OPTION")!=null) {
			return list.get(0);
		} else {
			return null;
		}
		
	}
	
	public Map<String, Object> gradeOption(LessonPlanOption lessonPlanOption) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " SELECT GROUP_CONCAT(PME.NAME SEPARATOR '、') AS OPTION "
				   + " FROM proposition_manage.lesson_plan_option LPO "
				   + " LEFT JOIN proposition_manage.education PME ON PME.ID = LPO.CODE "
				   + " WHERE LPO.LESSON_PLAN_ID = ? "
				   + " AND LPO.TYPE = ? ";
		
		args.add(lessonPlanOption.getLesson_plan_id());
		args.add(lessonPlanOption.getType());
		
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, args.toArray());
		
		if(list!=null && list.size()>0 && list.get(0).get("OPTION")!=null) {
			return list.get(0);
		} else {
			return null;
		}
		
	}
	
}
