package com.jrsoft.business.modules.process.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.jrsoft.business.modules.process.model.Area;
import com.jrsoft.business.modules.process.model.ProcessCheckout;
import com.jrsoft.business.modules.process.model.ProcessCheckoutList;
import com.jrsoft.business.modules.process.model.ProcessTaskList;
import com.jrsoft.engine.base.service.BaseService;

public interface ProcessManageServiceI {
	
	/**
	 * 根据项目id查询工序
	 * @param projUid
	 * @param userUid
	 * @param companyUid
	 * @param search
	 * @param start
	 * @param end
	 * @param isCompanyAdmin
	 * @return
	 */
	List<Map<String, Object>> searchProcessList(String projUid, String userUid, String companyUid, String search,
			boolean isCompanyAdmin, String dbType, String formType);
	
	
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
	List<Map<String, Object>> searchInspectList(String processId, String userUid, String projUid, String search,
			int start, int end, boolean isCompanyAdmin, String dbType);

	
	/**
	 * 删除工序
	 * @param id
	 */
	void delete(String id);

	
	/**
	 * 查询排序最大值
	 * @param parentUid
	 */
	int searchMaxSort(String parentUid);

	
	/**
	 * 根据id编辑工序
	 * @param id
	 * @return
	 */
	Map<String, Object> edit(String id);

	
	/**
	 * 根据id编辑检查项
	 * @param id
	 * @return
	 */
	Map<String, Object> editInspect(String id);

	
	/**
	 * 根据id查询检查项
	 * @param id
	 */
	void deleteInspect(String[] id);

	
	/**
	 * 查询检查项排序最大值
	 * @param parentUid
	 * @return
	 */
	int InspectMaxSort(String parentUid);

	
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
	List<Map<String, Object>> searchProcessCheckout(String projUid, String userUid, String companyUid, String search,
			int start, int end, boolean isCompanyAdmin, String dbType);

	
	/**
	 * 编辑工序检验
	 * @param id
	 * @return
	 */
	Map<String, Object> editCheckoutById(String id);

	
	/**
	 * 删除工序检验
	 * @param idss
	 */
	void deleteCheckoutByIds(String[] idss);

	
	/**
	 * 查询工序检验编号最大值
	 * @param projUid
	 * @return
	 */
	int checkoutMaxSort(String projUid, String dbType);

	
	/**
	 * 保存工序检验批
	 * @param data
	 */
	void saveProcessCheckout(ProcessCheckoutList data, int max);

	
	/**
	 * 新增工序任务查询检验批树
	 * @param projUid
	 * @return
	 */
	Map<String, Object> processTaskTree(String projUid, String companyUid,String parentUid,String purpose);

	
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
	List<Map<String, Object>> searchProcessTaskList(String projUid, String userUid, String companyUid, String search,
			int start, int end, boolean isCompanyAdmin, String examineStatus, String constructionStatus, String partUid,
			String principalUid, String dbType);

	
	/**
	 * 保存工序任务
	 * @param data
	 */
	void saveProcessTask(ProcessTaskList data, int max);

	
	/**
	 * 删除工序任务
	 * @param idss
	 */
	void deleteProcessTaskByIds(String[] ids);


	int searchTaskSortMax(String projUid, String dbType);

	
	/**
	 * 编辑工序任务
	 * @param id
	 * @return
	 */
	Map<String, Object> editProcessTaskById(String id, String dbType);

	
	/**
	 * 查询工序任务人员
	 * @param principal	负责人
	 * @param inspect	检查人
	 * @param spot		抽查人员
	 * @return
	 */
	List<Map<String, Object>> searchPersonnel(String principal, String projUid);

	
	/**
	 * 根据项目id查询区域与工序任务
	 * @param projUid
	 * @param parentUid
	 * @return
	 */
	Map<String, Object> searchPartAndProcessTask(String projUid, String parentUid,String search);

	
	/**
	 * 根据工序id查询检查项
	 * @param processId	工序id
	 * @return
	 */
	List<Map<String, Object>> searchExamineNape(String processId, String taskId,Integer inspectType);
	

	/**
	 * 根据工序与区域查询工序任务
	 * @param processId
	 * @param partId
	 * @return
	 */
	List<Map<String, Object>> searchProcessTaskByProIdAndPartId(String processId, String partId, String projUid);

	
	/**
	 * 查询第二层区域
	 * @param id
	 * @return
	 */
	List<Map<String, Object>> searchAreaFirstFloor(String id, String dbType);

	
	/**
	 * 查询楼层状态
	 * @param valueOf
	 * @return
	 */
	List<Map<String, Object>> searchAreaProcess(String valueOf, String dbType);

	/**
	 * 查询数量统计
	 * @param projUid
	 * @return
	 */
	Map<String, Object> getStatisticsNumObj(String projUid,String areaUid);

	
	/**
	 * 根据项目id查询首页信息
	 * @param projUid
	 * @return
	 */
	Map<String, Object> searchHomeMessageByProjUid(String projUid, String date, String dbType);

	
	/**
	 * 根据项目id查询顶层区域
	 * @param projUid
	 * @return
	 */
	List<Map<String, Object>> searchAreaTop(String projUid,String parentUid);

	
	/**
	 * 更新检查项状态
	 * @param taskId
	 */
	void updateInspectStatus(String taskId);

	
	/**
	 * 根据区域id查询图纸信息
	 * @param id
	 * @return
	 */
	Map<String, Object> searchDrawingInfoByAreaId(String id);

	
	/**
	 * 微信端根据项目id查询工序
	 * @param projUid
	 * @param userUid
	 * @param companyUid
	 * @param search
	 * @param isCompanyAdmin
	 * @return
	 */
	List<Map<String, Object>> searchWeChatProcessList(String projUid, String parentUid, String search);

	
	/**
	 * 根据项目id查询检验批名称是否重复
	 * @param projUid
	 * @return
	 */
	Map<String, Object> checkInspection(String projUid, String[] names);

	
	/**
	 * 查询检查项明细是否有记录存在
	 * @param taskId
	 * @return
	 */
	void searchProcessDetails(String taskId, String ids, String type);

	
	/**
	 * 导出
	 * @param companyUid
	 * @param status
	 * @param startDate
	 * @param endDate
	 * @param search
	 * @param response
	 */
	void export(String companyUid, String projUid, String projName, Integer status, String areaUid, String abarbeitungUid, 
			String search, HttpServletResponse response);

	
}
