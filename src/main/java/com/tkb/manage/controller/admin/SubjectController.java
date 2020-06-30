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
import com.tkb.manage.model.Subject;
import com.tkb.manage.service.SubjectService;

@Controller
@SessionAttributes("accountSession")
@RequestMapping("/tkbrule/subject")
public class SubjectController {
	
	@Autowired
	private SubjectService subjectService;
	
	@Autowired
	private FunctionController functionController;
	
	@Value("${admin.menu.name}") 
	private String menuName;
	
	@RequestMapping(value = "/list" , method = {RequestMethod.POST, RequestMethod.GET})
    public String list(@SessionAttribute("accountSession") Account accountSession, @ModelAttribute Subject subject, Model model){
		
		if(subject.getId() != null) {
			subject.setId(null);
			model.addAttribute("parent_name", subject.getParent_name());
		} else {
			subject.setParent_id("0");
			subject.setLayer("1");
			subject.setParent_name("學科管理");
			model.addAttribute("parent_name", "學科管理");
		}
//		System.out.println("parent_id:"+subject.getParent_id());
//		System.out.println("layer:"+subject.getLayer());
		
		List<Map<String, Object>> list = subjectService.list(subject);
		model.addAttribute("list", list);
		
		//取得上一頁資訊
		Subject parent = new Subject();
		parent.setId(subject.getParent_id());
		Map<String, Object> backData = subjectService.backData(parent);
		
		if(backData != null) {
			model.addAttribute("parent_parent_id", backData.get("PARENT_ID").toString());
			String parent_name = backData.get("PARENT_NAME")!=null ? backData.get("PARENT_NAME").toString() : "學科管理";
			model.addAttribute("parent_parent_name", parent_name);
		}
		
		//選單
		List<Map<String, Object>> menu = functionController.menu(accountSession, menuName);
		model.addAttribute("menu", menu);
		
		return "admin/subject/list";
    }
	
	@RequestMapping(value = "/add" , method = {RequestMethod.POST, RequestMethod.GET})
    public String add(@SessionAttribute("accountSession") Account accountSession, @ModelAttribute Subject subject, Model model){
		
		//設定上一頁資訊
		model.addAttribute("parent_parent_id", subject.getParent_id());
		if("".equals(subject.getParent_name())) {
			model.addAttribute("parent_parent_name", "學科管理");
		} else {
			model.addAttribute("parent_parent_name", subject.getParent_name());
		}
		
		//選單
		List<Map<String, Object>> menu = functionController.menu(accountSession, menuName);
		model.addAttribute("menu", menu);
		
		return "admin/subject/form";
    }
	
	@RequestMapping(value = "/addSubmit" , method = {RequestMethod.POST, RequestMethod.GET})
    public String addSubmit(@SessionAttribute("accountSession") Account accountSession, @ModelAttribute Subject subject, Model model){
		
		int sort = subjectService.maxSort(subject);
		subject.setSort(String.valueOf(sort));
		subject.setCreate_by(accountSession.getAccount());
		subject.setUpdate_by(accountSession.getAccount());
		int id = subjectService.add(subject);
		
		//取得上一頁資訊
		Subject parent = new Subject();
		parent.setId(subject.getParent_id());
		Map<String, Object> backData = subjectService.backData(parent);
		
		if(backData != null) {
			model.addAttribute("parent_parent_id", backData.get("PARENT_ID").toString());
			String parent_name = backData.get("PARENT_NAME")!=null ? backData.get("PARENT_NAME").toString() : "學科管理";
			model.addAttribute("parent_parent_name", parent_name);
		}
		
		model.addAttribute("id", id);
		model.addAttribute("parent_id", subject.getParent_id());
		model.addAttribute("parent_name", subject.getParent_name());
		model.addAttribute("layer", subject.getLayer());
		model.addAttribute("PATH", "list");
		return "admin/path";
    }
	
	@RequestMapping(value = "/update" , method = {RequestMethod.POST, RequestMethod.GET})
    public String update(@SessionAttribute("accountSession") Account accountSession, @ModelAttribute Subject subject, Model model){
		
		String parent_name = subject.getParent_name();
		//設定上一頁資訊
		model.addAttribute("parent_parent_id", subject.getParent_id());
		if("".equals(subject.getParent_name())) {
			model.addAttribute("parent_parent_name", "學科管理");
		} else {
			model.addAttribute("parent_parent_name", subject.getParent_name());
		}
		model.addAttribute("parent_name", subject.getParent_name());
		
		//取得學習領域資料
		subject = subjectService.data(subject);
		subject.setParent_name(parent_name);
		model.addAttribute("subject", subject);
		
		//選單
		List<Map<String, Object>> menu = functionController.menu(accountSession, menuName);
		model.addAttribute("menu", menu);
		
		return "admin/subject/form";
    }
	
	@RequestMapping(value = "/updateSubmit" , method = {RequestMethod.POST, RequestMethod.GET})
    public String updateSubmit(@SessionAttribute("accountSession") Account accountSession, @ModelAttribute Subject subject, Model model){
		
		subject.setUpdate_by(accountSession.getAccount());
		subjectService.update(subject);
		
		//取得上一頁資訊
		Subject parent = new Subject();
		parent.setId(subject.getParent_id());
		Map<String, Object> backData = subjectService.backData(parent);
		
		if(backData != null) {
			model.addAttribute("parent_parent_id", backData.get("PARENT_ID").toString());
			String parent_name = backData.get("PARENT_NAME")!=null ? backData.get("PARENT_NAME").toString() : "學科管理";
			model.addAttribute("parent_parent_name", parent_name);
		}
		
		model.addAttribute("id", subject.getId());
		model.addAttribute("parent_id", subject.getParent_id());
		model.addAttribute("parent_name", subject.getParent_name());
		model.addAttribute("layer", subject.getLayer());
		model.addAttribute("PATH", "list");
		return "admin/path";
    }
	
	@RequestMapping(value = "/delete" , method = {RequestMethod.POST})
	public String delete(@SessionAttribute("accountSession") Account accountSession, @ModelAttribute Subject subject, Model model) {
		
		String parent_name = subject.getParent_name();
		
		subject = subjectService.data(subject);
		
		subjectService.delete(subject);
		subjectService.updateSortByDelete(subject);
		
		if("".equals(parent_name)) {
			model.addAttribute("parent_parent_name", "學科管理");
		} else {
			model.addAttribute("parent_parent_name", subject.getParent_name());
		}
		
		model.addAttribute("id", subject.getId());
		model.addAttribute("parent_id", subject.getParent_id());
		model.addAttribute("parent_name", parent_name);
		model.addAttribute("layer", subject.getLayer());
		model.addAttribute("PATH", "list");
		return "admin/path";
	}
	
	//藉由學制取得年級
  	@RequestMapping(value = "/get/child" , method = {RequestMethod.GET, RequestMethod.POST})
  	public void child(HttpServletRequest pRequest, HttpServletResponse pResponse, Model model) throws Exception {
  		
  		String id = pRequest.getParameter("id")!=null ? pRequest.getParameter("id") : "";
  		
  		Subject subject = new Subject();
  		subject.setParent_id(id);
  		List<Map<String, Object>> list = subjectService.getChild(subject);
  		
  		JSONArray tJSONArray = new JSONArray(list);
		pResponse.setCharacterEncoding("utf-8");
		PrintWriter out = pResponse.getWriter();
		out.write(tJSONArray.toString());
  		
  	}
  	
}
