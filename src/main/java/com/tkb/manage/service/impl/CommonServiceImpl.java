package com.tkb.manage.service.impl;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Properties;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.tkb.manage.service.CommonService;

@Service
public class CommonServiceImpl implements CommonService {
	
//	@Autowired
//	private HistoryService historyService;
	
//	public void history(String id, String table, String action, ManagerAccount managerAccountSession, String jsonString) {
//		
//		History history = new History();
//		history.setFk_uuid(id);
//		history.setTable_name(table);
//		history.setAction(action);
//		history.setIp(managerAccountSession.getIp());
//		history.setContent(jsonString);
//		history.setCreate_by(managerAccountSession.getAccount());
//		historyService.add(history);
//		
//	}
	
	public String[] uploadFileAbsolutePath(String localPath, String uploadedFolder) throws IOException {
		
		File local = new File(localPath);
    	
//    	System.out.println(i+":"+local.getName());
    	
    	String path = uploadedFolder;
    	
    	FileInputStream fin = new FileInputStream(localPath);
    	
    	byte[] buffer = new byte[1024];
        int len = 0;
        File file = new File(path);
        
        if(!file.exists()) {
			file.mkdirs();
		}
        
        Random ran = new Random();
		String num = String.format("%04d", (ran.nextInt(9999)+1));
		
		String[] fileName = new String[2];
		
		//以"年月日時分秒ID副檔名"來重新命名檔案
		fileName[0] = new SimpleDateFormat("yyyyMMddHHmmssSS").format(new java.util.Date()) + num + "." + local.getName().substring(local.getName().lastIndexOf(".")+1, local.getName().length());
		fileName[1] = local.getName();
		
        OutputStream out = new FileOutputStream(path+fileName[0]);
    	while ((len = fin.read(buffer)) != -1) {
            out.write(buffer, 0, len);
        }
    	out.close();
        fin.close();
        
        return fileName;
	}
	
	public String uploadFileSaveDateName(MultipartFile files, String uploadedFolder) throws IOException {
		
		File file = new File(uploadedFolder);
		if(!file.exists()) {
			file.mkdirs();
		}
		
		byte[] bytes = files.getBytes();
		
		String fileName = "";
		
		Random ran = new Random();
		String num = String.format("%04d", (ran.nextInt(9999)+1));
		
		//若上傳檔案為空則回傳原本檔名
//		if (files == null || "".equals(files)) {
//			return files.getOriginalFilename();
//		}
		//以"年月日時分秒ID副檔名"來重新命名檔案
		fileName = new SimpleDateFormat("yyyyMMddHHmmssSS").format(new java.util.Date()) + num + "." + files.getOriginalFilename().substring(files.getOriginalFilename().lastIndexOf(".")+1, files.getOriginalFilename().length());
		
		try {			
			Path path = Paths.get(uploadedFolder+fileName);
			Files.write(path, bytes);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fileName;
		
	}
	
	public String uploadBase64(String image, String uploadedFolder) throws IOException {
		
		byte[] base64 = Base64.getDecoder().decode(image);
		Random ran = new Random();
		String num = String.format("%04d", (ran.nextInt(9999)+1));
		
		//以"年月日時分秒ID副檔名"來重新命名檔案
		String fileName = new SimpleDateFormat("yyyyMMddHHmmssSS").format(new java.util.Date()) + num + ".jpeg";
		File file = new File(uploadedFolder);
		if(!file.exists()) {
			file.mkdirs();
		}
		file = new File(uploadedFolder+fileName);
		
		OutputStream outputStream = new FileOutputStream(file);
		outputStream.write(base64);
		outputStream.flush();
		outputStream.close();
		
		return fileName;
		
	}
	
	public void deleteFile(String fileName, String uploadedFolder) throws IOException {
		
		try {
			Path path = Paths.get(uploadedFolder + fileName);
			if(Files.exists(path)){
				Files.delete(path);
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		
	}
	
	public boolean checkImageWidth(MultipartFile filePath, int maxWidth) throws IOException {
		
		BufferedImage buff = ImageIO.read(filePath.getInputStream());
		
		if(buff.getWidth() == maxWidth) {
			return true;
		} else {
			return false;
		}
		
	}
   
   public boolean checkImageHeight(MultipartFile filePath,int maxHeight) throws IOException {
		
	   BufferedImage buff = ImageIO.read(filePath.getInputStream());
		
		if(buff.getHeight() == maxHeight) {
			return true;
		} else {
			return false;
		}
		
	}
   
	//寄送信件
	public void sendEmail(String email, String title, String content) throws IOException {
		try {
			Properties properties = System.getProperties();// 獲取系統屬性
		 	
		 	properties.setProperty("mail.transport.protocol", "smtp");
	        properties.setProperty("mail.smtp.host", "mail.tkbnews.com.tw");// 設置郵件服務器
	        properties.setProperty("mail.smtp.auth", "true");// 打開認證
//	        properties.setProperty("mail.user", "ifUserNameNeeded");
//	        properties.setProperty("mail.password", "ifPasswordNeeded");
	        
	        Session mailSession = Session.getDefaultInstance(properties, null);
//           Transport transport = mailSession.getTransport();
            InternetAddress from = new InternetAddress("service@tkbnews.com.tw");
            // 產生整封 email 的主體 message
            MimeMessage msg = new MimeMessage(mailSession);
           
            msg.setSubject(title);
            msg.setFrom(from);
   		    msg.setRecipients(Message.RecipientType.TO, email);
   		    msg.setText(content);
//           //QQ郵箱需要下面這段代碼，163郵箱不需要
//           MailSSLSocketFactory sf = new MailSSLSocketFactory();
//           sf.setTrustAllHosts(true);
//           properties.put("mail.smtp.ssl.enable", "true");
//           properties.put("mail.smtp.ssl.socketFactory", sf);

       	    Transport transport = mailSession.getTransport("smtp");
       	    transport.connect("mail.tkbnews.com.tw", "byone@tkbnews.com.tw", "j;6m06vu06");
       	    transport.sendMessage(msg, msg.getRecipients(Message.RecipientType.TO));
   		    transport.close();

       } catch (Exception e) {
           e.printStackTrace();
       }
	}

}
