package com.jrsoft.business.modules.contactsheet.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.jrsoft.engine.exception.EngineException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.jrsoft.business.modules.contactsheet.service.ContactsheetServiceI;
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

@Api(value = "contactsheet", description = "工程联系单")
@RestController
@RequestMapping(value = "/api_v1/business/contactsheet")
public class ContactsheetController {
	
	@Autowired 
	private ContactsheetServiceI contactsheetServiceI;

	
	
	
	@ApiOperation(value="工程联系单", notes="根据项目id查询工程联系单")
	@ApiResponses({ @ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
	@ApiImplicitParams({
		@ApiImplicitParam(name = "projUid", value = "项目id", required = true, dataType = "String",paramType = "query"),
		@ApiImplicitParam(name = "pageSize", value = "每页显示大小",required = true,  dataType = "int",paramType = "query"),
		@ApiImplicitParam(name = "pageIndex", value = "",required = true,  dataType = "int",paramType = "query"),
		@ApiImplicitParam(name = "search", value = "搜索框", dataType = "String",paramType = "query"),
	})
	@RequestMapping(value = "searchContactsheetList", method= RequestMethod.GET)
	@ResponseBody
	public ReturnJson<List<Map<String, Object>>> searchContactsheetList(String projUid, int pageSize, int pageIndex, 
			String search, String userUid, String companyUid,String dataType){
		ReturnPageJson returnJson = null;
		try{
			if(!CommonUtil.ifNotEmpty(companyUid)){
				companyUid = SessionUtil.getCompanyUid();
			}
			if(!CommonUtil.ifNotEmpty(userUid)){
				userUid = SessionUtil.getUserUid();
			}
			boolean isCompanyAdmin = RoleUtil.isCompanyAdmin();
			int start = (pageIndex-1)*pageSize + 1;
        	int end = pageIndex * pageSize;
			if(!CommonUtil.ifNotEmpty(search)){
				search = null;
			}
			List<Map<String, Object>> data = contactsheetServiceI.searchContactsheetList(projUid , start, end, search, isCompanyAdmin, userUid, companyUid,dataType);
			returnJson = new ReturnPageJson();
			returnJson.setData(data);
			if(data.size() > 0) {
				returnJson.setTotalCount((Long)data.get(0).get("totalCount"));
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
	
	
	@ApiOperation(value="编辑联系单", notes="根据id查询联系单信息")
	@ApiResponses({ @ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id", value = "id", dataType = "String",paramType = "query")
	})
	@RequestMapping(value={"/{id}"}, method= RequestMethod.GET)
	public ReturnJson<Map<String, Object>> theRealNameSystem(@PathVariable String id) {
        ReturnJson returnJson = null;
        try {
        	Map<String, Object> data = contactsheetServiceI.searchContactsheetById(id);
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
     * 更新联系单审批状态
     * @param id
     * @param userUid
     * @return
     */
    @ApiOperation(value = "更新联系单审批状态", notes = "更新联系单审批状态")
    @ApiResponses({@ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
    @ApiImplicitParam(name = "id", value = "主键id", required = true, dataType = "String", paramType = "query")
    @RequestMapping(value = {"/{id}/{status}"}, method = RequestMethod.PUT)
    @ResponseBody
    public ReturnJson<Map<String, Object>> updateReportStatusById(@PathVariable String id, @PathVariable Integer status) {
    	ReturnJson returnJson = null;
    	String dbType = CommonUtil.getDatabaseType();
    	try {
    		contactsheetServiceI.updateContactsheetStatusById(id, status);
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
	
	
	@ApiOperation(value="删除联系单", notes="删除联系单")
	@ApiImplicitParam(name = "id", value = "图书ID", required = true, dataType = "String",paramType = "path")
	@RequestMapping(value="/{id}", method= RequestMethod.DELETE)
	public ReturnJson delete(@PathVariable String id) {
		ReturnJson returnJson = null;
		try{
			contactsheetServiceI.delete(id);
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
	
}
