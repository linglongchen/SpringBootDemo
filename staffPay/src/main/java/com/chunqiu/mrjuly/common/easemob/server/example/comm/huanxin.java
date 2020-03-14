package com.chunqiu.mrjuly.common.easemob.server.example.comm;

import com.chunqiu.mrjuly.common.vo.Json;
import com.chunqiu.mrjuly.common.easemob.server.example.api.impl.EasemobIMUsers;
import io.swagger.client.model.RegisterUsers;

import java.util.ArrayList;
import java.util.List;



public class huanxin {

    private final static EasemobIMUsers easemobIMUsers = new EasemobIMUsers();

    public static void main(String[] args){
        //添加IM人员
        List<RegUser> regUserList = new ArrayList<>();
        RegUser regUser = new RegUser();
        regUser.setUsername("ceshishebeibianma1");
        regUser.setPassword("123456");
        RegUser regUser1 = new RegUser();
        regUser1.setUsername("ceshishebeibianma2");
        regUser1.setPassword("123456");
        regUserList.add(regUser1);
        reg(regUserList);


    }


    public static void reg(List<RegUser> regUser){
        RegisterUsers registerUsers = new RegisterUsers();
        for (int i = 0;i<regUser.size();i++){
            io.swagger.client.model.User p = new io.swagger.client.model.User().username(regUser.get(i).getUsername()).password(regUser.get(i).getPassword());
            registerUsers.add(p);
        }
        Object result = easemobIMUsers.createNewIMUserBatch(registerUsers);
        if (result == null){
            result = "success";
        }
        Json json = new Json();
        json.setSuccess(true);
        json.setMsg(result.toString());
    }


    public static void reg2(RegUser regUser){
            RegisterUsers registerUsers = new RegisterUsers();
            io.swagger.client.model.User p = new io.swagger.client.model.User().username(regUser.getUsername()).password(regUser.getPassword());
            registerUsers.add(p);
        Object result = easemobIMUsers.createNewIMUserSingle(registerUsers);
        Json json = new Json();
        json.setSuccess(true);
        json.setMsg(result.toString());
    }
}
