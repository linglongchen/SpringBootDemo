package com.chunqiu.mrjuly.modules.system.dao;

import com.chunqiu.mrjuly.modules.system.model.User;
import com.chunqiu.mrjuly.modules.system.model.UserRole;
import com.chunqiu.mrjuly.common.persistence.CrudDao;

/**
 * 系统用户信息DAO接口
 * @author wcf
 * @version 2018-11-14
 */
public interface UserDao extends CrudDao<User, Long> {
    /**
     * 通过登录名获取帐号信息
     * @param name
     * @return
     */
    User getByName(String name);


    int insertEstablishRole(UserRole userRole);

    User userInfoById(User user);

    User getByAccount(User user);

    User getByBusinessId(Long id);
}
