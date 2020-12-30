package com.chunqiu.mrjuly.modules.gen.dao;

import com.chunqiu.mrjuly.common.persistence.CrudDao;
import com.chunqiu.mrjuly.modules.gen.model.GenTableColumn;

/**
 * 业务表字段DAO接口
 * @author
 * @version 2013-10-15
 */
public interface GenTableColumnDao extends CrudDao<GenTableColumn, Integer> {
	
	public void deleteByGenTableId(Integer genTableId);
}
