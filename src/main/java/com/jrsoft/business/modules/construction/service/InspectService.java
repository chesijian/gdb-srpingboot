package com.jrsoft.business.modules.construction.service;

import com.jrsoft.business.modules.construction.domain.CheckItem;
import com.jrsoft.business.modules.construction.domain.CheckRecord;
import com.jrsoft.engine.base.model.ReturnPageJson;


import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface InspectService{
	/**
	 * 获取检查项列表数据
	 * @param companyUid
	 * @return
	 */
	public List<CheckItem> getCheckItemData(String companyUid, String parentUid, String formType, String projUid, String itemLb, int start, int end,
											Boolean ifCatalog, String type, String search, String projLx, String projPurpose);

	public int getCheckItemTotalCount(String companyUid, String parentUid, String formType, String projUid, String itemLb, Boolean ifCatalog, String type);

	/**
	 * 获取检查项目录数据
	 * @param companyUid
	 * @param formType
	 * @param projUid
	 * @return
	 */
	public List<Map<String,Object>> getCheckItemCatalog(String companyUid, String formType, String projUid);

	/**
	 * 懒加载获取检查项目录数据
	 * @param parentUid
	 * @param formType
	 * @param projUid
	 * @return
	 */
	List<Map<String,Object>> getInspectItemCatalog(String formType, String projUid, String parentUid, String type);
	/**
	 * 获取检查部位列表数据
	 * @return
	 */
	public List<CheckItem> getCheckItemStandards(String parentUid);

	/**
	 * 批量删除检查项
	 */
	public void  batchDeleteCheckItem(String[] ids, String tableName);

	/**
	 * 导入检查项
	 * @param tempSheet
	 */
	//public int  importCheckItem(Sheet tempSheet);
	/**
	 * 导入检查部位
	 * @param list
	 */
	public void  importCheckItemFromStandard(List<CheckItem> list, String sqlTableName, String parentId) throws SQLException;

	/**
	 * 获取检查列表查询数据
	 * @param companyUid
	 * @return
	 */
	ReturnPageJson<List<Map<String, Object>>> getCheckRecordDatas(String companyUid, String status, String checkType, String search, int pageIndex, int pageSize, String projUid,
                                                                  String dataType, String fromType, boolean isCompanyAdmin, String userUid, String principal,
                                                                  String partUid, String processId, String type, String processStatus, String areaId, String processPerson, String myAbarbeitung, String lb);

	/**
	 * 获取检查列表总数
	 * @param companyUid
	 * @return
	 */
	Map<String, Object> getCheckRecordTotalCount(String companyUid, String status, String checkType, String search, String projUid,
                                                 String dataType, String fromType, boolean isCompanyAdmin, String userUid, String principal, String partUid,
                                                 String processId, String type, String processStatus, String areaId, String processPerson, String myAbarbeitung, String lb);

	public Map<String, Object> getCheckRecordById(String id, String userUid);
	public Map<String, Object> getCheckItemById(String id);
	public void deleteById(String id);

	/**
	 * 获取检查统计数量数据
	 * @param companyUid
	 * @return
	 */
	public Map<String, Object> getStatisticCountData(String companyUid, String checkType, String projUid, String statisticsDate, String formType, String dbType);
	/**
	 * 获取检查统计图表数据
	 * @param companyUid
	 * @return
	 */
	public List<Map<String, Object>> getStatisticChartsData(String companyUid, String statisticsDate, String projUid, String formType, String checkType);

	/**
	 * 获取检查统计表格数据
	 * @param companyUid
	 * @param statisticsDate
	 * @param projUid
	 * @return
	 */
	Map<String, Object> getStatisticTableData(String companyUid, String statisticsDate, String projUid, String checkType);

	/**
	 * 获取检查记录中检查部位数据
	 * @param projUid
	 * @return
	 */
	public List<Map<String,Object>> getCheckPartDatas(String projUid, String checkType);
	public List<Map<String,Object>> getPartTree(String projUid, String companyUid, String parentUid, String purpose, String parentName);

	/**
	 * 新增检查记录
	 * @param list
	 */
	public void  saveCheckRecord(List<CheckRecord> list);

	public void  updateCheckRecord(String markerId, CheckRecord checkRecord);

	/**
	 *获取测点包含的信息
	 * @param id
	 * @return
	 */
	public List<Map<String, Object>> getMeasurePoitData(String id);

	/**
	 * 获取实测权限人员
	 * @param projUid
	 * @return
	 */
	public List<Map<String, Object>> getAuthorityMember(String projUid, String type);

	/**
	 * 获取实测统计数量数据
	 * @param companyUid
	 * @return
	 */
	public Map<String, Object> getMeasureCountData(String companyUid, String projUid, String statisticsDate);

	/**
	 * 获取实测统计图表数据
	 * @param companyUid
	 * @return
	 */
	public List<Map<String, Object>> getMeasureChartsData(String companyUid, String statisticsDate, String projUid);
	/**
	 * 获取实测统计表格数据
	 * @param companyUid
	 * @param statisticsDate
	 * @param projUid
	 * @return
	 */
	public List<Map<String, Object>> getMeasureTableData(String companyUid, String statisticsDate, String projUid);

	public Map<String, Object> getDrawingMeasureData(String id, String projUid);

	/**
	 * 批量删除测点
	 */
	public void  batchDeletePoitRecord(String[] ids);

	/**
	 * 获取微信端检查统计数量数据
	 * @param companyUid
	 * @return
	 */
	public Map<String, Object> getStatisticCountDataOfWechat(String companyUid, String projUid, String statisticsDate, String formType, String checkType);

	public List<Map<String, Object>> getCheckerStatistictData(String userField, String statisticsDate, String projUid, String formType, String checkType);
	public List<Map<String, Object>> getRectifyStatistictData(String statisticsDate, String projUid, String formType, String checkType);

	/**
	 * 导出检查记录
	 * @param companyUid
	 * @return
	 */
	public List<Map<String, Object>> exportCheckRecord(String companyUid, String status, String checkType, String search, String projUid,
                                                       String dataType, String fromType, boolean isCompanyAdmin, String userUid, String principal, String partUid);

	/**
	 * 监管版检查统计
	 * @param companyUid
	 * @return
	 */
	public Map<String, Object> getStatisticCountDataForSupervise(String companyUid, String statisticsDate, String checkType);

	List<Map<String, Object>> getInspectHeatMapData(String companyUid, String statisticsDate, String checkType);

	/**
	 * 监管版检查统计图表数据
	 * @param companyUid
	 * @param statisticsDate
	 * @return
	 */
	List<Map<String, Object>> getInspectChartData(String companyUid, String statisticsDate, String checkType);
	
	
	/**
	 * 获取企业库目录数据
	 * @param parentUid
	 * @param moduleType
	 * @param companyUid
	 * @return
	 */
	public List<CheckItem> getCheckItemEnterprise(String parentUid, String moduleType,
                                                  String companyUid, String projType, String projPurpose, String type, String id);
	
	
	/**
	 * 获取检查权限人员数据
	 * @param companyUid
	 * @param projUid
	 * @param type
	 * @return
	 */
	Map<String, Object> getUserPermission(String companyUid, String projUid, String type, String userType, String search);


	/**
	 * 查询计划内质量现场检查
	 * @param companyUid
	 * @param projUid
	 * @param type
	 * @param status
	 * @param userUid
	 * @param start
	 * @param end
	 * @param lb
	 * @return
	 */
	public List<Map<String, Object>> searchPlanInsideQuality(String companyUid, String projUid, String type,
                                                             String status, String userUid, int start, int end, String lb);
	
	
	/**
	 * 获取企业库检查项列表数据
	 * @param parentUid
	 * @param companyUid
	 * @return
	 */
	public List<CheckItem> getCheckItemEnterpriseList(String parentUid, String companyUid, int start, int end);


	
	/**
	 * 删除权限人员
	 * @param projUid
	 * @param type
	 * @param groupName
	 */
	public void deletePermissionUser(String projUid, String type, String groupName);


	/**
	 * 获取待检数据
	 * @param projUid
	 * @param userUid
	 * @param start
	 * @param end
	 * @return
	 */
	List<Map<String, Object>> getWaitingCheckDatas(String projUid, String userUid, int start, int end, String lb);
	
	
	/**
	 * 获取企业树
	 * @param companyUid
	 * @param projType
	 * @param projPurpose
	 * @param type
	 * @param id
	 * @return
	 */
	public List<CheckItem> getCheckItemEnterprise2(String companyUid,
                                                   String projType, String projPurpose, String type, String id);

	/**
	 * 查询区域业态
	 * @param projUid
	 * @return
	 */
	List<Map<String, Object>> getAreaPurposes(String projUid);

	/**
	 * 获取检查记录中检查项数据
	 * @param projUid
	 * @param checkType
	 * @return
	 */
	List<Map<String, Object>> getCheckRecordItemList(String projUid, String checkType);

}
