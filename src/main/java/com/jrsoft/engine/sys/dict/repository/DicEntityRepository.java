package com.jrsoft.engine.sys.dict.repository;

import com.jrsoft.engine.sys.dict.domin.DicEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;

/**
  @Description 
  @Author author
  @Create 2019-05-15 20:20:54
**/
//extends JpaRepository<DicEntity, String>, JpaSpecificationExecutor<DicEntity>
public interface DicEntityRepository extends JpaRepository<DicEntity, Integer> {
    DicEntity getByDicId(String dicId);
}
