package com.jrsoft.business.modules.construction.dao;

import com.jrsoft.business.modules.construction.domain.CheckItem;
import com.jrsoft.engine.common.persistence.annotation.MyBatisDao;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


/**
 * mybatis
 *
 * @author Blueeyedboy
 * @create 2018-10-13 15:33
 **/
@MyBatisDao
public interface PollingDao {
	List<Map<String,Object>> findYearListByProjUid(@Param("projUid") String projUid);
	//Map findByName(@Param("name") String name);

	List<Map<String,Object>> getPollingDataList(@Param("projUid") String projUid, @Param("pollingType") String pollingType, @Param("yearVal") String yearVal,
                                                @Param("start") Integer start, @Param("end") Integer end, @Param("dbType") String dbType);

	/**
	 * 导入巡检任务与巡检项关系数据
	 * @param dataList
	 * @return
	 */
	public int insertCheckItemOfPolling(@Param("dataList") List<CheckItem> dataList);

	List<Map<String,Object>> getPollingInspectLists(@Param("pollingUid") String pollingUid, @Param("start") Integer start,
                                                    @Param("end") Integer end, @Param("dbType") String dbType);

	int deleteInspectItemByPollingUid(@Param("pollingUid") String pollingUid);

	List<Map<String, Object>> getInspectItemScoreTree(@Param("pollingUid") String pollingUid,
                                                      @Param("itemUid") String itemUid, @Param("dbType") String dbType);

	/**
	 * 获取巡检项得分情况
	 * @param itemUid
	 * @return
	 */
	Map<String, Object> getItemScore(@Param("pollingUid") String pollingUid, @Param("itemUid") String itemUid, @Param("dbType") String dbType);

	/**
	 * 批量删除检查项得分数据
	 * @author 车斯剑
	 * @date 2019年3月4日下午5:55:31
	 * @param dataList
	 * @return
	 */
	void batchDeleteCheckItemScore(@Param("dataList") List<String> dataList, @Param("pollingUid") String pollingUid);
}
