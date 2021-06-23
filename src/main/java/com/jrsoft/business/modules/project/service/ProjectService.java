package com.jrsoft.business.modules.project.service;

import com.jrsoft.business.modules.project.query.ProjectQuery;
import com.jrsoft.engine.base.model.ReturnPageJson;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 项目立项
 * @author Xu
 *
 */
public interface ProjectService {
	
	/**
	 * 根据当前用户查询项目列表
	 * @param query
	 * @return
	 */
	ReturnPageJson<List<Map<String, Object>>> getCurUserProjectLists(ProjectQuery query);
	
	
	/**
	 * 根据id获取项目数据
	 * @param request
	 * @param id
	 * @return
	 */
	Map<String, Object> getProjectById(String id);
	
	
	/**
	 * 根据当前用户查询班组列表
	 * @param search		搜索框
	 * @param pageSize		显示多少条
	 * @param pageIndex		从第几条显示
	 * @param userUid		
	 * @param companyUid	
	 * @return
	 */
	List<Map<String, Object>> searchTreamGroup(String companyUid, String userUid, String search, int start, int end, boolean isCompanyAdmin, String projUid);
	
	
	/**
	 * 根据id查询班组信息
	 * @param id	
	 * @return
	 */
	Map<String, Object> searchTreamGroupById(String id);
	
	
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
	ReturnPageJson<List<Map<String, Object>>> searchWorkPartList(String projUid, String userUid, String companyUid, String search, int start,
			int end, boolean isCompanyAdmin, String parentUid, String dbType);
	
	
	/**
	 * 返回上一级
	 * @param superiorId	上级id
	 * @param projUid		项目id
	 * @return
	 */
	List<Map<String, Object>> returnSuperior(String parentUid, String projUid);
	
	
	/**
	 * 删除项目
	 * @param id	需要删除部位的id
	 * @return
	 */
	void deleteProjectById(String id);
	
	
	/**
	 * 删除部位及所有下级
	 * @param id	需要删除部位的id
	 * @return
	 */
	void deletePartById(String id);
	
	
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
	List<Map<String, Object>> searchWorkLogList(String projUid, String userUid, String companyUid, String search,
			int start, int end, Integer type, String startDate,String endDate);
	
	
	/**
	 * 根据id查询日志详情
	 * @param id
	 * @return
	 */
	Map<String, Object> searchLogById(String id, String userUid, String dbType);
	
	
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
	List<Map<String, Object>> searchVisaList(String projUid, String userUid, String companyUid, String search,
			int start, int end, boolean isCompanyAdmin, String type);
	
	
	/**
	 * 根据id查询施工签证
	 * @param id
	 * @return
	 */
	Map<String, Object> searchvisaById(String id, String userUid);
	
	
	/**
	 * 根据项目id获取项目成员
	 * @param id
	 * @return
	 */
	Map<String, Object> getMemberByProjUid(String id, String search, String dbType);


	/**
	 * 根据项目编号，查询关键字来查询项目成员信息
	 * @param projUid
	 * @param search
	 * @param dbType
	 * @return
	 */
	List<Map<String,Object>> getMemberByProjUidAndSearch(String projUid,String search,String dbType);


	/**
	 * 删除班组
	 * @param id	班组id
	 */
	void deleteTreamGroup(String id);
	
	/**
	 * 根据用户ids查询用户信息
	 * @param principal
	 * @return
	 */
	List<Map<String, Object>> searchUser(String[] principal);
	
	
	
	/**
	 * 根据id查询部位详情
	 * @param id
	 * @return
	 */
	List<Map<String, Object>> searchWorkPartDetails(String id);

	
	/**
	 * 查询最新项目
	 * @param companyUid
	 * @return
	 */
	Map<String, Object> getNewestProj(String userUid, String companyUid, boolean isCompanyAdmin, String dbType);

	
	/**
	 * 查询图纸列表
	 * @param projUid
	 * @param userUid
	 * @param companyUid
	 * @param search
	 * @param start
	 * @param end
	 * @param isCompanyAdmin
	 * @return
	 */
	List<Map<String, Object>> searchDrawingList(String projUid, String userUid, String companyUid, String search,
			int start, int end, boolean isCompanyAdmin);


	/**
	 * 根据id查询部位详情
	 * @param id	主键id
	 * @return
	 */
	Map<String, Object> searchDrawingById(String id);

	
	/**
	 * 根据id删除部位
	 * @param id
	 */
	void deleteDrawingById(String id);

	
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
	List<Map<String, Object>> searchPhotographList(String projUid, String userUid, String companyUid, String search,
			int start, int end, boolean isCompanyAdmin);

	
	/**
	 * 根据部位id查询下级部位
	 * @param id
	 * @return
	 */
	List<Map<String, Object>> searchWorkPartNext(String id);

	
	/**
	 * 根据id删除日志
	 * @param id
	 * @return
	 */
	void deleteLogById(String id);

	
	/**
	 * 根据id删除签证
	 * @param id
	 */
	void deleteVisaById(String id);
	

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
	ReturnPageJson searchReportList(String projUid, String userUid, String companyUid, String search,
									int start, int end, String type, int dataType, String startDate,String endDate);

	
	/**
	 * 根据项目id查询项目简介
	 * @param projUid	项目id
	 * @return
	 */
	Map<String, Object> searchProjBriefByProjUid(String projUid);

	
	/**
	 * 根据项目id查询现场拍照
	 * @param projUid	项目id
	 * @return
	 */
	List<Map<String, Object>> searchPhotographByProjUid(String projUid, String dbType);

	
	/**
	 * 根据多个用户的id查询用户职位
	 * @param ids
	 * @return
	 */
	List<Map<String, Object>> getPositionByUserIds(String[] ids);

	/**
	 * 根据id现场拍照
	 * @param id
	 */
	void deletePhotographById(String id);

	
	/**
	 * 编辑后删除之前的项目头像
	 * @param fileId
	 * @param projUid
	 */
	void deleteProjHeadPortrait(String fileId, String projUid);
	
	
	/**
	 * 查询进度信息(首页)
	 * @param projUid
	 * @return
	 */
	Map<String, Object> searchScheduleInfo(String projUid, String dbType);

	
	/**
	 * 新闻公告(首页)
	 * @param projUid
	 * @return
	 */
	List<Map<String, Object>> searchHomeNews(String projUid, String dbType);

	
	/**
	 * 根据id工作汇报详情
	 * @param id
	 * @return
	 */
	Map<String, Object> reportDetailsById(String id, String dbType, String userUid);

	
	/**
	 * 根据id删除工作汇报
	 * @param id
	 */
	void deleteReportById(String id);

	
	/**
	 * 根据公司id获取当前公司项目数量
	 * @param companyUid
	 * @return
	 */
	Integer searchProjectSum(String companyUid);

	/**
	 * 根据PROJ_UID_来查询相关的人员信息
	 * @param projUid 项目编号
	 */
	List<Map<String,Object>> getWorkerInfoByProjUid(String projUid);

	
	/**
	 * 根据公司查询人数
	 * @param companyUid
	 * @return
	 */
	Integer getUsers(String companyUid);

	
	/**
	 * 查询项目集合
	 * @param companyUid
	 * @return
	 */
	List<Map<String, Object>> searchExtProjList(String projUid, String companyUid);

	
	/**
	 * 获取企业集合
	 * @return
	 */
	List<Map<String, Object>> searchCorpList(String corpId);

	
	/**
	 * 根据项目id获取参建单位
	 * @param projUid
	 * @return
	 */
	List<Map<String, Object>> searchParticipationList(String projUid, Integer type);

	
	/**
	 * 修改项目编码(第三方接口编码)
	 * @param string
	 * @param string2
	 */
	void updateProjectCode(String id, String projectCode);

	
	/**
	 * 查询工资单
	 * @param projUid
	 * @return
	 */
	List<Map<String, Object>> searchPayrollList(String projUid);

	
	/**
	 * 查询班组集合
	 * @param projUid
	 * @return
	 */
	List<Map<String, Object>> searchTeamList(String projUid);

	
	/**
	 * 更新班组编号
	 * @param string
	 * @param string2
	 * @param string3
	 */
	void updateTeamCode(String teamId, String teamCode, String projId);

	
	/**
	 * 修改班组
	 * @param teamIds
	 * @return
	 */
	List<Map<String, Object>> updateTeamList(String projUid);

	
	/**
	 * 根据项目id与班组id查询项目工人
	 * @param projUid
	 * @param teamCode
	 * @return
	 */
	List<Map<String, Object>> searchWorkerInfoList(String projUid);

	
	/**
	 * 获取人员进退场集合
	 * @param projUid
	 * @param teamUid
	 * @return
	 */
	List<Map<String, Object>> searchEntryExitInfoList(String projUid);

	
	/**
	 * 根据工人id查询工人
	 * @param workerIds
	 * @return
	 */
	List<Map<String, Object>> updateWorkerInfoList(String projUid);

	
	/**
	 * 上传劳动合同
	 * @param projUid
	 * @param teamUid
	 * @return
	 */
	List<Map<String, Object>> searchUploadContractList(String projUid);

	
	/**
	 * //修改状态为2, 已推送
	 * @param string
	 */
	void updateProjectPushStatus(String id);

	
	/**
	 * 修改状态为2, 已推送
	 * @param string
	 */
	void updateParticipationPushStatus(String string);

	
	/**
	 * 修改状态为2, 已推送
	 * @param string
	 */
	void updateTeamPushStatus(String id);

	
	/**
	 * 修改状态为2, 已推送
	 * @param string
	 */
	void updateTeamRosterInfo(String id);

	
	/**
	 * 修改状态为2, 已推送
	 * @param string
	 */
	void updateEntryExitInfoPushStatus(String id);

	
	/**
	 * 修改状态为2, 已推送
	 * @param string
	 */
	void updateEntryContractPushStatus(String id);

	
	/**
	 * 查询工资单
	 * @param projUid
	 * @return
	 */
	List<Map<String, Object>> searchUploadPayrollList(String projUid);

	
	/**
	 * 更新工资单状态
	 * @param string
	 * @param string2
	 */
	void updatePayrollCode(String id, String payrollCode);

	
	/**
	 * 查询工资单详细信息
	 * @param projUid
	 * @return
	 */
	List<Map<String, Object>> searchUploadPayrollDetailList(String projUid);

	
	/**
	 * 查询是否接入平台
	 * @param i
	 * @param string
	 * @param string2
	 * @param apiKey
	 * @param j
	 * @return
	 */
	Integer searchPlatformJoint(String projUid, String type, String name, String url, String apiKey, String port);

	
	/**
	 * 更新上传企业状态
	 * @param string
	 */
	void updateCorp(String id);

	
	/**
	 * 修改参建单位状态
	 * @param string
	 */
	void updateParticipateInfoStatus(String id);

	
	/**
	 * 修改班组状态为已上传
	 * @param string
	 */
	void updateTeamInfoStatus(String string);
	
	
	/**
	 * 修改工人状态为已上传
	 * @param string
	 */
	void updateRosterInfoStatus(String string);

	
	/**
	 * 获取建设单位统一社会信用代码
	 * @param companyUid
	 * @return
	 */
	Map<String, Object> getSocietyCode(String companyUid);

	
	/**
	 * 查询当天日报检查数据
	 * @param id
	 * @param format
	 * @return
	 */
	Map<String, Object> searchReportInspect(String projUid, String id, String date);

	
	/**
	 * 根据id删除日报问题统计
	 * @param id
	 */
	void deleteReportIssueById(String id);

	
	/**
	 * 获得人员信息
	 * @param string
	 * @return
	 */
	List<Map<String, Object>> getHasPhotoWorkersByProjUid(String string);

	
	/**
	 * 更新月报审批状态
	 * @param id
	 */
	void updateReportStatusById(String id, Integer status);

}
