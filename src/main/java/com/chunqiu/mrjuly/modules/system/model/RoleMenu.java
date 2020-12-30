package com.chunqiu.mrjuly.modules.system.model;

import javax.validation.constraints.NotNull;

import com.chunqiu.mrjuly.common.persistence.DataEntity;

/**
 * 角色菜单信息Entity
 * @author wcf
 * @version 2018-11-14
 */
public class RoleMenu extends DataEntity<RoleMenu, Integer> {
	
	private static final long serialVersionUID = 1L;
	private Long roleId;		// 角色编号
	private Integer menuId;		// 菜单编号
	
	public RoleMenu() {
		super();
	}

	public RoleMenu(Integer id){
		super(id);
	}

	@NotNull(message="角色编号不能为空")
	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	
	@NotNull(message="菜单编号不能为空")
	public Integer getMenuId() {
		return menuId;
	}

	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}
	
}