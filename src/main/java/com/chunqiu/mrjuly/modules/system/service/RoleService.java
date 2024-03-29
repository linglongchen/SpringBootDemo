package com.chunqiu.mrjuly.modules.system.service;

import com.chunqiu.mrjuly.common.utils.UserUtils;
import com.chunqiu.mrjuly.modules.system.model.UserRole;
import com.chunqiu.mrjuly.common.persistence.CrudService;
import com.chunqiu.mrjuly.modules.system.dao.RoleDao;
import com.chunqiu.mrjuly.modules.system.dao.UserDao;
import com.chunqiu.mrjuly.modules.system.dao.UserRoleDao;
import com.chunqiu.mrjuly.modules.system.model.Role;
import com.chunqiu.mrjuly.modules.system.model.RoleMenu;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 系统角色Service
 * @author wcf
 * @version 2018-11-14
 */
@Service
public class RoleService extends CrudService<RoleDao, Role, Long> {
	@Resource
	private RoleDao dao;
	@Resource
	private MenuService menuService;
	@Resource
	private UserRoleDao userRoleDao;
	@Resource
	private UserDao userDao;

	public List<Role> findByUserid(Long userId){
		return dao.findByUserId(userId);
	}

	/**
	 * 编辑角色菜单
	 * @param roleId
	 * @param addMenus
	 * @param delMenus
	 */
	public void editRoleMenu(Long roleId, String addMenus, String delMenus){
		if(StringUtils.isNotBlank(delMenus)){
			dao.delRoleMenu(roleId, delMenus);
		}
		if(StringUtils.isNotBlank(addMenus)){
			List<RoleMenu> roleMenus = new ArrayList<RoleMenu>();
			for(String addMenu : addMenus.split(",")){
				if(StringUtils.isNotBlank(addMenu)){
					RoleMenu roleMenu = new RoleMenu();
					roleMenu.setRoleId(roleId);
					roleMenu.setMenuId(Integer.parseInt(addMenu));
					roleMenus.add(roleMenu);
				}
			}
			dao.addRoleMenu(roleMenus);
		}
		// 更新菜单缓存
		menuService.updateUserMenuTree(roleId);
	}

	/**
	 * 新增更新角色
	 * @param role
	 */
	@Override
	public void save(Role role){
		/*if(dao.checkRoleName(role) > 0){
			throw new ServiceException("该角色已存在");
		}*/


		//获取当前登录角色设置查询的父级ID
		Long parentId = UserUtils.getUser().getRole().getId();
		Role parentRole=dao.get(parentId);
		role.setParentId(parentId);
		if(role.getId() == null){
		 dao.insert(role);
			//初始化菜单
			if (role.getPlatformType()!= null){
				insetRoleList(role);
			}
			//更新角色父级ids
			role.setParentIds(parentId+","+role.getId());
			role.setParentIdss(parentRole.getParentIdss()+","+role.getId());
			dao.update(role);
		}else {
			//更新角色父级ids
			role.setParentIds(parentId+","+role.getId());
			role.setParentIds(parentRole.getParentIdss()+","+role.getId());
			dao.update(role);
		}
	}

	public void insetRoleList(Role role) {
		List<Integer> roleList = new ArrayList<>();
		if (role.getPlatformType() == 1){
			//总控存储菜单id
			Collections.addAll(roleList,	1,2,7,8,9,20,21,22,289,290,292,293,296,297,298,299,301,302,303,304,306,307,308,312,313,314,315,
					335,336,337,338,342,371,372,373,374,387,388,389,392,398,408,409,434,435,437,441,442,443,444,447,
					448,450,451,452,453,456,457,460,464,465,466,467,470,475);
		}else if (role.getPlatformType() == 2){
			//本地存储菜单id
			Collections.addAll(roleList,	1,2,7,8,9,20,21,22,340,341,347,348,349,350,351,352,353,354,355,356,
					357,358,359,360,361,362,363,364,365,367,368,375,376,377,449,468,469,472,473,474);
		}else if (role.getPlatformType() == 3){
			//商家存储菜单id
			Collections.addAll(roleList,1,2,7,8,9,20,21,22,320,321,334,339,343,378,
					380,381,382,383,384,385,386,391,393,399,404,412,413,414,417,422,423,
					424,430,458,476);
		}else if (role.getPlatformType() == 4){
			//广告商存储菜单id
			Collections.addAll(roleList,1,2,7,8,9,20,21,22,344,345,346,394,395,396,
					397,400,401,402,403,407,415,416,418,420,421,425,426,428,429,432,433,438,
					439,440,454);
		}


			for (int i = 0;i<roleList.size();i++){
				RoleMenu roleMenu = new RoleMenu();
				roleMenu.setRoleId(role.getId());
				roleMenu.setMenuId(roleList.get(i));
				dao.insertRoleMenuList(roleMenu);
			}

	}

	/**
	 * 删除数据
	 *
	 * @param role
	 */
	public void delete(Role role) {
		//获取此角色下的账号
		UserRole userRole=new UserRole();
		userRole.setRoleId(role.getId());
		List<UserRole> userRoleList=userRoleDao.findList(userRole);
		//获取此角色下所有角色
		List<Role> roleStringList=dao.findRoleByParentIdss(role);
		//获取此角色下所有角色的账号
		List<UserRole> userRoleList1=new ArrayList<>();
		if(CollectionUtils.isNotEmpty(roleStringList)){
			userRoleList1=userRoleDao.findListByRoleId(roleStringList);
		}
		//删除获取此角色下所有角色的账号
		if(CollectionUtils.isNotEmpty(userRoleList1)){
			for(UserRole userRole1:userRoleList1){
				userDao.delete(userRole1.getUid());
				userRoleDao.delete(userRole1.getId());
			}
		}
		//删除角色
		if(CollectionUtils.isNotEmpty(roleStringList)){
			for(Role role2:roleStringList){
				dao.delete(role2);
			}
		}
	}
}
