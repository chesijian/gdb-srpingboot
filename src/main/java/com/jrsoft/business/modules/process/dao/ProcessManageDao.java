package com.jrsoft.business.modules.process.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.jrsoft.business.modules.process.model.Area;
import com.jrsoft.engine.common.persistence.annotation.MyBatisDao;

@MyBatisDao
public interface ProcessManageDao {
	
	/**
	 * 查询第一层工序
	 * @param projUid
	 * @return
	 */
	List<Map<String, Object>> searchFirstProcess(@Param("projUid") String projUid, @Param("dbType") String dbType);
	
	
	/**
	 * 查询下级工序
	 * @param id
	 * @return
	 */
	List<Map<String, Object>> searchChildrenProcess(@Param("projUid") String projUid, @Param("parentUid")String parentUid,
			@Param("isCatalog")boolean isCatalog, @Param("dbType") String dbType);

	
	/**
	 * 根据工序id查询检查项
	 * @param processId
	 * @param userUid
	 * @param companyUid
	 * @param search
	 * @param start
	 * @param end
	 * @param isCompanyAdmin
	 * @return
	 */
	List<Map<String, Object>> searchInspectList(@Param("processId") String processId, @Param("userUid") String userUid, @Param("companyUid") String companyUid, @Param("search") String search,
			@Param("start") int start, @Param("end") int end, @Param("isCompanyAdmin") boolean isCompanyAdmin);

	
	/**
	 * 删除工序
	 * @param id
	 */
	void delete(@Param("id") String id);

	
	/**
	 * 查询排序最大值
	 * @param parentUid
	 * @return
	 */
	int searchMaxSort(@Param("parentUid") String parentUid);

	
	/**
	 * 根据id编辑工序
	 * @param id
	 * @return
	 */
	Map<String, Object> edit(@Param("id") String id);

	
	/**
	 * 根据id编辑检查项
	 * @param id
	 * @return
	 */
	Map<String, Object> editInspect(@Param("id") String id);

	
	/**
	 * 根据id删除检查项
	 * @param id
	 */
	void deleteInspect(@Param("id") String[] id);

	
	/**
	 * 查询检查项排序最大值
	 * @param parentUid
	 * @return
	 */
	int InspectMaxSort(@Param("parentUid") String parentUid);

	
	/**
	 * 根据项目id查询工序检验
	 * @param projUid
	 * @param userUid
	 * @param companyUid
	 * @param search
	 * @param start
	 * @param end
	 * @param isCompanyAdmin
	 * @return
	 */
	List<Map<String, Object>> searchProcessCheckout(@Param("projUid") String projUid, @Param("userUid") String userUid, @Param("companyUid") String companyUid, @Param("search") String search,
			@Param("start") int start, @Param("end") int end, @Param("isCompanyAdmin") boolean isCompanyAdmin);

	
	/**
	 * 编辑工序检验
	 * @param id
	 * @return
	 */
	Map<String, Object> editCheckoutById(@Param("id") String id, @Param("dbType") String dbType);

	
	/**
	 * 删除工序检验
	 * @param idss
	 */
	void deleteCheckoutByIds(@Param("idss") String[] idss);

	
	/**
	 * 查询工序检验编号最大值
	 * @param projUid
	 * @return
	 */
	int checkoutMaxSort(@Param("projUid") String projUid, @Param("dbType") String dbType);

	
	/**
	 * 根据区域查询检验批
	 * @param valueOf
	 * @return
	 */
	List<Map<String, Object>> getProcessCheckout(@Param("projUid") String projUid, @Param("id") String id);

	
	/**
	 * 根据项目id查询工序任务列表
	 * @param projUid
	 * @param userUid
	 * @param companyUid
	 * @param search
	 * @param start
	 * @param end
	 * @param isCompanyAdmin
	 * @param examineStatus
	 * @param constructionStatus
	 * @param partUid
	 * @param principalUid
	 * @return
	 */
	List<Map<String, Object>> searchProcessTaskList(@Param("projUid") String projUid, @Param("userUid") String userUid, 
			@Param("companyUid") String companyUid, @Param("search") String search,
			@Param("start") int start, @Param("end") int end, @Param("isCompanyAdmin") boolean isCompanyAdmin, 
			@Param("examineStatus") String examineStatus, @Param("constructionStatus") String constructionStatus, @Param("partUid") String partUid,
			@Param("principalUid") String principalUid);

	
	/**
	 * 查询工序任务的排序最大值
	 * @param projUid
	 * @return
	 */
	int searchTaskSortMax(@Param("projUid") String projUid, @Param("dbType") String dbType);

	
	/**
	 * 删除工序任务
	 * @param ids
	 */
	void deleteProcessTaskByIds(@Param("ids") String[] ids);

	
	/**
	 * 编辑工序任务
	 * @param id
	 * @return
	 */
	Map<String, Object> editProcessTaskById(@Param("id") String id, @Param("userUid") String userUid);

	
	/**
	 * 查询负责人
	 * @param principal
	 * @return
	 */
	List<Map<String, Object>> searchPersonnel(@Param("principal") String[] principal, @Param("projUid") String projUid);

	
	/**
	 * 根据项目id查询区域与工序任务
	 * @param projUid
	 * @param parentUid
	 * @return
	 */
	List<Map<String, Object>> searchPartAndProcessTask(@Param("projUid") String projUid, @Param("parentUid") String parentUid, @Param("dbType") String dbType);

	
	/**
	 * 根据区域id查询工序任务
	 * @param projUid
	 * @param parentUid
	 * @return
	 */
	List<Map<String, Object>> getProcessTaskByPartId(@Param("projUid") String projUid, @Param("parentUid") String parentUid, 
			@Param("userUid") String userUid, @Param("dbType") String dbType,@Param("search")String search);

	
	/**
	 * 根据工序id查询检查项
	 * @param processId	工序id
	 * @return
	 */
	List<Map<String, Object>> searchExamineNape(@Param("processId") String processId, @Param("taskId") String taskId,@Param("inspectType")Integer inspectType, @Param("dbType") String dbType);

	
	/**
	 * 查询任务数与工序数
	 * @param id
	 * @return
	 */
	List<Map<String, Object>> searchTaskCountAndProcessCount(@Param("id") String id, @Param("dbType") String dbType);

	
	/**
	 * 根据工序与区域查询工序任务
	 * @param processId
	 * @param partId
	 * @return
	 */
	List<Map<String, Object>> searchProcessTaskByProIdAndPartId(@Param("processId") String processId, 
			@Param("partId") String partId, @Param("projUid") String projUid, @Param("dbType") String dbType);

	
	/**
	 * 查询第二层区域
	 * @param id
	 * @return
	 */
	List<Map<String, Object>> searchAreaFirstFloor(@Param("id") String id, @Param("dbType") String dbType);

	
	/**
	 * 查询楼层状态
	 * @param id
	 * @return
	 */
	List<Map<String, Object>> searchAreaProcess(@Param("id") String id, @Param("dbType") String dbType);

	/**
	 * 查询数量统计
	 * @param projUid
	 * @return
	 */
	Map<String, Object> getStatisticsNumObj(@Param("projUid") String projUid,@Param("areaUid")String areaUid);

	
	/**
	 * 根据项目id查询顶层区域
	 * @param projUid
	 * @return
	 */
	List<Map<String, Object>> searchAreaTop(@Param("projUid") String projUid,@Param("parentUid")String parentUid);

	
	/**
	 * PC端施工率
	 * @param projUid
	 * @return
	 */
	List<Map<String, Object>> searchConstructionRateMonth(@Param("projUid") String projUid, 
			@Param("date") String date, @Param("dbType") String dbType);

	
	/**
	 * 更新检查项状态
	 * @param taskId
	 */
	void updateInspectStatus(@Param("taskId") String taskId);

	
	/**
	 * 更新工序任务状态
	 * @param taskId
	 */
	void updateTaskStatus(@Param("taskId") String taskId);

	
	/**
	 * 根据区域id查询图纸信息
	 * @param id
	 * @return
	 */
	Map<String, Object> searchDrawingInfoByAreaId(@Param("id") String id, @Param("dbType") String dbType);

	
	/**
	 * 根据工序查询检查项
	 * @param parentUid
	 * @return
	 */
	List<Map<String, Object>> searchInspectLists(@Param("parentUid") String parentUid);

	
	/**
	 * 根据项目id查询检验批名称是否重复
	 * @param projUid
	 * @param name
	 * @return
	 */
	Map<String, Object> checkInspection(@Param("projUid") String projUid, @Param("name") String name);

	
	/**
	 * 查询问题数
	 * @param projUid
	 * @return
	 */
	Integer searchIssueCount(@Param("projUid") String projUid);

	
	/**
	 * 查询整改
	 * @param projUid
	 * @return
	 */
	List<Map<String, Object>> searchabarbeitungCount(@Param("projUid") String projUid, 
			@Param("date") String date, @Param("dbType") String dbType);

	/**
	 * 根据年份查询月份
	 * @param projUid
	 * @param date
	 * @return
	 */
	List<Map<String, Object>> searchConstructionRateYear(@Param("projUid") String projUid, 
			@Param("date") String date, @Param("dbType") String dbType);

	
	/**
	 * 根据检查项ids更新检查项状态
	 * @param idss
	 * @param type
	 */
	void updateInspectStatusByInspectid(@Param("id") String id, @Param("type") String type, @Param("taskId") String taskId);

	
	/**
	 * 查询检查项明细是否有记录存在
	 * @param taskId
	 * @return
	 */
	Integer searchProcessDetails(@Param("taskId") String taskId, @Param("id") String id);

	
	/**
	 * 根据项目id查询工序检验(mySql)
	 * @param projUid
	 * @param userUid
	 * @param companyUid
	 * @param search
	 * @param start
	 * @param end
	 * @param isCompanyAdmin
	 * @return
	 */
	List<Map<String, Object>> searchProcessCheckoutMySql(@Param("projUid") String projUid, @Param("userUid") String userUid, @Param("companyUid") String companyUid, @Param("search") String search,
			@Param("start") int start, @Param("end") int end, @Param("isCompanyAdmin") boolean isCompanyAdmin);
	
	
	/**
	 * 根据工序id查询检查项(MySQL)
	 * @param processId
	 * @param userUid
	 * @param companyUid
	 * @param search
	 * @param start
	 * @param end
	 * @param isCompanyAdmin
	 * @return
	 */
	List<Map<String, Object>> searchInspectListMySql(@Param("processId") String processId, @Param("userUid") String userUid, @Param("projUid") String projUid, @Param("search") String search,
			@Param("start") int start, @Param("end") int end, @Param("isCompanyAdmin") boolean isCompanyAdmin);

	
	
	/**
	 * 根据项目id查询工序任务列表(Mysql)
	 * @param projUid
	 * @param userUid
	 * @param companyUid
	 * @param search
	 * @param start
	 * @param end
	 * @param isCompanyAdmin
	 * @param examineStatus
	 * @param constructionStatus
	 * @param partUid
	 * @param principalUid
	 * @return
	 */
	List<Map<String, Object>> searchProcessTaskListMySql(@Param("projUid") String projUid, @Param("userUid") String userUid, 
			@Param("companyUid") String companyUid, @Param("search") String search,
			@Param("start") int start, @Param("end") int end, @Param("isCompanyAdmin") boolean isCompanyAdmin, 
			@Param("examineStatus") String examineStatus, @Param("constructionStatus") String constructionStatus, @Param("partUid") String partUid,
			@Param("principalUid") String principalUid);

	
	
	/**
	 * 编辑工序任务
	 * @param id
	 * @return
	 */
	Map<String, Object> editProcessTaskByIdMySql(@Param("id") String id, @Param("userUid") String userUid);

	
	/**
	 * 导出
	 * @param companyUid
	 * @param status
	 * @param startDate
	 * @param endDate
	 * @param search
	 * @return
	 */
	List<Map<String, Object>> export(@Param("companyUid") String companyUid, @Param("projUid") String projUid, @Param("status") Integer status, 
			@Param("areaUid") String areaUid, @Param("abarbeitungUid") String abarbeitungUid,
			@Param("search") String search, @Param("dbType") String dbType);

	
	/**
	 * 获取检查项目录数据
	 * @param companyUid
	 * @param parentUid
	 * @param formType
	 * @param projUid
	 * @return
	 */
	public List<Map<String,Object>> getCheckItemCatalog(@Param("companyUid") String companyUid, @Param("parentUid") String parentUid,@Param("formType")String formType,@Param("projUid")String projUid);

	
}
