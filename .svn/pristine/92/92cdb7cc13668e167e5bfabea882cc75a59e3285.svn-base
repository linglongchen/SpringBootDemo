package com.chunqiu.mrjuly.common.aspect;


import com.chunqiu.mrjuly.common.config.JulyProperties;
import com.chunqiu.mrjuly.common.utils.HttpContextUtils;
import com.chunqiu.mrjuly.common.utils.IPUtils;
import com.chunqiu.mrjuly.modules.system.model.AdminLog;
import com.chunqiu.mrjuly.modules.system.model.User;
import com.chunqiu.mrjuly.modules.system.service.AdminLogService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * AOP 记录用户操作日志
 * 系统日志：切面处理类
 * @author qj
 */
@Aspect
@Component
public class LogAspect {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JulyProperties julyProperties;

    @Autowired
    private AdminLogService adminLogService;

     //定义切点 @Pointcut
    //在注解的位置切入代码
    @Pointcut("@annotation(com.chunqiu.mrjuly.common.annotation.Log)")
    public void pointcut() {
        // do nothing
    }

    //切面 配置通知
    @Around("pointcut()")
    public Object around(ProceedingJoinPoint point) throws JsonProcessingException {
        Object result = null;
        long beginTime = System.currentTimeMillis();
        try {
            // 执行方法
            result = point.proceed();
        } catch (Throwable e) {
            log.error(e.getMessage());
        }
        // 获取request
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        // 设置IP地址
        String ip = IPUtils.getIpAddr(request);
        // 执行时长(毫秒)
        long time = System.currentTimeMillis() - beginTime;
        if (julyProperties.isOpenAopLog()) {
            // 保存日志
            User user = (User) SecurityUtils.getSubject().getPrincipal();
            AdminLog log = new AdminLog();
            if (user != null ){
                log.setUsername(user.getName());
                log.setIp(ip);
                log.setTime(time);
                adminLogService.saveLog(point, log);
            }

        }
        return result;
    }
}
