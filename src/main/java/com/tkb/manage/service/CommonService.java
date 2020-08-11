package com.tkb.manage.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface CommonService {
	
	//歷程記錄
//	public void history(String id, String table, String action, ManagerAccount managerAccountSession, String jsonString);
	//檔案上傳，來源絕對路徑
	public String[] uploadFileAbsolutePath(String localPath, String uploadedFolder) throws IOException;
	//檔案上傳，儲存日期檔名
	public String uploadFileSaveDateName(MultipartFile files,String uploadedFolder) throws IOException;
	//Base64檔案上傳，儲存日期檔名
	public String uploadBase64(String image,String uploadedFolder) throws IOException;
	//檔案移除
	public void deleteFile(String fileName,String uploadedFolder) throws IOException;
	//檢查圖片寬度
	public boolean checkImageWidth(MultipartFile filePath,int maxWidth) throws IOException;
	//檢查圖片高度
	public boolean checkImageHeight(MultipartFile filePath,int maxHeight) throws IOException;
	//寄送信件
	public void sendEmail(String email, String title, String content) throws IOException;
	//加密
	public String encrypt(String sSrc, String sKey) throws Exception;
	//解密
	public String decrypt(String sSrc, String sKey) throws Exception;
	
}
