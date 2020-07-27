package com.tkb.manage.dao;

import java.util.List;
import java.util.Map;

import com.tkb.manage.model.LessonPlanFile;

public interface LessonPlanFileDao {
	
	public int add(LessonPlanFile lessonPlanFile);
	public List<Map<String, Object>> historyList(LessonPlanFile lessonPlanFile);
	public List<Map<String, Object>> getFile(LessonPlanFile lessonPlanFile);
	public List<Map<String, Object>> getAnnex(LessonPlanFile lessonPlanFile);
	public List<Map<String, Object>> getMaterial(LessonPlanFile lessonPlanFile);
	
}
