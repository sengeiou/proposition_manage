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
import com.tkb.manage.model.Identity;
import com.tkb.manage.service.IdentityService;

@Controller
@SessionAttributes("accountSession")
@RequestMapping("/tkbrule/identity")
public class IdentityController {
	
	@Autowired
	private IdentityService identityService;
	
	@Autowired
	private FunctionController functionController;
	
	@Value("${admin.menu.name}") 
	private String menuName;
	
	@RequestMapping(value = "/list" , method = {RequestMethod.POST, RequestMethod.GET})
    public String list(@SessionAttribute("accountSession") Account accountSession, @ModelAttribute Identity identity, Model model){
		
		if(identity.getId() != null) {
			identity.setId(null);
			model.addAttribute("parent_name", identity.getParent_name());
		} else {
			identity.setParent_id("0");
			identity.setLayer("1");
			identity.setParent_name("身份管理");
			model.addAttribute("parent_name", "身份管理");
		}
//		System.out.println("parent_id:"+identity.getParent_id());
//		System.out.println("layer:"+identity.getLayer());
		
		List<Map<String, Object>> list = identityService.list(identity);
		model.addAttribute("list", list);
		
		//取得上一頁資訊
		Identity parent = new Identity();
		parent.setId(identity.getParent_id());
		Map<String, Object> backData = identityService.backData(parent);
		
		if(backData != null) {
			model.addAttribute("parent_parent_id", backData.get("PARENT_ID").toString());
			String parent_name = backData.get("PARENT_NAME")!=null ? backData.get("PARENT_NAME").toString() : "身份管理";
			model.addAttribute("parent_parent_name", parent_name);
		}
		
		//選單
		List<Map<String, Object>> menu = functionController.menu(accountSession, menuName);
		model.addAttribute("menu", menu);
		
		return "admin/identity/list";
    }
	
	@RequestMapping(value = "/add" , method = {RequestMethod.POST, RequestMethod.GET})
    public String add(@SessionAttribute("accountSession") Account accountSession, @ModelAttribute Identity identity, Model model){
		
		//設定上一頁資訊
		model.addAttribute("parent_parent_id", identity.getParent_id());
		if("".equals(identity.getParent_name())) {
			model.addAttribute("parent_parent_name", "身份管理");
		} else {
			model.addAttribute("parent_parent_name", identity.getParent_name());
		}
		
		//選單
		List<Map<String, Object>> menu = functionController.menu(accountSession, menuName);
		model.addAttribute("menu", menu);
		
		return "admin/identity/form";
    }
	
	@RequestMapping(value = "/addSubmit" , method = {RequestMethod.POST, RequestMethod.GET})
    public String addSubmit(@SessionAttribute("accountSession") Account accountSession, @ModelAttribute Identity identity, Model model){
		
		if(identity.getLevel()==null || "".equals(identity.getLevel())) {
			identity.setLevel("0");
		}
		int sort = identityService.maxSort(identity);
		identity.setSort(String.valueOf(sort));
		identity.setCreate_by(accountSession.getAccount());
		identity.setUpdate_by(accountSession.getAccount());
		int id = identityService.add(identity);
		
		//取得上一頁資訊
		Identity parent = new Identity();
		parent.setId(identity.getParent_id());
		Map<String, Object> backData = identityService.backData(parent);
		
		if(backData != null) {
			model.addAttribute("parent_parent_id", backData.get("PARENT_ID").toString());
			String parent_name = backData.get("PARENT_NAME")!=null ? backData.get("PARENT_NAME").toString() : "身份管理";
			model.addAttribute("parent_parent_name", parent_name);
		}
		
		model.addAttribute("id", id);
		model.addAttribute("parent_id", identity.getParent_id());
		model.addAttribute("parent_name", identity.getParent_name());
		model.addAttribute("layer", identity.getLayer());
		model.addAttribute("PATH", "list");
		return "admin/path";
    }
	
	@RequestMapping(value = "/update" , method = {RequestMethod.POST, RequestMethod.GET})
    public String update(@SessionAttribute("accountSession") Account accountSession, @ModelAttribute Identity identity, Model model){
		
		String parent_name = identity.getParent_name();
		//設定上一頁資訊
		model.addAttribute("parent_parent_id", identity.getParent_id());
		if("".equals(identity.getParent_name())) {
			model.addAttribute("parent_parent_name", "身份管理");
		} else {
			model.addAttribute("parent_parent_name", identity.getParent_name());
		}
		model.addAttribute("parent_name", identity.getParent_name());
		
		//取得身份資料
		identity = identityService.data(identity);
		identity.setParent_name(parent_name);
		model.addAttribute("identity", identity);
		
		//選單
		List<Map<String, Object>> menu = functionController.menu(accountSession, menuName);
		model.addAttribute("menu", menu);
		
		return "admin/identity/form";
    }
	
	@RequestMapping(value = "/updateSubmit" , method = {RequestMethod.POST, RequestMethod.GET})
    public String updateSubmit(@SessionAttribute("accountSession") Account accountSession, @ModelAttribute Identity identity, Model model){
		
		if(identity.getLevel()==null || "".equals(identity.getLevel())) {
			identity.setLevel("0");
		}
		identity.setUpdate_by(accountSession.getAccount());
		identityService.update(identity);
		
		//取得上一頁資訊
		Identity parent = new Identity();
		parent.setId(identity.getParent_id());
		Map<String, Object> backData = identityService.backData(parent);
		
		if(backData != null) {
			model.addAttribute("parent_parent_id", backData.get("PARENT_ID").toString());
			String parent_name = backData.get("PARENT_NAME")!=null ? backData.get("PARENT_NAME").toString() : "身份管理";
			model.addAttribute("parent_parent_name", parent_name);
		}
		
		model.addAttribute("id", identity.getId());
		model.addAttribute("parent_id", identity.getParent_id());
		model.addAttribute("parent_name", identity.getParent_name());
		model.addAttribute("layer", identity.getLayer());
		model.addAttribute("PATH", "list");
		return "admin/path";
    }
	
	@RequestMapping(value = "/delete" , method = {RequestMethod.POST})
	public String delete(@SessionAttribute("accountSession") Account accountSession, @ModelAttribute Identity identity, Model model) {
		
		String parent_name = identity.getParent_name();
		
		identity = identityService.data(identity);
		
		identityService.delete(identity);
		identityService.updateSortByDelete(identity);
		
		if("".equals(parent_name)) {
			model.addAttribute("parent_parent_name", "身份管理");
		} else {
			model.addAttribute("parent_parent_name", identity.getParent_name());
		}
		
		model.addAttribute("id", identity.getId());
		model.addAttribute("parent_id", identity.getParent_id());
		model.addAttribute("parent_name", parent_name);
		model.addAttribute("layer", identity.getLayer());
		model.addAttribute("PATH", "list");
		return "admin/path";
	}
	
	//藉由學制取得年級
  	@RequestMapping(value = "/get/child" , method = {RequestMethod.GET, RequestMethod.POST})
  	public void child(HttpServletRequest pRequest, HttpServletResponse pResponse, Model model) throws Exception {
  		
  		String id = pRequest.getParameter("id")!=null ? pRequest.getParameter("id") : "";
  		
  		Identity identity = new Identity();
  		identity.setParent_id(id);
  		List<Map<String, Object>> list = identityService.getChild(identity);
  		
  		JSONArray tJSONArray = new JSONArray(list);
		pResponse.setCharacterEncoding("utf-8");
		PrintWriter out = pResponse.getWriter();
		out.write(tJSONArray.toString());
  		
  	}
  	
}
