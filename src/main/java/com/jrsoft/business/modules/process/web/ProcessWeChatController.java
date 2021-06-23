package com.jrsoft.business.modules.process.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletResponse;

import com.jrsoft.engine.exception.EngineException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jrsoft.business.modules.process.model.Area;
import com.jrsoft.business.modules.process.service.ProcessManageServiceI;
import com.jrsoft.engine.base.model.ReturnJson;
import com.jrsoft.engine.base.model.ReturnPageJson;
import com.jrsoft.engine.common.utils.CommonUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "processWeChat", description = "工序微信端")
@RestController
@RequestMapping(value = "/api_v1/business/processWeChat")
public class ProcessWeChatController {
	
	
	@Autowired 
	private ProcessManageServiceI processManageServiceI;
	
	
	/**
     * 根据项目id查询区域与工序检验批
     * @param projUid
     * @return
     */
    @ApiOperation(value = "根据项目id查询区域与工序检验批", notes = "根据项目id查询区域与工序检验批")
    @ApiResponses({@ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
    @ApiImplicitParams({
		@ApiImplicitParam(name = "parentUid", value = "上级id", required = true, dataType = "String",paramType = "query"),
		@ApiImplicitParam(name = "projUid", value = "项目id",  dataType = "String",paramType = "query")
	})
    @RequestMapping(value = "/searchPartAndProcessTask", method = RequestMethod.GET)
    public ReturnJson<Map<String, Object>> searchPartAndProcessTask(String projUid, String parentUid,String search) {
        ReturnJson returnJson = null;
        try {
        	Map<String, Object> data = processManageServiceI.searchPartAndProcessTask(projUid, parentUid,search);
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
     * 根据工序id查询检查项
     * @return
     */
    @ApiOperation(value = "根据工序id查询检查项", notes = "根据工序id查询检查项")
    @ApiResponses({@ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
    @ApiImplicitParams({
		@ApiImplicitParam(name = "id", value = "任务id", required = true, dataType = "String",paramType = "query")
	})
    @RequestMapping(value = "/searchExamineNape", method = RequestMethod.GET)
    public ReturnJson<Map<String, Object>> searchExamineNape(String id) {
        ReturnJson returnJson = null;
        Map<String, Object> result = new HashMap<String, Object>();
        try {
        	String dbType = CommonUtil.getDatabaseType();	//获取数据库类型
        	Map<String, Object> data = processManageServiceI.editProcessTaskById(id, dbType);	//查询当前id工序任务
        	//根据工序Id与工序任务id查询检查项
        	List<Map<String, Object>> inspectList = processManageServiceI.searchExamineNape(String.valueOf(data.get("processUid")),id, (Integer)data.get("inspectType"));
        	String principals = (String)data.get("principal");
    		String spots = (String)data.get("spot");
    		String inspects = (String)data.get("inspect");
    		String projUid = (String)data.get("projUid");
    		if(CommonUtil.ifNotEmpty(principals) && principals.length() > 0){
                List<Map<String, Object>> principal = processManageServiceI.searchPersonnel(principals, projUid);
                result.put("principal", principal);
            }
            if(CommonUtil.ifNotEmpty(spots) && spots.length() > 0){
    			List<Map<String, Object>> spot = processManageServiceI.searchPersonnel(spots, projUid);
    			result.put("spot", spot);
    		}
            if(CommonUtil.ifNotEmpty(inspects) && inspects.length() > 0){
    			List<Map<String, Object>> inspect = processManageServiceI.searchPersonnel(inspects, projUid);
    			result.put("inspect", inspect);
    		}
            Long count = 0L;
            if(inspectList.size() > 0) {
            	for(Map<String, Object> inspect : inspectList) {
					Long agendaCount = (Long)inspect.get("agendaCount");
					count += agendaCount;
            		
            	}
            }
    		result.put("inspectList", inspectList);
    		result.put("agendaCount", count);
    		result.put("data", data);
            returnJson = ReturnJson.ok(result);
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
     * 检验工序任务中的检查项是否完成
     * @param id 
     * @return
     */
    @ApiOperation(value = "检验工序任务中的检查项是否完成", notes = "检验工序任务中的检查项是否完成")
    @ApiResponses({@ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
    @ApiImplicitParams({
		@ApiImplicitParam(name = "id", value = "工序任务id", required = true, dataType = "String",paramType = "query"),
	})
    @RequestMapping(value = "/checkoutInspect", method = RequestMethod.GET)
    public ReturnJson<Map<String, Object>> checkoutInspect(String id) {
        ReturnJson returnJson = null;
        try {
        	String dbType = CommonUtil.getDatabaseType();	//获取数据库类型
        	boolean flag = true;
        	Map<String, Object> data = processManageServiceI.editProcessTaskById(id, dbType);	//查询当前id工序任务
        	List<Map<String, Object>> inspectList = processManageServiceI.searchExamineNape(String.valueOf(data.get("processUid")), String.valueOf(data.get("id")),0);	//查询检查项
        	for(Map<String,Object> i : inspectList){
        		Integer record = (Integer) i.get("record");
        		if(record != 1) {
        			flag = false;
        		}
        	}
            returnJson = ReturnJson.ok(flag);
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
     * 根据工序与区域查询工序任务
     * @return
     */
    @ApiOperation(value = "根据工序与区域查询工序任务", notes = "根据工序与区域查询工序任务")
    @ApiResponses({@ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
    @ApiImplicitParams({
    	@ApiImplicitParam(name = "processId", value = "工序id", dataType = "String",paramType = "query"),
    	@ApiImplicitParam(name = "partId", value = "区域id", dataType = "String",paramType = "query"),
    	@ApiImplicitParam(name = "projUid", value = "项目id", required = true, dataType = "String",paramType = "query"),
    })
    @RequestMapping(value = "/searchProcessTaskByProIdAndPartId", method = RequestMethod.GET)
    public ReturnJson<Map<String, Object>> searchProcessTaskByProIdAndPartId(String partId, String processId, String projUid) {
    	ReturnJson returnJson = null;
    	try {
    		List<Map<String, Object>> data = processManageServiceI.searchProcessTaskByProIdAndPartId(processId, partId, projUid);
    		Map<String, List<Map<String, Object>>> obj = new HashMap<String, List<Map<String, Object>>>();
    		if(data.size() > 0) {
    			for(Map<String, Object> map : data) {
        			if(obj.containsKey(map.get("partName"))) {
        				obj.get(map.get("partName")).add(map);
        			}else {
        				List<Map<String, Object>> cildr = new ArrayList<Map<String, Object>>();
        				cildr.add(map);
        				obj.put((String) map.get("partName"), cildr);
        			}
        		}
    		}
    		
    		
    		returnJson = ReturnJson.ok(obj);
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
     * 根据区域顶层查询层数下面的楼号工序任务是否完成
     * @param id 
     * @return
     */
    @ApiOperation(value = "根据区域顶层查询层数下面的楼号工序任务是否完成", notes = "根据区域顶层查询层数下面的楼号工序任务是否完成")
    @ApiResponses({@ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
    @ApiImplicitParams({
		@ApiImplicitParam(name = "id", value = "区域id", required = true, dataType = "String",paramType = "query"),
	})
    @RequestMapping(value = "/searchAreaProcess", method = RequestMethod.GET)
    public ReturnJson<Map<String, Object>> searchAreaProcess(String id) {
        ReturnJson returnJson = null;
        Map<String, Object> result = new HashMap<>();
        try {
        	String dbType = CommonUtil.getDatabaseType();	//获取数据库类型
        	List<Map<String, Object>> areaFirstFloor = processManageServiceI.searchAreaFirstFloor(id, dbType);	//根据区域id查询第二层区域
        	// 根据区域查询第三层区域
        	Map<String, List<Map<String, Object>>> listMap = new HashMap<String, List<Map<String, Object>>>();
        	if(areaFirstFloor.size() > 0) {
        		for(Map<String, Object> map: areaFirstFloor ) {
            		List<Map<String, Object>> areaFinalLayer = processManageServiceI.searchAreaFirstFloor((String)map.get("id"), dbType);
            		if(areaFinalLayer.size() > 0) {
            			listMap.put((String) map.get("name"), areaFinalLayer);
            		}else {
            			List<Map<String, Object>> list = new ArrayList<>();
            			listMap.put((String) map.get("name"), list);
            		}
            		
            	}
        	}
        	
        	int notConstructionCount = 0; 	//未施工
			int constructionCount = 0; 	//施工中
			int complete = 0; 	//完工
			int acceptance = 0; 	//验收
        	if(CommonUtil.ifNotEmpty(listMap)){
        		for(Entry<String, List<Map<String, Object>>> e : listMap.entrySet()) {
        			List<Map<String, Object>> data = e.getValue();
        			if(data.size() > 0) {
        				for(Map<String, Object> map : data) {
            				List<Map<String, Object>> taskList = processManageServiceI.searchAreaProcess((String)map.get("id"), dbType);
            				String str = "";
            				if(taskList.size() > 0) {
            					for(Map<String, Object> map2 : taskList) {
                					if((int) map2.get("constructionStatus") == 0) {
                						str += "0";
                						
                					}
                					if((int) map2.get("constructionStatus") == 1) {
                						str += "1";
                					}
                					if((int) map2.get("constructionStatus") == 2) {
                						str += "2";
                					}
                					if((int) map2.get("constructionStatus") == 3) {
    	        						str += "3";
    	        					}
                				}
            					
            					if(str.contains("1")) {
            						map.put("status", 1);	//施工中
            						constructionCount++;
            						
            					}
            					
            					if(!str.contains("1") && !str.contains("2") && !str.contains("3")) {
            						map.put("status", 0);	//施工未开始
            						notConstructionCount++;
            					}
            					
            					if(!str.contains("0") && !str.contains("1") ) {
            						map.put("status", 2);	//待验收
            						acceptance++;
            					}
            					
            					if(!str.contains("0") && !str.contains("1") && !str.contains("2")) {
            						map.put("status", 3);	//已完工
            						complete++;
            					}
            					
            				}
            				
            				
            			}
        			}
        			
        		}
        	}
        	result.put("data", listMap);
        	result.put("notConstructionCount", notConstructionCount);
        	result.put("constructionCount", constructionCount);
        	result.put("complete", complete);
        	result.put("acceptance", acceptance);
        	returnJson = ReturnJson.ok(result);
        	
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
     * 显示工序统计
     * @return
     */
    @ApiOperation(value = "显示工序统计", notes = "显示工序统计")
    @ApiResponses({@ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
    @ApiImplicitParams({
		@ApiImplicitParam(name = "projUid", value = "项目id", required = true, dataType = "String",paramType = "query")
	})
    @RequestMapping(value = "/searchProcessStatistics", method = RequestMethod.GET)
    public ReturnJson<Map<String, Object>> searchProcessStatistics(String projUid,String parentUid) {
        ReturnJson returnJson = null;
        Map<String, Object> result = new HashMap<String, Object>();
        try {
        	String dbType = CommonUtil.getDatabaseType();	//获取数据库类型
        	//根据项目id查询顶层区域
        	List<Map<String, Object>> areaList = processManageServiceI.searchAreaTop(projUid,parentUid);
        	
        	//Map<String, List<Map<String, Object>>> listMap = new HashMap<String, List<Map<String, Object>>>();
        	
        	/*int notConstructionCount = 0; 	//未施工
			int constructionCount = 0; 	//施工中
			int complete = 0; 	//完工
			int acceptance = 0; 	//验收*/
        	/*if(areaList.size() > 0) {
        		for(Area map : areaList) {
        			//根据区域id查询第二层区域
            		List<Map<String, Object>> areaFirstFloor = processManageServiceI.searchAreaFirstFloor(map.getId(), dbType);	
            		
            		if(areaFirstFloor.size() > 0) {
            			listMap.put(map.getId()+","+map.getName()+","+"root", areaFirstFloor);
                	}else {
                		List<Map<String, Object>> list = new ArrayList<>();
            			listMap.put(map.getId()+","+map.getName()+","+"root", list);
                	}
        		}	
        	}*/
			Map<String, Object> numMap = processManageServiceI.getStatisticsNumObj(projUid,"");
        	/*if(CommonUtil.ifNotEmpty(listMap)){
    			for(Entry<String, List<Map<String, Object>>> e : listMap.entrySet()) {
    				List<Map<String, Object>> data = e.getValue();
    				if(data.size() > 0) {
        				for(Map<String, Object> map : data) {
            				List<Map<String, Object>> taskList = processManageServiceI.searchAreaProcess((String)map.get("id"), dbType);
            				String str = "";
            				if(taskList.size() > 0) {
            					for(Map<String, Object> map2 : taskList) {
                					if((int) map2.get("constructionStatus") == 0) {
                						str += "0";
                					}
                					if((int) map2.get("constructionStatus") == 1) {
                						str += "1";
                					}
                					if((int) map2.get("constructionStatus") == 2) {
                						str += "2";
                					}
                					if((int) map2.get("constructionStatus") == 3) {
    	        						str += "3";
    	        					}
                			}
            					
            					if(str.contains("1")) {
            						map.put("status", 1);	//施工中
            						constructionCount++;
            					}
            					
            					if(!str.contains("1") && !str.contains("2") && !str.contains("3")) {
            						map.put("status", 0);	//施工未开始
            						notConstructionCount++;
            					}
            					
            					if(!str.contains("0") && !str.contains("1") ) {
            						map.put("status", 2);	//待验收
            						acceptance++;
            					}
            					
            					if(!str.contains("0") && !str.contains("1") && !str.contains("2")) {
            						map.put("status", 3);	//已完工
            						complete++;
            					}
            					
            				}
            				
            				
            			}
        			}
        		}
    		}*/
        	
        	result.put("data", areaList);
        	result.put("numMap", numMap);
        	/*result.put("notConstructionCount", notConstructionCount);
        	result.put("constructionCount", constructionCount);
        	result.put("complete", complete);
        	result.put("acceptance", acceptance);*/
        	returnJson = ReturnJson.ok(result);
        	
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
     * 更新检查项状态
     * @param taskId
     * @return
     */
    @ApiOperation(value = "更新检查项状态", notes = "更新检查项状态")
    @ApiResponses({@ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
    @ApiImplicitParams({
		@ApiImplicitParam(name = "taskId", value = "工序任务id", required = true, dataType = "String",paramType = "query")
	})
    @RequestMapping(value = "/updateInspectStatus", method = RequestMethod.GET)
    public ReturnJson<Map<String, Object>> updateInspectStatus(String taskId) {
        ReturnJson returnJson = null;
        try {
        	processManageServiceI.updateInspectStatus(taskId);
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
     * 根据区域id查询图纸信息
     * @param id 
     * @return
     */
    @ApiOperation(value = "根据区域id查询图纸信息", notes = "根据区域id查询图纸信息")
    @ApiResponses({@ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
    @ApiImplicitParams({
		@ApiImplicitParam(name = "id", value = "区域id", required = true, dataType = "String",paramType = "query")
	})
    @RequestMapping(value = "/searchDrawingInfoByAreaId", method = RequestMethod.GET)
    public ReturnJson<Map<String, Object>> searchDrawingInfoByAreaId(String id) {
        ReturnJson returnJson = null;
        try {
        	Map<String, Object> info =  processManageServiceI.searchDrawingInfoByAreaId(id);
        	returnJson = ReturnJson.ok(info);
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
    
    
    @ApiOperation(value="微信端根据项目id查询工序", notes="微信端根据项目id查询工序")
	@ApiResponses({ @ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
	@ApiImplicitParams({
		@ApiImplicitParam(name = "projUid", value = "项目id",required = true,  dataType = "String",paramType = "query"),
		@ApiImplicitParam(name = "parentUid", value = "上级id",required = true,  dataType = "String",paramType = "query"),
		@ApiImplicitParam(name = "search", value = "搜索框", dataType = "String",paramType = "query")
	})
	@RequestMapping(value={"searchWeChatProcessList"}, method= RequestMethod.GET)
	public ReturnPageJson<List<Map<String, Object>>> searchWeChatProcessList(String projUid, String parentUid, String search){
		ReturnPageJson returnJson = null;
		try{
        	if(CommonUtil.ifNotEmpty(projUid)) {
        		List<Map<String, Object>> data = processManageServiceI.searchWeChatProcessList(projUid, parentUid, search);
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
    
}
