package com.jrsoft.business.modules.construction.domain;

import com.jrsoft.engine.base.domain.BaseEntity;
import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.util.List;

/**
 * Created by chesijian on 2018/10/27.
 */
@Data
@Alias("FileCatalog")
public class FileCatalog extends BaseEntity {
    String id;
    String code;
    String label;
    String parentUid;
    String projUid;
    String tempUid;
    String projectType;
    String yeTai;
    Integer sort;



}
