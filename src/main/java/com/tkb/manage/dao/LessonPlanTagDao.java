package com.tkb.manage.dao;

import java.util.List;
import java.util.Map;

import com.tkb.manage.model.LessonPlanTag;

public interface LessonPlanTagDao {
	
	public Integer add(LessonPlanTag lessonPlanTag);
	public void delete(LessonPlanTag lessonPlanTag);
	public List<Map<String, Object>> tagList(LessonPlanTag lessonPlanTag);
	
}
