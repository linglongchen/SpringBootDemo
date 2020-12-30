package com.chunqiu.mrjuly.modules.basic;

import com.chunqiu.mrjuly.common.utils.UserUtils;
import com.chunqiu.mrjuly.modules.system.model.User;
import com.chunqiu.mrjuly.modules.system.service.MenuService;
import com.chunqiu.mrjuly.common.persistence.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;


@Controller
@RequestMapping("${adminPath}")
public class HomeController extends BaseController {
    @Autowired
    private MenuService menuService;

    /**
     * 首页
     * @return
     */
    @RequestMapping("/index")
    public String index(ModelMap map,ThymeleafViewResolver viewResolver){
        // 登录成后，即可通过 Subject 获取登录的用户信息
        User user = UserUtils.getUser();
        if(user ==null){
            return "login";
        }

        map.addAttribute("user",user);
        map.addAttribute("html", menuService.getMenuHtml(adminPath));
        return "index";
    }

    @RequestMapping("/user")
    @ResponseBody
    public User user(){
        // 登录成后，即可通过 Subject 获取登录的用户信息
        User user = UserUtils.getUser();
        return user;
    }

    @RequestMapping("iconSelect")
    public String iconSelect(){
        return "dialog/iconSelect";
    }
}
