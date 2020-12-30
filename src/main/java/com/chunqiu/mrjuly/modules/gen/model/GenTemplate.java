package com.chunqiu.mrjuly.modules.gen.model;

import com.chunqiu.mrjuly.common.utils.StringUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;
import org.hibernate.validator.constraints.Length;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.Date;
import java.util.List;

/**
 * 生成方案Entity
 * @author
 * @version 2013-10-15
 */
@XmlRootElement(name="template")
public class GenTemplate {
	
	private Integer id;
	private String name; 	// 名称
	private String category;		// 分类
	private String filePath;		// 生成文件路径
	private String fileName;		// 文件名
	private String content;		// 内容
	private String remarks;
	protected Date createDate;	// 创建日期
	protected Date updateDate;	// 更新日期
	protected String delFlag; 	// 删除标记（0：正常；1：删除；2：审核）

	protected String orderBy;	//排序字段

	private String dbName = "mysql";	//当前数据库类型

	public GenTemplate() {
		super();
	}

	public GenTemplate(Integer id){
		this();
		this.id = id;
	}
	
	@Length(min=1, max=200)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
	
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@XmlTransient
	public List<String> getCategoryList() {
		if (category == null){
			return Lists.newArrayList();
		}else{
			return Lists.newArrayList(StringUtils.split(category, ","));
		}
	}

	public void setCategoryList(List<String> categoryList) {
		if (categoryList == null){
			this.category = "";
		}else{
			this.category = ","+StringUtils.join(categoryList, ",") + ",";
		}
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
	@JsonIgnore
	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
	@JsonIgnore
	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	@Override
	public String toString() {
		return "GenTemplate{" +
				"id=" + id +
				", name='" + name + '\'' +
				", category='" + category + '\'' +
				", filePath='" + filePath + '\'' +
				", fileName='" + fileName + '\'' +
				", content='" + content + '\'' +
				", remarks='" + remarks + '\'' +
				", createDate=" + createDate +
				", updateDate=" + updateDate +
				", delFlag='" + delFlag + '\'' +
				", orderBy='" + orderBy + '\'' +
				", dbName='" + dbName + '\'' +
				'}';
	}
}


