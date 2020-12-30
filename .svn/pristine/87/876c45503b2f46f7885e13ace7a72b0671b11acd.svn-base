package com.chunqiu.mrjuly.common.utils;

import com.chunqiu.mrjuly.modules.system.model.User;
import org.apache.shiro.SecurityUtils;

public class UserUtils {

    public static final String USER_CACHE = "CHUNQIU_CACHE:";
    public static final String USER_SESSION = "CHUNQIU_SESSION:";

    public static final String USER_ROLE_MENULIST = "CHUNQIU_MENU:";

    /**
     * 获取当前用户信息
     * @return
     */
    public static User getUser(){
        try {
            return (User) SecurityUtils.getSubject().getPrincipal();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }


}
