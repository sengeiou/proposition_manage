package com.tkb.manage.filter;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.util.StringUtils;

import com.tkb.manage.model.Account;
import com.tkb.manage.util.exception.AuthorizationException;

public class LoginFilter implements Filter {
	
//	private static String publicKey = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAIEEe1hcb4ynhUfSa+YwNiVJZNbUpsRD4MYGjxAcZLzE2AS0qxA+F8OXytUlzjKg/UweLmLJx0ZPuQNDBDPjpEUCAwEAAQ==";
	private static String privateKey = "MIIBVgIBADANBgkqhkiG9w0BAQEFAASCAUAwggE8AgEAAkEAgQR7WFxvjKeFR9Jr5jA2JUlk1tSmxEPgxgaPEBxkvMTYBLSrED4Xw5fK1SXOMqD9TB4uYsnHRk+5A0MEM+OkRQIDAQABAkAUyGEKKdG6SZ35pNbGvMtsFDePN7Ape3mwzIutH7XGhn5QPpFQBV0lVoPDixaDzk/xgwimJsZ9Wzjf6CAF+5iBAiEA7YeBA4fHWS7iNLSFFzwD2I6RY7vpSZB6xszthWS7k6ECIQCLDNVV2Nuwt69gSuXfURebzw77dhO23NsfxgkQLmSOJQIhALHUNeGXwMZjFFWTJOUNOG/j5LN/VpuNwG2ftN+eV9ShAiEAgJv2FdjmGaVLiHy98LdIZsw0x1CHG3NbGSBJ8bV3PCECIQDcE0iNOgIp2O0XAwsJ/EI4mfLfGF+DmtTIbDDR//ihlw==";
	private static final Logger logger = LoggerFactory.getLogger(LoginFilter.class);
	
	@Override
	public void destroy() {
	}
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain)
			throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		response.setHeader(HttpHeaders.CACHE_CONTROL, CacheControl.noCache().cachePrivate().mustRevalidate().getHeaderValue());
		
//		Gson gson = new Gson();
//		System.out.println(request.getRemoteAddr());
//		System.out.println(request.getMethod());
//		System.out.println(request.getRequestURI());
//		System.out.println(gson.toJson(request.getParameterMap()));
		
		Account accountSession = (Account)request.getSession().getAttribute("accountSession");
		
		//API
		if(request.getServletPath().indexOf("swagger") >= 0 || request.getServletPath().indexOf("v2") >= 0) {
			request.getRequestDispatcher(((HttpServletRequest) request).getServletPath()).forward(request, response);
		} else if (request.getServletPath().indexOf("/api") >= 0) {
			
			final String API_KEY = "t1k2b3-tkbManageApi";

			String encryptedString = getJwtFromRequest((HttpServletRequest) req);
//			 System.out.println(publicKey1);
			try {
//				 RSAKeyPairGenerator rSAKeyPairGenerator = new
//				 RSAKeyPairGenerator();
//				 System.out.println("公鑰："+Base64.getEncoder().encodeToString(rSAKeyPairGenerator.getPublicKey().getEncoded()));
//				 System.out.println("私鑰："+Base64.getEncoder().encodeToString(rSAKeyPairGenerator.getPrivateKey().getEncoded()));
				// 使用從header讀取來的token做加密字串的動作
//				 String a = Base64.getEncoder().encodeToString(encrypt(API_KEY, publicKey));
//				 System.out.println("a:"+a);
				//產生金鑰
//				 String str = Base64.getEncoder().encodeToString(encrypt(API_KEY, publicKey));
//				 System.out.println("str:"+str);

				// 再用私鑰做解密的動作
				String decryptedString = decrypt(encryptedString, privateKey);
//				String decryptedString = API_KEY; // 測試用
//				System.out.println("descypt:" + decryptedString);

				 if(!API_KEY.equals(decryptedString)){
					 throw new AuthorizationException("驗證失敗");
				 }
			} catch (Exception ex) {
				logger.error("請確認參數是否有值", ex);
				throw new AuthorizationException("驗證失敗");
			}

			filterChain.doFilter(req, res);
			
		//已登入
		} else if (accountSession != null) {
			//如果已登入卻輸入此網址 則導向首頁
			if(request.getServletPath().equals("/tkbrule/login") || request.getServletPath().equals("/tkbrule/") || request.getServletPath().equals("/tkbrule")) {
				response.sendRedirect(request.getContextPath() + "/tkbrule/index");
			} else if(request.getServletPath().equals("/manager/login") || request.getServletPath().equals("/teacher/login") || request.getServletPath().equals("/") || request.getServletPath().equals("")) {
				response.sendRedirect(request.getContextPath() + "/index");
			} else {
				filterChain.doFilter(request, response);
			}
		//未登入
		} else {
//			//請求至登入頁或執行登入動作 則導至請求的目的端
			if(request.getServletPath().equals("/tkbrule/login") || request.getServletPath().equals("/tkbrule/manager/doLogin") || request.getServletPath().equals("/tkbrule/teacher/doLogin") || request.getServletPath().equals("/tkbrule/logout")) {
				filterChain.doFilter(request, response);
			} else if(request.getServletPath().equals("/manager/login") || request.getServletPath().equals("/teacher/login") || request.getServletPath().equals("/teacher/register") || request.getServletPath().equals("/teacher/registerSubmit") || request.getServletPath().equals("/teacher/check") || request.getServletPath().equals("/teacher/send/verify") || request.getServletPath().equals("/manager/doLogin") || request.getServletPath().equals("/teacher/doLogin") || request.getServletPath().equals("/logout")) {
				filterChain.doFilter(request, response);
//			//請求至非登入頁 或執行非登入動作 導至登入頁	
			} else {
				//自動導至網站首頁
//				response.sendRedirect("/");
				if(request.getServletPath().indexOf("/tkbrule") >= 0) {
					request.getRequestDispatcher("/tkbrule/login").forward(request, response);
				} else {
					if(request.getServletPath().indexOf("/admin") >= 0) {
						filterChain.doFilter(request, response);
					} else if(request.getServletPath().indexOf("/front") >= 0) {
						filterChain.doFilter(request, response);
					} else {
						request.getRequestDispatcher("/").forward(request, response);
					}
				}
			}
		}
	}
	
	private String getJwtFromRequest(HttpServletRequest request) {
		
		String bearerToken = request.getHeader("Authorization");
//		System.out.println("Authorization:"+bearerToken);
//		System.out.println(StringUtils.hasText(bearerToken));
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
//			System.out.println(bearerToken.substring(7, bearerToken.length()));
			return bearerToken.substring(7, bearerToken.length());
		}
		return null;
	}

	public static PublicKey getPublicKey(String base64PublicKey) {
		PublicKey publicKey = null;
		try {
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(base64PublicKey.getBytes()));
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			publicKey = keyFactory.generatePublic(keySpec);
			return publicKey;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		}
		return publicKey;
	}

	public static PrivateKey getPrivateKey(String base64PrivateKey) {
		PrivateKey privateKey = null;
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(base64PrivateKey.getBytes()));
		KeyFactory keyFactory = null;
		try {
			keyFactory = KeyFactory.getInstance("RSA");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		try {
			privateKey = keyFactory.generatePrivate(keySpec);
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		}
		return privateKey;
	}

	public static byte[] encrypt(String data, String publicKey) throws BadPaddingException, IllegalBlockSizeException,
			InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException {
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		cipher.init(Cipher.ENCRYPT_MODE, getPublicKey(publicKey));
		return cipher.doFinal(data.getBytes());
	}

	public static String decrypt(byte[] data, PrivateKey privateKey) throws NoSuchPaddingException,
			NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		return new String(cipher.doFinal(data));
	}

	public static String decrypt(String data, String base64PrivateKey) throws IllegalBlockSizeException,
			InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
		return decrypt(Base64.getDecoder().decode(data.getBytes()), getPrivateKey(base64PrivateKey));
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}
	
}
