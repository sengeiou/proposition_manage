package com.tkb.manage.controller.admin;

import java.io.PrintWriter;
import java.util.ArrayList;
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
import com.tkb.manage.model.Function;
import com.tkb.manage.service.FunctionService;

@Controller
@SessionAttributes("accountSession")
@RequestMapping("/tkbrule/function")
public class FunctionController {
	
	@Autowired
	private FunctionService functionService;
	
	@Value("${admin.menu.name}") 
	private String menuName;
	
	@RequestMapping(value = "/list" , method = {RequestMethod.POST, RequestMethod.GET})
    public String list(@SessionAttribute("accountSession") Account accountSession, @ModelAttribute Function function, Model model){
		
		if(function.getId() != null) {
			function.setId(null);
			model.addAttribute("parent_name", function.getParent_name());
		} else {
			function.setParent_id("0");
			function.setLayer("1");
			function.setParent_name("功能管理");
			model.addAttribute("parent_name", "功能管理");
		}
//		System.out.println("parent_id:"+function.getParent_id());
//		System.out.println("layer:"+function.getLayer());
		
		List<Map<String, Object>> list = functionService.list(function);
		model.addAttribute("list", list);
		
		//取得上一頁資訊
		Function parent = new Function();
		parent.setId(function.getParent_id());
		Map<String, Object> backData = functionService.backData(parent);
		
		if(backData != null) {
			model.addAttribute("parent_parent_id", backData.get("PARENT_ID").toString());
			String parent_name = backData.get("PARENT_NAME")!=null ? backData.get("PARENT_NAME").toString() : "功能管理";
			model.addAttribute("parent_parent_name", parent_name);
		}
		
		//選單
		List<Map<String, Object>> menu = menu(accountSession, menuName);
		model.addAttribute("menu", menu);
		
		return "admin/function/list";
    }
	
	@RequestMapping(value = "/add" , method = {RequestMethod.POST, RequestMethod.GET})
    public String add(@SessionAttribute("accountSession") Account accountSession, @ModelAttribute Function function, Model model){
		
		//設定上一頁資訊
		model.addAttribute("parent_parent_id", function.getParent_id());
		if("".equals(function.getParent_name())) {
			model.addAttribute("parent_parent_name", "功能管理");
		} else {
			model.addAttribute("parent_parent_name", function.getParent_name());
		}
		
		//選單
		List<Map<String, Object>> menu = menu(accountSession, menuName);
		model.addAttribute("menu", menu);
		
		return "admin/function/form";
    }
	
	@RequestMapping(value = "/addSubmit" , method = {RequestMethod.POST, RequestMethod.GET})
    public String addSubmit(@SessionAttribute("accountSession") Account accountSession, @ModelAttribute Function function, Model model){
		
		if(function.getLevel()==null || "".equals(function.getLevel())) {
			function.setLevel("0");
		}
		if("".equals(function.getLink())) {
			function.setLink(null);
		}
		if("".equals(function.getIcon())) {
			function.setIcon(null);
		}
		int sort = functionService.maxSort(function);
		function.setSort(String.valueOf(sort));
		function.setCreate_by(accountSession.getAccount());
		function.setUpdate_by(accountSession.getAccount());
		int id = functionService.add(function);
		
		//取得上一頁資訊
		Function parent = new Function();
		parent.setId(function.getParent_id());
		Map<String, Object> backData = functionService.backData(parent);
		
		if(backData != null) {
			model.addAttribute("parent_parent_id", backData.get("PARENT_ID").toString());
			String parent_name = backData.get("PARENT_NAME")!=null ? backData.get("PARENT_NAME").toString() : "功能管理";
			model.addAttribute("parent_parent_name", parent_name);
		}
		
		model.addAttribute("id", id);
		model.addAttribute("parent_id", function.getParent_id());
		model.addAttribute("parent_name", function.getParent_name());
		model.addAttribute("layer", function.getLayer());
		model.addAttribute("PATH", "list");
		return "admin/path";
    }
	
	@RequestMapping(value = "/update" , method = {RequestMethod.POST, RequestMethod.GET})
    public String update(@SessionAttribute("accountSession") Account accountSession, @ModelAttribute Function function, Model model){
		
		String parent_name = function.getParent_name();
		//設定上一頁資訊
		model.addAttribute("parent_parent_id", function.getParent_id());
		if("".equals(function.getParent_name())) {
			model.addAttribute("parent_parent_name", "功能管理");
		} else {
			model.addAttribute("parent_parent_name", function.getParent_name());
		}
		model.addAttribute("parent_name", function.getParent_name());
		
		//取得功能資料
		function = functionService.data(function);
		function.setParent_name(parent_name);
		model.addAttribute("function", function);
		
		//選單
		List<Map<String, Object>> menu = menu(accountSession, menuName);
		model.addAttribute("menu", menu);
		
		return "admin/function/form";
    }
	
	@RequestMapping(value = "/updateSubmit" , method = {RequestMethod.POST, RequestMethod.GET})
    public String updateSubmit(@SessionAttribute("accountSession") Account accountSession, @ModelAttribute Function function, Model model){
		
		if(function.getLevel()==null || "".equals(function.getLevel())) {
			function.setLevel("0");
		}
		if("".equals(function.getLink())) {
			function.setLink(null);
		}
		if("".equals(function.getIcon())) {
			function.setIcon(null);
		}
		function.setUpdate_by(accountSession.getAccount());
		functionService.update(function);
		
		//取得上一頁資訊
		Function parent = new Function();
		parent.setId(function.getParent_id());
		Map<String, Object> backData = functionService.backData(parent);
		
		if(backData != null) {
			model.addAttribute("parent_parent_id", backData.get("PARENT_ID").toString());
			String parent_name = backData.get("PARENT_NAME")!=null ? backData.get("PARENT_NAME").toString() : "功能管理";
			model.addAttribute("parent_parent_name", parent_name);
		}
		
		model.addAttribute("id", function.getId());
		model.addAttribute("parent_id", function.getParent_id());
		model.addAttribute("parent_name", function.getParent_name());
		model.addAttribute("layer", function.getLayer());
		model.addAttribute("PATH", "list");
		return "admin/path";
    }
	
	@RequestMapping(value = "/delete" , method = {RequestMethod.POST})
	public String delete(@SessionAttribute("accountSession") Account accountSession, @ModelAttribute Function function, Model model) {
		
		String parent_name = function.getParent_name();
		
		function = functionService.data(function);
		
		functionService.delete(function);
		functionService.updateSortByDelete(function);
		
		if("".equals(parent_name)) {
			model.addAttribute("parent_parent_name", "功能管理");
		} else {
			model.addAttribute("parent_parent_name", function.getParent_name());
		}
		
		model.addAttribute("id", function.getId());
		model.addAttribute("parent_id", function.getParent_id());
		model.addAttribute("parent_name", parent_name);
		model.addAttribute("layer", function.getLayer());
		model.addAttribute("PATH", "list");
		return "admin/path";
	}
	
	//藉由學制取得年級
  	@RequestMapping(value = "/get/child" , method = {RequestMethod.GET, RequestMethod.POST})
  	public void child(HttpServletRequest pRequest, HttpServletResponse pResponse, Model model) throws Exception {
  		
  		String id = pRequest.getParameter("id")!=null ? pRequest.getParameter("id") : "";
  		
  		Function function = new Function();
  		function.setParent_id(id);
  		List<Map<String, Object>> list = functionService.getChild(function);
  		
  		JSONArray tJSONArray = new JSONArray(list);
		pResponse.setCharacterEncoding("utf-8");
		PrintWriter out = pResponse.getWriter();
		out.write(tJSONArray.toString());
  		
  	}
  	
  	//取得選單
  	public List<Map<String, Object>> menu(@SessionAttribute("accountSession") Account accountSession, String name) {
		
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String level = accountSession.getLevel();
		
		Function function = new Function();
		//取得第一層
		function.setName(name);
		function.setLevel(level);
		List<Map<String, Object>> menu1 = functionService.getMenu(function);
		
		//取得第二層
		if(menu1 != null) {
			for(int i=0; i<menu1.size(); i++) {
				list.add(i,menu1.get(i));
				function = new Function();
				function.setParent_id(menu1.get(i).get("ID").toString());
				function.setLevel(level);
				List<Map<String, Object>> menu2 = functionService.getMenu(function);
				list.get(i).put("MENU2", menu2);
			}
		}
		
		return list;
		
	}
	
}
