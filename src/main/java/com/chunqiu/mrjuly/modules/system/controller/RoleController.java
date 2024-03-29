package com.chunqiu.mrjuly.modules.system.controller;

import com.chunqiu.mrjuly.common.utils.UserUtils;
import com.chunqiu.mrjuly.common.vo.Grid;
import com.chunqiu.mrjuly.common.vo.GridParam;
import com.chunqiu.mrjuly.common.vo.Json;
import com.chunqiu.mrjuly.common.vo.ZTree;
import com.chunqiu.mrjuly.common.annotation.Log;
import com.chunqiu.mrjuly.common.exception.ServiceException;
import com.chunqiu.mrjuly.modules.system.service.MenuService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import lombok.extern.slf4j.Slf4j;

import com.chunqiu.mrjuly.common.persistence.BaseController;
import com.chunqiu.mrjuly.modules.system.model.Role;
import com.chunqiu.mrjuly.modules.system.service.RoleService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 系统角色Controller
 * @author wcf
 * @version 2018-11-14
 */
@Slf4j
@Controller
@RequestMapping(value = "${adminPath}/system/role")
public class RoleController extends BaseController {

	@Autowired
	private RoleService roleService;
	@Autowired
	private MenuService menuService;
	
	/**
	 * @Description：系统角色列表页面
	 * @author wcf
	 */
	@Log("登录系统角色页面")
	@RequestMapping("index")
	@RequiresPermissions("system:role:view")
	public String index(){
		return "system/roleList";
	}
	
	/**
	 * @Description：系统角色列表数据
	 * @author wcf
	 */
	@Log("查询系统角色列表")
	@RequestMapping("list")
	@ResponseBody
	@RequiresPermissions("system:role:view")
	public Grid list(Role role, GridParam param) {
		//获取当前登录角色设置查询的父级ID
		Long parentId = UserUtils.getUser().getRole().getId();
		role.setParentIds(parentId.toString());
		role.setId(parentId);
		return roleService.findPage(role, param);
	}
	/**
	 * @Description：系统角色表单页面--新增/编辑
	 * @author wcf
	 */
	@Log("登录系统角色新增/编辑页面")
	@RequestMapping(value = "form")
	@RequiresPermissions("system:role:view")
	public String form(Role role, ModelMap model) {
		if(role.getName() == null && role.getId() != null){
			role = roleService.get(role.getId());
		}
		model.addAttribute("role", role);
		model.addAttribute("user",UserUtils.getUser());
		return "system/roleForm";
	}
	/**
	 * @Description：系统角色新增/编辑 保存方法
	 * @author wcf
	 */
	@Log("保存系统角色")
	@RequestMapping("saveRole")
	@RequiresPermissions("system:role:edit")
	public String saveRole(Role role, ModelMap model){
		try{
			Integer roleType = UserUtils.getUser().getRoleType();
			if (roleType == 1){
				if (role.getPlatformType() == null){
					model.addAttribute("error", "请选择对应平台！");
					return form(role, model);
				}
			}
			roleService.save(role);
		}catch(Exception e){
			if(e instanceof ServiceException){
				model.addAttribute("error", "该角色已存在！");
			}else{
				model.addAttribute("error", "保存失败！");
			}
			log.error("保存失败！ msg={}", e.getMessage(), e);

			return form(role, model);
		}
		return successPath;
	}
	/**
	 * @Description：系统角色数据删除方法
	 * @author wcf
	 */
	@Log("删除系统角色")
	@RequestMapping("delRole")
	@ResponseBody
	@RequiresPermissions("system:role:edit")
	public Json delRole(Role role){
		Json json = new Json();
		try{
			roleService.delete(role);
			json.setSuccess(true);
		}catch(Exception e){
			log.error("删除失败！ msg={}", e.getMessage(), e);

			json.setSuccess(false);
			json.setMsg("删除失败！");
		}
		return json;
	}

	/**
	 * 权限设置页面
	 * @param model
	 * @return
	 */
	@Log("进入权限设置页面")
	@RequestMapping("editPermission")
	@RequiresPermissions("system:role:view")
	public String editPermission(ModelMap model){
		return "system/permissionSelectForm";
	}

	@RequestMapping("rolePermissionList")
	@ResponseBody
	public List<ZTree> rolePermissionList(String roleId){
		return menuService.findMenuByRole(Long.parseLong(roleId));
	}

	/**
	 * @Description: 编辑保存角色菜单关系
	 * @param roleId
	 * @param addMenus
	 * @param delMenus
	 * @return
	 * @author wcf
	 * @date 2016年9月21日
	 */
	@RequestMapping("editRoleMenu")
	@ResponseBody
	@RequiresPermissions("system:role:edit")
	public Json editRoleMenu(String roleId, String addMenus, String delMenus){
		Json json = new Json();
		try{
			roleService.editRoleMenu(Long.parseLong(roleId), addMenus, delMenus);
			json.setSuccess(true);
		}catch(Exception e){
			log.error("操作失败", e);

			json.setSuccess(false);
			json.setMsg("操作失败！");
		}
		return json;
	}

	/**
	 * 获取可以选择的角色
	 * @return
	 */
	@RequestMapping("getSelect")
	@ResponseBody
	public List<Map<String, Object>> getSelect() {
		Role role2 = new Role();
		Long parentId = UserUtils.getUser().getRole().getId();
		role2.setParentIds(parentId.toString());
		role2.setId(parentId);
		List<Role> roles=roleService.findList(role2);
		List<Map<String, Object>> list = new ArrayList<>();
		if(CollectionUtils.isNotEmpty(roles)){
			for (Role role:roles) {
				Map<String, Object> map = new HashMap<>();
				map.put("id", role.getId());
				map.put("name", role.getName());
				list.add(map);
			}
		}

		return list;
	}
}