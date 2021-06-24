package com.jrsoft.engine.dao;

import com.jrsoft.engine.base.domain.sys.DicTreeNode;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by chesijian on 2021/6/24.
 */
public interface PfSysDicDao {
    List<DicTreeNode> findTreeDeep(@Param("parentId") String parentId, @Param("type") Integer type, @Param("enable") Integer enable);

}
