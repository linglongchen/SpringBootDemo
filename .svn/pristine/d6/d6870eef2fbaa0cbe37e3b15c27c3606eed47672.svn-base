package com.chunqiu.mrjuly.modules.system.model;

import org.hibernate.validator.constraints.Length;

import com.chunqiu.mrjuly.common.persistence.DataEntity;

/**
 * 系统角色Entity
 * @author wcf
 * @version 2018-11-14
 */
public class Role extends DataEntity<Role, Long> {
	
	private static final long serialVersionUID = 1L;
	private Long parentId;		 // 父级id
	private String parentIds;    // 父级ids
	private String parentIdss;   //父级idss
	private String name;		 // 岗位名称
	private String comment;		 // 描述
	private Integer platformType;//平台类型 ：1.操作系统平台 2.本地系统服务端
								// 3.在线服务系统端 4.广告管理系统端
	
	public Role() {
		super();
	}

	public Role(Long id){
		super(id);
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parent_id) {
		this.parentId = parent_id;
	}

	public String getParentIds() {
		return parentIds;
	}

	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}

	@Length(min=1, max=50, message="岗位名称长度必须介于 1 和 50 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=1, max=50, message="描述长度必须介于 1 和 50 之间")
	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Integer getPlatformType() {
		return platformType;
	}

	public void setPlatformType(Integer platformType) {
		this.platformType = platformType;
	}

	public String getParentIdss() {
		return parentIdss;
	}

	public void setParentIdss(String parentIdss) {
		this.parentIdss = parentIdss;
	}
}