package com.tkb.manage.dao;

import java.util.Map;

import com.tkb.manage.model.LessonPlanOption;

public interface LessonPlanOptionDao {
	
	public void add(LessonPlanOption lessonPlanOption);
	public void delete(LessonPlanOption lessonPlanOption);
	public Map<String, Object> option(LessonPlanOption lessonPlanOption);
	public Map<String, Object> subjectOption(LessonPlanOption lessonPlanOption);
	public Map<String, Object> gradeOption(LessonPlanOption lessonPlanOption);
	
}
