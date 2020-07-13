package com.tkb.manage.service;

import java.util.Map;

import com.tkb.manage.model.TeacherAccountOption;

public interface TeacherAccountOptionService {
	
	public void add(TeacherAccountOption teacherAccountOption);
	public void delete(TeacherAccountOption teacherAccountOption);
	public Map<String, Object> option(TeacherAccountOption teacherAccountOption);
	public Map<String, Object> educationOption(TeacherAccountOption teacherAccountOption);
	public Map<String, Object> fieldOption(TeacherAccountOption teacherAccountOption);
	
}
