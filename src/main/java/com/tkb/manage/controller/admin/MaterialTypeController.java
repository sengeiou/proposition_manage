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
import com.tkb.manage.model.MaterialType;
import com.tkb.manage.service.MaterialTypeService;

@Controller
@SessionAttributes("accountSession")
@RequestMapping("/tkbrule/material/type")
public class MaterialTypeController {
	
	@Autowired
	private MaterialTypeService materialTypeService;
	
	@Autowired
	private FunctionController functionController;
	
	@Value("${admin.menu.name}") 
	private String menuName;
	
	@RequestMapping(value = "/list" , method = {RequestMethod.POST, RequestMethod.GET})
    public String list(@SessionAttribute("accountSession") Account accountSession, @ModelAttribute MaterialType materialType, Model model){
		
		if(materialType.getId() != null) {
			materialType.setId(null);
			model.addAttribute("parent_name", materialType.getParent_name());
		} else {
			materialType.setParent_id("0");
			materialType.setLayer("1");
			materialType.setParent_name("素材分類管理");
			model.addAttribute("parent_name", "素材分類管理");
		}
//		System.out.println("parent_id:"+materialType.getParent_id());
//		System.out.println("layer:"+materialType.getLayer());
		
		List<Map<String, Object>> list = materialTypeService.list(materialType);
		model.addAttribute("list", list);
		
		//取得上一頁資訊
		MaterialType parent = new MaterialType();
		parent.setId(materialType.getParent_id());
		Map<String, Object> backData = materialTypeService.backData(parent);
		
		if(backData != null) {
			model.addAttribute("parent_parent_id", backData.get("PARENT_ID").toString());
			String parent_name = backData.get("PARENT_NAME")!=null ? backData.get("PARENT_NAME").toString() : "素材分類管理";
			model.addAttribute("parent_parent_name", parent_name);
		}
		
		//選單
		List<Map<String, Object>> menu = functionController.menu(accountSession, menuName);
		model.addAttribute("menu", menu);
		
		return "admin/materialType/list";
    }
	
	@RequestMapping(value = "/add" , method = {RequestMethod.POST, RequestMethod.GET})
    public String add(@SessionAttribute("accountSession") Account accountSession, @ModelAttribute MaterialType materialType, Model model){
		
		//設定上一頁資訊
		model.addAttribute("parent_parent_id", materialType.getParent_id());
		if("".equals(materialType.getParent_name())) {
			model.addAttribute("parent_parent_name", "素材分類管理");
		} else {
			model.addAttribute("parent_parent_name", materialType.getParent_name());
		}
		
		//選單
		List<Map<String, Object>> menu = functionController.menu(accountSession, menuName);
		model.addAttribute("menu", menu);
		
		return "admin/materialType/form";
    }
	
	@RequestMapping(value = "/addSubmit" , method = {RequestMethod.POST, RequestMethod.GET})
    public String addSubmit(@SessionAttribute("accountSession") Account accountSession, @ModelAttribute MaterialType materialType, Model model){
		
		int sort = materialTypeService.maxSort(materialType);
		materialType.setSort(String.valueOf(sort));
		materialType.setCreate_by(accountSession.getAccount());
		materialType.setUpdate_by(accountSession.getAccount());
		int id = materialTypeService.add(materialType);
		
		//取得上一頁資訊
		MaterialType parent = new MaterialType();
		parent.setId(materialType.getParent_id());
		Map<String, Object> backData = materialTypeService.backData(parent);
		
		if(backData != null) {
			model.addAttribute("parent_parent_id", backData.get("PARENT_ID").toString());
			String parent_name = backData.get("PARENT_NAME")!=null ? backData.get("PARENT_NAME").toString() : "素材分類管理";
			model.addAttribute("parent_parent_name", parent_name);
		}
		
		model.addAttribute("id", id);
		model.addAttribute("parent_id", materialType.getParent_id());
		model.addAttribute("parent_name", materialType.getParent_name());
		model.addAttribute("layer", materialType.getLayer());
		model.addAttribute("PATH", "list");
		return "admin/path";
    }
	
	@RequestMapping(value = "/update" , method = {RequestMethod.POST, RequestMethod.GET})
    public String update(@SessionAttribute("accountSession") Account accountSession, @ModelAttribute MaterialType materialType, Model model){
		
		String parent_name = materialType.getParent_name();
		//設定上一頁資訊
		model.addAttribute("parent_parent_id", materialType.getParent_id());
		if("".equals(materialType.getParent_name())) {
			model.addAttribute("parent_parent_name", "素材分類管理");
		} else {
			model.addAttribute("parent_parent_name", materialType.getParent_name());
		}
		model.addAttribute("parent_name", materialType.getParent_name());
		
		//取得素材分類資料
		materialType = materialTypeService.data(materialType);
		materialType.setParent_name(parent_name);
		model.addAttribute("materialType", materialType);
		
		//選單
		List<Map<String, Object>> menu = functionController.menu(accountSession, menuName);
		model.addAttribute("menu", menu);
		
		return "admin/materialType/form";
    }
	
	@RequestMapping(value = "/updateSubmit" , method = {RequestMethod.POST, RequestMethod.GET})
    public String updateSubmit(@SessionAttribute("accountSession") Account accountSession, @ModelAttribute MaterialType materialType, Model model){
		
		materialType.setUpdate_by(accountSession.getAccount());
		materialTypeService.update(materialType);
		
		//取得上一頁資訊
		MaterialType parent = new MaterialType();
		parent.setId(materialType.getParent_id());
		Map<String, Object> backData = materialTypeService.backData(parent);
		
		if(backData != null) {
			model.addAttribute("parent_parent_id", backData.get("PARENT_ID").toString());
			String parent_name = backData.get("PARENT_NAME")!=null ? backData.get("PARENT_NAME").toString() : "素材分類管理";
			model.addAttribute("parent_parent_name", parent_name);
		}
		
		model.addAttribute("id", materialType.getId());
		model.addAttribute("parent_id", materialType.getParent_id());
		model.addAttribute("parent_name", materialType.getParent_name());
		model.addAttribute("layer", materialType.getLayer());
		model.addAttribute("PATH", "list");
		return "admin/path";
    }
	
	@RequestMapping(value = "/delete" , method = {RequestMethod.POST})
	public String delete(@SessionAttribute("accountSession") Account accountSession, @ModelAttribute MaterialType materialType, Model model) {
		
		String parent_name = materialType.getParent_name();
		
		materialType = materialTypeService.data(materialType);
		
		materialTypeService.delete(materialType);
		materialTypeService.updateSortByDelete(materialType);
		
		if("".equals(parent_name)) {
			model.addAttribute("parent_parent_name", "素材分類管理");
		} else {
			model.addAttribute("parent_parent_name", materialType.getParent_name());
		}
		
		model.addAttribute("id", materialType.getId());
		model.addAttribute("parent_id", materialType.getParent_id());
		model.addAttribute("parent_name", parent_name);
		model.addAttribute("layer", materialType.getLayer());
		model.addAttribute("PATH", "list");
		return "admin/path";
	}
	
	//藉由學制取得年級
  	@RequestMapping(value = "/get/child" , method = {RequestMethod.GET, RequestMethod.POST})
  	public void child(HttpServletRequest pRequest, HttpServletResponse pResponse, Model model) throws Exception {
  		
  		String id = pRequest.getParameter("id")!=null ? pRequest.getParameter("id") : "";
  		
  		MaterialType materialType = new MaterialType();
  		materialType.setParent_id(id);
  		List<Map<String, Object>> list = materialTypeService.getChild(materialType);
  		
  		JSONArray tJSONArray = new JSONArray(list);
		pResponse.setCharacterEncoding("utf-8");
		PrintWriter out = pResponse.getWriter();
		out.write(tJSONArray.toString());
  		
  	}
  	
}
