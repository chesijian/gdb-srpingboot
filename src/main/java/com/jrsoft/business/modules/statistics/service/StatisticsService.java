package com.jrsoft.business.modules.statistics.service;

import com.jrsoft.business.modules.statistics.domain.ProjectRanking;
import com.jrsoft.business.modules.statistics.query.ProjApplicationQuery;
import com.jrsoft.engine.base.model.ReturnPageJson;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 统计服务service
 *
 * @author chesijian
 * @create 2019-11-13 15:42
 **/
public interface StatisticsService {
	ProjectRanking findByName(String name);


	Map<String, Object> getDistributionNum();

	/**
	 * 获取项目进度逾期率排行数据
	 * @param orderType
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	ReturnPageJson<List<ProjectRanking>> getProjProgressRanking(String orderType, int pageIndex, int pageSize, String classify);
	/**
	 * 获取项目进度排行数据
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	ReturnPageJson<List<ProjectRanking>> getProjProgressList(int pageIndex, int pageSize, String classify);
	ReturnPageJson<List<ProjectRanking>> getCheckRanking(String checkType, int pageIndex, int pageSize, String classify, String orderType);

	ReturnPageJson<List<Map<String,Object>>> getCheckItemRanking(String checkType, int pageIndex, int pageSize, String projUid, String areaUid, String checkItemUid, String status);

	/**
	 * 获取问题统计数据
	 * @param dateType
	 * @param projUid
	 * @return
	 */
	ReturnPageJson<List<Map<String,Object>>> getCheckNumRanking(String projUid, String dateType);

	Map<String, Object> getCheckCount(String checkType, String dateTime, String projUid);

	ReturnPageJson<List<Map<String, Object>>> getCheckStatusList(String projUid, String checkType, String areaUid, String checkItemUid, String status);

	/**
	 * 获取工序数量统计情况
	 * @param projUid
	 * @return
	 */
	Map<String, Object> getProcedureCount(String projUid);

	/**
	 * 获取实测实量数量统计情况
	 * @param projUid
	 * @return
	 */
	Map<String, Object> getMeasureCount(String projUid);

	Map<String,Object> getProgressChart(String projUid);

	/**
	 * 检查查询汇总表
	 * @param checkType
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	ReturnPageJson<List<Map<String,Object>>> getCheckNumCollect(String projUid, String checkType, int pageIndex, int pageSize, String startDate, String endDate);

	/**
	 * 实测实量查询汇总表
	 * @param projUid
	 * @param startDate
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	ReturnPageJson<List<Map<String,Object>>> getMeasureNumCollect(String projUid, String startDate, String endDate, int pageIndex, int pageSize);


	/**
	 * 集团大屏业态分析数据
	 * @param checkType
	 * @return
	 */
	List<Map<String,Object>> getYeTaiAnalyze(String checkType);

	List<Map<String,Object>> getProjMapDistribution();

	ReturnPageJson<List<Map<String,Object>>> getTaskCollect(String projUid, String taskStatus, int pageIndex, int pageSize, String search);

	/**
	 * 问题检查项分布报表
	 * @param checkType
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	ReturnPageJson<List<Map<String,Object>>> getCheckItemCollect(String checkType, int pageIndex, int pageSize);

	/**
	 * 问题楼栋分布报表
	 * @param checkType
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	ReturnPageJson<List<Map<String,Object>>> getAreaProblemCollect(String checkType, int pageIndex, int pageSize);

	ReturnPageJson<List<Map<String,Object>>> getProjAreaReport(String projUid, String checkType, String yeTai, int pageIndex, int pageSize);

	/**
	 * 获取应用统计数据
	 * @return
	 */
	Map<String,Object> getApplicationNums();

	ReturnPageJson<List<Map<String,Object>>> getApplicationInfo(String tableName, int pageIndex, int pageSize, String param);

    void exportApplicationInfo(HttpServletResponse response, String tableName, String param, String fileName);

	ReturnPageJson getProjStatistics(String tableName, int pageIndex, int pageSize, String param, String columnName, String projUid);

    ReturnPageJson getProjApplication(ProjApplicationQuery query);


}
