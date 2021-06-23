package com.jrsoft.engine.service;

import com.jrsoft.engine.base.domain.org.OrgTreeNode;
import com.jrsoft.engine.base.domain.sys.PfMenu;
import com.jrsoft.engine.common.query.UserQuery;
import com.jrsoft.engine.model.org.OrgPosition;
import com.jrsoft.engine.model.org.OrgUser;

import java.util.List;

/**
 * Created by chesijian on 2021/6/16.
 */
public interface OrgManagerServiceI {
    OrgTreeNode getOrg(String type, String projType);

    /**
     * 查询用户总数
     * @param userQuery
     * @return
     */
    long queryUserTotalCount(UserQuery userQuery);

    /**
     * 查询组织架构用户
     * @param userQuery
     * @return
     */
    List<OrgUser> getUserList(UserQuery userQuery);

    /**
     * 获取用户岗位
     * @param userUid
     * @return
     */
    List<OrgPosition> queryUserPositionById(String userUid);

    /**
     * 查询菜单树
     * @param parentId
     * @return
     */
    List<PfMenu> findAllMenu(String parentId);
}
