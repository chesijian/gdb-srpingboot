package com.jrsoft.business.modules.construction.web;


import com.jrsoft.business.modules.construction.service.MeasuredService;
import com.jrsoft.engine.base.model.ReturnJson;
import com.jrsoft.engine.base.model.ReturnPageJson;
import com.jrsoft.engine.common.utils.CommonUtil;
import com.jrsoft.engine.common.utils.SessionUtil;
import com.jrsoft.engine.exception.EngineException;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *工地宝 实测实量
 * @author chesj
 *
 */
@Api(value = "MeasuredController", description = "施工宝检查接口")
@RestController
@RequestMapping(value = "/api_v1/construction/measured")
public class MeasuredController {


	@Autowired
	private MeasuredService measuredService;


	/**
	 * 获取区域图纸树数据
	 * @param request
	 * @param parentUid
	 * @return
	 */
	@ApiOperation(value="获取区域图纸树数据", notes="获取区域图纸树数据")
	@ApiResponses({ @ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
	@ApiImplicitParam(name = "projUid", value = "项目Id", required = true, dataType = "String",paramType = "query")
	@RequestMapping(value={"drawingAreaTree"}, method= RequestMethod.GET)
	@ResponseBody
	public ReturnPageJson<List<Map<String, Object>>> getDrawingAreaTree(HttpServletRequest request, String parentUid, String projUid, String search,Integer pageIndex, Integer pageSize){
		ReturnPageJson returnJson = null;
		try {
			String dbType = CommonUtil.getDatabaseType();	//获取数据库类型
			int start = (pageIndex-1)*pageSize + 1;
			int end = pageIndex * pageSize;
			List<Map<String, Object>> data = measuredService.getDrawingAreaTree(projUid,search,parentUid,start, end, dbType);
			returnJson = new ReturnPageJson();
			returnJson.setData(data);
		} catch (EngineException e){
			e.printStackTrace();
			returnJson = new ReturnPageJson(EngineException.ERRCODE_EXCEPTION,e.getMessage());
		}catch (Exception e){
			e.printStackTrace();
			returnJson = new ReturnPageJson(EngineException.ERRCODE_EXCEPTION,e.getMessage());
		}finally {
			return returnJson;
		}
	}

	/**
	 * 获取日志劳动力分析统计图表数据
	 * @param request
	 * @param projUid
	 * @return
	 */
	@ApiOperation(value="获取日志劳动力分析统计图表数据", notes="获取日志劳动力分析统计图表数据")
	@ApiResponses({ @ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
	@ApiImplicitParam(name = "projUid", value = "项目Id", required = true, dataType = "String",paramType = "query")
	@RequestMapping(value={"workLogStatistics"}, method= RequestMethod.GET)
	@ResponseBody
	public ReturnPageJson<List<Map<String, Object>>> workLogStatistics(HttpServletRequest request, String statisticsDate, String projUid){
		ReturnPageJson returnJson = null;
		try {

			List<Map<String, Object>> data = measuredService.getWorkLogStatistics(projUid,statisticsDate);
			returnJson = new ReturnPageJson();
			returnJson.setData(data);
		} catch (EngineException e){
			e.printStackTrace();
			returnJson = new ReturnPageJson(EngineException.ERRCODE_EXCEPTION,e.getMessage());
		}catch (Exception e){
			e.printStackTrace();
			returnJson = new ReturnPageJson(EngineException.ERRCODE_EXCEPTION,e.getMessage());
		}finally {
			return returnJson;
		}
	}
	/**
	 * 获取日志劳动力分析统计详情
	 * @param request
	 * @param projUid
	 * @return
	 */
	@ApiOperation(value="获取日志劳动力分析统计详情", notes="获取日志劳动力分析统计详情")
	@ApiResponses({ @ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
	@ApiImplicitParam(name = "projUid", value = "项目Id", required = true, dataType = "String",paramType = "query")
	@RequestMapping(value={"statistictDetails"}, method= RequestMethod.GET)
	@ResponseBody
	public ReturnPageJson<Map<String, Object>> statistictDetails(HttpServletRequest request, String statisticsDate, String projUid){
		ReturnPageJson returnJson = null;
		try {
			Map<String, Object> result = new HashMap<>();
			List<Map<String, Object>> data = measuredService.getWorkLogDetail(projUid,statisticsDate);
			List<Map<String, Object>> logSum = measuredService.getWorkLogSum(projUid,statisticsDate);
			result.put("data", data);
			result.put("logSum", logSum);
			returnJson = new ReturnPageJson();
			returnJson.setData(result);
		} catch (EngineException e){
			e.printStackTrace();
			returnJson = new ReturnPageJson(EngineException.ERRCODE_EXCEPTION,e.getMessage());
		}catch (Exception e){
			e.printStackTrace();
			returnJson = new ReturnPageJson(EngineException.ERRCODE_EXCEPTION,e.getMessage());
		}finally {
			return returnJson;
		}
	}

	@ApiOperation(value="获取编制计划列表", notes="获取编制计划列表")
	@ApiResponses({ @ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
	@ApiImplicitParams({
			@ApiImplicitParam(name = "search", value = "查询键",  dataType = "String",paramType = "query"),
			@ApiImplicitParam(name = "status", value = "计划状态",  dataType = "String",paramType = "query"),
			@ApiImplicitParam(name = "pageIndex", value = "第几页", required = true,dataType = "int",paramType = "query"),
			@ApiImplicitParam(name = "pageSize", value = "每页显示大小",required = true,  dataType = "int",paramType = "query")
	})
	@RequestMapping(value={"plans"}, method= RequestMethod.GET)
	public ReturnPageJson<List<Map<String,Object>>> getPlans(String companyUid, String projUid, String search,String issueStatus, String status,String type, int pageIndex,int pageSize) {
		ReturnPageJson returnJson = null;
		try{
			int start = (pageIndex-1)*pageSize + 1;
			int end = pageIndex * pageSize;
			if(!CommonUtil.ifNotEmpty(companyUid)){
				companyUid = SessionUtil.getCompanyUid();
			}
			//查询单条数据
			returnJson = measuredService.getPlanLists(companyUid,projUid,search,status,type,start, end,issueStatus);

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
	 * 根据id获取计划编制数据
	 * @param partUid
	 * @param id
	 * @return
	 */
	@ApiOperation(value="根据id获取计划编制数据", notes="根据id获取计划编制数据")
	@ApiResponses({ @ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "数据业务id",  dataType = "String",paramType = "query"),
	})
	@RequestMapping(value={"planDet"}, method= RequestMethod.GET)
	public ReturnPageJson<Map<String, Object>> getCheckItemById(String id,Boolean isQueryPart,String partUid,String projUid){
		ReturnPageJson returnJson = null;
		try {

			List<Map<String, Object>> data = measuredService.getPlanDetById(id,isQueryPart,partUid,projUid);
			returnJson = new ReturnPageJson();
			returnJson.setData(data);
		} catch (Exception e) {
			e.printStackTrace();
			returnJson = new ReturnPageJson(EngineException.ERRCODE_EXCEPTION,e.getMessage());
		}
		return returnJson;
	}

	/**
	 * 删除编制计划
	 * @author 车斯剑
	 * @date 2019年7月5日下午2:32:50
	 * @param ids
	 * @return
	 */
	@ApiOperation(value="删除编制计划", notes="删除编制计划")
	@ApiImplicitParam(name = "ids", value = "计划id集合", required = true, dataType = "String",paramType = "path")
	@RequestMapping(value="/plans/{ids}", method= RequestMethod.DELETE)
	public ReturnJson deletePlans(@PathVariable String ids){
		ReturnJson returnJson = null;
		try {
			String[] idsArr = ids.split(",");
			measuredService.deletePlans(idsArr);
			returnJson = ReturnJson.ok(ids);
		} catch (EngineException e){
			e.printStackTrace();
			returnJson = ReturnJson.error(e.errcode,e.getMessage());
		}catch (Exception e){
			e.printStackTrace();
			returnJson = ReturnJson.error(EngineException.ERRCODE_EXCEPTION,e.getMessage());
		}finally {
			return returnJson;
		}
	}


}
