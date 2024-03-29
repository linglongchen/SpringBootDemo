package com.chunqiu.mrjuly.modules.system.service;

import javax.annotation.Resource;

import com.chunqiu.mrjuly.common.utils.AddressUtils;
import com.chunqiu.mrjuly.common.utils.IDUtil;
import com.chunqiu.mrjuly.common.annotation.Log;
import com.chunqiu.mrjuly.common.persistence.CrudService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.stereotype.Service;
import com.chunqiu.mrjuly.modules.system.model.AdminLog;
import com.chunqiu.mrjuly.modules.system.dao.AdminLogDao;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.*;

/**
 * systemService
 * @author qj
 * @version 2019-01-14
 */
@Service
public class AdminLogService extends CrudService<AdminLogDao, AdminLog, Long> {
	@Resource
	private AdminLogDao dao;
	@Autowired
	ObjectMapper objectMapper;

	/**
	 * 保存数据（插入或更新）
	 * @param entity
	 */
	public void save(AdminLog entity) {
		if (entity.getIsNewRecord()){
			entity.setId(IDUtil.nextId());

			entity.preInsert();
			dao.insert(entity);
		}else{
			entity.preUpdate();
			dao.update(entity);
		}
	}

	public void saveLog(ProceedingJoinPoint joinPoint, AdminLog adminLog) throws JsonProcessingException {

		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();
		Log logAnnotation = method.getAnnotation(Log.class);
		if (logAnnotation != null) {
			// 注解上的描述
			adminLog.setOperation(logAnnotation.value());
		}
		// 请求的类名
		String className = joinPoint.getTarget().getClass().getName();
		// 请求的方法名
		String methodName = signature.getName();
		adminLog.setMethod(className + "." + methodName + "()");
		// 请求的方法参数值
		Object[] args = joinPoint.getArgs();
		// 请求的方法参数名称
		LocalVariableTableParameterNameDiscoverer u = new LocalVariableTableParameterNameDiscoverer();
		String[] paramNames = u.getParameterNames(method);
		if (args != null && paramNames != null) {
			StringBuilder params = new StringBuilder();
			params = handleParams(params, args, Arrays.asList(paramNames));
			adminLog.setParams(params.toString());
		}
		adminLog.setCreateTime(new Date());
		adminLog.setLocation(AddressUtils.getCityInfo(adminLog.getIp()));
		// 保存系统日志
		save(adminLog);
	}

	/**
	 * 获取请求参数
	 * @param params
	 * @param args
	 * @param paramNames
	 * @return
	 * @throws JsonProcessingException
	 */
	private StringBuilder handleParams(StringBuilder params, Object[] args, List paramNames) throws JsonProcessingException {
		for (int i = 0; i < args.length; i++) {
			if (args[i] instanceof Map) {
				Set set = ((Map) args[i]).keySet();
				List list = new ArrayList();
				List paramList = new ArrayList<>();
				for (Object key : set) {
					list.add(((Map) args[i]).get(key));
					paramList.add(key);
				}
				return handleParams(params, list.toArray(), paramList);
			} else {
				if (args[i] instanceof Serializable) {
					Class<?> aClass = args[i].getClass();
					try {
						aClass.getDeclaredMethod("toString", new Class[]{null});
						// 如果不抛出NoSuchMethodException 异常则存在 toString 方法 ，安全的writeValueAsString ，否则 走 Object的 toString方法
						params.append("  ").append(paramNames.get(i)).append(": ").append(objectMapper.writeValueAsString(args[i]));
					} catch (NoSuchMethodException e) {
						params.append("  ").append(paramNames.get(i)).append(": ").append(objectMapper.writeValueAsString(args[i].toString()));
					}
				} else if (args[i] instanceof MultipartFile) {
					MultipartFile file = (MultipartFile) args[i];
					params.append("  ").append(paramNames.get(i)).append(": ").append(file.getName());
				} else {
					params.append("  ").append(paramNames.get(i)).append(": ").append(args[i]);
				}
			}
		}
		return params;
	}
}