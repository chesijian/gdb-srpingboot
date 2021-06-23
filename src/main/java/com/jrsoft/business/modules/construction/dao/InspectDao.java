package com.jrsoft.business.modules.construction.dao;

import com.jrsoft.business.modules.construction.domain.CheckItem;
import com.jrsoft.business.modules.construction.domain.CheckPart;
import com.jrsoft.business.modules.construction.domain.CheckRecord;
import com.jrsoft.engine.common.persistence.annotation.MyBatisDao;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;


@MyBatisDao
public interface InspectDao {

	/**
	 * 获取检查项列表数据
	 * @author 车斯剑
	 * @date 2018年10月18日下午7:02:20
	 * @return
	 */
	public List<CheckItem> getCheckItemData(@Param("companyUid") String companyUid, @Param("parentUid") String parentUid, @Param("formType") String formType, @Param("projUid") String projUid, @Param("itemLb") String itemLb,
											@Param("start") int start, @Param("end") int end, @Param("ifCatalog") Boolean ifCatalog, @Param("type") String type,
											@Param("search") String search, @Param("dbType") String dbType, @Param("projLx") String projLx, @Param("projPurpose") String projPurpose);
	public int getCheckItemTotalCount(@Param("companyUid") String companyUid, @Param("parentUid") String parentUid, @Param("formType") String formType,
                                      @Param("projUid") String projUid, @Param("itemLb") String itemLb, @Param("ifCatalog") Boolean ifCatalog, @Param("type") String type);

	public List<Map<String,Object>> getCheckItemCatalog(@Param("companyUid") String companyUid, @Param("parentUid") String parentUid,
                                                        @Param("formType") String formType, @Param("projUid") String projUid, @Param("type") String type);

	List<Map<String,Object>> getInspectItemCatalog(@Param("parentUid") String parentUid,
                                                   @Param("formType") String formType, @Param("projUid") String projUid, @Param("type") String type);
	/**
	 * 获取国标检查项目录数据
	 * @author 车斯剑
	 * @date 2018年10月18日下午7:02:20
	 * @return
	 */
	public List<CheckItem> getCheckItemStandardsByParentId(@Param("parentUid") String parentUid, @Param("dbType") String dbType);
	
	public List<CheckItem> getCheckItemFromStandardDet(@Param("parentUid") String parentUid);
	
	public CheckItem getCheckItemFromStandarCount(@Param("sign") String sign, @Param("companyUid") String companyUid, @Param("projUid") String projUid,
                                                  @Param("sqlTableName") String sqlTableName, @Param("formType") String formType, @Param("parentId") String parentId, @Param("itemName") String itemName);

	/**
	 * 批量删除检查项
	 * @author 车斯剑
	 * @date 2018年10月19日下午5:55:31
	 * @param ids
	 * @return
	 */
	public void batchDeleteCheckItem(@Param("ids") String[] ids, @Param("tableName") String tableName);

	/**
	 * 获取检查列表查询数据
	 * @author 车斯剑
	 * @date 2018年10月20日下午2:27:18
	 * @param companyUid
	 * @return
	 */
	public List<Map<String, Object>> getCheckRecordDatas(@Param("companyUid") String companyUid, @Param("status") String status, @Param("checkType") String checkType, @Param("search") String search, @Param("start") int start, @Param("end") int end, @Param("projUid") String projUid,
                                                         @Param("dataType") String dataType, @Param("formType") String formType, @Param("isCompanyAdmin") boolean isCompanyAdmin, @Param("userUid") String userUid,
                                                         @Param("principal") String principal, @Param("partUid") String partUid, @Param("processId") String processId, @Param("lb") String lb, @Param("processStatus") String processStatus,
                                                         @Param("areaId") String areaId, @Param("processPerson") String processPerson, @Param("myAbarbeitung") String myAbarbeitung, @Param("dbType") String dbType, @Param("dataStatus") String dataStatus);

	int getCheckRecordTotalNum(@Param("companyUid") String companyUid, @Param("status") String status, @Param("checkType") String checkType, @Param("search") String search, @Param("start") int start, @Param("end") int end, @Param("projUid") String projUid,
                               @Param("dataType") String dataType, @Param("formType") String formType, @Param("isCompanyAdmin") boolean isCompanyAdmin, @Param("userUid") String userUid,
                               @Param("principal") String principal, @Param("partUid") String partUid, @Param("processId") String processId, @Param("lb") String lb, @Param("processStatus") String processStatus,
                               @Param("areaId") String areaId, @Param("processPerson") String processPerson, @Param("myAbarbeitung") String myAbarbeitung, @Param("dbType") String dbType, @Param("dataStatus") String dataStatus);
	/**
	 * 获取检查列表总数
	 * @author 车斯剑
	 * @date 2018年10月20日下午2:27:18
	 * @param companyUid
	 * @return
	 */
	Map<String, Object> getCheckRecordTotalCount(@Param("companyUid") String companyUid, @Param("status") String status, @Param("checkType") String checkType, @Param("search") String search, @Param("projUid") String projUid,
                                                 @Param("dataType") String dataType, @Param("formType") String formType, @Param("isCompanyAdmin") boolean isCompanyAdmin, @Param("userUid") String userUid, @Param("principal") String principal,
                                                 @Param("partUid") String partUid, @Param("dbType") String dbType, @Param("processId") String processId, @Param("type") String type, @Param("processStatus") String processStatus,
                                                 @Param("areaId") String areaId, @Param("processPerson") String processPerson, @Param("myAbarbeitung") String myAbarbeitung, @Param("lb") String lb);

	/**
	 * 导入检查项
	 * @param dataList
	 * @return
	 */
	public int insertCheckItemByBatch(@Param("dataList") List<CheckItem> dataList, @Param("sqlTableName") String sqlTableName);

	/**
	 * 导入检查部位
	 * @param dataList
	 * @return
	 */
	public int insertCheckPartByBatch(@Param("dataList") List<CheckPart> dataList);

	/**
	 * 获取整改单明细及回复记录
	 * @author 车斯剑
	 * @date 2017年7月26日下午2:19:34
	 * @param checkId
	 * @return
	 */
	public Map<String, Object> getImproveBillDetail(@Param("checkId") String checkId);




	public Map<String, Object> getCheckRecordById(@Param("id") String id, @Param("userUid") String userUid, @Param("dbType") String dbType);
	public Map<String, Object> getCheckRecordByIdMySql(@Param("id") String id, @Param("userUid") String userUid, @Param("dbType") String dbType);
	public Map<String, Object> getCheckItemById(@Param("id") String id);
	public Map<String, Object> findItemByName(@Param("itemName") String itemName);

	public void deleteById(@Param("id") String id);

	/**
	 * 获取检查统计数量数据
	 * @param companyUid
	 * @param checkType
	 * @param projUid
	 * @return
	 */
	public Map<String, Object> getStatisticCountData(@Param("companyUid") String companyUid, @Param("checkType") String checkType, @Param("projUid") String projUid,
                                                     @Param("statisticsDate") String statisticsDate, @Param("formType") String formType, @Param("dbType") String dbType);

	/**
	 * 获取检查统计图表数据
	 * @param companyUid
	 * @param statisticsDate
	 * @param projUid
	 * @return
	 */
	public List<Map<String, Object>> getStatisticChartsData(@Param("companyUid") String companyUid, @Param("statisticsDate") String statisticsDate,
                                                            @Param("projUid") String projUid, @Param("formType") String formType, @Param("dbType") String dbType, @Param("checkType") String checkType);

	/**
	 * 获取检查统计表格数据
	 * @param companyUid
	 * @param statisticsDate
	 * @param projUid
	 * @return
	 */
	public List<Map<String, Object>> getStatisticTableData(@Param("companyUid") String companyUid, @Param("statisticsDate") String statisticsDate,
                                                           @Param("projUid") String projUid, @Param("checkType") String checkType, @Param("dbType") String dbType);

	public List<Map<String, Object>> getCheckPartDatas(@Param("projUid") String projUid, @Param("checkType") String checkType);

	/**
	 * PC端获取部位树结构数据
	 * @param projUid
	 * @return
	 */
	public List<Map<String, Object>> getPartTree(@Param("projUid") String projUid, @Param("companyUid") String companyUid, @Param("parentUid") String parentUid);

	public int insertCheckRecordBatch(@Param("dataList") List<CheckRecord> dataList);

	public int updateCheckRecord(@Param("markerId") String markerId, @Param("obj") CheckRecord checkRecord);

	public List<Map<String, Object>> getMeasurePoitData(@Param("uuid") String uuid, @Param("dbType") String dbType);

	public List<Map<String, Object>> getAuthorityMember(@Param("projUid") String projUid, @Param("roleIds") String[] roleIds);

	public Map<String, Object> getMeasureCountData(@Param("companyUid") String companyUid, @Param("projUid") String projUid, @Param("statisticsDate") String statisticsDate, @Param("dbType") String dbType);

	public List<Map<String, Object>> getMeasureChartsData(@Param("companyUid") String companyUid, @Param("statisticsDate") String statisticsDate, @Param("projUid") String projUid, @Param("dbType") String dbType);
	public List<Map<String, Object>> getMeasureTableData(@Param("companyUid") String companyUid, @Param("statisticsDate") String statisticsDate, @Param("projUid") String projUid, @Param("dbType") String dbType);

	public Map<String, Object> getDrawingMeasureData(@Param("id") String id, @Param("projUid") String projUid, @Param("dbType") String dbType);

	/**
	 * 删除测点
	 * @param ids
	 */
	public void batchDeletePoitRecord(@Param("ids") String[] ids);

	public Map<String, Object> getStatisticCountDataOfWechat(@Param("companyUid") String companyUid, @Param("projUid") String projUid, @Param("statisticsDate") String statisticsDate,
                                                             @Param("formType") String formType, @Param("checkType") String checkType, @Param("dbType") String dbType);

	public List<Map<String, Object>> getCheckerStatistictData(@Param("userField") String userField, @Param("statisticsDate") String statisticsDate, @Param("projUid") String projUid,
                                                              @Param("formType") String formType, @Param("checkType") String checkType, @Param("dbType") String dbType);

	public List<Map<String, Object>> getRectifyStatistictData(@Param("statisticsDate") String statisticsDate, @Param("projUid") String projUid,
                                                              @Param("formType") String formType, @Param("checkType") String checkType, @Param("dbType") String dbType);

	/**
	 * 导出检查记录
	 * @author 车斯剑
	 * @date 2018年2月18日下午2:27:18
	 * @param companyUid
	 * @return
	 */
	public List<Map<String, Object>> exportCheckRecord(@Param("companyUid") String companyUid, @Param("status") String status, @Param("checkType") String checkType, @Param("search") String search, @Param("projUid") String projUid,
                                                       @Param("dataType") String dataType, @Param("formType") String formType, @Param("isCompanyAdmin") boolean isCompanyAdmin, @Param("userUid") String userUid,
                                                       @Param("principal") String principal, @Param("partUid") String partUid, @Param("dbType") String dbType);

	public Map<String, Object> getStatisticCountDataForSupervise(@Param("companyUid") String companyUid, @Param("statisticsDate") String statisticsDate, @Param("checkType") String checkType);

	/**
	 * 监管版检查统计热力图数据
	 * @param companyUid
	 * @param statisticsDate
	 * @return
	 */
	public List<Map<String, Object>> getInspectHeatMapData(@Param("companyUid") String companyUid, @Param("statisticsDate") String statisticsDate, @Param("checkType") String checkType);

	/**
	 * 监管版检查统计图表数据
	 * @param companyUid
	 * @param statisticsDate
	 * @return
	 */
	public List<Map<String, Object>> getInspectChartData(@Param("companyUid") String companyUid, @Param("statisticsDate") String statisticsDate, @Param("checkType") String checkType);
	
	
	/**
	 * PC端获取部位树结构数据(MySql)
	 * @param projUid
	 * @return
	 */
	public List<Map<String, Object>> getPartTreeMySql(@Param("projUid") String projUid, @Param("companyUid") String companyUid, @Param("parentUid") String parentUid, @Param("purpose") String purpose, @Param("parentName") String parentName);
	
	
	/**
	 * 获取企业库目录数据
	 * @param parentUid
	 * @param moduleType
	 * @param companyUid
	 * @return
	 */
	public List<CheckItem> getCheckItemEnterprise(@Param("parentUid") String parentUid, @Param("moduleType") String moduleType,
                                                  @Param("companyUid") String companyUid, @Param("projType") String projType, @Param("projPurpose") String projPurpose, @Param("type") String type, @Param("id") String id);
	
	
	/**
	 * 获取检查权限人员数据
	 * @param companyUid
	 * @param projUid
	 * @param type
	 * @return
	 */
	Map<String, Object> getUserPermission(@Param("companyUid") String companyUid, @Param("projUid") String projUid, @Param("type") String type, @Param("userType") String userType);
	
	
	/**
	 * 查询计划内质量现场检查
	 * @param companyUid
	 * @param projUid
	 * @param type
	 * @param status
	 * @param userUid
	 * @param start
	 * @param end
	 * @return
	 */
	public List<Map<String, Object>> searchPlanInsideQuality(@Param("companyUid") String companyUid, @Param("projUid") String projUid, @Param("type") String type,
                                                             @Param("status") String status, @Param("userUid") String userUid, @Param("start") int start, @Param("end") int end, @Param("lb") String lb);
	
	
	/**
	 * 获取企业库检查项列表数据
	 * @param parentUid
	 * @param companyUid
	 * @return
	 */
	public List<CheckItem> getCheckItemEnterpriseList(@Param("parentUid") String parentUid, @Param("companyUid") String companyUid,
                                                      @Param("start") int start, @Param("end") int end);
	
	/**
	 * 更新检查项业态和项目类型
	 * @param id
	 * @param newProjectType
	 * @param newYeTai
	 */
	public void updatePurposeAndLx(@Param("id") String id, @Param("newProjectType") String newProjectType, @Param("newYeTai") String newYeTai);
	
	
	/**
	 * 删除权限人员
	 * @param projUid
	 * @param type
	 * @param groupName
	 */
	public void deletePermissionUser(@Param("projUid") String projUid, @Param("type") String type, @Param("groupName") String groupName);

	/**
	 * 获取权限人员
	 * @param ids
	 * @return
	 */
	List<Map<String, Object>> getPermissionUsers(@Param("ids") String[] ids, @Param("search") String search);

	/**
	 * 获取权限角色人员
	 * @param ids
	 * @return
	 */
	List<Map<String, Object>> getPermissionRoleUsers(@Param("ids") String[] ids, @Param("projUid") String projUid, @Param("search") String search);

	List<Map<String, Object>> getWaitingCheckDatas(@Param("projUid") String projUid, @Param("userUid") String userUid, @Param("start") int start,
                                                   @Param("end") int end, @Param("lb") String lb);
	
	
	/**
	 * 查询企业库树
	 * @param moduleType
	 * @param companyUid
	 * @param projType
	 * @param projPurpose
	 * @param type
	 * @param id
	 * @return
	 */
	public List<CheckItem> searchInspectItemList(@Param("companyUid") String companyUid,
                                                 @Param("projType") String projType, @Param("projPurpose") String projPurpose, @Param("type") String type, @Param("id") String id);

	List<Map<String, Object>> getAreaPurposes(@Param("projUid") String projUid);

	/**
	 * 获取检查统计表格部位数据
	 * @param statisticsDate
	 * @param projUid
	 * @return
	 */
	public List<Map<String, Object>> getStatisticPartData(@Param("statisticsDate") String statisticsDate,
                                                          @Param("projUid") String projUid, @Param("checkType") String checkType, @Param("dbType") String dbType);

	/**
	 * 获取检查统计表格
	 * @param statisticsDate
	 * @param projUid
	 * @return
	 */
	public List<Map<String, Object>> getStatisticItemNameData(@Param("statisticsDate") String statisticsDate,
                                                              @Param("projUid") String projUid, @Param("checkType") String checkType, @Param("dbType") String dbType);


	Map<String, Object> getStatisticObj(@Param("partUid") String partUid, @Param("itemName") String itemName);

	List<Map<String, Object>> getCheckRecordItemList(@Param("projUid") String projUid, @Param("checkType") String checkType);

}
