package com.tkb.manage.util.aop.aspect;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.google.gson.Gson;
import com.tkb.manage.model.Account;
import com.tkb.manage.model.History;
import com.tkb.manage.service.HistoryService;
import com.tkb.manage.util.aop.annotation.AuditAction;
import com.tkb.manage.util.aop.common.JacksonSerializer;
import com.tkb.manage.util.aop.common.enums.Action;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
public class SystemAudioAspect {
	
	@Autowired
    private HistoryService historyService;
	
	/**
     * 攔截ADD操作，紀錄新增的數據
     */
    @After(value = "execution(public * com.tkb.manage.controller..*.*addSubmit(..))")
    public void doAfter(JoinPoint joinPoint) {
    	//獲取該方法上的 @AuditAction注解
        AuditAction audioAction = this.getAudioActionByJoinPoint(joinPoint);
        if (audioAction != null && audioAction.action() == Action.ADD) {
        	//撈取第一組變數，作為儲存資料
            Object obj = joinPoint.getArgs()[0];
            this.addAudioLog(obj, History.add, audioAction.targetTable());
        }
    }
    
    /**
     * 攔截DELETE操作，紀錄被删除的數據
     * @param joinPoint
     */
    @Before(value = "execution(public * com.tkb.manage.controller..*.*delete(..))")
    public void doBefore(JoinPoint joinPoint) {
        //獲取方法中的参数
        Object[] args = joinPoint.getArgs();
        //獲取該方法上的 @AuditAction注解
        AuditAction audioAction = this.getAudioActionByJoinPoint(joinPoint);
        if (audioAction != null && audioAction.action() == Action.DELETE) {
            Object obj = null;
            String targetTable = audioAction.targetTable();
            Gson gson = new Gson();
            String id = gson.fromJson(gson.toJson(args), Map.class).get("id").toString();
            obj = historyService.getData("proposition_manage", targetTable, id);
            if (obj != null) {
                this.addAudioLog(obj, History.delete, targetTable);
            }
        }
    }
    
    /**
     * 攔截UPDATE操作，紀錄更新前後的數據
     */
    @Around(value = "execution(public * com.tkb.manage.controller..*.*editSubmit(..))")
    public Object edit(ProceedingJoinPoint pjp) throws Throwable {
    	return doAround(pjp);
    }
    
    @Around(value = "execution(public * com.tkb.manage.controller..*.*updateSubmit(..))")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        AuditAction audioAction = this.getAudioActionByJoinPoint(pjp);
        Object proceed = null;
        if (audioAction != null && audioAction.action() == Action.UPDATE) {
            Object originalObj = null;
            Object arg = pjp.getArgs()[0];
            String targetTable = audioAction.targetTable();
            Gson gson = new Gson();
            String id = gson.fromJson(gson.toJson(arg), Map.class).get("id").toString();
            originalObj = historyService.getData("proposition_manage", targetTable, id);
            
            History history = null;
            if (originalObj != null) {
                // TODO 在執行原方法之前，紀錄修改前後數據
            	Map<String, Object> map = new HashMap<String, Object>();
            	map.put("before", originalObj);
            	map.put("after", arg);
            	history = this.addAudioLog(map, History.update, targetTable);
            }
            //執行原方法
            proceed = pjp.proceed();
            // TODO 在執行原方法之後，紀錄新數據
//            System.out.println("before:"+originalObj);
//        	System.out.println("after:"+arg);
//        	List<Map<String ,Object>> changelist=compareTwoClass(originalObj,arg);
//        	for(Map<String,Object> map:changelist){
//        		System.out.println("【"+map.get("name")+"】从【"+map.get("old")+"】改为了【"+map.get("new")+"】;\n");
//            }
//            if (history != null) {
//                this.addAudioLog(arg, History.update, targetTable);
//            }
        }
        if (proceed == null) {
            return pjp.proceed();
        }
        return proceed;
    }
    
    private History addAudioLog(Object obj, String type, String tableName) {
    	
    	HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String url = request.getRequestURL().toString();
        String jsonString = JacksonSerializer.toJSONString(obj);
        String ip = getRemoteHost(request);
        
        HttpSession session = request.getSession();
        Account account = (Account)session.getAttribute("accountSession");
        History history = History.builder().url(url).data(jsonString).method(type).table_name(tableName).ip(ip).create_by(account.getAccount()).build();
        historyService.add(history);
        return history;
    }
    
    /**
     * 根據JoinPoint對象獲取目標方法上的注解
     * @param joinPoint
     * @return AuditAction
     */
    private AuditAction getAudioActionByJoinPoint(JoinPoint joinPoint) {
    	
        String methodName = joinPoint.getSignature().getName();
        Method[] methods = joinPoint.getSignature().getDeclaringType().getMethods();
        System.out.println("methodName:"+methodName);
        Method tempMethod = Arrays.stream(methods)
                .filter(m -> m.getName().equals(methodName))
                .filter(m -> Objects.equals(m.getParameterTypes().length, joinPoint.getArgs().length))
                .findFirst()
                .orElse(null);
        System.out.println("name:"+tempMethod.getName());
        System.out.println("tempMethod:"+tempMethod);
        if (tempMethod != null) {
            AuditAction audioAction = (AuditAction) Arrays.stream(tempMethod.getDeclaredAnnotations())
                    .filter(a -> a instanceof AuditAction)
                    .findFirst()
                    .orElse(null);
            System.out.println("audioAction:"+audioAction.action());
            if (audioAction != null ) {
                return audioAction;
            }
        }
        return null;
    }
    
    /**
     * 獲取目標主機的IP
     * @param request
     * @return
     */
	private String getRemoteHost(HttpServletRequest request) {
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
	
	/**
	 * 获取两个对象同名属性内容不相同的列表
	 * @param class1 old对象
	 * @param class2 new对象
	 * @return  区别列表
	 * @throws ClassNotFoundException 异常
	 * @throws IllegalAccessException 异常
	 */
	public static List<Map<String ,Object>> compareTwoClass(Object class1, Object class2) throws ClassNotFoundException, IllegalAccessException {
		List<Map<String,Object>> list=new ArrayList<Map<String, Object>>();
		//获取对象的class
		Class<?> clazz1 = class1.getClass();
		Class<?> clazz2 = class2.getClass();
		//获取对象的属性列表
		Field[] field1 = clazz1.getDeclaredFields();
		Field[] field2 = clazz2.getDeclaredFields();
		StringBuilder sb=new StringBuilder();
		//遍历属性列表field1
		for(int i=0;i<field1.length;i++) {
			//遍历属性列表field2
			for (int j = 0; j < field2.length; j++) {
				//如果field1[i]属性名与field2[j]属性名内容相同
				if (field1[i].getName().equals(field2[j].getName())) {
					if (field1[i].getName().equals(field2[j].getName())) {
						field1[i].setAccessible(true);
						field2[j].setAccessible(true);
						//如果field1[i]属性值与field2[j]属性值内容不相同
						if (!compareTwo(field1[i].get(class1), field2[j].get(class2))) {
							Map<String, Object> map2 = new HashMap<String, Object>();
							AuditAction name=field1[i].getAnnotation(AuditAction.class);
							String fieldName="";
							if(name!=null){
								fieldName=name.name();
							}else {
								fieldName=field1[i].getName();
							}
							map2.put("name", fieldName);
							map2.put("old", field1[i].get(class1));
							map2.put("new", field2[j].get(class2));
							list.add(map2);
						}
						break;
					}
				}
			}
		}
		return list;

	}
	
	/**
	 * 对比两个数据是否内容相同
	 *
	 * @param  object1,object2  比较对象,12
	 * @return boolean类型
	 */
	public static boolean compareTwo(Object object1,Object object2){

		if(object1==null&&object2==null){
			return true;
		}
		if(object1==null&&object2!=null){
			return false;
		}
		if(object1.equals(object2)){
			return true;
		}
		return false;
	}
	
}
