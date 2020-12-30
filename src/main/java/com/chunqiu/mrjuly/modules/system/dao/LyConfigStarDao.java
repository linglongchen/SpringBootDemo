package com.chunqiu.mrjuly.modules.system.dao;

import java.util.List;

import com.chunqiu.mrjuly.modules.system.model.LyConfigStar;

/**
 * 用户星级折扣DAO层，DB_Table：ly_config_star
 * 
 * @author wy
 *
 */
public interface LyConfigStarDao {
	/**
	 * 根据主键删除记录
	 * 
	 * @param id
	 * @return 成功删除的行数
	 * @author wy
	 */
	int deleteByPrimaryKey(Long id);

	/**
	 * 新增用户星级折扣记录
	 * 
	 * @param record
	 *            用户星级折扣实体数据
	 * @return 成功新增的行数
	 * @author wy
	 */
	int insertSelective(LyConfigStar record);

	/**
	 * 根据ID查询用户星级折扣记录
	 * 
	 * @param id
	 * @return 用户星级折扣实体数据
	 * @author wy
	 */
	LyConfigStar selectByPrimaryKey(Long id);

	/**
	 * 根据ID更新用户星级折扣记录
	 * 
	 * @param record
	 *            用户星级折扣实体数据
	 * @return 成功更新的行数
	 * @author wy
	 */
	int updateByPrimaryKeySelective(LyConfigStar record);

	/**
	 * 获取所有用户星级折扣记录
	 * 
	 * @return 用户星级折扣记录列表
	 * @author wy
	 */
	List<LyConfigStar> getAllConfigStar();

	/**
	 * 获取最大星级
	 * 
	 * @return 最大星级
	 * @author wy
	 */
	Integer getMaxStar();
}