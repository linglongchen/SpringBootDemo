package com.chunqiu.mrjuly.modules.system.controller;

import com.chunqiu.mrjuly.common.utils.StringUtils;
import com.chunqiu.mrjuly.common.utils.UserUtils;
import com.chunqiu.mrjuly.common.vo.Json;
import com.chunqiu.mrjuly.common.vo.ZTree;
import com.chunqiu.mrjuly.common.annotation.Log;
import com.chunqiu.mrjuly.common.persistence.BaseController;
import com.chunqiu.mrjuly.modules.system.model.Menu;
import com.chunqiu.mrjuly.modules.system.service.MenuService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * 菜单信息Controller
 * @author qj
 * @version 2019-01-15
 */
@Slf4j
@Controller
@RequestMapping(value = "${adminPath}/system/menu")
public class MenuController extends BaseController {

	@Autowired
	private MenuService menuService;
	
	/**
	 * @Description：菜单信息列表页面
	 * @author qj
	 */
	@Log("登录菜单列表")
	@RequestMapping("index")
	@RequiresPermissions("system:menu:view")
	public String index(){
		return "system/menuList";
	}
	
	/**
	 * @Description：菜单信息列表数据
	 * @author qj
	 */
	@Log("获取菜单列表")
	@RequestMapping("list")
	@ResponseBody
	@RequiresPermissions("system:menu:view")
	public List<Menu> list(Menu menu) {
		menu.setIsShow(null);
		menu.setRoleId(UserUtils.getUser().getRole().getId());
		return menuService.findAllList(menu);
	}
	/**
	 * @Description：菜单信息表单页面--新增/编辑
	 * @author qj
	 */
	@Log("登录菜单新增/编辑页面")
	@RequestMapping(value = "form")
	@RequiresPermissions("system:menu:view")
	public String form(Menu menu, ModelMap model) {
		if(menu.getId() != null){
			menu = menuService.get(menu.getId());
			menu.setParent(menuService.get(menu.getParentId()));
		}else{
			if(menu.getParentId() == null){
				menu.setParent(new Menu(1));
			}
			menu.setParent(menuService.get(menu.getParentId()));
			if(menu.getId() == null){
				menu.setSort(menuService.getMaxSortByPid(menu.getParent().getId()) + 30);
			}
		}
		model.addAttribute("menu", menu);
		return "system/menuForm";
	}
	/**
	 * @Description：菜单信息新增/编辑 保存方法
	 * @author wcf
	 */
	@Log("保存菜单")
	@RequestMapping("saveMenu")
	@RequiresPermissions("system:menu:edit")
	public String saveMenu(Menu menu, ModelMap model){
		try{
			menuService.save(menu);
		}catch(Exception e){
			log.error("保存失败！ msg={}", e.getMessage(), e);
			model.addAttribute("error", "保存失败！");
			return form(menu, model);
		}
		return successPath;
	}
	/**
	 * @Description：菜单信息数据删除方法
	 * @author wcf
	 */
	@Log("删除菜单")
	@RequestMapping("delMenu")
	@ResponseBody
	@RequiresPermissions("system:menu:edit")
	public Json delMenu(Menu menu){
		Json json = new Json();
		try{
			menuService.delete(menu);
			json.setSuccess(true);
		}catch(Exception e){
			log.error("删除失败！ msg={}", e.getMessage(), e);

			json.setSuccess(false);
			json.setMsg("删除失败！");
		}
		return json;
	}

	/**
	 * 获取所有的菜单列表
	 * @return
	 */
	@Log("获取所有的菜单列表")
	@RequestMapping("getMenuList")
	@ResponseBody
	public List<ZTree> getMenuList(String menuId){
		Menu menu = new Menu();
		menu.setIsShow("1");

		List<Menu> list = menuService.findAllList(menu);

		List<ZTree> trees = new ArrayList<>();
		for (Menu m : list){
			ZTree tree = new ZTree();

			tree.setId(m.getId());
			tree.setpId(m.getParentId());
			tree.setName(m.getName());
			if(StringUtils.isNotEmpty(menuId) && m.getId() == Integer.parseInt(menuId)){
				tree.setChecked(true);
			}

			trees.add(tree);
		}
		return trees;
	}

	/**
	 * 上级菜单选择页
	 * @return
	 */
	@Log("获取上级菜单选择页")
	@RequestMapping("menuSelect")
	public String menuSelect(String menuId, ModelMap model){
		model.addAttribute("menuId", menuId);
		return "system/menuSelectForm";
	}

}