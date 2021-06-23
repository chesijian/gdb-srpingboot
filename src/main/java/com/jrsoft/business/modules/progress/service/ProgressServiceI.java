package com.jrsoft.business.modules.progress.service;

import java.util.List;
import java.util.Map;

import com.jrsoft.business.modules.progress.model.Schedule;
import com.jrsoft.business.modules.progress.model.Task;
import com.jrsoft.business.modules.progress.model.TaskAndRelevance;
import com.jrsoft.business.modules.progress.model.TaskDTO;
import com.jrsoft.business.modules.progress.query.ScheduleQuery;
import com.jrsoft.engine.base.model.ReturnPageJson;
import com.jrsoft.engine.base.service.BaseService;

public interface ProgressServiceI{
	
	/**
	 * 根据项目id查询进度列表
	 * @param projUid
	 * @param userUid
	 * @param companyUid
	 * @param search
	 * @param start
	 * @param end
	 * @param isCompanyAdmin
	 * @return
	 */
	List<Map<String, Object>> searchProgressList(String projUid, String userUid, String companyUid, String search,
			int start, int end, boolean isCompanyAdmin, String dbType);
	
	
	/**
	 * 进度变更版本
	 * @param id
	 */
	void updateProgressVersions(Schedule data, String userUid, boolean isCompanyAdmin, String companyUid, String dbType);

	
	/**
	 * 根据id获取数据
	 * @param id
	 * @return
	 */
	Map<String, Object> getProgressById(String id, String dbType);

	
	/**
	 * 根据id删除数据
	 * @param id
	 */
	void deleteByPrimaryKeys(String id);

	
	/**
	 * 根据id查询进度任务列表
	 * @param id
	 * @return
	 */
	Map<String, Object> getProgressTaskById(String id, String userUid, boolean isCompanyAdmin, String companyUid, String weChat);
	Map<String, Object> getProgressTaskByParent(String scheduleUid, String parentId);

	
	/**
	 * 根据id查询进度任务详情
	 * @param id
	 * @return
	 */
	Task getTaskDetailsById(String id);

	
	/**
	 * 根据id删除任务
	 * @param id
	 */
	void deleteTask(String id);

	
	/**
	 * 根据id删除任务之间关联线
	 * @param id
	 */
	void deleteRelevance(String id);

	
	/**
	 * 根据项目id更改进度禁用
	 * @param id
	 * @param projUid
	 */
	void updateScheduleStatus(String id, String projUid);

	
	/**
	 * 保存任务
	 * @param taskAndRelevance
	 * @return
	 */
	Map<String, Object> saveTaskAndRelevance(TaskAndRelevance taskAndRelevance);

	
	/**
	 * 导入进度任务
	 * @param fileId
	 * @param scheduleUid
	 */
	void importExcelTasks(String fileId, String scheduleUid, String projUid);

	
	/**
	 * 导入XML文件
	 * @param fileId
	 * @param scheduleUid
	 */
	void importXmlTasks(String fileId, String scheduleUid, String projUid);

	
	/**
	 * 过滤没有下级的进度任务
	 * @param string
	 * @return
	 */
	List<Task> getTaskFilterData(String id, String parent, String companyUid);

	
	/**
	 * 过滤自己新增的任务
	 * @param id
	 * @param parent
	 * @param companyUid
	 * @return
	 */
	List<Task> getTaskFilterData2(String id, String parent, String companyUid);

	
	/**
	 * 查询任务上级全名称
	 * @param parent
	 * @return
	 */
	Task searchTaskSuperiorName(String id, String parent, String companyUid);

	/**
	 * 根据id查询子任务列表
	 * @param projUid
	 * @param parentUid
	 * @return
	 */
	List<TaskDTO> getChildTasks(String projUid, String parentUid);
	
	
	void batchUpdateTask(String ids, String assignee,String approver,String startDate,String endDate,String assigneeName,String approverName);

	/**
	 * 根据id查询进度模板
	 * @param parentUid
	 * @return
	 */
	List<Map<String, Object>> getTemplates(String parentUid);

	void insertProgressTemplate(List<Task> list, String parentId,String scheduleUid);

	/**
	 * 更新父任务
	 */
	void updateParentTask(String parent);

	/**
	 * 过滤自己新增的任务
	 * @param scheduleUid
	 * @param pageSize
	 * @param pageIndex
	 * @return
	 */
	List<Task> getProgressTaskForApp(String scheduleUid,String userUid,int pageSize, int pageIndex, Integer type,String dataType,String search);

	/**
	 * 获取提醒信息
	 * @return
	 */
	List<Map<String, Object>> getRemindList();

	/**
	 * 获取租赁项目进度明细表
	 * @param query
	 * @return
	 */
    ReturnPageJson getReportForLease(ScheduleQuery query);

	/**
	 * 获取租赁项目进度汇总表
	 * @param query
	 * @return
	 */
	ReturnPageJson getReportForLeaseCollect(ScheduleQuery query);

	/**
	 * 更新任务进度状态
	 */
    void updateTaskStatus();
}
