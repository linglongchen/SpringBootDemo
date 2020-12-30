package com.chunqiu.mrjuly.modules.basic;

import com.chunqiu.mrjuly.common.enums.ResultStatusCode;
import com.chunqiu.mrjuly.common.shiro.ShiroRealm;
import com.chunqiu.mrjuly.common.vo.Result;
import com.chunqiu.mrjuly.common.persistence.BaseController;
import org.apache.shiro.SecurityUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("${adminPath}/common")
@RestController
public class CommonController extends BaseController {

    /**
     * 未授权跳转方法
     * @return
     */
    @RequestMapping("/unauth")
    public Result unauth(){
        ShiroRealm shiroRealm=new ShiroRealm();
        shiroRealm.clearCache();
        SecurityUtils.getSubject().logout();
        return new Result(ResultStatusCode.UNAUTHO_ERROR);
    }

    /**
     * 被踢出后跳转方法
     * @return
     */
    @RequestMapping("/kickout")
    public Result kickout(){
        return new Result(ResultStatusCode.INVALID_TOKEN);
    }

}
