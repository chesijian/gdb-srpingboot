package com.jrsoft.engine.dao;

import com.jrsoft.engine.base.domain.sys.PfMenu;
import com.jrsoft.engine.common.query.UserQuery;
import com.jrsoft.engine.model.org.OrgDepart;
import com.jrsoft.engine.model.org.OrgPosition;
import com.jrsoft.engine.model.org.OrgUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by chesijian on 2021/6/16.
 */
public interface OrgManagerDao {


    OrgDepart findDepartById(@Param("departUid") String departUid);

    /**
     * 查询下级部门
     * @param id
     * @return
     */
    List<OrgDepart> findDepartByParentId(@Param("parentUid")String id);

    long queryUserTotalCount(UserQuery userQuery);

    List<OrgUser> getUserList(UserQuery userQuery);

    /**
     * 获取用户岗位
     * @param userUid
     * @return
     */
    List<OrgPosition> queryUserPositionById(@Param("userUid")String userUid);

    List<PfMenu> findMenusByParentId(@Param("parentId")String parentId, @Param("companyUid")String companyUid);
}
