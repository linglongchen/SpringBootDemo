package com.chunqiu.mrjuly.modules.system.model;

import com.chunqiu.mrjuly.common.utils.excel.annotation.ExcelField;
import com.chunqiu.mrjuly.common.persistence.DataEntity;

import javax.validation.constraints.NotEmpty;

/**
 * 机器外壳设备编码
 */
public class CodeExport extends DataEntity<CodeExport, Long> {
    @ExcelField(title = "机器外壳设备编码", type = 0, align = 2, sort = 1)
    @NotEmpty
    private String uniqueEncoding;


    public String getUniqueEncoding() {
        return uniqueEncoding;
    }

    public void setUniqueEncoding(String uniqueEncoding) {
        this.uniqueEncoding = uniqueEncoding;
    }
}
