package com.tkb.manage.controller.api;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tkb.manage.model.Subject;
import com.tkb.manage.service.SubjectService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin
@RestController
@RequestMapping("/api/subject")
@Api(tags = { "學科API" })
@ApiResponses({ 
	@ApiResponse(code = 400, message = "請求參數有誤"),
	@ApiResponse(code = 401, message = "未授權"),
	@ApiResponse(code = 403, message = "禁止訪問"),
	@ApiResponse(code = 404, message = "請求路徑不存在")
})
public class SubjectAPI {
	
	private static final Logger logger = LoggerFactory.getLogger(SubjectAPI.class);
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	
	@Autowired
	private SubjectService subjectService;
	
	@RequestMapping(value = "/list", method = { RequestMethod.GET })
	@ResponseBody
	@ApiOperation(value = "取得學科清單", notes = "無")
	@ApiImplicitParam(paramType = "header", name = "Authorization", value = "Bearer a4ht0ObOFH5NOt9mJai3KHBkDgR0NZL5yOwbTMIDGI5ifaPfp0ENSyXji/doY5/Xc59/ysJ5z2J4fU4nChmdOQ==", required = true, dataType = "String")
	public ResponseEntity<?> subjectList(
			) {
//		Gson gson = new Gson();
//		logger.info(gson.toJson(organization));
		logger.info("excute request /api/subject/list time is {} ", dateFormat.format(new Date()));
		
//		boolean status = true;		//狀態
//		String msg = "done";		//訊息
		
		Subject subject = new Subject();
		subject.setLayer("1");
		List<Map<String, Object>> list = subjectService.getList(subject);
		
		return new ResponseEntity<List<Map<String, Object>>>(list, HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/inidlist", method = { RequestMethod.GET })
	@ResponseBody
	@ApiOperation(value = "依ID取得可使用的學科清單", notes = "無")
	@ApiImplicitParam(paramType = "header", name = "Authorization", value = "Bearer a4ht0ObOFH5NOt9mJai3KHBkDgR0NZL5yOwbTMIDGI5ifaPfp0ENSyXji/doY5/Xc59/ysJ5z2J4fU4nChmdOQ==", required = true, dataType = "String")
	public ResponseEntity<?> subjectInIdList(
//			@RequestParam("ids[]") Integer[] ids
			@RequestParam(value = "id", required = true) int[] id
			) {
//		Gson gson = new Gson();
//		logger.info(gson.toJson(organization));
		logger.info("excute request /api/subject/inidlist time is {} ", dateFormat.format(new Date()));
		
//		boolean status = true;		//狀態
//		String msg = "done";		//訊息
		
		Subject subject = new Subject();
		subject.setId(StringUtils.join(ArrayUtils.toObject(id), ","));
		List<Map<String, Object>> list = subjectService.getListInId(subject);
		
		return new ResponseEntity<List<Map<String, Object>>>(list, HttpStatus.OK);
		
	}
	
}
