package com.chunqiu.mrjuly.common.utils;

import java.math.BigDecimal;
import java.util.List;

public class Series {
    public String name;

    public String type;

    public List<BigDecimal> data;
    public Series( String name, String type, List<BigDecimal> data) {
        super();
        this.name = name;
        this.type = type;
        this.data = data;
    }
}
