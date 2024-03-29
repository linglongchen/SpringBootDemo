package com.chunqiu.mrjuly.modules.demo;

import com.chunqiu.mrjuly.common.utils.BeanValidators;
import com.chunqiu.mrjuly.common.utils.DateUtils;
import com.chunqiu.mrjuly.common.utils.excel.ExportExcel;
import com.chunqiu.mrjuly.common.utils.excel.ImportExcel;
import com.chunqiu.mrjuly.common.vo.ExportVo;
import com.chunqiu.mrjuly.common.vo.Json;
import com.chunqiu.mrjuly.common.persistence.BaseController;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("${adminPath}/demo")
public class DemoController extends BaseController {

    /**
     * demo grid页面
     *
     * @return
     */
    @RequestMapping("demoList")
    public String demoList() {
        return "demo/demoList";
    }

    /**
     * demo form页面
     * @return
     */
    @RequestMapping("demoForm")
    public String demoForm() {
        return "demo/demoForm";
    }

    /**
     * demo select页面
     * @return
     */
    @RequestMapping("demoSelect")
    public String demoSelect(){
        return "demo/demoSelect";
    }

    /**
     * 树状grid
     * @return
     */
    @RequestMapping("treegrid")
    public String treegrid(){
        return "demo/treegrid";
    }

    @RequestMapping("getSelect")
    @ResponseBody
    public List<Map<String, Object>> getSelect() {
        List<Map<String, Object>> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", i);
            map.put("name", "数据" + i);
            list.add(map);
        }
        return list;
    }

    /**
     * 导出数据
     *
     * @return
     */
    @RequestMapping(value = "export", method = RequestMethod.GET)
    @ResponseBody
    public String exportFile(HttpServletRequest request, HttpServletResponse response) {
        try {
            String fileName = "demo数据" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";

            //测试数据
            List<ExportVo> list = new ArrayList<ExportVo>();
            for (int i = 0; i < 10; i++) {
                ExportVo vo = new ExportVo();
                vo.setAccount("demo" + i);
                vo.setName("测试帐号" + i);
                vo.setGender("男");
                vo.setPhone("138888888" + i);

                list.add(vo);
            }

            new ExportExcel("demo数据", ExportVo.class).setDataList(list).write(response, fileName).dispose();
            return null;
        } catch (Exception e) {
            request.setAttribute("error", "导出数据失败");
        }
        return null;
    }

    /**
     * 导入用户数据
     *
     * @param file
     * @return
     */
    @RequestMapping(value = "import", method = RequestMethod.POST)
    @ResponseBody
    public Json importFile(MultipartFile file) {
        Json json = new Json();
        try {
            int successNum = 0;     //导入成功条数计数器
            int failureNum = 0;     //导入失败条数计数器
            ImportExcel ei = new ImportExcel(file, 1, 0);
            List<ExportVo> list = ei.getDataList(ExportVo.class);
            for (ExportVo vo : list) {
                try {
                    BeanValidators.validateWithException(validator, vo);    //通过对象上注解验证数据完整性
                    System.out.print("帐号：" + vo.getAccount() + ";姓名：" + vo.getName() + "；性别：" + vo.getGender() + "；手机：" + vo.getPhone());
                    successNum ++;
                } catch (ConstraintViolationException ex) {
                    ex.printStackTrace();
                    failureNum ++;
                } catch (Exception ex) {
                    ex.printStackTrace();
                    failureNum ++;
                }
            }
            if (failureNum > 0) {
                json.setSuccess(false);
                json.setMsg("成功导入 " + successNum + " 条demo，失败" + + failureNum + " 条demo");
                return json;
            }
            json.setSuccess(true);
            json.setMsg("已成功导入 " + successNum + " 条demo");
            return json;
        } catch (Exception e) {
            e.printStackTrace();

            json.setSuccess(false);
            json.setMsg("导入demo失败");
        }
        return json;
    }

    /**
     * 下载导入用户数据模板
     *
     * @param response
     * @param map
     * @return
     */
    @RequestMapping(value = "import/template")
    @ResponseBody
    public void importFileTemplate(HttpServletResponse response, ModelMap map) {
        try {
            String fileName = "demo数据导入模板.xlsx";
            List<ExportVo> list = Lists.newArrayList();

            ExportVo vo = new ExportVo();
            vo.setPhone("13888888888");
            vo.setGender("男");
            vo.setName("张三");
            vo.setAccount("zhangsan");

            list.add(vo);
            new ExportExcel("demo数据", ExportVo.class, 2).setDataList(list).write(response, fileName).dispose();
        } catch (Exception e) {
            addMessage(map, "导入模板下载失败！失败信息：" + e.getMessage());
        }
    }
}
