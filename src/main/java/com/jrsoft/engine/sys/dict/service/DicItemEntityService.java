package com.jrsoft.engine.sys.dict.service;

import com.jrsoft.engine.base.service.BaseService;
import com.jrsoft.engine.sys.dict.domin.DicItemEntity;

import java.util.List;

/**
  @Description 
  @Author author
  @Create 2019-05-15 20:20:54
**/
//extends BaseService<DicItemEntity>
public interface DicItemEntityService {
    List<DicItemEntity> findAllByParentId(String parentId);
    void deleteAllByParentId(String parentId);
}
