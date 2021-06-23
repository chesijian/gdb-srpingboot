package com.jrsoft.business.modules.news.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.jrsoft.business.modules.construction.service.MessageService;
import com.jrsoft.engine.exception.EngineException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.jrsoft.business.modules.news.service.NewsManageServiceI;
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

@Api(value = "News", description = "新闻公告")
@RestController
@RequestMapping(value = "/api_v1/business/news")
public class NewsManageController {
	
	@Autowired 
	private NewsManageServiceI newsManageServiceI;
	
	@Autowired
	private MessageService messageService;
	
	
	
	@ApiOperation(value="根据项目id查询新闻公告列表", notes="根据项目id查询新闻公告列表")
	@ApiResponses({ @ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
	@ApiImplicitParams({
		@ApiImplicitParam(name = "projUid", value = "项目id",required = true,  dataType = "String",paramType = "query"),
		@ApiImplicitParam(name = "pageSize", value = "每页显示大小",required = true,  dataType = "int",paramType = "query"),
		@ApiImplicitParam(name = "pageIndex", value = "",required = true,  dataType = "int",paramType = "query"),
		@ApiImplicitParam(name = "type", value = "新闻公告类型",  dataType = "String",paramType = "query"),
		@ApiImplicitParam(name = "search", value = "搜索框", dataType = "String",paramType = "query")
	})
	@RequestMapping(value={"searchNewsList"}, method= RequestMethod.GET)
	public ReturnPageJson<List<Map<String, Object>>> searchNewsList(String projUid, String userUid, String search, String companyUid, int pageSize, int pageIndex, String type){
		ReturnPageJson returnJson = null;
		String dbType = CommonUtil.getDatabaseType();	//获取数据库类型
		try{
			boolean isCompanyAdmin = RoleUtil.isCompanyAdmin();
			boolean isProjectManager = RoleUtil.isAllow("ROLE_PROJECT_MANAGER");
			boolean isProjectAdmin = RoleUtil.isAllow(" ROLE_PROJECT_ADMIN");
			boolean isAdmin = false;
			if(isCompanyAdmin||isProjectManager||isProjectAdmin){
				isAdmin =true;
			}
			if(!CommonUtil.ifNotEmpty(companyUid)) {
				companyUid = SessionUtil.getCompanyUid();
			}
			if(!CommonUtil.ifNotEmpty(userUid)){
				userUid = SessionUtil.getUserUid();
			}
			
			int start = (pageIndex-1)*pageSize + 1;
        	int end = pageIndex * pageSize;
        	
        	List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
			data = newsManageServiceI.searchNewsList(projUid, userUid, companyUid, search, start, end, isAdmin, type, dbType);
			
			returnJson = new ReturnPageJson();
			returnJson.setData(data);
			returnJson.setTotalCount(data.size());
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
	 * 根据id查询新闻公告详情
	 * @return
	 */
	@ApiOperation(value="根据id查询新闻公告详情", notes="根据id查询新闻公告详情")
	@ApiResponses({ @ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
	@ApiImplicitParam(name = "id", value = "主键id", required = true, dataType = "String",paramType = "query")
	@RequestMapping(value={"searchNewsById"}, method= RequestMethod.GET)
	@ResponseBody
	public ReturnJson<Map<String, Object>> searchNewsById(String id, String userUid){
		ReturnJson returnJson = null;
		try {
			String dbType = CommonUtil.getDatabaseType();	//获取数据库类型
			if(CommonUtil.ifNotEmpty(id)) {
				if(!CommonUtil.ifNotEmpty(userUid)){
					userUid=SessionUtil.getUserUid();
				}
				Map<String, Object> data = newsManageServiceI.searchNewsById(id, userUid, dbType);
				
				messageService.insertLookUser(id,userUid);	//增加当前用户已读记录
				returnJson = ReturnJson.ok(data);
			}else {
				returnJson = ReturnJson.ok("主键id为 null !!!");
			}
			
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
	 * 根据ids删除新闻公告详情
	 * @return
	 */
	@ApiOperation(value="根据id查询新闻公告详情", notes="根据id查询新闻公告详情")
	@ApiResponses({ @ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
	@ApiImplicitParam(name = "ids", value = "主键ids", required = true, dataType = "String",paramType = "query")
	@RequestMapping(value={"deleteNewsByIds"}, method= RequestMethod.DELETE)
	@ResponseBody
	public ReturnJson<Map<String, Object>> deleteNewsByIds(String ids){
		ReturnJson returnJson = null;
		try {
			if(ids.length() > 0) {
				String[] idss = ids.split(",");
				newsManageServiceI.deleteNewsByIds(idss);
				returnJson = ReturnJson.ok(200);
			}else {
				returnJson = ReturnJson.ok("ids为null !!!");
			}
			
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
