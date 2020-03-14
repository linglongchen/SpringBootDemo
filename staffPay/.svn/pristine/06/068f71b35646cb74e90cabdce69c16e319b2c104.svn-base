package com.chunqiu.mrjuly.common.vo;

import com.chunqiu.mrjuly.common.utils.excel.annotation.ExcelField;

import javax.validation.constraints.NotEmpty;

/**
 * @Description:
 * @Date: 2018/3/15
 * @Author: wcf
 */
public class ExportVo {
    @ExcelField(title = "帐号", type = 0, align = 2, sort = 0)
    @NotEmpty
    private String account;
    @ExcelField(title = "姓名", type = 0, align = 2, sort = 1)
    @NotEmpty
    private String name;
    @ExcelField(title = "手机", type = 0, align = 2, sort = 2)
    @NotEmpty
    private String phone;
    @ExcelField(title = "性别", type = 0, align = 2, sort = 3)
    @NotEmpty
    private String gender;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
