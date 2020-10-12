package com.tkb.manage.service;

import com.tkb.manage.model.TeacherPasswordLog;

public interface TeacherPasswordLogService {
	
	public TeacherPasswordLog data(TeacherPasswordLog teacherPasswordLog);
	public int add(TeacherPasswordLog teacherPasswordLog);
	public void update(TeacherPasswordLog teacherPasswordLog);
	public TeacherPasswordLog dataBy1Hour(TeacherPasswordLog teacherPasswordLog);
}
