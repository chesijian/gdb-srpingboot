package com.jrsoft.engine.filter;/**
 * Created by chesijian on 2021/6/12.
 */

import com.jrsoft.engine.base.domain.sys.PfMenu;
import com.jrsoft.engine.model.org.OrgRole;
import com.jrsoft.engine.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;
import java.util.List;

/**
 * @ClassName MyFilterInvocationSecurity
 * @Description TODO
 * @Author chesijian
 * @Date 2021/6/12 11:50
 * @Version 1.0
 */
//@Component
public class MyFilterInvocationSecurity implements FilterInvocationSecurityMetadataSource {

    AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Autowired
    private MenuService menuService;


    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        String requestUrl = ((FilterInvocation)o).getRequestUrl();
        List<PfMenu> allMenus = menuService.getAllMenus();
        if(allMenus!=null){
            for (PfMenu menu : allMenus) {
                if (antPathMatcher.match(menu.getPattern(),requestUrl)){
                    List<OrgRole> roleList = menu.getRoleList();
                    String[] roles= new String[roleList.size()];
                    for (int i = 0; i < roleList.size(); i++) {
                        roles[1]=roleList.get(i).getRoleId();
                    }
                    return SecurityConfig.createList(roles);
                }
            }
        }

        return SecurityConfig.createList("ROLE_login");
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }
}
