package com.chunqiu.mrjuly.modules.system.controller;

import com.chunqiu.mrjuly.common.utils.MD5Util;
import com.chunqiu.mrjuly.common.utils.MD5Utils;
import com.chunqiu.mrjuly.common.utils.UserUtils;
import com.chunqiu.mrjuly.common.vo.Grid;
import com.chunqiu.mrjuly.common.vo.GridParam;
import com.chunqiu.mrjuly.common.vo.Json;
import com.chunqiu.mrjuly.modules.system.service.UserService;
import com.chunqiu.mrjuly.common.annotation.Log;
import com.chunqiu.mrjuly.common.persistence.BaseController;
import com.chunqiu.mrjuly.modules.system.model.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 系统用户信息Controller
 * @author wcf
 * @version 2018-11-14
 */
@Slf4j
@Controller
@RequestMapping(value = "${adminPath}/system/user")
public class UserController extends BaseController {

	@Autowired
	private UserService userService;
	
	/**
	 * @Description：系统用户信息列表页面
	 * @author wcf
	 */
	@Log("登录系统用户信息列表页面")
	@RequestMapping("index")
	@RequiresPermissions("system:user:view")
	public String index(){
		return "system/userList1";
	}

	/**
	 * @Description：系统用户信息列表数据
	 * @author wcf
	 */
	@Log("获取系统用户信息列表数据")
	@RequestMapping("list")
	@ResponseBody
	@RequiresPermissions("system:user:view")
	public Grid list(User user, GridParam param) {
		int roleType = UserUtils.getUser().getRoleType();
		if (roleType == 1){
			user.setRoleType(roleType);
			return userService.findPage(user, param);
		}else {
			Long  roleId=UserUtils.getUser().getRole().getId();
			user.setRoleId(roleId);
			return userService.findPage(user, param);
		}
	}
	/**
	 * @Description：系统用户信息表单页面--新增/编辑
	 * @author wcf
	 */
	@Log("登录系统用户新增/编辑页面")
	@RequestMapping(value = "form")
	@RequiresPermissions("system:user:view")
	public String form(User user, ModelMap model) {
		if(user.getName() == null && user.getId() != null){
			user = userService.get(user.getId());
			model.addAttribute("user", user);
		}
		//返回用户信息
		model.addAttribute("userInfo", UserUtils.getUser());
		model.addAttribute("user", user);
		return "system/userForm1";
	}
	/**
	 * @Description：系统用户信息新增/编辑 保存方法
	 * @author wcf
	 */
	@Log("保存系统用户")
	@RequestMapping("saveUser")
	@RequiresPermissions("system:user:edit")
	public String saveUser(User user, ModelMap model){
		if (user.getId() == null){
			if(null !=userService.getByAccount(user)){
				model.addAttribute("error", "账号已存在！");
				return form(user, model);
			}
		}

		try{
			//平台类型
			int roleType =	UserUtils.getUser().getRoleType();
			if (roleType == 2){
				//酒店平台
				user.setRoleType(2);
				user.setHotelCodeId(UserUtils.getUser().getHotelCodeId());
				user.setBusinessType(null);
			}else if (roleType == 3){
				//商家平台
				user.setRoleType(3);
				user.setBusinessId(UserUtils.getUser().getBusinessId());
			}else if (roleType == 4){
				//广告平台
				user.setRoleType(4);
				user.setBusinessId(UserUtils.getUser().getBusinessId());
				user.setBusinessType(null);
				//系统超级管理员
			}else if (roleType == 0){
				user.setRoleType(1);
				user.setBusinessType(null);
			}
			userService.save(user);
		}catch(Exception e){
			log.error("保存失败！ msg={}", e.getMessage(), e);
			model.addAttribute("error", "保存失败！");
			return form(user, model);
		}

		return successPath;
	}
	/**
	 * @Description：系统用户信息数据删除方法
	 * @author wcf
	 */
	@Log("删除系统用户")
	@RequestMapping("delUser")
	@ResponseBody
	@RequiresPermissions("system:user:edit")
	public Json delUser(User user){
		Json json = new Json();
		try{
			userService.delete(user);
			json.setSuccess(true);
		}catch(Exception e){
			log.error("删除失败！ msg={}", e.getMessage(), e);

			json.setSuccess(false);
			json.setMsg("删除失败！");
		}
		return json;
	}

	@RequestMapping("getManagerList")
	@ResponseBody
	@RequiresPermissions("system:user:view")
	public List<User> getManagerList(){
		User user = new User();
		return userService.findList(user);
	}

	/**
	 * 修改密码页面
	 * @return
	 */
	@Log("登录修改系统用户页面")
	@RequestMapping("editPwd")
	@RequiresPermissions("system:user:edit")
	public String editPwd(ModelMap modelMap){
		return "system/editPwd";
	}

	@Log("修改系统用户密码")
	@RequestMapping("savePwd")
	@RequiresPermissions("system:user:edit")
	public String savePwd(User user,ModelMap modelMap){
		long userId = UserUtils.getUser().getId();
		User userInfo = userService.get(userId);
		Boolean flag = MD5Utils.isValidate(user.getPassword(),userInfo.getPassword());
		if (!flag){
			modelMap.addAttribute("error", "旧密码输入错误！");
			return editPwd(modelMap);
		}else {
			//修改密码
			User user1 = new User();
			user1.setId(userId);
			user1.setPassword(MD5Util.encrypt(user.getNewPassWord()));
			userService.save(user1);
		}
		return successPath;
	}


	@RequestMapping("userList")
	@ResponseBody
	@RequiresPermissions("system:user:view")
	public User userList(User user,ModelMap modelMap){
	  	user = UserUtils.getUser();
	  	int roleType = user.getRoleType();
  		if (StringUtils.isBlank(user.getAccount())) {
			user.setAccount("Welcome Linkinn");
			user.setName("Welcome Linkinn");
		}
  		if (user != null) {
  			user.setRoleType(roleType);
		}
		return user;
	}

	/**
	 * 打开用户星级管理页面
	 * @author wy
	 */
	@Log("打开用户星级管理页面")
	@RequestMapping("openUserStarManage")
	@RequiresPermissions("system:userStar:view")
	public String openUserStarManage(){
		return "system/userStarManage";
	}
}
