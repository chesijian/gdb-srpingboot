package com.jrsoft.engine.sys.dict.repository;

import com.jrsoft.engine.sys.dict.domin.DicItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

/**
  @Description 
  @Author author
  @Create 2019-05-15 20:20:54
**/
@NoRepositoryBean
public interface DicItemEntityRepository extends JpaRepository<DicItemEntity, String>, JpaSpecificationExecutor<DicItemEntity> {
    List<DicItemEntity> findAllByParentId(String parentId);
}
