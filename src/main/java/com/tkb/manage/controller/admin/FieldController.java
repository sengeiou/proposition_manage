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
import com.tkb.manage.model.Field;
import com.tkb.manage.service.FieldService;

@Controller
@SessionAttributes("accountSession")
@RequestMapping("/tkbrule/field")
public class FieldController {
	
	@Autowired
	private FieldService fieldService;
	
	@Autowired
	private FunctionController functionController;
	
	@Value("${admin.menu.name}") 
	private String menuName;
	
	@RequestMapping(value = "/list" , method = {RequestMethod.POST, RequestMethod.GET})
    public String list(@SessionAttribute("accountSession") Account accountSession, @ModelAttribute Field field, Model model){
		
		if(field.getId() != null) {
			field.setId(null);
			model.addAttribute("parent_name", field.getParent_name());
		} else {
			field.setParent_id("0");
			field.setLayer("1");
			field.setParent_name("學習領域管理");
			model.addAttribute("parent_name", "學習領域管理");
		}
//		System.out.println("parent_id:"+field.getParent_id());
//		System.out.println("layer:"+field.getLayer());
		
		List<Map<String, Object>> list = fieldService.list(field);
		model.addAttribute("list", list);
		
		//取得上一頁資訊
		Field parent = new Field();
		parent.setId(field.getParent_id());
		Map<String, Object> backData = fieldService.backData(parent);
		
		if(backData != null) {
			model.addAttribute("parent_parent_id", backData.get("PARENT_ID").toString());
			String parent_name = backData.get("PARENT_NAME")!=null ? backData.get("PARENT_NAME").toString() : "學習領域管理";
			model.addAttribute("parent_parent_name", parent_name);
		}
		
		//選單
		List<Map<String, Object>> menu = functionController.menu(accountSession, menuName);
		model.addAttribute("menu", menu);
		
		return "admin/field/list";
    }
	
	@RequestMapping(value = "/add" , method = {RequestMethod.POST, RequestMethod.GET})
    public String add(@SessionAttribute("accountSession") Account accountSession, @ModelAttribute Field field, Model model){
		
		//設定上一頁資訊
		model.addAttribute("parent_parent_id", field.getParent_id());
		if("".equals(field.getParent_name())) {
			model.addAttribute("parent_parent_name", "學習領域管理");
		} else {
			model.addAttribute("parent_parent_name", field.getParent_name());
		}
		
		//選單
		List<Map<String, Object>> menu = functionController.menu(accountSession, menuName);
		model.addAttribute("menu", menu);
		
		return "admin/field/form";
    }
	
	@RequestMapping(value = "/addSubmit" , method = {RequestMethod.POST, RequestMethod.GET})
    public String addSubmit(@SessionAttribute("accountSession") Account accountSession, @ModelAttribute Field field, Model model){
		
		int sort = fieldService.maxSort(field);
		field.setSort(String.valueOf(sort));
		field.setCreate_by(accountSession.getAccount());
		field.setUpdate_by(accountSession.getAccount());
		int id = fieldService.add(field);
		
		//取得上一頁資訊
		Field parent = new Field();
		parent.setId(field.getParent_id());
		Map<String, Object> backData = fieldService.backData(parent);
		
		if(backData != null) {
			model.addAttribute("parent_parent_id", backData.get("PARENT_ID").toString());
			String parent_name = backData.get("PARENT_NAME")!=null ? backData.get("PARENT_NAME").toString() : "學習領域管理";
			model.addAttribute("parent_parent_name", parent_name);
		}
		
		model.addAttribute("id", id);
		model.addAttribute("parent_id", field.getParent_id());
		model.addAttribute("parent_name", field.getParent_name());
		model.addAttribute("layer", field.getLayer());
		model.addAttribute("PATH", "list");
		return "admin/path";
    }
	
	@RequestMapping(value = "/update" , method = {RequestMethod.POST, RequestMethod.GET})
    public String update(@SessionAttribute("accountSession") Account accountSession, @ModelAttribute Field field, Model model){
		
		String parent_name = field.getParent_name();
		//設定上一頁資訊
		model.addAttribute("parent_parent_id", field.getParent_id());
		if("".equals(field.getParent_name())) {
			model.addAttribute("parent_parent_name", "學習領域管理");
		} else {
			model.addAttribute("parent_parent_name", field.getParent_name());
		}
		model.addAttribute("parent_name", field.getParent_name());
		
		//取得學習領域資料
		field = fieldService.data(field);
		field.setParent_name(parent_name);
		model.addAttribute("field", field);
		
		//選單
		List<Map<String, Object>> menu = functionController.menu(accountSession, menuName);
		model.addAttribute("menu", menu);
		
		return "admin/field/form";
    }
	
	@RequestMapping(value = "/updateSubmit" , method = {RequestMethod.POST, RequestMethod.GET})
    public String updateSubmit(@SessionAttribute("accountSession") Account accountSession, @ModelAttribute Field field, Model model){
		
		field.setUpdate_by(accountSession.getAccount());
		fieldService.update(field);
		
		//取得上一頁資訊
		Field parent = new Field();
		parent.setId(field.getParent_id());
		Map<String, Object> backData = fieldService.backData(parent);
		
		if(backData != null) {
			model.addAttribute("parent_parent_id", backData.get("PARENT_ID").toString());
			String parent_name = backData.get("PARENT_NAME")!=null ? backData.get("PARENT_NAME").toString() : "學習領域管理";
			model.addAttribute("parent_parent_name", parent_name);
		}
		
		model.addAttribute("id", field.getId());
		model.addAttribute("parent_id", field.getParent_id());
		model.addAttribute("parent_name", field.getParent_name());
		model.addAttribute("layer", field.getLayer());
		model.addAttribute("PATH", "list");
		return "admin/path";
    }
	
	@RequestMapping(value = "/delete" , method = {RequestMethod.POST})
	public String delete(@SessionAttribute("accountSession") Account accountSession, @ModelAttribute Field field, Model model) {
		
		String parent_name = field.getParent_name();
		
		field = fieldService.data(field);
		
		fieldService.delete(field);
		fieldService.updateSortByDelete(field);
		
		if("".equals(parent_name)) {
			model.addAttribute("parent_parent_name", "學習領域管理");
		} else {
			model.addAttribute("parent_parent_name", field.getParent_name());
		}
		
		model.addAttribute("id", field.getId());
		model.addAttribute("parent_id", field.getParent_id());
		model.addAttribute("parent_name", parent_name);
		model.addAttribute("layer", field.getLayer());
		model.addAttribute("PATH", "list");
		return "admin/path";
	}
	
	//藉由學制取得年級
  	@RequestMapping(value = "/get/child" , method = {RequestMethod.GET, RequestMethod.POST})
  	public void child(HttpServletRequest pRequest, HttpServletResponse pResponse, Model model) throws Exception {
  		
  		String id = pRequest.getParameter("id")!=null ? pRequest.getParameter("id") : "";
  		
  		Field field = new Field();
  		field.setParent_id(id);
  		List<Map<String, Object>> list = fieldService.getChild(field);
  		
  		JSONArray tJSONArray = new JSONArray(list);
		pResponse.setCharacterEncoding("utf-8");
		PrintWriter out = pResponse.getWriter();
		out.write(tJSONArray.toString());
  		
  	}
  	
}
