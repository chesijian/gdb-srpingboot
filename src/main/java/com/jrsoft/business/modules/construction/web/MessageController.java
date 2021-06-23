package com.jrsoft.business.modules.construction.web;


import com.jrsoft.business.modules.construction.domain.MessagePo;
import com.jrsoft.business.modules.construction.service.MessageService;
import com.jrsoft.engine.base.model.ReturnJson;
import com.jrsoft.engine.base.model.ReturnPageJson;
import com.jrsoft.engine.common.utils.CommonUtil;
import com.jrsoft.engine.common.utils.RoleUtil;
import com.jrsoft.engine.common.utils.SessionUtil;

import com.jrsoft.engine.exception.EngineException;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 施工宝消息管理
 * * @author chesj
 *
 */
@Api(value = "MessageController", description = "施工宝消息接口")
@RestController
@RequestMapping(value = "/api_v1/business/messages")
public class MessageController {

	@Autowired
	private MessageService messageService;


	/**
	 * * 获取首页消息列表数据
	 * @author 车斯剑
	 * @param request
	 * @param projUid
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	@ApiOperation(value="获取首页消息列表数据", notes="获取首页消息列表数据")
	@ApiResponses({ @ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
	@ApiImplicitParams({
			@ApiImplicitParam(name = "projUid", value = "项目id",  dataType = "String",paramType = "query"),
			@ApiImplicitParam(name = "status", value = "状态",  dataType = "String",paramType = "query"),
			@ApiImplicitParam(name = "checkType", value = "检查类型",  dataType = "String",paramType = "query"),
			@ApiImplicitParam(name = "pageIndex", value = "分页开始位置", required = true,dataType = "int",paramType = "query"),
			@ApiImplicitParam(name = "pageSize", value = "每页显示大小",required = true,  dataType = "int",paramType = "query")
	})
	@RequestMapping(value={""}, method= RequestMethod.GET)
	public ReturnPageJson<List<Map<String, Object>>> getList(HttpServletRequest request,String projUid, String userUid,String statisDate, Integer pageIndex, Integer pageSize) {
		ReturnPageJson returnJson = null;
		try{
			String companyUid = SessionUtil.getCompanyUid();
			boolean isCompanyAdmin = RoleUtil.isCompanyAdmin();
			if(!CommonUtil.ifNotEmpty(userUid)){
				userUid = SessionUtil.getUserUid();
			}
			int start = (pageIndex-1)*pageSize + 1;
			int end = pageIndex * pageSize;
			String dbType = CommonUtil.getDatabaseType();	//获取数据库类型
			List<Map<String,Object>> data = messageService.getMessageList(companyUid,userUid, projUid, start, end,isCompanyAdmin,statisDate, dbType);

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
	 * * 获取评论列表数据
	 * @author 车斯剑
	 * @param request
	 * @param businessKey
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	@ApiOperation(value="获取评论列表数据", notes="获取评论列表数据")
	@ApiResponses({ @ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
	@ApiImplicitParams({
			@ApiImplicitParam(name = "businessKey", value = "项目id",  dataType = "String",paramType = "query"),
			@ApiImplicitParam(name = "pageIndex", value = "分页开始位置", required = true,dataType = "int",paramType = "query"),
			@ApiImplicitParam(name = "pageSize", value = "每页显示大小",required = true,  dataType = "int",paramType = "query")
	})
	@RequestMapping(value={"comments"}, method= RequestMethod.GET)
	public ReturnPageJson<List<Map<String, Object>>> getCommentsList(HttpServletRequest request,String businessKey, Integer pageIndex, Integer pageSize) {
		ReturnPageJson returnJson = null;
		try{
			String companyUid = SessionUtil.getCompanyUid();
			int start = (pageIndex-1)*pageSize + 1;
			int end = pageIndex * pageSize;
			boolean ifEnd =false;
			List<Map<String,Object>> data = messageService.getCommentsList(businessKey,start, end);
			int totalCount = messageService.getCommentTotalCount(businessKey);
			if(data != null && data.size()<pageSize){
				ifEnd = true;
			}
			returnJson = new ReturnPageJson();
			returnJson.setData(data);
			returnJson.setTotalCount(totalCount);
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
	 * * 获取回复列表数据
	 * @author 车斯剑
	 * @param request
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	@ApiOperation(value="获取回复列表数据", notes="获取回复列表数据")
	@ApiResponses({ @ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
	@ApiImplicitParams({
			@ApiImplicitParam(name = "replyUid", value = "主回复id",  dataType = "String",paramType = "query"),
			@ApiImplicitParam(name = "pageIndex", value = "分页开始位置", required = true,dataType = "int",paramType = "query"),
			@ApiImplicitParam(name = "pageSize", value = "每页显示大小",required = true,  dataType = "int",paramType = "query")
	})
	@RequestMapping(value={"replyDetails"}, method= RequestMethod.GET)
	public ReturnPageJson<List<Map<String, Object>>> getReplyDetails(HttpServletRequest request,String replyUid, Integer pageIndex, Integer pageSize) {
		ReturnPageJson returnJson = null;
		try{
			int start = (pageIndex-1)*pageSize + 1;
			int end = pageIndex * pageSize;
			List<Map<String,Object>> data = messageService.getReplyDetails(replyUid,start, end);
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
	 * * 获取已读点赞列表数据
	 * @author 车斯剑
	 * @param request
	 * @param businessKey
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	@ApiOperation(value="获取已读点赞列表数据", notes="获取已读点赞列表数据")
	@ApiResponses({ @ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
	@ApiImplicitParams({
			@ApiImplicitParam(name = "businessKey", value = "项目id",  dataType = "String",paramType = "query"),
			@ApiImplicitParam(name = "ifLike", value = "是否是点赞",  dataType = "int",paramType = "query"),
			@ApiImplicitParam(name = "pageIndex", value = "分页开始位置", required = true,dataType = "int",paramType = "query"),
			@ApiImplicitParam(name = "pageSize", value = "每页显示大小",required = true,  dataType = "int",paramType = "query")
	})
	@RequestMapping(value={"lookAndLike"}, method= RequestMethod.GET)
	public ReturnPageJson<List<Map<String, Object>>> getLookAndLikeList(HttpServletRequest request,String businessKey, String ifLike, Integer pageIndex, Integer pageSize) {
		ReturnPageJson returnJson = null;
		try{
			int start = (pageIndex-1)*pageSize + 1;
			int end = pageIndex * pageSize;

			boolean ifEnd =false;
			List<Map<String,Object>> data = messageService.getLookAndLikeList(businessKey,ifLike,start, end);
			int totalCount = messageService.getLookAndLikeTotalCount(businessKey,ifLike);
			if(data != null && data.size()<pageSize){
				ifEnd = true;
			}
			returnJson = new ReturnPageJson();
			returnJson.setData(data);
			returnJson.setTotalCount(totalCount);
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

	@ApiOperation(value="点赞、取消点赞", notes="点赞、取消点赞")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "businessKey", value = "业务id", required = true, dataType = "String",paramType = "path"),
			@ApiImplicitParam(name = "userUid", value = "用户id", required = true, dataType = "String")
	})
	@RequestMapping(value="like", method= RequestMethod.PUT)
	public ReturnJson<Map<String,Object>> like(String businessKey,String userUid, Integer likeValue) {
		ReturnJson returnJson = null;
		try{
			messageService.updateLikeValue(businessKey,userUid,likeValue);
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
	 * * 获取已选中的人
	 * @author 车斯剑
	 * @param request
	 * @param userIds
	 * @param companyUid
	 * @return
	 */
	@ApiOperation(value="获取已选中的人", notes="获取已选中的人")
	@ApiResponses({ @ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
	@ApiImplicitParams({
			@ApiImplicitParam(name = "userIds", value = "用户id集合",  dataType = "String",paramType = "query"),
			@ApiImplicitParam(name = "companyUid", value = "公司id",  dataType = "int",paramType = "query")
	})
	@RequestMapping(value={"selectUsers"}, method= RequestMethod.GET)
	public ReturnPageJson<List<Map<String, Object>>> getSelectedUsers(HttpServletRequest request,String userIds, String companyUid) {
		ReturnPageJson returnJson = null;
		try{

			String[] ids = userIds.split(",");
			List<Map<String,Object>> data = messageService.getSelectedUsers(ids,companyUid);

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



	/*@ApiOperation(value="发送消息", notes="发送消息")
	@ApiResponses({ @ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
	@ApiImplicitParams({
			@ApiImplicitParam(name = "unitIds", value = "协同单位id", dataType = "String",paramType = "query"),
			@ApiImplicitParam(name = "roleType", value = "类型(1: 角色权限; 2: 人员权限)", dataType = "String",paramType = "query"),
			@ApiImplicitParam(name = "pageSize", value = "每页显示大小",required = true,  dataType = "int",paramType = "query")
	})
	@RequestMapping(value={"/sendMessage/{assigneeIds}"}, method= RequestMethod.PUT)
	public ReturnJson<Map<String, Object>> sendMessage(@PathVariable String assigneeIds, @RequestBody MsgParams msg) {
		ReturnJson returnJson = null;
		try {
			String[] assigneeList = assigneeIds.split(",");
			//SysNoticeUtil.send(msg.getSubject(),msg.getContent(), SessionUtil.getBasePath(),msg.getUrl(),msg.getParams(),msg.getType(),assigneeList);
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
	}*/

	/**
	 * 查询当前人是否已点赞
	 * @param businessKey
	 * @param userUid
	 * @param likeValue
	 * @return
	 */
	@ApiOperation(value="查询当前人是否已点赞", notes="点赞、取消点赞")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "businessKey", value = "业务id", required = true, dataType = "String",paramType = "path"),
			@ApiImplicitParam(name = "userUid", value = "用户id", required = true, dataType = "String")
	})
	@RequestMapping(value="ifLike", method= RequestMethod.GET)
	public ReturnJson<Integer> getIfLike(String businessKey,String userUid) {
		ReturnJson returnJson = null;
		try{
			Integer realut =messageService.getIfLike(businessKey,userUid);
			returnJson = ReturnJson.ok(realut);
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
