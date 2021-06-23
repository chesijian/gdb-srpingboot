package com.jrsoft.business.modules.construction.service;

import com.jrsoft.business.modules.construction.domain.CheckItem;
import com.jrsoft.business.modules.construction.domain.Polling;
import com.jrsoft.engine.base.service.BaseService;


import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * 巡检服务service
 *
 * @author chesijian
 * @create 2019-2-13 15:42
 **/
public interface PollingService extends BaseService<Polling> {
	List<Map<String,Object>> findYearListByProjUid(String projUid);

	List<Map<String,Object>> getPollingDataList(String projUid, String pollingType, String yearVal, Integer start, Integer end);

	/**
	 * 导入巡检任务与巡检项关系数据
	 * @param list
	 */
	public void  insertCheckItemOfPolling(String pollingUid, List<CheckItem> list, String operate);

	/**
	 * 获取巡检任务关联的巡检项列表
	 * @param pollingUid
	 * @param start
	 * @param end
	 * @return
	 */
	List<Map<String,Object>> getPollingInspectLists(String pollingUid, Integer start, Integer end);

	List<Map<String, Object>> getInspectItemScoreTree(String pollingUid, String itemUid);
	Map<String, Object> getItemScore(String pollingUid, String itemUid);
}
