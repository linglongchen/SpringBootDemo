package com.chunqiu.mrjuly.modules.system.service;

import javax.annotation.Resource;

import com.chunqiu.mrjuly.common.persistence.CrudService;
import org.springframework.stereotype.Service;
import com.chunqiu.mrjuly.modules.system.model.Dict;
import com.chunqiu.mrjuly.modules.system.dao.DictDao;

/**
 * 字典信息Service
 * @author wcf
 * @version 2018-11-14
 */
@Service
public class DictService extends CrudService<DictDao, Dict, Integer> {
	@Resource
	private DictDao dao;

}