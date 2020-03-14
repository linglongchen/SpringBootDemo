package com.chunqiu.mrjuly.modules.gen.model;



import com.chunqiu.mrjuly.modules.system.model.DictMo;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

/**
 * 生成方案Entity
 * @author
 * @version 2013-10-15
 */
@XmlRootElement(name="config")
public class GenConfig implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private List<GenCategory> categoryList;	// 代码模板分类
	private List<DictMo> javaTypeList;		// Java类型
	private List<DictMo> queryTypeList;		// 查询类型
	private List<DictMo> showTypeList;		// 显示类型

	public GenConfig() {
		super();
	}

	@XmlElementWrapper(name = "category")
	@XmlElement(name = "category")
	public List<GenCategory> getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(List<GenCategory> categoryList) {
		this.categoryList = categoryList;
	}

	@XmlElementWrapper(name = "javaType")
	@XmlElement(name = "dict")
	public List<DictMo> getJavaTypeList() {
		return javaTypeList;
	}

	public void setJavaTypeList(List<DictMo> javaTypeList) {
		this.javaTypeList = javaTypeList;
	}

	@XmlElementWrapper(name = "queryType")
	@XmlElement(name = "dict")
	public List<DictMo> getQueryTypeList() {
		return queryTypeList;
	}

	public void setQueryTypeList(List<DictMo> queryTypeList) {
		this.queryTypeList = queryTypeList;
	}

	@XmlElementWrapper(name = "showType")
	@XmlElement(name = "dict")
	public List<DictMo> getShowTypeList() {
		return showTypeList;
	}

	public void setShowTypeList(List<DictMo> showTypeList) {
		this.showTypeList = showTypeList;
	}
	
}