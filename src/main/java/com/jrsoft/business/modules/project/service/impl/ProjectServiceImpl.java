package com.jrsoft.business.modules.project.service.impl;

import java.text.SimpleDateFormat;
import java.util.*;

import com.jrsoft.business.modules.construction.dao.MessageDao;
import com.jrsoft.business.modules.project.query.ProjectQuery;
import com.jrsoft.engine.base.model.ReturnPageJson;
import com.jrsoft.engine.common.utils.RoleUtil;
import com.jrsoft.engine.exception.EngineException;
import com.jrsoft.engine.model.org.OrgRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jrsoft.business.modules.project.dao.ProjectDao;
import com.jrsoft.business.modules.project.service.ProjectService;
import com.jrsoft.engine.common.utils.CommonUtil;
import com.jrsoft.engine.common.utils.SessionUtil;



/**
 * 项目立项业务实现类
 * @author eric
 *
 */
@Service
@Primary
@Transactional
public class ProjectServiceImpl implements ProjectService{
	@Autowired
	private ProjectDao proDao;
	@Autowired
	private MessageDao messageDao;

	/**
	 * 根据当前用户查询项目列表
	 * @param query	搜索框
	 * @return
	 */
	@Override
	public ReturnPageJson<List<Map<String, Object>>> getCurUserProjectLists(ProjectQuery query) {
		String companyUid = SessionUtil.getCompanyUid();
		String userUid = SessionUtil.getUserUid();

		ReturnPageJson returnJson = new ReturnPageJson();
		List<Map<String, Object>> data = proDao.searchProjList(query);
		if(data.size() > 0) {
			returnJson.setTotalCount((Long) data.get(0).get("totalCount"));
		}
		returnJson.setData(data);
		return returnJson;
	}

	@Override
	public Map<String, Object> getProjectById(String id) {
		String dbType = CommonUtil.getDatabaseType();
		if(dbType.equals("mysql")) {
			return proDao.searchProjDetailsById(id);
		}else {
			return proDao.getProjectById(id);
		}
	}

	@Override
	public List<Map<String, Object>> searchTreamGroup(String companyUid, String userUid, String search, int start,
			int end, boolean isCompanyAdmin, String projUid) {
		return proDao.searchTreamGroup(companyUid, userUid, search, start, end, isCompanyAdmin, projUid);
	}

	@Override
	public Map<String, Object> searchTreamGroupById(String id) {
		return proDao.searchTreamGroupById(id);
	}

	
	@Override
	public ReturnPageJson<List<Map<String, Object>>> searchWorkPartList(String projUid, String userUid, String companyUid, String search, int pageIndex,
			int pageSize, boolean isCompanyAdmin, String parentUid, String dbType) {
		ReturnPageJson returnJson = null;
		List<Map<String, Object>> data = null;
		try {
			int start = (pageIndex - 1) * pageSize;
			int totalcount =0;
			if(dbType.equals("mysql")) {
				data = proDao.searchWorkPartListMySql(projUid, userUid, companyUid, search, start, pageSize, isCompanyAdmin, parentUid);
				totalcount = proDao.getWorkPartListTotalCount(projUid, userUid, companyUid, search, isCompanyAdmin, parentUid);
			}else if(dbType.equals("mssql")) {
				start = start +1;
				int end = pageIndex * pageSize;
				data = proDao.searchWorkPartList(projUid, userUid, companyUid, search, start, end, isCompanyAdmin, parentUid);
			}
			returnJson = new ReturnPageJson();
			returnJson.setData(data);
			returnJson.setTotalCount(totalcount);
		} catch (EngineException e) {
			e.printStackTrace();
			returnJson = new ReturnPageJson(e.errcode, e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			returnJson = new ReturnPageJson(EngineException.ERRCODE_EXCEPTION, e.getMessage());
		} finally {
			return returnJson;
		}

	}

	@Override
	public List<Map<String, Object>> searchWorkPartNext(String id) {
		return proDao.searchWorkPartNext(id);
	}

	@Override
	public List<Map<String, Object>> returnSuperior(String superiorId, String projUid) {
		// 根据上级id查询上一级id
		Map<String, Object> superiorMap = proDao.superior(superiorId);
		//根据上一级id查询
		String id = (String) superiorMap.get("ID_");
		List<Map<String, Object>> listMap = proDao.returnSuperior(id, projUid);
		
		return listMap;
	}

	@Override
	public void deleteProjectById(String id) {
		proDao.deleteProjectById(id);
	}
	
	/**
	 * 删除部位以及所有下级
	 */
	@Override
	public void deletePartById(String id) {
		// 根据id查询parentUid
		proDao.deletePartById(id);
		List<Map<String, Object>> partList = proDao.searchWorkPartNext(id);
		if(partList.size() > 0) {
			for (Map<String, Object> m : partList) {
				String partId = (String) m.get("ID_");
				deletePartById(partId);
			}
		}

		
	}

	private void deleteNextPart(String partId) {
		List<Map<String, Object>> partList = proDao.searchWorkPartNext(partId);
		
		if(partList != null && partList.size() > 0) {
			for (Map<String, Object> m : partList) {
				String partId2 = (String) m.get("ID_");
				proDao.deletePartById(partId2);
				
				deleteNextPart(partId2);
			}
		}
	}

	@Override
	public List<Map<String, Object>> searchWorkLogList(String projUid, String userUid, String companyUid, String search,
			int pageIndex, int pageSize, Integer type, String startDate,String endDate) {
		String dbType = CommonUtil.getDatabaseType();	//获取数据库类型
		if(dbType.equals("mysql")) {
			int starts = (pageIndex - 1) * pageSize;
			List<OrgRole> aa = SessionUtil.getRoleSet();
			boolean isCompanyAdmin = RoleUtil.isCompanyAdmin();
			boolean isProjectManager = RoleUtil.isAllow("ROLE_PROJECT_MANAGER");
			boolean isProjectAdmin = RoleUtil.isAllow(" ROLE_PROJECT_ADMIN");
			boolean isAdmin = false;
			if(isCompanyAdmin||isProjectManager||isProjectAdmin){
				isAdmin =true;
			}

			return proDao.searchWorkLogListMySql(projUid, userUid, companyUid, search, starts, pageSize, isAdmin, type,startDate,endDate);
		}
		/*else if(dbType.equals("mssql")) {
			return proDao.searchWorkLogList(projUid, userUid, companyUid, search, start, end, isCompanyAdmin, type);
		}*/
		return null;
	}

	@Override
	public Map<String, Object> searchLogById(String id, String userUid, String dbType) {
		return proDao.searchLogById(id, userUid, dbType);
	}


	@Override
	public List<Map<String, Object>> searchVisaList(String projUid, String userUid, String companyUid, String search,
			int start, int end, boolean isCompanyAdmin, String type) {
		String dbType = CommonUtil.getDatabaseType();	//获取数据库类型
		if(dbType.equals("mysql")) {
			start = start - 1;
		}
		return proDao.searchVisaList(projUid, userUid, companyUid, search, start, end, isCompanyAdmin, type, dbType);
	}

	@Override
	public Map<String, Object> searchvisaById(String id, String userUid) {
		String dbType = CommonUtil.getDatabaseType();	//获取数据库类型
		return proDao.searchvisaById(id, userUid, dbType);
	}


	@Override
	public Map<String, Object> getMemberByProjUid(String id, String search, String dbType) {
		return proDao.getMemberByProjUid(id, search, dbType);
	}

	@Override
	public List<Map<String, Object>> getMemberByProjUidAndSearch(String projUid, String search, String dbType) {
		return proDao.getMemberByProjUidAndSearch(projUid,search,dbType);
	}

	@Override
	public void deleteTreamGroup(String id) {
		proDao.deleteTreamGroup(id);
	}

	@Override
	public List<Map<String, Object>> searchUser(String[] principal) {
		return proDao.searchUser(principal);
	}


	/**
	 * 根据id查询部位详情
	 */
	@Override
	public List<Map<String, Object>> searchWorkPartDetails(String id) {
		return proDao.searchWorkPartDetails(id);
	}

	@Override
	public Map<String, Object> getNewestProj(String userUid, String companyUid, boolean isCompanyAdmin, String dbType) {
		Map<String, Object> proj = proDao.getUserFristProj(userUid, companyUid,dbType);
		if(proj==null){
			proj =proDao.getNewestProj(userUid, companyUid, isCompanyAdmin, dbType);
		}
		return proj;
	}
	
	
	/**
	 * 根据项目id查询图纸列表
	 */
	@Override
	public List<Map<String, Object>> searchDrawingList(String projUid, String userUid, String companyUid, String search,
			int start, int end, boolean isCompanyAdmin) {
		return proDao.searchDrawingList(projUid, userUid, companyUid, search, start, end, isCompanyAdmin);
	}

	@Override
	public Map<String, Object> searchDrawingById(String id) {
		String dbType = CommonUtil.getDatabaseType();	//获取数据库类型
		return proDao.searchDrawingById(id,dbType);
	}

	@Override
	public void deleteDrawingById(String id) {
		proDao.deleteDrawingById(id);
		proDao.deleteAttachmentByParentId(id);
	}

	@Override
	public List<Map<String, Object>> searchPhotographList(String projUid, String userUid, String companyUid,
			String search, int start, int end, boolean isCompanyAdmin) {
		String dbType = CommonUtil.getDatabaseType();	//获取数据库类型
		if(dbType.equals("mysql")) {
			start = start - 1;
		}
		return proDao.searchPhotographList(projUid, userUid, companyUid, search, start, end, isCompanyAdmin, dbType);
	}

	@Override
	public void deleteLogById(String id) {
		proDao.deleteLogdDetailsById(id);	//删除日志明细
		proDao.deleteLogdById(id);	//删除日志表
		
		messageDao.deleteByBusinessKey(id);
	}

	@Override
	public void deleteVisaById(String id) {
		proDao.deleteVisaDetailsById(id);	//删除签证明细
		proDao.deleteVisaById(id);	//删除签证
		
		messageDao.deleteByBusinessKey(id);
	}

	@Override
	public ReturnPageJson searchReportList(String projUid, String userUid, String companyUid, String search,
										   int start, int end, String type, int dataType, String startDate,String endDate) {
		ReturnPageJson returnJson = new ReturnPageJson();
		String dbType = CommonUtil.getDatabaseType();	//获取数据库类型
		if(dbType.equals("mysql")) {
			int starts = start - 1;
			boolean isCompanyAdmin = RoleUtil.isCompanyAdmin();
			boolean isProjectManager = RoleUtil.isAllow("ROLE_PROJECT_MANAGER");
			boolean isProjectAdmin = RoleUtil.isAllow(" ROLE_PROJECT_ADMIN");
			boolean isAdmin = false;
			if(isCompanyAdmin||isProjectManager||isProjectAdmin){
				isAdmin =true;
			}
			Integer totalCount = proDao.getReportListTotalCount(projUid, userUid, companyUid, search, starts, end,
					isAdmin, type, dataType,startDate,endDate);
			List<Map<String, Object>> data = proDao.searchReportListMySql(projUid, userUid, companyUid, search, starts, end,
					isAdmin, type, dataType,startDate,endDate);
			for (Map<String, Object> map : data) {
				map.put("week", dateToWeek((String) map.get("createTime")));
			}
			returnJson.setData(data);
			returnJson.setTotalCount(totalCount);
		}
		return returnJson;
	}

	public static String dateToWeek(String datetime) {
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
		Calendar cal = Calendar.getInstance(); // 获得一个日历
		Date datet = null;
		try {
			datet = f.parse(datetime);
			cal.setTime(datet);
		} catch (Exception e) {
			e.printStackTrace();
		}
		int w = cal.get(Calendar.DAY_OF_WEEK) - 1; // 指示一个星期中的某天。
		if (w < 0)
			w = 0;
		return weekDays[w];
	}

	
	@Override
	public Map<String, Object> searchProjBriefByProjUid(String projUid) {
		String dbType = CommonUtil.getDatabaseType();
		if(dbType.equals("mysql")) {
			return proDao.searchProjBriefByProjUidMySql(projUid, dbType);
		}else if(dbType.equals("mssql")) {
			return proDao.searchProjBriefByProjUid(projUid, dbType);
		}
		return null;
	}

	@Override
	public List<Map<String, Object>> searchPhotographByProjUid(String projUid, String dbType) {
		return proDao.searchPhotographByProjUid(projUid, dbType);
	}

	@Override
	public List<Map<String, Object>> getPositionByUserIds(String[] ids) {
		return proDao.getPositionByUserIds(ids);
	}

	@Override
	public void deletePhotographById(String id) {
		proDao.deletePhotographById(id);
	}

	@Override
	public void deleteProjHeadPortrait(String fileId, String projUid) {
		proDao.deleteProjHeadPortrait(fileId, projUid);
	}

	@Override
	public Map<String, Object> searchScheduleInfo(String projUid, String dbType) {
		return proDao.searchScheduleInfo(projUid, dbType);
	}

	@Override
	public List<Map<String, Object>> searchHomeNews(String projUid, String dbType) {
		return proDao.searchHomeNews(projUid, dbType);
	}

	@Override
	public Map<String, Object> reportDetailsById(String id, String dbType, String userUid) {
		if(dbType.equals("mysql")) {
			return proDao.reportDetailsByIdMySql(id, dbType, userUid);
		}else if(dbType.equals("mssql")) {
			return proDao.reportDetailsById(id);
		}
		return null;
	}

	@Override
	public void deleteReportById(String id) {
		proDao.deleteReportById(id);
	}

	@Override
	public Integer searchProjectSum(String companyUid) {
		String dbType = CommonUtil.getDatabaseType();	//获取数据库类型
		return proDao.searchProjectSum(companyUid, dbType);
	}

	@Override
	public List<Map<String, Object>> getWorkerInfoByProjUid(String projUid){
		return proDao.getWorkerInfoByProjUid(projUid);
	}

	
	@Override
	public Integer getUsers(String companyUid) {
		String dbType = CommonUtil.getDatabaseType();	//获取数据库类型
		return proDao.getUsers(companyUid, dbType);
	}

	
	@Override
	public List<Map<String, Object>> searchExtProjList(String projUid, String companyUid) {
		return proDao.searchExtProjList(projUid, companyUid);
	}

	@Override
	public List<Map<String, Object>> searchCorpList(String corpId) {
		return proDao.searchCorpList(corpId);
	}

	@Override
	public List<Map<String, Object>> searchParticipationList(String projUid, Integer type) {
		String dbType = CommonUtil.getDatabaseType();	//获取数据库类型
		return proDao.searchParticipationList(projUid, dbType, type);
	}

	@Override
	public void updateProjectCode(String id, String projectCode) {
		proDao.updateProjectCode(id, projectCode);
	}

	@Override
	public List<Map<String, Object>> searchPayrollList(String projUid) {
		return proDao.searchPayrollList(projUid);
	}

	@Override
	public List<Map<String, Object>> searchTeamList(String projUid) {
		return proDao.searchTeamList(projUid);
	}

	@Override
	public void updateTeamCode(String teamId, String teamCode, String projId) {
		proDao.updateTeamCode(teamId,teamCode,projId);
	}

	@Override
	public List<Map<String, Object>> updateTeamList(String projUid) {
//		String[] teamId = teamIds.split(",");
		return proDao.updateTeamList(projUid);
	}

	@Override
	public List<Map<String, Object>> searchWorkerInfoList(String projUid) {
		return proDao.searchWorkerInfoList(projUid, CommonUtil.getDatabaseType());
	}

	@Override
	public List<Map<String, Object>> searchEntryExitInfoList(String projUid) {
		return proDao.searchEntryExitInfoList(projUid);
	}

	@Override
	public List<Map<String, Object>> updateWorkerInfoList(String projUid) {
		return proDao.updateWorkerInfoList(projUid);
	}

	@Override
	public List<Map<String, Object>> searchUploadContractList(String projUid) {
		return proDao.searchUploadContractList(projUid);
	}

	@Override
	public void updateProjectPushStatus(String id) {
		proDao.updateProjectPushStatus(id);
	}

	@Override
	public void updateParticipationPushStatus(String id) {
		proDao.updateParticipationPushStatus(id);
	}

	@Override
	public void updateTeamPushStatus(String id) {
		proDao.updateTeamPushStatus(id);
	}

	@Override
	public void updateTeamRosterInfo(String id) {
		proDao.updateTeamRosterInfo(id);
	}

	@Override
	public void updateEntryExitInfoPushStatus(String id) {
		proDao.updateEntryExitInfoPushStatus(id);
	}

	@Override
	public void updateEntryContractPushStatus(String id) {
		proDao.updateEntryContractPushStatus(id);
	}

	@Override
	public List<Map<String, Object>> searchUploadPayrollList(String projUid) {
		return proDao.searchUploadPayrollList(projUid);
	}

	@Override
	public void updatePayrollCode(String id, String payrollCode) {
		proDao.updatePayrollCode(id, payrollCode);
	}

	@Override
	public List<Map<String, Object>> searchUploadPayrollDetailList(String projUid) {
		return proDao.searchUploadPayrollDetailList(projUid);
	}

	@Override
	public Integer searchPlatformJoint(String projUid, String type, String name, String url, String apiKey, String port) {
		return proDao.searchPlatformJoint(projUid, type, name, url, apiKey, port);
	}

	@Override
	public void updateCorp(String id) {
		proDao.updateCorp(id);
	}

	@Override
	public void updateParticipateInfoStatus(String id) {
		proDao.updateParticipateInfoStatus(id);
	}

	@Override
	public void updateTeamInfoStatus(String id) {
		proDao.updateTeamInfoStatus(id);
	}

	@Override
	public void updateRosterInfoStatus(String id) {
		proDao.updateRosterInfoStatus(id);
	}

	@Override
	public Map<String, Object> getSocietyCode(String companyUid) {
		return proDao.getSocietyCode(companyUid);
	}

	@Override
	public Map<String, Object> searchReportInspect(String projUid, String id, String date) {
		Map<String, Object> result = new HashMap<String, Object>();
		//查询日报问题统计
		Map<String, Object> issueLsit = new HashMap<String, Object>();
		String userUid = SessionUtil.getUserUid();
		if(id != null) {
			issueLsit = proDao.searchLogById(id, SessionUtil.getUserUid(), CommonUtil.getDatabaseType());
//					issueLsit = proDao.searchReportIssueList(id);
			date = (String)issueLsit.get("DATE_");
			projUid = (String)issueLsit.get("PROJ_UID_");
			userUid = (String)issueLsit.get("CREATE_USER_");
		}
		//质量检查
		List<Map<String, Object>> quality = proDao.searchQualityAndSafetyInspect(projUid, date, "quality", "inspect",userUid);
		//安全检查
		List<Map<String, Object>> safety = proDao.searchQualityAndSafetyInspect(projUid, date, "safety", "inspect",userUid);
		//实测实量检查
		List<Map<String, Object>> measured = proDao.searchQualityAndSafetyInspect(projUid, date, "", "measure",userUid);
		//工序检查
		List<Map<String, Object>> process = proDao.searchQualityAndSafetyInspect(projUid, date, "", "process",userUid);
		//材料验收
		List<Map<String, Object>> material = proDao.searchMaterialInspect(projUid, date );
		
		
		result.put("quality", quality);
		result.put("safety", safety);
		result.put("measured", measured);
		result.put("process", process);
		result.put("material", material);
		result.put("details", issueLsit);
		
		return result;
	}

	
	@Override
	public void deleteReportIssueById(String id) {
		proDao.deleteReportIssueById(id);
	}

	@Override
	public List<Map<String, Object>> getHasPhotoWorkersByProjUid(String projUid) {
		return proDao.getHasPhotoWorkersByProjUid(projUid);
	}

	
	@Override
	public void updateReportStatusById(String id, Integer status) {
		proDao.updateReportStatusById(id, status);
	}
}
