package com.jrsoft.business.modules.construction.domain;

import com.jrsoft.engine.impl.persistence.entity.BaseModelEntity;

import java.util.List;

/**
 * Created by chesijian on 2018/10/27.
 */
public class CheckPart extends BaseModelEntity {
    String name;
    String parentUid;
    List<CheckPart> children;
    int sort;
    boolean leaf;

    public List<CheckPart> getChildren() {
        return children;
    }

    public void setChildren(List<CheckPart> children) {
        this.children = children;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public boolean isLeaf() {
        return leaf;
    }

    public void setLeaf(boolean leaf) {
        this.leaf = leaf;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentUid() {
        return parentUid;
    }

    public void setParentUid(String parentUid) {
        this.parentUid = parentUid;
    }


}
