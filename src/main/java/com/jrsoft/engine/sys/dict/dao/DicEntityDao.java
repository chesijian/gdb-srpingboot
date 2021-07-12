package com.jrsoft.engine.sys.dict.dao;

import com.jrsoft.engine.sys.dict.domin.DicEntity;
import org.apache.ibatis.annotations.Param;

/**
 * Created by chesijian on 2021/7/12.
 */
public interface DicEntityDao {

    DicEntity getByDicId(@Param("dicId") String dicId);
}
