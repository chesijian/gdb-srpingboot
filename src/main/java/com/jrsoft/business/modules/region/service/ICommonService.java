package com.jrsoft.business.modules.region.service;


import java.util.List;

import com.jrsoft.business.modules.progress.model.Iink;
import com.jrsoft.business.modules.region.model.AreaVO;
import com.jrsoft.engine.base.service.BaseService;

/**
 * 通用服务接口
 *
 * Created by zcf on 2018/10/25.
 */
public interface ICommonService{
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
