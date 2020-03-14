package com.chunqiu.mrjuly.common.persistence;

import com.chunqiu.mrjuly.common.utils.UserUtils;
import com.chunqiu.mrjuly.common.utils.excel.annotation.ExcelField;
import com.chunqiu.mrjuly.modules.system.model.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.time.DateUtils;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Date;

/**
 * 数据Entity类
 * @author
 * @version 2014-05-16
 */
public abstract class DataEntity<T, Pk extends Serializable> extends BaseEntity<T, Pk> implements Serializable{

	private static final long serialVersionUID = 1L;
	
	protected User createBy;	// 创建者

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title = "下单日期", type = 0, align = 2, sort = 0)
	@NotEmpty
	protected Date createDate;	// 创建日期
	protected User updateBy;	// 更新者
	protected Date updateDate;	// 更新日期
	protected String delFlag; 	// 删除标记（0：正常；1：删除；2：审核）
	protected String remarks;
	protected Date createDateEnd;	// 创建日期
	public DataEntity() {
		super();
		this.delFlag = DEL_FLAG_NORMAL;
	}
	
	public DataEntity(Pk id) {
		super(id);
	}
	
	/**
	 * 插入之前执行方法，需要手动调用
	 */
	@Override
	public void preInsert(){
		// 不限制ID为UUID，调用setIsNewRecord()使用自定义ID
		if (this.isNewRecord){
			//update by wcf at 2016-03-16
			//setId(IdGen.uuid());
			setIsNewRecord(true);
			setId(null);//使用mysql的自增长
		}
		User user = UserUtils.getUser();
		if (user.getId() != null){
			//update by wcf at 2016-03-16
			this.updateBy = user;
			this.createBy = user;
		}
		this.updateDate = new Date();
		if(this.createDate == null){
			this.createDate = new Date();
		}
	}
	
	/**
	 * 更新之前执行方法，需要手动调用
	 */
	@Override
	public void preUpdate(){
		User user = UserUtils.getUser();
		if (user.getId()!=null){
			//update by wcf at 2016-03-16
			this.updateBy = user;
		}
		this.updateDate = new Date();
	}
	
	@JsonIgnore
	public User getCreateBy() {
		return createBy;
	}

	public void setCreateBy(User createBy) {
		this.createBy = createBy;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createTime) {
		this.createDate = createTime;
	}

	public User getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(User updateBy) {
		this.updateBy = updateBy;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	@Length(min=1, max=1)
	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Date getCreateDateEnd() {
		return createDateEnd;
	}

	public void setCreateDateEnd(Date createDateEnd) {
		if (createDateEnd!=null){
			createDateEnd= DateUtils.addSeconds(createDateEnd,24*60*60-1);
		}
		this.createDateEnd = createDateEnd;
	}
}
