package com.chunqiu.mrjuly.modules.system.model;

import com.chunqiu.mrjuly.common.persistence.DataEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.Date;


/**
 * 系统用户信息Entity
 * @author wcf
 * @version 2018-11-14
 */
public class User extends DataEntity<User, Long> {

	private static final long serialVersionUID = 1L;
	private String code;		// 员工号
	private String name;		// 姓名
	private String phone;		// 手机号
	private String account;		// 登录账号
	private String password;		// 登录密码
	private String headImg;		// 头像
	private String remark;		// 备注
	private Integer roleType;   //角色平台类型标识（1.操作系统平台 2.本地系统服务端 3.在线服务系统端 4.广告管理系统端）
	private Integer flag;		// 标识（0:正常 1:删除）
	private Date createTime;		// 创建时间
	private Date updateTime;		// 更新时间
	private Long  roleId;       //角色id
	private Role role;
	private Integer hotelCodeId;
	private Integer departmentId;
	private Integer codeId;
	private Integer businessId;
	private String shopNumber;

	private String newPassWord;
	private Integer businessType;//商家类别：1-商家，2-商场

	public String getNewPassWord() {
		return newPassWord;
	}

	public void setNewPassWord(String newPassWord) {
		this.newPassWord = newPassWord;
	}

	public String getShopNumber() {
		return shopNumber;
	}

	public void setShopNumber(String shopNumber) {
		this.shopNumber = shopNumber;
	}

	public Integer getBusinessId() {
		return businessId;
	}

	public void setBusinessId(Integer businessId) {
		this.businessId = businessId;
	}

	public Integer getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}

	public Integer getCodeId() {
		return codeId;
	}

	public void setCodeId(Integer codeId) {
		this.codeId = codeId;
	}

	public Integer getHotelCodeId() {
		return hotelCodeId;
	}

	public void setHotelCodeId(Integer hotelCodeId) {
		this.hotelCodeId = hotelCodeId;
	}

	public User() {
		super();
	}

	public User(Long id){
		super();
		this.id = id;
	}

	@Length(min=0, max=50, message="员工号长度必须介于 0 和 50 之间")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Length(min=1, max=50, message="姓名长度必须介于 1 和 50 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Length(min=1, max=50, message="手机号长度必须介于 1 和 50 之间")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Length(min=1, max=50, message="登录账号长度必须介于 1 和 50 之间")
	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	@Length(min=0, max=50, message="登录密码长度必须介于 0 和 50 之间")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Length(min=0, max=250, message="头像长度必须介于 0 和 250 之间")
	public String getHeadImg() {
		return headImg;
	}

	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}

	@Length(min=0, max=50, message="备注长度必须介于 0 和 50 之间")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getRoleType() {
		return roleType;
	}

	public void setRoleType(Integer roleType) {
		this.roleType = roleType;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@NotNull(message="角色id不能为空")
	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public Integer getBusinessType() {
		return businessType;
	}

	public void setBusinessType(Integer businessType) {
		this.businessType = businessType;
	}

	@Override
	public String toString() {
		return "User{" +
				"code='" + code + '\'' +
				", name='" + name + '\'' +
				", phone='" + phone + '\'' +
				", account='" + account + '\'' +
				", password='" + password + '\'' +
				", headImg='" + headImg + '\'' +
				", remark='" + remark + '\'' +
				", roleType=" + roleType +
				", flag=" + flag +
				", createTime=" + createTime +
				", updateTime=" + updateTime +
				", roleId=" + roleId +
				", role=" + role +
				'}';
	}
}
