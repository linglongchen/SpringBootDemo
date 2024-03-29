package com.chunqiu.mrjuly.modules.system.service;

import com.chunqiu.mrjuly.common.utils.MD5Util;
import com.chunqiu.mrjuly.common.persistence.CrudService;
import com.chunqiu.mrjuly.modules.system.dao.UserDao;
import com.chunqiu.mrjuly.modules.system.dao.UserRoleDao;
import com.chunqiu.mrjuly.modules.system.model.User;
import com.chunqiu.mrjuly.modules.system.model.UserRole;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 系统用户信息Service
 * @author wcf
 * @version 2018-11-14
 */
@Service
public class UserService extends CrudService<UserDao, User, Long> {
	@Resource
	private UserDao dao;
	@Resource
	private UserRoleDao userRoleDao;

	public User getUserByName(String name){
		return dao.getByName(name);
	}


	public void updateUser(User user){
		dao.update(user);
	}

	@Transactional
	public void save(User user){
		if(user.getId() == null){
			//新增
			user.setHeadImg("https://webappcommon.oss-cn-beijing.aliyuncs.com/common%2FdefultAvatar.png");
			user.setPassword(MD5Util.encrypt(user.getPassword()));
			user.setCreateTime(new Date());
			user.setUpdateTime(new Date());
			dao.insert(user);

			UserRole userRole = new UserRole();
			userRole.setUid(user.getId());
			userRole.setRoleId(user.getRoleId());
			userRole.preInsert();
			userRoleDao.insert(userRole);

		}else{
			//更新
			user.preUpdate();
			dao.update(user);
			UserRole userRole = new UserRole();
			userRole.setUid(user.getId());
			userRole = userRoleDao.getInfoByUid(userRole);
			userRole.setRoleId(user.getRoleId());
			userRole.preUpdate();
			userRoleDao.update(userRole);
		}
	}



	public User userList (User user){
		return dao.userInfoById(user);
	}

	public User getByAccount(User user){
		return dao.getByAccount(user);
	}

}
