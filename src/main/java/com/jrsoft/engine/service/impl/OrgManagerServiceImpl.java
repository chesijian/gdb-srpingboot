package com.jrsoft.engine.service.impl;/**
 * Created by chesijian on 2021/6/16.
 */

import com.jrsoft.engine.base.domain.org.OrgTreeNode;
import com.jrsoft.engine.base.domain.sys.PfMenu;
import com.jrsoft.engine.common.query.UserQuery;
import com.jrsoft.engine.common.utils.CommonUtil;
import com.jrsoft.engine.common.utils.SessionUtil;
import com.jrsoft.engine.dao.OrgManagerDao;
import com.jrsoft.engine.exception.EngineIllegalArgumentException;
import com.jrsoft.engine.model.org.OrgDepart;
import com.jrsoft.engine.model.org.OrgPosition;
import com.jrsoft.engine.model.org.OrgUser;
import com.jrsoft.engine.service.OrgManagerServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName OrgManagerServiceImpl
 * @Description TODO
 * @Author chesijian
 * @Date 2021/6/16 13:49
 * @Version 1.0
 */
@Service
public class OrgManagerServiceImpl implements OrgManagerServiceI {

    @Autowired
    private OrgManagerDao orgManagerDao;

    @Override
    public OrgTreeNode getOrg(String type, String projType) {
        String companyUid= SessionUtil.getCompanyUid();
        if (companyUid == null) {
            throw new EngineIllegalArgumentException("companyUid is null");
        }
        OrgDepart company = orgManagerDao.findDepartById(companyUid);
        if (company == null) {
            throw new EngineIllegalArgumentException("company is null");
        }
        OrgTreeNode root = new OrgTreeNode();
        root.setId(company.getId());
        root.setNodeId(company.getDepartId());
        root.setLeaf(false);
        root.setType(company.getDepartType());
        root.setLabel(company.getDepartName());
        root.setText(company.getDepartName());
        root.setParentId(null);
        root.setSort(company.getSort());
        recursion(root);
        return root;
    }

    /**
     * 递归查询下级部门
     * @param treeNode
     */
    private void recursion(OrgTreeNode treeNode){

        List<OrgDepart> departList = orgManagerDao.findDepartByParentId(treeNode.getId());
        if(CommonUtil.ifNotEmptyList(departList)){
            if(treeNode.getChildren()==null){
                treeNode.setChildren(new ArrayList<>());
            }
            treeNode.setLeaf(false);
            for (OrgDepart depart : departList) {
                OrgTreeNode child = new OrgTreeNode();
                child.setId(depart.getId());
                child.setNodeId(depart.getDepartId());
                child.setLeaf(false);
                child.setType(depart.getDepartType());
                child.setLabel(depart.getDepartName());
                child.setText(depart.getDepartName());
                child.setParentId(null);
                child.setSort(depart.getSort());
                treeNode.getChildren().add(child);
                recursion(child);
            }

        }
    }

    /**
     * 查询用户总数
     * @param userQuery
     * @return
     */
    @Override
    public long queryUserTotalCount(UserQuery userQuery) {
        return orgManagerDao.queryUserTotalCount(userQuery);
    }

    /**
     * 查询组织架构用户
     * @param userQuery
     * @return
     */
    @Override
    public List<OrgUser> getUserList(UserQuery userQuery) {
        return orgManagerDao.getUserList(userQuery);
    }

    /**
     * 获取用户岗位
     * @param userUid
     * @return
     */
    @Override
    public List<OrgPosition> queryUserPositionById(String userUid) {
        return orgManagerDao.queryUserPositionById(userUid);
    }

    @Override
    public List<PfMenu> findAllMenu(String parentId) {
        String CompanyUid = SessionUtil.getCompanyUid();

        // 新的做法全部查出，然后递归组装数据
        List<PfMenu> menuList = new ArrayList<>();
        //List<PfMenu> data = commandContext.getPfMenuManager().findAllMenu((PfMenuQueryImpl)query);
        List<PfMenu> data = orgManagerDao.findMenusByParentId(parentId,CompanyUid);

        Map<String,List<PfMenu>> menuChildrenMap = new HashMap<>();
        if(CommonUtil.ifNotEmpty(data)){
            for(PfMenu menu : data){
                menu.setResourceType(3);
                String tempParentId = menu.getParentId();
                if(tempParentId == null){
                    /*if(obj.getParentId() == null){
                        menuList.add(menu);
                    }*/
                    continue;
                }
                // 找到第一级节点
                if(tempParentId.equals(parentId)){
                    menuList.add(menu);
                }
                List<PfMenu> children = menuChildrenMap.get(tempParentId);
                if(children == null){
                    children = new ArrayList<>();
                    menuChildrenMap.put(tempParentId,children);
                }
                children.add(menu);
            }
        }
        // 为第一级节点查找下级
        for(PfMenu menu : menuList){
            List<PfMenu> children = getMenuChildren(menu,menuChildrenMap);
            menu.setChildren(children);
        }
        // 旧的写法，递归查数据库效率低
        /*menuList = commandContext.getPfMenuManager().findAllMenu((PfMenuQueryImpl)query);
        if(CommonUtil.ifNotEmptyList(menuList)){
            for(PfMenu item : menuList){
                deep( commandContext, (PfMenuPo)item,query, ifDeep,ifContainAuth);
            }
        }*/
				//return (T)menuList;

        /*if(data.size()>0){
            for (PfMenu menu : data) {
                findAllMenu(menu.getId());
            }
        }*/

        return menuList;
    }

    private List<PfMenu> getMenuChildren(PfMenu menu, Map<String, List<PfMenu>> menuChildrenMap) {
        List<PfMenu> children = menuChildrenMap.get(menu.getId());
        if(children != null){
            for(PfMenu item : children){
                List<PfMenu> son = getMenuChildren(item,menuChildrenMap);
                item.setChildren(son);
            }
        }
        return children;
    }
}
