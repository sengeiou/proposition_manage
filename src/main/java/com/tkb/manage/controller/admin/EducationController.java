package com.tkb.manage.controller.admin;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.tkb.manage.model.Account;
import com.tkb.manage.model.Education;
import com.tkb.manage.service.EducationService;

@Controller
@SessionAttributes("accountSession")
@RequestMapping("/tkbrule/education")
public class EducationController {
	
	@Autowired
	private EducationService educationService;
	
	@Autowired
	private FunctionController functionController;
	
	@Value("${admin.menu.name}") 
	private String menuName;
	
	@RequestMapping(value = "/list" , method = {RequestMethod.POST, RequestMethod.GET})
    public String list(@SessionAttribute("accountSession") Account accountSession, @ModelAttribute Education education, Model model){
		
		if(education.getId() != null) {
			education.setId(null);
			model.addAttribute("parent_name", education.getParent_name());
		} else {
			education.setParent_id("0");
			education.setLayer("1");
			education.setParent_name("學制年級管理");
			model.addAttribute("parent_name", "學制年級管理");
		}
//		System.out.println("parent_id:"+education.getParent_id());
//		System.out.println("layer:"+education.getLayer());
		
		List<Map<String, Object>> list = educationService.list(education);
		model.addAttribute("list", list);
		
		//取得上一頁資訊
		Education parent = new Education();
		parent.setId(education.getParent_id());
		Map<String, Object> backData = educationService.backData(parent);
		
		if(backData != null) {
			model.addAttribute("parent_parent_id", backData.get("PARENT_ID").toString());
			String parent_name = backData.get("PARENT_NAME")!=null ? backData.get("PARENT_NAME").toString() : "學制年級管理";
			model.addAttribute("parent_parent_name", parent_name);
		}
		
		//選單
		List<Map<String, Object>> menu = functionController.menu(accountSession, menuName);
		model.addAttribute("menu", menu);
		
		return "admin/education/list";
    }
	
	@RequestMapping(value = "/add" , method = {RequestMethod.POST, RequestMethod.GET})
    public String add(@SessionAttribute("accountSession") Account accountSession, @ModelAttribute Education education, Model model){
		
		//設定上一頁資訊
		model.addAttribute("parent_parent_id", education.getParent_id());
		if("".equals(education.getParent_name())) {
			model.addAttribute("parent_parent_name", "學制年級管理");
		} else {
			model.addAttribute("parent_parent_name", education.getParent_name());
		}
		
		//選單
		List<Map<String, Object>> menu = functionController.menu(accountSession, menuName);
		model.addAttribute("menu", menu);
		
		return "admin/education/form";
    }
	
	@RequestMapping(value = "/addSubmit" , method = {RequestMethod.POST, RequestMethod.GET})
    public String addSubmit(@SessionAttribute("accountSession") Account accountSession, @ModelAttribute Education education, Model model){
		
		int sort = educationService.maxSort(education);
		education.setSort(String.valueOf(sort));
		education.setCreate_by(accountSession.getAccount());
		education.setUpdate_by(accountSession.getAccount());
		int id = educationService.add(education);
		
		//取得上一頁資訊
		Education parent = new Education();
		parent.setId(education.getParent_id());
		Map<String, Object> backData = educationService.backData(parent);
		
		if(backData != null) {
			model.addAttribute("parent_parent_id", backData.get("PARENT_ID").toString());
			String parent_name = backData.get("PARENT_NAME")!=null ? backData.get("PARENT_NAME").toString() : "學制年級管理";
			model.addAttribute("parent_parent_name", parent_name);
		}
		
		model.addAttribute("id", id);
		model.addAttribute("parent_id", education.getParent_id());
		model.addAttribute("parent_name", education.getParent_name());
		model.addAttribute("layer", education.getLayer());
		model.addAttribute("PATH", "list");
		return "admin/path";
    }
	
	@RequestMapping(value = "/update" , method = {RequestMethod.POST, RequestMethod.GET})
    public String update(@SessionAttribute("accountSession") Account accountSession, @ModelAttribute Education education, Model model){
		
		String parent_name = education.getParent_name();
		//設定上一頁資訊
		model.addAttribute("parent_parent_id", education.getParent_id());
		if("".equals(education.getParent_name())) {
			model.addAttribute("parent_parent_name", "學制年級管理");
		} else {
			model.addAttribute("parent_parent_name", education.getParent_name());
		}
		model.addAttribute("parent_name", education.getParent_name());
		
		//取得學習領域資料
		education = educationService.data(education);
		education.setParent_name(parent_name);
		model.addAttribute("education", education);
		
		//選單
		List<Map<String, Object>> menu = functionController.menu(accountSession, menuName);
		model.addAttribute("menu", menu);
		
		return "admin/education/form";
    }
	
	@RequestMapping(value = "/updateSubmit" , method = {RequestMethod.POST, RequestMethod.GET})
    public String updateSubmit(@SessionAttribute("accountSession") Account accountSession, @ModelAttribute Education education, Model model){
		
		education.setUpdate_by(accountSession.getAccount());
		educationService.update(education);
		
		//取得上一頁資訊
		Education parent = new Education();
		parent.setId(education.getParent_id());
		Map<String, Object> backData = educationService.backData(parent);
		
		if(backData != null) {
			model.addAttribute("parent_parent_id", backData.get("PARENT_ID").toString());
			String parent_name = backData.get("PARENT_NAME")!=null ? backData.get("PARENT_NAME").toString() : "學制年級管理";
			model.addAttribute("parent_parent_name", parent_name);
		}
		
		model.addAttribute("id", education.getId());
		model.addAttribute("parent_id", education.getParent_id());
		model.addAttribute("parent_name", education.getParent_name());
		model.addAttribute("layer", education.getLayer());
		model.addAttribute("PATH", "list");
		return "admin/path";
    }
	
	@RequestMapping(value = "/delete" , method = {RequestMethod.POST})
	public String delete(@SessionAttribute("accountSession") Account accountSession, @ModelAttribute Education education, Model model) {
		
		String parent_name = education.getParent_name();
		
		education = educationService.data(education);
		
		educationService.delete(education);
		educationService.updateSortByDelete(education);
		
		if("".equals(parent_name)) {
			model.addAttribute("parent_parent_name", "學制年級管理");
		} else {
			model.addAttribute("parent_parent_name", education.getParent_name());
		}
		
		model.addAttribute("id", education.getId());
		model.addAttribute("parent_id", education.getParent_id());
		model.addAttribute("parent_name", parent_name);
		model.addAttribute("layer", education.getLayer());
		model.addAttribute("PATH", "list");
		return "admin/path";
	}
	
	//藉由學制取得年級
  	@RequestMapping(value = "/get/child" , method = {RequestMethod.GET, RequestMethod.POST})
  	public void child(HttpServletRequest pRequest, HttpServletResponse pResponse, Model model) throws Exception {
  		
  		String id = pRequest.getParameter("id")!=null ? pRequest.getParameter("id") : "";
  		
  		Education education = new Education();
  		education.setParent_id(id);
  		List<Map<String, Object>> list = educationService.getChild(education);
  		
  		JSONArray tJSONArray = new JSONArray(list);
		pResponse.setCharacterEncoding("utf-8");
		PrintWriter out = pResponse.getWriter();
		out.write(tJSONArray.toString());
  		
  	}
  	
}
