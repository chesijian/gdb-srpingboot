package com.jrsoft.business.modules.construction.dao;

import com.jrsoft.business.modules.construction.domain.FileCatalog;
import com.jrsoft.engine.common.persistence.annotation.MyBatisDao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;


/**
 * mybatis
 *
 * @author Blueeyedboy
 * @create 2018-10-13 15:33
 **/
@MyBatisDao
public interface BusinessDao {

	List<Map<String, Object>> getPurchaseList(@Param("projUid") String projUid);

	List<Map<String, Object>> getPurchaseDet(@Param("orderCode") String orderCode, @Param("search") String search, @Param("start") int start, @Param("end") int end);

	void batchInsertCatalog(@Param("dataList") List<FileCatalog> list);

	List<FileCatalog> getHistoryFileCatalog(FileCatalog item);

	/**
	 * 获取父级
	 * @param item
	 * @return
	 */
    FileCatalog getParentFileCatalog(FileCatalog item);

    ;
}
