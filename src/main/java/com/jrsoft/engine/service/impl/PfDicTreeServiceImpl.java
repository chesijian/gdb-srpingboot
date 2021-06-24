package com.jrsoft.engine.service.impl;/**
 * Created by chesijian on 2021/6/24.
 */

import com.jrsoft.engine.base.domain.sys.DicTreeNode;
import com.jrsoft.engine.dao.PfSysDicDao;
import com.jrsoft.engine.service.PfDicTreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName PfDicTreeServiceImpl
 * @Description TODO
 * @Author chesijian
 * @Date 2021/6/24 15:27
 * @Version 1.0
 */
@Service
public class PfDicTreeServiceImpl implements PfDicTreeService {

    @Autowired
    private PfSysDicDao pfSysDicDao;


    @Override
    public List<DicTreeNode> findTreeDeep(String parentId, Integer type, Integer enable) {
        return pfSysDicDao.findTreeDeep(parentId,type,enable);
    }

    @Override
    public List<DicTreeNode> findTree(String parentId, Integer type, Integer enable) {
        return null;
    }
}
