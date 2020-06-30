package com.tkb.manage.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tkb.manage.dao.LessonPlanFileDao;
import com.tkb.manage.model.LessonPlanFile;
import com.tkb.manage.service.LessonPlanFileService;

@Service
public class LessonPlanFileServiceImpl implements LessonPlanFileService {
	
	@Autowired
	private LessonPlanFileDao lessonPlanFileDao;
	
	public int add(LessonPlanFile lessonPlanFile) {
		return lessonPlanFileDao.add(lessonPlanFile);
	}
	
	public List<Map<String, Object>> historyList(LessonPlanFile lessonPlanFile) {
		return lessonPlanFileDao.historyList(lessonPlanFile);
	}
	
}
