package com.tkb.manage.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tkb.manage.dao.LessonPlanTagDao;
import com.tkb.manage.model.LessonPlanTag;
import com.tkb.manage.service.LessonPlanTagService;

@Service
public class LessonPlanTagServiceImpl implements LessonPlanTagService {
	
	@Autowired
	private LessonPlanTagDao lessonPlanTagDao;
	
	public Integer add(LessonPlanTag lessonPlanTag) {
		return lessonPlanTagDao.add(lessonPlanTag);
	}
	
	public void delete(LessonPlanTag lessonPlanTag) {
		lessonPlanTagDao.delete(lessonPlanTag);
	}
	
}
