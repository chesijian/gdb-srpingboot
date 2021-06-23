package com.jrsoft.business.modules.meeting.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.jrsoft.business.modules.construction.service.MessageService;
import com.jrsoft.engine.exception.EngineException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.jrsoft.business.modules.contactsheet.service.ContactsheetServiceI;
import com.jrsoft.business.modules.meeting.service.MeetingServiceI;
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

@Api(value = "meeting", description = "会议")
@RestController
@RequestMapping(value = "/api_v1/business/meeting")
public class MeetingController {
	
	@Autowired 
	private MeetingServiceI meetingServiceI;

	@Autowired
    private MessageService messageService;
	
	
	@ApiOperation(value="会议室", notes="根据项目id查询会议室列表")
	@ApiResponses({ @ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
	@ApiImplicitParams({
		@ApiImplicitParam(name = "projUid", value = "项目id", required = true, dataType = "String",paramType = "query"),
		@ApiImplicitParam(name = "pageSize", value = "每页显示大小",required = true,  dataType = "int",paramType = "query"),
		@ApiImplicitParam(name = "pageIndex", value = "",required = true,  dataType = "int",paramType = "query"),
		@ApiImplicitParam(name = "date", value = "时间", dataType = "String",paramType = "query"),
		@ApiImplicitParam(name = "search", value = "搜索框", dataType = "String",paramType = "query"),
	})
	@RequestMapping(value = "searchMeetingRoomtList", method= RequestMethod.GET)
	@ResponseBody
	public ReturnJson<List<Map<String, Object>>> searchMeetingRoomtList(String projUid, int pageSize, int pageIndex, 
			String search, String userUid, String companyUid, String date){
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
			List<Map<String, Object>> data = meetingServiceI.searchMeetingRoomtList(projUid , start, end, search, isCompanyAdmin, userUid, companyUid);
			
			returnJson = new ReturnPageJson();
			if(data.size() > 0) {
				for(Map<String, Object> map : data) {
					//查询会议室占用时间记录
					List<Map<String, Object>> timeDetails = meetingServiceI.searchMeetingRoomtTimeDetails(String.valueOf(map.get("id")), date);
					map.put("timeDetails", timeDetails);
				}
				if(CommonUtil.getDatabaseType().equals("mysql")) {
					returnJson.setTotalCount((Long)data.get(0).get("totalCount"));
				}else {
					returnJson.setTotalCount((Integer)data.get(0).get("totalCount"));
				}
			}
			returnJson.setData(data);
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
	
	
	@ApiOperation(value="编辑会议室", notes="根据id查询会议室")
	@ApiResponses({ @ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id", value = "id", dataType = "String",paramType = "query")
	})
	@RequestMapping(value={"/{id}"}, method= RequestMethod.GET)
	public ReturnJson<Map<String, Object>> edit(@PathVariable String id) {
        ReturnJson returnJson = null;
        try {
        	Map<String, Object> data = meetingServiceI.searchMeetingRoomtById(id);
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
	
	
	@ApiOperation(value="删除会议室", notes="删除会议室")
	@ApiImplicitParam(name = "id", value = "ID", required = true, dataType = "String",paramType = "path")
	@RequestMapping(value="/{id}", method= RequestMethod.DELETE)
	public ReturnJson delete(@PathVariable String id) {
		ReturnJson returnJson = null;
		try{
			meetingServiceI.delete(id);
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
	
	
	@ApiOperation(value="会议", notes="根据项目id查询会议列表")
	@ApiResponses({ @ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
	@ApiImplicitParams({
		@ApiImplicitParam(name = "projUid", value = "项目id", required = true, dataType = "String",paramType = "query"),
		@ApiImplicitParam(name = "pageSize", value = "每页显示大小",required = true,  dataType = "int",paramType = "query"),
		@ApiImplicitParam(name = "pageIndex", value = "",required = true,  dataType = "int",paramType = "query"),
		@ApiImplicitParam(name = "type", value = "区分会议(1: 待进行会议, 2:我发起的;4:与我相关的, 3:会议纪要)", required = true,  dataType = "int",paramType = "query"),
		@ApiImplicitParam(name = "search", value = "搜索框", dataType = "String",paramType = "query")
	})
	@RequestMapping(value = "searchMeetingList", method= RequestMethod.GET)
	@ResponseBody
	public ReturnJson<List<Map<String, Object>>> searchMeetingList(String projUid, int pageSize, int pageIndex, 
			String search, String userUid, String companyUid, String type){
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
			List<Map<String, Object>> data = meetingServiceI.searchMeetingList(projUid , start, end, type, search, isCompanyAdmin, userUid, companyUid);
			
			returnJson = new ReturnPageJson();
			returnJson.setData(data);
			if(data.size() > 0) {
				if(CommonUtil.getDatabaseType().equals("mysql")) {
					returnJson.setTotalCount((Long)data.get(0).get("totalCount"));
				}else {
					returnJson.setTotalCount((Integer)data.get(0).get("totalCount"));
				}
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
	@ApiOperation(value="会议", notes="根据项目id查询会议任务列表")
	@ApiResponses({ @ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
	@ApiImplicitParams({
			@ApiImplicitParam(name = "projUid", value = "项目id", required = true, dataType = "String",paramType = "query"),
			@ApiImplicitParam(name = "pageSize", value = "每页显示大小",required = true,  dataType = "int",paramType = "query"),
			@ApiImplicitParam(name = "pageIndex", value = "",required = true,  dataType = "int",paramType = "query"),
			@ApiImplicitParam(name = "type", value = "区分会议(1: 待进行会议, 2:我发起的;4:与我相关的, 3:会议纪要)", required = true,  dataType = "int",paramType = "query"),
			@ApiImplicitParam(name = "search", value = "搜索框", dataType = "String",paramType = "query")
	})

	@RequestMapping(value = "meetingTasks", method= RequestMethod.GET)
	@ResponseBody
	public ReturnJson<List<Map<String, Object>>> getMeetingTasks(String projUid, int pageSize, int pageIndex,
																   String search, String userUid, String companyUid, String type){
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
			List<Map<String, Object>> data = meetingServiceI.getMeetingTasks(projUid , start, end, type, search, isCompanyAdmin, userUid, companyUid);
			returnJson = new ReturnPageJson();
			returnJson.setData(data);

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

	@ApiOperation(value="会议任务反馈", notes="根据id查询会议任务反馈列表")
	@ApiResponses({ @ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "任务id", required = true, dataType = "String",paramType = "query"),
			@ApiImplicitParam(name = "search", value = "搜索框", dataType = "String",paramType = "query")
	})

	@RequestMapping(value = "orderDetail", method= RequestMethod.GET)
	@ResponseBody
	public ReturnJson<Map<String, Object>> getTaskFeedBacks(String id){
		ReturnPageJson returnJson = null;
		try{

			Map<String, Object> data = meetingServiceI.getOrderDetail(id);
			returnJson = new ReturnPageJson();
			returnJson.setData(data);

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


	@ApiOperation(value="编辑会议", notes="根据id查询会议")
	@ApiResponses({ @ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id", value = "id", dataType = "String",paramType = "query")
	})
	@RequestMapping(value={"/meeting/{id}"}, method= RequestMethod.GET)
	public ReturnJson<Map<String, Object>> editMeeting(@PathVariable String id) {
        ReturnJson returnJson = null;
        try {
        	Map<String, Object> data = meetingServiceI.searchMeetingById(id, SessionUtil.getUserUid());
        	if(data != null) {
        		List<Map<String, Object>> task = (List<Map<String, Object>>) data.get("meetingTask");
        		if(task.size() > 0 && task != null) {
        			for(Map<String, Object> map : task) {
        				Map<String, Object> principalObj = new HashMap<String, Object>();
        				principalObj.put("id", map.get("principalUid"));
        				principalObj.put("userName", map.get("principalName"));
        				map.put("principalObj", principalObj);
        				if(null!=map.get("copyUserUid")){
							String copyUserUid = (String)map.get("copyUserUid");
							List<Map<String, Object>> copyUserList = meetingServiceI.searchMeetingcopyUser(copyUserUid);
							map.put("copyUserList", copyUserList);
						}

        			}
        		}
        	}
        	
        	messageService.insertLookUser(id, SessionUtil.getUserUid());
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
	
	
	@ApiOperation(value="删除会议", notes="删除会议")
	@ApiImplicitParam(name = "id", value = "ID", required = true, dataType = "String",paramType = "path")
	@RequestMapping(value="/meeting/{id}", method= RequestMethod.DELETE)
	public ReturnJson deleteMeeting(@PathVariable String id) {
		ReturnJson returnJson = null;
		try{
			meetingServiceI.deleteMeeting(id);
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
	
	
	@ApiOperation(value="查询会议室占用时间", notes="查询会议室占用时间")
	@ApiResponses({ @ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id", value = "会议室id", dataType = "String",paramType = "query"),
		@ApiImplicitParam(name = "startDate", value = "开始时间", dataType = "String",paramType = "query"),
		@ApiImplicitParam(name = "endDate", value = "结束时间", dataType = "String",paramType = "query")
	})
	@RequestMapping(value={"/checkMeetingOccupationTime"}, method= RequestMethod.GET)
	public ReturnJson<Map<String, Object>> checkMeetingOccupationTime(String id, String startDate, String endDate) {
        ReturnJson returnJson = null;
        try {
        	Integer count = meetingServiceI.checkMeetingOccupationTime(id, startDate, endDate);
            returnJson = ReturnJson.ok(count);
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
	
	
	@ApiOperation(value="更新当前人签到状态", notes="更新当前人签到状态")
	@ApiResponses({ @ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id", value = "会议id", dataType = "String",paramType = "query"),
		@ApiImplicitParam(name = "userId", value = "用户id", dataType = "String",paramType = "query"),
		@ApiImplicitParam(name = "status", value = "状态", dataType = "String",paramType = "query")
	})
	@RequestMapping(value={"/updateMeetingUserStatus"}, method= RequestMethod.GET)
	public ReturnJson<Map<String, Object>> updateMeetingUserStatus(String id, String userId, int status) {
		ReturnJson returnJson = null;
		try {
			meetingServiceI.updateMeetingUserStatus(id, userId, status);
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
