package com.chunqiu.mrjuly.modules.system.dao;

import com.chunqiu.mrjuly.modules.system.model.Menu;
import com.chunqiu.mrjuly.common.persistence.CrudDao;

import java.util.List;

/**
 * 菜单信息DAO接口
 * @author qj
 * @version 2018-11-14
 */
public interface MenuDao extends CrudDao<Menu, Integer> {
    /**
     * 通过角色id获取所有权限信息
     * @param roleId
     * @return
     */
    List<Menu> getAllMenuByRoleId(Long roleId);

    List<Menu> findByRoleId(Menu menu);

    List<Menu> findByParentIdsLike(Menu menu);

    int updateParentIds(Menu menu);

    /**
     * 根据pid获取子目录最大排序值
     * @param pid
     * @return
     */
    int getMaxSortByPid(Integer pid);

    /**
     * 根据角色查询菜单列表,查询所有的菜单，标记自己拥有的
     * @param menu
     * @return
     */
    List<Menu> findMenuByRole(Menu menu);
}