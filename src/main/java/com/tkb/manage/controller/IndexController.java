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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

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
import com.tkb.manage.model.Function;
import com.tkb.manage.model.Identity;
import com.tkb.manage.model.LessonPlan;
import com.tkb.manage.model.LessonPlanAudit;
import com.tkb.manage.model.LessonPlanFile;
import com.tkb.manage.model.LessonPlanOption;
import com.tkb.manage.model.LessonPlanTag;
import com.tkb.manage.model.MaterialType;
import com.tkb.manage.model.Proposition;
import com.tkb.manage.model.PropositionAudit;
import com.tkb.manage.model.PropositionFile;
import com.tkb.manage.model.PropositionOption;
import com.tkb.manage.model.PropositionTag;
import com.tkb.manage.model.SchoolMaster;
import com.tkb.manage.model.Subject;
import com.tkb.manage.model.TeacherAccountOption;
import com.tkb.manage.model.TeacherPasswordLog;
import com.tkb.manage.service.CommonService;
import com.tkb.manage.service.ContractMaterialOptionService;
import com.tkb.manage.service.ContractMaterialService;
import com.tkb.manage.service.ContractService;
import com.tkb.manage.service.EducationService;
import com.tkb.manage.service.IdentityService;
import com.tkb.manage.service.LessonPlanAuditService;
import com.tkb.manage.service.LessonPlanFileService;
import com.tkb.manage.service.LessonPlanOptionService;
import com.tkb.manage.service.LessonPlanService;
import com.tkb.manage.service.LessonPlanTagService;
import com.tkb.manage.service.MaterialTypeService;
import com.tkb.manage.service.PropositionAuditService;
import com.tkb.manage.service.PropositionFileService;
import com.tkb.manage.service.PropositionOptionService;
import com.tkb.manage.service.PropositionService;
import com.tkb.manage.service.PropositionTagService;
import com.tkb.manage.service.SchoolMasterService;
import com.tkb.manage.service.SubjectService;
import com.tkb.manage.service.TeacherAccountOptionService;
import com.tkb.manage.service.TeacherAccountService;
import com.tkb.manage.service.TeacherPasswordLogService;
import com.tkb.manage.util.aop.annotation.AuditAction;
import com.tkb.manage.util.aop.common.enums.Action;

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
	
//	@Autowired
//	private FieldService fieldService;
	
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
	private LessonPlanTagService lessonPlanTagService;
	
	@Autowired
	private PropositionService propositionService;
	
	@Autowired
	private PropositionOptionService propositionOptionService;
	
	@Autowired
	private PropositionFileService propositionFileService;
	
	@Autowired
	private PropositionAuditService propositionAuditService;
	
	@Autowired
	private PropositionTagService propositionTagService;
	
	@Autowired
	private SubjectService subjectService;
	
	@Autowired
	private MaterialTypeService materialTypeService;
	
	@Autowired
	private TeacherPasswordLogService teacherPasswordLogService;
	
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
	
	@Value("${forget.password.key}") 
	private String pwKey;
	
	@Value("${web.path}") 
	private String webPath;
	
	private String[] edu = {"E", "J", "S"};
	private String[] sub = {"C", "E", "M"};
	
	private static final Logger logger = LoggerFactory.getLogger(IndexController.class);
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	
//	private Integer page_count = 10;
	
	@RequestMapping(value = "/index" , method = {RequestMethod.GET, RequestMethod.POST})
	public String index(Model model, @SessionAttribute("accountSession") Account accountSession) {
		
		int level = Integer.valueOf(accountSession.getLevel());
		
		Contract contract = new Contract();
		
		//管理者(幾份合約未履行、幾份合約到期)
		if(level <= 2) {
			//老師授權合約
			contract = new Contract();	
			Map<String, Object> contractNum = contractService.contractNum(contract);
			contractNum.put("NO_COMPLETE", Integer.valueOf(contractNum.get("TOTAL").toString())-Integer.valueOf(contractNum.get("COMPLETE").toString()));
			model.addAttribute("contractNum", contractNum);
			//素材授權合約
			Map<String, Object> contractMaterialNum = contractMaterialService.contractNum();
			model.addAttribute("contractMaterialNum", contractMaterialNum);
		//創作教師(幾份教案、命題基本題、命題題組題未審核及未修訂)
		} else if(level == 3) {
			contract = new Contract();
			contract.setTeacher_id(accountSession.getId());
			
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> educationSubjectList = new ArrayList<Map<String, Object>>();
			educationSubjectList = contractService.getSubjectEducationByTeacher(contract);
			if(educationSubjectList != null) {
				for(int i = 0; i < educationSubjectList.size();i++) {
					Map<String, Object> lessonPlan = contractService.getLessonPlanNum(educationSubjectList.get(i).get("TEACHER_ID").toString(), educationSubjectList.get(i).get("EDUCATION_ID").toString(), educationSubjectList.get(i).get("SUBJECT_ID").toString());
					Map<String, Object> basic = contractService.getPropositionNum(educationSubjectList.get(i).get("TEACHER_ID").toString(), educationSubjectList.get(i).get("EDUCATION_ID").toString(), educationSubjectList.get(i).get("SUBJECT_ID").toString(), "1");
					Map<String, Object> group = contractService.getPropositionNum(educationSubjectList.get(i).get("TEACHER_ID").toString(), educationSubjectList.get(i).get("EDUCATION_ID").toString(), educationSubjectList.get(i).get("SUBJECT_ID").toString(), "2");
					
					if(lessonPlan == null) {
						lessonPlan = new HashMap<String, Object>();
						lessonPlan.put("LP1_COUNT", "0");
						lessonPlan.put("A_SUM", "0");
						lessonPlan.put("B_SUM", "0");
						lessonPlan.put("C_SUM", "0");
						lessonPlan.put("D_SUM", "0");
						lessonPlan.put("E_SUM", "0");
						lessonPlan.put("F_SUM", "0");
					}
					if(basic == null) {
						basic = new HashMap<String, Object>();
						basic.put("LP1_COUNT", "0");
						basic.put("A_SUM", "0");
						basic.put("B_SUM", "0");
						basic.put("C_SUM", "0");
						basic.put("D_SUM", "0");
						basic.put("E_SUM", "0");
						basic.put("F_SUM", "0");
					}
					if(group == null) {
						group = new HashMap<String, Object>();
						group.put("LP1_COUNT", "0");
						group.put("A_SUM", "0");
						group.put("B_SUM", "0");
						group.put("C_SUM", "0");
						group.put("D_SUM", "0");
						group.put("E_SUM", "0");
						group.put("F_SUM", "0");
					}
					lessonPlan.put("EDUCATION_ID", educationSubjectList.get(i).get("EDUCATION_ID").toString());
					lessonPlan.put("SUBJECT_ID", educationSubjectList.get(i).get("SUBJECT_ID").toString());
					lessonPlan.put("CONTRACT_SUM", educationSubjectList.get(i).get("LESSON_NUM").toString());
					lessonPlan.put("QUESTION_TYPE", "0");
					basic.put("EDUCATION_ID", educationSubjectList.get(i).get("EDUCATION_ID").toString());
					basic.put("SUBJECT_ID", educationSubjectList.get(i).get("SUBJECT_ID").toString());
					basic.put("CONTRACT_SUM", educationSubjectList.get(i).get("BASIC_NUM").toString());
					basic.put("QUESTION_TYPE", "1");
					group.put("EDUCATION_ID", educationSubjectList.get(i).get("EDUCATION_ID").toString());
					group.put("SUBJECT_ID", educationSubjectList.get(i).get("SUBJECT_ID").toString());
					group.put("CONTRACT_SUM", educationSubjectList.get(i).get("QUESTIONS_GROUP_NUM").toString());
					group.put("QUESTION_TYPE", "2");
					list.add(lessonPlan);
					list.add(basic);
					list.add(group);			
				}
			} else {
				Map<String, Object> lessonPlan = null;
				Map<String, Object> basic = null;
				Map<String, Object> group = null;
				if(lessonPlan == null) {
					lessonPlan = new HashMap<String, Object>();
					lessonPlan.put("LP1_COUNT", "0");
					lessonPlan.put("A_SUM", "0");
					lessonPlan.put("B_SUM", "0");
					lessonPlan.put("C_SUM", "0");
					lessonPlan.put("D_SUM", "0");
					lessonPlan.put("E_SUM", "0");
					lessonPlan.put("F_SUM", "0");
				}
				if(basic == null) {
					basic = new HashMap<String, Object>();
					basic.put("LP1_COUNT", "0");
					basic.put("A_SUM", "0");
					basic.put("B_SUM", "0");
					basic.put("C_SUM", "0");
					basic.put("D_SUM", "0");
					basic.put("E_SUM", "0");
					basic.put("F_SUM", "0");
				}
				if(group == null) {
					group = new HashMap<String, Object>();
					group.put("LP1_COUNT", "0");
					group.put("A_SUM", "0");
					group.put("B_SUM", "0");
					group.put("C_SUM", "0");
					group.put("D_SUM", "0");
					group.put("E_SUM", "0");
					group.put("F_SUM", "0");
				}
				list.add(lessonPlan);
				list.add(basic);
				list.add(group);
			}

			model.addAttribute("list", list);
			model.addAttribute("educationSubjectList", educationSubjectList);
		
		//審議委員(幾份教案、命題基本題、命題題組題未審核)
		} else if(level == 4) {
			Account teacher = new Account();
			teacher.setId(accountSession.getId());
			teacher = teacherAccountService.data(teacher);
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

			LessonPlan lessonPlanData = new LessonPlan();
			lessonPlanData.setAuditor2(teacher.getAccount());
			Proposition propositionData = new Proposition();
			propositionData.setAuditor2(teacher.getAccount());
			Map<String, Object> lessonPlan = lessonPlanService.auditNum(lessonPlanData);
			propositionData.setQuestion_type("1");
			Map<String, Object> basic = propositionService.auditNum(propositionData);
			propositionData.setQuestion_type("2");
			Map<String, Object> group = propositionService.auditNum(propositionData);

			if(lessonPlan.get("A_SUM") == null) {
				lessonPlan = new HashMap<String, Object>();
				lessonPlan.put("C_SUM", "0");
				lessonPlan.put("D_SUM", "0");
				lessonPlan.put("E_SUM", "0");
				lessonPlan.put("AUDIT", "0");
			}
			if(basic.get("A_SUM") == null) {
				basic = new HashMap<String, Object>();
				basic.put("C_SUM", "0");
				basic.put("D_SUM", "0");
				basic.put("E_SUM", "0");
				basic.put("AUDIT", "0");
			}
			if(group.get("A_SUM") == null) {
				group = new HashMap<String, Object>();
				group.put("C_SUM", "0");
				group.put("D_SUM", "0");
				group.put("E_SUM", "0");
				group.put("AUDIT", "0");
			}

			lessonPlan.put("QUESTION_TYPE", "0");
			lessonPlan.put("AUDIT", Integer.valueOf(lessonPlan.get("C_SUM").toString())+Integer.valueOf(lessonPlan.get("D_SUM").toString())+Integer.valueOf(lessonPlan.get("E_SUM").toString()));
			basic.put("QUESTION_TYPE", "1");
			basic.put("AUDIT", Integer.valueOf(basic.get("C_SUM").toString())+Integer.valueOf(basic.get("D_SUM").toString())+Integer.valueOf(basic.get("E_SUM").toString()));
			group.put("QUESTION_TYPE", "2");
			group.put("AUDIT", Integer.valueOf(group.get("C_SUM").toString())+Integer.valueOf(group.get("D_SUM").toString())+Integer.valueOf(group.get("E_SUM").toString()));
			list.add(lessonPlan);
			list.add(basic);
			list.add(group);	
			
			model.addAttribute("list", list);
			model.addAttribute("education_name", teacher.getEducation_name());
			model.addAttribute("subject_name", teacher.getSubject_name());
			
		} else if(level == 5 || level == 6 || level == 7) {
			Map<String, Object> educationSubjectList = new HashMap<String, Object>();
			Subject subject = new Subject();
			Education education = new Education();
			subject.setId(accountSession.getSubject_list());
			List<Map<String, Object>> subjectList = subjectService.getListInId(subject);
			model.addAttribute("subjectList", subjectList);
			if(subjectList != null) {
				String[] educationArr = accountSession.getEducation_list().split(",");
				List<Map<String, Object>> educationList = new ArrayList<Map<String, Object>>();
				for(int i=0; i<subjectList.size(); i++) {
					for(int j=0; j<educationArr.length; j++) {
						contract = new Contract();
						contract.setEducation_id(educationArr[j]);
						contract.setSubject_id(subjectList.get(i).get("ID").toString());
						
						education = new Education();
						education.setId(educationArr[j]);
						education = educationService.data(education);
						educationSubjectList = contractService.getSubjectEducation(contract);

						Map<String, Object> getSubjectEducation = contractService.getLessonPlanProposition(contract, accountSession.getAccount());
						if(getSubjectEducation.get("A_SUM") != null && educationSubjectList.get("LESSON_NUM") != null) {
							getSubjectEducation.put("SUBJECT_NAME", subjectList.get(i).get("NAME").toString());
							getSubjectEducation.put("SUBJECT_ID", subjectList.get(i).get("ID").toString());
							getSubjectEducation.put("EDUCATION_NAME", education.getName());
							getSubjectEducation.put("EDUCATION_ID", education.getId());
							getSubjectEducation.put("CONTRACT_SUM", Integer.valueOf(educationSubjectList.get("LESSON_NUM").toString())+Integer.valueOf(educationSubjectList.get("BASIC_NUM").toString())+Integer.valueOf(educationSubjectList.get("QUESTIONS_GROUP_NUM").toString()));
							educationList.add(getSubjectEducation);
						} else {
							Map<String, Object> map = new HashMap<String, Object>();
							map.put("SUBJECT_NAME", subjectList.get(i).get("NAME").toString());
							map.put("SUBJECT_ID", subjectList.get(i).get("ID").toString());
							map.put("EDUCATION_NAME", education.getName());
							map.put("EDUCATION_ID", education.getId());
							map.put("P1_COUNT", "0");
							map.put("A_SUM", "0");
							map.put("B_SUM", "0");
							map.put("C_SUM", "0");
							map.put("D_SUM", "0");
							map.put("E_SUM", "0");
							map.put("F_SUM", "0");
							map.put("CONTRACT_SUM", "0");
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
    public String list(
    	@SessionAttribute("accountSession") Account accountSession,
    	@ModelAttribute Account account,
    	@ModelAttribute Contract contract,
    	HttpServletRequest pRequest,
    	Model model){
		
		int level = Integer.valueOf(accountSession.getLevel());
		
		String search_content_provision = (pRequest.getParameter("search_content_provision")==null || "0".equals(pRequest.getParameter("search_content_provision"))) ? "0" : "1";
		String search_content_audit = (pRequest.getParameter("search_content_audit")==null || "0".equals(pRequest.getParameter("search_content_audit"))) ? "0" : "1";
		account.setSearch_content_provision(search_content_provision);
		account.setSearch_content_audit(search_content_audit);
		
		//設定每頁筆數
		if(account.getPage_count() == null) {
			account.setPage_count(10);
		}
		
		model.addAttribute("page_count", account.getPage_count());
		
		if(account.getPage() == null) {
			account.setPage(1);
		}
		
		if(account.getSearch_position() == null) {
			account.setSearch_position("1");
			account.setSearch_content_provision("1");
			account.setSearch_content_audit("1");
		}
		
//		account.setPage_count(page_count);
		List<Map<String, Object>> list = teacherAccountService.list(account);
		model.addAttribute("list", list);
		
		int count = teacherAccountService.count(account);
		account.setCount(count);
		account.setTotal_page((account.getCount()/account.getPage_count())<1 ? 1 : ((account.getCount()/account.getPage_count())+((account.getCount()%account.getPage_count()) > 0 ? 1 : 0)));
		
		if(level == 6) {
			Account ac = new Account();
			ac.setStatus("2");
			List<Map<String, Object>> verifyList = teacherAccountService.verifyList(ac);
			model.addAttribute("verifyList", verifyList);
		}
		
		//取得學科清單
		Subject subject = new Subject();
		subject.setLayer("1");
		List<Map<String, Object>> subjectList = subjectService.getList(subject);
		model.addAttribute("subjectList", subjectList);
		
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
	
	@RequestMapping(value = "/teacher/register" , method = {RequestMethod.POST, RequestMethod.GET})
    public String teacherRegister(@ModelAttribute Account account, Model model){	
		
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
		
		return "front/teacherAccount/register";
    }
	
	@AuditAction(action = Action.ADD, targetTable = "teacher_account")
	@RequestMapping(value = "/teacher/registerSubmit" , method = {RequestMethod.POST, RequestMethod.GET})
    public String registerSubmit(@ModelAttribute Account account,
    		HttpServletRequest pRequest,
    		Model model){
		
		String[] educationList = pRequest.getParameterValues("education")!=null ? pRequest.getParameterValues("education") : null;
		String[] subjectList = pRequest.getParameterValues("subject")!=null ? pRequest.getParameterValues("subject") : null;
		String content_provision = pRequest.getParameter("content_provision") == null ? "0" : "1";
		String content_audit = pRequest.getParameter("content_audit") == null ? "0" : "1";
		
		String level = "4";
		account.setPosition("1");//註冊僅供一般老師使用
    	if("1".equals(content_provision)) {
    		//創作教師
    		level = "3";
    	} else {
    		//內容審核
    		level = "4";
    	}
	
    	Identity identity = new Identity();
    	identity.setLevel(level);
    	Map<String, Object> levelId = identityService.getDataByLevel(identity);
    	String identity_id = levelId.get("ID").toString();
    	
    	account.setBranch(!"".equals(account.getBranch())?account.getBranch():null);
		account.setAccount(account.getId_no());
		account.setPassword(account.getId_no().substring(account.getId_no().length()-4,account.getId_no().length()));
		account.setIdentity_id(identity_id);
		account.setContent_provision(content_provision);
		account.setContent_audit(content_audit);
		account.setStatus("2");
		account.setCreate_by(account.getId_no());
		account.setUpdate_by(account.getId_no());
		int id = teacherAccountService.add(account);
		account.setId(String.valueOf(id));
		
		TeacherAccountOption teacherAccountOption = new TeacherAccountOption();
		if(educationList != null) {
			for(int i=0; i<educationList.length; i++) {
				teacherAccountOption.setTeacher_account_id(String.valueOf(id));
				teacherAccountOption.setType("3");
				teacherAccountOption.setCode(educationList[i]);
				teacherAccountOption.setCreate_by(account.getId_no());
				teacherAccountOptionService.add(teacherAccountOption);
				
				account.getTeacherAccountOption().add(teacherAccountOption);
			}
		}
		
		if(subjectList != null) {
			teacherAccountOption = new TeacherAccountOption();
			for(int i=0; i<subjectList.length; i++) {
				teacherAccountOption.setTeacher_account_id(String.valueOf(id));
				teacherAccountOption.setType("1");
				teacherAccountOption.setCode(subjectList[i]);
				teacherAccountOption.setCreate_by(account.getId_no());
				teacherAccountOptionService.add(teacherAccountOption);
				
				account.getTeacherAccountOption().add(teacherAccountOption);
			}
		}
		
		model.addAttribute("PATH", "/");
		return "front/path";
    }
	
	@RequestMapping(value = "/teacher/audit" , method = {RequestMethod.GET, RequestMethod.POST})
	public String teacherAudit(Model model,
			@SessionAttribute("accountSession") Account accountSession,
			@ModelAttribute Account account,
			HttpServletRequest pRequest
			) {
		
		try {
			//審核
			account.setUpdate_by(accountSession.getUpdate_by());
			teacherAccountService.audit(account);
			
		} catch(Exception e) {
//			System.out.println("error:"+e.getMessage());
			logger.error("error:"+e.getMessage(), dateFormat.format(new Date()));
		}
		//選單
		List<Map<String, Object>> menu = functionController.menu(accountSession, menuName);
		model.addAttribute("menu", menu);
		
		model.addAttribute("PATH", "/teacher");
		return "front/path";
	}
	
	@RequestMapping(value = "/teacher/check" , method = {RequestMethod.GET, RequestMethod.POST})
	public void checkAccount(HttpServletRequest pRequest, HttpServletResponse pResponse, Model model) throws Exception {

		String id_no = pRequest.getParameter("id_no") == null ? "" : pRequest.getParameter("id_no");
		String id = pRequest.getParameter("id") == null ? "" : pRequest.getParameter("id");
		String status = "F";
		int check_id = teacherAccountService.checkAccount(id_no, id);

    	if(check_id == 0) {
    		status = "T";
    	}
    	
    	pResponse.setCharacterEncoding("utf-8");
		PrintWriter out = pResponse.getWriter();
		out.write(status);
		
	}
	
	@RequestMapping(value = "/teacher/add" , method = {RequestMethod.POST, RequestMethod.GET})
    public String teacherAdd(@SessionAttribute("accountSession") Account accountSession, @ModelAttribute Account account, Model model){	
		
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
	
	@AuditAction(action = Action.ADD, targetTable = "teacher_account")
	@RequestMapping(value = "/teacher/addSubmit" , method = {RequestMethod.POST, RequestMethod.GET})
    public String addSubmit(
    		@ModelAttribute Account account,
    		@SessionAttribute("accountSession") Account accountSession,
    		HttpServletRequest pRequest,
    		Model model){
		
		String[] educationList = pRequest.getParameterValues("education")!=null ? pRequest.getParameterValues("education") : null;
		String[] subjectList = pRequest.getParameterValues("subject")!=null ? pRequest.getParameterValues("subject") : null;
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
		account.setPassword(account.getId_no().substring(account.getId_no().length()-4,account.getId_no().length()));
		account.setIdentity_id(identity_id);
		account.setContent_provision(content_provision);
		account.setContent_audit(content_audit);
		account.setStatus("1");
		account.setCreate_by(accountSession.getAccount());
		account.setUpdate_by(accountSession.getAccount());
		int id = teacherAccountService.add(account);
		account.setId(String.valueOf(id));
		
		List<TeacherAccountOption> tao = new ArrayList<TeacherAccountOption>();
		account.setTeacherAccountOption(tao);
		
		TeacherAccountOption teacherAccountOption = new TeacherAccountOption();
		if(educationList != null) {
			for(int i=0; i<educationList.length; i++) {
				teacherAccountOption.setTeacher_account_id(String.valueOf(id));
				teacherAccountOption.setType("3");
				teacherAccountOption.setCode(educationList[i]);
				teacherAccountOption.setCreate_by(accountSession.getAccount());
				teacherAccountOptionService.add(teacherAccountOption);
				
				tao.add(teacherAccountOption);
			}
		}
		
		if(subjectList != null) {
			teacherAccountOption = new TeacherAccountOption();
			for(int i=0; i<subjectList.length; i++) {
//				System.out.println(fieldList[i]);
				teacherAccountOption.setTeacher_account_id(String.valueOf(id));
				teacherAccountOption.setType("1");
				teacherAccountOption.setCode(subjectList[i]);
				teacherAccountOption.setCreate_by(accountSession.getAccount());
				teacherAccountOptionService.add(teacherAccountOption);
				
				tao.add(teacherAccountOption);
			}
		}
		
		model.addAttribute("ACT", "teacher");
		model.addAttribute("PATH", "/teacher");
		return "front/path";
    }
	
	@RequestMapping(value = "/teacher/content" , method = {RequestMethod.POST, RequestMethod.GET})
    public String teacherContent(
    	@SessionAttribute("accountSession") Account accountSession,
    	@ModelAttribute Account account,
    	HttpServletRequest pRequest,
    	Model model){
		
		Account data = teacherAccountService.data(account);
		model.addAttribute("data", data);
		
		String search_content_provision = (pRequest.getParameter("search_content_provision")==null || "0".equals(pRequest.getParameter("search_content_provision"))) ? "0" : "1";
		String search_content_audit = (pRequest.getParameter("search_content_audit")==null || "0".equals(pRequest.getParameter("search_content_audit"))) ? "0" : "1";
		account.setSearch_content_provision(search_content_provision);
		account.setSearch_content_audit(search_content_audit);
		
		//選單
		List<Map<String, Object>> menu = functionController.menu(accountSession, menuName);
		model.addAttribute("menu", menu);
		
		return "front/teacherAccount/content";
    }
	
	@RequestMapping(value = "/teacher/member" , method = {RequestMethod.POST, RequestMethod.GET})
    public String teacherMember(
    	@SessionAttribute("accountSession") Account accountSession,
    	@ModelAttribute Account account,
    	HttpServletRequest pRequest,
    	Model model){
		
		account = new Account();
		account.setId(accountSession.getId());
		Account data = teacherAccountService.data(account);
		model.addAttribute("data", data);
		
		//取得學制清單
		TeacherAccountOption teacherAccountOption = new TeacherAccountOption();
		teacherAccountOption.setTeacher_account_id(data.getId());
		teacherAccountOption.setType("3");
		Map<String, Object> educationOption = teacherAccountOptionService.educationOption(teacherAccountOption);
		String educationStr = educationOption!=null ? educationOption.get("OPTION").toString() : "";
		model.addAttribute("educationStr", educationStr);
		
		//取得學科清單
		teacherAccountOption = new TeacherAccountOption();
		teacherAccountOption.setTeacher_account_id(data.getId());
		teacherAccountOption.setType("1");
		Map<String, Object> subjectOption = teacherAccountOptionService.fieldOption(teacherAccountOption);
		String subjectStr = subjectOption!=null ? subjectOption.get("OPTION").toString() : "";
		model.addAttribute("subjectStr", subjectStr);

		//選單
		List<Map<String, Object>> menu = functionController.menu(accountSession, menuName);
		model.addAttribute("menu", menu);
		
		return "front/teacherAccount/member";
    }
	
	@RequestMapping(value = "/teacher/edit/password" , method = {RequestMethod.POST, RequestMethod.GET})
    public String teacherEditPw(
    	@SessionAttribute("accountSession") Account accountSession,
    	@ModelAttribute Account account,
    	HttpServletRequest pRequest,
    	Model model){

		account.setId(accountSession.getId());
		teacherAccountService.updatePassword(account);
		model.addAttribute("PATH", "/teacher/member");
//		model.addAttribute("MESSAGE", "驗碼帳號及密碼重製錯誤");
		return "front/path";
    }
	
	@RequestMapping(value = "/teacher/mail" , method = {RequestMethod.POST, RequestMethod.GET})
    public String teacherMail(
    	@SessionAttribute("accountSession") Account accountSession,
    	@ModelAttribute Account account,
    	HttpServletRequest pRequest,
    	Model model){
		
		account = new Account();
		account.setId(accountSession.getId());
		Account data = teacherAccountService.data(account);
		model.addAttribute("data", data);

		//選單
		List<Map<String, Object>> menu = functionController.menu(accountSession, menuName);
		model.addAttribute("menu", menu);

		return "front/teacherAccount/mail";
    }
	
	@RequestMapping(value = "/teacher/mail/send" , method = {RequestMethod.POST, RequestMethod.GET})
    public String teacherMailSend(
    	@SessionAttribute("accountSession") Account accountSession,
    	@ModelAttribute Account account,
    	HttpServletRequest pRequest,
    	Model model){
		String title = pRequest.getParameter("title") == null ? "" : pRequest.getParameter("title");
		String message = pRequest.getParameter("message") == null ? "" : pRequest.getParameter("message");
		String status = "F";
		account = new Account();
		account.setStatus("1");
		account.setLevel("2");
		List<Map<String, Object>> manager = teacherAccountService.managerList(account);
				
		try {
			
			if(manager != null && manager.size() > 0) {
				for(int i = 0;i < manager.size(); i++) {
					commonService.sendEmail(manager.get(i).get("EMAIL").toString(), title, message);
				}
			}else {
				model.addAttribute("MESSAGE", "無管理者");
			}
			
			status = "T";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if("T".equals(status)) {
			if(manager != null) {
				model.addAttribute("MESSAGE", "寄信成功");
			}
		}else {
			model.addAttribute("MESSAGE", "寄信錯誤");
		}
		model.addAttribute("PATH", "/teacher/member");
		return "front/path";
    }
	
	@RequestMapping(value = "/teacher/edit" , method = {RequestMethod.POST, RequestMethod.GET})
    public String teacherEdit(
    	@SessionAttribute("accountSession") Account accountSession,
    	@ModelAttribute Account account,
    	HttpServletRequest pRequest,
    	Model model){
		
		int page = account.getPage();
		int total_page = account.getTotal_page();
		int page_count = account.getPage_count();
		
		Account search = account;
		
		account = teacherAccountService.data(account);
		account.setSearch_position(search.getSearch_position());
		account.setSearch_content_provision(search.getSearch_content_provision());
		account.setSearch_content_audit(search.getSearch_content_audit());
		account.setSearch_name(search.getSearch_name());
		account.setSearch_subject(search.getSearch_subject());
		account.setSearch_school_master(search.getSearch_school_master());
		account.setSearch_email(search.getSearch_email());
		
		account.setPage(page);
		account.setTotal_page(total_page);
		account.setPage_count(page_count);
		model.addAttribute("account", account);
		
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
		
		//取得學制清單
		TeacherAccountOption teacherAccountOption = new TeacherAccountOption();
		teacherAccountOption.setTeacher_account_id(account.getId());
		teacherAccountOption.setType("3");
		Map<String, Object> educationOption = teacherAccountOptionService.option(teacherAccountOption);
		String educationStr = educationOption!=null ? educationOption.get("OPTION").toString() : "";
		model.addAttribute("educationStr", educationStr);
		
		//取得學科清單
		teacherAccountOption = new TeacherAccountOption();
		teacherAccountOption.setTeacher_account_id(account.getId());
		teacherAccountOption.setType("1");
		Map<String, Object> subjectOption = teacherAccountOptionService.option(teacherAccountOption);
		String subjectStr = subjectOption!=null ? subjectOption.get("OPTION").toString() : "";
		model.addAttribute("subjectStr", subjectStr);
		
		//選單
		List<Map<String, Object>> menu = functionController.menu(accountSession, menuName);
		model.addAttribute("menu", menu);
		
		return "front/teacherAccount/edit";
    }
	
	@AuditAction(action = Action.UPDATE, targetTable = "teacher_account")
	@RequestMapping(value = "/teacher/editSubmit" , method = {RequestMethod.POST, RequestMethod.GET})
    public String editSubmit(
    		@ModelAttribute Account account,
    		@SessionAttribute("accountSession") Account accountSession,
    		HttpServletRequest pRequest,
    		Model model){
		
		String[] educationList = pRequest.getParameterValues("education")!=null ? pRequest.getParameterValues("education") : null;
		String[] subjectList = pRequest.getParameterValues("subject")!=null ? pRequest.getParameterValues("subject") : null;
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
//		account.setStatus("1");
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
		
		if(subjectList != null) {
			teacherAccountOption = new TeacherAccountOption();
			teacherAccountOption.setTeacher_account_id(account.getId());
			teacherAccountOption.setType("1");
			teacherAccountOptionService.delete(teacherAccountOption);
			for(int i=0; i<subjectList.length; i++) {
//				System.out.println(fieldList[i]);
				teacherAccountOption.setTeacher_account_id(account.getId());
				teacherAccountOption.setType("1");
				teacherAccountOption.setCode(subjectList[i]);
				teacherAccountOption.setCreate_by(accountSession.getAccount());
				teacherAccountOptionService.add(teacherAccountOption);
			}
		}
		
		//分頁
		model.addAttribute("page", account.getPage());
		model.addAttribute("total_page", account.getTotal_page());
		model.addAttribute("page_count", account.getPage_count());		
		model.addAttribute("account", account);
	
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
	        	String mobile_phone = sheet.getCell(3, i).getContents() == "" ? null : sheet.getCell(3, i).getContents();
	        	String telephone = sheet.getCell(4, i).getContents() == "" ? null : sheet.getCell(4, i).getContents();
	        	String email = sheet.getCell(5, i).getContents() == "" ? null : sheet.getCell(5, i).getContents();        	
	        	String address_zip = sheet.getCell(6, i).getContents() == "" ? null : sheet.getCell(6, i).getContents();
	        	String address_city = sheet.getCell(7, i).getContents() == "" ? null : sheet.getCell(7, i).getContents();
	        	String address_area = sheet.getCell(8, i).getContents() == "" ? null : sheet.getCell(8, i).getContents();
	        	String address_road = sheet.getCell(9, i).getContents() == "" ? null : sheet.getCell(9, i).getContents();      	
	        	String census_zip = sheet.getCell(10, i).getContents() == "" ? null : sheet.getCell(10, i).getContents();
	        	String census_city = sheet.getCell(11, i).getContents() == "" ? null : sheet.getCell(11, i).getContents();
	        	String census_area = sheet.getCell(12, i).getContents() == "" ? null : sheet.getCell(12, i).getContents();
	        	String census_road = sheet.getCell(13, i).getContents() == "" ? null : sheet.getCell(13, i).getContents();
	        	String bank = sheet.getCell(14, i).getContents() == "" ? null : sheet.getCell(14, i).getContents();
	        	String branch = sheet.getCell(15, i).getContents() == "" ? null : sheet.getCell(15, i).getContents();
	        	String remittance_account = sheet.getCell(16, i).getContents() == "" ? null : sheet.getCell(16, i).getContents();
	        	String education_id = sheet.getCell(17, i).getContents() == "" ? null : sheet.getCell(17, i).getContents();
	        	String subject_id = sheet.getCell(18, i).getContents() == "" ? null : sheet.getCell(18, i).getContents();
	        	String content_provision = "".equals(sheet.getCell(19, i).getContents()) ? null : ("是".equals(sheet.getCell(19, i).getContents()) ? "1" : "0");
	        	String content_audit = "".equals(sheet.getCell(20, i).getContents()) ? null : ("是".equals(sheet.getCell(20, i).getContents()) ? "1" : "0");
	        	String position = "1";
	        	String status = "1";
	        	
	        	if(name == null) {
	        		break;
	        	}
	        	
//	        	if(email == null) {
//	        		continue;
//	        	}
	        	
//	        	if(phone == null) {
//	        		continue;
//	        	}
	        	
	        	int check_id = teacherAccountService.checkAccount(id_no, "0");
	        	
	        	if(check_id != 0) {
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
	        	
//	        	Field field = new Field();
//	        	field.setName(field_name);
//	        	Map<String ,Object> fieldId = fieldService.searchName(field);
//	        	String field_id = fieldId!=null ? fieldId.get("ID").toString() : null;
	        	
	        	account = new Account();
	        	account.setAccount(id_no);
	        	account.setPassword(id_no.substring(id_no.length()-4,id_no.length()));
	        	account.setName(name);
	        	account.setSchool_master_id(school_master_id);
	        	account.setId_no(id_no);
	        	account.setMobile_phone(mobile_phone);
	        	account.setTelephone(telephone);
	        	account.setEmail(email);
	        	account.setAddress_zip(address_zip);
	        	account.setAddress_city(address_city);
	        	account.setAddress_area(address_area);
	        	account.setAddress_road(address_road);
	        	account.setCensus_zip(census_zip);
	        	account.setCensus_city(census_city);
	        	account.setCensus_area(census_area);
	        	account.setCensus_road(census_road);
	        	account.setBank(bank);
	        	account.setBranch(branch);
	        	account.setRemittance_account(remittance_account);
	        	account.setEducation_id(education_id);
	        	account.setSubject_id(subject_id);
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
		model.addAttribute("ACT", "teacher");
		return "front/path";
		
	}
	
	@RequestMapping(value = "/teacher/send/verify" , method = {RequestMethod.GET, RequestMethod.POST})
	public void sendVerify(HttpServletRequest pRequest, HttpServletResponse pResponse, Model model) throws Exception {
		
		Account account = new Account();
		
		account.setAccount(pRequest.getParameter("account") == null ? "" : pRequest.getParameter("account"));
		
		account.setContent_audit("1");
		account.setEducation_id("2");
		account.setSubject_id("1");
		
		String cKey = "tKb_ma#Na^ge_Api";
		Calendar date = Calendar.getInstance();
		DateFormat yyyymmdd = new SimpleDateFormat("yyyyMMddHHmmss");
		String yyyymmddStr = yyyymmdd.format(date.getTime());
		String temp = account.getAccount() + "," + yyyymmddStr;
		String content = commonService.encrypt(temp, cKey);
		
		//10分鐘內不可重複寄送
		
		List<Map<String, Object>> list = teacherAccountService.getAuditorByEduSub(account);

		JSONArray tJSONArray = new JSONArray(list);
		
		pResponse.setCharacterEncoding("utf-8");
		PrintWriter out = pResponse.getWriter();
		out.write(tJSONArray.toString());
		
	}
	
	@RequestMapping(value = "/teacher/forget/password" , method = {RequestMethod.POST, RequestMethod.GET})
    public String forgetPassword(Model model, HttpServletRequest pRequest){	
		
		Account account = new Account();
		account.setAccount(pRequest.getParameter("forgetAccount") == null ? "" : pRequest.getParameter("forgetAccount"));
		account = teacherAccountService.dataByAccount(account);
		
		String ip = getIp(pRequest);
		String verify_code = randomCode(8);

		try {
			
			TeacherPasswordLog teacherPasswordLog = new TeacherPasswordLog();
			teacherPasswordLog.setTeacher_account_id(account.getId());
			teacherPasswordLog.setAccount(account.getAccount());
			teacherPasswordLog.setOld_password(account.getPassword());
			teacherPasswordLog.setIp(ip);
			teacherPasswordLog.setVerify_code(verify_code);
			teacherPasswordLog.setVerify_status("1");
			teacherPasswordLog.setVerify_count("1");
			teacherPasswordLog.setFrom_type("2");
			int id = teacherPasswordLogService.add(teacherPasswordLog);
			
			String code = commonService.encrypt(verify_code+","+account.getAccount()+","+id, pwKey);
			String url = webPath + "teacher/forget/Verify?code="+code;
			String subject = "素養管理平台-忘記密碼";
		    String message = "<html><body>老師您好:<br/> "
		    		 	   + "請點擊以下連結重製密碼<br/>"
		    		 	   + "<a href='"+url+"'>請點此處</a><br/>"
		    		 	   + "若連結失效請用瀏覽器開啟以下連結<br/>"
		    		 	   + url
		     			   + "</body></html>";
			commonService.sendEmail(account.getEmail(), subject, message);
			return "front/teacherAccount/forget";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		model.addAttribute("PATH", "/");
		model.addAttribute("MESSAGE", "忘記密碼發生錯誤,即將跳回首頁");
		return "front/path";
    }
	
	@RequestMapping(value = "/teacher/forget/Verify" , method = {RequestMethod.GET, RequestMethod.POST})
	public String forgetPasswordVerify(HttpServletRequest pRequest, HttpServletResponse pResponse, Model model) {
		String code = pRequest.getParameter("code") == null ? "" : pRequest.getParameter("code");
		code = code.replaceAll(" ", "+");//加密後如果有+號,瀏覽器會轉成空白,須再轉回來
		
		String deStr;
		try {
			deStr = commonService.decrypt(code, pwKey);
			String codeArray[] = deStr.split(",");
			Account account = new Account();
			account.setAccount(codeArray[1]);
			account = teacherAccountService.dataByAccount(account);
			
			TeacherPasswordLog teacherPasswordLog = new TeacherPasswordLog();
			teacherPasswordLog.setAccount(account.getAccount());
			teacherPasswordLog.setVerify_status("1");
			teacherPasswordLog.setId(codeArray[2]);
			teacherPasswordLog = teacherPasswordLogService.data(teacherPasswordLog);
			
			//比對驗證碼
			if(codeArray[0].equals(teacherPasswordLog.getVerify_code())) {
				account.setPassword(account.getId_no().substring(account.getId_no().length()-4,account.getId_no().length()));
				teacherAccountService.updateVerify(account);
				
				//寫入更新密碼後LOG
				teacherPasswordLog.setNew_password(account.getPassword());
				teacherPasswordLog.setVerify_status("2");
				teacherPasswordLogService.update(teacherPasswordLog);
				
				return "front/teacherAccount/updatePw";
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		model.addAttribute("PATH", "/");
		model.addAttribute("MESSAGE", "驗碼帳號及密碼重製錯誤");
		return "front/path";
		
	}
	
	@RequestMapping(value = "/teacher/forget/check" , method = {RequestMethod.GET, RequestMethod.POST})
	public void forgetPasswordCheck(HttpServletRequest pRequest, HttpServletResponse pResponse, Model model) throws Exception {
		
		String status = "F";
		Account account = new Account();
		account.setAccount(pRequest.getParameter("account") == null ? "" : pRequest.getParameter("account"));
		account = teacherAccountService.dataByAccount(account);
		
		if(account != null) {
			TeacherPasswordLog teacherPasswordLog = new TeacherPasswordLog();
			teacherPasswordLog.setAccount(account.getAccount());
			teacherPasswordLog = teacherPasswordLogService.dataBy1Hour(teacherPasswordLog);
			
			if(teacherPasswordLog == null) {
				status = "T";
			}else {
				status = "E";//一小時內重複送出
			}
			
		}

		pResponse.setCharacterEncoding("utf-8");
		PrintWriter out = pResponse.getWriter();
		out.write(status);
		
	}
	
	private String getIp(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;
	}
	
	private String randomCode(int num) {
		
		String text = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";//儲存數字0-9 和 大小寫字母
		char[] ch = new char[num]; //宣告一個字元陣列物件ch 儲存 驗證碼
		for (int i = 0; i < num; i++) {
			Random random = new Random();//建立一個新的隨機數生成器
			int index = random.nextInt(text.length());//返回[0,string.length)範圍的int值    作用：儲存下標
			ch[i] = text.charAt(index);//charAt() : 返回指定索引處的 char 值   ==》儲存到字元陣列物件ch裡面
		}
		return String.valueOf(ch);
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
			
			//設定學制代碼
			String educationCode = "國小".equals(contract.getSubject_name()) ? "A" : "國中".equals(contract.getSubject_name()) ? "B" : "高中".equals(contract.getSubject_name()) ? "C" : "D";
			
			//取得學科代碼
			String subject_id = contract.getSubject_id();
			Subject subject = new Subject();
			subject.setId(subject_id);
			subject = subjectService.data(subject);
			
			//設定合約流水號，規則：老師授權合約代號1碼+學制1碼+學科2碼
			contract.setContract_id("T"+educationCode+subject.getCode());
			
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
			contractService.add(contract);
			
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
		
		//取得學制清單
		Education education = new Education();
		education.setId(contractMaterial.getEducation_id());
		education = educationService.data(education);
		model.addAttribute("educationName", education.getName());
		
		//取得學科資料
		Subject subject = new Subject();
		subject.setId(contractMaterial.getSubject_id());
		subject = subjectService.data(subject);
		model.addAttribute("subjectName", subject.getName());
		
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
			contractMaterial.setContract_id("M"+contractMaterial.getContract_id());
			contractMaterial.setCreate_by(accountSession.getAccount());
			contractMaterial.setUpdate_by(accountSession.getAccount());
			int id = contractMaterialService.add(contractMaterial);
			//修改合約序號，規則：年月日+流水號後四碼=共10碼
//			contractMaterial = new ContractMaterial();
//			contractMaterial.setId(String.valueOf(id));
//			contractMaterialService.updateTeacherId(contractMaterial);
			
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
//			lessonPlan.setFile_status("A");
			int addCount = lessonPlanService.uploadStatusCount(lessonPlan);
			model.addAttribute("addCount", addCount);
			//待修訂數
			lessonPlan.setFile_status("B");
			int bCount = lessonPlanService.uploadStatusCount(lessonPlan);
			lessonPlan.setFile_status("D");
			int dCount = lessonPlanService.uploadStatusCount(lessonPlan);
			model.addAttribute("editCount", bCount+dCount);
			//已完稿數
			lessonPlan.setFile_status("F");
			int doneCount = lessonPlanService.uploadStatusCount(lessonPlan);
			model.addAttribute("doneCount", doneCount);
		} else if(level == 4) {
			lessonPlan.setAuditor2(accountSession.getAccount());
			list = lessonPlanService.auditList(lessonPlan);
			count = lessonPlanService.auditCount(lessonPlan);
		} else if(level == 5) {
			lessonPlan.setEducation_id(accountSession.getEducation_list());
			lessonPlan.setSubject_id(accountSession.getSubject_list());
			list = lessonPlanService.principalList(lessonPlan);
			count = lessonPlanService.principalCount(lessonPlan);
		} else if(level == 6) {
			lessonPlan.setEducation_id(accountSession.getEducation_list());
			lessonPlan.setSubject_id(accountSession.getSubject_list());
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
		contract.setLesson_num("0");	//設定0，表示教案數需大於0才顯示
		List<Map<String, Object>> contractList = contractService.getList(contract);
		model.addAttribute("contractList", contractList);
		
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
		
		//取得素材分類清單
		MaterialType materialType = new MaterialType();
		materialType.setParent_id("0");
		List<Map<String, Object>> materialTypeList = materialTypeService.list(materialType);
		model.addAttribute("materialTypeList", materialTypeList);
		
		//選單
		List<Map<String, Object>> menu = functionController.menu(accountSession, menuName);
		model.addAttribute("menu", menu);
		
		return "front/lessonPlan/add";
    }
	
	@RequestMapping(value = "/lesson/addSubmit" , method = {RequestMethod.GET, RequestMethod.POST})
	public String lessonAddSubmit(Model model,
		@SessionAttribute("accountSession") Account accountSession,
		@ModelAttribute LessonPlan lessonPlan,
		@RequestParam("word") MultipartFile word,
		@RequestParam("pdf") MultipartFile pdf,
		@RequestParam(value="attachment", required=false) MultipartFile[] attachment,
		@RequestParam(value="contractMat", required=false) MultipartFile[] contractMat,
		HttpServletRequest pRequest
		) {
		
		try {
			
			//取得相關領域審核人(校長)
			Account account = new Account();
			account.setAccount(accountSession.getAccount());
			account.setPosition("3"); //改成編輯號,maybe "4"
			account.setEducation_id(lessonPlan.getEducation_id());
			account.setSubject_id(lessonPlan.getSubject_id());
			Map<String, Object> getAuditor = teacherAccountService.getAuditor(account);
			
			//新增
			lessonPlan.setLesson_plan_number(lessonPlan.getContract_id()+"A");
			lessonPlan.setAuditor(getAuditor.get("ACCOUNT").toString());
			lessonPlan.setFile_status("A");
			lessonPlan.setCreate_by(accountSession.getAccount());
			lessonPlan.setUpdate_by(accountSession.getAccount());
			int id = lessonPlanService.add(lessonPlan);
			
			/**
			 * 設定檔名
			 * 檔名編碼規則：
			 * 1：國小(E)，2：國中(J)，3：高中(S)
			 * 1：國文(C)，2：英文(E)，3：數學(M)
			 * 教案流水號：3碼
			 * 命題流水號：4碼
			 */
			LessonPlan lp = new LessonPlan();
			lp.setEducation_id(lessonPlan.getEducation_id());
			lp.setSubject_id(lessonPlan.getSubject_id());
			lp.setId(String.valueOf(id));
			Map<String, Object> getNum = lessonPlanService.getNum(lp);
			String file_name = edu[Integer.valueOf(lessonPlan.getEducation_id())-1]+sub[Integer.valueOf(lessonPlan.getSubject_id())-1]+"-L-"+getNum.get("NUM").toString();
			
			LessonPlanOption lessonPlanOption = new LessonPlanOption();
			
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
			
			//設定初審(校長)
			LessonPlanAudit lessonPlanAudit = new LessonPlanAudit();
			lessonPlanAudit.setLesson_plan_id(String.valueOf(id));
			lessonPlanAudit.setAuditor(getAuditor.get("ACCOUNT").toString());
			lessonPlanAudit.setVersion("A");
			lessonPlanAudit.setFile_status("A");
			lessonPlanAudit.setCreate_by(accountSession.getAccount());
			lessonPlanAudit.setUpdate_by(accountSession.getAccount());
			int lpa_id = lessonPlanAuditService.add(lessonPlanAudit);
			
			//初稿(word)
//			System.out.println("word:"+word.getOriginalFilename());
			
			//教案檔案
			LessonPlanFile lessonPlanFile = new LessonPlanFile();
			lessonPlanFile.setLesson_plan_id(String.valueOf(id));
			lessonPlanFile.setLesson_plan_audit_id(String.valueOf(lpa_id));
			
			String uploadName = commonService.uploadFileSaveDateName(word, uploadedFolder+"file/lessonPlan/");
			lessonPlanFile.setData_type("1");
			lessonPlanFile.setMaterial_type_id(null);
			lessonPlanFile.setUpload_name(uploadName);
			lessonPlanFile.setFile_name(file_name+word.getOriginalFilename().substring(word.getOriginalFilename().lastIndexOf("."), word.getOriginalFilename().length()));
			lessonPlanFile.setDisplay("1");
			lessonPlanFile.setCreate_by(accountSession.getAccount());
			lessonPlanFile.setUpdate_by(accountSession.getAccount());
			lessonPlanFileService.add(lessonPlanFile);
			
			//初稿(pdf)
//			System.out.println("pdf:"+pdf.getOriginalFilename());
			
			lessonPlanFile = new LessonPlanFile();
			lessonPlanFile.setLesson_plan_id(String.valueOf(id));
			lessonPlanFile.setLesson_plan_audit_id(String.valueOf(lpa_id));
			
			uploadName = commonService.uploadFileSaveDateName(pdf, uploadedFolder+"file/lessonPlan/");
			lessonPlanFile.setData_type("2");
			lessonPlanFile.setMaterial_type_id(null);
			lessonPlanFile.setMaterial_link(null);
			lessonPlanFile.setUpload_name(uploadName);
			lessonPlanFile.setFile_name(file_name+pdf.getOriginalFilename().substring(pdf.getOriginalFilename().lastIndexOf("."), pdf.getOriginalFilename().length()));
			lessonPlanFile.setDisplay("1");
			lessonPlanFile.setCreate_by(accountSession.getAccount());
			lessonPlanFile.setUpdate_by(accountSession.getAccount());
			lessonPlanFileService.add(lessonPlanFile);
			
			//附件
			String[] materialType = pRequest.getParameterValues("material_type");
			String[] link = pRequest.getParameterValues("link");
			if((materialType!=null && link!=null && !attachment[0].isEmpty()) && (materialType.length==attachment.length && link.length==attachment.length)) {
				lessonPlanFile = new LessonPlanFile();
				lessonPlanFile.setLesson_plan_id(String.valueOf(id));
				lessonPlanFile.setLesson_plan_audit_id(String.valueOf(lpa_id));
				for(int i=0; i<attachment.length; i++) {
					if(!"4".equals(materialType[i])) {
//						System.out.println("attachment"+i+":"+attachment[i].getOriginalFilename());
						uploadName = commonService.uploadFileSaveDateName(attachment[i], uploadedFolder+"file/lessonPlan/");
						lessonPlanFile.setData_type("3");
						lessonPlanFile.setMaterial_type_id(materialType[i]);
						lessonPlanFile.setMaterial_link(null);
						lessonPlanFile.setUpload_name(uploadName);
						lessonPlanFile.setFile_name(attachment[i].getOriginalFilename());
					} else {
						lessonPlanFile.setData_type("3");
						lessonPlanFile.setMaterial_type_id(materialType[i]);
						lessonPlanFile.setMaterial_link(!"".equals(link[i]) ? link[i] : null);
						lessonPlanFile.setUpload_name(null);
						lessonPlanFile.setFile_name(null);
					}
					lessonPlanFile.setDisplay("1");
					lessonPlanFile.setCreate_by(accountSession.getAccount());
					lessonPlanFile.setUpdate_by(accountSession.getAccount());
					lessonPlanFileService.add(lessonPlanFile);
				}
			}
			
			//素材授權
			if(!contractMat[0].isEmpty()) {
				lessonPlanFile = new LessonPlanFile();
				lessonPlanFile.setLesson_plan_id(String.valueOf(id));
				lessonPlanFile.setLesson_plan_audit_id(String.valueOf(lpa_id));
				for(int i=0; i<contractMat.length; i++) {
//					System.out.println("contractMat"+i+":"+contractMat[i].getOriginalFilename());
					uploadName = commonService.uploadFileSaveDateName(contractMat[i], uploadedFolder+"file/lessonPlan/");
					lessonPlanFile.setData_type("4");
					lessonPlanFile.setMaterial_type_id(null);
					lessonPlanFile.setMaterial_link(null);
					lessonPlanFile.setUpload_name(uploadName);
					lessonPlanFile.setFile_name(contractMat[i].getOriginalFilename());
					lessonPlanFile.setDisplay("1");
					lessonPlanFile.setCreate_by(accountSession.getAccount());
					lessonPlanFile.setUpdate_by(accountSession.getAccount());
					lessonPlanFileService.add(lessonPlanFile);
				}
			}
			
			//關鍵字
			LessonPlanTag lessonPlanTag = new LessonPlanTag();
			lessonPlanTag.setLesson_plan_id(String.valueOf(id));
			
			String[] tag = lessonPlan.getTag() != null ? lessonPlan.getTag().split(",") : null;
			if(tag != null && tag.length > 0) {
				for(int i=0; i<tag.length; i++) {
					lessonPlanTag.setName(tag[i]);
					lessonPlanTag.setCreate_by(accountSession.getAccount());
					lessonPlanTag.setUpdate_by(accountSession.getAccount());
					lessonPlanTagService.add(lessonPlanTag);
				}
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
	
	@RequestMapping(value = "/lesson/content" , method = {RequestMethod.POST, RequestMethod.GET})
    public String lessonContent(@SessionAttribute("accountSession") Account accountSession, @ModelAttribute LessonPlan lessonPlan, Model model){
		
		//取得教案資料
		LessonPlan data = lessonPlanService.data(lessonPlan);
		model.addAttribute("data", data);
		
		//取得學制資料
		Education education = new Education();
		education.setId(data.getEducation_id());
		education = educationService.data(education);
		model.addAttribute("education_name", education.getName());
		
		//取得年級清單
		LessonPlanOption lessonPlanOption = new LessonPlanOption();
		lessonPlanOption.setLesson_plan_id(lessonPlan.getId());
		lessonPlanOption.setType("4");
		Map<String, Object> gradeOption = lessonPlanOptionService.gradeOption(lessonPlanOption);
		String go = gradeOption.get("OPTION").toString();
		model.addAttribute("gradeOption", go);
		
		//取得學科資料
		Subject subject = new Subject();
		subject.setId(data.getSubject_id());
		subject = subjectService.data(subject);
		model.addAttribute("subject_name", subject.getName());
		
		//取得跨科清單
		lessonPlanOption = new LessonPlanOption();
		lessonPlanOption.setLesson_plan_id(lessonPlan.getId());
		lessonPlanOption.setType("2");
		Map<String, Object> subjectOption = lessonPlanOptionService.subjectOption(lessonPlanOption);
		String so = subjectOption.get("OPTION").toString();
		model.addAttribute("subjectOption", so);
		
		//取得內容上傳歷程
		LessonPlanAudit lessonPlanAudit = new LessonPlanAudit();
		lessonPlanAudit.setLesson_plan_id(lessonPlan.getId());
		List<Map<String, Object>> auditList = lessonPlanAuditService.historyList(lessonPlanAudit);
		model.addAttribute("auditList", auditList);
		
		LessonPlanFile lessonPlanFile = new LessonPlanFile();
		lessonPlanFile.setLesson_plan_id(lessonPlan.getId());
		List<Map<String, Object>> fileList = lessonPlanFileService.historyList(lessonPlanFile);
		model.addAttribute("fileList", fileList);
		
		//取得標籤
		LessonPlanTag lessonPlanTag = new LessonPlanTag();
		lessonPlanTag.setLesson_plan_id(lessonPlan.getId());
		List<Map<String, Object>> tagList = lessonPlanTagService.tagList(lessonPlanTag);
		model.addAttribute("tagList", tagList);
		
		//取得檔案清單
		lessonPlanFile = new LessonPlanFile();
		lessonPlanFile.setLesson_plan_id(lessonPlan.getId());
		List<Map<String, Object>> getFile = lessonPlanFileService.getFile(lessonPlanFile);
		model.addAttribute("getFile", getFile);
		
		//取得附件清單
		List<Map<String, Object>> getAnnex = lessonPlanFileService.getAnnex(lessonPlanFile);
		model.addAttribute("getAnnex", getAnnex);
		
		//取得授權申請清單
		List<Map<String, Object>> getMaterial = lessonPlanFileService.getMaterial(lessonPlanFile);
		model.addAttribute("getMaterial", getMaterial);
		
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
		
		//取得年級清單
		Education education = new Education();
		education.setParent_id(data.getEducation_id());
		List<Map<String, Object>> gradeList = educationService.getChild(education);
		model.addAttribute("gradeList", gradeList);
		
		//串接年級資料
		LessonPlanOption lessonPlanOption = new LessonPlanOption();
		lessonPlanOption.setLesson_plan_id(lessonPlan.getId());
		lessonPlanOption.setType("4");
		Map<String, Object> gradeOption = lessonPlanOptionService.option(lessonPlanOption);
		String go = gradeOption.get("OPTION").toString();
		model.addAttribute("gradeOption", go);
		
		//取得跨科清單
		Subject subject = new Subject();
		subject.setParent_id(data.getSubject_id());
		List<Map<String, Object>> crossSubjectList = subjectService.getChild(subject);
		model.addAttribute("crossSubjectList", crossSubjectList);
		
		//串接跨科資料
		lessonPlanOption = new LessonPlanOption();
		lessonPlanOption.setLesson_plan_id(lessonPlan.getId());
		lessonPlanOption.setType("2");
		Map<String, Object> subjectOption = lessonPlanOptionService.option(lessonPlanOption);
		String so = subjectOption.get("OPTION").toString();
		model.addAttribute("subjectOption", so);
		
		//取得內容上傳歷程
		LessonPlanAudit lessonPlanAudit = new LessonPlanAudit();
		lessonPlanAudit.setLesson_plan_id(lessonPlan.getId());
		List<Map<String, Object>> auditList = lessonPlanAuditService.historyList(lessonPlanAudit);
		model.addAttribute("auditList", auditList);
		
		LessonPlanFile lessonPlanFile = new LessonPlanFile();
		lessonPlanFile.setLesson_plan_id(lessonPlan.getId());
		List<Map<String, Object>> fileList = lessonPlanFileService.historyList(lessonPlanFile);
		model.addAttribute("fileList", fileList);
		
		//取得標籤
		LessonPlanTag lessonPlanTag = new LessonPlanTag();
		lessonPlanTag.setLesson_plan_id(lessonPlan.getId());
		List<Map<String, Object>> tagList = lessonPlanTagService.tagList(lessonPlanTag);
		model.addAttribute("tagList", tagList);
		
		//取得檔案清單
		lessonPlanFile = new LessonPlanFile();
		lessonPlanFile.setLesson_plan_id(lessonPlan.getId());
		List<Map<String, Object>> getFile = lessonPlanFileService.getFile(lessonPlanFile);
		model.addAttribute("getFile", getFile);
		
		//取得附件清單
		List<Map<String, Object>> getAnnex = lessonPlanFileService.getAnnex(lessonPlanFile);
		model.addAttribute("getAnnex", getAnnex);
		
		//取得授權申請清單
		List<Map<String, Object>> getMaterial = lessonPlanFileService.getMaterial(lessonPlanFile);
		model.addAttribute("getMaterial", getMaterial);
		
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
			
			//學科
			String[] subjectOption = pRequest.getParameter("subjectOption").split(",");
			String[] subject = pRequest.getParameterValues("crossSubject");
			if(!Arrays.equals(subjectOption, subject)) {
				lessonPlanOption.setType("2");
				lessonPlanOptionService.delete(lessonPlanOption);
				for(int i=0; i<subject.length; i++) {
					lessonPlanOption = new LessonPlanOption();
					lessonPlanOption.setLesson_plan_id(lessonPlan.getId());
					lessonPlanOption.setType("2");
					lessonPlanOption.setCode(subject[i]);
					lessonPlanOption.setCreate_by(accountSession.getAccount());
					lessonPlanOptionService.add(lessonPlanOption);
				}
			}
			
			//年級
			String[] grade = pRequest.getParameterValues("grade");
			String[] gradeOption = pRequest.getParameter("gradeOption").split(",");
			if(!Arrays.equals(gradeOption, grade)) {
				lessonPlanOption.setType("4");
				lessonPlanOptionService.delete(lessonPlanOption);
				for(int i=0; i<grade.length; i++) {
					lessonPlanOption = new LessonPlanOption();
					lessonPlanOption.setLesson_plan_id(lessonPlan.getId());
					lessonPlanOption.setType("4");
					lessonPlanOption.setCode(grade[i]);
					lessonPlanOption.setCreate_by(accountSession.getAccount());
					lessonPlanOptionService.add(lessonPlanOption);
				}
			}
			
			String tagOld = pRequest.getParameter("tagOld").replace(" ", "");
			lessonPlan.setTag(lessonPlan.getTag().replace(" ", "").replace("\t", "").replace("\r", "").replace("\n", ""));
			
			if(!tagOld.equals(lessonPlan.getTag())) {
				//關鍵字
				LessonPlanTag lessonPlanTag = new LessonPlanTag();
				lessonPlanTag.setLesson_plan_id(lessonPlan.getId());
				lessonPlanTagService.delete(lessonPlanTag);
				
				String[] tag = lessonPlan.getTag() != null ? lessonPlan.getTag().split(",") : null;
				if(tag != null && tag.length > 0) {
					for(int i=0; i<tag.length; i++) {
						lessonPlanTag.setName(tag[i]);
						lessonPlanTag.setCreate_by(accountSession.getAccount());
						lessonPlanTag.setUpdate_by(accountSession.getAccount());
						lessonPlanTagService.add(lessonPlanTag);
					}
				}
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
			
			//通過：1，待修正：0
			String verify = pRequest.getParameter("verify");
			//系統指派：1，人工設定：0
			String verifyMethod = pRequest.getParameter("verifyMethod");
			//審核狀態(A：初審中，B：初審待修正，C：初審通過 / 審核中，D：審核待修正，E：審核通過 / 完稿確認，F：完稿，G：完稿待修正)
			String file_status = pRequest.getParameter("file_status");
			String version = "B";
			
			LessonPlanAudit lessonPlanAudit = new LessonPlanAudit();
			
			//通過
			if("1".equals(verify)) {
				if("A".equals(file_status)) {
					file_status = "C";
					version = "B";
				} else if("C".equals(file_status)) {
					file_status = "E";
					version = "C";
				} else if("E".equals(file_status)) {
					file_status = "F";
					version = "D";
				}
				
				//系統指派
				if("1".equals(verifyMethod)) {
					//隨機選擇審核人
					Account account = new Account();
					account.setAccount(lessonPlan.getCreate_by());
					account.setPosition("1");
					account.setEducation_id(lessonPlan.getEducation_id());
					account.setSubject_id(lessonPlan.getSubject_id());
					account.setContent_audit("1");
					Map<String, Object> getAuditor = teacherAccountService.getAuditor(account);
					lessonPlanAudit.setAuditor(getAuditor.get("ACCOUNT").toString());
					lessonPlan.setAuditor2(getAuditor.get("ACCOUNT").toString());
				//人工設定
				} else if("0".equals(verifyMethod)) {
					String auditor = pRequest.getParameter("auditor");
					lessonPlanAudit.setAuditor(auditor);
					lessonPlan.setAuditor2(auditor);
				}
				
			//待修正
			} else {
				if("A".equals(file_status)) {
					file_status = "B";
					version = "B";
				} else if("C".equals(file_status)) {
					file_status = "D";
					version = "C";
				} else if("E".equals(file_status)) {
					file_status = "G";
					version = "D";
				}
				
				//回饋意見
				String audit_feedback = pRequest.getParameter("audit_feedback");
				if(!"".equals(audit_feedback)) {
//					System.out.println(audit_feedback);
					lessonPlanAudit.setAudit_feedback(audit_feedback);
				}
				
			}
			
			//新增審核紀錄
			lessonPlanAudit.setLesson_plan_id(lessonPlan.getId());
			lessonPlanAudit.setFile_status(file_status);
			lessonPlanAudit.setAuditor(accountSession.getAccount());
			lessonPlanAudit.setVersion(version);
			lessonPlanAudit.setCreate_by(accountSession.getAccount());
			lessonPlanAudit.setUpdate_by(accountSession.getAccount());
			int lpa_id = lessonPlanAuditService.add(lessonPlanAudit);
			
			//回饋檔案
			if(fileName!=null && !fileName.isEmpty()) {
				//教案檔案
				LessonPlanFile lessonPlanFile = new LessonPlanFile();
				lessonPlanFile.setLesson_plan_id(lessonPlan.getId());
				lessonPlanFile.setLesson_plan_audit_id(String.valueOf(lpa_id));
				
				//取得檔案清單
				List<Map<String, Object>> getFile = lessonPlanFileService.getFile(lessonPlanFile);
				String pdf_name = getFile.get(1).get("file_name").toString();
				
				String uploadName = commonService.uploadFileSaveDateName(fileName, uploadedFolder+"file/lessonPlan/");
				lessonPlanFile.setData_type("5");
				lessonPlanFile.setMaterial_type_id(null);
				lessonPlanFile.setMaterial_link(null);
				lessonPlanFile.setUpload_name(uploadName);
				lessonPlanFile.setFile_name(pdf_name.substring(0,pdf_name.lastIndexOf("."))+fileName.getOriginalFilename().substring(fileName.getOriginalFilename().lastIndexOf("."), fileName.getOriginalFilename().length()));
				lessonPlanFile.setDisplay("1");
				lessonPlanFile.setCreate_by(accountSession.getAccount());
				lessonPlanFile.setUpdate_by(accountSession.getAccount());
				lessonPlanFileService.add(lessonPlanFile);
			}
			
			//審核
			lessonPlan.setFile_status(file_status);
			lessonPlanService.audit(lessonPlan);
			
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
		
		//取得學制資料
		Education education = new Education();
		education.setId(data.getEducation_id());
		education = educationService.data(education);
		model.addAttribute("education_name", education.getName());
		
		//取得年級清單
		LessonPlanOption lessonPlanOption = new LessonPlanOption();
		lessonPlanOption.setLesson_plan_id(lessonPlan.getId());
		lessonPlanOption.setType("4");
		Map<String, Object> gradeOption = lessonPlanOptionService.gradeOption(lessonPlanOption);
		String go = gradeOption.get("OPTION").toString();
		model.addAttribute("gradeOption", go);
		
		//取得學科資料
		Subject subject = new Subject();
		subject.setId(data.getSubject_id());
		subject = subjectService.data(subject);
		model.addAttribute("subject_name", subject.getName());
		
		//取得跨科清單
		lessonPlanOption = new LessonPlanOption();
		lessonPlanOption.setLesson_plan_id(lessonPlan.getId());
		lessonPlanOption.setType("2");
		Map<String, Object> subjectOption = lessonPlanOptionService.subjectOption(lessonPlanOption);
		String so = subjectOption.get("OPTION").toString();
		model.addAttribute("subjectOption", so);
				
		//取得內容上傳歷程
		LessonPlanAudit lessonPlanAudit = new LessonPlanAudit();
		lessonPlanAudit.setLesson_plan_id(lessonPlan.getId());
		List<Map<String, Object>> auditList = lessonPlanAuditService.historyList(lessonPlanAudit);
		model.addAttribute("auditList", auditList);
		
		LessonPlanFile lessonPlanFile = new LessonPlanFile();
		lessonPlanFile.setLesson_plan_id(lessonPlan.getId());
		List<Map<String, Object>> fileList = lessonPlanFileService.historyList(lessonPlanFile);
		model.addAttribute("fileList", fileList);
				
		//取得標籤
		LessonPlanTag lessonPlanTag = new LessonPlanTag();
		lessonPlanTag.setLesson_plan_id(lessonPlan.getId());
		List<Map<String, Object>> tagList = lessonPlanTagService.tagList(lessonPlanTag);
		model.addAttribute("tagList", tagList);
		
		//取得檔案清單
		lessonPlanFile = new LessonPlanFile();
		lessonPlanFile.setLesson_plan_id(lessonPlan.getId());
		List<Map<String, Object>> getFile = lessonPlanFileService.getFile(lessonPlanFile);
		model.addAttribute("getFile", getFile);
		
		//取得附件清單
		List<Map<String, Object>> getAnnex = lessonPlanFileService.getAnnex(lessonPlanFile);
		model.addAttribute("getAnnex", getAnnex);
		
		//取得授權申請清單
		List<Map<String, Object>> getMaterial = lessonPlanFileService.getMaterial(lessonPlanFile);
		model.addAttribute("getMaterial", getMaterial);
		
		//選單
		List<Map<String, Object>> menu = functionController.menu(accountSession, menuName);
		model.addAttribute("menu", menu);
		
		return "front/lessonPlan/edit";
    }
	
	@RequestMapping(value = "/lesson/editSubmit" , method = {RequestMethod.GET, RequestMethod.POST})
	public String lessonEditSubmit(Model model,
			@SessionAttribute("accountSession") Account accountSession,
			@ModelAttribute LessonPlan lessonPlan,
			@RequestParam("word") MultipartFile word,
			@RequestParam("pdf") MultipartFile pdf,
			HttpServletRequest pRequest
			) {
		
		try {
			
			String file_status = pRequest.getParameter("file_status");
			String auditor = pRequest.getParameter("auditor");
			String auditor2 = pRequest.getParameter("auditor2");
			
			LessonPlanAudit lessonPlanAudit = new LessonPlanAudit();
			
			if("B".equals(file_status)) {
				file_status = "A";
				lessonPlanAudit.setAuditor(auditor);
			}
			
			if("D".equals(file_status)) {
				file_status = "C";
				lessonPlanAudit.setAuditor(auditor2);
			}
			
			//新增審核紀錄
			lessonPlanAudit.setLesson_plan_id(lessonPlan.getId());
			lessonPlanAudit.setVersion("A");
			lessonPlanAudit.setFile_status(file_status);
			lessonPlanAudit.setCreate_by(accountSession.getAccount());
			lessonPlanAudit.setUpdate_by(accountSession.getAccount());
			int lpa_id = lessonPlanAuditService.add(lessonPlanAudit);
			
			//教案檔案
			LessonPlanFile lessonPlanFile = new LessonPlanFile();
			lessonPlanFile.setLesson_plan_id(lessonPlan.getId());
			lessonPlanFile.setLesson_plan_audit_id(String.valueOf(lpa_id));
			
			//取得檔案清單
			List<Map<String, Object>> getFile = lessonPlanFileService.getFile(lessonPlanFile);
			String word_name = getFile.get(0).get("file_name").toString();
			String pdf_name = getFile.get(1).get("file_name").toString();
			
			String uploadName = commonService.uploadFileSaveDateName(word, uploadedFolder+"file/lessonPlan/");
			lessonPlanFile.setData_type("1");
			lessonPlanFile.setMaterial_type_id(null);
			lessonPlanFile.setUpload_name(uploadName);
			lessonPlanFile.setFile_name(word_name);
			lessonPlanFile.setDisplay("1");
			lessonPlanFile.setCreate_by(accountSession.getAccount());
			lessonPlanFile.setUpdate_by(accountSession.getAccount());
			lessonPlanFileService.add(lessonPlanFile);
			
			//初稿(pdf)
//			System.out.println("pdf:"+pdf.getOriginalFilename());
			
			lessonPlanFile = new LessonPlanFile();
			lessonPlanFile.setLesson_plan_id(lessonPlan.getId());
			lessonPlanFile.setLesson_plan_audit_id(String.valueOf(lpa_id));
			
			uploadName = commonService.uploadFileSaveDateName(pdf, uploadedFolder+"file/lessonPlan/");
			lessonPlanFile.setData_type("2");
			lessonPlanFile.setMaterial_type_id(null);
			lessonPlanFile.setMaterial_link(null);
			lessonPlanFile.setUpload_name(uploadName);
			lessonPlanFile.setFile_name(pdf_name);
			lessonPlanFile.setDisplay("1");
			lessonPlanFile.setCreate_by(accountSession.getAccount());
			lessonPlanFile.setUpdate_by(accountSession.getAccount());
			lessonPlanFileService.add(lessonPlanFile);
			
			//審核
			lessonPlan.setFile_status(file_status);
			lessonPlanService.audit(lessonPlan);
			
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
			//命題基本題總需上傳數
			int basicNum = Integer.valueOf(uploadNum.get("BASIC_NUM").toString());
			model.addAttribute("basicNum", basicNum);
			//合約未完成數
//			int undoneCount = contractService.undoneCount(contract);
			List<Map<String, Object>> contractList = contractService.getList(contract);
			int undoneCount = contractList != null ? contractList.size() : 0;
			model.addAttribute("undoneCount", undoneCount);
			//已上傳數
//			lessonPlan.setFile_status("A");
			int addCount = propositionService.uploadStatusCount(proposition);
			model.addAttribute("addCount", addCount);
			//待修訂數
			proposition.setFile_status("B");
			int bCount = propositionService.uploadStatusCount(proposition);
			proposition.setFile_status("D");
			int dCount = propositionService.uploadStatusCount(proposition);
			model.addAttribute("editCount", bCount+dCount);
			//已完稿數
			proposition.setFile_status("F");
			int doneCount = propositionService.uploadStatusCount(proposition);
			model.addAttribute("doneCount", doneCount);
		} else if(level == 4) {
			proposition.setAuditor2(accountSession.getAccount());
			list = propositionService.auditList(proposition);
			count = propositionService.auditCount(proposition);
		} else if(level == 5) {
			proposition.setEducation_id(accountSession.getEducation_list());
			proposition.setSubject_id(accountSession.getSubject_list());
			list = propositionService.principalList(proposition);
			count = propositionService.principalCount(proposition);
		} else if(level == 6) {
			proposition.setEducation_id(accountSession.getEducation_list());
			proposition.setSubject_id(accountSession.getSubject_list());
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
		contract.setBasic_num("0");		//設定0，表示命題選擇題數需大於0才顯示
		List<Map<String, Object>> contractList = contractService.getList(contract);
		model.addAttribute("contractList", contractList);
		
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
		
		//取得素材分類清單
		MaterialType materialType = new MaterialType();
		materialType.setParent_id("0");
		List<Map<String, Object>> materialTypeList = materialTypeService.list(materialType);
		model.addAttribute("materialTypeList", materialTypeList);
		
		//選單
		List<Map<String, Object>> menu = functionController.menu(accountSession, menuName);
		model.addAttribute("menu", menu);
		
		return "front/proposition/basic/add";
    }
	
	@RequestMapping(value = "/proposition/basic/addSubmit" , method = {RequestMethod.GET, RequestMethod.POST})
	public String propositionBasicAddSubmit(Model model,
			@SessionAttribute("accountSession") Account accountSession,
			@ModelAttribute Proposition proposition,
			@RequestParam("word") MultipartFile word,
			@RequestParam("pdf") MultipartFile pdf,
			@RequestParam(value="attachment", required=false) MultipartFile[] attachment,
			@RequestParam(value="contractMat", required=false) MultipartFile[] contractMat,
			HttpServletRequest pRequest
			) {
		
		try {
			
			//取得相關領域審核人(校長)
			Account account = new Account();
			account.setAccount(accountSession.getAccount());
			account.setPosition("3");
			account.setEducation_id(proposition.getEducation_id());
			account.setSubject_id(proposition.getSubject_id());
			Map<String, Object> getAuditor = teacherAccountService.getAuditor(account);
			
			//新增
			proposition.setProposition_number(proposition.getContract_id()+"B");
			proposition.setQuestion_type("1");
			proposition.setAuditor(getAuditor.get("ACCOUNT").toString());
			proposition.setFile_status("A");
			proposition.setCreate_by(accountSession.getAccount());
			proposition.setUpdate_by(accountSession.getAccount());
			int id = propositionService.add(proposition);
			
			/**
			 * 設定檔名
			 * 檔名編碼規則：
			 * 1：國小(E)，2：國中(J)，3：高中(S)
			 * 1：國文(C)，2：英文(E)，3：數學(M)
			 * 教案流水號：3碼
			 * 命題流水號：4碼
			 */
			Proposition p = new Proposition();
			p.setQuestion_type("1");
			p.setEducation_id(proposition.getEducation_id());
			p.setSubject_id(proposition.getSubject_id());
			p.setId(String.valueOf(id));
			Map<String, Object> getNum = propositionService.getNum(p);
			String file_name = edu[Integer.valueOf(proposition.getEducation_id())-1]+sub[Integer.valueOf(proposition.getSubject_id())-1]+"-B-"+getNum.get("NUM").toString();
			
			PropositionOption propositionOption = new PropositionOption();
			
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
			
			//跨學科
			String[] subject = pRequest.getParameterValues("crossSubject");
			for(int i=0; i<subject.length; i++) {
				propositionOption = new PropositionOption();
				propositionOption.setProposition_id(String.valueOf(id));
				propositionOption.setType("2");
				propositionOption.setCode(subject[i]);
				propositionOption.setCreate_by(accountSession.getAccount());
				propositionOptionService.add(propositionOption);
			}
			
			//設定初審(校長)
			PropositionAudit propositionAudit = new PropositionAudit();
			propositionAudit.setProposition_id(String.valueOf(id));
			propositionAudit.setAuditor(getAuditor.get("ACCOUNT").toString());
			propositionAudit.setVersion("A");
			propositionAudit.setFile_status("A");
			propositionAudit.setCreate_by(accountSession.getAccount());
			propositionAudit.setUpdate_by(accountSession.getAccount());
			int lpa_id = propositionAuditService.add(propositionAudit);
			
			//命題檔案
			PropositionFile propositionFile = new PropositionFile();
			propositionFile.setProposition_id(String.valueOf(id));
			propositionFile.setProposition_audit_id(String.valueOf(lpa_id));
			
			String uploadName = commonService.uploadFileSaveDateName(word, uploadedFolder+"file/proposition/");
			propositionFile.setData_type("1");
			propositionFile.setMaterial_type_id(null);
			propositionFile.setUpload_name(uploadName);
			propositionFile.setFile_name(file_name+word.getOriginalFilename().substring(word.getOriginalFilename().lastIndexOf("."), word.getOriginalFilename().length()));
			propositionFile.setDisplay("1");
			propositionFile.setCreate_by(accountSession.getAccount());
			propositionFile.setUpdate_by(accountSession.getAccount());
			propositionFileService.add(propositionFile);
			
			//初稿(pdf)
//			System.out.println("pdf:"+pdf.getOriginalFilename());
			
			propositionFile = new PropositionFile();
			propositionFile.setProposition_id(String.valueOf(id));
			propositionFile.setProposition_audit_id(String.valueOf(lpa_id));
			
			uploadName = commonService.uploadFileSaveDateName(pdf, uploadedFolder+"file/proposition/");
			propositionFile.setData_type("2");
			propositionFile.setMaterial_type_id(null);
			propositionFile.setMaterial_link(null);
			propositionFile.setUpload_name(uploadName);
			propositionFile.setFile_name(file_name+pdf.getOriginalFilename().substring(pdf.getOriginalFilename().lastIndexOf("."), pdf.getOriginalFilename().length()));
			propositionFile.setDisplay("1");
			propositionFile.setCreate_by(accountSession.getAccount());
			propositionFile.setUpdate_by(accountSession.getAccount());
			propositionFileService.add(propositionFile);
			
			//附件
			String[] materialType = pRequest.getParameterValues("material_type");
			String[] link = pRequest.getParameterValues("link");
			if((materialType!=null && link!=null && !attachment[0].isEmpty()) && (materialType.length==attachment.length && link.length==attachment.length)) {
				propositionFile = new PropositionFile();
				propositionFile.setProposition_id(String.valueOf(id));
				propositionFile.setProposition_audit_id(String.valueOf(lpa_id));
				for(int i=0; i<attachment.length; i++) {
					if(!"4".equals(materialType[i])) {
//						System.out.println("attachment"+i+":"+attachment[i].getOriginalFilename());
						uploadName = commonService.uploadFileSaveDateName(attachment[i], uploadedFolder+"file/proposition/");
						propositionFile.setData_type("3");
						propositionFile.setMaterial_type_id(materialType[i]);
						propositionFile.setMaterial_link(null);
						propositionFile.setUpload_name(uploadName);
						propositionFile.setFile_name(attachment[i].getOriginalFilename());
					} else {
						propositionFile.setData_type("3");
						propositionFile.setMaterial_type_id(materialType[i]);
						propositionFile.setMaterial_link(!"".equals(link[i]) ? link[i] : null);
						propositionFile.setUpload_name(null);
						propositionFile.setFile_name(null);
					}
					propositionFile.setDisplay("1");
					propositionFile.setCreate_by(accountSession.getAccount());
					propositionFile.setUpdate_by(accountSession.getAccount());
					propositionFileService.add(propositionFile);
				}
			}
			
			//素材授權
			if(!contractMat[0].isEmpty()) {
				propositionFile = new PropositionFile();
				propositionFile.setProposition_id(String.valueOf(id));
				propositionFile.setProposition_audit_id(String.valueOf(lpa_id));
				for(int i=0; i<contractMat.length; i++) {
//					System.out.println("contractMat"+i+":"+contractMat[i].getOriginalFilename());
					uploadName = commonService.uploadFileSaveDateName(contractMat[i], uploadedFolder+"file/proposition/");
					propositionFile.setData_type("4");
					propositionFile.setMaterial_type_id(null);
					propositionFile.setMaterial_link(null);
					propositionFile.setUpload_name(uploadName);
					propositionFile.setFile_name(contractMat[i].getOriginalFilename());
					propositionFile.setDisplay("1");
					propositionFile.setCreate_by(accountSession.getAccount());
					propositionFile.setUpdate_by(accountSession.getAccount());
					propositionFileService.add(propositionFile);
				}
			}
			
			//關鍵字
			PropositionTag propositionTag = new PropositionTag();
			propositionTag.setProposition_id(String.valueOf(id));
			
			String[] tag = proposition.getTag() != null ? proposition.getTag().split(",") : null;
			if(tag != null && tag.length > 0) {
				for(int i=0; i<tag.length; i++) {
					propositionTag.setName(tag[i]);
					propositionTag.setCreate_by(accountSession.getAccount());
					propositionTag.setUpdate_by(accountSession.getAccount());
					propositionTagService.add(propositionTag);
				}
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
	
	@RequestMapping(value = "/proposition/basic/content" , method = {RequestMethod.POST, RequestMethod.GET})
    public String propositionBasicContent(@SessionAttribute("accountSession") Account accountSession, @ModelAttribute Proposition proposition, Model model){
		
		//取得命題資料
		Proposition data = propositionService.data(proposition);
		model.addAttribute("data", data);
		
		//取得學制資料
		Education education = new Education();
		education.setId(data.getEducation_id());
		education = educationService.data(education);
		model.addAttribute("education_name", education.getName());
		
		//取得年級清單
		PropositionOption propositionOption = new PropositionOption();
		propositionOption.setProposition_id(proposition.getId());
		propositionOption.setType("4");
		Map<String, Object> gradeOption = propositionOptionService.gradeOption(propositionOption);
		String go = gradeOption.get("OPTION").toString();
		model.addAttribute("gradeOption", go);
		
		//取得學科資料
		Subject subject = new Subject();
		subject.setId(data.getSubject_id());
		subject = subjectService.data(subject);
		model.addAttribute("subject_name", subject.getName());
		
		//取得跨科清單
		propositionOption = new PropositionOption();
		propositionOption.setProposition_id(proposition.getId());
		propositionOption.setType("2");
		Map<String, Object> subjectOption = propositionOptionService.subjectOption(propositionOption);
		String so = subjectOption.get("OPTION").toString();
		model.addAttribute("subjectOption", so);
		
		//取得內容上傳歷程
		PropositionAudit propositionAudit = new PropositionAudit();
		propositionAudit.setProposition_id(proposition.getId());
		List<Map<String, Object>> auditList = propositionAuditService.historyList(propositionAudit);
		model.addAttribute("auditList", auditList);
		
		PropositionFile propositionFile = new PropositionFile();
		propositionFile.setProposition_id(proposition.getId());
		List<Map<String, Object>> fileList = propositionFileService.historyList(propositionFile);
		model.addAttribute("fileList", fileList);
		
		//取得標籤
		PropositionTag propositionTag = new PropositionTag();
		propositionTag.setProposition_id(proposition.getId());
		List<Map<String, Object>> tagList = propositionTagService.tagList(propositionTag);
		model.addAttribute("tagList", tagList);
		
		//取得檔案清單
		propositionFile = new PropositionFile();
		propositionFile.setProposition_id(proposition.getId());
		List<Map<String, Object>> getFile = propositionFileService.getFile(propositionFile);
		model.addAttribute("getFile", getFile);
		
		//取得附件清單
		List<Map<String, Object>> getAnnex = propositionFileService.getAnnex(propositionFile);
		model.addAttribute("getAnnex", getAnnex);
		
		//取得授權申請清單
		List<Map<String, Object>> getMaterial = propositionFileService.getMaterial(propositionFile);
		model.addAttribute("getMaterial", getMaterial);
		
		//選單
		List<Map<String, Object>> menu = functionController.menu(accountSession, menuName);
		model.addAttribute("menu", menu);
		
		return "front/proposition/basic/content";
    }
	
	@RequestMapping(value = "/proposition/basic/update" , method = {RequestMethod.POST, RequestMethod.GET})
    public String propositionBasicUpdate(@SessionAttribute("accountSession") Account accountSession, @ModelAttribute Proposition proposition, Model model){
		
		//取得命題資料
		Proposition data = propositionService.data(proposition);
		model.addAttribute("data", data);
		
		//取得年級清單
		Education education = new Education();
		education.setParent_id(data.getEducation_id());
		List<Map<String, Object>> gradeList = educationService.getChild(education);
		model.addAttribute("gradeList", gradeList);
		
		//串接年級資料
		PropositionOption propositionOption = new PropositionOption();
		propositionOption.setProposition_id(proposition.getId());
		propositionOption.setType("4");
		Map<String, Object> gradeOption = propositionOptionService.option(propositionOption);
		String go = gradeOption.get("OPTION").toString();
		model.addAttribute("gradeOption", go);
		
		//取得跨科清單
		Subject subject = new Subject();
		subject.setParent_id(data.getSubject_id());
		List<Map<String, Object>> crossSubjectList = subjectService.getChild(subject);
		model.addAttribute("crossSubjectList", crossSubjectList);
		
		//串接跨科資料
		propositionOption = new PropositionOption();
		propositionOption.setProposition_id(proposition.getId());
		propositionOption.setType("2");
		Map<String, Object> subjectOption = propositionOptionService.option(propositionOption);
		String so = subjectOption.get("OPTION").toString();
		model.addAttribute("subjectOption", so);
		
		//取得內容上傳歷程
		PropositionAudit propositionAudit = new PropositionAudit();
		propositionAudit.setProposition_id(proposition.getId());
		List<Map<String, Object>> auditList = propositionAuditService.historyList(propositionAudit);
		model.addAttribute("auditList", auditList);
		
		PropositionFile propositionFile = new PropositionFile();
		propositionFile.setProposition_id(proposition.getId());
		List<Map<String, Object>> fileList = propositionFileService.historyList(propositionFile);
		model.addAttribute("fileList", fileList);
		
		//取得標籤
		PropositionTag propositionTag = new PropositionTag();
		propositionTag.setProposition_id(proposition.getId());
		List<Map<String, Object>> tagList = propositionTagService.tagList(propositionTag);
		model.addAttribute("tagList", tagList);
		
		//取得檔案清單
		propositionFile = new PropositionFile();
		propositionFile.setProposition_id(proposition.getId());
		List<Map<String, Object>> getFile = propositionFileService.getFile(propositionFile);
		model.addAttribute("getFile", getFile);
		
		//取得附件清單
		List<Map<String, Object>> getAnnex = propositionFileService.getAnnex(propositionFile);
		model.addAttribute("getAnnex", getAnnex);
		
		//取得授權申請清單
		List<Map<String, Object>> getMaterial = propositionFileService.getMaterial(propositionFile);
		model.addAttribute("getMaterial", getMaterial);
		
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
			String[] subjectOption = pRequest.getParameter("subjectOption").split(",");
			String[] subject = pRequest.getParameterValues("crossSubject");
			if(!Arrays.equals(subjectOption, subject)) {
				propositionOption.setType("2");
				propositionOptionService.delete(propositionOption);
				for(int i=0; i<subject.length; i++) {
					propositionOption = new PropositionOption();
					propositionOption.setProposition_id(proposition.getId());
					propositionOption.setType("2");
					propositionOption.setCode(subject[i]);
					propositionOption.setCreate_by(accountSession.getAccount());
					propositionOptionService.add(propositionOption);
				}
			}
			
			//年級
			String[] grade = pRequest.getParameterValues("grade");
			String[] gradeOption = pRequest.getParameter("gradeOption").split(",");
			if(!Arrays.equals(gradeOption, grade)) {
				propositionOption.setType("4");
				propositionOptionService.delete(propositionOption);
				for(int i=0; i<grade.length; i++) {
					propositionOption = new PropositionOption();
					propositionOption.setProposition_id(proposition.getId());
					propositionOption.setType("4");
					propositionOption.setCode(grade[i]);
					propositionOption.setCreate_by(accountSession.getAccount());
					propositionOptionService.add(propositionOption);
				}
			}
			
			String tagOld = pRequest.getParameter("tagOld").replace(" ", "");
			proposition.setTag(proposition.getTag().replace(" ", "").replace("\t", "").replace("\r", "").replace("\n", ""));
			
			if(!tagOld.equals(proposition.getTag())) {
				//關鍵字
				PropositionTag propositionTag = new PropositionTag();
				propositionTag.setProposition_id(proposition.getId());
				propositionTagService.delete(propositionTag);
				
				String[] tag = proposition.getTag() != null ? proposition.getTag().split(",") : null;
				if(tag != null && tag.length > 0) {
					for(int i=0; i<tag.length; i++) {
						propositionTag.setName(tag[i]);
						propositionTag.setCreate_by(accountSession.getAccount());
						propositionTag.setUpdate_by(accountSession.getAccount());
						propositionTagService.add(propositionTag);
					}
				}
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
			
			//通過：1，待修正：0
			String verify = pRequest.getParameter("verify");
			//系統指派：1，人工設定：0
			String verifyMethod = pRequest.getParameter("verifyMethod");
			//審核狀態(A：初審中，B：初審待修正，C：初審通過 / 審核中，D：審核待修正，E：審核通過 / 完稿確認，F：完稿，G：完稿待修正)
			String file_status = pRequest.getParameter("file_status");
			String version = "B";
			
			PropositionAudit propositionAudit = new PropositionAudit();
			
			//通過
			if("1".equals(verify)) {
				if("A".equals(file_status)) {
					file_status = "C";
					version = "B";
				} else if("C".equals(file_status)) {
					file_status = "E";
					version = "C";
				} else if("E".equals(file_status)) {
					file_status = "F";
					version = "D";
				}
				
				//系統指派
				if("1".equals(verifyMethod)) {
					//隨機選擇審核人
					Account account = new Account();
					account.setAccount(proposition.getCreate_by());
					account.setPosition("1");
					account.setEducation_id(proposition.getEducation_id());
					account.setSubject_id(proposition.getSubject_id());
					account.setContent_audit("1");
					Map<String, Object> getAuditor = teacherAccountService.getAuditor(account);
					propositionAudit.setAuditor(getAuditor.get("ACCOUNT").toString());
					proposition.setAuditor2(getAuditor.get("ACCOUNT").toString());
				//人工設定
				} else if("0".equals(verifyMethod)) {
					String auditor = pRequest.getParameter("auditor");
					propositionAudit.setAuditor(auditor);
					proposition.setAuditor2(auditor);
				}
				
			//待修正
			} else {
				if("A".equals(file_status)) {
					file_status = "B";
					version = "B";
				} else if("C".equals(file_status)) {
					file_status = "D";
					version = "C";
				} else if("E".equals(file_status)) {
					file_status = "G";
					version = "D";
				}
				
				//回饋意見
				String audit_feedback = pRequest.getParameter("audit_feedback");
				if(!"".equals(audit_feedback)) {
//					System.out.println(audit_feedback);
					propositionAudit.setAudit_feedback(audit_feedback);
				}
				
			}
			
			//新增審核紀錄
			propositionAudit.setProposition_id(proposition.getId());
			propositionAudit.setFile_status(file_status);
			propositionAudit.setAuditor(accountSession.getAccount());
			propositionAudit.setVersion(version);
			propositionAudit.setCreate_by(accountSession.getAccount());
			propositionAudit.setUpdate_by(accountSession.getAccount());
			int lpa_id = propositionAuditService.add(propositionAudit);
			
			//回饋檔案
			if(fileName!=null && !fileName.isEmpty()) {
				//命題檔案
				PropositionFile propositionFile = new PropositionFile();
				propositionFile.setProposition_id(proposition.getId());
				propositionFile.setProposition_audit_id(String.valueOf(lpa_id));
				
				//取得檔案清單
				List<Map<String, Object>> getFile = propositionFileService.getFile(propositionFile);
				String pdf_name = getFile.get(1).get("file_name").toString();
				
				String uploadName = commonService.uploadFileSaveDateName(fileName, uploadedFolder+"file/proposition/");
				propositionFile.setData_type("5");
				propositionFile.setMaterial_type_id(null);
				propositionFile.setMaterial_link(null);
				propositionFile.setUpload_name(uploadName);
				propositionFile.setFile_name(pdf_name.substring(0,pdf_name.lastIndexOf("."))+fileName.getOriginalFilename().substring(fileName.getOriginalFilename().lastIndexOf("."), fileName.getOriginalFilename().length()));
				propositionFile.setDisplay("1");
				propositionFile.setCreate_by(accountSession.getAccount());
				propositionFile.setUpdate_by(accountSession.getAccount());
				propositionFileService.add(propositionFile);
			}
			
			//審核
			proposition.setFile_status(file_status);
			propositionService.audit(proposition);
			
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
		
		//取得學制資料
		Education education = new Education();
		education.setId(data.getEducation_id());
		education = educationService.data(education);
		model.addAttribute("education_name", education.getName());
		
		//取得年級清單
		PropositionOption propositionOption = new PropositionOption();
		propositionOption.setProposition_id(proposition.getId());
		propositionOption.setType("4");
		Map<String, Object> gradeOption = propositionOptionService.gradeOption(propositionOption);
		String go = gradeOption.get("OPTION").toString();
		model.addAttribute("gradeOption", go);
		
		//取得學科資料
		Subject subject = new Subject();
		subject.setId(data.getSubject_id());
		subject = subjectService.data(subject);
		model.addAttribute("subject_name", subject.getName());
		
		//取得跨科清單
		propositionOption = new PropositionOption();
		propositionOption.setProposition_id(proposition.getId());
		propositionOption.setType("2");
		Map<String, Object> subjectOption = propositionOptionService.subjectOption(propositionOption);
		String so = subjectOption.get("OPTION").toString();
		model.addAttribute("subjectOption", so);
		
		//取得內容上傳歷程
		PropositionAudit propositionAudit = new PropositionAudit();
		propositionAudit.setProposition_id(proposition.getId());
		List<Map<String, Object>> auditList = propositionAuditService.historyList(propositionAudit);
		model.addAttribute("auditList", auditList);
		
		PropositionFile propositionFile = new PropositionFile();
		propositionFile.setProposition_id(proposition.getId());
		List<Map<String, Object>> fileList = propositionFileService.historyList(propositionFile);
		model.addAttribute("fileList", fileList);
		
		//取得標籤
		PropositionTag propositionTag = new PropositionTag();
		propositionTag.setProposition_id(proposition.getId());
		List<Map<String, Object>> tagList = propositionTagService.tagList(propositionTag);
		model.addAttribute("tagList", tagList);
		
		//取得檔案清單
		propositionFile = new PropositionFile();
		propositionFile.setProposition_id(proposition.getId());
		List<Map<String, Object>> getFile = propositionFileService.getFile(propositionFile);
		model.addAttribute("getFile", getFile);
		
		//取得附件清單
		List<Map<String, Object>> getAnnex = propositionFileService.getAnnex(propositionFile);
		model.addAttribute("getAnnex", getAnnex);
		
		//取得授權申請清單
		List<Map<String, Object>> getMaterial = propositionFileService.getMaterial(propositionFile);
		model.addAttribute("getMaterial", getMaterial);
		
		//選單
		List<Map<String, Object>> menu = functionController.menu(accountSession, menuName);
		model.addAttribute("menu", menu);
		
		return "front/proposition/basic/edit";
    }
	
	@RequestMapping(value = "/proposition/basic/editSubmit" , method = {RequestMethod.GET, RequestMethod.POST})
	public String propositionBasicEditSubmit(Model model,
			@SessionAttribute("accountSession") Account accountSession,
			@ModelAttribute Proposition proposition,
			@RequestParam("word") MultipartFile word,
			@RequestParam("pdf") MultipartFile pdf,
			HttpServletRequest pRequest
			) {
		
		try {
			
			String file_status = pRequest.getParameter("file_status");
			String auditor = pRequest.getParameter("auditor");
			String auditor2 = pRequest.getParameter("auditor2");
			
			PropositionAudit propositionAudit = new PropositionAudit();
			
			if("B".equals(file_status)) {
				file_status = "A";
				propositionAudit.setAuditor(auditor);
			}
			
			if("D".equals(file_status)) {
				file_status = "C";
				propositionAudit.setAuditor(auditor2);
			}
			
			//新增審核紀錄
			propositionAudit.setProposition_id(proposition.getId());
			propositionAudit.setVersion("A");
			propositionAudit.setFile_status(file_status);
			propositionAudit.setCreate_by(accountSession.getAccount());
			propositionAudit.setUpdate_by(accountSession.getAccount());
			int lpa_id = propositionAuditService.add(propositionAudit);
			
			//命題檔案
			PropositionFile propositionFile = new PropositionFile();
			propositionFile.setProposition_id(proposition.getId());
			propositionFile.setProposition_audit_id(String.valueOf(lpa_id));
			
			//取得檔案清單
			List<Map<String, Object>> getFile = propositionFileService.getFile(propositionFile);
			String word_name = getFile.get(0).get("file_name").toString();
			String pdf_name = getFile.get(1).get("file_name").toString();
			
			String uploadName = commonService.uploadFileSaveDateName(word, uploadedFolder+"file/proposition/");
			propositionFile.setData_type("1");
			propositionFile.setMaterial_type_id(null);
			propositionFile.setUpload_name(uploadName);
			propositionFile.setFile_name(word_name);
			propositionFile.setDisplay("1");
			propositionFile.setCreate_by(accountSession.getAccount());
			propositionFile.setUpdate_by(accountSession.getAccount());
			propositionFileService.add(propositionFile);
			
			//初稿(pdf)
//			System.out.println("pdf:"+pdf.getOriginalFilename());
			
			propositionFile = new PropositionFile();
			propositionFile.setProposition_id(proposition.getId());
			propositionFile.setProposition_audit_id(String.valueOf(lpa_id));
			
			uploadName = commonService.uploadFileSaveDateName(pdf, uploadedFolder+"file/proposition/");
			propositionFile.setData_type("2");
			propositionFile.setMaterial_type_id(null);
			propositionFile.setMaterial_link(null);
			propositionFile.setUpload_name(uploadName);
			propositionFile.setFile_name(pdf_name);
			propositionFile.setDisplay("1");
			propositionFile.setCreate_by(accountSession.getAccount());
			propositionFile.setUpdate_by(accountSession.getAccount());
			propositionFileService.add(propositionFile);
			
			//審核
			proposition.setFile_status(file_status);
			propositionService.audit(proposition);
			
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
	
	/**
	 * 命題-混合題
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
			//命題基本題總需上傳數
			int questionsGroupNum = Integer.valueOf(uploadNum.get("QUESTIONS_GROUP_NUM").toString());
			model.addAttribute("questionsGroupNum", questionsGroupNum);
			//合約未完成數
//			int undoneCount = contractService.undoneCount(contract);
			List<Map<String, Object>> contractList = contractService.getList(contract);
			int undoneCount = contractList != null ? contractList.size() : 0;
			model.addAttribute("undoneCount", undoneCount);
			//已上傳數
//			lessonPlan.setFile_status("A");
			int addCount = propositionService.uploadStatusCount(proposition);
			model.addAttribute("addCount", addCount);
			//待修訂數
			proposition.setFile_status("B");
			int bCount = propositionService.uploadStatusCount(proposition);
			proposition.setFile_status("D");
			int dCount = propositionService.uploadStatusCount(proposition);
			model.addAttribute("editCount", bCount+dCount);
			//已完稿數
			proposition.setFile_status("F");
			int doneCount = propositionService.uploadStatusCount(proposition);
			model.addAttribute("doneCount", doneCount);
		} else if(level == 4) {
			proposition.setAuditor2(accountSession.getAccount());
			list = propositionService.auditList(proposition);
			count = propositionService.auditCount(proposition);
		} else if(level == 5) {
			proposition.setEducation_id(accountSession.getEducation_list());
			proposition.setSubject_id(accountSession.getSubject_list());
			list = propositionService.principalList(proposition);
			count = propositionService.principalCount(proposition);
		} else if(level == 6) {
			proposition.setEducation_id(accountSession.getEducation_list());
			proposition.setSubject_id(accountSession.getSubject_list());
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
		contract.setQuestions_group_num("0");		//設定0，表示命題混合題數需大於0才顯示
		List<Map<String, Object>> contractList = contractService.getList(contract);
		model.addAttribute("contractList", contractList);
		
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
		
		//取得素材分類清單
		MaterialType materialType = new MaterialType();
		materialType.setParent_id("0");
		List<Map<String, Object>> materialTypeList = materialTypeService.list(materialType);
		model.addAttribute("materialTypeList", materialTypeList);
		
		//選單
		List<Map<String, Object>> menu = functionController.menu(accountSession, menuName);
		model.addAttribute("menu", menu);
		
		return "front/proposition/group/add";
    }
	
	@RequestMapping(value = "/proposition/group/addSubmit" , method = {RequestMethod.GET, RequestMethod.POST})
	public String propositionGroupAddSubmit(Model model,
			@SessionAttribute("accountSession") Account accountSession,
			@ModelAttribute Proposition proposition,
			@RequestParam("word") MultipartFile word,
			@RequestParam("pdf") MultipartFile pdf,
			@RequestParam(value="attachment", required=false) MultipartFile[] attachment,
			@RequestParam(value="contractMat", required=false) MultipartFile[] contractMat,
			HttpServletRequest pRequest
			) {
		
		try {
			
			//取得相關領域審核人(校長)
			Account account = new Account();
			account.setAccount(accountSession.getAccount());
			account.setPosition("3");
			account.setEducation_id(proposition.getEducation_id());
			account.setSubject_id(proposition.getSubject_id());
			Map<String, Object> getAuditor = teacherAccountService.getAuditor(account);
			
			//新增
			proposition.setProposition_number(proposition.getContract_id()+"B");
			proposition.setQuestion_type("2");
			proposition.setAuditor(getAuditor.get("ACCOUNT").toString());
			proposition.setFile_status("A");
			proposition.setCreate_by(accountSession.getAccount());
			proposition.setUpdate_by(accountSession.getAccount());
			int id = propositionService.add(proposition);
			
			/**
			 * 設定檔名
			 * 檔名編碼規則：
			 * 1：國小(E)，2：國中(J)，3：高中(S)
			 * 1：國文(C)，2：英文(E)，3：數學(M)
			 * 教案流水號：3碼
			 * 命題流水號：4碼
			 */
			Proposition p = new Proposition();
			p.setQuestion_type("2");
			p.setEducation_id(proposition.getEducation_id());
			p.setSubject_id(proposition.getSubject_id());
			p.setId(String.valueOf(id));
			Map<String, Object> getNum = propositionService.getNum(p);
			String file_name = edu[Integer.valueOf(proposition.getEducation_id())-1]+sub[Integer.valueOf(proposition.getSubject_id())-1]+"-G-"+getNum.get("NUM").toString();
			
			PropositionOption propositionOption = new PropositionOption();
			
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
			
			//跨學科
			String[] subject = pRequest.getParameterValues("crossSubject");
			for(int i=0; i<subject.length; i++) {
				propositionOption = new PropositionOption();
				propositionOption.setProposition_id(String.valueOf(id));
				propositionOption.setType("2");
				propositionOption.setCode(subject[i]);
				propositionOption.setCreate_by(accountSession.getAccount());
				propositionOptionService.add(propositionOption);
			}
			
			//設定初審(校長)
			PropositionAudit propositionAudit = new PropositionAudit();
			propositionAudit.setProposition_id(String.valueOf(id));
			propositionAudit.setAuditor(getAuditor.get("ACCOUNT").toString());
			propositionAudit.setVersion("A");
			propositionAudit.setFile_status("A");
			propositionAudit.setCreate_by(accountSession.getAccount());
			propositionAudit.setUpdate_by(accountSession.getAccount());
			int lpa_id = propositionAuditService.add(propositionAudit);
			
			//命題檔案
			PropositionFile propositionFile = new PropositionFile();
			propositionFile.setProposition_id(String.valueOf(id));
			propositionFile.setProposition_audit_id(String.valueOf(lpa_id));
			
			String uploadName = commonService.uploadFileSaveDateName(word, uploadedFolder+"file/proposition/");
			propositionFile.setData_type("1");
			propositionFile.setMaterial_type_id(null);
			propositionFile.setUpload_name(uploadName);
			propositionFile.setFile_name(file_name+word.getOriginalFilename().substring(word.getOriginalFilename().lastIndexOf("."), word.getOriginalFilename().length()));
			propositionFile.setDisplay("1");
			propositionFile.setCreate_by(accountSession.getAccount());
			propositionFile.setUpdate_by(accountSession.getAccount());
			propositionFileService.add(propositionFile);
			
			//初稿(pdf)
//			System.out.println("pdf:"+pdf.getOriginalFilename());
			
			propositionFile = new PropositionFile();
			propositionFile.setProposition_id(String.valueOf(id));
			propositionFile.setProposition_audit_id(String.valueOf(lpa_id));
			
			uploadName = commonService.uploadFileSaveDateName(pdf, uploadedFolder+"file/proposition/");
			propositionFile.setData_type("2");
			propositionFile.setMaterial_type_id(null);
			propositionFile.setMaterial_link(null);
			propositionFile.setUpload_name(uploadName);
			propositionFile.setFile_name(file_name+pdf.getOriginalFilename().substring(pdf.getOriginalFilename().lastIndexOf("."), pdf.getOriginalFilename().length()));
			propositionFile.setDisplay("1");
			propositionFile.setCreate_by(accountSession.getAccount());
			propositionFile.setUpdate_by(accountSession.getAccount());
			propositionFileService.add(propositionFile);
			
			//附件
			String[] materialType = pRequest.getParameterValues("material_type");
			String[] link = pRequest.getParameterValues("link");
			if((materialType!=null && link!=null && !attachment[0].isEmpty()) && (materialType.length==attachment.length && link.length==attachment.length)) {
				propositionFile = new PropositionFile();
				propositionFile.setProposition_id(String.valueOf(id));
				propositionFile.setProposition_audit_id(String.valueOf(lpa_id));
				for(int i=0; i<attachment.length; i++) {
					if(!"4".equals(materialType[i])) {
//						System.out.println("attachment"+i+":"+attachment[i].getOriginalFilename());
						uploadName = commonService.uploadFileSaveDateName(attachment[i], uploadedFolder+"file/proposition/");
						propositionFile.setData_type("3");
						propositionFile.setMaterial_type_id(materialType[i]);
						propositionFile.setMaterial_link(null);
						propositionFile.setUpload_name(uploadName);
						propositionFile.setFile_name(attachment[i].getOriginalFilename());
					} else {
						propositionFile.setData_type("3");
						propositionFile.setMaterial_type_id(materialType[i]);
						propositionFile.setMaterial_link(!"".equals(link[i]) ? link[i] : null);
						propositionFile.setUpload_name(null);
						propositionFile.setFile_name(null);
					}
					propositionFile.setDisplay("1");
					propositionFile.setCreate_by(accountSession.getAccount());
					propositionFile.setUpdate_by(accountSession.getAccount());
					propositionFileService.add(propositionFile);
				}
			}
			
			//素材授權
			if(!contractMat[0].isEmpty()) {
				propositionFile = new PropositionFile();
				propositionFile.setProposition_id(String.valueOf(id));
				propositionFile.setProposition_audit_id(String.valueOf(lpa_id));
				for(int i=0; i<contractMat.length; i++) {
//					System.out.println("contractMat"+i+":"+contractMat[i].getOriginalFilename());
					uploadName = commonService.uploadFileSaveDateName(contractMat[i], uploadedFolder+"file/proposition/");
					propositionFile.setData_type("4");
					propositionFile.setMaterial_type_id(null);
					propositionFile.setMaterial_link(null);
					propositionFile.setUpload_name(uploadName);
					propositionFile.setFile_name(contractMat[i].getOriginalFilename());
					propositionFile.setDisplay("1");
					propositionFile.setCreate_by(accountSession.getAccount());
					propositionFile.setUpdate_by(accountSession.getAccount());
					propositionFileService.add(propositionFile);
				}
			}
			
			//關鍵字
			PropositionTag propositionTag = new PropositionTag();
			propositionTag.setProposition_id(String.valueOf(id));
			
			String[] tag = proposition.getTag() != null ? proposition.getTag().split(",") : null;
			if(tag != null && tag.length > 0) {
				for(int i=0; i<tag.length; i++) {
					propositionTag.setName(tag[i]);
					propositionTag.setCreate_by(accountSession.getAccount());
					propositionTag.setUpdate_by(accountSession.getAccount());
					propositionTagService.add(propositionTag);
				}
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
	
	@RequestMapping(value = "/proposition/group/content" , method = {RequestMethod.POST, RequestMethod.GET})
    public String propositionGroupContent(@SessionAttribute("accountSession") Account accountSession, @ModelAttribute Proposition proposition, Model model){
		
		//取得命題資料
		Proposition data = propositionService.data(proposition);
		model.addAttribute("data", data);
		
		//取得學制資料
		Education education = new Education();
		education.setId(data.getEducation_id());
		education = educationService.data(education);
		model.addAttribute("education_name", education.getName());
		
		//取得年級清單
		PropositionOption propositionOption = new PropositionOption();
		propositionOption.setProposition_id(proposition.getId());
		propositionOption.setType("4");
		Map<String, Object> gradeOption = propositionOptionService.gradeOption(propositionOption);
		String go = gradeOption.get("OPTION").toString();
		model.addAttribute("gradeOption", go);
		
		//取得學科資料
		Subject subject = new Subject();
		subject.setId(data.getSubject_id());
		subject = subjectService.data(subject);
		model.addAttribute("subject_name", subject.getName());
		
		//取得跨科清單
		propositionOption = new PropositionOption();
		propositionOption.setProposition_id(proposition.getId());
		propositionOption.setType("2");
		Map<String, Object> subjectOption = propositionOptionService.subjectOption(propositionOption);
		String so = subjectOption.get("OPTION").toString();
		model.addAttribute("subjectOption", so);
		
		//取得內容上傳歷程
		PropositionAudit propositionAudit = new PropositionAudit();
		propositionAudit.setProposition_id(proposition.getId());
		List<Map<String, Object>> auditList = propositionAuditService.historyList(propositionAudit);
		model.addAttribute("auditList", auditList);
		
		PropositionFile propositionFile = new PropositionFile();
		propositionFile.setProposition_id(proposition.getId());
		List<Map<String, Object>> fileList = propositionFileService.historyList(propositionFile);
		model.addAttribute("fileList", fileList);
		
		//取得標籤
		PropositionTag propositionTag = new PropositionTag();
		propositionTag.setProposition_id(proposition.getId());
		List<Map<String, Object>> tagList = propositionTagService.tagList(propositionTag);
		model.addAttribute("tagList", tagList);
		
		//取得檔案清單
		propositionFile = new PropositionFile();
		propositionFile.setProposition_id(proposition.getId());
		List<Map<String, Object>> getFile = propositionFileService.getFile(propositionFile);
		model.addAttribute("getFile", getFile);
		
		//取得附件清單
		List<Map<String, Object>> getAnnex = propositionFileService.getAnnex(propositionFile);
		model.addAttribute("getAnnex", getAnnex);
		
		//取得授權申請清單
		List<Map<String, Object>> getMaterial = propositionFileService.getMaterial(propositionFile);
		model.addAttribute("getMaterial", getMaterial);
		
		//選單
		List<Map<String, Object>> menu = functionController.menu(accountSession, menuName);
		model.addAttribute("menu", menu);
		
		return "front/proposition/group/content";
    }
	
	@RequestMapping(value = "/proposition/group/update" , method = {RequestMethod.POST, RequestMethod.GET})
    public String propositionGroupUpdate(@SessionAttribute("accountSession") Account accountSession, @ModelAttribute Proposition proposition, Model model){
		
		//取得命題資料
		Proposition data = propositionService.data(proposition);
		model.addAttribute("data", data);
		
		//取得年級清單
		Education education = new Education();
		education.setParent_id(data.getEducation_id());
		List<Map<String, Object>> gradeList = educationService.getChild(education);
		model.addAttribute("gradeList", gradeList);
		
		//串接年級資料
		PropositionOption propositionOption = new PropositionOption();
		propositionOption.setProposition_id(proposition.getId());
		propositionOption.setType("4");
		Map<String, Object> gradeOption = propositionOptionService.option(propositionOption);
		String go = gradeOption.get("OPTION").toString();
		model.addAttribute("gradeOption", go);
		
		//取得跨科清單
		Subject subject = new Subject();
		subject.setParent_id(data.getSubject_id());
		List<Map<String, Object>> crossSubjectList = subjectService.getChild(subject);
		model.addAttribute("crossSubjectList", crossSubjectList);
		
		//串接跨科資料
		propositionOption = new PropositionOption();
		propositionOption.setProposition_id(proposition.getId());
		propositionOption.setType("2");
		Map<String, Object> subjectOption = propositionOptionService.option(propositionOption);
		String so = subjectOption.get("OPTION").toString();
		model.addAttribute("subjectOption", so);
		
		//取得內容上傳歷程
		PropositionAudit propositionAudit = new PropositionAudit();
		propositionAudit.setProposition_id(proposition.getId());
		List<Map<String, Object>> auditList = propositionAuditService.historyList(propositionAudit);
		model.addAttribute("auditList", auditList);
		
		PropositionFile propositionFile = new PropositionFile();
		propositionFile.setProposition_id(proposition.getId());
		List<Map<String, Object>> fileList = propositionFileService.historyList(propositionFile);
		model.addAttribute("fileList", fileList);
		
		//取得標籤
		PropositionTag propositionTag = new PropositionTag();
		propositionTag.setProposition_id(proposition.getId());
		List<Map<String, Object>> tagList = propositionTagService.tagList(propositionTag);
		model.addAttribute("tagList", tagList);
		
		//取得檔案清單
		propositionFile = new PropositionFile();
		propositionFile.setProposition_id(proposition.getId());
		List<Map<String, Object>> getFile = propositionFileService.getFile(propositionFile);
		model.addAttribute("getFile", getFile);
		
		//取得附件清單
		List<Map<String, Object>> getAnnex = propositionFileService.getAnnex(propositionFile);
		model.addAttribute("getAnnex", getAnnex);
		
		//取得授權申請清單
		List<Map<String, Object>> getMaterial = propositionFileService.getMaterial(propositionFile);
		model.addAttribute("getMaterial", getMaterial);
		
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
			String[] subjectOption = pRequest.getParameter("subjectOption").split(",");
			String[] subject = pRequest.getParameterValues("crossSubject");
			if(!Arrays.equals(subjectOption, subject)) {
				propositionOption.setType("2");
				propositionOptionService.delete(propositionOption);
				for(int i=0; i<subject.length; i++) {
					propositionOption = new PropositionOption();
					propositionOption.setProposition_id(proposition.getId());
					propositionOption.setType("2");
					propositionOption.setCode(subject[i]);
					propositionOption.setCreate_by(accountSession.getAccount());
					propositionOptionService.add(propositionOption);
				}
			}
			
			//年級
			String[] grade = pRequest.getParameterValues("grade");
			String[] gradeOption = pRequest.getParameter("gradeOption").split(",");
			if(!Arrays.equals(gradeOption, grade)) {
				propositionOption.setType("4");
				propositionOptionService.delete(propositionOption);
				for(int i=0; i<grade.length; i++) {
					propositionOption = new PropositionOption();
					propositionOption.setProposition_id(proposition.getId());
					propositionOption.setType("4");
					propositionOption.setCode(grade[i]);
					propositionOption.setCreate_by(accountSession.getAccount());
					propositionOptionService.add(propositionOption);
				}
			}
			
			String tagOld = pRequest.getParameter("tagOld").replace(" ", "");
			proposition.setTag(proposition.getTag().replace(" ", "").replace("\t", "").replace("\r", "").replace("\n", ""));
			
			if(!tagOld.equals(proposition.getTag())) {
				//關鍵字
				PropositionTag propositionTag = new PropositionTag();
				propositionTag.setProposition_id(proposition.getId());
				propositionTagService.delete(propositionTag);
				
				String[] tag = proposition.getTag() != null ? proposition.getTag().split(",") : null;
				if(tag != null && tag.length > 0) {
					for(int i=0; i<tag.length; i++) {
						propositionTag.setName(tag[i]);
						propositionTag.setCreate_by(accountSession.getAccount());
						propositionTag.setUpdate_by(accountSession.getAccount());
						propositionTagService.add(propositionTag);
					}
				}
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
			
			//通過：1，待修正：0
			String verify = pRequest.getParameter("verify");
			//系統指派：1，人工設定：0
			String verifyMethod = pRequest.getParameter("verifyMethod");
			//審核狀態(A：初審中，B：初審待修正，C：初審通過 / 審核中，D：審核待修正，E：審核通過 / 完稿確認，F：完稿，G：完稿待修正)
			String file_status = pRequest.getParameter("file_status");
			String version = "B";
			
			PropositionAudit propositionAudit = new PropositionAudit();
			
			//通過
			if("1".equals(verify)) {
				if("A".equals(file_status)) {
					file_status = "C";
					version = "B";
				} else if("C".equals(file_status)) {
					file_status = "E";
					version = "C";
				} else if("E".equals(file_status)) {
					file_status = "F";
					version = "D";
				}
				
				//系統指派
				if("1".equals(verifyMethod)) {
					//隨機選擇審核人
					Account account = new Account();
					account.setAccount(proposition.getCreate_by());
					account.setPosition("1");
					account.setEducation_id(proposition.getEducation_id());
					account.setSubject_id(proposition.getSubject_id());
					account.setContent_audit("1");
					Map<String, Object> getAuditor = teacherAccountService.getAuditor(account);
					propositionAudit.setAuditor(getAuditor.get("ACCOUNT").toString());
					proposition.setAuditor2(getAuditor.get("ACCOUNT").toString());
				//人工設定
				} else if("0".equals(verifyMethod)) {
					String auditor = pRequest.getParameter("auditor");
					propositionAudit.setAuditor(auditor);
					proposition.setAuditor2(auditor);
				}
				
			//待修正
			} else {
				if("A".equals(file_status)) {
					file_status = "B";
					version = "B";
				} else if("C".equals(file_status)) {
					file_status = "D";
					version = "C";
				} else if("E".equals(file_status)) {
					file_status = "G";
					version = "D";
				}
				
				//回饋意見
				String audit_feedback = pRequest.getParameter("audit_feedback");
				if(!"".equals(audit_feedback)) {
//					System.out.println(audit_feedback);
					propositionAudit.setAudit_feedback(audit_feedback);
				}
				
			}
			
			//新增審核紀錄
			propositionAudit.setProposition_id(proposition.getId());
			propositionAudit.setFile_status(file_status);
			propositionAudit.setAuditor(accountSession.getAccount());
			propositionAudit.setVersion(version);
			propositionAudit.setCreate_by(accountSession.getAccount());
			propositionAudit.setUpdate_by(accountSession.getAccount());
			int lpa_id = propositionAuditService.add(propositionAudit);
			
			//回饋檔案
			if(fileName!=null && !fileName.isEmpty()) {
				//命題檔案
				PropositionFile propositionFile = new PropositionFile();
				propositionFile.setProposition_id(proposition.getId());
				propositionFile.setProposition_audit_id(String.valueOf(lpa_id));
				
				//取得檔案清單
				List<Map<String, Object>> getFile = propositionFileService.getFile(propositionFile);
				String pdf_name = getFile.get(1).get("file_name").toString();
				
				String uploadName = commonService.uploadFileSaveDateName(fileName, uploadedFolder+"file/proposition/");
				propositionFile.setData_type("5");
				propositionFile.setMaterial_type_id(null);
				propositionFile.setMaterial_link(null);
				propositionFile.setUpload_name(uploadName);
				propositionFile.setFile_name(pdf_name.substring(0,pdf_name.lastIndexOf("."))+fileName.getOriginalFilename().substring(fileName.getOriginalFilename().lastIndexOf("."), fileName.getOriginalFilename().length()));
				propositionFile.setDisplay("1");
				propositionFile.setCreate_by(accountSession.getAccount());
				propositionFile.setUpdate_by(accountSession.getAccount());
				propositionFileService.add(propositionFile);
			}
			
			//審核
			proposition.setFile_status(file_status);
			propositionService.audit(proposition);
			
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
		
		//取得學制資料
		Education education = new Education();
		education.setId(data.getEducation_id());
		education = educationService.data(education);
		model.addAttribute("education_name", education.getName());
		
		//取得年級清單
		PropositionOption propositionOption = new PropositionOption();
		propositionOption.setProposition_id(proposition.getId());
		propositionOption.setType("4");
		Map<String, Object> gradeOption = propositionOptionService.gradeOption(propositionOption);
		String go = gradeOption.get("OPTION").toString();
		model.addAttribute("gradeOption", go);
		
		//取得學科資料
		Subject subject = new Subject();
		subject.setId(data.getSubject_id());
		subject = subjectService.data(subject);
		model.addAttribute("subject_name", subject.getName());
		
		//取得跨科清單
		propositionOption = new PropositionOption();
		propositionOption.setProposition_id(proposition.getId());
		propositionOption.setType("2");
		Map<String, Object> subjectOption = propositionOptionService.subjectOption(propositionOption);
		String so = subjectOption.get("OPTION").toString();
		model.addAttribute("subjectOption", so);
		
		//取得內容上傳歷程
		PropositionAudit propositionAudit = new PropositionAudit();
		propositionAudit.setProposition_id(proposition.getId());
		List<Map<String, Object>> auditList = propositionAuditService.historyList(propositionAudit);
		model.addAttribute("auditList", auditList);
		
		PropositionFile propositionFile = new PropositionFile();
		propositionFile.setProposition_id(proposition.getId());
		List<Map<String, Object>> fileList = propositionFileService.historyList(propositionFile);
		model.addAttribute("fileList", fileList);
		
		//取得標籤
		PropositionTag propositionTag = new PropositionTag();
		propositionTag.setProposition_id(proposition.getId());
		List<Map<String, Object>> tagList = propositionTagService.tagList(propositionTag);
		model.addAttribute("tagList", tagList);
		
		//取得檔案清單
		propositionFile = new PropositionFile();
		propositionFile.setProposition_id(proposition.getId());
		List<Map<String, Object>> getFile = propositionFileService.getFile(propositionFile);
		model.addAttribute("getFile", getFile);
		
		//取得附件清單
		List<Map<String, Object>> getAnnex = propositionFileService.getAnnex(propositionFile);
		model.addAttribute("getAnnex", getAnnex);
		
		//取得授權申請清單
		List<Map<String, Object>> getMaterial = propositionFileService.getMaterial(propositionFile);
		model.addAttribute("getMaterial", getMaterial);
		
		//選單
		List<Map<String, Object>> menu = functionController.menu(accountSession, menuName);
		model.addAttribute("menu", menu);
		
		return "front/proposition/group/edit";
    }
	
	@RequestMapping(value = "/proposition/group/editSubmit" , method = {RequestMethod.GET, RequestMethod.POST})
	public String propositionGroupEditSubmit(Model model,
			@SessionAttribute("accountSession") Account accountSession,
			@ModelAttribute Proposition proposition,
			@RequestParam("word") MultipartFile word,
			@RequestParam("pdf") MultipartFile pdf,
			HttpServletRequest pRequest
			) {
		
		try {
			
			String file_status = pRequest.getParameter("file_status");
			String auditor = pRequest.getParameter("auditor");
			String auditor2 = pRequest.getParameter("auditor2");
			
			PropositionAudit propositionAudit = new PropositionAudit();
			
			if("B".equals(file_status)) {
				file_status = "A";
				propositionAudit.setAuditor(auditor);
			}
			
			if("D".equals(file_status)) {
				file_status = "C";
				propositionAudit.setAuditor(auditor2);
			}
			
			//新增審核紀錄
			propositionAudit.setProposition_id(proposition.getId());
			propositionAudit.setVersion("A");
			propositionAudit.setFile_status(file_status);
			propositionAudit.setCreate_by(accountSession.getAccount());
			propositionAudit.setUpdate_by(accountSession.getAccount());
			int lpa_id = propositionAuditService.add(propositionAudit);
			
			//命題檔案
			PropositionFile propositionFile = new PropositionFile();
			propositionFile.setProposition_id(proposition.getId());
			propositionFile.setProposition_audit_id(String.valueOf(lpa_id));
			
			//取得檔案清單
			List<Map<String, Object>> getFile = propositionFileService.getFile(propositionFile);
			String word_name = getFile.get(0).get("file_name").toString();
			String pdf_name = getFile.get(1).get("file_name").toString();
			
			String uploadName = commonService.uploadFileSaveDateName(word, uploadedFolder+"file/proposition/");
			propositionFile.setData_type("1");
			propositionFile.setMaterial_type_id(null);
			propositionFile.setUpload_name(uploadName);
			propositionFile.setFile_name(word_name);
			propositionFile.setDisplay("1");
			propositionFile.setCreate_by(accountSession.getAccount());
			propositionFile.setUpdate_by(accountSession.getAccount());
			propositionFileService.add(propositionFile);
			
			//初稿(pdf)
//			System.out.println("pdf:"+pdf.getOriginalFilename());
			
			propositionFile = new PropositionFile();
			propositionFile.setProposition_id(proposition.getId());
			propositionFile.setProposition_audit_id(String.valueOf(lpa_id));
			
			uploadName = commonService.uploadFileSaveDateName(pdf, uploadedFolder+"file/proposition/");
			propositionFile.setData_type("2");
			propositionFile.setMaterial_type_id(null);
			propositionFile.setMaterial_link(null);
			propositionFile.setUpload_name(uploadName);
			propositionFile.setFile_name(pdf_name);
			propositionFile.setDisplay("1");
			propositionFile.setCreate_by(accountSession.getAccount());
			propositionFile.setUpdate_by(accountSession.getAccount());
			propositionFileService.add(propositionFile);
			
			//審核
			proposition.setFile_status(file_status);
			propositionService.audit(proposition);
			
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
	
	@RequestMapping(value = "/get/auditor" , method = {RequestMethod.GET, RequestMethod.POST})
	public void getAuditor(@SessionAttribute("accountSession") Account accountSession, HttpServletRequest pRequest, HttpServletResponse pResponse, Model model) throws Exception {
		
		Account account = new Account();
		
		String education_id = pRequest.getParameter("education_id") == null ? "" : pRequest.getParameter("education_id");
		String subject_id = pRequest.getParameter("subject_id") == null ? "" : pRequest.getParameter("subject_id");
		
		account.setContent_audit("1");
		account.setEducation_id(education_id);
		account.setSubject_id(subject_id);
		
		List<Map<String, Object>> list = teacherAccountService.getAuditorByEduSub(account);

		JSONArray tJSONArray = new JSONArray(list);
		
		pResponse.setCharacterEncoding("utf-8");
		PrintWriter out = pResponse.getWriter();
		out.write(tJSONArray.toString());
		
	}
}
