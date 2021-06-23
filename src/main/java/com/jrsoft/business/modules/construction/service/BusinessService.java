package com.jrsoft.business.modules.construction.service;

import com.jrsoft.business.modules.construction.domain.FileCatalog;
import com.jrsoft.engine.base.service.BaseService;


import java.util.List;
import java.util.Map;

/**
 * 企业服务service
 *
 * @author Blueeyedboy
 * @create 2018-10-13 15:42
 **/
public interface BusinessService {

    List<Map<String, Object>> getPurchaseList(String projUid);
    List<Map<String, Object>> getPurchaseDet(String orderCode, String search, int start, int end);

    /**
     * 导入文档目录模板
     * @param list
     * @param parentId
     */
    void importCatalogTemplate(List<FileCatalog> list, String parentId);
}
