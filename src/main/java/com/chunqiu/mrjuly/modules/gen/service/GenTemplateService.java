package com.chunqiu.mrjuly.modules.gen.service;

import com.chunqiu.mrjuly.common.utils.PageUtils;
import com.chunqiu.mrjuly.common.vo.Grid;
import com.chunqiu.mrjuly.common.vo.GridParam;
import com.chunqiu.mrjuly.modules.gen.dao.GenTemplateDao;
import com.chunqiu.mrjuly.modules.gen.model.GenTemplate;
import com.github.pagehelper.Page;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 代码模板Service
 * @author
 * @version 2013-10-15
 */
@Service
public class GenTemplateService  {

	@Resource
	private GenTemplateDao genTemplateDao;
	
	public GenTemplate get(Integer id) {
		return genTemplateDao.get(id);
	}
	
	public Grid find(GridParam gridParam, GenTemplate genTemplate) {
		/*genTemplate.setPage(page);
		page.setList(genTemplateDao.findList(genTemplate));
		return page;*/

		Grid grid = new Grid();

		Page page = PageUtils.offsetPage(gridParam);
		genTemplateDao.findList(genTemplate);

		grid.setPage(page);
		return grid;
	}
	
	public void save(GenTemplate genTemplate) {
		if (genTemplate.getContent()!=null){
			genTemplate.setContent(StringEscapeUtils.unescapeHtml4(genTemplate.getContent()));
		}
		if (genTemplate.getId()==null){
			genTemplate.setCreateDate(new Date());
			genTemplateDao.insert(genTemplate);
		}else{
			genTemplate.setUpdateDate(new Date());
			genTemplateDao.update(genTemplate);
		}
	}
	
	public void delete(GenTemplate genTemplate) {
		genTemplateDao.delete(genTemplate.getId());
	}
	
}
