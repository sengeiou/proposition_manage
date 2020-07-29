package com.tkb.manage.dao;

import java.util.List;
import java.util.Map;

import com.tkb.manage.model.LessonPlan;

public interface LessonPlanDao {
	
	public List<Map<String, Object>> manageList(LessonPlan lessonPlan);
	public Integer manageCount(LessonPlan lessonPlan);
	public List<Map<String, Object>> auditList(LessonPlan lessonPlan);
	public Integer auditCount(LessonPlan lessonPlan);
	public List<Map<String, Object>> teacherList(LessonPlan lessonPlan);
	public Integer teacherCount(LessonPlan lessonPlan);
	public List<Map<String, Object>> principalList(LessonPlan lessonPlan);
	public Integer principalCount(LessonPlan lessonPlan);
	public List<Map<String, Object>> leaderList(LessonPlan lessonPlan);
	public Integer leaderCount(LessonPlan lessonPlan);
	public List<Map<String, Object>> secretaryGeneralList(LessonPlan lessonPlan);
	public Integer secretaryGeneralCount(LessonPlan lessonPlan);
	public Integer uploadStatusCount(LessonPlan lessonPlan);
	public Integer add(LessonPlan lessonPlan);
	public void update(LessonPlan lessonPlan);
	public LessonPlan data(LessonPlan lessonPlan);
	public void audit(LessonPlan lessonPlan);
	public Map<String, Object> auditNum(LessonPlan lessonPlan);
	
}
