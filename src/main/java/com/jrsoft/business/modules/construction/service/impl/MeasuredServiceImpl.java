package com.jrsoft.business.modules.construction.service.impl;


import com.jrsoft.business.modules.construction.dao.MeasuredDao;
import com.jrsoft.business.modules.construction.service.MeasuredService;
import com.jrsoft.engine.base.model.ReturnPageJson;
import com.jrsoft.engine.common.utils.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional()
public class MeasuredServiceImpl implements MeasuredService {
	@Autowired
	private MeasuredDao measuredDao;

	@Override
	public List<Map<String, Object>> getDrawingAreaTree(String projUid, String search, String parentUid, 
			int start, int end, String dbType) {
		return measuredDao.getDrawingAreaTree(projUid,search,parentUid,start,end,dbType);
	}

	@Override
	public List<Map<String, Object>> getWorkLogStatistics(String projUid, String statisticsDate) {
		String dbType = CommonUtil.getDatabaseType();	//获取数据库类型
		return  measuredDao.getWorkLogStatistics(projUid,statisticsDate, dbType);
	}

	@Override
	public List<Map<String, Object>> getWorkLogDetail(String projUid, String statisticsDate) {
		String dbType = CommonUtil.getDatabaseType();	//获取数据库类型
		return measuredDao.getWorkLogDetail(projUid,statisticsDate, dbType);
	}

	/**
	 * 获取日志劳动力分析统计详情合计数据
	 * @param projUid
	 * @param statisticsDate
	 * @return
	 */
	@Override
	public List<Map<String, Object>> getWorkLogSum(String projUid, String statisticsDate) {
		String dbType = CommonUtil.getDatabaseType();	//获取数据库类型
		return measuredDao.getWorkLogSum(projUid,statisticsDate, dbType);
	}

	@Override
	public Map<String, Object> getCorpInfo(String companyUid) {
		String dbType = CommonUtil.getDatabaseType();	//获取数据库类型
		return measuredDao.getCorpInfo(companyUid, dbType);
	}

	/**
	 * 获取编制计划列表
	 * @param companyUid
	 * @param projUid
	 * @return
	 */
	@Override
	public ReturnPageJson getPlanLists(String companyUid, String projUid, String search, String status, String type, int start, int end,String issueStatus) {
		String dbType = CommonUtil.getDatabaseType();
		if(dbType.equals("mysql")) {
			start --;
		}
		List<Map<String, Object>> data = measuredDao.getPlanLists(companyUid,projUid,search,status,issueStatus,type,start, end,dbType);
		int total = measuredDao.getPlansTotal(companyUid,projUid,search,status,issueStatus,type,dbType);
		ReturnPageJson returnJson = new ReturnPageJson();
		returnJson.setData(data);
		returnJson.setTotalCount(total);
		return returnJson;
	}

	/**
	 * 删除编制计划
	 * @param ids
	 */
	@Override
	public void deletePlans(String[] ids) {
		measuredDao.deletePlansDet(ids);
		measuredDao.deletePlans(ids);
	}

	/**
	 * 根据id获取计划编制数据
	 * @param id
	 * @return
	 */
	@Override
	public Map<String, Object> getPlanById(String id) {
		String dbType = CommonUtil.getDatabaseType();
		return measuredDao.getPlanById(id,dbType);
	}

	@Override
	public List<Map<String, Object>> getPlanDetById(String id, Boolean isQueryPart,String partUid,String projUid) {
		return measuredDao.queryPlanDet(id,isQueryPart,partUid,projUid);
	}
}
