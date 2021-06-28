package com.jrsoft.engine.sys.dict.repository;

import com.jrsoft.engine.sys.dict.domin.DicEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
  @Description 
  @Author author
  @Create 2019-05-15 20:20:54
**/
public interface DicEntityRepository extends JpaRepository<DicEntity, String>, JpaSpecificationExecutor<DicEntity> {
    DicEntity getByDicId(String dicId);
}
