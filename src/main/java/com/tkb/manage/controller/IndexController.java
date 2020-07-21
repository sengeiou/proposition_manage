package com.tkb.manage.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.tkb.manage.controller.admin.FunctionController;
import com.tkb.manage.model.Account;
import com.tkb.manage.model.Contract;
import com.tkb.manage.model.ContractMaterial;
import com.tkb.manage.model.ContractMaterialOption;
import com.tkb.manage.model.Education;
import com.tkb.manage.model.Field;
import com.tkb.manage.model.Function;
import com.tkb.manage.model.Identity;
import com.tkb.manage.model.LessonPlan;
import com.tkb.manage.model.LessonPlanAudit;
import com.tkb.manage.model.LessonPlanFile;
import com.tkb.manage.model.LessonPlanOption;
import com.tkb.manage.model.Proposition;
import com.tkb.manage.model.PropositionAudit;
import com.tkb.manage.model.PropositionFile;
import com.tkb.manage.model.PropositionOption;
import com.tkb.manage.model.SchoolMaster;
import com.tkb.manage.model.Subject;
import com.tkb.manage.model.TeacherAccountOption;
import com.tkb.manage.service.CommonService;
import com.tkb.manage.service.ContractMaterialOptionService;
import com.tkb.manage.service.ContractMaterialService;
import com.tkb.manage.service.ContractService;
import com.tkb.manage.service.EducationService;
import com.tkb.manage.service.FieldService;
import com.tkb.manage.service.IdentityService;
import com.tkb.manage.service.LessonPlanAuditService;
import com.tkb.manage.service.LessonPlanFileService;
import com.tkb.manage.service.LessonPlanOptionService;
import com.tkb.manage.service.LessonPlanService;
import com.tkb.manage.service.PropositionAuditService;
import com.tkb.manage.service.PropositionFileService;
import com.tkb.manage.service.PropositionOptionService;
import com.tkb.manage.service.PropositionService;
import com.tkb.manage.service.SchoolMasterService;
import com.tkb.manage.service.SubjectService;
import com.tkb.manage.service.TeacherAccountOptionService;
import com.tkb.manage.service.TeacherAccountService;

import jxl.Sheet;
import jxl.Workbook;

@Controller
@SessionAttributes("accountSession")
@RequestMapping("/")
public class IndexController {
	
	@Autowired
	private FunctionController functionController;
	
	@Autowired
	private TeacherAccountService teacherAccountService;
	
	@Autowired
	private TeacherAccountOptionService teacherAccountOptionService;
	
	@Autowired
	private IdentityService identityService;
	
	@Autowired
	private FieldService fieldService;
	
	@Autowired
	private SchoolMasterService schoolMasterService;
	
	@Autowired
	private EducationService educationService;
	
	@Autowired
	private ContractService contractService;
	
	@Autowired
	private ContractMaterialService contractMaterialService;
	
	@Autowired
	private ContractMaterialOptionService contractMaterialOptionService;
	
	@Autowired
	private LessonPlanService lessonPlanService;
	
	@Autowired
	private LessonPlanOptionService lessonPlanOptionService;
	
	@Autowired
	private LessonPlanFileService lessonPlanFileService;
	
	@Autowired
	private LessonPlanAuditService lessonPlanAuditService;
	
	@Autowired
	private PropositionService propositionService;
	
	@Autowired
	private PropositionOptionService propositionOptionService;
	
	@Autowired
	private PropositionFileService propositionFileService;
	
	@Autowired
	private PropositionAuditService propositionAuditService;
	
	@Autowired
	private SubjectService subjectService;
	
	@Autowired
	private CommonService commonService;
	
	@Value("${front.menu.name}") 
	private String menuName;
	
	@Value("${upload.file.path}") 
	private String uploadedFolder;
	
	@Value("${teacher.download}") 
	private String teacherDownload;
	
	@Value("${teacher.uploadName}") 
	private String teacherUploadName;
	
	@Value("${teacher.fileName}") 
	private String teacherFileName;
	
	private static final Logger logger = LoggerFactory.getLogger(IndexController.class);
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	
//	private Integer page_count = 10;
	
	@RequestMapping(value = "/index" , method = {RequestMethod.GET, RequestMethod.POST})
	public String index(Model model, @SessionAttribute("accountSession") Account accountSession) {
		
		int level = Integer.valueOf(accountSession.getLevel());
		
		Contract contract = new Contract();
		
		//管理者(幾份合約未履行、幾份合約到期)
		if(level <= 2) {
			contract = new Contract();
			//合約未履行數量
			int allUndoneNum = contractService.allUndoneNum(contract);
			model.addAttribute("allUndoneNum", allUndoneNum);
			//合約已過期數量
			int expired = contractService.expired(contract);
			model.addAttribute("expired", expired);
		//老師(幾份教案、命題基本題、命題題組題未審核及未修訂)
		} else if(level == 3) {
			contract = new Contract();
			contract.setTeacher_id(accountSession.getId());
			//總需上傳數-已上傳=未上傳數
			Map<String, Object> uploadNum = contractService.uploadNum(contract);
			int lessonNum = Integer.valueOf(uploadNum.get("LESSON_NUM").toString());
			model.addAttribute("lessonNum", lessonNum);
			LessonPlan lessonPlan = new LessonPlan();
			lessonPlan.setCreate_by(accountSession.getAccount());
			//教案已上傳數
			lessonPlan.setUpload_status("Y");
			int lessonAddCount = lessonPlanService.uploadStatusCount(lessonPlan);
			model.addAttribute("lessonAddCount", lessonAddCount);
			//教案待修訂數
			lessonPlan.setUpload_status("N");
			int lessonEditCount = lessonPlanService.uploadStatusCount(lessonPlan);
			model.addAttribute("lessonEditCount", lessonEditCount);
			//教案已完稿數
			lessonPlan.setUpload_status("C");
			int lessonDoneCount = lessonPlanService.uploadStatusCount(lessonPlan);
			model.addAttribute("lessonDoneCount", lessonDoneCount);
			
			Proposition proposition = new Proposition();
			proposition.setQuestion_type("1");
			proposition.setCreate_by(accountSession.getAccount());
			
			//命題基本題總需上傳數
			int basicNum = Integer.valueOf(uploadNum.get("BASIC_NUM").toString());
			model.addAttribute("basicNum", basicNum);
			//命題基本題已上傳數
			proposition.setUpload_status("Y");
			int basicAddCount = propositionService.uploadStatusCount(proposition);
			model.addAttribute("basicAddCount", basicAddCount);
			//命題基本題待修訂數
			proposition.setUpload_status("N");
			int basicEditCount = propositionService.uploadStatusCount(proposition);
			model.addAttribute("basicEditCount", basicEditCount);
			//命題基本題已完稿數
			proposition.setUpload_status("C");
			int basicDoneCount = propositionService.uploadStatusCount(proposition);
			model.addAttribute("basicDoneCount", basicDoneCount);
			
			proposition = new Proposition();
			proposition.setQuestion_type("2");
			proposition.setCreate_by(accountSession.getAccount());
			
			//命題題組題總需上傳數
			int questionsGroupNum = Integer.valueOf(uploadNum.get("QUESTIONS_GROUP_NUM").toString());
			model.addAttribute("questionsGroupNum", questionsGroupNum);
			//命題題組題已上傳數
			proposition.setUpload_status("Y");
			int questionsGroupAddCount = propositionService.uploadStatusCount(proposition);
			model.addAttribute("questionsGroupAddCount", questionsGroupAddCount);
			//命題題組題待修訂數
			proposition.setUpload_status("N");
			int questionsGroupEditCount = propositionService.uploadStatusCount(proposition);
			model.addAttribute("questionsGroupEditCount", questionsGroupEditCount);
			//命題題組題已完稿數
			proposition.setUpload_status("C");
			int questionsGroupDoneCount = propositionService.uploadStatusCount(proposition);
			model.addAttribute("questionsGroupDoneCount", questionsGroupDoneCount);
			
		//審核人(幾份教案、命題基本題、命題題組題未審核)
		} else if(level == 4) {
			LessonPlan lessonPlan = new LessonPlan();
			lessonPlan.setAuditor(accountSession.getAccount());
			int unLessonAuditCount = lessonPlanService.auditCount(lessonPlan);
			model.addAttribute("unLessonAuditCount", unLessonAuditCount);
			
			Proposition proposition = new Proposition();
			proposition.setQuestion_type("1");
			proposition.setAuditor(accountSession.getAccount());
			int unBasicAuditCount = propositionService.auditCount(proposition);
			model.addAttribute("unBasicAuditCount", unBasicAuditCount);
			
			proposition = new Proposition();
			proposition.setQuestion_type("2");
			proposition.setAuditor(accountSession.getAccount());
			int unGroupAuditCount = propositionService.auditCount(proposition);
			model.addAttribute("unGroupAuditCount", unGroupAuditCount);
		} else if(level == 5 || level == 6 || level == 7) {
			Field field = new Field();
			field.setId(accountSession.getField_list());
			List<Map<String, Object>> fieldList = fieldService.getListInId(field);
			model.addAttribute("fieldList", fieldList);
			if(fieldList != null) {
				String[] educationArr = accountSession.getEducation_list().split(",");
				List<Map<String, Object>> educationList = new ArrayList<Map<String, Object>>();
				for(int i=0; i<fieldList.size(); i++) {
					for(int j=0; j<educationArr.length; j++) {
						contract = new Contract();
//						contract.setField_id(fieldList.get(i).get("ID").toString());
						contract.setEducation_id(educationArr[j]);
						Map<String, Object> getListByFieldEducation = contractService.getDataByFieldEducation(contract);
						if(getListByFieldEducation != null) {
							educationList.add(getListByFieldEducation);
						} else {
							Education education = new Education();
							education.setId(educationArr[j]);
							education = educationService.data(education);
							
							Map<String, Object> map = new HashMap<String, Object>();
							map.put("FIELD_NAME", fieldList.get(i).get("NAME").toString());
							map.put("FIELD_ID", fieldList.get(i).get("ID").toString());
							map.put("EDUCATION_NAME", education.getName());
							map.put("EDUCATION_ID", education.getId());
							map.put("UPLOAD_NUM", "0");
							map.put("COMPLETE_NUM", "0");
							map.put("TOTAL_NUM", "0");
							educationList.add(map);
						}
					}
				}
				model.addAttribute("educationList", educationList);
			}
		}
		
		//選單
		List<Map<String, Object>> menu = functionController.menu(accountSession, menuName);
		model.addAttribute("menu", menu);
		
		return "front/index";
	}
	
	@RequestMapping(value = "/path" , method = {RequestMethod.GET, RequestMethod.POST})
	public String path(Model model, @SessionAttribute("accountSession") Account accountSession,
		@ModelAttribute Function function) {
		
		return "front/path";
	}
	
	@RequestMapping(value = "/teacher" , method = {RequestMethod.POST, RequestMethod.GET})
    public String list(@SessionAttribute("accountSession") Account accountSession, @ModelAttribute Account account, @ModelAttribute Contract contract, Model model){
		
		//設定每頁筆數
		if(account.getPage_count() == null) {
			account.setPage_count(10);
		}
		
		model.addAttribute("page_count", account.getPage_count());
		
		if(account.getPage() == null) {
			account.setPage(1);
		}
		
//		account.setPage_count(page_count);
		List<Map<String, Object>> list = teacherAccountService.list(account);
		model.addAttribute("list", list);
		
		int count = teacherAccountService.count(account);
		account.setCount(count);
		account.setTotal_page((account.getCount()/account.getPage_count())<1 ? 1 : ((account.getCount()/account.getPage_count())+((account.getCount()%account.getPage_count()) > 0 ? 1 : 0)));
		
		//取得學校清單
		SchoolMaster schoolMaster = new SchoolMaster();
		List<Map<String, Object>> schoolMasterList = schoolMasterService.getList(schoolMaster);
		model.addAttribute("schoolMasterList", schoolMasterList);
		
		//選單
		List<Map<String, Object>> menu = functionController.menu(accountSession, menuName);
		model.addAttribute("menu", menu);
		
		//分頁
		model.addAttribute("page", account.getPage());
		model.addAttribute("total_page", account.getTotal_page());
		
		return "front/teacherAccount/list";
    }
	
	@RequestMapping(value = "/teacher/add" , method = {RequestMethod.POST, RequestMethod.GET})
    public String teacherAdd(@SessionAttribute("accountSession") Account accountSession, @ModelAttribute Account account, Model model){
		
//		//取得領域清單
//		Field field = new Field();
//		List<Map<String, Object>> fieldList = subjectService.getList(field);
//		model.addAttribute("fieldList", fieldList);
		
		//取得學科清單
		Subject subject = new Subject();
		subject.setLayer("1");
		List<Map<String, Object>> subjectList = subjectService.getList(subject);
		model.addAttribute("subjectList", subjectList);
		
		//取得學制清單
		Education education = new Education();
		education.setLayer("1");
		List<Map<String, Object>> educationList = educationService.getList(education);
		model.addAttribute("educationList", educationList);
		
		//取得身份清單
		Identity identity = new Identity();
		identity.setParent_id("0");
		List<Map<String, Object>> identityList = identityService.list(identity);
		model.addAttribute("identityList", identityList);
		
		//取得學校清單
		SchoolMaster schoolMaster = new SchoolMaster();
		List<Map<String, Object>> schoolMasterList = schoolMasterService.getList(schoolMaster);
		model.addAttribute("schoolMasterList", schoolMasterList);
		
		//選單
		List<Map<String, Object>> menu = functionController.menu(accountSession, menuName);
		model.addAttribute("menu", menu);
		
		return "front/teacherAccount/add";
    }
	
	@RequestMapping(value = "/teacher/addSubmit" , method = {RequestMethod.POST, RequestMethod.GET})
    public String addSubmit(@SessionAttribute("accountSession") Account accountSession,
    		@ModelAttribute Account account,
    		HttpServletRequest pRequest,
    		Model model){
		
		String[] educationList = pRequest.getParameterValues("education")!=null ? pRequest.getParameterValues("education") : null;
		String[] fieldList = pRequest.getParameterValues("field")!=null ? pRequest.getParameterValues("field") : null;
		String content_provision = pRequest.getParameter("content_provision") == null ? "0" : "1";
		String content_audit = pRequest.getParameter("content_audit") == null ? "0" : "1";
		
		String level = "4";
		if("1".equals(account.getPosition())) {
	    	if("1".equals(content_provision)) {
	    		//創作教師
	    		level = "3";
	    	} else {
	    		//內容審核
	    		level = "4";
	    	}
		} else if("2".equals(account.getPosition())) {
			//組長
			level = "5";
		} else {
			//校長
			level = "6";
		}
    	
    	Identity identity = new Identity();
    	identity.setLevel(level);
    	Map<String, Object> levelId = identityService.getDataByLevel(identity);
    	String identity_id = levelId.get("ID").toString();
    	
    	account.setBranch(!"".equals(account.getBranch())?account.getBranch():null);
//    	account.setAddress(!"".equals(account.getAddress())?account.getAddress():null);
    	
		account.setAccount(account.getId_no());
//		account.setPassword(account.getPhone());
		account.setIdentity_id(identity_id);
		account.setContent_provision(content_provision);
		account.setContent_audit(content_audit);
		account.setStatus("1");
		account.setCreate_by(accountSession.getAccount());
		account.setUpdate_by(accountSession.getAccount());
		int id = teacherAccountService.add(account);
		
		TeacherAccountOption teacherAccountOption = new TeacherAccountOption();
		if(educationList != null) {
			for(int i=0; i<educationList.length; i++) {
//				System.out.println(educationList[i]);
				teacherAccountOption.setTeacher_account_id(String.valueOf(id));
				teacherAccountOption.setType("3");
				teacherAccountOption.setCode(educationList[i]);
				teacherAccountOption.setCreate_by(accountSession.getAccount());
				teacherAccountOptionService.add(teacherAccountOption);
			}
		}
		
		if(fieldList != null) {
			teacherAccountOption = new TeacherAccountOption();
			for(int i=0; i<fieldList.length; i++) {
//				System.out.println(fieldList[i]);
				teacherAccountOption.setTeacher_account_id(String.valueOf(id));
				teacherAccountOption.setType("1");
				teacherAccountOption.setCode(fieldList[i]);
				teacherAccountOption.setCreate_by(accountSession.getAccount());
				teacherAccountOptionService.add(teacherAccountOption);
			}
		}
		
		model.addAttribute("PATH", "/teacher");
		return "front/path";
    }
	
	@RequestMapping(value = "/teacher/content" , method = {RequestMethod.POST, RequestMethod.GET})
    public String teacherContent(@SessionAttribute("accountSession") Account accountSession, @ModelAttribute Account account, Model model){
		
		Account data = teacherAccountService.data(account);
		model.addAttribute("data", data);
		
		//選單
		List<Map<String, Object>> menu = functionController.menu(accountSession, menuName);
		model.addAttribute("menu", menu);
		
		return "front/teacherAccount/content";
    }
	
	@RequestMapping(value = "/teacher/edit" , method = {RequestMethod.POST, RequestMethod.GET})
    public String teacherEdit(@SessionAttribute("accountSession") Account accountSession, @ModelAttribute Account account, Model model){
		
		int page = account.getPage();
		int total_page = account.getTotal_page();
		int page_count = account.getPage_count();
		
		account = teacherAccountService.data(account);
		account.setPage(page);
		account.setTotal_page(total_page);
		account.setPage_count(page_count);
		model.addAttribute("account", account);
		
		//取得領域清單
		Field field = new Field();
		List<Map<String, Object>> fieldList = fieldService.getList(field);
		model.addAttribute("fieldList", fieldList);
		
		//取得學制清單
		Education education = new Education();
		education.setLayer("1");
		List<Map<String, Object>> educationList = educationService.getList(education);
		model.addAttribute("educationList", educationList);
		
		//取得身份清單
		Identity identity = new Identity();
		identity.setParent_id("0");
		List<Map<String, Object>> identityList = identityService.list(identity);
		model.addAttribute("identityList", identityList);
		
		//取得學校清單
		SchoolMaster schoolMaster = new SchoolMaster();
		List<Map<String, Object>> schoolMasterList = schoolMasterService.getList(schoolMaster);
		model.addAttribute("schoolMasterList", schoolMasterList);
		
		//取得學制清單
		TeacherAccountOption teacherAccountOption = new TeacherAccountOption();
		teacherAccountOption.setTeacher_account_id(account.getId());
		teacherAccountOption.setType("3");
		Map<String, Object> educationOption = teacherAccountOptionService.option(teacherAccountOption);
		String educationStr = educationOption!=null ? educationOption.get("OPTION").toString() : "";
		model.addAttribute("educationStr", educationStr);
		
		//取得領域清單
		teacherAccountOption = new TeacherAccountOption();
		teacherAccountOption.setTeacher_account_id(account.getId());
		teacherAccountOption.setType("1");
		Map<String, Object> fieldOption = teacherAccountOptionService.option(teacherAccountOption);
		String fieldStr = fieldOption!=null ? fieldOption.get("OPTION").toString() : "";
		model.addAttribute("fieldStr", fieldStr);
		
		//選單
		List<Map<String, Object>> menu = functionController.menu(accountSession, menuName);
		model.addAttribute("menu", menu);
		
		return "front/teacherAccount/edit";
    }
	
	@RequestMapping(value = "/teacher/editSubmit" , method = {RequestMethod.POST, RequestMethod.GET})
    public String editSubmit(@SessionAttribute("accountSession") Account accountSession,
    		@ModelAttribute Account account,
    		HttpServletRequest pRequest,
    		Model model){
		
		String[] educationList = pRequest.getParameterValues("education")!=null ? pRequest.getParameterValues("education") : null;
		String[] fieldList = pRequest.getParameterValues("field")!=null ? pRequest.getParameterValues("field") : null;
		String content_provision = pRequest.getParameter("content_provision") == null ? "0" : "1";
		String content_audit = pRequest.getParameter("content_audit") == null ? "0" : "1";
		
		String level = "4";
		if("1".equals(account.getPosition())) {
	    	if("1".equals(content_provision)) {
	    		//創作教師
	    		level = "3";
	    	} else {
	    		//內容審核
	    		level = "4";
	    	}
		} else if("2".equals(account.getPosition())) {
			//組長
			level = "5";
		} else {
			//校長
			level = "6";
		}
    	
    	Identity identity = new Identity();
    	identity.setLevel(level);
    	Map<String, Object> levelId = identityService.getDataByLevel(identity);
    	String identity_id = levelId.get("ID").toString();
    	
    	account.setBranch(!"".equals(account.getBranch())?account.getBranch():null);
//    	account.setAddress(!"".equals(account.getAddress())?account.getAddress():null);
    	
//		account.setAccount(account.getEmail());
//		account.setPassword(account.getPhone());
		account.setIdentity_id(identity_id);
		account.setContent_provision(content_provision);
		account.setContent_audit(content_audit);
		account.setStatus("1");
		account.setUpdate_by(accountSession.getAccount());
		teacherAccountService.update(account);
		
		TeacherAccountOption teacherAccountOption = new TeacherAccountOption();
		if(educationList != null) {
			teacherAccountOption.setTeacher_account_id(account.getId());
			teacherAccountOption.setType("3");
			teacherAccountOptionService.delete(teacherAccountOption);
			for(int i=0; i<educationList.length; i++) {
//				System.out.println(educationList[i]);
				teacherAccountOption.setTeacher_account_id(account.getId());
				teacherAccountOption.setType("3");
				teacherAccountOption.setCode(educationList[i]);
				teacherAccountOption.setCreate_by(accountSession.getAccount());
				teacherAccountOptionService.add(teacherAccountOption);
			}
		}
		
		if(fieldList != null) {
			teacherAccountOption = new TeacherAccountOption();
			teacherAccountOption.setTeacher_account_id(account.getId());
			teacherAccountOption.setType("1");
			teacherAccountOptionService.delete(teacherAccountOption);
			for(int i=0; i<fieldList.length; i++) {
//				System.out.println(fieldList[i]);
				teacherAccountOption.setTeacher_account_id(account.getId());
				teacherAccountOption.setType("1");
				teacherAccountOption.setCode(fieldList[i]);
				teacherAccountOption.setCreate_by(accountSession.getAccount());
				teacherAccountOptionService.add(teacherAccountOption);
			}
		}
		
		//分頁
		model.addAttribute("page", account.getPage());
		model.addAttribute("total_page", account.getTotal_page());
		model.addAttribute("page_count", account.getPage_count());
		
		model.addAttribute("PATH", "/teacher");
		return "front/path";
    }
	
	//實現Spring Boot 的檔案下載功能，對映網址為/download
    @RequestMapping("/teacher/download")
    public String downloadFile(HttpServletRequest request,
                               HttpServletResponse response) throws UnsupportedEncodingException {

        // 獲取指定目錄下的第一個檔案
        File scFileDir = new File(teacherDownload);
        File TrxFiles[] = scFileDir.listFiles();
        String fileName = "";
        for(int i=0; i<TrxFiles.length; i++) {
        	if(TrxFiles[i].toString().indexOf(teacherUploadName) >= 0) {
        		fileName = TrxFiles[i].getName();		//下載的檔名
        	}
        }
        
        // 如果檔名不為空，則進行下載
        if (fileName != null) {
            //設定檔案路徑
            String realPath = teacherDownload+"/";
            File file = new File(realPath, fileName);

            // 如果檔名存在，則進行下載
            if (file.exists()) {

                // 配置檔案下載
                response.setHeader("content-type", "application/octet-stream");
                response.setContentType("application/octet-stream");
                // 下載檔案能正常顯示中文
                response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(teacherFileName, "UTF-8"));
//                response.setHeader("Content-Disposition:", "attachment;filename*=utf-8''" + URLEncoder.encode(teacherFileName, "UTF-8"));

                // 實現檔案下載
                byte[] buffer = new byte[1024];
                FileInputStream fis = null;
                BufferedInputStream bis = null;
                try {
                    fis = new FileInputStream(file);
                    bis = new BufferedInputStream(fis);
                    OutputStream os = response.getOutputStream();
                    int i = bis.read(buffer);
                    while (i != -1) {
                        os.write(buffer, 0, i);
                        i = bis.read(buffer);
                    }
//                    System.out.println("Download the song successfully!");
                }
                catch (Exception e) {
//                    System.out.println("Download the song failed!");
                }
                finally {
                    if (bis != null) {
                        try {
                            bis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return null;
    }
	
	@RequestMapping(value = "/teacher/import/excel" , method = {RequestMethod.GET, RequestMethod.POST})
	public String importExcel(
			@SessionAttribute("accountSession")Account accountSession,
			@ModelAttribute Account account,
			Model model,
			HttpServletRequest pRequest) {
		
		try {
			
			Part filePart = pRequest.getPart("import");
			InputStream is = filePart.getInputStream();
			Workbook book = Workbook.getWorkbook(is);  
	        Sheet sheet = book.getSheet(0);
	        
	        int rowCount = sheet.getRows();
	        
	        for(int i=1	; i<rowCount; i++) {
	        	
	        	String name = sheet.getCell(0, i).getContents() == "" ? null : sheet.getCell(0, i).getContents();
	        	String school_master_id = sheet.getCell(1, i).getContents() == "" ? null : sheet.getCell(1, i).getContents();
	        	String id_no = sheet.getCell(2, i).getContents() == "" ? null : sheet.getCell(2, i).getContents();
	        	String phone = sheet.getCell(3, i).getContents() == "" ? null : sheet.getCell(3, i).getContents();
	        	String email = sheet.getCell(4, i).getContents() == "" ? null : sheet.getCell(4, i).getContents();
	        	String address = sheet.getCell(5, i).getContents() == "" ? null : sheet.getCell(5, i).getContents();
	        	String bank = sheet.getCell(6, i).getContents() == "" ? null : sheet.getCell(6, i).getContents();
	        	String branch = sheet.getCell(7, i).getContents() == "" ? null : sheet.getCell(7, i).getContents();
	        	String remittance_account = sheet.getCell(8, i).getContents() == "" ? null : sheet.getCell(8, i).getContents();
	        	String field_name = sheet.getCell(9, i).getContents() == "" ? null : sheet.getCell(9, i).getContents();
	        	String content_provision = "".equals(sheet.getCell(10, i).getContents()) ? null : ("是".equals(sheet.getCell(10, i).getContents()) ? "1" : "0");
	        	String content_audit = "".equals(sheet.getCell(11, i).getContents()) ? null : ("是".equals(sheet.getCell(101, i).getContents()) ? "1" : "0");
	        	String position = "1";
	        	String status = "1";
	        	
	        	if(name == null) {
	        		break;
	        	}
	        	
	        	if(email == null) {
	        		continue;
	        	}
	        	
	        	if(phone == null) {
	        		continue;
	        	}
	        	
	        	String level = "4";
	        	if("1".equals(content_provision)) {
	        		level = "3";
	        	} else {
	        		level = "4";
	        	}
	        	
	        	Identity identity = new Identity();
	        	identity.setLevel(level);
	        	Map<String, Object> levelId = identityService.getDataByLevel(identity);
	        	String identity_id = levelId.get("ID").toString();
	        	
//	        	SchoolMaster schoolMaster = new SchoolMaster();
//	        	schoolMaster.setCountry_name(country_name);
//	        	schoolMaster.setName(school_name);
//	        	Map<String ,Object> schoolId = schoolMasterService.searchName(schoolMaster);
//	        	String school_master_id = schoolId!=null ? schoolId.get("ID").toString() : null;
	        	
	        	Field field = new Field();
	        	field.setName(field_name);
	        	Map<String ,Object> fieldId = fieldService.searchName(field);
	        	String field_id = fieldId!=null ? fieldId.get("ID").toString() : null;
	        	
	        	account = new Account();
	        	account.setAccount(id_no);
	        	account.setPassword(phone);
	        	account.setName(name);
	        	account.setSchool_master_id(school_master_id);
	        	account.setId_no(id_no);
//	        	account.setPhone(phone);
	        	account.setEmail(email);
//	        	account.setAddress(address);
	        	account.setBank(bank);
	        	account.setBranch(branch);
	        	account.setRemittance_account(remittance_account);
//	        	account.setAddress(address);
	        	account.setField_id(field_id);
	        	account.setIdentity_id(identity_id);
	        	account.setPosition(position);
	        	account.setContent_provision(content_provision);
	        	account.setContent_audit(content_audit);
	        	account.setStatus(status);
	        	account.setCreate_by(accountSession.getAccount());
	        	account.setUpdate_by(accountSession.getAccount());
	        	
				teacherAccountService.add(account);

	        }
			
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		model.addAttribute("PATH", "/teacher");
		return "front/path";
		
	}
	
	/**
	 * 老師授權合約
	 */
	@RequestMapping(value = "/contract/teacher" , method = {RequestMethod.GET, RequestMethod.POST})
	public String contract(Model model, @SessionAttribute("accountSession") Account accountSession, @ModelAttribute Contract contract) {
		
		//設定每頁筆數
		if(contract.getPage_count() == null) {
			contract.setPage_count(10);
		}
		model.addAttribute("page_count", contract.getPage_count());
		
		if(contract.getPage() == null) {
			contract.setPage(1);
		}
		
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		int count = 0;
		
		if("3".equals(accountSession.getLevel())) {
			contract.setTeacher_id(accountSession.getId());
		}
		
		list = contractService.list(contract);
		count = contractService.count(contract);
		
		contract.setCount(count);
		contract.setTotal_page((contract.getCount()/contract.getPage_count())<1 ? 1 : ((contract.getCount()/contract.getPage_count())+((contract.getCount()%contract.getPage_count()) > 0 ? 1 : 0)));
		
		model.addAttribute("list", list);
		
		//選單
		List<Map<String, Object>> menu = functionController.menu(accountSession, menuName);
		model.addAttribute("menu", menu);
		
		//分頁
		model.addAttribute("page", contract.getPage());
		model.addAttribute("total_page", contract.getTotal_page());
		
		return "front/contract/teacher/list";
	}
	
	@RequestMapping(value = "/contract/teacher/add" , method = {RequestMethod.GET, RequestMethod.POST})
	public String contractAdd(Model model, @SessionAttribute("accountSession") Account accountSession, @ModelAttribute Contract contract) {
		
		//取得學制清單
		Education education = new Education();
		education.setLayer("1");
		List<Map<String, Object>> educationList = educationService.getList(education);
		model.addAttribute("educationList", educationList);
		
		//取得學科清單
		Subject subject = new Subject();
		subject.setLayer("1");
		List<Map<String, Object>> subjectList = subjectService.getList(subject);
		model.addAttribute("subjectList", subjectList);
		
		//選單
		List<Map<String, Object>> menu = functionController.menu(accountSession, menuName);
		model.addAttribute("menu", menu);
		
		return "front/contract/teacher/add";
	}
	
	@RequestMapping(value = "/contract/teacher/addSubmit" , method = {RequestMethod.GET, RequestMethod.POST})
	public String contractAddSubmit(Model model,
			@SessionAttribute("accountSession") Account accountSession,
			@ModelAttribute Contract contract,
//			@RequestParam("tkbContractFile") MultipartFile tkbContractFile,
			@RequestParam("csofeContractFile") MultipartFile csofeContractFile
			) {
		
		try {
			
			String subject_id = contract.getSubject_id();
			
			contract.setTkb_contract_num(null);
			contract.setTkb_contract_file(null);
			contract.setTkb_contract_name(null);
			contract.setTkb_partya(null);
			contract.setTkb_partyb(null);
//			contract.setTkb_contract_file(!"".equals(tkbContractFile.getOriginalFilename()) ? commonService.uploadFileSaveDateName(tkbContractFile, uploadedFolder+"file/contract/") : null);
//			contract.setTkb_contract_name(tkbContractFile.getOriginalFilename());
			contract.setCsofe_contract_file(!"".equals(csofeContractFile.getOriginalFilename()) ? commonService.uploadFileSaveDateName(csofeContractFile, uploadedFolder+"file/contract/") : null);
			contract.setCsofe_contract_name(csofeContractFile.getOriginalFilename());
			contract.setLesson_num((contract.getLesson_num()==null || "".equals(contract.getLesson_num())) ? "0" : contract.getLesson_num());
			contract.setBasic_num((contract.getBasic_num()==null || "".equals(contract.getBasic_num())) ? "0" : contract.getBasic_num());
			contract.setQuestions_group_num((contract.getQuestions_group_num()==null || "".equals(contract.getQuestions_group_num())) ? "0" : contract.getQuestions_group_num());
			contract.setCreate_by(accountSession.getAccount());;
			contract.setUpdate_by(accountSession.getAccount());
			int id = contractService.add(contract);
			
			//設定學制代碼
			String educationCode = "國小".equals(contract.getSubject_name()) ? "A" : "國中".equals(contract.getSubject_name()) ? "B" : "高中".equals(contract.getSubject_name()) ? "C" : "D";
			
			//修改合約序號，規則：年月日+流水號後四碼=共10碼
			contract = new Contract();
			contract.setId(String.valueOf(id));
			contract.setContract_id("T"+educationCode);
			contract.setSubject_id(subject_id);
			contractService.updateTeacherId(contract);
			
		} catch(Exception e) {
//			System.out.println("error:"+e.getMessage());
			logger.error("error:"+e.getMessage(), dateFormat.format(new Date()));
		}
		//選單
		List<Map<String, Object>> menu = functionController.menu(accountSession, menuName);
		model.addAttribute("menu", menu);
		
		model.addAttribute("PATH", "/contract/teacher");
		return "front/path";
	}
	
	@RequestMapping(value = "/contract/teacher/content" , method = {RequestMethod.GET, RequestMethod.POST})
	public String contractTeacherContent(Model model, @SessionAttribute("accountSession") Account accountSession, @ModelAttribute Contract contract) {
		
		//取得合約資料
		contract = contractService.data(contract);
		model.addAttribute("contract", contract);
		
		//選單
		List<Map<String, Object>> menu = functionController.menu(accountSession, menuName);
		model.addAttribute("menu", menu);
		
		return "front/contract/teacher/content";
	}
	
	/**
	 * 素材授權合約
	 */
	@RequestMapping(value = "/contract/material" , method = {RequestMethod.GET, RequestMethod.POST})
	public String contractMaterial(Model model, @SessionAttribute("accountSession") Account accountSession, @ModelAttribute ContractMaterial contractMaterial) {
		
		//設定每頁筆數
		if(contractMaterial.getPage_count() == null) {
			contractMaterial.setPage_count(10);
		}
		model.addAttribute("page_count", contractMaterial.getPage_count());
		
		if(contractMaterial.getPage() == null) {
			contractMaterial.setPage(1);
		}
		
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		int count = 0;
		
		if("3".equals(accountSession.getLevel())) {
			contractMaterial.setTeacher_id(accountSession.getId());
		}
		
		list = contractMaterialService.list(contractMaterial);
		count = contractMaterialService.count(contractMaterial);
		
		contractMaterial.setCount(count);
		contractMaterial.setTotal_page((contractMaterial.getCount()/contractMaterial.getPage_count())<1 ? 1 : ((contractMaterial.getCount()/contractMaterial.getPage_count())+((contractMaterial.getCount()%contractMaterial.getPage_count()) > 0 ? 1 : 0)));
		
		model.addAttribute("list", list);
		
		//選單
		List<Map<String, Object>> menu = functionController.menu(accountSession, menuName);
		model.addAttribute("menu", menu);
		
		//分頁
		model.addAttribute("page", contractMaterial.getPage());
		model.addAttribute("total_page", contractMaterial.getTotal_page());
		
		return "front/contract/material/list";
	}
	
	@RequestMapping(value = "/contract/material/add" , method = {RequestMethod.GET, RequestMethod.POST})
	public String contractMaterialAdd(Model model, @SessionAttribute("accountSession") Account accountSession, @ModelAttribute ContractMaterial contractMaterial) {
		
		Account account = new Account();
		String name = "";
		
		if("1".equals(contractMaterial.getLp_type())) {
			LessonPlan lessonPlan = new LessonPlan();
			lessonPlan.setId(contractMaterial.getLp_id());
			lessonPlan = lessonPlanService.data(lessonPlan);
			name = lessonPlan.getName();
			
			account.setAccount(lessonPlan.getCreate_by());
		} else {
			Proposition proposition = new Proposition();
			proposition.setId(contractMaterial.getLp_id());
			proposition = propositionService.data(proposition);
			name = proposition.getName();
			
			account.setAccount(proposition.getCreate_by());
		}
		
		//教案命題名稱
		model.addAttribute("name", name);
		
		//老師名稱
		Map<String, Object> getDataByAccount = teacherAccountService.getDataByAccount(account);
		
		contractMaterial.setTeacher_id(getDataByAccount!=null ? getDataByAccount.get("ID").toString() : "");
		
		//取得領域資料
		Field field = new Field();
		field.setId(contractMaterial.getField_id());
		field = fieldService.data(field);
		model.addAttribute("fieldName", field.getName());
		
		//取得學制清單
		Education education = new Education();
		education.setId(contractMaterial.getEducation_id());
		education = educationService.data(education);
		model.addAttribute("educationName", education.getName());
		
		//選單
		List<Map<String, Object>> menu = functionController.menu(accountSession, menuName);
		model.addAttribute("menu", menu);
		
		return "front/contract/material/add";
	}
	
	@RequestMapping(value = "/contract/material/addSubmit" , method = {RequestMethod.GET, RequestMethod.POST})
	public String contractAddSubmit(Model model,
			@SessionAttribute("accountSession") Account accountSession,
			@ModelAttribute ContractMaterial contractMaterial,
			@RequestParam("tkbContractFile") MultipartFile tkbContractFile,
			@RequestParam("csofeContractFile") MultipartFile csofeContractFile,
			HttpServletRequest pRequest
			) {
		
		try {
			
			contractMaterial.setTkb_contract_file(!"".equals(tkbContractFile.getOriginalFilename()) ? commonService.uploadFileSaveDateName(tkbContractFile, uploadedFolder+"file/contract/") : null);
			contractMaterial.setTkb_contract_name(tkbContractFile.getOriginalFilename());
			contractMaterial.setCsofe_contract_file(!"".equals(csofeContractFile.getOriginalFilename()) ? commonService.uploadFileSaveDateName(csofeContractFile, uploadedFolder+"file/contract/") : null);
			contractMaterial.setCsofe_contract_name(csofeContractFile.getOriginalFilename());
			contractMaterial.setLesson_num((contractMaterial.getLesson_num()==null || "".equals(contractMaterial.getLesson_num())) ? "0" : contractMaterial.getLesson_num());
			contractMaterial.setBasic_num((contractMaterial.getBasic_num()==null || "".equals(contractMaterial.getBasic_num())) ? "0" : contractMaterial.getBasic_num());
			contractMaterial.setQuestions_group_num((contractMaterial.getQuestions_group_num()==null || "".equals(contractMaterial.getQuestions_group_num())) ? "0" : contractMaterial.getQuestions_group_num());
			contractMaterial.setCreate_by(accountSession.getAccount());
			contractMaterial.setUpdate_by(accountSession.getAccount());
			int id = contractMaterialService.add(contractMaterial);
			//修改合約序號，規則：年月日+流水號後四碼=共10碼
			contractMaterial = new ContractMaterial();
			contractMaterial.setId(String.valueOf(id));
			contractMaterialService.updateTeacherId(contractMaterial);
			
			ContractMaterialOption contractMaterialOption = new ContractMaterialOption();
			String[] contractType = pRequest.getParameterValues("contractType");
			for(int i=0; i<contractType.length; i++) {
				contractMaterialOption = new ContractMaterialOption();
				contractMaterialOption.setContract_material_id(String.valueOf(id));
				contractMaterialOption.setMaterial_type(contractType[i]);
				contractMaterialOption.setCreate_by(accountSession.getAccount());
				contractMaterialOptionService.add(contractMaterialOption);
			}
			
		} catch(Exception e) {
//			System.out.println("error:"+e.getMessage());
			logger.error("error:"+e.getMessage(), dateFormat.format(new Date()));
		}
		//選單
		List<Map<String, Object>> menu = functionController.menu(accountSession, menuName);
		model.addAttribute("menu", menu);
		
		model.addAttribute("PATH", "/contract/material");
		return "front/path";
	}
	
	@RequestMapping(value = "/contract/material/content" , method = {RequestMethod.GET, RequestMethod.POST})
	public String contractTeacherContent(Model model, @SessionAttribute("accountSession") Account accountSession, @ModelAttribute ContractMaterial contractMaterial) {
		
		//取得合約資料
		contractMaterial = contractMaterialService.data(contractMaterial);
		model.addAttribute("contractMaterial", contractMaterial);
		
		//選單
		List<Map<String, Object>> menu = functionController.menu(accountSession, menuName);
		model.addAttribute("menu", menu);
		
		return "front/contract/material/content";
	}
	
	/**
	 * 教案
	 */
	@RequestMapping(value = "/lesson" , method = {RequestMethod.POST, RequestMethod.GET})
    public String lesson(@SessionAttribute("accountSession") Account accountSession, @ModelAttribute LessonPlan lessonPlan, @ModelAttribute ContractMaterial contractMaterial, Model model){
		
		//設定每頁筆數
		if(lessonPlan.getPage_count() == null) {
			lessonPlan.setPage_count(10);
		}
		
		model.addAttribute("page_count", lessonPlan.getPage_count());
		
		if(lessonPlan.getPage() == null) {
			lessonPlan.setPage(1);
		}
		
		int level = Integer.valueOf(accountSession.getLevel());
		
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		int count = 0;
		
		if(level <= 2) {
			list = lessonPlanService.manageList(lessonPlan);
			count = lessonPlanService.manageCount(lessonPlan);
		} else if(level == 3) {
			lessonPlan.setCreate_by(accountSession.getAccount());
			list = lessonPlanService.teacherList(lessonPlan);
			count = lessonPlanService.teacherCount(lessonPlan);
			Contract contract = new Contract();
			contract.setTeacher_id(accountSession.getId());
			//總需上傳數-已上傳=未上傳數
			Map<String, Object> uploadNum = contractService.uploadNum(contract);
			//教案總需上傳數
			int lessonNum = Integer.valueOf(uploadNum.get("LESSON_NUM").toString());
			model.addAttribute("lessonNum", lessonNum);
			//合約未完成數
//			int undoneCount = contractService.undoneCount(contract);
			List<Map<String, Object>> contractList = contractService.getList(contract);
			int undoneCount = contractList != null ? contractList.size() : 0;
			model.addAttribute("undoneCount", undoneCount);
			//已上傳數
			lessonPlan.setFile_status("A");
			int addCount = lessonPlanService.uploadStatusCount(lessonPlan);
			model.addAttribute("addCount", addCount);
			//待修訂數
			lessonPlan.setUpload_status("N");
			int editCount = lessonPlanService.uploadStatusCount(lessonPlan);
			model.addAttribute("editCount", editCount);
			//已完稿數
			lessonPlan.setUpload_status("C");
			int doneCount = lessonPlanService.uploadStatusCount(lessonPlan);
			model.addAttribute("doneCount", doneCount);
		} else if(level == 4) {
			lessonPlan.setAuditor(accountSession.getAccount());
			list = lessonPlanService.auditList(lessonPlan);
			count = lessonPlanService.auditCount(lessonPlan);
		} else if(level == 5) {
			lessonPlan.setField_id(accountSession.getField_list());
			lessonPlan.setEducation_id(accountSession.getEducation_list());
			list = lessonPlanService.principalList(lessonPlan);
			count = lessonPlanService.principalCount(lessonPlan);
		} else if(level == 6) {
			lessonPlan.setField_id(accountSession.getField_list());
			lessonPlan.setEducation_id(accountSession.getEducation_list());
			list = lessonPlanService.leaderList(lessonPlan);
			count = lessonPlanService.leaderCount(lessonPlan);
		} else if(level == 7) {
			list = lessonPlanService.secretaryGeneralList(lessonPlan);
			count = lessonPlanService.secretaryGeneralCount(lessonPlan);
		} else {
			list = null;
			count = 0;
		}
		
		lessonPlan.setCount(count);
		lessonPlan.setTotal_page((lessonPlan.getCount()/lessonPlan.getPage_count())<1 ? 1 : ((lessonPlan.getCount()/lessonPlan.getPage_count())+((lessonPlan.getCount()%lessonPlan.getPage_count()) > 0 ? 1 : 0)));
		
		model.addAttribute("list", list);
		
		//選單
		List<Map<String, Object>> menu = functionController.menu(accountSession, menuName);
		model.addAttribute("menu", menu);
		
		//分頁
		model.addAttribute("page", lessonPlan.getPage());
		model.addAttribute("total_page", lessonPlan.getTotal_page());
		
		return "front/lessonPlan/list";
    }
	
	@RequestMapping(value = "/lesson/add" , method = {RequestMethod.POST, RequestMethod.GET})
    public String lessonAdd(@SessionAttribute("accountSession") Account accountSession, @ModelAttribute LessonPlan lessonPlan, Model model){
		
		//取得合約清單
		Contract contract = new Contract();
		contract.setTeacher_id(accountSession.getId());
		List<Map<String, Object>> contractList = contractService.getList(contract);
		model.addAttribute("contractList", contractList);
		
		//設定合約
		lessonPlan.setContract_id(contractList.get(0).get("CONTRACT_ID").toString());
		//設定學制
		lessonPlan.setEducation_id(contractList.get(0).get("EDUCATION_ID").toString());
		//設定學科
		lessonPlan.setSubject_id(contractList.get(0).get("SUBJECT_ID").toString());
		
		//取得領域清單
		Field field = new Field();
		List<Map<String, Object>> fieldList = fieldService.getList(field);
		model.addAttribute("fieldList", fieldList);
		
		//取得學制清單
		Education education = new Education();
		education.setLayer("1");
		List<Map<String, Object>> educationList = educationService.getList(education);
		model.addAttribute("educationList", educationList);
		
		//取得學科清單
		Subject subject = new Subject();
		subject.setLayer("1");
		List<Map<String, Object>> subjectList = subjectService.getList(subject);
		model.addAttribute("subjectList", subjectList);
		
		//選單
		List<Map<String, Object>> menu = functionController.menu(accountSession, menuName);
		model.addAttribute("menu", menu);
		
		return "front/lessonPlan/add";
    }
	
	@RequestMapping(value = "/lesson/addSubmit" , method = {RequestMethod.GET, RequestMethod.POST})
	public String lessonAddSubmit(Model model,
			@SessionAttribute("accountSession") Account accountSession,
			@ModelAttribute LessonPlan lessonPlan,
			@RequestParam("fileName") MultipartFile fileName,
//			@RequestParam("csofeContractFile") MultipartFile csofeContractFile
			HttpServletRequest pRequest
			) {
		
		try {
			
			//取得相關領域審核人
//			Contract contract = new Contract();
//			contract.setContract_id(lessonPlan.getContract_id());
//			Map<String, Object> callNum = contractService.callNum(contract, accountSession);
			Account account = new Account();
			account.setId(accountSession.getId());
			account.setField_id(lessonPlan.getField_id());
			Map<String, Object> callNum = teacherAccountService.callNum(account);
			lessonPlan.setAuditor(callNum.get("ACCOUNT").toString());
			lessonPlan.setFile_status("Y");
			lessonPlan.setCreate_by(accountSession.getAccount());
			lessonPlan.setUpdate_by(accountSession.getAccount());
			int id = lessonPlanService.add(lessonPlan);
			
			LessonPlanOption lessonPlanOption = new LessonPlanOption();
			
			//跨學科
			String[] subject = pRequest.getParameterValues("crossSubject");
			for(int i=0; i<subject.length; i++) {
				lessonPlanOption = new LessonPlanOption();
				lessonPlanOption.setLesson_plan_id(String.valueOf(id));
				lessonPlanOption.setType("2");
				lessonPlanOption.setCode(subject[i]);
				lessonPlanOption.setCreate_by(accountSession.getAccount());
				lessonPlanOptionService.add(lessonPlanOption);
			}
			
			//年級
			String[] grade = pRequest.getParameterValues("grade");
			for(int i=0; i<grade.length; i++) {
				lessonPlanOption = new LessonPlanOption();
				lessonPlanOption.setLesson_plan_id(String.valueOf(id));
				lessonPlanOption.setType("4");
				lessonPlanOption.setCode(grade[i]);
				lessonPlanOption.setCreate_by(accountSession.getAccount());
				lessonPlanOptionService.add(lessonPlanOption);
			}
			
			//教案檔案
			LessonPlanFile lessonPlanFile = new LessonPlanFile();
			lessonPlanFile.setLesson_plan_id(String.valueOf(id));
			
			String uploadName = commonService.uploadFileSaveDateName(fileName, uploadedFolder+"file/lessonPlan/");
			lessonPlanFile.setType("1");
			lessonPlanFile.setUpload_name(uploadName);
			lessonPlanFile.setFile_name(fileName.getOriginalFilename());
			lessonPlanFile.setCreate_by(accountSession.getAccount());
			lessonPlanFile.setUpdate_by(accountSession.getAccount());
			lessonPlanFileService.add(lessonPlanFile);
			
			//新增審核人
			LessonPlanAudit lessonPlanAudit = new LessonPlanAudit();
			lessonPlanAudit.setLesson_plan_id(String.valueOf(id));
			lessonPlanAudit.setAuditor(callNum.get("ACCOUNT").toString());
			lessonPlanAudit.setFile_status("Y");
			lessonPlanAudit.setUpload_status("Y");
			lessonPlanAudit.setCreate_by(accountSession.getAccount());
			lessonPlanAudit.setUpdate_by(accountSession.getAccount());
			lessonPlanAuditService.add(lessonPlanAudit);
			
		} catch(Exception e) {
//			System.out.println("error:"+e.getMessage());
			logger.error("error:"+e.getMessage(), dateFormat.format(new Date()));
		}
		//選單
		List<Map<String, Object>> menu = functionController.menu(accountSession, menuName);
		model.addAttribute("menu", menu);
		
		model.addAttribute("PATH", "/lesson");
		return "front/path";
	}
	
	@RequestMapping(value = "/lesson/content" , method = {RequestMethod.POST, RequestMethod.GET})
    public String lessonContent(@SessionAttribute("accountSession") Account accountSession, @ModelAttribute LessonPlan lessonPlan, Model model){
		
		//取得教案資料
		LessonPlan data = lessonPlanService.data(lessonPlan);
		model.addAttribute("data", data);
		
		//取得學習領域資料
		Field field = new Field();
		field.setId(data.getField_id());
		field = fieldService.data(field);
		model.addAttribute("field_name", field.getName());
		
		//取得學科清單
		LessonPlanOption lessonPlanOption = new LessonPlanOption();
		lessonPlanOption.setLesson_plan_id(lessonPlan.getId());
		lessonPlanOption.setType("2");
		Map<String, Object> subjectOption = lessonPlanOptionService.subjectOption(lessonPlanOption);
		String subject = subjectOption.get("OPTION").toString();
		model.addAttribute("subject", subject);
		
		//取得學制資料
		Education education = new Education();
		education.setId(data.getEducation_id());
		education = educationService.data(education);
		model.addAttribute("education_name", education.getName());
		
		//取得年級清單
		lessonPlanOption = new LessonPlanOption();
		lessonPlanOption.setLesson_plan_id(lessonPlan.getId());
		lessonPlanOption.setType("4");
		Map<String, Object> gradeOption = lessonPlanOptionService.gradeOption(lessonPlanOption);
		String grade = gradeOption.get("OPTION").toString();
		model.addAttribute("grade", grade);
		
		//取得內容上傳歷程
		LessonPlanFile lessonPlanFile = new LessonPlanFile();
		lessonPlanFile.setLesson_plan_id(lessonPlan.getId());
//		lessonPlanFile.setType("1");
		List<Map<String, Object>> contentList = lessonPlanFileService.historyList(lessonPlanFile);
		model.addAttribute("contentList", contentList);
		
		//取得審核回饋歷程
//		lessonPlanFile = new LessonPlanFile();
//		lessonPlanFile.setLesson_plan_id(lessonPlan.getId());
//		lessonPlanFile.setType("2");
//		List<Map<String, Object>> auditList = lessonPlanFileService.historyList(lessonPlanFile);
//		model.addAttribute("auditList", auditList);
		
		//取得最新檔案
		model.addAttribute("nowFile", contentList.get(0));
		
		//選單
		List<Map<String, Object>> menu = functionController.menu(accountSession, menuName);
		model.addAttribute("menu", menu);
		
		return "front/lessonPlan/content";
    }
	
	@RequestMapping(value = "/lesson/update" , method = {RequestMethod.POST, RequestMethod.GET})
    public String lessonUpdate(@SessionAttribute("accountSession") Account accountSession, @ModelAttribute LessonPlan lessonPlan, Model model){
		
		//取得教案資料
		LessonPlan data = lessonPlanService.data(lessonPlan);
		model.addAttribute("data", data);
		
		//取得領域清單
		Field field = new Field();
		List<Map<String, Object>> fieldList = fieldService.getList(field);
		model.addAttribute("fieldList", fieldList);
		
		//取得學制清單
		Education education = new Education();
		education.setLayer("1");
		List<Map<String, Object>> educationList = educationService.getList(education);
		model.addAttribute("educationList", educationList);
		
		LessonPlanOption lessonPlanOption = new LessonPlanOption();
		lessonPlanOption.setLesson_plan_id(lessonPlan.getId());
		lessonPlanOption.setType("2");
		Map<String, Object> subjectOption = lessonPlanOptionService.option(lessonPlanOption);
		String so = subjectOption.get("OPTION").toString();
		model.addAttribute("subjectOption", so);
		
		//取得學科清單
		Subject subject = new Subject();
		subject.setLayer("1");
		List<Map<String, Object>> subjectList = subjectService.getList(subject);
		model.addAttribute("subjectList", subjectList);
		
		//取得年級清單
		education = new Education();
		education.setParent_id(data.getEducation_id());
		List<Map<String, Object>> gradeList = educationService.getChild(education);
		model.addAttribute("gradeList", gradeList);
		
		lessonPlanOption = new LessonPlanOption();
		lessonPlanOption.setLesson_plan_id(lessonPlan.getId());
		lessonPlanOption.setType("4");
		Map<String, Object> gradeOption = lessonPlanOptionService.option(lessonPlanOption);
		String go = gradeOption.get("OPTION").toString();
		model.addAttribute("gradeOption", go);
		
		//取得內容上傳歷程
		LessonPlanFile lessonPlanFile = new LessonPlanFile();
		lessonPlanFile.setLesson_plan_id(lessonPlan.getId());
		lessonPlanFile.setType("1");
		List<Map<String, Object>> contentList = lessonPlanFileService.historyList(lessonPlanFile);
		model.addAttribute("nowFile", contentList.get(0));
		
		//選單
		List<Map<String, Object>> menu = functionController.menu(accountSession, menuName);
		model.addAttribute("menu", menu);
		
		return "front/lessonPlan/update";
    }
	
	@RequestMapping(value = "/lesson/updateSubmit" , method = {RequestMethod.GET, RequestMethod.POST})
	public String lessonUpdateSubmit(Model model,
			@SessionAttribute("accountSession") Account accountSession,
			@ModelAttribute LessonPlan lessonPlan,
			HttpServletRequest pRequest
			) {
		
		try {
			
			lessonPlan.setUpdate_by(accountSession.getAccount());
			lessonPlanService.update(lessonPlan);
			
			LessonPlanOption lessonPlanOption = new LessonPlanOption();
			lessonPlanOption.setLesson_plan_id(lessonPlan.getId());
			lessonPlanOptionService.delete(lessonPlanOption);
			
			//學科
			String[] subject = pRequest.getParameterValues("subject");
			for(int i=0; i<subject.length; i++) {
				lessonPlanOption = new LessonPlanOption();
				lessonPlanOption.setLesson_plan_id(lessonPlan.getId());
				lessonPlanOption.setType("2");
				lessonPlanOption.setCode(subject[i]);
				lessonPlanOption.setCreate_by(accountSession.getAccount());
				lessonPlanOptionService.add(lessonPlanOption);
			}
			
			//年級
			String[] grade = pRequest.getParameterValues("grade");
			for(int i=0; i<grade.length; i++) {
				lessonPlanOption = new LessonPlanOption();
				lessonPlanOption.setLesson_plan_id(lessonPlan.getId());
				lessonPlanOption.setType("4");
				lessonPlanOption.setCode(grade[i]);
				lessonPlanOption.setCreate_by(accountSession.getAccount());
				lessonPlanOptionService.add(lessonPlanOption);
			}
			
			
		} catch(Exception e) {
//			System.out.println("error:"+e.getMessage());
			logger.error("error:"+e.getMessage(), dateFormat.format(new Date()));
		}
		//選單
		List<Map<String, Object>> menu = functionController.menu(accountSession, menuName);
		model.addAttribute("menu", menu);
		
		model.addAttribute("PATH", "/lesson");
		return "front/path";
	}
	
	@RequestMapping(value = "/lesson/audit" , method = {RequestMethod.GET, RequestMethod.POST})
	public String lessonAudit(Model model,
			@SessionAttribute("accountSession") Account accountSession,
			@ModelAttribute LessonPlan lessonPlan,
			@RequestParam(value = "fileName", required = false) MultipartFile fileName,
//			@RequestParam("csofeContractFile") MultipartFile csofeContractFile
			HttpServletRequest pRequest
			) {
		
		try {
			
			String audit = pRequest.getParameter("verify");
			String file_status = "C", upload_status = "C";
			
			if("0".equals(audit)) {
				file_status = "N";
				upload_status = "N";
				//教案檔案
				LessonPlanFile lessonPlanFile = new LessonPlanFile();
				lessonPlanFile.setLesson_plan_id(lessonPlan.getId());
				
				String uploadName = commonService.uploadFileSaveDateName(fileName, uploadedFolder+"file/lessonPlan/");
				lessonPlanFile.setType("2");
				lessonPlanFile.setUpload_name(uploadName);
				lessonPlanFile.setFile_name(fileName.getOriginalFilename());
				lessonPlanFile.setCreate_by(accountSession.getAccount());
				lessonPlanFile.setUpdate_by(accountSession.getAccount());
				lessonPlanFileService.add(lessonPlanFile);
			} else {
				file_status = "C";
				upload_status = "C";
			}
			
			//審核
			lessonPlan.setFile_status(file_status);
			lessonPlan.setUpload_status(upload_status);
			lessonPlanService.audit(lessonPlan);
			
			//新增審核紀錄
			LessonPlanAudit lessonPlanAudit = new LessonPlanAudit();
			lessonPlanAudit.setLesson_plan_id(lessonPlan.getId());
			lessonPlanAudit.setAuditor(accountSession.getAccount());
			lessonPlanAudit.setFile_status(file_status);
			lessonPlanAudit.setUpload_status(upload_status);
			lessonPlanAudit.setCreate_by(accountSession.getAccount());
			lessonPlanAudit.setUpdate_by(accountSession.getAccount());
			lessonPlanAuditService.add(lessonPlanAudit);
			
		} catch(Exception e) {
//			System.out.println("error:"+e.getMessage());
			logger.error("error:"+e.getMessage(), dateFormat.format(new Date()));
		}
		//選單
		List<Map<String, Object>> menu = functionController.menu(accountSession, menuName);
		model.addAttribute("menu", menu);
		
		model.addAttribute("PATH", "/lesson");
		return "front/path";
	}
	
	@RequestMapping(value = "/lesson/edit" , method = {RequestMethod.POST, RequestMethod.GET})
    public String lessonEdit(@SessionAttribute("accountSession") Account accountSession, @ModelAttribute LessonPlan lessonPlan, Model model){
		
		//取得教案資料
		LessonPlan data = lessonPlanService.data(lessonPlan);
		model.addAttribute("data", data);
		
		//取得學習領域資料
		Field field = new Field();
		field.setId(data.getField_id());
		field = fieldService.data(field);
		model.addAttribute("field_name", field.getName());
		
		//取得學科清單
		LessonPlanOption lessonPlanOption = new LessonPlanOption();
		lessonPlanOption.setLesson_plan_id(lessonPlan.getId());
		lessonPlanOption.setType("2");
		Map<String, Object> subjectOption = lessonPlanOptionService.subjectOption(lessonPlanOption);
		String subject = subjectOption.get("OPTION").toString();
		model.addAttribute("subject", subject);
		
		//取得學制資料
		Education education = new Education();
		education.setId(data.getEducation_id());
		education = educationService.data(education);
		model.addAttribute("education_name", education.getName());
		
		//取得年級清單
		lessonPlanOption = new LessonPlanOption();
		lessonPlanOption.setLesson_plan_id(lessonPlan.getId());
		lessonPlanOption.setType("4");
		Map<String, Object> gradeOption = lessonPlanOptionService.gradeOption(lessonPlanOption);
		String grade = gradeOption.get("OPTION").toString();
		model.addAttribute("grade", grade);
		
		//取得內容上傳歷程
		LessonPlanFile lessonPlanFile = new LessonPlanFile();
		lessonPlanFile.setLesson_plan_id(lessonPlan.getId());
		lessonPlanFile.setType("1");
		List<Map<String, Object>> contentList = lessonPlanFileService.historyList(lessonPlanFile);
		model.addAttribute("contentList", contentList);
		
		//取得審核回饋歷程
		lessonPlanFile = new LessonPlanFile();
		lessonPlanFile.setLesson_plan_id(lessonPlan.getId());
		lessonPlanFile.setType("2");
		List<Map<String, Object>> auditList = lessonPlanFileService.historyList(lessonPlanFile);
		model.addAttribute("auditList", auditList);
		
		//取得最新檔案
		model.addAttribute("nowFile", contentList.get(0));
		
		//選單
		List<Map<String, Object>> menu = functionController.menu(accountSession, menuName);
		model.addAttribute("menu", menu);
		
		return "front/lessonPlan/edit";
    }
	
	@RequestMapping(value = "/lesson/editSubmit" , method = {RequestMethod.GET, RequestMethod.POST})
	public String lessonEditSubmit(Model model,
			@SessionAttribute("accountSession") Account accountSession,
			@ModelAttribute LessonPlan lessonPlan,
			@RequestParam("fileName") MultipartFile fileName,
//			@RequestParam("csofeContractFile") MultipartFile csofeContractFile
			HttpServletRequest pRequest
			) {
		
		try {
			
			String file_status = "Y", upload_status = "Y";
			
			//教案檔案
			LessonPlanFile lessonPlanFile = new LessonPlanFile();
			lessonPlanFile.setLesson_plan_id(lessonPlan.getId());
			
			String uploadName = commonService.uploadFileSaveDateName(fileName, uploadedFolder+"file/lessonPlan/");
			lessonPlanFile.setType("1");
			lessonPlanFile.setUpload_name(uploadName);
			lessonPlanFile.setFile_name(fileName.getOriginalFilename());
			lessonPlanFile.setCreate_by(accountSession.getAccount());
			lessonPlanFile.setUpdate_by(accountSession.getAccount());
			lessonPlanFileService.add(lessonPlanFile);
			
			//審核
			lessonPlan.setFile_status(file_status);
			lessonPlan.setUpload_status(upload_status);
			lessonPlanService.audit(lessonPlan);
			
			//新增審核紀錄
			LessonPlanAudit lessonPlanAudit = new LessonPlanAudit();
			lessonPlanAudit.setLesson_plan_id(lessonPlan.getId());
			lessonPlanAudit.setAuditor(accountSession.getAccount());
			lessonPlanAudit.setFile_status(file_status);
			lessonPlanAudit.setUpload_status(upload_status);
			lessonPlanAudit.setCreate_by(accountSession.getAccount());
			lessonPlanAudit.setUpdate_by(accountSession.getAccount());
			lessonPlanAuditService.add(lessonPlanAudit);
			
		} catch(Exception e) {
//			System.out.println("error:"+e.getMessage());
			logger.error("error:"+e.getMessage(), dateFormat.format(new Date()));
		}
		//選單
		List<Map<String, Object>> menu = functionController.menu(accountSession, menuName);
		model.addAttribute("menu", menu);
		
		model.addAttribute("PATH", "/lesson");
		return "front/path";
	}
	
	@RequestMapping(value = "/lesson/get/field" , method = {RequestMethod.GET, RequestMethod.POST})
	public void getField(@SessionAttribute("accountSession") Account accountSession, HttpServletRequest pRequest, HttpServletResponse pResponse, Model model) throws Exception {
		
		Field field = new Field();
		
		String id = pRequest.getParameter("id") == null ? "" : pRequest.getParameter("id");
		field.setParent_id(id);

		List<Map<String, Object>> list = fieldService.getChild(field);
		
		JSONArray tJSONArray = new JSONArray(list);
		
		pResponse.setCharacterEncoding("utf-8");
		PrintWriter out = pResponse.getWriter();
		out.write(tJSONArray.toString());
		
	}
	
	/**
	 * 命題-基本題
	 */
	@RequestMapping(value = "/proposition/basic" , method = {RequestMethod.POST, RequestMethod.GET})
    public String propositionBasic(@SessionAttribute("accountSession") Account accountSession, @ModelAttribute Proposition proposition, @ModelAttribute ContractMaterial contractMaterial, Model model){
		
		//設定每頁筆數
		if(proposition.getPage_count() == null) {
			proposition.setPage_count(10);
		}
		model.addAttribute("page_count", proposition.getPage_count());
		
		if(proposition.getPage() == null) {
			proposition.setPage(1);
		}
		
		int level = Integer.valueOf(accountSession.getLevel());
		
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		int count = 0;
		
		proposition.setQuestion_type("1");
		
		if(level <= 2) {
			list = propositionService.manageList(proposition);
			count = propositionService.manageCount(proposition);
		} else if(level == 3) {
			proposition.setCreate_by(accountSession.getAccount());
			list = propositionService.teacherList(proposition);
			count = propositionService.teacherCount(proposition);
			Contract contract = new Contract();
			contract.setTeacher_id(accountSession.getId());
			//總需上傳數-已上傳=未上傳數
			Map<String, Object> uploadNum = contractService.uploadNum(contract);
			//命題總需上傳數
			int basicNum = Integer.valueOf(uploadNum.get("BASIC_NUM").toString());
			model.addAttribute("basicNum", basicNum);
			//合約未完成數
//			int undoneCount = contractService.undoneCount(contract);
			List<Map<String, Object>> contractList = contractService.getList(contract);
			int undoneCount = contractList != null ? contractList.size() : 0;
			model.addAttribute("undoneCount", undoneCount);
			//已上傳數
			proposition.setUpload_status("Y");
			int addCount = propositionService.uploadStatusCount(proposition);
			model.addAttribute("addCount", addCount);
			//待修訂數
			proposition.setUpload_status("N");
			int editCount = propositionService.uploadStatusCount(proposition);
			model.addAttribute("editCount", editCount);
			//已完稿數
			proposition.setUpload_status("C");
			int doneCount = propositionService.uploadStatusCount(proposition);
			model.addAttribute("doneCount", doneCount);
		} else if(level == 4) {
			proposition.setAuditor(accountSession.getAccount());
			list = propositionService.auditList(proposition);
			count = propositionService.auditCount(proposition);
		} else if(level == 5) {
			proposition.setField_id(accountSession.getField_list());
			proposition.setEducation_id(accountSession.getEducation_list());
			list = propositionService.principalList(proposition);
			count = propositionService.principalCount(proposition);
		} else if(level == 6) {
			proposition.setField_id(accountSession.getField_list());
			proposition.setEducation_id(accountSession.getEducation_list());
			list = propositionService.leaderList(proposition);
			count = propositionService.leaderCount(proposition);
		} else if(level == 7) {
			list = propositionService.secretaryGeneralList(proposition);
			count = propositionService.secretaryGeneralCount(proposition);
		} else {
			list = null;
			count = 0;
		}
		
		proposition.setCount(count);
		proposition.setTotal_page((proposition.getCount()/proposition.getPage_count())<1 ? 1 : ((proposition.getCount()/proposition.getPage_count())+((proposition.getCount()%proposition.getPage_count()) > 0 ? 1 : 0)));
		
		model.addAttribute("list", list);
		
		//選單
		List<Map<String, Object>> menu = functionController.menu(accountSession, menuName);
		model.addAttribute("menu", menu);
		
		//分頁
		model.addAttribute("page", proposition.getPage());
		model.addAttribute("total_page", proposition.getTotal_page());
		
		return "front/proposition/basic/list";
    }
	
	@RequestMapping(value = "/proposition/basic/add" , method = {RequestMethod.POST, RequestMethod.GET})
    public String propositionBasicAdd(@SessionAttribute("accountSession") Account accountSession, @ModelAttribute Proposition proposition, Model model){
		
		//取得合約清單
		Contract contract = new Contract();
		contract.setTeacher_id(accountSession.getId());
		List<Map<String, Object>> contractList = contractService.getList(contract);
		model.addAttribute("contractList", contractList);
		
		//取得領域清單
		Field field = new Field();
		List<Map<String, Object>> fieldList = fieldService.getList(field);
		model.addAttribute("fieldList", fieldList);
		
		//取得學制清單
		Education education = new Education();
		education.setLayer("1");
		List<Map<String, Object>> educationList = educationService.getList(education);
		model.addAttribute("educationList", educationList);
		
		//取得學科清單
		Subject subject = new Subject();
		subject.setLayer("1");
		List<Map<String, Object>> subjectList = subjectService.getList(subject);
		model.addAttribute("subjectList", subjectList);
		
		//選單
		List<Map<String, Object>> menu = functionController.menu(accountSession, menuName);
		model.addAttribute("menu", menu);
		
		return "front/proposition/basic/add";
    }
	
	@RequestMapping(value = "/proposition/basic/addSubmit" , method = {RequestMethod.GET, RequestMethod.POST})
	public String propositionBasicAddSubmit(Model model,
			@SessionAttribute("accountSession") Account accountSession,
			@ModelAttribute Proposition proposition,
			@RequestParam("fileName") MultipartFile fileName,
//			@RequestParam("csofeContractFile") MultipartFile csofeContractFile
			HttpServletRequest pRequest
			) {
		
		try {
			
			//取得相關領域審核人
//			Contract contract = new Contract();
//			contract.setContract_id(proposition.getContract_id());
//			Map<String, Object> callNum = contractService.callNum(contract, accountSession);
			Account account = new Account();
			account.setId(accountSession.getId());
			account.setField_id(proposition.getField_id());
			Map<String, Object> callNum = teacherAccountService.callNum(account);
			proposition.setQuestion_type("1");
			proposition.setAuditor(callNum.get("ACCOUNT").toString());
			proposition.setFile_status("Y");
			proposition.setUpload_status("Y");
			proposition.setCreate_by(accountSession.getAccount());
			proposition.setUpdate_by(accountSession.getAccount());
			int id = propositionService.add(proposition);
			
			PropositionOption propositionOption = new PropositionOption();
			
			//學科
			String[] subject = pRequest.getParameterValues("subject");
			for(int i=0; i<subject.length; i++) {
				propositionOption = new PropositionOption();
				propositionOption.setProposition_id(String.valueOf(id));
				propositionOption.setType("2");
				propositionOption.setCode(subject[i]);
				propositionOption.setCreate_by(accountSession.getAccount());
				propositionOptionService.add(propositionOption);
			}
			
			//年級
			String[] grade = pRequest.getParameterValues("grade");
			for(int i=0; i<grade.length; i++) {
				propositionOption = new PropositionOption();
				propositionOption.setProposition_id(String.valueOf(id));
				propositionOption.setType("4");
				propositionOption.setCode(grade[i]);
				propositionOption.setCreate_by(accountSession.getAccount());
				propositionOptionService.add(propositionOption);
			}
			
			//教案檔案
			PropositionFile propositionFile = new PropositionFile();
			propositionFile.setProposition_id(String.valueOf(id));
			
			String uploadName = commonService.uploadFileSaveDateName(fileName, uploadedFolder+"file/proposition/");
			propositionFile.setType("1");
			propositionFile.setUpload_name(uploadName);
			propositionFile.setFile_name(fileName.getOriginalFilename());
			propositionFile.setCreate_by(accountSession.getAccount());
			propositionFile.setUpdate_by(accountSession.getAccount());
			propositionFileService.add(propositionFile);
			
			//新增審核人
			PropositionAudit propositionAudit = new PropositionAudit();
			propositionAudit.setProposition_id(String.valueOf(id));
			propositionAudit.setAuditor(callNum.get("ACCOUNT").toString());
			propositionAudit.setFile_status("Y");
			propositionAudit.setUpload_status("Y");
			propositionAudit.setCreate_by(accountSession.getAccount());
			propositionAudit.setUpdate_by(accountSession.getAccount());
			propositionAuditService.add(propositionAudit);
			
		} catch(Exception e) {
//			System.out.println("error:"+e.getMessage());
			logger.error("error:"+e.getMessage(), dateFormat.format(new Date()));
		}
		//選單
		List<Map<String, Object>> menu = functionController.menu(accountSession, menuName);
		model.addAttribute("menu", menu);
		
		model.addAttribute("PATH", "/proposition/basic");
		return "front/path";
	}
	
	@RequestMapping(value = "/proposition/basic/content" , method = {RequestMethod.POST, RequestMethod.GET})
    public String propositionBasicContent(@SessionAttribute("accountSession") Account accountSession, @ModelAttribute Proposition proposition, Model model){
		
		//取得命題資料
		Proposition data = propositionService.data(proposition);
		model.addAttribute("data", data);
		
		//取得學習領域資料
		Field field = new Field();
		field.setId(data.getField_id());
		field = fieldService.data(field);
		model.addAttribute("field_name", field.getName());
		
		//取得學科清單
		PropositionOption propositionOption = new PropositionOption();
		propositionOption.setProposition_id(proposition.getId());
		propositionOption.setType("2");
		Map<String, Object> subjectOption = propositionOptionService.subjectOption(propositionOption);
		String subject = subjectOption.get("OPTION").toString();
		model.addAttribute("subject", subject);
		
		//取得學制資料
		Education education = new Education();
		education.setId(data.getEducation_id());
		education = educationService.data(education);
		model.addAttribute("education_name", education.getName());
		
		//取得年級清單
		propositionOption = new PropositionOption();
		propositionOption.setProposition_id(proposition.getId());
		propositionOption.setType("4");
		Map<String, Object> gradeOption = propositionOptionService.gradeOption(propositionOption);
		String grade = gradeOption.get("OPTION").toString();
		model.addAttribute("grade", grade);
		
		//取得內容上傳歷程
		PropositionFile propositionFile = new PropositionFile();
		propositionFile.setProposition_id(proposition.getId());
//		propositionFile.setType("1");
		List<Map<String, Object>> contentList = propositionFileService.historyList(propositionFile);
		model.addAttribute("contentList", contentList);
		
		//取得審核回饋歷程
//		propositionFile = new PropositionFile();
//		propositionFile.setProposition_id(proposition.getId());
//		propositionFile.setType("2");
//		List<Map<String, Object>> auditList = propositionFileService.historyList(propositionFile);
//		model.addAttribute("auditList", auditList);
		
		//取得最新檔案
		model.addAttribute("nowFile", contentList.get(0));
		
		//選單
		List<Map<String, Object>> menu = functionController.menu(accountSession, menuName);
		model.addAttribute("menu", menu);
		
		return "front/proposition/basic/content";
    }
	
	@RequestMapping(value = "/proposition/basic/update" , method = {RequestMethod.POST, RequestMethod.GET})
    public String propositionBasicUpdate(@SessionAttribute("accountSession") Account accountSession, @ModelAttribute Proposition proposition, Model model){
		
		//取得教案資料
		Proposition data = propositionService.data(proposition);
		model.addAttribute("data", data);
		
		//取得領域清單
		Field field = new Field();
		List<Map<String, Object>> fieldList = fieldService.getList(field);
		model.addAttribute("fieldList", fieldList);
		
		//取得學制清單
		Education education = new Education();
		education.setLayer("1");
		List<Map<String, Object>> educationList = educationService.getList(education);
		model.addAttribute("educationList", educationList);
		
		PropositionOption propositionOption = new PropositionOption();
		propositionOption.setProposition_id(proposition.getId());
		propositionOption.setType("2");
		Map<String, Object> subjectOption = propositionOptionService.option(propositionOption);
		String so = subjectOption.get("OPTION").toString();
		model.addAttribute("subjectOption", so);
		
		//取得學科清單
		Subject subject = new Subject();
		subject.setLayer("1");
		List<Map<String, Object>> subjectList = subjectService.getList(subject);
		model.addAttribute("subjectList", subjectList);
		
		//取得年級清單
		education = new Education();
		education.setParent_id(data.getEducation_id());
		List<Map<String, Object>> gradeList = educationService.getChild(education);
		model.addAttribute("gradeList", gradeList);
		
		propositionOption = new PropositionOption();
		propositionOption.setProposition_id(proposition.getId());
		propositionOption.setType("4");
		Map<String, Object> gradeOption = propositionOptionService.option(propositionOption);
		String go = gradeOption.get("OPTION").toString();
		model.addAttribute("gradeOption", go);
		
		//取得內容上傳歷程
		PropositionFile propositionFile = new PropositionFile();
		propositionFile.setProposition_id(proposition.getId());
		propositionFile.setType("1");
		List<Map<String, Object>> contentList = propositionFileService.historyList(propositionFile);
		model.addAttribute("nowFile", contentList.get(0));
		
		//選單
		List<Map<String, Object>> menu = functionController.menu(accountSession, menuName);
		model.addAttribute("menu", menu);
		
		return "front/proposition/basic/update";
    }
	
	@RequestMapping(value = "/proposition/basic/updateSubmit" , method = {RequestMethod.GET, RequestMethod.POST})
	public String propositionBasicUpdateSubmit(Model model,
			@SessionAttribute("accountSession") Account accountSession,
			@ModelAttribute Proposition proposition,
			HttpServletRequest pRequest
			) {
		
		try {
			
			proposition.setUpdate_by(accountSession.getAccount());
			propositionService.update(proposition);
			
			PropositionOption propositionOption = new PropositionOption();
			propositionOption.setProposition_id(proposition.getId());
			propositionOptionService.delete(propositionOption);
			
			//學科
			String[] subject = pRequest.getParameterValues("subject");
			for(int i=0; i<subject.length; i++) {
				propositionOption = new PropositionOption();
				propositionOption.setProposition_id(proposition.getId());
				propositionOption.setType("2");
				propositionOption.setCode(subject[i]);
				propositionOption.setCreate_by(accountSession.getAccount());
				propositionOptionService.add(propositionOption);
			}
			
			//年級
			String[] grade = pRequest.getParameterValues("grade");
			for(int i=0; i<grade.length; i++) {
				propositionOption = new PropositionOption();
				propositionOption.setProposition_id(proposition.getId());
				propositionOption.setType("4");
				propositionOption.setCode(grade[i]);
				propositionOption.setCreate_by(accountSession.getAccount());
				propositionOptionService.add(propositionOption);
			}
			
			
		} catch(Exception e) {
//			System.out.println("error:"+e.getMessage());
			logger.error("error:"+e.getMessage(), dateFormat.format(new Date()));
		}
		//選單
		List<Map<String, Object>> menu = functionController.menu(accountSession, menuName);
		model.addAttribute("menu", menu);
		
		model.addAttribute("PATH", "/proposition/basic");
		return "front/path";
	}
	
	@RequestMapping(value = "/proposition/basic/audit" , method = {RequestMethod.GET, RequestMethod.POST})
	public String propositionBasicAudit(Model model,
			@SessionAttribute("accountSession") Account accountSession,
			@ModelAttribute Proposition proposition,
			@RequestParam(value = "fileName", required = false) MultipartFile fileName,
//			@RequestParam("csofeContractFile") MultipartFile csofeContractFile
			HttpServletRequest pRequest
			) {
		
		try {
			
			String audit = pRequest.getParameter("verify");
			String file_status = "C", upload_status = "C";
			
			if("0".equals(audit)) {
				file_status = "N";
				upload_status = "N";
				//教案檔案
				PropositionFile propositionFile = new PropositionFile();
				propositionFile.setProposition_id(proposition.getId());
				
				String uploadName = commonService.uploadFileSaveDateName(fileName, uploadedFolder+"file/proposition/");
				propositionFile.setType("2");
				propositionFile.setUpload_name(uploadName);
				propositionFile.setFile_name(fileName.getOriginalFilename());
				propositionFile.setCreate_by(accountSession.getAccount());
				propositionFile.setUpdate_by(accountSession.getAccount());
				propositionFileService.add(propositionFile);
			} else {
				file_status = "C";
				upload_status = "C";
			}
			
			//審核
			proposition.setFile_status(file_status);
			proposition.setUpload_status(upload_status);
			propositionService.audit(proposition);
			
			//新增審核紀錄
			PropositionAudit propositionAudit = new PropositionAudit();
			propositionAudit.setProposition_id(proposition.getId());
			propositionAudit.setAuditor(accountSession.getAccount());
			propositionAudit.setFile_status(file_status);
			propositionAudit.setUpload_status(upload_status);
			propositionAudit.setCreate_by(accountSession.getAccount());
			propositionAudit.setUpdate_by(accountSession.getAccount());
			propositionAuditService.add(propositionAudit);
			
		} catch(Exception e) {
//			System.out.println("error:"+e.getMessage());
			logger.error("error:"+e.getMessage(), dateFormat.format(new Date()));
		}
		//選單
		List<Map<String, Object>> menu = functionController.menu(accountSession, menuName);
		model.addAttribute("menu", menu);
		
		model.addAttribute("PATH", "/proposition/basic");
		return "front/path";
	}
	
	@RequestMapping(value = "/proposition/basic/edit" , method = {RequestMethod.POST, RequestMethod.GET})
    public String propositionBasicEdit(@SessionAttribute("accountSession") Account accountSession, @ModelAttribute Proposition proposition, Model model){
		
		//取得命題資料
		Proposition data = propositionService.data(proposition);
		model.addAttribute("data", data);
		
		//取得學習領域資料
		Field field = new Field();
		field.setId(data.getField_id());
		field = fieldService.data(field);
		model.addAttribute("field_name", field.getName());
		
		//取得學科清單
		PropositionOption propositionOption = new PropositionOption();
		propositionOption.setProposition_id(proposition.getId());
		propositionOption.setType("2");
		Map<String, Object> subjectOption = propositionOptionService.subjectOption(propositionOption);
		String subject = subjectOption.get("OPTION").toString();
		model.addAttribute("subject", subject);
		
		//取得學制資料
		Education education = new Education();
		education.setId(data.getEducation_id());
		education = educationService.data(education);
		model.addAttribute("education_name", education.getName());
		
		//取得年級清單
		propositionOption = new PropositionOption();
		propositionOption.setProposition_id(proposition.getId());
		propositionOption.setType("4");
		Map<String, Object> gradeOption = propositionOptionService.gradeOption(propositionOption);
		String grade = gradeOption.get("OPTION").toString();
		model.addAttribute("grade", grade);
		
		//取得內容上傳歷程
		PropositionFile propositionFile = new PropositionFile();
		propositionFile.setProposition_id(proposition.getId());
		propositionFile.setType("1");
		List<Map<String, Object>> contentList = propositionFileService.historyList(propositionFile);
		model.addAttribute("contentList", contentList);
		
		//取得審核回饋歷程
		propositionFile = new PropositionFile();
		propositionFile.setProposition_id(proposition.getId());
		propositionFile.setType("2");
		List<Map<String, Object>> auditList = propositionFileService.historyList(propositionFile);
		model.addAttribute("auditList", auditList);
		
		//取得最新檔案
		model.addAttribute("nowFile", contentList.get(0));
		
		//選單
		List<Map<String, Object>> menu = functionController.menu(accountSession, menuName);
		model.addAttribute("menu", menu);
		
		return "front/proposition/basic/edit";
    }
	
	@RequestMapping(value = "/proposition/basic/editSubmit" , method = {RequestMethod.GET, RequestMethod.POST})
	public String propositionBasicEditSubmit(Model model,
			@SessionAttribute("accountSession") Account accountSession,
			@ModelAttribute Proposition proposition,
			@RequestParam("fileName") MultipartFile fileName,
//			@RequestParam("csofeContractFile") MultipartFile csofeContractFile
			HttpServletRequest pRequest
			) {
		
		try {
			
			String file_status = "Y", upload_status = "Y";
			
			//教案檔案
			PropositionFile propositionFile = new PropositionFile();
			propositionFile.setProposition_id(proposition.getId());
			
			String uploadName = commonService.uploadFileSaveDateName(fileName, uploadedFolder+"file/proposition/");
			propositionFile.setType("1");
			propositionFile.setUpload_name(uploadName);
			propositionFile.setFile_name(fileName.getOriginalFilename());
			propositionFile.setCreate_by(accountSession.getAccount());
			propositionFile.setUpdate_by(accountSession.getAccount());
			propositionFileService.add(propositionFile);
			
			//審核
			proposition.setFile_status(file_status);
			proposition.setUpload_status(upload_status);
			propositionService.audit(proposition);
			
			//新增審核紀錄
			PropositionAudit propositionAudit = new PropositionAudit();
			propositionAudit.setProposition_id(proposition.getId());
			propositionAudit.setAuditor(accountSession.getAccount());
			propositionAudit.setFile_status(file_status);
			propositionAudit.setUpload_status(upload_status);
			propositionAudit.setCreate_by(accountSession.getAccount());
			propositionAudit.setUpdate_by(accountSession.getAccount());
			propositionAuditService.add(propositionAudit);
			
		} catch(Exception e) {
//			System.out.println("error:"+e.getMessage());
			logger.error("error:"+e.getMessage(), dateFormat.format(new Date()));
		}
		//選單
		List<Map<String, Object>> menu = functionController.menu(accountSession, menuName);
		model.addAttribute("menu", menu);
		
		model.addAttribute("PATH", "/proposition/basic");
		return "front/path";
	}
	
	@RequestMapping(value = "/proposition/basic/get/grade" , method = {RequestMethod.GET, RequestMethod.POST})
	public void propositionBasicGetGrade(@SessionAttribute("accountSession") Account accountSession, HttpServletRequest pRequest, HttpServletResponse pResponse, Model model) throws Exception {
		
		Education education = new Education();
		
		String id = pRequest.getParameter("id") == null ? "" : pRequest.getParameter("id");
		education.setParent_id(id);

		List<Map<String, Object>> list = educationService.getChild(education);
		
		JSONArray tJSONArray = new JSONArray(list);
		
		pResponse.setCharacterEncoding("utf-8");
		PrintWriter out = pResponse.getWriter();
		out.write(tJSONArray.toString());
		
	}
	
	
	/**
	 * 命題-題組題
	 */
	@RequestMapping(value = "/proposition/group" , method = {RequestMethod.POST, RequestMethod.GET})
    public String propositionGroup(@SessionAttribute("accountSession") Account accountSession, @ModelAttribute Proposition proposition, @ModelAttribute ContractMaterial contractMaterial, Model model){
		
		//設定每頁筆數
		if(proposition.getPage_count() == null) {
			proposition.setPage_count(10);
		}
		model.addAttribute("page_count", proposition.getPage_count());
		
		if(proposition.getPage() == null) {
			proposition.setPage(1);
		}
		
		int level = Integer.valueOf(accountSession.getLevel());
		
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		int count = 0;
		
		proposition.setQuestion_type("2");
		
		if(level <= 2) {
			list = propositionService.manageList(proposition);
			count = propositionService.manageCount(proposition);
		} else if(level == 3) {
			proposition.setCreate_by(accountSession.getAccount());
			list = propositionService.teacherList(proposition);
			count = propositionService.teacherCount(proposition);
			Contract contract = new Contract();
			contract.setTeacher_id(accountSession.getId());
			//總需上傳數-已上傳=未上傳數
			Map<String, Object> uploadNum = contractService.uploadNum(contract);
			//命題總需上傳數
			int questionsGroupNum = Integer.valueOf(uploadNum.get("QUESTIONS_GROUP_NUM").toString());
			model.addAttribute("questionsGroupNum", questionsGroupNum);
			//合約未完成數
//			int undoneCount = contractService.undoneCount(contract);
			List<Map<String, Object>> contractList = contractService.getList(contract);
			int undoneCount = contractList != null ? contractList.size() : 0;
			model.addAttribute("undoneCount", undoneCount);
			//已上傳數
			proposition.setUpload_status("Y");
			int addCount = propositionService.uploadStatusCount(proposition);
			model.addAttribute("addCount", addCount);
			//待修訂數
			proposition.setUpload_status("N");
			int editCount = propositionService.uploadStatusCount(proposition);
			model.addAttribute("editCount", editCount);
			//已完稿數
			proposition.setUpload_status("C");
			int doneCount = propositionService.uploadStatusCount(proposition);
			model.addAttribute("doneCount", doneCount);
		} else if(level == 4) {
			proposition.setAuditor(accountSession.getAccount());
			list = propositionService.auditList(proposition);
			count = propositionService.auditCount(proposition);
		} else if(level == 5) {
			proposition.setField_id(accountSession.getField_list());
			proposition.setEducation_id(accountSession.getEducation_list());
			list = propositionService.principalList(proposition);
			count = propositionService.principalCount(proposition);
		} else if(level == 6) {
			proposition.setField_id(accountSession.getField_list());
			proposition.setEducation_id(accountSession.getEducation_list());
			list = propositionService.leaderList(proposition);
			count = propositionService.leaderCount(proposition);
		} else if(level == 7) {
			list = propositionService.secretaryGeneralList(proposition);
			count = propositionService.secretaryGeneralCount(proposition);
		} else {
			list = null;
			count = 0;
		}
		
		proposition.setCount(count);
		proposition.setTotal_page((proposition.getCount()/proposition.getPage_count())<1 ? 1 : ((proposition.getCount()/proposition.getPage_count())+((proposition.getCount()%proposition.getPage_count()) > 0 ? 1 : 0)));
		
		model.addAttribute("list", list);
		
		//選單
		List<Map<String, Object>> menu = functionController.menu(accountSession, menuName);
		model.addAttribute("menu", menu);
		
		//分頁
		model.addAttribute("page", proposition.getPage());
		model.addAttribute("total_page", proposition.getTotal_page());
		
		return "front/proposition/group/list";
    }
	
	@RequestMapping(value = "/proposition/group/add" , method = {RequestMethod.POST, RequestMethod.GET})
    public String propositionGroupAdd(@SessionAttribute("accountSession") Account accountSession, @ModelAttribute Proposition proposition, Model model){
		
		//取得合約清單
		Contract contract = new Contract();
		contract.setTeacher_id(accountSession.getId());
		List<Map<String, Object>> contractList = contractService.getList(contract);
		model.addAttribute("contractList", contractList);
		
		//取得領域清單
		Field field = new Field();
		List<Map<String, Object>> fieldList = fieldService.getList(field);
		model.addAttribute("fieldList", fieldList);
		
		//取得學制清單
		Education education = new Education();
		education.setLayer("1");
		List<Map<String, Object>> educationList = educationService.getList(education);
		model.addAttribute("educationList", educationList);
		
		//取得學科清單
		Subject subject = new Subject();
		subject.setLayer("1");
		List<Map<String, Object>> subjectList = subjectService.getList(subject);
		model.addAttribute("subjectList", subjectList);
		
		//選單
		List<Map<String, Object>> menu = functionController.menu(accountSession, menuName);
		model.addAttribute("menu", menu);
		
		return "front/proposition/group/add";
    }
	
	@RequestMapping(value = "/proposition/group/addSubmit" , method = {RequestMethod.GET, RequestMethod.POST})
	public String propositionGroupAddSubmit(Model model,
			@SessionAttribute("accountSession") Account accountSession,
			@ModelAttribute Proposition proposition,
			@RequestParam("fileName") MultipartFile fileName,
//			@RequestParam("csofeContractFile") MultipartFile csofeContractFile
			HttpServletRequest pRequest
			) {
		
		try {
			
			//取得相關領域審核人
//			Contract contract = new Contract();
//			contract.setContract_id(proposition.getContract_id());
//			Map<String, Object> callNum = contractService.callNum(contract, accountSession);
			Account account = new Account();
			account.setId(accountSession.getId());
			account.setField_id(proposition.getField_id());
			Map<String, Object> callNum = teacherAccountService.callNum(account);
			proposition.setQuestion_type("2");
			proposition.setAuditor(callNum.get("ACCOUNT").toString());
			proposition.setFile_status("Y");
			proposition.setUpload_status("Y");
			proposition.setCreate_by(accountSession.getAccount());
			proposition.setUpdate_by(accountSession.getAccount());
			int id = propositionService.add(proposition);
			
			PropositionOption propositionOption = new PropositionOption();
			
			//學科
			String[] subject = pRequest.getParameterValues("subject");
			for(int i=0; i<subject.length; i++) {
				propositionOption = new PropositionOption();
				propositionOption.setProposition_id(String.valueOf(id));
				propositionOption.setType("2");
				propositionOption.setCode(subject[i]);
				propositionOption.setCreate_by(accountSession.getAccount());
				propositionOptionService.add(propositionOption);
			}
			
			//年級
			String[] grade = pRequest.getParameterValues("grade");
			for(int i=0; i<grade.length; i++) {
				propositionOption = new PropositionOption();
				propositionOption.setProposition_id(String.valueOf(id));
				propositionOption.setType("4");
				propositionOption.setCode(grade[i]);
				propositionOption.setCreate_by(accountSession.getAccount());
				propositionOptionService.add(propositionOption);
			}
			
			//教案檔案
			PropositionFile propositionFile = new PropositionFile();
			propositionFile.setProposition_id(String.valueOf(id));
			
			String uploadName = commonService.uploadFileSaveDateName(fileName, uploadedFolder+"file/proposition/");
			propositionFile.setType("1");
			propositionFile.setUpload_name(uploadName);
			propositionFile.setFile_name(fileName.getOriginalFilename());
			propositionFile.setCreate_by(accountSession.getAccount());
			propositionFile.setUpdate_by(accountSession.getAccount());
			propositionFileService.add(propositionFile);
			
			//新增審核人
			PropositionAudit propositionAudit = new PropositionAudit();
			propositionAudit.setProposition_id(String.valueOf(id));
			propositionAudit.setAuditor(callNum.get("ACCOUNT").toString());
			propositionAudit.setFile_status("Y");
			propositionAudit.setUpload_status("Y");
			propositionAudit.setCreate_by(accountSession.getAccount());
			propositionAudit.setUpdate_by(accountSession.getAccount());
			propositionAuditService.add(propositionAudit);
			
		} catch(Exception e) {
//			System.out.println("error:"+e.getMessage());
			logger.error("error:"+e.getMessage(), dateFormat.format(new Date()));
		}
		//選單
		List<Map<String, Object>> menu = functionController.menu(accountSession, menuName);
		model.addAttribute("menu", menu);
		
		model.addAttribute("PATH", "/proposition/group");
		return "front/path";
	}
	
	@RequestMapping(value = "/proposition/group/content" , method = {RequestMethod.POST, RequestMethod.GET})
    public String propositionGroupContent(@SessionAttribute("accountSession") Account accountSession, @ModelAttribute Proposition proposition, Model model){
		
		//取得命題資料
		Proposition data = propositionService.data(proposition);
		model.addAttribute("data", data);
		
		//取得學習領域資料
		Field field = new Field();
		field.setId(data.getField_id());
		field = fieldService.data(field);
		model.addAttribute("field_name", field.getName());
		
		//取得學科清單
		PropositionOption propositionOption = new PropositionOption();
		propositionOption.setProposition_id(proposition.getId());
		propositionOption.setType("2");
		Map<String, Object> subjectOption = propositionOptionService.subjectOption(propositionOption);
		String subject = subjectOption.get("OPTION").toString();
		model.addAttribute("subject", subject);
		
		//取得學制資料
		Education education = new Education();
		education.setId(data.getEducation_id());
		education = educationService.data(education);
		model.addAttribute("education_name", education.getName());
		
		//取得年級清單
		propositionOption = new PropositionOption();
		propositionOption.setProposition_id(proposition.getId());
		propositionOption.setType("4");
		Map<String, Object> gradeOption = propositionOptionService.gradeOption(propositionOption);
		String grade = gradeOption.get("OPTION").toString();
		model.addAttribute("grade", grade);
		
		//取得內容上傳歷程
		PropositionFile propositionFile = new PropositionFile();
		propositionFile.setProposition_id(proposition.getId());
//		propositionFile.setType("1");
		List<Map<String, Object>> contentList = propositionFileService.historyList(propositionFile);
		model.addAttribute("contentList", contentList);
		
		//取得審核回饋歷程
//		propositionFile = new PropositionFile();
//		propositionFile.setProposition_id(proposition.getId());
//		propositionFile.setType("2");
//		List<Map<String, Object>> auditList = propositionFileService.historyList(propositionFile);
//		model.addAttribute("auditList", auditList);
		
		//取得最新檔案
		model.addAttribute("nowFile", contentList.get(0));
		
		//選單
		List<Map<String, Object>> menu = functionController.menu(accountSession, menuName);
		model.addAttribute("menu", menu);
		
		return "front/proposition/group/content";
    }
	
	@RequestMapping(value = "/proposition/group/update" , method = {RequestMethod.POST, RequestMethod.GET})
    public String propositionGroupUpdate(@SessionAttribute("accountSession") Account accountSession, @ModelAttribute Proposition proposition, Model model){
		
		//取得教案資料
		Proposition data = propositionService.data(proposition);
		model.addAttribute("data", data);
		
		//取得領域清單
		Field field = new Field();
		List<Map<String, Object>> fieldList = fieldService.getList(field);
		model.addAttribute("fieldList", fieldList);
		
		//取得學制清單
		Education education = new Education();
		education.setLayer("1");
		List<Map<String, Object>> educationList = educationService.getList(education);
		model.addAttribute("educationList", educationList);
		
		PropositionOption propositionOption = new PropositionOption();
		propositionOption.setProposition_id(proposition.getId());
		propositionOption.setType("2");
		Map<String, Object> subjectOption = propositionOptionService.option(propositionOption);
		String so = subjectOption.get("OPTION").toString();
		model.addAttribute("subjectOption", so);
		
		//取得學科清單
		Subject subject = new Subject();
		subject.setLayer("1");
		List<Map<String, Object>> subjectList = subjectService.getList(subject);
		model.addAttribute("subjectList", subjectList);
		
		//取得年級清單
		education = new Education();
		education.setParent_id(data.getEducation_id());
		List<Map<String, Object>> gradeList = educationService.getChild(education);
		model.addAttribute("gradeList", gradeList);
		
		propositionOption = new PropositionOption();
		propositionOption.setProposition_id(proposition.getId());
		propositionOption.setType("4");
		Map<String, Object> gradeOption = propositionOptionService.option(propositionOption);
		String go = gradeOption.get("OPTION").toString();
		model.addAttribute("gradeOption", go);
		
		//取得內容上傳歷程
		PropositionFile propositionFile = new PropositionFile();
		propositionFile.setProposition_id(proposition.getId());
		propositionFile.setType("1");
		List<Map<String, Object>> contentList = propositionFileService.historyList(propositionFile);
		model.addAttribute("nowFile", contentList.get(0));
		
		//選單
		List<Map<String, Object>> menu = functionController.menu(accountSession, menuName);
		model.addAttribute("menu", menu);
		
		return "front/proposition/group/update";
    }
	
	@RequestMapping(value = "/proposition/group/updateSubmit" , method = {RequestMethod.GET, RequestMethod.POST})
	public String propositionGroupUpdateSubmit(Model model,
			@SessionAttribute("accountSession") Account accountSession,
			@ModelAttribute Proposition proposition,
			HttpServletRequest pRequest
			) {
		
		try {
			
			proposition.setUpdate_by(accountSession.getAccount());
			propositionService.update(proposition);
			
			PropositionOption propositionOption = new PropositionOption();
			propositionOption.setProposition_id(proposition.getId());
			propositionOptionService.delete(propositionOption);
			
			//學科
			String[] subject = pRequest.getParameterValues("subject");
			for(int i=0; i<subject.length; i++) {
				propositionOption = new PropositionOption();
				propositionOption.setProposition_id(proposition.getId());
				propositionOption.setType("2");
				propositionOption.setCode(subject[i]);
				propositionOption.setCreate_by(accountSession.getAccount());
				propositionOptionService.add(propositionOption);
			}
			
			//年級
			String[] grade = pRequest.getParameterValues("grade");
			for(int i=0; i<grade.length; i++) {
				propositionOption = new PropositionOption();
				propositionOption.setProposition_id(proposition.getId());
				propositionOption.setType("4");
				propositionOption.setCode(grade[i]);
				propositionOption.setCreate_by(accountSession.getAccount());
				propositionOptionService.add(propositionOption);
			}
			
			
		} catch(Exception e) {
//			System.out.println("error:"+e.getMessage());
			logger.error("error:"+e.getMessage(), dateFormat.format(new Date()));
		}
		//選單
		List<Map<String, Object>> menu = functionController.menu(accountSession, menuName);
		model.addAttribute("menu", menu);
		
		model.addAttribute("PATH", "/proposition/group");
		return "front/path";
	}
	
	@RequestMapping(value = "/proposition/group/audit" , method = {RequestMethod.GET, RequestMethod.POST})
	public String propositionGroupAudit(Model model,
			@SessionAttribute("accountSession") Account accountSession,
			@ModelAttribute Proposition proposition,
			@RequestParam(value = "fileName", required = false) MultipartFile fileName,
//			@RequestParam("csofeContractFile") MultipartFile csofeContractFile
			HttpServletRequest pRequest
			) {
		
		try {
			
			String audit = pRequest.getParameter("verify");
			String file_status = "C", upload_status = "C";
			
			if("0".equals(audit)) {
				file_status = "N";
				upload_status = "N";
				//教案檔案
				PropositionFile propositionFile = new PropositionFile();
				propositionFile.setProposition_id(proposition.getId());
				
				String uploadName = commonService.uploadFileSaveDateName(fileName, uploadedFolder+"file/proposition/");
				propositionFile.setType("2");
				propositionFile.setUpload_name(uploadName);
				propositionFile.setFile_name(fileName.getOriginalFilename());
				propositionFile.setCreate_by(accountSession.getAccount());
				propositionFile.setUpdate_by(accountSession.getAccount());
				propositionFileService.add(propositionFile);
			} else {
				file_status = "C";
				upload_status = "C";
			}
			
			//審核
			proposition.setFile_status(file_status);
			proposition.setUpload_status(upload_status);
			propositionService.audit(proposition);
			
			//新增審核紀錄
			PropositionAudit propositionAudit = new PropositionAudit();
			propositionAudit.setProposition_id(proposition.getId());
			propositionAudit.setAuditor(accountSession.getAccount());
			propositionAudit.setFile_status(file_status);
			propositionAudit.setUpload_status(upload_status);
			propositionAudit.setCreate_by(accountSession.getAccount());
			propositionAudit.setUpdate_by(accountSession.getAccount());
			propositionAuditService.add(propositionAudit);
			
		} catch(Exception e) {
//			System.out.println("error:"+e.getMessage());
			logger.error("error:"+e.getMessage(), dateFormat.format(new Date()));
		}
		//選單
		List<Map<String, Object>> menu = functionController.menu(accountSession, menuName);
		model.addAttribute("menu", menu);
		
		model.addAttribute("PATH", "/proposition/group");
		return "front/path";
	}
	
	@RequestMapping(value = "/proposition/group/edit" , method = {RequestMethod.POST, RequestMethod.GET})
    public String propositionGroupEdit(@SessionAttribute("accountSession") Account accountSession, @ModelAttribute Proposition proposition, Model model){
		
		//取得命題資料
		Proposition data = propositionService.data(proposition);
		model.addAttribute("data", data);
		
		//取得學習領域資料
		Field field = new Field();
		field.setId(data.getField_id());
		field = fieldService.data(field);
		model.addAttribute("field_name", field.getName());
		
		//取得學科清單
		PropositionOption propositionOption = new PropositionOption();
		propositionOption.setProposition_id(proposition.getId());
		propositionOption.setType("2");
		Map<String, Object> subjectOption = propositionOptionService.subjectOption(propositionOption);
		String subject = subjectOption.get("OPTION").toString();
		model.addAttribute("subject", subject);
		
		//取得學制資料
		Education education = new Education();
		education.setId(data.getEducation_id());
		education = educationService.data(education);
		model.addAttribute("education_name", education.getName());
		
		//取得年級清單
		propositionOption = new PropositionOption();
		propositionOption.setProposition_id(proposition.getId());
		propositionOption.setType("4");
		Map<String, Object> gradeOption = propositionOptionService.gradeOption(propositionOption);
		String grade = gradeOption.get("OPTION").toString();
		model.addAttribute("grade", grade);
		
		//取得內容上傳歷程
		PropositionFile propositionFile = new PropositionFile();
		propositionFile.setProposition_id(proposition.getId());
		propositionFile.setType("1");
		List<Map<String, Object>> contentList = propositionFileService.historyList(propositionFile);
		model.addAttribute("contentList", contentList);
		
		//取得審核回饋歷程
		propositionFile = new PropositionFile();
		propositionFile.setProposition_id(proposition.getId());
		propositionFile.setType("2");
		List<Map<String, Object>> auditList = propositionFileService.historyList(propositionFile);
		model.addAttribute("auditList", auditList);
		
		//取得最新檔案
		model.addAttribute("nowFile", contentList.get(0));
		
		//選單
		List<Map<String, Object>> menu = functionController.menu(accountSession, menuName);
		model.addAttribute("menu", menu);
		
		return "front/proposition/group/edit";
    }
	
	@RequestMapping(value = "/proposition/group/editSubmit" , method = {RequestMethod.GET, RequestMethod.POST})
	public String propositionGroupEditSubmit(Model model,
			@SessionAttribute("accountSession") Account accountSession,
			@ModelAttribute Proposition proposition,
			@RequestParam("fileName") MultipartFile fileName,
//			@RequestParam("csofeContractFile") MultipartFile csofeContractFile
			HttpServletRequest pRequest
			) {
		
		try {
			
			String file_status = "Y", upload_status = "Y";
			
			//教案檔案
			PropositionFile propositionFile = new PropositionFile();
			propositionFile.setProposition_id(proposition.getId());
			
			String uploadName = commonService.uploadFileSaveDateName(fileName, uploadedFolder+"file/proposition/");
			propositionFile.setType("1");
			propositionFile.setUpload_name(uploadName);
			propositionFile.setFile_name(fileName.getOriginalFilename());
			propositionFile.setCreate_by(accountSession.getAccount());
			propositionFile.setUpdate_by(accountSession.getAccount());
			propositionFileService.add(propositionFile);
			
			//審核
			proposition.setFile_status(file_status);
			proposition.setUpload_status(upload_status);
			propositionService.audit(proposition);
			
			//新增審核紀錄
			PropositionAudit propositionAudit = new PropositionAudit();
			propositionAudit.setProposition_id(proposition.getId());
			propositionAudit.setAuditor(accountSession.getAccount());
			propositionAudit.setFile_status(file_status);
			propositionAudit.setUpload_status(upload_status);
			propositionAudit.setCreate_by(accountSession.getAccount());
			propositionAudit.setUpdate_by(accountSession.getAccount());
			propositionAuditService.add(propositionAudit);
			
		} catch(Exception e) {
//			System.out.println("error:"+e.getMessage());
			logger.error("error:"+e.getMessage(), dateFormat.format(new Date()));
		}
		//選單
		List<Map<String, Object>> menu = functionController.menu(accountSession, menuName);
		model.addAttribute("menu", menu);
		
		model.addAttribute("PATH", "/proposition/group");
		return "front/path";
	}
	
	@RequestMapping(value = "/proposition/group/get/grade" , method = {RequestMethod.GET, RequestMethod.POST})
	public void propositionGroupGetGrade(@SessionAttribute("accountSession") Account accountSession, HttpServletRequest pRequest, HttpServletResponse pResponse, Model model) throws Exception {
		
		Education education = new Education();
		
		String id = pRequest.getParameter("id") == null ? "" : pRequest.getParameter("id");
		education.setParent_id(id);

		List<Map<String, Object>> list = educationService.getChild(education);
		
		JSONArray tJSONArray = new JSONArray(list);
		
		pResponse.setCharacterEncoding("utf-8");
		PrintWriter out = pResponse.getWriter();
		out.write(tJSONArray.toString());
		
	}
	
	@GetMapping("/switch/identity")
	public String switchIdentity(Model model,
			@SessionAttribute("accountSession") Account accountSession,
			HttpServletRequest pRequest
			) {
		
		try {
			
			if("1".equals(accountSession.getContent_provision()) && "1".equals(accountSession.getContent_audit())) {
				Identity identity = new Identity();
				if("4".equals(accountSession.getLevel())) {
					identity.setLevel("3");
				} else {
					identity.setLevel("4");
				}
				Account account = new Account();
				account.setId(accountSession.getId());
				
				Map<String, Object> getDataByLevel = identityService.getDataByLevel(identity);
				if(getDataByLevel != null) {
					String identity_id = getDataByLevel.get("ID").toString();
					account.setIdentity_id(identity_id);
					teacherAccountService.updateIdentity(account);
					
					accountSession.setLevel(identity.getLevel());
					accountSession.setIdentity_id(identity_id);
				}
				
			}
			
		} catch(Exception e) {
//			System.out.println("error:"+e.getMessage());
			logger.error("error:"+e.getMessage(), dateFormat.format(new Date()));
		}
		
		model.addAttribute("PATH", "/index");
		return "front/path";
	}
	
	@RequestMapping(value = "/get/education" , method = {RequestMethod.GET, RequestMethod.POST})
	public void getEducation(@SessionAttribute("accountSession") Account accountSession, HttpServletRequest pRequest, HttpServletResponse pResponse, Model model) throws Exception {
		
		Education education = new Education();
		
		String id = pRequest.getParameter("id") == null ? "" : pRequest.getParameter("id");
		education.setParent_id(id);

		List<Map<String, Object>> list = educationService.getChild(education);
		
		JSONArray tJSONArray = new JSONArray(list);
		
		pResponse.setCharacterEncoding("utf-8");
		PrintWriter out = pResponse.getWriter();
		out.write(tJSONArray.toString());
		
	}
	
	@RequestMapping(value = "/get/grade" , method = {RequestMethod.GET, RequestMethod.POST})
	public void getGrade(@SessionAttribute("accountSession") Account accountSession, HttpServletRequest pRequest, HttpServletResponse pResponse, Model model) throws Exception {
		getEducation(accountSession, pRequest, pResponse, model);
	}
	
	@RequestMapping(value = "/get/subject" , method = {RequestMethod.GET, RequestMethod.POST})
	public void getSubject(@SessionAttribute("accountSession") Account accountSession, HttpServletRequest pRequest, HttpServletResponse pResponse, Model model) throws Exception {
		
		Subject subject = new Subject();
		
		String id = pRequest.getParameter("id") == null ? "" : pRequest.getParameter("id");
		subject.setParent_id(id);

		List<Map<String, Object>> list = subjectService.getChild(subject);
		
		JSONArray tJSONArray = new JSONArray(list);
		
		pResponse.setCharacterEncoding("utf-8");
		PrintWriter out = pResponse.getWriter();
		out.write(tJSONArray.toString());
		
	}
	
	@RequestMapping(value = "/get/cross/subject" , method = {RequestMethod.GET, RequestMethod.POST})
	public void getCrossSubject(@SessionAttribute("accountSession") Account accountSession, HttpServletRequest pRequest, HttpServletResponse pResponse, Model model) throws Exception {
		getSubject(accountSession, pRequest, pResponse, model);
	}
	
	@RequestMapping(value = "/set/contract" , method = {RequestMethod.GET, RequestMethod.POST})
	public void setContract(@SessionAttribute("accountSession") Account accountSession, HttpServletRequest pRequest, HttpServletResponse pResponse, Model model) throws Exception {
		
		Contract contract = new Contract();
		
		String contract_id = pRequest.getParameter("contract_id") == null ? "" : pRequest.getParameter("contract_id");
		contract.setContract_id(contract_id);

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = contractService.getDataByContractId(contract);
		list.add(map);
		
		JSONArray tJSONArray = new JSONArray(list);
		
		pResponse.setCharacterEncoding("utf-8");
		PrintWriter out = pResponse.getWriter();
		out.write(tJSONArray.toString());
		
	}
}
