package com.jrsoft.engine.service;

import com.jrsoft.engine.base.domain.sys.PfMenu;

import java.util.List;

/**
 * Created by chesijian on 2021/6/12.
 */
public interface MenuService {

    List<PfMenu> getAllMenus();

    PfMenu getMenuById(String id);

    /**
     * 新增菜单
     * @param entity
     */
    void insertMenu(PfMenu entity);
}
