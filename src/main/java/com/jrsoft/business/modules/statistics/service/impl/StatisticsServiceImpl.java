package com.jrsoft.business.modules.statistics.service.impl;

import com.jrsoft.business.common.domain.ExcelTitle;
import com.jrsoft.business.common.util.ExportExcel;
import com.jrsoft.business.modules.progress.dao.ProgressDao;
import com.jrsoft.business.modules.project.dao.ProjectDao;
import com.jrsoft.business.modules.statistics.dao.StatisticsDao;
import com.jrsoft.business.modules.statistics.domain.ProjectRanking;
import com.jrsoft.business.modules.statistics.domain.SheetData;
import com.jrsoft.business.modules.statistics.query.ProjApplicationQuery;
import com.jrsoft.business.modules.statistics.service.StatisticsService;
import com.jrsoft.engine.base.model.ReturnPageJson;
import com.jrsoft.engine.common.utils.SessionUtil;
import com.jrsoft.engine.exception.EngineException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 课本类处理
 *
 * @author Blueeyedboy
 * @create 2018-10-13 16:00
 **/

@Service
@Transactional
public class StatisticsServiceImpl implements StatisticsService {

	@Autowired
	private StatisticsDao statisticsDao;
	@Autowired
	private ProjectDao proDao;
	@Autowired
	private ProgressDao progressDao;

	@Override
	public ProjectRanking findByName(String name) {
		ProjectRanking book = statisticsDao.findByName(name);
		return book;
	}

	@Override
	public Map<String, Object> getDistributionNum() {
		String companyUid = SessionUtil.getCompanyUid();
		Map<String, Object> result = new HashMap<>();
		int totalCount = 0;
		List<Map<String, Object>> distributionNumList =statisticsDao.getDistributionNum(companyUid);
		List<Map<String, Object>>  purchaseNumList =statisticsDao.getclassifyNumList(companyUid,"自购地");
		List<Map<String, Object>> leaseNumList =statisticsDao.getclassifyNumList(companyUid,"租赁");
		for (Map<String, Object> map : distributionNumList) {
			totalCount += Integer.valueOf(String.valueOf(map.get("numCount")));
		}
		result.put("totalCount",totalCount);
		result.put("dataList",distributionNumList);
		result.put("purchaseNumList",purchaseNumList);
		result.put("leaseNumList",leaseNumList);
		return result;
	}

	@Override
	public ReturnPageJson<List<ProjectRanking>> getCheckRanking(String checkType, int pageIndex, int pageSize, String classify, String orderType) {
		String companyUid = SessionUtil.getCompanyUid();
		int start = (pageIndex - 1) * pageSize;
		List<ProjectRanking> data = statisticsDao.getCheckRanking(companyUid,checkType,start,pageSize,classify,orderType);
		int totalCount = statisticsDao.getProjectTotalCount(companyUid,classify);
		ReturnPageJson returnJson = new ReturnPageJson();
		returnJson.setData(data);
		returnJson.setTotalCount(totalCount);
		return returnJson;
	}

	@Override
	public ReturnPageJson<List<Map<String, Object>>> getCheckItemRanking(String checkType, int pageIndex, int pageSize, String projUid, String areaUid, String checkItemUid, String status) {
		String companyUid = SessionUtil.getCompanyUid();
		int start = (pageIndex - 1) * pageSize;
		List<Map<String, Object>> data = statisticsDao.getCheckItemRanking(companyUid,checkType,start,pageSize,projUid,areaUid,checkItemUid,status);
		int totalCount = statisticsDao.getCheckItemRankingTotalCount(companyUid,checkType,projUid,areaUid,checkItemUid,status);
		ReturnPageJson returnJson = new ReturnPageJson();
		returnJson.setData(data);
		returnJson.setTotalCount(totalCount);
		return returnJson;
	}

	@Override
	public ReturnPageJson<List<ProjectRanking>> getProjProgressRanking(String orderType, int pageIndex, int pageSize, String classify) {
		String companyUid = SessionUtil.getCompanyUid();
		int start = (pageIndex - 1) * pageSize;
		List<ProjectRanking> data = statisticsDao.getProjProgressRanking(companyUid,start,pageSize,classify,orderType);
		int totalCount = statisticsDao.getProjectTotalCount(companyUid,classify);
		ReturnPageJson returnJson = new ReturnPageJson();
		returnJson.setData(data);
		returnJson.setTotalCount(totalCount);
		return returnJson;
	}

	@Override
	public ReturnPageJson<List<ProjectRanking>> getProjProgressList(int pageIndex, int pageSize, String classify) {
		String companyUid = SessionUtil.getCompanyUid();
		int start = (pageIndex - 1) * pageSize;
		List<ProjectRanking> data = statisticsDao.getProjProgressList(companyUid,start,pageSize,classify);
		int totalCount = statisticsDao.getProjectTotalCount(companyUid,classify);
		ReturnPageJson returnJson = new ReturnPageJson();
		returnJson.setData(data);
		returnJson.setTotalCount(totalCount);
		return returnJson;
	}

	@Override
	public Map<String, Object> getCheckCount(String checkType, String dateTime, String projUid) {
		return statisticsDao.getCheckCount(checkType,dateTime,projUid);
	}

	@Override
	public ReturnPageJson<List<Map<String, Object>>> getCheckNumRanking(String projUid, String dateType) {
		List<Map<String, Object>> data = statisticsDao.getCheckNumRanking(projUid,dateType);
		int totalCount =0;
		int finishNum =0;
		int onTimeFinishNum =0;
		for (Map<String, Object>  map : data) {
			totalCount += Integer.valueOf(String.valueOf(map.get("totalCount")));
			finishNum += Integer.valueOf(String.valueOf(map.get("finishNum")));
			onTimeFinishNum += Integer.valueOf(String.valueOf(map.get("onTimeFinishNum")));
		}
		//float b = (float)finishNum/totalCount;
		Map<String, Object> totalMap = new HashMap<>();
		totalMap.put("checkType","totalType");
		totalMap.put("totalCount",totalCount);
		totalMap.put("finishPercent",totalCount==0? 0:(float)finishNum/totalCount);
		totalMap.put("onTimePercent",totalCount==0? 0:(float)onTimeFinishNum/totalCount);
		data.add(totalMap);
		ReturnPageJson returnJson = new ReturnPageJson();
		returnJson.setData(data);
		return returnJson;
	}

	@Override
	public ReturnPageJson<List<Map<String, Object>>> getCheckStatusList(String projUid, String checkType, String areaUid, String checkItemUid, String status) {
		List<Map<String, Object>> data = statisticsDao.getCheckStatusList(projUid,checkType,areaUid,checkItemUid,status);
		int totalCount = statisticsDao.getCheckStatusCount(projUid,checkType,areaUid,checkItemUid,status);
		ReturnPageJson returnJson = new ReturnPageJson();
		returnJson.setData(data);
		returnJson.setTotalCount(totalCount);
		return returnJson;
	}

	@Override
	public Map<String, Object> getProcedureCount(String projUid) {
		return statisticsDao.getProcedureCount(projUid);
	}

	@Override
	public Map<String, Object> getMeasureCount(String projUid) {
		return statisticsDao.getMeasureCount(projUid);
	}

	@Override
	public Map<String,Object> getProgressChart(String projUid) {
		Map<String, Object> reult = new HashMap<>();
		List<Map<String, Object>> dataList = new ArrayList<>();
		List<Map<String, Integer>> planData = statisticsDao.getProgressPlanNums(projUid);
		List<Map<String, Integer>> actData = statisticsDao.getProgressActNums(projUid);
		for (int i=1;i<=12;i++){
			Map<String, Object> data = new HashMap<>();
			data.put("monthNum",i+"月");
			for (Map<String, Integer> planMap : planData) {
				if(planMap.get("monthNum")==i){
					data.put("计划",planMap.get("countNum"));
				}
			}
			for (Map<String, Integer> actMap : actData) {
				if(actMap.get("monthNum")==i){
					data.put("实际",actMap.get("countNum"));
				}
			}
			dataList.add(data);
		}
		Map<String, Object> scheduleObj =proDao.searchScheduleInfo(projUid, "mysql");
		Double totalSchedule =Double.valueOf(String.valueOf(scheduleObj.get("schedule")));
		reult.put("dataList",dataList);
		reult.put("totalSchedule",totalSchedule);
		return reult;
	}

	@Override
	public ReturnPageJson<List<Map<String, Object>>> getCheckNumCollect(String projUid, String checkType, int pageIndex, int pageSize, String startDate, String endDate) {
		int start = (pageIndex-1)*pageSize;
		String companyUid = SessionUtil.getCompanyUid();
		List<Map<String, Object>> data = statisticsDao.getCheckNumCollect(companyUid,projUid,checkType,start,pageSize,startDate,endDate);
		int totalCount = statisticsDao.getCheckNumCollectTotalCount(companyUid,projUid,checkType,startDate,endDate);
		ReturnPageJson returnJson = new ReturnPageJson();
		returnJson.setData(data);
		returnJson.setTotalCount(totalCount);
		return returnJson;
	}

	@Override
	public ReturnPageJson<List<Map<String, Object>>> getMeasureNumCollect(String projUid, String startDate, String endDate, int pageIndex, int pageSize) {
		int start = (pageIndex-1)*pageSize;
		String companyUid = SessionUtil.getCompanyUid();
		List<Map<String, Object>> data = statisticsDao.getMeasureNumCollect(companyUid,projUid,startDate,endDate,start,pageSize);
		int totalCount = statisticsDao.getMeasureNumCollectTotalCount(companyUid,projUid,startDate,endDate);
		ReturnPageJson returnJson = new ReturnPageJson();
		returnJson.setData(data);
		return returnJson;
	}

	/**
	 * 集团大屏业态分析数据
	 * @param checkType
	 * @return
	 */
	@Override
	public List<Map<String, Object>> getYeTaiAnalyze(String checkType) {
		String companyUid = SessionUtil.getCompanyUid();
		List<Map<String, Object>> data = statisticsDao.getYeTaiAnalyze(companyUid,checkType);
		return data;
	}

	@Override
	public List<Map<String, Object>> getProjMapDistribution() {
		String companyUid = SessionUtil.getCompanyUid();
		List<Map<String, Object>> data = statisticsDao.getProjMapDistribution(companyUid);
		return data;
	}

	@Override
	public ReturnPageJson<List<Map<String, Object>>> getTaskCollect(String projUid, String taskStatus, int pageIndex, int pageSize, String search) {
		int start = (pageIndex-1)*pageSize;
		String companyUid = SessionUtil.getCompanyUid();
		List<Map<String, Object>> data = statisticsDao.getTaskCollect(companyUid,projUid,taskStatus,start,pageSize,search);
		int totalCount =statisticsDao.getTaskCollectTotalCount(companyUid,projUid,taskStatus,search);
		ReturnPageJson returnJson = new ReturnPageJson();
		returnJson.setData(data);
		returnJson.setTotalCount(totalCount);
		return returnJson;
	}

	/**
	 * 问题检查项分布报表
	 * @param checkType
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	@Override
	public ReturnPageJson<List<Map<String, Object>>> getCheckItemCollect(String checkType, int pageIndex, int pageSize) {
		int start = (pageIndex-1)*pageSize;
		String companyUid = SessionUtil.getCompanyUid();
		List<Map<String, Object>> data = statisticsDao.getCheckItemCollect(companyUid,checkType,start,pageSize);
		int totalCount = statisticsDao.getCheckItemCollectTotalCount(companyUid,checkType);
		ReturnPageJson returnJson = new ReturnPageJson();
		returnJson.setData(data);
		returnJson.setTotalCount(totalCount);
		return returnJson;
	}

	@Override
	public ReturnPageJson<List<Map<String, Object>>> getAreaProblemCollect(String checkType, int pageIndex, int pageSize) {
		int start = (pageIndex-1)*pageSize;
		String companyUid = SessionUtil.getCompanyUid();
		List<Map<String, Object>> data = statisticsDao.getAreaProblemCollect(companyUid,checkType,start,pageSize);
		int totalCount = statisticsDao.getCheckItemCollectTotalCount(companyUid,checkType);
		ReturnPageJson returnJson = new ReturnPageJson();
		returnJson.setData(data);
		returnJson.setTotalCount(totalCount);
		return returnJson;
	}

	/**
	 * 项目区域报表
	 * @param projUid
	 * @param checkType
	 * @param yeTai
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	@Override
	public ReturnPageJson<List<Map<String, Object>>> getProjAreaReport(String projUid, String checkType, String yeTai, int pageIndex, int pageSize) {
		int start = (pageIndex-1)*pageSize;
		List<Map<String, Object>> data = statisticsDao.getProjAreaReport(projUid,checkType,yeTai,start,pageSize);
		ReturnPageJson returnJson = new ReturnPageJson();
		returnJson.setData(data);
		return returnJson;
	}

	/**
	 * 获取应用统计数据
	 * @return
	 */
	@Override
	public Map<String,Object> getApplicationNums() {
		String companyUid = SessionUtil.getCompanyUid();
		return statisticsDao.getApplicationNums(companyUid);
	}

	@Override
	public ReturnPageJson<List<Map<String, Object>>> getApplicationInfo(String tableName, int pageIndex, int pageSize, String param) {
		int start = (pageIndex-1)*pageSize;
		String companyUid = SessionUtil.getCompanyUid();
		List<Map<String, Object>> data = null;
		if(tableName.equals("ga_task")){//进度计划
			data = statisticsDao.getProgressPlanInfo(companyUid,start,pageSize);
		}else if(tableName.equals("ga_task_record")){//进度上报
			data = statisticsDao.getTaskRecord(companyUid, param);
		}else if(tableName.equals("t_inspection_plan")){//检查计划
			data = statisticsDao.getInspectionPlanInfo(companyUid,start,pageSize,param);
		}else{
			data = statisticsDao.getApplicationInfo(companyUid,tableName,start,pageSize,param);
		}

		ReturnPageJson returnJson = new ReturnPageJson();
		returnJson.setData(data);
		return returnJson;
	}

	@Override
	public void exportApplicationInfo(HttpServletResponse response, String tableName, String param, String fileName) {
		String companyUid = SessionUtil.getCompanyUid();
		List<Map<String, Object>> data = null;
		List<SheetData> sheetList = new ArrayList<>();
		try {
			if(tableName.equals("ga_task")){//进度计划
				data = statisticsDao.getProgressPlanInfo(companyUid,0,500);
			}else if(tableName.equals("ga_task_record")){//进度上报
				data = statisticsDao.getTaskRecord(companyUid, param);
			}else if(tableName.equals("t_inspection_plan")){//检查计划
				data = statisticsDao.getInspectionPlanInfo(companyUid,0,500,param);
			}else{
				data = statisticsDao.getApplicationInfo(companyUid,tableName,0,500,param);
			}
			List<ExcelTitle> titles  = new ArrayList<>();
			titles.add(new ExcelTitle("项目名称","projName"));
			titles.add(new ExcelTitle("累计录入数据(条)","totalNum"));
			titles.add(new ExcelTitle("本周新增录入数据(条)","weekNum"));
			SheetData applicationSheet = new SheetData();
			applicationSheet.setDataList(data);
			applicationSheet.setSheetName(fileName);
			applicationSheet.setTitles(titles);
			sheetList.add(applicationSheet);
			ExportExcel ex = new ExportExcel();
			String zipname = "顺丰工地现场管理报表.xls";
			zipname = java.net.URLDecoder.decode(zipname,"UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/octet-stream; charset=UTF-8");
			response.addHeader("Content-Disposition", "attachment; filename=\"" + new String(zipname.getBytes("UTF-8"), "ISO8859-1") + "\";");
			OutputStream os = response.getOutputStream();
			ex.exportListExcel(os,  sheetList);
		}catch (EngineException e){
			e.printStackTrace();
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public ReturnPageJson getProjStatistics(String tableName, int pageIndex, int pageSize, String param, String columnName, String projUid) {
		int start = (pageIndex-1)*pageSize;
		String companyUid = SessionUtil.getCompanyUid();
		List<Map<String, Object>> data = null;
		if(tableName.equals("ga_task_record")){//进度上报
			data = statisticsDao.getTaskRecord(companyUid, param);
		}else{
			data = statisticsDao.getProjStatistics(companyUid,tableName,columnName,start,pageSize,param,projUid);
		}
		int totalCount = statisticsDao.getProjStatisticsTotalCount(companyUid,tableName,param,projUid);
		ReturnPageJson returnJson = new ReturnPageJson();
		returnJson.setData(data);
		returnJson.setTotalCount(totalCount);
		return returnJson;
	}

	/**
	 * 获取项目应用统计
	 * @param query
	 * @return
	 */
	@Override
	public ReturnPageJson getProjApplication(ProjApplicationQuery query) {
		List<Map<String, Object>> data = statisticsDao.getProjApplication(query);
		int totalCount = statisticsDao.getProjectTotalCount(query.getCompanyUid(),"");
		ReturnPageJson returnJson = new ReturnPageJson();
		returnJson.setData(data);
		returnJson.setTotalCount(totalCount);
		return returnJson;
	}


}
