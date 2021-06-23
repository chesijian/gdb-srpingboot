package com.jrsoft.business.modules.process.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jrsoft.engine.base.web.BaseController;
import com.jrsoft.engine.base.model.ReturnJson;
import com.jrsoft.engine.base.model.ReturnPageJson;
import com.jrsoft.engine.common.utils.RoleUtil;
import com.jrsoft.engine.exception.EngineException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jrsoft.business.modules.process.model.ProcessCheckout;
import com.jrsoft.business.modules.process.model.ProcessCheckoutList;
import com.jrsoft.business.modules.process.model.ProcessTask;
import com.jrsoft.business.modules.process.model.ProcessTaskList;
import com.jrsoft.business.modules.process.service.ProcessManageServiceI;

import com.jrsoft.engine.common.utils.CommonUtil;
import com.jrsoft.engine.common.utils.SessionUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "process", description = "工序管理")
@RestController
@RequestMapping(value = "/api_v1/business/process")
public class ProcessManageController extends BaseController {
	
	@Autowired 
	private ProcessManageServiceI processManageServiceI;
	
	
	@ApiOperation(value="根据项目id查询工序", notes="根据项目id查询工序")
	@ApiResponses({ @ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
	@ApiImplicitParams({
		@ApiImplicitParam(name = "projUid", value = "项目id",required = true,  dataType = "String",paramType = "query"),
		@ApiImplicitParam(name = "formType", value = "工序类型(procedure)....",required = true,  dataType = "String",paramType = "query"),
		@ApiImplicitParam(name = "search", value = "搜索框", dataType = "String",paramType = "query")
	})
	@RequestMapping(value={"searchProcessList"}, method= RequestMethod.GET)
	public ReturnPageJson<List<Map<String, Object>>> searchProcessList(String projUid, String userUid, String search,
                                                                       String companyUid, String formType){
		ReturnPageJson returnJson = null;
		try{
			boolean isCompanyAdmin = RoleUtil.isCompanyAdmin();
			if(!CommonUtil.ifNotEmpty(companyUid)) {
				companyUid = SessionUtil.getCompanyUid();
			}
			if(!CommonUtil.ifNotEmpty(userUid)){
				userUid = SessionUtil.getUserUid();
			}
			String dbType="mysql";
        	if(CommonUtil.ifNotEmpty(projUid)) {
        		List<Map<String, Object>> data = processManageServiceI.searchProcessList(projUid, userUid, companyUid, 
        				search, isCompanyAdmin, dbType, formType);
        		returnJson = new ReturnPageJson();
    			returnJson.setData(data);
        	}else {
        		returnJson.setErrorMsg("项目id 为 null !!!");
        	}
			
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
     * 根据id编辑工序
     * @param id 
     * @return
     */
    @ApiOperation(value = "编辑工序", notes = "编辑工序")
    @ApiResponses({@ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
    @ApiImplicitParam(name = "id", value = "工序的id", required = true, dataType = "String", paramType = "path")
    @RequestMapping(value = "/process/{id}", method = RequestMethod.GET)
    public ReturnJson<Map<String, Object>> edit(@PathVariable String id) {
        ReturnJson returnJson = null;
        try {
        	Map<String, Object> data = processManageServiceI.edit(id);
            returnJson = ReturnJson.ok(data);
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
     * 删除工序
     * @param id 需要删除工序的id
     * @return
     */
    @ApiOperation(value = "删除工序", notes = "删除工序")
    @ApiResponses({@ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
    @ApiImplicitParam(name = "id", value = "工序的id", required = true, dataType = "String", paramType = "path")
    @RequestMapping(value = "/process/{id}", method = RequestMethod.DELETE)
    public ReturnJson<Map<String, Object>> delete(@PathVariable String id) {
        ReturnJson returnJson = null;
        try {
        	processManageServiceI.delete(id);
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
     * 查询工序排序最大值
     * @param id 需要删除工序的id
     * @return
     */
    @ApiOperation(value = "查询排序最大值", notes = "查询排序最大值")
    @ApiResponses({@ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
    @ApiImplicitParams({
        @ApiImplicitParam(name = "parentUid", value = "上级id", required = true, dataType = "String", paramType = "path"),
    })
    @RequestMapping(value = "/searchMaxSort", method = RequestMethod.GET)
    public ReturnJson<Map<String, Object>> searchMaxSort(String parentUid) {
        ReturnJson returnJson = null;
        try {
        	if(CommonUtil.ifNotEmpty(parentUid)){
        		int maxSort = processManageServiceI.searchMaxSort(parentUid);
        		returnJson = ReturnJson.ok(maxSort+1);
        	}else {
        		returnJson.setErrorMsg("上级id为null !!!");
        	}
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
    
	
	@ApiOperation(value = "根据工序id查询检查项", notes = "根据工序id查询检查项")
    @ApiResponses({@ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "processId", value = "工序id", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示大小", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageIndex", value = "", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "search", value = "搜索框", dataType = "String", paramType = "query")
    })
    @RequestMapping(value = {"searchInspectList"}, method = RequestMethod.GET)
    public ReturnPageJson<List<Map<String, Object>>> searchInspectList(HttpServletRequest request, String processId, String userUid, String search, String projUid, int pageSize, int pageIndex) {
        ReturnPageJson returnJson = null;
        try {
        	String dbType = "mysql";	//获取数据库类型
            boolean isCompanyAdmin = RoleUtil.isCompanyAdmin();

            int start = (pageIndex - 1) * pageSize + 1;
            int end = pageIndex * pageSize;
            if (!CommonUtil.ifNotEmpty(search)) {
                search = null;
            }
            if(!CommonUtil.ifNotEmpty(userUid)){
				userUid = SessionUtil.getUserUid();
			}
            if (CommonUtil.ifNotEmpty(processId)) {
                List<Map<String, Object>> data = processManageServiceI.searchInspectList(processId, userUid, projUid, search, start, end, isCompanyAdmin, dbType);
                
                returnJson = new ReturnPageJson();
                returnJson.setData(data);
                if(data.size() > 0) {
                	if(dbType.equals("mysql")) {
                		returnJson.setTotalCount((Long)data.get(0).get("totalCount"));
                	}else {
                		returnJson.setTotalCount((Integer)data.get(0).get("totalCount"));
                	}
                }else {
                	returnJson.setTotalCount(0);
                }
            } else {
                returnJson.setErrorMsg("工序id为 null !!!");
            }
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

	
	/**
     * 根据id编辑检查项
     * @param id 
     * @return
     */
    @ApiOperation(value = "编辑检查项", notes = "编辑检查项")
    @ApiResponses({@ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
    @ApiImplicitParam(name = "id", value = "工序的id", required = true, dataType = "String", paramType = "path")
    @RequestMapping(value = "/inspect/{id}", method = RequestMethod.GET)
    public ReturnJson<Map<String, Object>> editInspect(@PathVariable String id) {
        ReturnJson returnJson = null;
        try {
        	Map<String, Object> data = processManageServiceI.editInspect(id);
            returnJson = ReturnJson.ok(data);
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
     * 删除检查项
     * @param id 需要删除工序的id
     * @return
     */
    @ApiOperation(value = "删除检查项", notes = "删除检查项")
    @ApiResponses({@ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
    @ApiImplicitParam(name = "ids", value = "检查项id", required = true, dataType = "String", paramType = "path")
    @RequestMapping(value = "/inspect/ids", method = RequestMethod.DELETE)
    public ReturnJson<Map<String, Object>> deleteInspect(String ids) {
        ReturnJson returnJson = null;
        try {
        	if(ids.length() > 0) {
        		String[] idss = ids.split(",");
        		processManageServiceI.deleteInspect(idss);
        		returnJson = ReturnJson.ok();
        	}else {
        		returnJson.setErrorMsg("ids 为null!!!");
        	}
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
     * 查询检查项排序最大值
     * @param id 需要删除工序的id
     * @return
     */
    @ApiOperation(value = "查询检查项排序最大值", notes = "查询检查项排序最大值")
    @ApiResponses({@ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
    @ApiImplicitParam(name = "parentUid", value = "上级id", required = true, dataType = "String", paramType = "path")
    @RequestMapping(value = "/InspectMaxSort", method = RequestMethod.GET)
    public ReturnJson<Map<String, Object>> InspectMaxSort(String parentUid) {
        ReturnJson returnJson = null;
        try {
        	if(CommonUtil.ifNotEmpty(parentUid)){
        		int maxSort = processManageServiceI.InspectMaxSort(parentUid);
        		returnJson = ReturnJson.ok(maxSort+1);
        	}else {
        		returnJson.setErrorMsg("上级id为null !!!");
        	}
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
	
    
    @ApiOperation(value="保存工序检验批", notes="保存工序检验批")
	@ApiImplicitParam(name = "data", value = "任务", required = true, dataType = "ProcessCheckoutList")
	@RequestMapping(value={"save"}, method= RequestMethod.POST)
	public ReturnJson<ProcessCheckout> postBook(@RequestBody ProcessCheckoutList data) {
		ReturnJson returnJson = null;
		try{
			String dbType = "mysql";	//获取数据库类型
			int max = 0;
			for (ProcessCheckout c : data.getProcesscheck()) {
				try {
					max = processManageServiceI.checkoutMaxSort(c.getProjUid(), dbType);
				} catch (Exception e) {
					max = 1001;
				}
			}
			if(max == 0) {
				max = 1000;
			}
			processManageServiceI.saveProcessCheckout(data, max);
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
    
    
    
    @ApiOperation(value = "根据项目id查询工序检验", notes = "根据项目id查询工序检验")
    @ApiResponses({@ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "projUid", value = "工序id", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示大小", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageIndex", value = "", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "search", value = "搜索框", dataType = "String", paramType = "query")
    })
    @RequestMapping(value = {"searchProcessCheckout"}, method = RequestMethod.GET)
    public ReturnPageJson<List<Map<String, Object>>> searchProcessCheckout(HttpServletRequest request, String projUid, String userUid, String search, String companyUid, int pageSize, int pageIndex) {
        ReturnPageJson returnJson = null;
        try {
        	String dbType = "mysql";	//获取数据库类型
            boolean isCompanyAdmin = RoleUtil.isCompanyAdmin();
            if (!CommonUtil.ifNotEmpty(companyUid)) {
                companyUid = SessionUtil.getCompanyUid();
            }
            int start = (pageIndex - 1) * pageSize + 1;
            int end = pageIndex * pageSize;
            if (!CommonUtil.ifNotEmpty(search)) {
                search = null;
            }
            if(!CommonUtil.ifNotEmpty(userUid)){
				userUid = SessionUtil.getUserUid();
			}
            if (CommonUtil.ifNotEmpty(projUid)) {
                List<Map<String, Object>> data = processManageServiceI.searchProcessCheckout(projUid, userUid, companyUid, search, 
                		start, end, isCompanyAdmin, dbType);
                returnJson = new ReturnPageJson();
                returnJson.setData(data);
                if(data.size() > 0) {
                	if(dbType.equals("mysql")) {
                		returnJson.setTotalCount((Long) data.get(0).get("totalCount"));
                	}else {
                		returnJson.setTotalCount((Integer) data.get(0).get("totalCount"));
                	}
                }else {
                	returnJson.setTotalCount(0);
                }
            } else {
                returnJson.setErrorMsg("项目id为 null !!!");
            }
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
    
    
    /**
     * 根据id编辑工序检验
     * @param id 
     * @return
     */
    @ApiOperation(value = "编辑工序检验", notes = "编辑工序检验")
    @ApiResponses({@ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
    @ApiImplicitParam(name = "id", value = "检验的id", required = true, dataType = "String", paramType = "path")
    @RequestMapping(value = "/checkout/{id}", method = RequestMethod.GET)
    public ReturnJson<Map<String, Object>> editCheckoutById(@PathVariable String id) {
        ReturnJson returnJson = null;
        try {
        	Map<String, Object> data = processManageServiceI.editCheckoutById(id);
            returnJson = ReturnJson.ok(data);
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
     * 删除工序检验
     * @param id 需要删除工序的id
     * @return
     */
    @ApiOperation(value = "删除工序检验", notes = "删除工序检验")
    @ApiResponses({@ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
    @ApiImplicitParam(name = "ids", value = "工序检验ids", required = true, dataType = "String", paramType = "path")
    @RequestMapping(value = "/checkout/ids", method = RequestMethod.DELETE)
    public ReturnJson<Map<String, Object>> deleteCheckoutByIds(String ids) {
        ReturnJson returnJson = null;
        try {
        	if(ids.length() > 0) {
        		String[] idss = ids.split(",");
        		processManageServiceI.deleteCheckoutByIds(idss);
        		returnJson = ReturnJson.ok();
        	}else {
        		returnJson.setErrorMsg("ids 为null!!!");
        	}
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
     * 查询工序检验编号最大值
     * @param id 需要删除工序的id
     * @return
     */
    @ApiOperation(value = "查询工序检验编号最大值", notes = "查询工序检验编号最大值")
    @ApiResponses({@ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
    @ApiImplicitParam(name = "projUid", value = "项目id", required = true, dataType = "String", paramType = "path")
    @RequestMapping(value = "/checkoutMaxSort", method = RequestMethod.GET)
    public ReturnJson<Map<String, Object>> checkoutMaxSort(String projUid) {
        ReturnJson returnJson = null;
        try {
        	String dbType = "mysql";	//获取数据库类型
        	if(CommonUtil.ifNotEmpty(projUid)){
        		int maxSort = processManageServiceI.checkoutMaxSort(projUid, dbType);
        		returnJson = ReturnJson.ok(maxSort);
        	}else {
        		returnJson.setErrorMsg("项目id为null !!!");
        	}
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
     * 查询工序任务查询检验批树
     * @param id 需要删除工序的id
     * @return
     */
    @ApiOperation(value = "查询工序任务查询检验批树", notes = "新增工序任务查询检验批树")
    @ApiResponses({@ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
    @ApiImplicitParam(name = "projUid", value = "项目id", required = true, dataType = "String", paramType = "path")
    @RequestMapping(value = "/processTaskTree", method = RequestMethod.GET)
    public ReturnJson<Map<String, Object>> processTaskTree(String projUid,String purpose) {
        ReturnJson returnJson = null;
        try {
        	if(CommonUtil.ifNotEmpty(projUid)){
        		String companyUid = SessionUtil.getCompanyUid();
        		Map<String, Object> data = processManageServiceI.processTaskTree(projUid, companyUid, "root",purpose);
        		returnJson = ReturnJson.ok(data);
        	}else {
        		returnJson.setErrorMsg("项目id为null !!!");
        	}
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
    
    
    @ApiOperation(value = "根据项目id查询工序任务列表", notes = "根据项目id查询工序检验列表")
    @ApiResponses({@ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "projUid", value = "工序id", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示大小", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageIndex", value = "", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "exStatus", value = "检查状态", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "constructionStatus", value = "施工状态", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "partUid", value = "区域id", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "principalUid", value = "负责人id", dataType = "String", paramType = "query")
    })
    @RequestMapping(value = {"searchProcessTaskList"}, method = RequestMethod.GET)
    public ReturnPageJson<List<Map<String, Object>>> searchProcessTaskList(String projUid, String userUid, String search, String companyUid, 
    		int pageSize, int pageIndex, String exStatus, String constructionStatus, String partUid, String principalUid) {
        ReturnPageJson returnJson = null;
        try {
        	String dbType = "mysql";	//获取数据库类型
            boolean isCompanyAdmin = RoleUtil.isCompanyAdmin();
            if (!CommonUtil.ifNotEmpty(companyUid)) {
                companyUid = SessionUtil.getCompanyUid();
            }
            int start = (pageIndex - 1) * pageSize + 1;
            int end = pageIndex * pageSize;
            if (!CommonUtil.ifNotEmpty(search)) {
                search = null;
            }
            if(!CommonUtil.ifNotEmpty(userUid)){
				userUid = SessionUtil.getUserUid();
			}
            if (CommonUtil.ifNotEmpty(projUid)) {
                List<Map<String, Object>> data = processManageServiceI.searchProcessTaskList(projUid, userUid, companyUid, search, 
                		start, end, isCompanyAdmin, exStatus, constructionStatus, partUid, principalUid, dbType);
                if(data.size() > 0) {
                	for(Map<String,Object> map : data){
                		String principals = (String)map.get("principal");
                		String spots = (String)map.get("spot");
                		String inspects = (String)map.get("inspect");
                		if(principals.length() > 0) {
                			List<Map<String, Object>> principal = processManageServiceI.searchPersonnel(principals, projUid);
                			map.put("principal", principal);
                		}
                		if(spots.length() > 0) {
                			List<Map<String, Object>> spot = processManageServiceI.searchPersonnel(spots, projUid);
                			map.put("spot", spot);
                		}
                		if(inspects.length() > 0) {
                			List<Map<String, Object>> inspect = processManageServiceI.searchPersonnel(inspects, projUid);
                			map.put("inspect", inspect);
                		}
                	}
                	returnJson = new ReturnPageJson();
                    returnJson.setData(data);
                    if(dbType.equals("mysql")) {
                    	returnJson.setTotalCount((Long)data.get(0).get("totalCount"));
                    }else {
                    	returnJson.setTotalCount((Integer)data.get(0).get("totalCount"));
                    }
                }else {
                	returnJson = new ReturnPageJson();
                    returnJson.setData(data);
                	returnJson.setTotalCount(0);
                }
                
                
            } else {
                returnJson.setErrorMsg("项目id为 null !!!");
            }
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
    
    
    @ApiOperation(value="保存工序任务", notes="保存工序任务")
	@ApiImplicitParam(name = "data", value = "任务", required = true, dataType = "ProcessTaskList")
	@RequestMapping(value={"/task/save"}, method= RequestMethod.POST)
	public ReturnJson<ProcessCheckout> post(@RequestBody ProcessTaskList data) {
		ReturnJson returnJson = null;
		try{
			String dbType = "mysql";	//获取数据库类型
			int max = 0;
			for(ProcessTask task : data.getTask()) {
				try {
					max = processManageServiceI.searchTaskSortMax(task.getProjUid(), dbType);
				} catch (Exception e) {
					max = 1000;
				}
			}
			if(max == 0) {
				max = 1000;
			}
			processManageServiceI.saveProcessTask(data, max);
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
     * 删除工序任务
     * @param id 需要删除工序的id
     * @return
     */
    @ApiOperation(value = "删除工序任务", notes = "删除工序任务")
    @ApiResponses({@ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
    @ApiImplicitParam(name = "ids", value = "工序任务ids", required = true, dataType = "String", paramType = "path")
    @RequestMapping(value = "/task/ids", method = RequestMethod.DELETE)
    public ReturnJson<Map<String, Object>> deleteProcessTaskByIds(String ids) {
        ReturnJson returnJson = null;
        try {
        	if(ids.length() > 0) {
        		String[] idss = ids.split(",");
        		processManageServiceI.deleteProcessTaskByIds(idss);
        		returnJson = ReturnJson.ok();
        	}else {
        		returnJson.setErrorMsg("ids 为null!!!");
        	}
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
     * 根据id编辑工序任务
     * @param id 
     * @return
     */
    @ApiOperation(value = "编辑工序任务", notes = "编辑工序任务")
    @ApiResponses({@ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
    @ApiImplicitParam(name = "id", value = "任务的id", required = true, dataType = "String", paramType = "path")
    @RequestMapping(value = "/task/{id}", method = RequestMethod.GET)
    public ReturnJson<Map<String, Object>> editProcessTaskById(@PathVariable String id) {
        ReturnJson returnJson = null;
        try {
        	String dbType = "mysql";	//获取数据库类型
        	Map<String, Object> data = processManageServiceI.editProcessTaskById(id, dbType);
        	String principals = (String)data.get("principal");
    		String spots = (String)data.get("spot");
    		String inspects = (String)data.get("inspect");
    		String projUid = (String)data.get("projUid");
    		if(CommonUtil.ifNotEmpty(principals) && principals.length() > 0) {
    			List<Map<String, Object>> principal = processManageServiceI.searchPersonnel(principals, projUid);
    			data.put("principal", principal);
    		}
    		if(CommonUtil.ifNotEmpty(spots) && spots.length() > 0) {
    			List<Map<String, Object>> spot = processManageServiceI.searchPersonnel(spots, projUid);
    			data.put("spot", spot);
    		}
    		if(CommonUtil.ifNotEmpty(inspects) && inspects.length() > 0) {
    			List<Map<String, Object>> inspect = processManageServiceI.searchPersonnel(inspects, projUid);
    			data.put("inspect", inspect);
    		}
            returnJson = ReturnJson.ok(data);
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
     * 根据项目id查询检验批名称是否重复
     * @param id 
     * @return
     */
    @ApiOperation(value = "根据项目id查询检验批名称是否重复", notes = "根据项目id查询检验批名称是否重复")
    @ApiResponses({@ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
    @ApiImplicitParams({
		@ApiImplicitParam(name = "projUid", value = "项目id", required = true, dataType = "String",paramType = "query"),
		@ApiImplicitParam(name = "name", value = "传过来的名称", required = true, dataType = "String",paramType = "query")
	})
    @RequestMapping(value = "/checkInspection", method = RequestMethod.GET)
    public ReturnJson<Map<String, Object>> checkInspection(String projUid, String name) {
        ReturnJson returnJson = null;
        try {
        	if(name.length() > 0) {
        		String[] names = name.split(",");
        		Map<String, Object> message = processManageServiceI.checkInspection(projUid, names);
        		if(CommonUtil.ifNotEmpty(message)) {
        			returnJson = ReturnJson.ok(message);
        		}else {
        			returnJson = ReturnJson.ok(true);
        		}
        		
        	}
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
     * 根据项目id查询首页信息
     * @param id 
     * @return
     */
    @ApiOperation(value = "根据项目id查询首页信息", notes = "根据项目id查询首页信息")
    @ApiResponses({@ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
    @ApiImplicitParams({
		@ApiImplicitParam(name = "projUid", value = "项目id", required = true, dataType = "String",paramType = "query"),
		@ApiImplicitParam(name = "date", value = "日期时间", dataType = "String",paramType = "query")
	})
    @RequestMapping(value = "/searchHomeMessageByProjUid", method = RequestMethod.GET)
    public ReturnJson<Map<String, Object>> searchHomeMessageByProjUid(String projUid, String date) {
        ReturnJson returnJson = null;
        try {
        	String dbType = "mysql";	//获取数据库类型
        	Map<String, Object> data = processManageServiceI.searchHomeMessageByProjUid(projUid, date, dbType);
            returnJson = ReturnJson.ok(data);
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
     * 根据检查项ids更新检查项状态
     * @param ids
     * @param type
     * @return
     */
    @ApiOperation(value = "根据检查项ids更新检查项状态", notes = "根据检查项ids更新检查项状态")
    @ApiResponses({@ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
    @ApiImplicitParams({
		@ApiImplicitParam(name = "ids", value = "检查项id", required = true, dataType = "String",paramType = "query"),
		@ApiImplicitParam(name = "taskId", value = "工序任务id", dataType = "String",paramType = "query"), 
		@ApiImplicitParam(name = "type", value = "需要修改状态", dataType = "String",paramType = "query") 
	})
    @RequestMapping(value = "/updateInspectStatusByInspectid", method = RequestMethod.GET)
    public ReturnJson<Map<String, Object>> updateInspectStatusByInspectid(String ids, String type, String taskId) {
        ReturnJson returnJson = null;
        try {
        	processManageServiceI.searchProcessDetails(taskId, ids, type);
        	/*if(count > 0) {
        		if(ids.length() > 0) {
            		String[] idss = ids.split(",");
            		processManageServiceI.updateInspectStatusByInspectid(idss, type, taskId);
            		returnJson = ReturnJson.ok();
            	}
        	}else {
        		processManageServiceI.saveProcessDet(ids, type, taskId);
        	}*/
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
    
    
    @ApiOperation(value="导出", notes="导出")
	@ApiResponses({ @ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
	@ApiImplicitParams({
		@ApiImplicitParam(name = "companyUid", value = "公司id", dataType = "String",paramType = "query"), 
		@ApiImplicitParam(name = "projUid", value = "项目id", dataType = "String",paramType = "query"), 
		@ApiImplicitParam(name = "projName", value = "项目名称", dataType = "String",paramType = "query"), 
		@ApiImplicitParam(name = "status", value = "状态", dataType = "String",paramType = "query"), 
		@ApiImplicitParam(name = "areaUid", value = "区域id", dataType = "String",paramType = "query"), 
		@ApiImplicitParam(name = "abarbeitungUid", value = "整改人", dataType = "String",paramType = "query"), 
		@ApiImplicitParam(name = "search", value = "搜索", dataType = "String",paramType = "query")
	})
	@RequestMapping(value={"/export"}, method= RequestMethod.GET)
	public ReturnJson<Map<String, Object>> export(String companyUid, String projUid, String projName, Integer status, String areaUid, String abarbeitungUid, 
			String search, HttpServletResponse response) {
		
		ReturnJson returnJson = null;
		try {
			if(!CommonUtil.ifNotEmpty(companyUid)) {
				companyUid = SessionUtil.getCompanyUid();
			}
			processManageServiceI.export(companyUid, projUid, projName, status, areaUid, abarbeitungUid, search, response);
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
    
    
}
