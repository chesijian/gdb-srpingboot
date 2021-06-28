package com.jrsoft.engine.sys.dict.service.impl;

import com.jrsoft.engine.base.service.BaseServiceImpl;
import com.jrsoft.engine.sys.dict.domin.DicEntity;
import com.jrsoft.engine.sys.dict.repository.DicEntityRepository;
import com.jrsoft.engine.sys.dict.service.DicEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
  @Description 
  @Author author
  @Create 2019-05-15 20:20:54
**/
@Service
@Transactional
public class DicEntityServiceImpl extends BaseServiceImpl<DicEntity, DicEntityRepository> implements DicEntityService {

    @Autowired
    private DicEntityRepository dicEntityRepository;

    @Override
    public DicEntity getByDicId(String dicId) {
        return dicEntityRepository.getByDicId(dicId);
    }
}
