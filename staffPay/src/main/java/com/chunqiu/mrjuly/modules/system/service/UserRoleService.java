package com.chunqiu.mrjuly.modules.system.service;

import com.chunqiu.mrjuly.common.persistence.CrudService;
import com.chunqiu.mrjuly.modules.system.dao.UserRoleDao;
import com.chunqiu.mrjuly.modules.system.model.UserRole;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 用户角色信息Service
 * @author wcf
 * @version 2018-11-14
 */
@Service
public class UserRoleService extends CrudService<UserRoleDao, UserRole, Long> {
	@Resource
	private UserRoleDao dao;

}