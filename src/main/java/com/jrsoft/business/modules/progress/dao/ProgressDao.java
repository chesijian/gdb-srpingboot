package com.jrsoft.business.modules.progress.dao;

import java.util.List;
import java.util.Map;

import com.jrsoft.business.modules.progress.model.*;
import com.jrsoft.business.modules.progress.query.ScheduleQuery;
import org.apache.ibatis.annotations.Param;

import com.jrsoft.engine.common.persistence.annotation.MyBatisDao;

@MyBatisDao
public interface ProgressDao {

	List<Map<String, Object>> searchProgressList(@Param("projUid")String projUid, @Param("userUid") String userUid, @Param("companyUid") String companyUid, @Param("search") String search,
			@Param("start") int start, @Param("end") int end, @Param("isCompanyAdmin") boolean isCompanyAdmin);

	
	void updateProgressVersions(@Param("id") String id, @Param("versions") String versions);


	Map<String, Object> getProgressById(@Param("id") String id, @Param("dbType") String dbType);


	void deleteByPrimaryKey(@Param("id") String id);

	
	/**
	 * 查询进度任务
	 * @param id
	 * @return
	 */
	List<Task> getProgressTaskById(@Param("id") String id, @Param("userUid") String userUid, @Param("isCompanyAdmin") boolean isCompanyAdmin, 
			@Param("companyUid") String companyUid, @Param("weChat") String weChat, @Param("dbType") String dbType);

	
	/**
	 * 查询任务之间的关系线
	 * @param id
	 * @return
	 */
	List<Iink> getProgressRelevancekById(@Param("id") String id, @Param("dbType") String dbType);

	/**
	 * 车斯剑
	 * 保存进度
	 * @param data
	 */
	void saveSchedule(Schedule data);

	
	/**
	 * 根据id查询进度任务详情
	 * @param id
	 * @return
	 */
	Task getTaskDetailsById(@Param("id") String id,@Param("dbType")String dbType);

	
	/**
	 * 根据id删除任务
	 * @param id
	 */
	void deleteTask(@Param("id") String id);

	
	/**
	 * 根据id删除任务之间关联线
	 * @param id
	 */
	void deleteRelevance(@Param("id") String id);

	
	/**
	 * 根据项目id更改进度禁用
	 * @param id
	 * @param projUid
	 */
	void updateScheduleStatus(@Param("id") String id, @Param("projUid") String projUid);

	
	/**
	 * 根据进度id删除任务
	 * @param id
	 */
	void deleteTaskById(@Param("id") String id);

	
	/**
	 * 过滤没有下级的进度任务
	 * @param id
	 * @param parent
	 * @return
	 */
	List<Task> getTaskFilterData(@Param("id") String id, @Param("parent") String parent, @Param("companyUid") String companyUid);

	
	/**
	 * 过滤新建的没有下级的进度任务
	 * @param id
	 * @param parent
	 * @param companyUid
	 * @return
	 */
	List<Task> getTaskFilterData2(@Param("id") String id, @Param("parent") String parent, @Param("companyUid") String companyUid);

	
	/**
	 * 查询任务上级全名称
	 * @param id
	 * @param parent
	 * @param companyUid
	 * @return
	 */
	Task searchTaskSuperiorName(@Param("id") String id, @Param("parent") String parent, @Param("companyUid") String companyUid);

	
	List<Map<String, Object>> searchProgressListMySql(@Param("projUid")String projUid, @Param("userUid") String userUid, @Param("companyUid") String companyUid, @Param("search") String search,
			@Param("start") int start, @Param("end") int end, @Param("isCompanyAdmin") boolean isCompanyAdmin);

	
	/**
	 * 查询任务上报记录
	 * @param id
	 * @return
	 */
	List<Map<String, Object>> searchTaskRecord(@Param("id") String id);

	/**
	 * 根据id查询子任务列表
	 * @param scheduleUid
	 * @param parentUid
	 * @return
	 */
	List<TaskDTO> getChildTasks(@Param("scheduleUid")String scheduleUid, @Param("parentUid")String parentUid);

	void batchUpdateTask(@Param("ids")String[] ids, @Param("assignee")String assignee,@Param("approver")String approver,
						 @Param("startDate")String startDate,@Param("endDate")String endDate,@Param("assigneeName")String assigneeName,@Param("approverName")String approverName);

	/**
	 * 根据id查询进度模板
	 * @param parentUid
	 * @return
	 */
	List<Map<String, Object>> getTemplates(@Param("parentUid")String parentUid);

	/**
	 * 获取启用的进度计划模板
	 * @return
	 */
	Map<String, Object> getTempSchedule();
	List<Task> getTemplatesList(@Param("scheduleUid")String scheduleUid);

	List<Task> getProgressTaskList(@Param("scheduleUid")String scheduleUid);

	List<Task> getChildProgressTaskList(@Param("parent")String parent);

	void updateTask(Task task);
	void updateParentTask(Task task);

	List<Task> getProgressTaskForApp(@Param("scheduleUid")String scheduleUid,@Param("userUid")String userUid,@Param("start")int start,
									 @Param("pageSize")int pageSize, @Param("type")Integer type,@Param("dataType")String dataType,@Param("search")String search);

	void insertTaskByBatch(@Param("taskList")List<Task> taskList);

	void batchUpdateParentTask(@Param("taskCode")String taskCode);
	/**
	 * 获取提醒
	 * @return
	 */
	List<Map<String, Object>> getRemindList();

	/**
	 * 获取租赁项目进度明细表
	 * @param query
	 * @return
	 */
	List<LeaseReport> getReportForLease(ScheduleQuery query);

	int getReportForLeaseTotalCount(ScheduleQuery query);

	List<Map<String,Object>> getReportForLeaseCollect(ScheduleQuery query);

	int getLeaseCollectReportForTotalCount(ScheduleQuery query);

    void updateTaskStatus();

	/**
	 * 获取进度明细(不分组)
	 * @param query
	 * @return
	 */
	List<Task> getDetsForProgress(ScheduleQuery query);
}
