package com.jrsoft.business.modules.statistics.dao;

import com.jrsoft.business.modules.statistics.domain.ProjectRanking;
import com.jrsoft.business.modules.statistics.query.ProjApplicationQuery;
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
public interface StatisticsDao {
	ProjectRanking findByName(@Param("name") String name);
	List<Map<String, Object>> getDistributionNum(@Param("companyUid") String companyUid);
	List<Map<String, Object>> getclassifyNumList(@Param("companyUid") String companyUid, @Param("classify") String classify);

	List<ProjectRanking> getProjProgressRanking(@Param("companyUid") String companyUid, @Param("start") int start, @Param("pageSize") int pageSize,
                                                @Param("classify") String classify, @Param("orderType") String orderType);
	List<ProjectRanking> getProjProgressList(@Param("companyUid") String companyUid, @Param("start") int start, @Param("pageSize") int pageSize, @Param("classify") String classify);
	List<ProjectRanking> getCheckRanking(@Param("companyUid") String companyUid, @Param("checkType") String checkType, @Param("start") int start,
                                         @Param("pageSize") int pageSize, @Param("classify") String classify, @Param("orderType") String orderType);

	List<Map<String, Object>> getCheckItemRanking(@Param("companyUid") String companyUid, @Param("checkType") String checkType,
                                                  @Param("start") int start, @Param("pageSize") int pageSize, @Param("projUid") String projUid,
                                                  @Param("areaUid") String areaUid, @Param("checkItemUid") String checkItemUid, @Param("status") String status);

	/**
	 * 获取问题统计数据
	 * @param projUid
	 * @param dateType
	 * @return
	 */
	List<Map<String, Object>> getCheckNumRanking(@Param("projUid") String projUid, @Param("dateTime") String dateType);

	/**
	 * 获取项目总数
	 * @param companyUid
	 * @return
	 */
	int getProjectTotalCount(@Param("companyUid") String companyUid, @Param("classify") String classify);
	/**
	 * 获取检查项分布总数
	 * @param companyUid
	 * @return
	 */
	int getCheckItemRankingTotalCount(@Param("companyUid") String companyUid, @Param("checkType") String checkType, @Param("projUid") String projUid,
                                      @Param("areaUid") String areaUid, @Param("checkItemUid") String checkItemUid, @Param("status") String status);

	Map<String, Object> getCheckCount(@Param("checkType") String checkType, @Param("dateTime") String dateTime, @Param("projUid") String projUid);

	/**
	 * 获取楼栋总览状态分类数据
	 * @param projUid
	 * @param checkType
	 * @return
	 */
	List<Map<String, Object>> getCheckStatusList(@Param("projUid") String projUid, @Param("checkType") String checkType,
                                                 @Param("areaUid") String areaUid, @Param("checkItemUid") String checkItemUid, @Param("status") String status);

	int getCheckStatusCount(@Param("projUid") String projUid, @Param("checkType") String checkType,
                            @Param("areaUid") String areaUid, @Param("checkItemUid") String checkItemUid, @Param("status") String status);

	Map<String, Object> getProcedureCount(@Param("projUid") String projUid);

	Map<String, Object> getMeasureCount(@Param("projUid") String projUid);

	List<Map<String, Integer>> getProgressPlanNums(@Param("projUid") String projUid);
	List<Map<String, Integer>> getProgressActNums(@Param("projUid") String projUid);

	List<Map<String, Object>> getCheckNumCollect(@Param("companyUid") String companyUid, @Param("projUid") String projUid, @Param("checkType") String checkType,
                                                 @Param("start") int start, @Param("pageSize") int pageSize, @Param("startDate") String startDate, @Param("endDate") String endDate);
	int getCheckNumCollectTotalCount(@Param("companyUid") String companyUid, @Param("projUid") String projUid, @Param("checkType") String checkType,
                                     @Param("startDate") String startDate, @Param("endDate") String endDate);

	List<Map<String, Object>> getMeasureNumCollect(@Param("companyUid") String companyUid, @Param("projUid") String projUid, @Param("startDate") String startDate,
                                                   @Param("endDate") String endDate, @Param("start") int start, @Param("pageSize") int pageSize);
	int getMeasureNumCollectTotalCount(@Param("companyUid") String companyUid, @Param("projUid") String projUid, @Param("startDate") String startDate, @Param("endDate") String endDate);

	/**
	 * 集团大屏业态分析数据
	 * @param companyUid
	 * @param start
	 * @param checkType
	 * @return
	 */
	List<Map<String,Object>> getYeTaiAnalyze(@Param("companyUid") String companyUid, @Param("checkType") String checkType);

	List<Map<String,Object>> getProjMapDistribution(@Param("companyUid") String companyUid);

	/**
	 * 会议任务查询汇总表
	 * @param projUid
	 * @param taskStatus
	 * @param start
	 * @param pageSize
	 * @return
	 */
	List<Map<String, Object>> getTaskCollect(@Param("companyUid") String companyUid, @Param("projUid") String projUid, @Param("taskStatus") String taskStatus,
                                             @Param("start") int start, @Param("pageSize") int pageSize, @Param("search") String search);
	int getTaskCollectTotalCount(@Param("companyUid") String companyUid, @Param("projUid") String projUid, @Param("taskStatus") String taskStatus, @Param("search") String search);

	/**
	 * 问题检查项分布报表
	 * @param checkType
	 * @param start
	 * @param pageSize
	 * @return
	 */
	List<Map<String, Object>> getCheckItemCollect(@Param("companyUid") String companyUid, @Param("checkType") String checkType, @Param("start") int start, @Param("pageSize") int pageSize);
	int getCheckItemCollectTotalCount(@Param("companyUid") String companyUid, @Param("checkType") String checkType);

	/**
	 * 问题楼栋分布报表
	 * @param checkType
	 * @param start
	 * @param pageSize
	 * @return
	 */
	List<Map<String, Object>> getAreaProblemCollect(@Param("companyUid") String companyUid, @Param("checkType") String checkType, @Param("start") int start, @Param("pageSize") int pageSize);

	/**
	 * 项目区域报表
	 * @param projUid
	 * @param checkType
	 * @param yeTai
	 * @param start
	 * @param pageSize
	 * @return
	 */
	List<Map<String, Object>> getProjAreaReport(@Param("projUid") String projUid, @Param("checkType") String checkType, @Param("yeTai") String yeTai, @Param("start") int start, @Param("pageSize") int pageSize);

	Map<String,Object> getApplicationNums(@Param("companyUid") String companyUid);

    List<Map<String,Object>> getApplicationInfo(@Param("companyUid") String companyUid, @Param("tableName") String tableName,
                                                @Param("start") int start, @Param("pageSize") int pageSize, @Param("param") String param);

	List<Map<String,Object>> getProgressPlanInfo(@Param("companyUid") String companyUid, @Param("start") int start, @Param("pageSize") int pageSize);

	List<Map<String,Object>> getInspectionPlanInfo(@Param("companyUid") String companyUid, @Param("start") int start,
                                                   @Param("pageSize") int pageSize, @Param("param") String param);


    List<Map<String,Object>> getTaskRecord(@Param("companyUid") String companyUid, @Param("param") String param);

	List<Map<String,Object>> getProjStatistics(@Param("companyUid") String companyUid, @Param("tableName") String tableName, @Param("columnName") String columnName,
                                               @Param("start") int start, @Param("pageSize") int pageSize, @Param("param") String param, @Param("projUid") String projUid);

	int getProjStatisticsTotalCount(@Param("companyUid") String companyUid, @Param("tableName") String tableName, @Param("param") String param, @Param("projUid") String projUid);

    List<Map<String,Object>> getProjApplication(ProjApplicationQuery query);
}
