package com.jrsoft.engine.sys.dict.service.impl;

import com.jrsoft.engine.base.service.BaseServiceImpl;
import com.jrsoft.engine.sys.dict.domin.DicItemEntity;
import com.jrsoft.engine.sys.dict.repository.DicItemEntityRepository;
import com.jrsoft.engine.sys.dict.service.DicItemEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
  @Description 
  @Author author
  @Create 2019-05-15 20:20:54
**/
@Service
@Transactional
public class DicItemEntityServiceImpl extends BaseServiceImpl<DicItemEntity, DicItemEntityRepository> implements DicItemEntityService {

    @Autowired
    DicItemEntityRepository dicItemEntityRepository;

    @Override
    public List<DicItemEntity> findAllByParentId(String parentId) {
        return dicItemEntityRepository.findAllByParentId(parentId);
    }

    @Override
    public void deleteAllByParentId(String parentId) {
        dicItemEntityRepository.findAllByParentId(parentId);
    }
}
