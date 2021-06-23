package com.jrsoft.business.modules.construction.dao;


import com.jrsoft.engine.common.persistence.annotation.MyBatisDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


@MyBatisDao
public interface MeasuredDao {

	/**
	 * 获取区域图纸树数据
	 * @author 车斯剑
	 * @date 2019年2月18日下午7:02:20
	 * @return
	 */
	public List<Map<String, Object>> getDrawingAreaTree(@Param("projUid") String projUid, @Param("search") String search, @Param("parentUid") String parentUid,
                                                        @Param("start") int start, @Param("end") int end, @Param("dbType") String dbType);
	/**
	 * 获取日志劳动力分析统计图表数据
	 * @author 车斯剑
	 * @date 2019年2月18日下午7:02:20
	 * @return
	 */
	public List<Map<String, Object>> getWorkLogStatistics(@Param("projUid") String projUid, @Param("statisticsDate") String statisticsDate, @Param("dbType") String dbType);

	/**
	 * 获取日志劳动力分析统计详情
	 * @author 车斯剑
	 * @date 2019年2月19日下午5:55:31
	 * @param projUid
	 * @return
	 */
	public List<Map<String, Object>> getWorkLogDetail(@Param("projUid") String projUid,
                                                      @Param("statisticsDate") String statisticsDate, @Param("dbType") String dbType);

	/**
	 * 获取日志劳动力分析统计详情合计数据
	 * @author 车斯剑
	 * @date 2019年2月20日下午2:27:18
	 * @param projUid
	 * @return
	 */
	public List<Map<String, Object>> getWorkLogSum(@Param("projUid") String projUid, @Param("statisticsDate") String statisticsDate, @Param("dbType") String dbType);



	Map<String, Object> getCorpInfo(@Param("companyUid") String companyUid, @Param("dbType") String dbType);

	/**
	 * 获取编制计划列表
	 * @param companyUid
	 * @param projUid
	 * @param search
	 * @param status
	 * @param type
	 * @param start
	 * @param end
	 * @return
	 */
	List<Map<String, Object>> getPlanLists(@Param("companyUid") String companyUid, @Param("projUid") String projUid, @Param("search") String search, @Param("status") String status,
                                           @Param("issueStatus") String issueStatus, @Param("type") String type, @Param("start") int start, @Param("end") int end, @Param("dbType") String dbType);

	/**
	 * 获取编制计划列表总数
	 * @param companyUid
	 * @param projUid
	 * @param search
	 * @param status
	 * @param issueStatus
	 * @param type
	 * @param dbType
	 * @return
	 */
	int getPlansTotal(@Param("companyUid") String companyUid, @Param("projUid") String projUid, @Param("search") String search, @Param("status") String status,
                      @Param("issueStatus") String issueStatus, @Param("type") String type, @Param("dbType") String dbType);

	/**
	 * 删除编制计划
	 * @param ids
	 */
	void deletePlans(@Param("ids") String[] ids);
	/**
	 * 删除编制计划明细
	 * @param ids
	 */
	void deletePlansDet(@Param("ids") String[] ids);

	/**
	 * 根据id获取计划编制数据
	 * @param id
	 * @return
	 */
	Map<String, Object> getPlanById(@Param("id") String id, @Param("dbType") String dbType);

	/**
	 * 获取计划编制明细
	 * @param parentId
	 * @param isQueryPart
	 * @return
	 */
	List<Map<String, Object>> queryPlanDet(@Param("parentId") String parentId, @Param("isQueryPart") Boolean isQueryPart,
                                           @Param("partUid") String partUid, @Param("projUid") String projUid);

}
