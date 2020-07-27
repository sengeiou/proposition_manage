package com.tkb.manage.service;

import java.util.List;
import java.util.Map;

import com.tkb.manage.model.LessonPlanTag;

public interface LessonPlanTagService {
	
	public Integer add(LessonPlanTag lessonPlanTag);
	public void delete(LessonPlanTag lessonPlanTag);
	public List<Map<String, Object>> tagList(LessonPlanTag lessonPlanTag);
	
}
