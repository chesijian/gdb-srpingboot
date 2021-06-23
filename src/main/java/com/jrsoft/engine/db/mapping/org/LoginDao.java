package com.jrsoft.engine.db.mapping.org;

import com.jrsoft.engine.model.org.OrgDepart;
import com.jrsoft.engine.model.org.OrgRole;
import com.jrsoft.engine.model.org.OrgUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by chesijian on 2021/5/25.
 */
public interface LoginDao {

    OrgUser loadUserByUsername(@Param("userId") String userId);

    /**
     * 获取用户角色列表
     * @param userUid
     * @return
     */
    List<OrgRole> getUserRoleList(String userUid);

    /**
     * 获取用户所在公司
     * @param companyUid
     * @return
     */
    OrgDepart getCompanyByUser(String companyUid);
}
