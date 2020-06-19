package com.tkb.manage.controller.api;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin
@RestController
@RequestMapping("/api")
@Api(tags = { "測試" })
@ApiResponses({ 
	@ApiResponse(code = 400, message = "請求參數有誤"),
	@ApiResponse(code = 401, message = "未授權"),
	@ApiResponse(code = 403, message = "禁止訪問"),
	@ApiResponse(code = 404, message = "請求路徑不存在")
})
public class APIController {
	
	private static final Logger logger = LoggerFactory.getLogger(APIController.class);
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	
	@RequestMapping(value = "/test", method = { RequestMethod.POST })
	@ResponseBody
	@ApiOperation(value = "依單位取得權限清單(排除員編有值的)", notes = "old_code")
	@ApiImplicitParam(paramType = "header", name = "Authorization", value = "Bearer a4ht0ObOFH5NOt9mJai3KHBkDgR0NZL5yOwbTMIDGI5ifaPfp0ENSyXji/doY5/Xc59/ysJ5z2J4fU4nChmdOQ==", required = true, dataType = "String")
	public ResponseEntity<?> codeList(
			) {
//		Gson gson = new Gson();
//		logger.info(gson.toJson(organization));
		logger.info("excute request /document/auth/code/list time is {} ", dateFormat.format(new Date()));
		
//		boolean status = true;		//狀態
//		String msg = "done";		//訊息
		
		List<Map<String, Object>> codeList = new ArrayList<Map<String, Object>>();
		
		return new ResponseEntity<List<Map<String, Object>>>(codeList, HttpStatus.OK);
		
	}
	
}
