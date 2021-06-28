package com.jrsoft.engine.sys.dict.service;

import com.jrsoft.engine.base.service.BaseService;
import com.jrsoft.engine.sys.dict.domin.DicEntity;

/**
  @Description 
  @Author author
  @Create 2019-05-15 20:20:54
**/
public interface DicEntityService extends BaseService<DicEntity> {
    DicEntity getByDicId(String dicId);
}
