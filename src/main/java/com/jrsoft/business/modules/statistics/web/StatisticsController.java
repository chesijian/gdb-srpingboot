package com.jrsoft.business.modules.statistics.web;

import com.jrsoft.business.common.util.ExportExcel;
import com.jrsoft.business.modules.statistics.domain.ExportContext;
import com.jrsoft.business.modules.statistics.domain.ExportStrategy;
import com.jrsoft.business.modules.statistics.domain.ProjectRanking;
import com.jrsoft.business.modules.statistics.domain.SheetData;
import com.jrsoft.business.modules.statistics.query.ProjApplicationQuery;
import com.jrsoft.business.modules.statistics.service.StatisticsService;
import com.jrsoft.engine.base.web.BaseController;
import com.jrsoft.engine.base.model.ReturnJson;
import com.jrsoft.engine.base.model.ReturnPageJson;

import com.jrsoft.engine.exception.EngineException;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 官方文档：http://swagger.io/docs/specification/api-host-and-base-path/
 */
@Api(value = "StatisticsController", description = "集团大屏统计接口")
@RestController
@RequestMapping(value = "/api_v1/modules/statistics")
public class StatisticsController extends BaseController {

	@Autowired
	private StatisticsService statisticsService;

	@ApiOperation(value="获取项目分布情况", notes="获取项目分布情况")
	@ApiResponses({ @ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
	@ApiImplicitParams({
			@ApiImplicitParam(name = "search", value = "查询键",  dataType = "String",paramType = "query")
	})
	@RequestMapping(value={"projectDistribution"}, method= RequestMethod.GET)
	public ReturnPageJson<Map<String,Object>> getDistributionNum(String checkType) {
		ReturnPageJson returnJson = null;
		try{
			Map<String,Object> result = new HashMap<>();
			Map<String,Object> distributionObj = statisticsService.getDistributionNum();//项目分布
			List<Map<String,Object>> projDistr = statisticsService.getProjMapDistribution();//业态分析
			result.put("projDistribution",distributionObj);
			result.put("projDistr",projDistr);
			returnJson = new ReturnPageJson();
			returnJson.setData(result);
			//returnJson.setTotalCount(pageInfo.getTotal());
		}catch (EngineException e){
			e.printStackTrace();
			returnJson = new ReturnPageJson(e.errcode,e.getMessage());
		}catch (Exception e){
			e.printStackTrace();
			returnJson = new ReturnPageJson(EngineException.ERRCODE_EXCEPTION,e.getMessage());
		}finally {
			return returnJson;
		}
	}


	@ApiOperation(value="获取检查指标排行数据", notes="获取检查指标排行数据")
	@ApiImplicitParam(name = "checkType", value = "检查类型", required = true, dataType = "String",paramType = "path")
	@RequestMapping(value="/checkRanking", method= RequestMethod.GET)
	public ReturnPageJson<List<ProjectRanking>> getCheckRanking(String checkType, int pageIndex, int pageSize, String classify, String orderType) {
		ReturnPageJson returnJson = null;
		try{
			returnJson = statisticsService.getCheckRanking(checkType,pageIndex,pageSize,classify,orderType);

		}catch (EngineException e){
			e.printStackTrace();
			returnJson = new ReturnPageJson(e.errcode,e.getMessage());
		}catch (Exception e){
			e.printStackTrace();
			returnJson = new ReturnPageJson(EngineException.ERRCODE_EXCEPTION,e.getMessage());
		}finally {
			return returnJson;
		}
	}


	@ApiOperation(value="获取检查项分布数据", notes="获取检查项分布数据")
	@ApiImplicitParam(name = "checkType", value = "检查类型", required = true, dataType = "String",paramType = "path")
	@RequestMapping(value="/checkItemRanking", method= RequestMethod.GET)
	public ReturnJson<ProjectRanking> getByName(String checkType, int pageIndex, int pageSize, String projUid, String areaUid, String checkItemUid, String status) {
		ReturnJson returnJson = null;
		try{
			returnJson = statisticsService.getCheckItemRanking(checkType,pageIndex,pageSize,projUid,areaUid,checkItemUid,status);
		}catch (EngineException e){
			e.printStackTrace();
			returnJson = ReturnJson.error(e.errcode,e.getMessage());
		}catch (Exception e){
			e.printStackTrace();
			returnJson = ReturnJson.error(EngineException.ERRCODE_EXCEPTION,e.getMessage());
		}finally {
			return returnJson;
		}
	}

	@ApiOperation(value="获取项目进度逾期率排行数据", notes="获取项目进度逾期率排行数据")
	@ApiImplicitParam(name = "id", value = "ID_", required = true, dataType = "String",paramType = "path")
	@RequestMapping(value="/projProgressRanking", method= RequestMethod.GET)
	public ReturnPageJson<List<ProjectRanking>> projProgressRanking(String orderType, int pageIndex, int pageSize, String classify) {
		ReturnPageJson returnJson = null;
		try{
			returnJson = statisticsService.getProjProgressRanking(orderType,pageIndex,pageSize,classify);

		}catch (EngineException e){
			e.printStackTrace();
			returnJson = new ReturnPageJson(e.errcode,e.getMessage());
		}catch (Exception e){
			e.printStackTrace();
			returnJson = new ReturnPageJson(EngineException.ERRCODE_EXCEPTION,e.getMessage());
		}finally {
			return returnJson;
		}
	}

	@ApiOperation(value="获取项目进度排行数据", notes="获取检查指标排行数据")
	@ApiImplicitParam(name = "id", value = "ID_", required = true, dataType = "String",paramType = "path")
	@RequestMapping(value="/projProgress", method= RequestMethod.GET)
	public ReturnPageJson<List<ProjectRanking>> projProgress(int pageIndex, int pageSize, String classify) {
		ReturnPageJson returnJson = null;
		try{
			returnJson = statisticsService.getProjProgressList(pageIndex,pageSize,classify);

		}catch (EngineException e){
			e.printStackTrace();
			returnJson = new ReturnPageJson(e.errcode,e.getMessage());
		}catch (Exception e){
			e.printStackTrace();
			returnJson = new ReturnPageJson(EngineException.ERRCODE_EXCEPTION,e.getMessage());
		}finally {
			return returnJson;
		}
	}

	@ApiOperation(value="获取检查数量统计情况", notes="获取检查数量统计情况")
	@ApiResponses({ @ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
	@ApiImplicitParams({
			@ApiImplicitParam(name = "search", value = "查询键",  dataType = "String",paramType = "query")
	})
	@RequestMapping(value={"checkCount"}, method= RequestMethod.GET)
	public ReturnPageJson<Map<String,Object>> getCheckCount(String checkType, String dateTime, String projUid) {
		ReturnPageJson returnJson = null;
		try{
			Map<String,Object> checkObj = statisticsService.getCheckCount(checkType,dateTime,projUid);
			returnJson = new ReturnPageJson();
			returnJson.setData(checkObj);
			//returnJson.setTotalCount(pageInfo.getTotal());
		}catch (EngineException e){
			e.printStackTrace();
			returnJson = new ReturnPageJson(e.errcode,e.getMessage());
		}catch (Exception e){
			e.printStackTrace();
			returnJson = new ReturnPageJson(EngineException.ERRCODE_EXCEPTION,e.getMessage());
		}finally {
			return returnJson;
		}
	}

	@ApiOperation(value="获取项目问题统计数据", notes="获取项目问题统计数据")
	@ApiImplicitParam(name = "id", value = "ID_", required = true, dataType = "String",paramType = "path")
	@RequestMapping(value="/checkNumRanking", method= RequestMethod.GET)
	public ReturnPageJson<List<Map<String,Object>>> checkNumRanking(String projUid, String dateType) {
		ReturnPageJson returnJson = null;
		try{
			returnJson = statisticsService.getCheckNumRanking(projUid,dateType);

		}catch (EngineException e){
			e.printStackTrace();
			returnJson = new ReturnPageJson(e.errcode,e.getMessage());
		}catch (Exception e){
			e.printStackTrace();
			returnJson = new ReturnPageJson(EngineException.ERRCODE_EXCEPTION,e.getMessage());
		}finally {
			return returnJson;
		}
	}


	@ApiOperation(value="获取楼栋总览状态分类数据", notes="获取楼栋总览状态分类数据")
	@ApiImplicitParam(name = "id", value = "ID_", required = true, dataType = "String",paramType = "path")
	@RequestMapping(value="/checkStatus", method= RequestMethod.GET)
	public ReturnPageJson<List<Map<String,Object>>> checkStatus(String projUid, String checkType, String areaUid, String checkItemUid, String status) {
		ReturnPageJson returnJson = null;
		try{
			returnJson = statisticsService.getCheckStatusList(projUid,checkType,areaUid,checkItemUid,status);

		}catch (EngineException e){
			e.printStackTrace();
			returnJson = new ReturnPageJson(e.errcode,e.getMessage());
		}catch (Exception e){
			e.printStackTrace();
			returnJson = new ReturnPageJson(EngineException.ERRCODE_EXCEPTION,e.getMessage());
		}finally {
			return returnJson;
		}
	}

	@ApiOperation(value="获取工序数量统计情况", notes="获取工序数量统计情况")
	@ApiResponses({ @ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
	@ApiImplicitParams({
			@ApiImplicitParam(name = "search", value = "查询键",  dataType = "String",paramType = "query")
	})
	@RequestMapping(value={"procedureCount"}, method= RequestMethod.GET)
	public ReturnPageJson<Map<String,Object>> procedureCount(String projUid) {
		ReturnPageJson returnJson = null;
		try{
			Map<String,Object> procedureObj = statisticsService.getProcedureCount(projUid);
			returnJson = new ReturnPageJson();
			returnJson.setData(procedureObj);
			//returnJson.setTotalCount(pageInfo.getTotal());
		}catch (EngineException e){
			e.printStackTrace();
			returnJson = new ReturnPageJson(e.errcode,e.getMessage());
		}catch (Exception e){
			e.printStackTrace();
			returnJson = new ReturnPageJson(EngineException.ERRCODE_EXCEPTION,e.getMessage());
		}finally {
			return returnJson;
		}
	}

	@ApiOperation(value="获取实测实量数量统计情况", notes="获取实测实量数量统计情况")
	@ApiResponses({ @ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
	@ApiImplicitParams({
			@ApiImplicitParam(name = "search", value = "查询键",  dataType = "String",paramType = "query")
	})
	@RequestMapping(value={"measureCount"}, method= RequestMethod.GET)
	public ReturnPageJson<Map<String,Object>> measureCount(String projUid) {
		ReturnPageJson returnJson = null;
		try{
			Map<String,Object> procedureObj = statisticsService.getMeasureCount(projUid);
			returnJson = new ReturnPageJson();
			returnJson.setData(procedureObj);
			//returnJson.setTotalCount(pageInfo.getTotal());
		}catch (EngineException e){
			e.printStackTrace();
			returnJson = new ReturnPageJson(e.errcode,e.getMessage());
		}catch (Exception e){
			e.printStackTrace();
			returnJson = new ReturnPageJson(EngineException.ERRCODE_EXCEPTION,e.getMessage());
		}finally {
			return returnJson;
		}
	}

	@ApiOperation(value="获取项目进度图表数据", notes="获取项目进度图表数据")
	@ApiImplicitParam(name = "id", value = "ID_", required = true, dataType = "String",paramType = "path")
	@RequestMapping(value="/progressChart", method= RequestMethod.GET)
	public ReturnPageJson<Map<String,Object>> progressChart(String projUid) {
		ReturnPageJson returnJson = null;
		try{
			Map<String,Object> data = statisticsService.getProgressChart(projUid);
			returnJson = new ReturnPageJson();
			returnJson.setData(data);
		}catch (EngineException e){
			e.printStackTrace();
			returnJson = new ReturnPageJson(e.errcode,e.getMessage());
		}catch (Exception e){
			e.printStackTrace();
			returnJson = new ReturnPageJson(EngineException.ERRCODE_EXCEPTION,e.getMessage());
		}finally {
			return returnJson;
		}
	}

	@ApiOperation(value="集团大屏业态分析数据", notes="集团大屏业态分析数据")
	@ApiImplicitParam(name = "id", value = "ID_", required = true, dataType = "String",paramType = "path")
	@RequestMapping(value="/yeTaiAnalyze", method= RequestMethod.GET)
	public ReturnPageJson<List<Map<String,Object>>> yeTaiAnalyze(String checkType) {
		ReturnPageJson returnJson = null;
		try{
			List<Map<String,Object>> data = statisticsService.getYeTaiAnalyze(checkType);
			returnJson = new ReturnPageJson();
			returnJson.setData(data);
		}catch (EngineException e){
			e.printStackTrace();
			returnJson = new ReturnPageJson(e.errcode,e.getMessage());
		}catch (Exception e){
			e.printStackTrace();
			returnJson = new ReturnPageJson(EngineException.ERRCODE_EXCEPTION,e.getMessage());
		}finally {
			return returnJson;
		}
	}

	@ApiOperation(value="检查查询汇总表", notes="检查查询汇总表")
	@ApiImplicitParam(name = "id", value = "ID_", required = true, dataType = "String",paramType = "path")
	@RequestMapping(value="/checkNumCollect", method= RequestMethod.GET)
	public ReturnPageJson<List<Map<String,Object>>> checkNumCollect(String projUid, String checkType, int pageIndex, int pageSize, String startDate, String endDate) {
		ReturnPageJson returnJson = null;
		try{
			returnJson = statisticsService.getCheckNumCollect(projUid,checkType,pageIndex,pageSize,startDate,endDate);

		}catch (EngineException e){
			e.printStackTrace();
			returnJson = new ReturnPageJson(e.errcode,e.getMessage());
		}catch (Exception e){
			e.printStackTrace();
			returnJson = new ReturnPageJson(EngineException.ERRCODE_EXCEPTION,e.getMessage());
		}finally {
			return returnJson;
		}
	}
	@ApiOperation(value="实测实量查询汇总表", notes="实测实量查询汇总表")
	@ApiImplicitParam(name = "id", value = "ID_", required = true, dataType = "String",paramType = "path")
	@RequestMapping(value="/measureNumCollect", method= RequestMethod.GET)
	public ReturnPageJson<List<Map<String,Object>>> measureNumCollect(String projUid, String startDate, String endDate, int pageIndex, int pageSize) {
		ReturnPageJson returnJson = null;
		try{
			returnJson = statisticsService.getMeasureNumCollect(projUid,startDate,endDate,pageIndex,pageSize);

		}catch (EngineException e){
			e.printStackTrace();
			returnJson = new ReturnPageJson(e.errcode,e.getMessage());
		}catch (Exception e){
			e.printStackTrace();
			returnJson = new ReturnPageJson(EngineException.ERRCODE_EXCEPTION,e.getMessage());
		}finally {
			return returnJson;
		}
	}

	@ApiOperation(value="会议任务查询报表", notes="会议任务查询报表")
	@ApiImplicitParam(name = "id", value = "ID_", required = true, dataType = "String",paramType = "path")
	@RequestMapping(value="/taskCollect", method= RequestMethod.GET)
	public ReturnPageJson<List<Map<String,Object>>> taskCollect(String projUid, String taskStatus, int pageIndex, int pageSize, String search) {
		ReturnPageJson returnJson = null;
		try{
			returnJson = statisticsService.getTaskCollect(projUid,taskStatus,pageIndex,pageSize,search);

		}catch (EngineException e){
			e.printStackTrace();
			returnJson = new ReturnPageJson(e.errcode,e.getMessage());
		}catch (Exception e){
			e.printStackTrace();
			returnJson = new ReturnPageJson(EngineException.ERRCODE_EXCEPTION,e.getMessage());
		}finally {
			return returnJson;
		}
	}

	@ApiOperation(value="问题检查项分布报表", notes="问题检查项分布报表")
	@ApiImplicitParam(name = "id", value = "ID_", required = true, dataType = "String",paramType = "path")
	@RequestMapping(value="/checkItemCollect", method= RequestMethod.GET)
	public ReturnPageJson<List<Map<String,Object>>> checkItemCollect(String projUid, String checkType, int pageIndex, int pageSize) {
		ReturnPageJson returnJson = null;
		try{
			returnJson = statisticsService.getCheckItemCollect(checkType,pageIndex,pageSize);

		}catch (EngineException e){
			e.printStackTrace();
			returnJson = new ReturnPageJson(e.errcode,e.getMessage());
		}catch (Exception e){
			e.printStackTrace();
			returnJson = new ReturnPageJson(EngineException.ERRCODE_EXCEPTION,e.getMessage());
		}finally {
			return returnJson;
		}
	}

	@ApiOperation(value="问题楼栋分布报表", notes="问题楼栋分布报表")
	@ApiImplicitParam(name = "id", value = "ID_", required = true, dataType = "String",paramType = "path")
	@RequestMapping(value="/areaProblemCollect", method= RequestMethod.GET)
	public ReturnPageJson<List<Map<String,Object>>> areaProblemCollect(String projUid, String checkType, int pageIndex, int pageSize) {
		ReturnPageJson returnJson = null;
		try{
			returnJson = statisticsService.getAreaProblemCollect(checkType,pageIndex,pageSize);

		}catch (EngineException e){
			e.printStackTrace();
			returnJson = new ReturnPageJson(e.errcode,e.getMessage());
		}catch (Exception e){
			e.printStackTrace();
			returnJson = new ReturnPageJson(EngineException.ERRCODE_EXCEPTION,e.getMessage());
		}finally {
			return returnJson;
		}
	}

	@ApiOperation(value="项目区域报表", notes="项目区域报表")
	@ApiImplicitParam(name = "id", value = "ID_", required = true, dataType = "String",paramType = "path")
	@RequestMapping(value="/projAreaReport", method= RequestMethod.GET)
	public ReturnPageJson<List<Map<String,Object>>> projAreaReport(String projUid, String checkType, String yeTai, int pageIndex, int pageSize) {
		ReturnPageJson returnJson = null;
		try{
			returnJson = statisticsService.getProjAreaReport(projUid,checkType,yeTai,pageIndex,pageSize);

		}catch (EngineException e){
			e.printStackTrace();
			returnJson = new ReturnPageJson(e.errcode,e.getMessage());
		}catch (Exception e){
			e.printStackTrace();
			returnJson = new ReturnPageJson(EngineException.ERRCODE_EXCEPTION,e.getMessage());
		}finally {
			return returnJson;
		}
	}

	/**
	 * 导出集团报表
	 * @author: 车斯剑
	 * @date: 2020年1月14日 上午10:41:23
	 * @param response
	 */
	@RequestMapping(value={"exportReport"}, method= RequestMethod.GET)
	public void exportReport(HttpServletResponse response, String fileName, String dataType, String params){
		try {
			ExportContext commandContext = new ExportContext();
			ExportStrategy commandStrategy = commandContext.getInstance(dataType);
			List<SheetData> data = new ArrayList<>();
			SheetData sheet = commandStrategy.process(fileName,dataType,params);
			data.add(sheet);
			ExportExcel ex = new ExportExcel();
			String zipname = "顺丰工程现场管理报表.xls";
			zipname = java.net.URLDecoder.decode(zipname,"UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/octet-stream; charset=UTF-8");
			response.addHeader("Content-Disposition", "attachment; filename=\"" + new String(zipname.getBytes("UTF-8"), "ISO8859-1") + "\";");
			OutputStream os = response.getOutputStream();
			ex.exportListExcel(os,  data);
		}catch (EngineException e){
			e.printStackTrace();
		}catch (Exception e){
			e.printStackTrace();
		}
	}


	/**
	 * 导出集团报表post请求方式(暂不用)
	 * @author: 车斯剑
	 * @date: 2020年1月14日 上午10:41:23
	 * @param response
	 */
	@RequestMapping(value={"exportReport_bak"}, method= RequestMethod.POST)
	public void exportReport_bak(HttpServletResponse response, String fileName, String dataType, String params){
		try {
			ExportContext commandContext = new ExportContext();
			ExportStrategy commandStrategy = commandContext.getInstance(dataType);
			List<SheetData> data = new ArrayList<>();
			SheetData sheet = commandStrategy.process(fileName,dataType,params);
			data.add(sheet);
			ExportExcel ex = new ExportExcel();
			String zipname = "顺丰工程现场管理报表.xls";
			zipname = java.net.URLDecoder.decode(zipname,"UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/octet-stream; charset=UTF-8");
			response.addHeader("Content-Disposition", "attachment; filename=\"" + new String(zipname.getBytes("UTF-8"), "ISO8859-1") + "\";");
			OutputStream os = response.getOutputStream();
			ex.exportListExcel(os,  data);
		}catch (EngineException e){
			e.printStackTrace();
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	@ApiOperation(value="获取应用统计数据", notes="获取应用统计数据")
	@ApiImplicitParam(name = "id", value = "ID_", required = true, dataType = "String",paramType = "path")
	@RequestMapping(value="/applications", method= RequestMethod.GET)
	public ReturnJson<Map<String,Object>> getApplicationNums() {
		ReturnJson returnJson = null;
		try{
			Map<String,Object> data = statisticsService.getApplicationNums();
			returnJson = ReturnJson.ok(data);
			return returnJson;

		}catch (EngineException e){
			e.printStackTrace();
			returnJson = new ReturnJson(e.errcode,e.getMessage());
		}catch (Exception e){
			e.printStackTrace();
			returnJson = new ReturnJson(EngineException.ERRCODE_EXCEPTION,e.getMessage());
		}finally {
			return returnJson;
		}
	}

	@ApiOperation(value="获取单个应用统计数据", notes="获取单个应用统计数据")
	@ApiImplicitParam(name = "id", value = "ID_", required = true, dataType = "String",paramType = "path")
	@RequestMapping(value="/application", method= RequestMethod.GET)
	public ReturnPageJson<List<Map<String,Object>>> getApplicationInfo(String tableName, int pageIndex, int pageSize, String param) {
		ReturnPageJson returnJson = null;
		try{
			returnJson = statisticsService.getApplicationInfo(tableName,pageIndex,pageSize,param);

		}catch (EngineException e){
			e.printStackTrace();
			returnJson = new ReturnPageJson(e.errcode,e.getMessage());
		}catch (Exception e){
			e.printStackTrace();
			returnJson = new ReturnPageJson(EngineException.ERRCODE_EXCEPTION,e.getMessage());
		}finally {
			return returnJson;
		}
	}

	/**
	 * 导出应用明细报表
	 * @author: 车斯剑
	 * @date: 2020年6月19日 上午10:41:23
	 * @param tableName
	 */
	@RequestMapping(value={"exportApplicationInfo"}, method= RequestMethod.GET)
	public void exportApplicationInfo(HttpServletResponse response, String tableName, String param, String fileName){
		statisticsService.exportApplicationInfo(response,tableName,param,fileName);
	}

	@ApiOperation(value="获取项目详情", notes="获取项目详情")
	@ApiImplicitParam(name = "id", value = "ID_", required = true, dataType = "String",paramType = "path")
	@RequestMapping(value="/projStatistics", method= RequestMethod.GET)
	public ReturnPageJson<List<Map<String,Object>>> getProjStatistics(String tableName, int pageIndex, int pageSize, String param, String columnName, String projUid) {
		ReturnPageJson returnJson = null;
		try{
			returnJson = statisticsService.getProjStatistics(tableName,pageIndex,pageSize,param,columnName,projUid);

		}catch (EngineException e){
			e.printStackTrace();
			returnJson = new ReturnPageJson(e.errcode,e.getMessage());
		}catch (Exception e){
			e.printStackTrace();
			returnJson = new ReturnPageJson(EngineException.ERRCODE_EXCEPTION,e.getMessage());
		}finally {
			return returnJson;
		}
	}

	@ApiOperation(value="获取项目详情", notes="获取项目详情")
	@ApiImplicitParam(name = "id", value = "ID_", required = true, dataType = "ProjApplicationQuery",paramType = "query")
	@RequestMapping(value="/projApplication", method= RequestMethod.GET)
	public ReturnPageJson<List<Map<String,Object>>> getProjApplication(ProjApplicationQuery query) {
		ReturnPageJson returnJson = null;
		try{
			returnJson = statisticsService.getProjApplication(query);

		}catch (EngineException e){
			e.printStackTrace();
			returnJson = new ReturnPageJson(e.errcode,e.getMessage());
		}catch (Exception e){
			e.printStackTrace();
			returnJson = new ReturnPageJson(EngineException.ERRCODE_EXCEPTION,e.getMessage());
		}finally {
			return returnJson;
		}
	}
}
