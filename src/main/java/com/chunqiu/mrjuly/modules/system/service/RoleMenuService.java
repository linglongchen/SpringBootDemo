package com.chunqiu.mrjuly.modules.system.service;

import com.chunqiu.mrjuly.common.persistence.CrudService;
import com.chunqiu.mrjuly.modules.system.dao.RoleMenuDao;
import com.chunqiu.mrjuly.modules.system.model.RoleMenu;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 角色菜单信息Service
 * @author wcf
 * @version 2018-11-14
 */
@Service
public class RoleMenuService extends CrudService<RoleMenuDao, RoleMenu, Integer> {
	@Resource
	private RoleMenuDao dao;

}