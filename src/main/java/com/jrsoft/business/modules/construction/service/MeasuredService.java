package com.jrsoft.business.modules.construction.service;


import com.jrsoft.engine.base.model.ReturnPageJson;


import java.util.List;
import java.util.Map;

public interface MeasuredService {
	/**
	 * 获取区域图纸树数据
	 * @param projUid
	 * @return
	 */
	public List<Map<String, Object>> getDrawingAreaTree(String projUid, String search, String parentUid,
                                                        int start, int end, String dbType);

	/**
	 * 获取日志劳动力分析统计图表数据
	 * @param projUid
	 * @return
	 */
	public List<Map<String, Object>> getWorkLogStatistics(String projUid, String statisticsDate);

	/**
	 * 获取日志劳动力分析统计详情
	 * @param projUid
	 * @param statisticsDate
	 * @return
	 */
	public List<Map<String, Object>> getWorkLogDetail(String projUid, String statisticsDate);

	/**
	 * 获取日志劳动力分析统计详情合计数据
	 * @param projUid
	 * @param statisticsDate
	 * @return
	 */
	public List<Map<String, Object>> getWorkLogSum(String projUid, String statisticsDate);

	/**
	 * 获取企业信息
	 * @param companyUid
	 * @return
	 */
	Map<String, Object> getCorpInfo(String companyUid);

	/**
	 * 获取编制计划列表
	 * @param companyUid
	 * @param projUid
	 * @return
	 */
	ReturnPageJson getPlanLists(String companyUid, String projUid, String search, String status, String type, int start, int end, String issueStatus);

	/**
	 * 删除编制计划
	 * @param ids
	 */
	void  deletePlans(String[] ids);

	/**
	 * 根据id获取计划编制数据
	 * @param id
	 * @return
	 */
	Map<String, Object> getPlanById(String id);

	/**
	 * 获取计划编制明细
	 * @param id
	 * @param isQueryPart
	 * @return
	 */
	List<Map<String, Object>> getPlanDetById(String id, Boolean isQueryPart, String partUid, String projUid);

}
