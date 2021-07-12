package com.jrsoft.engine.service;

import com.jrsoft.engine.base.domain.sys.DicTreeNode;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by chesijian on 2021/6/24.
 */
public interface PfDicTreeService {
    List<DicTreeNode> findTreeDeep(String parentId, Integer type, Integer enable);

    List<DicTreeNode> findTree(String parentId, Integer type, Integer enable);
}
