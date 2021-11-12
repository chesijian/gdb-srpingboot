package com.jrsoft.business.modules.construction.web;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jrsoft.business.modules.construction.domain.CheckItem;
import com.jrsoft.business.modules.construction.domain.CheckRecord;
import com.jrsoft.business.modules.construction.service.InspectService;
import com.jrsoft.business.modules.construction.service.MessageService;
import com.jrsoft.engine.exception.EngineException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.jrsoft.business.common.domain.ExcelTitle;
import com.jrsoft.business.common.util.CustomXWPFDocument;
import com.jrsoft.business.common.util.ExportExcel;
import com.jrsoft.business.common.util.WorderToNewWordUtils;
import com.jrsoft.engine.base.model.ReturnJson;
import com.jrsoft.engine.base.model.ReturnPageJson;
import com.jrsoft.engine.common.utils.CommonUtil;
import com.jrsoft.engine.common.utils.RoleUtil;
import com.jrsoft.engine.common.utils.SessionUtil;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


/**
 * 施工宝检查
 * @author chesj
 *
 */
@Api(value = "InspectController", description = "施工宝检查接口")
@RestController
@RequestMapping(value = "/api_v1/construction/inspect")
public class InspectController {

	@Autowired
	private InspectService inspectService;
	@Autowired
	private MessageService messageService;


	/**
	 * 递归获取检查项目录数据
	 * @param companyUid
	 * @return
	 */
	@ApiOperation(value="获取检查项目录数据", notes="获取检查项目录数据")
	@ApiResponses({ @ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
	@ApiImplicitParams({
			@ApiImplicitParam(name = "companyUid", value = "公司id",  dataType = "String",paramType = "query"),
			@ApiImplicitParam(name = "projUid", value = "项目id",  dataType = "String",paramType = "query"),
			@ApiImplicitParam(name = "parentUid", value = "父级id",  dataType = "String",paramType = "query"),
			@ApiImplicitParam(name = "formType", value = "表单类型",  dataType = "String",paramType = "query"),
			@ApiImplicitParam(name = "start", value = "分页开始位置", required = true,dataType = "int",paramType = "query"),
			@ApiImplicitParam(name = "pageSize", value = "每页显示大小",required = true,  dataType = "int",paramType = "query")
	})
	@RequestMapping(value={"checkItemCatalog"}, method= RequestMethod.GET)
	public ReturnPageJson<List<Map<String,Object>>> getCheckItemCatalog(String companyUid,String formType, String projUid){
		ReturnPageJson returnJson = null;
		try {
			if(!CommonUtil.ifNotEmpty(companyUid)){
				companyUid = SessionUtil.getCompanyUid();
			}
			List<Map<String,Object>> data = inspectService.getCheckItemCatalog(companyUid,formType,projUid);
			returnJson = new ReturnPageJson();
			returnJson.setData(data);
		} catch (EngineException e){
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
	 * 懒加载获取检查项目录数据
	 * @param parentUid
	 * @return
	 */
	@ApiOperation(value="获取检查项目录数据", notes="获取检查项目录数据")
	@ApiResponses({ @ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
	@ApiImplicitParams({
			@ApiImplicitParam(name = "projUid", value = "项目id",  dataType = "String",paramType = "query"),
			@ApiImplicitParam(name = "parentUid", value = "父级id",  dataType = "String",paramType = "query"),
			@ApiImplicitParam(name = "formType", value = "表单类型",  dataType = "String",paramType = "query"),
			@ApiImplicitParam(name = "start", value = "分页开始位置", required = true,dataType = "int",paramType = "query"),
			@ApiImplicitParam(name = "pageSize", value = "每页显示大小",required = true,  dataType = "int",paramType = "query")
	})
	@RequestMapping(value={"inspectItemCatalog"}, method= RequestMethod.GET)
	public ReturnPageJson<List<Map<String,Object>>> getInspectItemCatalog(String formType, String projUid,String parentUid,String type){
		ReturnPageJson returnJson = null;
		try {
			/*if(!CommonUtil.ifNotEmpty(companyUid)){
				companyUid = SessionUtil.getCompanyUid();
			}*/
			List<Map<String,Object>> data = inspectService.getInspectItemCatalog(formType,projUid,parentUid,type);
			returnJson = new ReturnPageJson();
			returnJson.setData(data);
		} catch (EngineException e){
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
	 * 获取检查项列表数据
	 * @param request
	 * @param companyUid
	 * @return
	 */
	@ApiOperation(value="获取检查项列表数据", notes="获取检查项列表数据")
	@ApiResponses({ @ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
	@ApiImplicitParams({
			@ApiImplicitParam(name = "companyUid", value = "公司id",  dataType = "String",paramType = "query"),
			@ApiImplicitParam(name = "parentUid", value = "父级id",  dataType = "String",paramType = "query"),
			@ApiImplicitParam(name = "formType", value = "表单类型",  dataType = "String",paramType = "query"),
			@ApiImplicitParam(name = "start", value = "分页开始位置", required = true,dataType = "int",paramType = "query"),
			@ApiImplicitParam(name = "pageSize", value = "每页显示大小",required = true,  dataType = "int",paramType = "query")
	})
	@RequestMapping(value={"getCheckItemData"}, method= RequestMethod.GET)
	public ReturnPageJson<List<CheckItem>> getCheckItemData(HttpServletRequest request, String companyUid, String parentUid, String formType, String projUid,
															String itemLb, String type, Boolean ifCatalog, Integer pageIndex, Integer pageSize, String search,
															String projLx, String projPurpose){
		ReturnPageJson returnJson = null;
		try{
			if(!CommonUtil.ifNotEmpty(companyUid)){
				companyUid = SessionUtil.getCompanyUid();
			}
			int start = (pageIndex-1)*pageSize;
			List<CheckItem> data = inspectService.getCheckItemData(companyUid,parentUid,formType,projUid,itemLb, start, pageSize,ifCatalog,type,search,projLx,projPurpose);
			int total = inspectService.getCheckItemTotalCount(companyUid,parentUid,formType,projUid,itemLb,ifCatalog,type);
			returnJson = new ReturnPageJson();
			returnJson.setData(data);
			returnJson.setTotalCount(total);
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
	 * 根据id获取检查项数据
	 * @param request
	 * @param id
	 * @return
	 */
	@ApiOperation(value="根据id获取检查项数据", notes="根据id获取检查项数据")
	@ApiResponses({ @ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "数据业务id",  dataType = "String",paramType = "query"),
	})
	@RequestMapping(value={"getCheckItemById"}, method= RequestMethod.GET)
	public ReturnPageJson<Map<String, Object>> getCheckItemById(HttpServletRequest request, String id){
		ReturnPageJson returnJson = null;
		try {

			Map<String, Object> data = inspectService.getCheckItemById(id);
			returnJson = new ReturnPageJson();
			returnJson.setData(data);
		} catch (Exception e) {
			e.printStackTrace();
			returnJson = new ReturnPageJson(EngineException.ERRCODE_EXCEPTION,e.getMessage());
		}
		return returnJson;
	}

	/**
	 * 获取国标检查项目录数据
	 * @param request
	 * @return
	 */
	@ApiOperation(value="获取国标检查项目录数据", notes="获取国标检查项目录数据")
	@ApiResponses({ @ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
	@ApiImplicitParams({
			@ApiImplicitParam(name = "companyUid", value = "公司id",  dataType = "String",paramType = "query"),
			@ApiImplicitParam(name = "parentUid", value = "父级id",  dataType = "String",paramType = "query"),
			@ApiImplicitParam(name = "formType", value = "表单类型",  dataType = "String",paramType = "query"),
			@ApiImplicitParam(name = "start", value = "分页开始位置", required = true,dataType = "int",paramType = "query"),
			@ApiImplicitParam(name = "pageSize", value = "每页显示大小",required = true,  dataType = "int",paramType = "query")
	})
	@RequestMapping(value={"checkItemStandards"}, method= RequestMethod.GET)
	public ReturnPageJson<List<CheckItem>> getCheckItemStandards(HttpServletRequest request, String parentUid){
		ReturnPageJson returnJson = null;
		try {

			List<CheckItem> data = inspectService.getCheckItemStandards(parentUid);
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
	 * 批量删除检查项
	 * @author 车斯剑
	 * @date 2017年7月11日下午2:32:50
	 * @param ids
	 * @return
	 */
	@ApiOperation(value="批量删除检查项", notes="批量删除检查项")
	
	@ApiImplicitParams({
		@ApiImplicitParam(name = "ids", value = "检查项id集合", required = true, dataType = "String",paramType = "path"),
		@ApiImplicitParam(name = "tableName", value = "表名",  dataType = "String",paramType = "query")
	})
	@RequestMapping(value="/batchDeleteCheckItem/{ids}/{tableName}", method= RequestMethod.DELETE)
	public ReturnJson batchDeleteCheckItem(@PathVariable String ids, @PathVariable String tableName){
		ReturnJson returnJson = null;
		try {
			String[] idsArr = ids.split(",");
			inspectService.batchDeleteCheckItem(idsArr, tableName);
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



	/**
	 * 导入国际检查项
	 * @author 车斯剑
	 * @date 2018年10月27日下午5:42:59
	 * @param list
	 * @return
	 */
	@ApiOperation(value="导入国际检查项", notes="导入国际检查项")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "checkItem", value = "检查项实体", required = true, dataType = "CheckItem")
	})
	@RequestMapping(value="/checkItem/{sqlTableName}/{parentId}", method= RequestMethod.POST)
	public ReturnJson insertCheckItem(@PathVariable String sqlTableName, @RequestBody List<CheckItem> list, @PathVariable String parentId){
		ReturnJson returnJson = null;
		try{
			inspectService.importCheckItemFromStandard(list,sqlTableName, parentId);
			returnJson = ReturnJson.ok();
		}catch (EngineException e){
			e.printStackTrace();
			returnJson = ReturnJson.error(e.errcode,e.getMessage());
		}catch (DataIntegrityViolationException e){
			e.printStackTrace();
			returnJson = ReturnJson.error(500102,e.getMessage());
		}catch (Exception e){
			e.printStackTrace();
			returnJson = ReturnJson.error(EngineException.ERRCODE_EXCEPTION,e.getMessage());
		}finally {
			return returnJson;
		}
	}

	/**
	 * * 获取检查列表查询数据
	 * @author 车斯剑
	 * @param request
	 * @param projUid
	 * @param checkType //数据类型
	 * @param search
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	@ApiOperation(value="获取检查列表查询数据", notes="获取检查列表查询数据")
	@ApiResponses({ @ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
	@ApiImplicitParams({
			@ApiImplicitParam(name = "projUid", value = "项目id",  dataType = "String",paramType = "query"),
			@ApiImplicitParam(name = "status", value = "状态",  dataType = "String",paramType = "query"),
			@ApiImplicitParam(name = "checkType", value = "检查类型",  dataType = "String",paramType = "query"),
			@ApiImplicitParam(name = "pageIndex", value = "分页开始位置", required = true,dataType = "int",paramType = "query"),
			@ApiImplicitParam(name = "pageSize", value = "每页显示大小",required = true,  dataType = "int",paramType = "query"),
			@ApiImplicitParam(name = "processId", value = "工序任务id", dataType = "String",paramType = "query"),
			@ApiImplicitParam(name = "type", value = "区分检查和工序的", dataType = "String",paramType = "query"),
			@ApiImplicitParam(name = "processStatus", value = "工序状态", dataType = "String",paramType = "query"),
			@ApiImplicitParam(name = "areaId", value = "区域id", dataType = "String",paramType = "query"),
			@ApiImplicitParam(name = "processPerson", value = "整改人id", dataType = "String",paramType = "query"),
			@ApiImplicitParam(name = "myAbarbeitung", value = "我待整改的检查", dataType = "String",paramType = "query")
	})
	@RequestMapping(value={"getCheckRecordDatas"}, method= RequestMethod.GET)
	public ReturnPageJson<List<Map<String, Object>>> getCheckRecordDatas(HttpServletRequest request,String formType, String projUid, String status,String principal,
																		 String checkType,String dataType, String search, Integer pageIndex, Integer pageSize,String dataStatus,
																		 String partUid, String processId, String lb, String processStatus, String areaId, String processPerson, String myAbarbeitung) {
		ReturnPageJson returnJson = null;

		try{
			String companyUid = SessionUtil.getCompanyUid();
			boolean isCompanyAdmin = RoleUtil.isCompanyAdmin();
			String userUid = SessionUtil.getUserUid();
			if(!CommonUtil.ifNotEmpty(search)){
				search = null;
			}
			returnJson = inspectService.getCheckRecordDatas(companyUid,status, checkType,search, pageIndex, pageSize,projUid,dataType,formType,
					isCompanyAdmin,userUid,principal,partUid, processId, lb, processStatus, areaId, processPerson, myAbarbeitung,dataStatus);

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
	 * * 获取检查列表统计数据
	 * @author 车斯剑
	 * @param projUid
	 * @param checkType //数据类型
	 * @param search
	 * @return
	 */
	@ApiOperation(value="获取检查列表统计数据", notes="获取检查列表统计数据")
	@ApiResponses({ @ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
	@ApiImplicitParams({
			@ApiImplicitParam(name = "projUid", value = "项目id",  dataType = "String",paramType = "query"),
			@ApiImplicitParam(name = "status", value = "状态",  dataType = "String",paramType = "query"),
			@ApiImplicitParam(name = "checkType", value = "检查类型",  dataType = "String",paramType = "query"),
			@ApiImplicitParam(name = "pageIndex", value = "分页开始位置", required = true,dataType = "int",paramType = "query"),
			@ApiImplicitParam(name = "pageSize", value = "每页显示大小",required = true,  dataType = "int",paramType = "query"),
			@ApiImplicitParam(name = "processId", value = "工序任务id", dataType = "String",paramType = "query"),
			@ApiImplicitParam(name = "type", value = "区分检查和工序的", dataType = "String",paramType = "query"),
			@ApiImplicitParam(name = "processStatus", value = "工序状态", dataType = "String",paramType = "query"),
			@ApiImplicitParam(name = "areaId", value = "区域id", dataType = "String",paramType = "query"),
			@ApiImplicitParam(name = "processPerson", value = "整改人id", dataType = "String",paramType = "query"),
			@ApiImplicitParam(name = "myAbarbeitung", value = "我待整改的检查", dataType = "String",paramType = "query")
	})
	@RequestMapping(value={"totalCouts"}, method= RequestMethod.GET)
	public ReturnPageJson<List<Map<String, Object>>> getCheckRecordTotalCount(String formType, String projUid, String status,String principal,
																		 String checkType,String dataType, String search, String dataStatus,
																		 String partUid, String processId, String type, String processStatus, String areaId, String processPerson, String myAbarbeitung) {
		ReturnPageJson returnJson = null;
		try{
			String companyUid = SessionUtil.getCompanyUid();
			boolean isCompanyAdmin = RoleUtil.isCompanyAdmin();
			String userUid = SessionUtil.getUserUid();
			if(!CommonUtil.ifNotEmpty(search)){
				search = null;
			}
			Map<String, Object> total = inspectService.getCheckRecordTotalCount(companyUid,status, checkType,search,projUid,dataType,formType,isCompanyAdmin,userUid,principal,partUid,
					processId, type, processStatus, areaId, processPerson, myAbarbeitung,dataStatus);
			returnJson = new ReturnPageJson();

			returnJson.setData(total);

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

	@ApiOperation(value="获取检查记录中检查部位数据", notes="获取检查记录中检查部位数据")
	@ApiResponses({ @ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
	@ApiImplicitParams({
			@ApiImplicitParam(name = "projUid", value = "项目id",  dataType = "String",paramType = "query"),
			@ApiImplicitParam(name = "status", value = "状态",  dataType = "String",paramType = "query"),
			@ApiImplicitParam(name = "checkType", value = "检查类型",  dataType = "String",paramType = "query"),
			@ApiImplicitParam(name = "pageIndex", value = "分页开始位置", required = true,dataType = "int",paramType = "query"),
			@ApiImplicitParam(name = "pageSize", value = "每页显示大小",required = true,  dataType = "int",paramType = "query")
	})
	@RequestMapping(value={"checkPartDatas"}, method= RequestMethod.GET)
	public ReturnPageJson<List<Map<String, Object>>> getCheckPartDatas(HttpServletRequest request,String projUid, String checkType) {
		ReturnPageJson returnJson = null;
		try{

			List<Map<String,Object>> data = inspectService.getCheckPartDatas(projUid,checkType);
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

	/**
	 * 获取检查记录
	 * @param request
	 * @param id
	 * @return
	 */
	@ApiOperation(value="获取检查记录", notes="获取检查记录")
	@ApiResponses({ @ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "数据业务id",  dataType = "String",paramType = "query"),
	})
	@RequestMapping(value={"getCheckRecordById"}, method= RequestMethod.GET)
	public ReturnPageJson<Map<String, Object>> getCheckRecordById(HttpServletRequest request, String id,String userUid){
		ReturnPageJson returnJson = null;
		try {
			if(!CommonUtil.ifNotEmpty(userUid)){
				userUid=SessionUtil.getUserUid();
			}
			messageService.insertLookUser(id,userUid);
			Map<String, Object> data = inspectService.getCheckRecordById(id,userUid);
			returnJson = new ReturnPageJson();
			returnJson.setData(data);
		} catch (Exception e) {
			e.printStackTrace();
			returnJson = new ReturnPageJson(EngineException.ERRCODE_EXCEPTION,e.getMessage());
		}
		return returnJson;
	}

	@ApiOperation(value="删除检查记录", notes="删除检查记录")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "主键", required = true, dataType = "String",paramType = "path")
	})
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public ReturnJson deleteTmp( @PathVariable String id) {
		ReturnJson returnJson = null;
		try {
			inspectService.deleteById(id);
			returnJson =ReturnJson.ok(id);
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

	/**
	 * * 获取检查统计数量数据
	 * @author 车斯剑
	 * @param request
	 * @param projUid
	 * @param checkType //数据类型
	 * @return
	 */
	@ApiOperation(value="获取检查统计数量数据", notes="获取检查统计数量数据")
	@ApiResponses({ @ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
	@ApiImplicitParams({
			@ApiImplicitParam(name = "projUid", value = "项目id",  dataType = "String",paramType = "query"),
			@ApiImplicitParam(name = "status", value = "状态",  dataType = "String",paramType = "query"),
			@ApiImplicitParam(name = "checkType", value = "检查类型",  dataType = "String",paramType = "query"),
	})
	@RequestMapping(value={"statisticCountData"}, method= RequestMethod.GET)
	public ReturnPageJson<Map<String, Object>> getStatisticCountData(HttpServletRequest request,String projUid, String checkType, String companyUid,String statisticsDate,String formType) {
		ReturnPageJson returnJson = null;
		try{
			String dbType = CommonUtil.getDatabaseType();	//获取数据库类型
			boolean isCompanyAdmin = RoleUtil.isCompanyAdmin();
			String userUid = SessionUtil.getUserUid();
			if(!CommonUtil.ifNotEmpty(companyUid)){
				companyUid = SessionUtil.getCompanyUid();
			}
			Map<String,Object> data = inspectService.getStatisticCountData(companyUid,checkType,projUid,statisticsDate,formType, dbType);
			returnJson = new ReturnPageJson();
			returnJson.setData(data);
			returnJson.setTotalCount(200);
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
	 * * 获取检查统计图表数据
	 * @author 车斯剑
	 * @param request
	 * @param projUid
	 * @param statisticsDate //数据日期
	 * @return
	 */
	@ApiOperation(value="获取检查统计图表数据", notes="获取检查统计图表数据")
	@ApiResponses({ @ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
	@ApiImplicitParams({
			@ApiImplicitParam(name = "projUid", value = "项目id",  dataType = "String",paramType = "query"),
			@ApiImplicitParam(name = "statisticsDate", value = "日期",  dataType = "String",paramType = "query"),
			@ApiImplicitParam(name = "checkType", value = "检查类型",  dataType = "String",paramType = "query"),
	})
	@RequestMapping(value={"statisticChartsData"}, method= RequestMethod.GET)
	public ReturnPageJson<List<Map<String, Object>>> getStatisticChartsData(HttpServletRequest request,String projUid, String statisticsDate, String companyUid,String formType,String checkType) {
		ReturnPageJson returnJson = null;
		try{

			if(!CommonUtil.ifNotEmpty(companyUid)){
				companyUid = SessionUtil.getCompanyUid();
			}
			List<Map<String, Object>> data = inspectService.getStatisticChartsData(companyUid,statisticsDate,projUid,formType,checkType);
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

	/**
	 * * 获取检查统计表格数据
	 * @author 车斯剑
	 * @param request
	 * @param projUid
	 * @param statisticsDate //数据日期
	 * @return
	 */
	@ApiOperation(value="获取检查统计表格数据", notes="获取检查统计表格数据")
	@ApiResponses({ @ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
	@ApiImplicitParams({
			@ApiImplicitParam(name = "projUid", value = "项目id",  dataType = "String",paramType = "query"),
			@ApiImplicitParam(name = "statisticsDate", value = "日期",  dataType = "String",paramType = "query"),
			@ApiImplicitParam(name = "checkType", value = "检查类型",  dataType = "String",paramType = "query"),
	})
	@RequestMapping(value={"statisticTableData"}, method= RequestMethod.GET)
	public ReturnPageJson<List<Map<String, Object>>> getStatisticTableData(HttpServletRequest request,String projUid, String statisticsDate, String companyUid,String checkType) {
		ReturnPageJson returnJson = null;
		try{

			if(!CommonUtil.ifNotEmpty(companyUid)){
				companyUid = SessionUtil.getCompanyUid();
			}
			Map<String, Object> data = inspectService.getStatisticTableData(companyUid,statisticsDate,projUid,checkType);
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

	/**
	 * PC端获取部位树结构数据
	 * @param request
	 * @param companyUid
	 * @return
	 */
	@ApiOperation(value="PC端获取部位树结构数据", notes="PC端获取部位树结构数据")
	@ApiResponses({ @ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
	@ApiImplicitParams({
			@ApiImplicitParam(name = "companyUid", value = "公司id",  dataType = "String",paramType = "query"),
			@ApiImplicitParam(name = "projUid", value = "项目id",  dataType = "String",paramType = "query"),
			@ApiImplicitParam(name = "purpose", value = "业态",  dataType = "String",paramType = "query"),
			@ApiImplicitParam(name = "start", value = "分页开始位置", required = true,dataType = "int",paramType = "query"),
			@ApiImplicitParam(name = "pageSize", value = "每页显示大小",required = true,  dataType = "int",paramType = "query")
	})
	@RequestMapping(value={"partTree"}, method= RequestMethod.GET)
	public ReturnPageJson<List<Map<String,Object>>> getPartTree(HttpServletRequest request, String companyUid,String projUid,String purpose){
		ReturnPageJson returnJson = null;
		try {
			if(!CommonUtil.ifNotEmpty(companyUid)){
				companyUid = SessionUtil.getCompanyUid();
			}
			List<Map<String, Object>> data = inspectService.getPartTree(projUid, companyUid,"root",purpose,"");
			returnJson = new ReturnPageJson();
			returnJson.setData(data);
		} catch (EngineException e){
			e.printStackTrace();
			returnJson = new ReturnPageJson(e.errcode,e.getMessage());
		}catch (Exception e){
			e.printStackTrace();
			returnJson = new ReturnPageJson(EngineException.ERRCODE_EXCEPTION,e.getMessage());
		}finally {
			return returnJson;
		}
	}

	@ApiOperation(value="新增实测检查记录", notes="新增实测检查记录")
	@ApiImplicitParam(name = "list", value = "检查记录表", required = true, dataType = "CheckRecord")
	@RequestMapping(value = "", method= RequestMethod.POST)
	public ReturnJson<CheckRecord> create(@RequestBody List<CheckRecord> list) {
		ReturnJson returnJson = new ReturnPageJson();
		try{
			inspectService.saveCheckRecord(list);
			returnJson = ReturnJson.ok();
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

	/**
	 * 更新实测检查记录
	 * @param markerId
	 * @param checkRecord
	 * @return
	 */
	@RequestMapping(value="/{markerId}", method= RequestMethod.PUT)
	public ReturnJson<CheckRecord> putCheckRecord(@PathVariable String markerId, @RequestBody CheckRecord checkRecord) {
		ReturnJson returnJson = null;
		try{
			inspectService.updateCheckRecord(markerId,checkRecord);
			returnJson = ReturnJson.ok();
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

	@ApiOperation(value="获取测点包含的信息", notes="获取测点包含的信息")
	@ApiImplicitParam(name = "id", value = "ID_", required = true, dataType = "String",paramType = "path")
	@RequestMapping(value="/measurePoitData/{id}", method= RequestMethod.GET)
	public ReturnJson<List<Map<String,Object>>> getMeasurePoitData(@PathVariable String id) {
		ReturnJson returnJson = null;
		try{
			List<Map<String,Object>> data = inspectService.getMeasurePoitData(id);
			returnJson = ReturnJson.ok(data);
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
	@ApiOperation(value="获取权限人员", notes="获取权限人员")
	@ApiImplicitParam(name = "id", value = "ID_", required = true, dataType = "String",paramType = "path")
	@RequestMapping(value="authorityMember", method= RequestMethod.GET)
	public ReturnJson<List<Map<String,Object>>> getAuthorityMember(String projUid,String roleType) {
		ReturnJson returnJson = null;
		try{
			List<Map<String,Object>> data = inspectService.getAuthorityMember(projUid,roleType);
			returnJson = ReturnJson.ok(data);
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

	/**
	 * * 获取检查统计数量数据
	 * @author 车斯剑
	 * @param request
	 * @param projUid
	 * @return
	 */
	@ApiOperation(value="获取实测统计数量数据", notes="获取实测统计数量数据")
	@ApiResponses({ @ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
	@ApiImplicitParams({
			@ApiImplicitParam(name = "projUid", value = "项目id",  dataType = "String",paramType = "query"),
			@ApiImplicitParam(name = "status", value = "状态",  dataType = "String",paramType = "query"),
			@ApiImplicitParam(name = "checkType", value = "检查类型",  dataType = "String",paramType = "query"),
	})
	@RequestMapping(value={"measureCountData"}, method= RequestMethod.GET)
	public ReturnPageJson<Map<String, Object>> getMeasureCountData(HttpServletRequest request,String projUid, String companyUid,String statisticsDate) {
		ReturnPageJson returnJson = null;
		try{
			boolean isCompanyAdmin = RoleUtil.isCompanyAdmin();
			String userUid = SessionUtil.getUserUid();
			if(!CommonUtil.ifNotEmpty(companyUid)){
				companyUid = SessionUtil.getCompanyUid();
			}
			Map<String,Object> data = inspectService.getMeasureCountData(companyUid,projUid,statisticsDate);
			returnJson = new ReturnPageJson();
			returnJson.setData(data);
			returnJson.setTotalCount(200);
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
	 * * 获取实测统计图表数据
	 * @author 车斯剑
	 * @param request
	 * @param projUid
	 * @param statisticsDate //数据日期
	 * @return
	 */
	@ApiOperation(value="获取实测统计图表数据", notes="获取实测统计图表数据")
	@ApiResponses({ @ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
	@ApiImplicitParams({
			@ApiImplicitParam(name = "projUid", value = "项目id",  dataType = "String",paramType = "query"),
			@ApiImplicitParam(name = "statisticsDate", value = "日期",  dataType = "String",paramType = "query"),
			@ApiImplicitParam(name = "checkType", value = "检查类型",  dataType = "String",paramType = "query"),
	})
	@RequestMapping(value={"measureChartsData"}, method= RequestMethod.GET)
	public ReturnPageJson<List<Map<String, Object>>> getMeasureChartsData(HttpServletRequest request,String projUid, String statisticsDate, String companyUid,String formType) {
		ReturnPageJson returnJson = null;
		try{

			if(!CommonUtil.ifNotEmpty(companyUid)){
				companyUid = SessionUtil.getCompanyUid();
			}
			List<Map<String, Object>> data = inspectService.getMeasureChartsData(companyUid,statisticsDate,projUid);
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

	/**
	 * * 获取检查统计表格数据
	 * @author 车斯剑
	 * @param request
	 * @param projUid
	 * @param statisticsDate //数据日期
	 * @return
	 */
	@ApiOperation(value="获取实测统计表格数据", notes="获取实测统计表格数据")
	@ApiResponses({ @ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
	@ApiImplicitParams({
			@ApiImplicitParam(name = "projUid", value = "项目id",  dataType = "String",paramType = "query"),
			@ApiImplicitParam(name = "statisticsDate", value = "日期",  dataType = "String",paramType = "query"),
			@ApiImplicitParam(name = "checkType", value = "检查类型",  dataType = "String",paramType = "query"),
	})
	@RequestMapping(value={"measureTableData"}, method= RequestMethod.GET)
	public ReturnPageJson<List<Map<String, Object>>> getMeasureTableData(HttpServletRequest request,String projUid, String statisticsDate, String companyUid) {
		ReturnPageJson returnJson = null;
		try{

			if(!CommonUtil.ifNotEmpty(companyUid)){
				companyUid = SessionUtil.getCompanyUid();
			}
			List<Map<String, Object>> data = inspectService.getMeasureTableData(companyUid,statisticsDate,projUid);
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

	@ApiOperation(value="获取测点包含的信息", notes="获取测点包含的信息")
	@ApiImplicitParam(name = "id", value = "ID_", required = true, dataType = "String",paramType = "path")
	@RequestMapping(value="drawingMeasureData", method= RequestMethod.GET)
	public ReturnJson<Map<String,Object>> getDrawingMeasureData(String id,String projUid) {
		ReturnJson returnJson = null;
		try{
			Map<String,Object>data = inspectService.getDrawingMeasureData(id,projUid);
			returnJson = ReturnJson.ok(data);
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

	@ApiOperation(value="删除测点", notes="根据url的id来指定删除测点")
	@ApiImplicitParam(name = "id", value = "测点ID", required = true, dataType = "String",paramType = "path")
	@RequestMapping(value="/measurePoint/{ids}", method= RequestMethod.DELETE)
	public ReturnJson deleteUser(@PathVariable String ids) {
		ReturnJson returnJson = null;
		try{
			String[] idsArr = ids.split(",");
			inspectService.batchDeletePoitRecord(idsArr);
			returnJson = ReturnJson.ok(ids);
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

	/**
	 * * 获取微信端检查统计数量数据
	 * @author 车斯剑
	 * @param request
	 * @param projUid
	 * @param checkType //数据类型
	 * @return
	 */
	@ApiOperation(value="获取微信端检查统计数量数据", notes="获取微信端检查统计数量数据")
	@ApiResponses({ @ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
	@ApiImplicitParams({
			@ApiImplicitParam(name = "projUid", value = "项目id",  dataType = "String",paramType = "query"),
			@ApiImplicitParam(name = "status", value = "状态",  dataType = "String",paramType = "query"),
			@ApiImplicitParam(name = "checkType", value = "检查类型",  dataType = "String",paramType = "query"),
	})
	@RequestMapping(value={"statisticCountDataOfWechat"}, method= RequestMethod.GET)
	public ReturnPageJson<Map<String, Object>> getStatisticCountDataOfWechat(HttpServletRequest request,String projUid, String checkType, String companyUid,String statisticsDate,String formType) {
		ReturnPageJson returnJson = null;
		try{

			if(!CommonUtil.ifNotEmpty(companyUid)){
				companyUid = SessionUtil.getCompanyUid();
			}
			Map<String,Object> data = inspectService.getStatisticCountDataOfWechat(companyUid,projUid,statisticsDate,formType,checkType);
			returnJson = new ReturnPageJson();
			returnJson.setData(data);
			returnJson.setTotalCount(200);
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
	 * * 获取检查统计检查人数据
	 * @author 车斯剑
	 * @param request
	 * @param projUid
	 * @param statisticsDate //数据日期
	 * @return
	 */
	@ApiOperation(value="获取检查统计检查人数据", notes="获取检查统计检查人数据")
	@ApiResponses({ @ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
	@ApiImplicitParams({
			@ApiImplicitParam(name = "projUid", value = "项目id",  dataType = "String",paramType = "query"),
			@ApiImplicitParam(name = "statisticsDate", value = "日期",  dataType = "String",paramType = "query"),
			@ApiImplicitParam(name = "checkType", value = "检查类型",  dataType = "String",paramType = "query"),
	})
	@RequestMapping(value={"checkerStatistictData"}, method= RequestMethod.GET)
	public ReturnPageJson<Map<String, Object>> getCheckerStatistictData(HttpServletRequest request,String projUid, String statisticsDate, String userField, String formType,String checkType) {
		ReturnPageJson returnJson = null;
		try{
			Map<String, Object> result = new HashMap<String, Object>();
			List<Map<String, Object>> data = inspectService.getCheckerStatistictData(userField,statisticsDate,projUid,formType, checkType);
			List<Map<String, Object>> rectifyData = inspectService.getRectifyStatistictData(statisticsDate,projUid,formType, checkType);
			result.put("checkData",data);
			result.put("rectifyData",rectifyData);
			returnJson = new ReturnPageJson();
			returnJson.setData(result);
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
	 * 导出检查记录
	 * @author: 车斯剑
	 * @date: 2019年2月5日 上午10:41:23
	 * @param request
	 * @param response
	 */
	@RequestMapping(value={"exportCheckRecord"}, method= RequestMethod.GET)
	public void exportCheckRecord(HttpServletRequest request, HttpServletResponse response,String status,String checkType,String search,String projUid,
								  String dataType,String fileName,String formType,String principal,String partUid){

		String companyUid = SessionUtil.getCompanyUid();
		boolean isCompanyAdmin = RoleUtil.isCompanyAdmin();
		String userUid = SessionUtil.getUserUid();
		List<Map<String,Object>> data = inspectService.exportCheckRecord(companyUid,status, checkType,search, projUid,dataType,formType,isCompanyAdmin,userUid,principal,partUid);
		List<ExcelTitle> titleList  = new ArrayList<>();

		titleList.add(new ExcelTitle("区域","inspectPart"));
		titleList.add(new ExcelTitle(12560,"检查项","inspectItem"));
		titleList.add(new ExcelTitle(10560,"描述","desc_"));
		titleList.add(new ExcelTitle("整改负责人","rectifyPrincipal"));
		titleList.add(new ExcelTitle("状态","statusName"));
		titleList.add(new ExcelTitle("检查日期","createDate"));
		String zipname = fileName+".xls";
		try {
			zipname = java.net.URLDecoder.decode(zipname,"UTF-8");

			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/octet-stream; charset=UTF-8");
			response.addHeader("Content-Disposition", "attachment; filename=\"" + new String(zipname.getBytes("UTF-8"), "ISO8859-1") + "\";");
			OutputStream os = response.getOutputStream();
			ExportExcel ex = new ExportExcel();
			//ex.exportListExcel(os, titleList, data,"",fileName);
			ex.exportExcel(os, titleList, data,"",fileName);
			os.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}



	@RequestMapping(value={"exportInspectReport"}, method= RequestMethod.GET)
	public void exportInspectReport(HttpServletRequest request, HttpServletResponse response,String id, String userUid,String projName) throws IOException {
		if(!CommonUtil.ifNotEmpty(userUid)){
			userUid=SessionUtil.getUserUid();
		}
		String modelPath = request.getSession().getServletContext().getRealPath("/static/inspectTemp.docx");
		Map<String, Object> paramMap = inspectService.getCheckRecordById(id,userUid);
		paramMap.put("projName",projName);
		paramMap.put("doctitle",paramMap.get("checkType")+"问题隐患通知单");
		String tempPath = "";//AttachmentUtil.TMP_PATH;

		String fileName = paramMap.get("checkType")+"问题隐患通知单.docx";

		//新生成图片方法
		//WordUtil.exportWeekReportByWeekData(tempPath,fileName,paramMap);
		/**/
		InputStream in = null;
		OutputStream os = null;
		String filePath = null;
		try {

			response.setContentType("application/vnd.ms-word");
			response.addHeader("Content-Disposition", "attachment; filename=\"" + new String(fileName.getBytes("GB2312"), "ISO8859-1") + "\";");//
			filePath = tempPath+File.separator+fileName;

			CustomXWPFDocument doc = WorderToNewWordUtils.changWord(modelPath,paramMap,null);
			FileOutputStream fopts = new FileOutputStream(filePath);
			doc.write(fopts);
			fopts.close();


			in = new FileInputStream(filePath); // 获取文件的流
			os = response.getOutputStream();
			int len = 0;
			byte buf[] = new byte[1024];// 缓存作用
			while ((len = in.read(buf)) > 0) // 切忌这后面不能加 分号 ”;“
			{
				os.write(buf, 0, len);// 向客户端输出，实际是把数据存放在response中，然后web服务器再去response中读取
			}
			in.close();
			os.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (in != null) {
					in.close();
				}
				if (os != null) {
					os.close();
				}
				//FileManager.delete(filePath);
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}


	/**
	 * * 监管版检查统计
	 * @author 车斯剑
	 * @param request
	 * @return
	 */
	@ApiOperation(value="监管版检查统计", notes="监管版检查统计")
	@ApiResponses({ @ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
	@ApiImplicitParams({
			@ApiImplicitParam(name = "projUid", value = "项目id",  dataType = "String",paramType = "query"),
			@ApiImplicitParam(name = "status", value = "状态",  dataType = "String",paramType = "query"),
			@ApiImplicitParam(name = "checkType", value = "检查类型",  dataType = "String",paramType = "query"),
	})
	@RequestMapping(value={"inspectStatistics"}, method= RequestMethod.GET)
	public ReturnPageJson<Map<String, Object>> inspectStatistics(HttpServletRequest request, String companyUid,String statisticsDate, String checkType) {
		ReturnPageJson returnJson = null;
		try{
			if(!CommonUtil.ifNotEmpty(companyUid)){
				companyUid = SessionUtil.getCompanyUid();
			}
			Map<String,Object> data = inspectService.getStatisticCountDataForSupervise(companyUid,statisticsDate,checkType);
			returnJson = new ReturnPageJson();
			returnJson.setData(data);
			returnJson.setTotalCount(200);
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
	 * * 监管版检查统计热力图数据
	 * @author 车斯剑
	 * @param request
	 * @return
	 */
	@ApiOperation(value="监管版检查统计热力图数据", notes="监管版检查统计热力图数据")
	@ApiResponses({ @ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
	@ApiImplicitParams({
			@ApiImplicitParam(name = "projUid", value = "项目id",  dataType = "String",paramType = "query"),
			@ApiImplicitParam(name = "status", value = "状态",  dataType = "String",paramType = "query"),
			@ApiImplicitParam(name = "checkType", value = "检查类型",  dataType = "String",paramType = "query"),
	})
	@RequestMapping(value={"inspectHeatMapData"}, method= RequestMethod.GET)
	public ReturnPageJson<Map<String, Object>> inspectHeatMapData(HttpServletRequest request, String companyUid,String statisticsDate,String checkType) {
		ReturnPageJson returnJson = null;
		try{
			if(!CommonUtil.ifNotEmpty(companyUid)){
				companyUid = SessionUtil.getCompanyUid();
			}
			List<Map<String,Object>> data = inspectService.getInspectHeatMapData(companyUid,statisticsDate,checkType);
			returnJson = new ReturnPageJson();
			returnJson.setData(data);
			returnJson.setTotalCount(200);
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
	 * * 监管版检查统计图表数据
	 * @author 车斯剑
	 * @param request
	 * @return
	 */
	@ApiOperation(value="监管版检查统计图表数据", notes="监管版检查统计图表数据")
	@ApiResponses({ @ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
	@ApiImplicitParams({
			@ApiImplicitParam(name = "projUid", value = "项目id",  dataType = "String",paramType = "query"),
			@ApiImplicitParam(name = "status", value = "状态",  dataType = "String",paramType = "query"),
			@ApiImplicitParam(name = "checkType", value = "检查类型",  dataType = "String",paramType = "query"),
	})
	@RequestMapping(value={"inspectChartData"}, method= RequestMethod.GET)
	public ReturnPageJson<Map<String, Object>> inspectChartData(HttpServletRequest request, String companyUid,String statisticsDate,String checkType) {
		ReturnPageJson returnJson = null;
		try{
			if(!CommonUtil.ifNotEmpty(companyUid)){
				companyUid = SessionUtil.getCompanyUid();
			}
			List<Map<String,Object>> data = inspectService.getInspectChartData(companyUid,statisticsDate,checkType);
			returnJson = new ReturnPageJson();
			returnJson.setData(data);
			returnJson.setTotalCount(200);
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
	 * 获取企业库目录数据
	 * @return
	 */
	@ApiOperation(value="获取企业库目录数据", notes="获取企业库目录数据")
	@ApiResponses({ @ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
	@ApiImplicitParams({
			@ApiImplicitParam(name = "companyUid", value = "公司id",  dataType = "String",paramType = "query"),
			@ApiImplicitParam(name = "parentUid", value = "父级id",  dataType = "String",paramType = "query"),
			@ApiImplicitParam(name = "moduleType", value = "模块类别",  dataType = "String",paramType = "query"), 
			@ApiImplicitParam(name = "projType", value = "项目类型",  dataType = "String",paramType = "query"), 
			@ApiImplicitParam(name = "projPurpose", value = "业态",  dataType = "String",paramType = "query"),  
			@ApiImplicitParam(name = "type", value = "质量:quality, 安全: safety",  dataType = "String",paramType = "query"), 
			@ApiImplicitParam(name = "id", value = "企业库获取下级检查项id",  dataType = "String",paramType = "query") 
	})
	@RequestMapping(value={"getCheckItemEnterprise"}, method= RequestMethod.GET)
	public ReturnPageJson<List<CheckItem>> getCheckItemEnterprise(String parentUid, String moduleType, 
			String companyUid, String projType, String projPurpose, String type, String id){
		ReturnPageJson returnJson = null;
		try {
			if(!CommonUtil.ifNotEmpty(companyUid)){
				companyUid = SessionUtil.getCompanyUid();
			}
			List<CheckItem> data = inspectService.getCheckItemEnterprise2(companyUid, projType, projPurpose, type, id);
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
	 * 获取企业库检查项列表数据
	 * @return
	 */
	@ApiOperation(value="获取企业库检查项列表数据", notes="获取企业库检查项列表数据")
	@ApiResponses({ @ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
	@ApiImplicitParams({
		@ApiImplicitParam(name = "companyUid", value = "公司id",  dataType = "String",paramType = "query"),
		@ApiImplicitParam(name = "parentUid", value = "父级id",  dataType = "String",paramType = "query"), 
		@ApiImplicitParam(name = "pageSize", value = "每页显示大小", required = true, dataType = "int", paramType = "query"),
        @ApiImplicitParam(name = "pageIndex", value = "", required = true, dataType = "int", paramType = "query") 
	})
	@RequestMapping(value={"getCheckItemEnterpriseList"}, method= RequestMethod.GET)
	public ReturnPageJson<List<CheckItem>> getCheckItemEnterpriseList(String parentUid, String companyUid, 
			Integer pageIndex, Integer pageSize){
		ReturnPageJson returnJson = null;
		try {
			if(!CommonUtil.ifNotEmpty(companyUid)){
				companyUid = SessionUtil.getCompanyUid();
			}
			int start = (pageIndex - 1) * pageSize + 1;
            int end = pageIndex * pageSize;
			
			List<CheckItem> data = inspectService.getCheckItemEnterpriseList(parentUid, companyUid, start, end);
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
	 * 获取检查权限人员数据
	 * @param projUid
	 * chesijian
	 * @return
	 */
	@ApiOperation(value="获取检查权限人员数据", notes="获取检查权限人员数据")
	@ApiResponses({ @ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
	@ApiImplicitParams({
			@ApiImplicitParam(name = "companyUid", value = "公司id",  dataType = "String",paramType = "query"),
			@ApiImplicitParam(name = "projUid", value = "项目id",  dataType = "String",paramType = "query"),
			@ApiImplicitParam(name = "type", value = "模块类别",  dataType = "String",paramType = "query")
	})
	@RequestMapping(value={"permissionUser"}, method= RequestMethod.GET)
	public ReturnPageJson<Map<String, Object>> getUserPermission(String companyUid, String projUid, String type,String userType,String search){
		ReturnPageJson returnJson = null;
		try {
			if(!CommonUtil.ifNotEmpty(companyUid)){
				companyUid = SessionUtil.getCompanyUid();
			}
			Map<String, Object> data = inspectService.getUserPermission(companyUid, projUid, type,userType,search);
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
	 * 查询计划内质量现场检查
	 * @return
	 */
	@ApiOperation(value="查询计划内质量现场检查", notes="查询计划内质量现场检查")
	@ApiResponses({ @ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
	@ApiImplicitParams({
		@ApiImplicitParam(name = "companyUid", value = "公司id",  dataType = "String",paramType = "query"),
		@ApiImplicitParam(name = "projUid", value = "项目id",  dataType = "String",paramType = "query"),
		@ApiImplicitParam(name = "lb", value = "类别,质量quality、安全safety、工序procedure、测量measure、物料material",  dataType = "String",paramType = "query"), 
		@ApiImplicitParam(name = "status", value = "1:待我检查, 2:待我整改, 3:待我复查, 4:我提交的",  dataType = "String",paramType = "query"), 
		@ApiImplicitParam(name = "pageIndex", value = "",  dataType = "String",paramType = "query"), 
		@ApiImplicitParam(name = "pageSize", value = "",  dataType = "String",paramType = "query") 
	})
	@RequestMapping(value={"searchPlanInsideQuality"}, method= RequestMethod.GET)
	public ReturnPageJson<List<CheckItem>> searchPlanInsideQuality(String companyUid, String projUid, String type, 
			String status, Integer pageIndex, Integer pageSize, String lb){
		ReturnPageJson returnJson = null;
		try {
			if(!CommonUtil.ifNotEmpty(companyUid)){
				companyUid = SessionUtil.getCompanyUid();
			}
//			int start = (pageIndex - 1) * pageSize + 1;
			int start = (pageIndex - 1) * pageSize;		//mysql 分页从0开始
            int end = pageIndex * pageSize;
			String userUid = SessionUtil.getUserUid();
			List<Map<String, Object>> data = inspectService.searchPlanInsideQuality(companyUid, projUid, type, 
					status, userUid, start, end, lb);
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
     * 删除权限人员
     *
     * @return
     */
    @ApiOperation(value = "删除权限人员", notes = "删除权限人员")
    @ApiResponses({@ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
    @ApiImplicitParams({
		@ApiImplicitParam(name = "projUid", value = "项目id",  dataType = "String",paramType = "query"),
		@ApiImplicitParam(name = "type", value = "类型",  dataType = "String",paramType = "query"),
		@ApiImplicitParam(name = "groupName", value = "组名",  dataType = "String",paramType = "query")
	})
    @RequestMapping(value = {"/{projUid}/{type}/{groupName}"}, method = RequestMethod.DELETE)
    @ResponseBody
    public ReturnJson<Map<String, Object>> deletePermissionUser(@PathVariable String projUid, @PathVariable String type,
    		@PathVariable String groupName) {
        ReturnJson returnJson = null;
        try {
        	inspectService.deletePermissionUser(projUid, type, groupName);
            returnJson = ReturnJson.ok();
        } catch (EngineException e) {
            e.printStackTrace();
            returnJson = ReturnJson.error(e.errcode, e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            returnJson = ReturnJson.error(EngineException.ERRCODE_EXCEPTION, e.getMessage());
        } finally {
            return returnJson;
        }
    }

	/**
	 * 查询计划内质量现场检查
	 * @return
	 */
	@ApiOperation(value="查询计划", notes="查询计划")
	@ApiResponses({ @ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
	@ApiImplicitParams({
			@ApiImplicitParam(name = "companyUid", value = "公司id",  dataType = "String",paramType = "query"),
			@ApiImplicitParam(name = "projUid", value = "项目id",  dataType = "String",paramType = "query"),
			@ApiImplicitParam(name = "lb", value = "类别,质量quality、安全safety、工序procedure、测量measure、物料material",  dataType = "String",paramType = "query"),
			@ApiImplicitParam(name = "pageIndex", value = "",  dataType = "String",paramType = "query"),
			@ApiImplicitParam(name = "pageSize", value = "",  dataType = "String",paramType = "query")
	})
	@RequestMapping(value={"waitingChecks"}, method= RequestMethod.GET)
	public ReturnPageJson<List<Map<String, Object>>> getWaitingChecks(String projUid, Integer pageIndex, Integer pageSize,String userUid,String lb){
		ReturnPageJson returnJson = null;
		try {
//			int start = (pageIndex - 1) * pageSize + 1;
			int start = (pageIndex - 1) * pageSize;		//mysql 分页从0开始
			int end = pageIndex * pageSize;
			List<Map<String, Object>> data = inspectService.getWaitingCheckDatas(projUid, userUid, start, end,lb);
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
	 * 查询区域业态
	 * @return
	 */
	@ApiOperation(value="查询区域业态", notes="查询区域业态")
	@ApiResponses({ @ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
	@ApiImplicitParams({
			@ApiImplicitParam(name = "projUid", value = "项目id",  dataType = "String",paramType = "query"),
			@ApiImplicitParam(name = "pageSize", value = "",  dataType = "String",paramType = "query")
	})
	@RequestMapping(value={"areaPurpose"}, method= RequestMethod.GET)
	public ReturnPageJson<List<Map<String, Object>>> getAreaPurposes(String projUid){
		ReturnPageJson returnJson = null;
		try {
			List<Map<String, Object>> data = inspectService.getAreaPurposes(projUid);
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

	@ApiOperation(value="获取检查记录中检查项数据", notes="获取检查记录中检查项数据")
	@ApiResponses({ @ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
	@ApiImplicitParams({
			@ApiImplicitParam(name = "projUid", value = "项目id",  dataType = "String",paramType = "query"),
			@ApiImplicitParam(name = "status", value = "状态",  dataType = "String",paramType = "query"),
			@ApiImplicitParam(name = "checkType", value = "检查类型",  dataType = "String",paramType = "query"),
	})
	@RequestMapping(value={"checkItems"}, method= RequestMethod.GET)
	public ReturnPageJson<List<Map<String, Object>>> checkItems(String projUid, String checkType) {
		ReturnPageJson returnJson = null;
		try{

			List<Map<String,Object>> data = inspectService.getCheckRecordItemList(projUid,checkType);
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
	
}
