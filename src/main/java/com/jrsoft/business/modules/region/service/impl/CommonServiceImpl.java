package com.jrsoft.business.modules.region.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jrsoft.business.modules.region.dao.CommonDao;
import com.jrsoft.business.modules.region.model.AreaVO;
import com.jrsoft.business.modules.region.service.ICommonService;

/**
 * 通用服务实现类
 * Created by zcf on 2018/10/25.
 */
@Service
@Primary
@Transactional
public class CommonServiceImpl implements ICommonService {
    @Autowired
    private CommonDao commonDao;

    /**
     * 获取省信息
     * @return
     */
    @Override
    public List<AreaVO> getProvinceList() {
        return commonDao.getProvinceList();
    }

    /**
     * 根据父节点ID获取市信息
     * @param parentId
     * @return
     */
    @Override
    public List<AreaVO> getCityByParentId(int parentId) {
        return commonDao.getCityByParentId(parentId);
    }

    /**
     * 根据父节点ID获取县信息
     * @param parentId
     * @return
     */
    @Override
    public List<AreaVO> getCountyByParentId(int parentId) {
        return commonDao.getCountyByParentId(parentId);
    }
}
