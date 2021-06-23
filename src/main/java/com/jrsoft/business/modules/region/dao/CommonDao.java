package com.jrsoft.business.modules.region.dao;

import com.jrsoft.business.modules.region.model.AreaVO;
import com.jrsoft.engine.common.persistence.annotation.MyBatisDao;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by zcf on 2018/10/25.
 */
@Repository
@MyBatisDao
public interface CommonDao {

    /**
     * 获取省信息
     * @return
     */
    public List<AreaVO> getProvinceList();

    /**
     * 根据父节点ID获取市信息
     * @param parentId
     * @return
     */
    public List<AreaVO> getCityByParentId(int parentId);
    /**
     * 根据父节点ID获取县信息
     * @param parentId
     * @return
     */
    public List<AreaVO> getCountyByParentId(int parentId);
}
