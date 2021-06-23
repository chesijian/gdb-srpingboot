package com.jrsoft.engine.service.impl;/**
 * Created by chesijian on 2021/6/12.
 */

import com.jrsoft.engine.base.domain.sys.PfMenu;
import com.jrsoft.engine.service.MenuService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName MenuServiceImpl
 * @Description TODO
 * @Author chesijian
 * @Date 2021/6/12 11:53
 * @Version 1.0
 */
@Service
public class MenuServiceImpl implements MenuService {

    @Override
    public List<PfMenu> getAllMenus() {
        List<PfMenu> data = new ArrayList<>();
        PfMenu menu = new PfMenu();
        menu.setPattern("/chesijian/**");
        data.add(menu);
        return data;
    }

    @Override
    public PfMenu getMenuById(String id) {
        return null;
    }
}
