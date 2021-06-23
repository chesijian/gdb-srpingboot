package com.jrsoft.business.modules.project.dao;

import java.util.List;
import java.util.Map;

import com.jrsoft.business.modules.project.query.ProjectQuery;
import org.apache.ibatis.annotations.Param;

import com.jrsoft.engine.common.persistence.annotation.MyBatisDao;
import sun.applet.Main;

@MyBatisDao
public interface ProjectDao {
	
	/**
	 * 根据当前用户查询项目列表
	 * @param request
	 * @param response
	 * @param pageSize	每页显示多少条
	 * @param createTime	
	 * @param type	下拉
	 * @param search	搜索框
	 * @param userUid	
	 * @param companyUid	
	 * @param projStatus	项目状态
	 * @return
	 */
	List<Map<String, Object>> getCurUserProjectLists(@Param("userUid")String userUid, @Param("companyUid")String companyUid , @Param("search")String search, @Param("start")int start, @Param("end")int end, @Param("isCompanyAdmin")boolean isCompanyAdmin, @Param("projStatus")String projStatus);
	
	
	/**
	 * 根据id获取项目数据
	 * @param request
	 * @param id
	 * @return
	 */
	Map<String, Object> getProjectById(@Param("id")String id);
	
	
	/**
	 * 根据当前用户查询班组列表
	 * @param search		搜索框
	 * @param pageSize		显示多少条
	 * @param pageIndex		从第几条显示
	 * @param userUid		
	 * @param companyUid	
	 * @return
	 */
	List<Map<String, Object>> searchTreamGroup(@Param("companyUid")String companyUid, @Param("userUid")String userUid, @Param("search")String search, @Param("start")int start, @Param("end")int end, @Param("isCompanyAdmin")boolean isCompanyAdmin, @Param("projUid")String projUid);
	
	
	/**
	 * 根据id查询班组信息
	 * @param id	
	 * @return
	 */
	Map<String, Object> searchTreamGroupById(@Param("id")String id);
	
	
	/**
	 * 根据项目id查询部位列表
	 * @param projUid		项目id
	 * @param userUid		
	 * @param search		搜索框
	 * @param companyUid	
 	 * @param pageSize		显示多少条
	 * @param pageIndex		从第几条显示
	 * @return
	 */
	List<Map<String, Object>> searchWorkPartList(@Param("projUid")String projUid, @Param("userUid")String userUid, @Param("companyUid")String companyUid, @Param("search")String search, @Param("start")int start,
			@Param("end")int end, @Param("isCompanyAdmin")boolean isCompanyAdmin, @Param("parentUid")String parentUid);
	
	
	/**
	 * 根据部位id查询下级部位
	 * @param id
	 * @return
	 */
	List<Map<String, Object>> searchWorkPartNext(@Param("id")String id);
	
	
	/**
	 * 根据上级id查询上一级id
	 * @param superiorId
	 * @return
	 */
	Map<String, Object> superior(@Param("superiorId")String superiorId);
	
	
	/**
	 * 根据上一级id查询
	 * @param id
	 * @param projUid
	 * @return
	 */
	List<Map<String, Object>> returnSuperior(@Param("id")String id, @Param("projUid")String projUid);

	
	/**
	 * 删除项目
	 * @param id	需要删除部位的id
	 * @return
	 */
	void deleteProjectById(@Param("id")String id);
	
	
	/**
	 * 查询日志列表
	 * @param projUid		项目id
	 * @param userUid		用户id
	 * @param companyUid	公司id
	 * @param search		搜索框
	 * @param start		
	 * @param end
	 * @param isCompanyAdmin
	 * @param type
	 * @return
	 */
	List<Map<String, Object>> searchWorkLogList(@Param("projUid")String projUid, @Param("userUid")String userUid, @Param("companyUid")String companyUid, @Param("search")String search,
			@Param("start")int start, @Param("end")int end, @Param("isCompanyAdmin")boolean isCompanyAdmin, @Param("type")Integer type);
	
	
	/**
	 * 根据id查询日志详情
	 * @param id
	 * @return
	 */
	Map<String, Object> searchLogById(@Param("id")String id, @Param("userUid")String userUid, @Param("dbType") String dbType);
	
	
	
	/**
	 * 查询施工签证列表
	 * @param projUid
	 * @param userUid
	 * @param companyUid
	 * @param search
	 * @param start
	 * @param end
	 * @param isCompanyAdmin
	 * @param type
	 * @return
	 */
	List<Map<String, Object>> searchVisaList(@Param("projUid")String projUid, @Param("userUid")String userUid, @Param("companyUid")String companyUid, @Param("search")String search,
			@Param("start")int start, @Param("end")int end, @Param("isCompanyAdmin")boolean isCompanyAdmin, @Param("type")String type, @Param("dbType") String dbType);
	
	
	/**
	 * 根据id查询施工签证
	 * @param id
	 * @return
	 */
	Map<String, Object> searchvisaById(@Param("id")String id, @Param("userUid")String userUid, @Param("dbType") String dbType);
	
	
	/**
	 * 根据项目id获取项目成员
	 * @param id
	 * @return
	 */
	Map<String, Object> getMemberByProjUid(@Param("id") String id, @Param("search") String search, @Param("dbType") String dbType);

	/**
	 * 根据项目编号，查询关键字来查询项目成员信息
	 * @param projUid
	 * @param search
	 * @param dbType
	 * @return
	 */
	List<Map<String,Object>> getMemberByProjUidAndSearch(@Param("projUid") String projUid,@Param("search") String search, @Param("dbType") String dbType);

	/**
	 * 删除班组
	 * @param id	班组id
	 */
	void deleteTreamGroup(@Param("id")String id);
	
	
	/**
	 * 根据用户ids查询用户信息
	 * @param principal
	 * @return
	 */
	List<Map<String, Object>> searchUser(@Param("principal")String[] principal);
	
	
	/**
	 * 根据id查询部位详情
	 * @param id
	 * @return
	 */
	List<Map<String, Object>> searchWorkPartDetails(@Param("id")String id);

	
	/**
	 * 删除部位
	 * @param partId
	 */
	void deletePartById(@Param("partId")String partId);

	/**
	 * 查询但前人最新项目
	 * @param companyUid
	 * @return
	 */
	Map<String, Object> getUserFristProj(@Param("userUid")String userUid, @Param("companyUid")String companyUid,@Param("dbType") String dbType);
	/**
	 * 查询最新项目
	 * @param companyUid
	 * @return
	 */
	Map<String, Object> getNewestProj(@Param("userUid")String userUid, @Param("companyUid")String companyUid, 
			@Param("isCompanyAdmin")boolean isCompanyAdmin, @Param("dbType") String dbType);
	
	
	/**
	 * 根据项目id查询图纸列表
	 * @param projUid
	 * @param userUid
	 * @param companyUid
	 * @param search
	 * @param start
	 * @param end
	 * @param isCompanyAdmin
	 * @return
	 */
	List<Map<String, Object>> searchDrawingList(@Param("projUid")String projUid, @Param("userUid")String userUid, @Param("companyUid")String companyUid, @Param("search")String search,
			@Param("start")int start, @Param("end")int end, @Param("isCompanyAdmin")boolean isCompanyAdmin);

	
	/**
	 * 根据id查询部位详情
	 * @param id	主键id
	 * @return
	 */
	Map<String, Object> searchDrawingById(@Param("id")String id,@Param("dbType")String dbType);

	
	/**
	 * 根据id删除部位
	 * @param id
	 */
	void deleteDrawingById(@Param("id")String id);

	
	/**
	 * 根据部位id查询附件保存的图片
	 * @param id
	 */
	void deleteAttachmentByParentId(String id);

	
	/**
	 * 根据项目id查询拍照列表
	 * @param projUid
	 * @param userUid
	 * @param companyUid
	 * @param search
	 * @param start
	 * @param end
	 * @param isCompanyAdmin
	 * @return
	 */
	List<Map<String, Object>> searchPhotographList(@Param("projUid")String projUid, @Param("userUid")String userUid, @Param("companyUid")String companyUid, @Param("search")String search,
			@Param("start")int start, @Param("end")int end, @Param("isCompanyAdmin")boolean isCompanyAdmin, @Param("dbType") String dbType);

	
	/**
	 * 删除日志详情数据
	 * @param id
	 */
	void deleteLogdDetailsById(@Param("id")String id);

	
	/**
	 * 删除日志
	 * @param id
	 */
	void deleteLogdById(@Param("id")String id);

	/**
	 * 删除签证详情
	 * @param id
	 */
	void deleteVisaDetailsById(@Param("id")String id);

	
	/**
	 * 删除签证
	 * @param id
	 */
	void deleteVisaById(@Param("id")String id);

	
	/**
	 * 查询工作汇报列表
	 * @param projUid
	 * @param userUid
	 * @param companyUid
	 * @param search
	 * @param start
	 * @param end
	 * @param isCompanyAdmin
	 * @param type
	 * @return
	 */
	List<Map<String, Object>> searchReportList(@Param("projUid")String projUid, @Param("userUid")String userUid, @Param("companyUid")String companyUid, @Param("search")String search,
			@Param("start")int start, @Param("end")int end, @Param("isCompanyAdmin")boolean isCompanyAdmin, @Param("type")String type, @Param("dataType")int dataType);

	
	/**
	 * 根据项目id查询项目简介
	 * @param projUid	项目id
	 * @return
	 */
	Map<String, Object> searchProjBriefByProjUid(@Param("projUid") String projUid, @Param("dbType") String dbType);

	
	/**
	 * 根据项目id查询现场拍照
	 * @param projUid
	 * @return
	 */
	List<Map<String, Object>> searchPhotographByProjUid(@Param("projUid")String projUid, @Param("dbType") String dbType);

	
	/**
	 * 根据多个用户的id查询用户职位
	 * @param ids
	 * @return
	 */
	List<Map<String, Object>> getPositionByUserIds(@Param("ids") String[] ids);

	
	/**
	 * 根据id现场拍照
	 * @param id
	 */
	void deletePhotographById(@Param("id") String id);

	
	/**
	 * 编辑后删除之前的项目头像
	 * @param fileId
	 * @param projUid
	 */
	void deleteProjHeadPortrait(@Param("fileId") String fileId, @Param("projUid") String projUid);

	
	/**
	 * 查询进度信息
	 * @param projUid
	 * @return
	 */
	Map<String, Object> searchScheduleInfo(@Param("projUid") String projUid, @Param("dbType") String dbType);

	
	/**
	 * 新闻公告(首页)
	 * @param projUid
	 * @return
	 */
	List<Map<String, Object>> searchHomeNews(@Param("projUid") String projUid, @Param("dbType") String dbType);

	
	/**
	 * 根据id工作汇报详情
	 * @param id
	 * @return
	 */
	Map<String, Object> reportDetailsById(@Param("id") String id);

	
	/**
	 * 根据id删除工作汇报
	 * @param id
	 */
	void deleteReportById(@Param("id") String id);

	
	/**
	 * 根据公司id获取当前公司项目数量
	 * @param companyUid
	 * @return
	 */
	Integer searchProjectSum(@Param("companyUid") String companyUid, @Param("dbType") String dbType);

	
	/**
	 * 获取项目列表(MySql)
	 * @param query
	 * @return
	 */
	List<Map<String, Object>> searchProjList(ProjectQuery query);

	
	/**
	 * 根据项目id获取项目信息(MySql)
	 * @param id
	 * @return
	 */
	Map<String, Object> searchProjDetailsById(@Param("id") String id);

	
	/**
	 * 工作汇报列表
	 * @param projUid
	 * @param userUid
	 * @param companyUid
	 * @param search
	 * @param start
	 * @param end
	 * @param isCompanyAdmin
	 * @param type
	 * @param dataType
	 * @return
	 */
	List<Map<String, Object>> searchReportListMySql(@Param("projUid")String projUid, @Param("userUid")String userUid, @Param("companyUid")String companyUid, @Param("search")String search,
			@Param("start")int start, @Param("end")int end, @Param("isCompanyAdmin")boolean isCompanyAdmin, @Param("type")String type, @Param("dataType")int dataType,@Param("startDate")String startDate,@Param("endDate")String endDate);

	Integer getReportListTotalCount(@Param("projUid")String projUid, @Param("userUid")String userUid, @Param("companyUid")String companyUid, @Param("search")String search,
													@Param("start")int start, @Param("end")int end, @Param("isCompanyAdmin")boolean isCompanyAdmin,
									@Param("type")String type, @Param("dataType")int dataType,@Param("startDate")String startDate,@Param("endDate")String endDate);


	/**
	 * 根据id查询汇报详情
	 * @param id
	 * @param dbType
	 * @return
	 */
	Map<String, Object> reportDetailsByIdMySql(@Param("id") String id, @Param("dbType") String dbType, @Param("userUid") String userUid);

	
	/**
	 * 查询日志列表(MySql)
	 * @param projUid		项目id
	 * @param userUid		用户id
	 * @param companyUid	公司id
	 * @param search		搜索框
	 * @param start		
	 * @param end
	 * @param isCompanyAdmin
	 * @param type
	 * @return
	 */
	List<Map<String, Object>> searchWorkLogListMySql(@Param("projUid")String projUid, @Param("userUid")String userUid, @Param("companyUid")String companyUid, @Param("search")String search,
			@Param("start")int start, @Param("end")int end, @Param("isCompanyAdmin")boolean isCompanyAdmin, @Param("type")Integer type, @Param("startDate")String startDate,@Param("endDate")String endDate);

	
	/**
	 * 根据PROJ_UID_来查询相关的人员信息
	 */
	List<Map<String,Object>> getWorkerInfoByProjUid(@Param("projUid") String projUid);

	
	/**
	 * 根据项目id查询项目简介(MySql)
	 * @param projUid	项目id
	 * @return
	 */
	Map<String, Object> searchProjBriefByProjUidMySql(@Param("projUid") String projUid, @Param("dbType") String dbType);


	List<Map<String, Object>> searchWorkPartListMySql(@Param("projUid")String projUid, @Param("userUid")String userUid, @Param("companyUid")String companyUid, @Param("search")String search, @Param("start")int start,
			@Param("end")int end, @Param("isCompanyAdmin")boolean isCompanyAdmin, @Param("parentUid")String parentUid);

	int getWorkPartListTotalCount(@Param("projUid")String projUid, @Param("userUid")String userUid, @Param("companyUid")String companyUid, @Param("search")String search,
													  @Param("isCompanyAdmin")boolean isCompanyAdmin, @Param("parentUid")String parentUid);
	/**
	 * 根据公司id查询用户
	 * @param companyUid
	 * @return
	 */
	Integer getUsers(@Param("companyUid") String companyUid, @Param("dbType") String dbType);

	
	/**
	 * 查询项目集合
	 * @param companyUid
	 * @return
	 */
	List<Map<String, Object>> searchExtProjList(@Param("projUid") String projUid, @Param("companyUid") String companyUid);

	
	/**
	 * 获取企业集合
	 * @return
	 */
	List<Map<String, Object>> searchCorpList(@Param("corpId") String corpId);

	
	/**
	 * 根据项目id获取参建单位
	 * @param projUid
	 * @return
	 */
	List<Map<String, Object>> searchParticipationList(@Param("projUid") String projUid, 
			@Param("dbType") String dbType, @Param("type") Integer type);

	
	/**
	 * 修改项目编码(第三方接口编码)
	 * @param string
	 * @param string2
	 */
	void updateProjectCode(@Param("id") String id, @Param("projectCode") String projectCode);

	
	/**
	 * 查询工资单
	 * @param projUid
	 * @return
	 */
	List<Map<String, Object>> searchPayrollList(@Param("projUid") String projUid);

	
	/**
	 * 查询班组集合
	 * @param projUid
	 * @return
	 */
	List<Map<String, Object>> searchTeamList(@Param("projUid") String projUid);

	
	/**
	 * 修改班组编码
	 * @param teamId
	 * @param teamCode
	 * @param projId
	 */
	void updateTeamCode(@Param("teamId") String teamId, @Param("teamCode") String teamCode, @Param("projId") String projId);

	
	/**
	 * 修改班组
	 * @param teamId
	 * @return
	 */
	List<Map<String, Object>> updateTeamList(@Param("projUid") String projUid);

	
	/**
	 * 根据项目id与班组id查询项目工人
	 * @param projUid
	 * @param teamCode
	 * @return
	 */
	List<Map<String, Object>> searchWorkerInfoList(@Param("projUid") String projUid, @Param("dbType") String dbType);

	
	/**
	 * 获取人员进退场集合
	 * @param projUid
	 * @param teamUid
	 * @return
	 */
	List<Map<String, Object>> searchEntryExitInfoList(@Param("projUid") String projUid);


	List<Map<String, Object>> updateWorkerInfoList(@Param("projUid") String projUid);

	
	/**
	 * 根据工人id查询工人
	 * @param ids
	 * @param projUid
	 * @param teamUid
	 * @return
	 */
	List<Map<String, Object>> updateWorkerInfoList(@Param("ids") String[] ids, @Param("projUid") String projUid, @Param("teamUid") String teamUid);

	
	/**
	 * 上传劳动合同
	 * @param projUid
	 * @param teamUid
	 * @return
	 */
	List<Map<String, Object>> searchUploadContractList(@Param("projUid") String projUid);

	
	/**
	 * 修改状态为2, 已推送
	 * @param id
	 */
	void updateProjectPushStatus(@Param("id") String id);

	
	/**
	 * 修改状态为2, 已推送
	 * @param id
	 */
	void updateParticipationPushStatus(@Param("id") String id);

	
	/**
	 * 修改状态为2, 已推送
	 * @param id
	 */
	void updateTeamPushStatus(@Param("id") String id);

	
	/**
	 * 修改状态为2, 已推送
	 * @param id
	 */
	void updateTeamRosterInfo(@Param("id") String id);

	
	/**
	 * 修改状态为2, 已推送
	 * @param id
	 */
	void updateEntryExitInfoPushStatus(@Param("id") String id);

	
	/**
	 * 修改状态为2, 已推送
	 * @param id
	 */
	void updateEntryContractPushStatus(@Param("id") String id);

	
	/**
	 * 查询工资单
	 * @param projUid
	 * @return
	 */
	List<Map<String, Object>> searchUploadPayrollList(@Param("projUid") String projUid);
	
	
	/**
	 * 更新工资单编码
	 * @param id
	 * @param payrollCode
	 */
	void updatePayrollCode(@Param("id") String id, @Param("payrollCode") String payrollCode);

	
	/**
	 * 查询工资单详细信息
	 * @param projUid
	 * @return
	 */
	List<Map<String, Object>> searchUploadPayrollDetailList(@Param("projUid") String projUid);

	
	/**
	 * 查询是否接入平台
	 * @param type
	 * @param name
	 * @param url
	 * @param apiKey
	 * @param port
	 * @return
	 */
	Integer searchPlatformJoint(@Param("projUid") String projUid, @Param("type") String type, @Param("name") String name, 
			@Param("url") String url, @Param("apiKey") String apiKey, @Param("port") String port);

	
	/**
	 * 更新上传企业状态
	 * @param id
	 */
	void updateCorp(@Param("id") String id);

	
	/**
	 * 修改参建单位状态
	 * @param id
	 */
	void updateParticipateInfoStatus(@Param("id") String id);

	
	/**
	 * 修改班组状态为已上传
	 * @param id
	 */
	void updateTeamInfoStatus(@Param("id") String id);

	
	/**
	 * 修改工人状态为已上传
	 * @param id
	 */
	void updateRosterInfoStatus(@Param("id") String id);

	
	/**
	 * 获取建设单位统一社会信用代码
	 * @param companyUid
	 * @return
	 */
	Map<String, Object> getSocietyCode(@Param("companyUid") String companyUid);

	
	/**
	 * 查询质量与检查记录
	 * @param projUid
	 * @param date
	 * @param userUid
	 * @return
	 */
	List<Map<String, Object>> searchQualityAndSafetyInspect(@Param("projUid") String projUid, @Param("date") String date, @Param("type") String type,
															@Param("formType") String formType,@Param("userUid")String userUid);

	
	/**
	 * 查询日报问题统计
	 * @param id
	 * @return
	 */
	List<Map<String, Object>> searchReportIssueList(@Param("id") String id);

	
	/**
	 * 根据id删除日报问题统计
	 * @param id
	 */
	void deleteReportIssueById(@Param("id") String id);

	
	/**
	 * 获得人员信息
	 * @param projUid
	 * @return
	 */
	List<Map<String, Object>> getHasPhotoWorkersByProjUid(@Param("projUid") String projUid);
	
	
	/**
	 * 更新月报审批状态
	 * @param id
	 */
	void updateReportStatusById(@Param("id") String id, @Param("status") Integer status);

	
	
	/**
	 * 查询物料验收清单
	 * @param projUid
	 * @param date
	 * @param string
	 * @param string2
	 * @return
	 */
	List<Map<String, Object>> searchMaterialInspect(@Param("projUid") String projUid, @Param("date") String date);

}
