package com.jrsoft.engine.service.impl;/**
 * Created by chesijian on 2021/5/25.
 */

import com.jrsoft.engine.base.domain.sys.PfTokenRedis;
import com.jrsoft.engine.db.mapping.org.LoginDao;
import com.jrsoft.engine.model.org.OrgDepart;
import com.jrsoft.engine.model.org.OrgRole;
import com.jrsoft.engine.model.org.OrgUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName LoginServiceImpl
 * @Description TODO
 * @Author chesijian
 * @Date 2021/5/25 11:06
 * @Version 1.0
 */
@Service
public class LoginServiceImpl implements UserDetailsService {

    @Autowired
    private LoginDao loginDao;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        OrgUser user =loginDao.loadUserByUsername(userId);
        if(user==null){
            throw new UsernameNotFoundException("用户名不存在");
        }

        List<OrgRole> roleList = loginDao.getUserRoleList(user.getId());
        PfTokenRedis result = new PfTokenRedis();
        OrgDepart company = loginDao.getCompanyByUser(user.getCompany());
        result.setUser(user);
        result.setRoleList(roleList);
        result.setCompany(company);
        return result;
    }
}
