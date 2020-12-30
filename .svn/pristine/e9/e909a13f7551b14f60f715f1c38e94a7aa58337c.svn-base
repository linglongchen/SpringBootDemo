package com.chunqiu.mrjuly.common.persistence;

import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.ModelMap;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.annotation.Resource;
import javax.validation.Validator;
import java.util.Map;

public abstract class BaseController {

    protected static final String successPath = "success";
    protected static final String error = "error";
    protected static final String error1 = "error1";

    protected static final String successPath1 = "success1";
    protected static final String successPath3 = "success3";

    protected static final String successPath2 = "success2";
    protected static final String successPath4 = "success4";
    protected static final String successPath5 = "success5";
    protected static final String successPath6 = "success6";
    protected static final String successPath7 = "success7";
    protected static final String successPath8 = "success8";

    protected static final String successHouse = "successHouse";
    /**
     * 管理基础路径
     */
    @Value("${adminPath}")
    protected String adminPath;
    /**
     * 验证Bean实例对象
     */
    @Autowired
    protected Validator validator;

    /**
     * 为Thymeleaf添加全局参数
     * @param viewResolver
     */
    @Resource
    private void configureThymeleafStaticVars(ThymeleafViewResolver viewResolver) {
        if(viewResolver != null) {
            Map<String, Object> vars = Maps.newHashMap();
            vars.put("ctx", "/admin");
            viewResolver.setStaticVariables(vars);
        }
    }


    /**
     * 添加Model消息
     * @param messages
     */
    protected void addMessage(ModelMap model, String... messages) {
        StringBuilder sb = new StringBuilder();
        for (String message : messages){
            sb.append(message).append(messages.length>1?"<br/>":"");
        }
        model.addAttribute("message", sb.toString());
    }
}
