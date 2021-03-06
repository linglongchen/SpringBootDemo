package com.chunqiu.mrjuly.modules.system.dao;

import com.chunqiu.mrjuly.modules.system.model.Role;
import com.chunqiu.mrjuly.modules.system.model.UserRole;
import com.chunqiu.mrjuly.common.persistence.CrudDao;

import java.util.List;

/**
 * 用户角色信息DAO接口
 * @author wcf
 * @version 2018-11-14
 */
public interface UserRoleDao extends CrudDao<UserRole, Long> {

    /**
     * 根据uid获取信息
     * @param userRole
     * @return
     */
    UserRole getInfoByUid(UserRole userRole);

    List<UserRole> findListByRoleId(List<Role> list);
}
