package com.jrsoft.engine.service.impl;/**
 * Created by chesijian on 2021/6/12.
 */

import com.jrsoft.engine.base.domain.sys.PfMenu;
import com.jrsoft.engine.dao.PfMenuDao;
import com.jrsoft.engine.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @ClassName MenuServiceImpl
 * @Description TODO
 * @Author chesijian
 * @Date 2021/6/12 11:53
 * @Version 1.0
 */
@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private PfMenuDao pfMenuDao;

    @Override
    public List<PfMenu> getAllMenus() {
        List<PfMenu> data = new ArrayList<>();
        PfMenu menu = new PfMenu();
        menu.setPattern("/chesijian/**");
        data.add(menu);
        return data;
    }

    @Override
    public void insertMenu(PfMenu entity) {
        pfMenuDao.save(entity);
    }

    @Override
    public PfMenu getMenuById(String id) {
        //Optional<PfMenu> byId = pfMenuDao.findById(id);
        return pfMenuDao.getMenuById(id);
    }

}
