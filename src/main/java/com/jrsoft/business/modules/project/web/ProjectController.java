package com.jrsoft.business.modules.project.web;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.jrsoft.business.modules.construction.service.InspectService;
import com.jrsoft.business.modules.construction.service.MessageService;
import com.jrsoft.business.modules.project.query.ProjectQuery;
import com.jrsoft.engine.exception.EngineException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.jrsoft.business.modules.project.service.ProjectService;
import com.jrsoft.engine.base.model.ReturnJson;
import com.jrsoft.engine.base.model.ReturnPageJson;
import com.jrsoft.engine.base.web.BaseController;
import com.jrsoft.engine.common.utils.CommonUtil;
import com.jrsoft.engine.common.utils.RoleUtil;
import com.jrsoft.engine.common.utils.SessionUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "ProjectController", description = "项目立项")
@RestController
@RequestMapping(value = "/api_v1/business/project")
public class ProjectController extends BaseController {

    @Autowired
    private ProjectService proService;

    @Autowired
    private MessageService messageService;


    /**
     * 根据当前用户查询项目列表
     *
     * @return
     */
    @ApiOperation(value = "获取项目列表", notes = "获取项目列表")
    @ApiResponses({@ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "query", value = "查询对象", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "projStatus", value = "项目状态", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示大小", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageIndex", value = "", required = true, dataType = "int", paramType = "query"),
    })
    @RequestMapping(value = {"searchProjectList"}, method = RequestMethod.GET)
    public ReturnPageJson<List<Map<String, Object>>> searchProjectList(ProjectQuery query) {
        ReturnPageJson returnJson = null;
        try {
            returnJson = proService.getCurUserProjectLists(query);

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
     * 根据id获取项目数据
     *
     * @param request
     * @param id
     * @return
     */
    @ApiOperation(value = "根据id获取项目数据", notes = "根据id获取项目数据")
    @ApiResponses({@ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
    @ApiImplicitParam(name = "id", value = "主键Id", required = true, dataType = "String", paramType = "query")
    @RequestMapping(value = {"getProjectById"}, method = RequestMethod.GET)
    @ResponseBody
    public ReturnJson<Map<String, Object>> getProjectById(HttpServletRequest request, String id) {
        Map<String, Object> result = new HashMap<String, Object>();
        ReturnJson returnJson = null;
        try {
            if (CommonUtil.ifNotEmpty(id)) {
                Map<String, Object> data = proService.getProjectById(id);
                returnJson = ReturnJson.ok(data);
            } else {
                returnJson.setErrorMsg("项目id为null");
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
     * 删除项目
     *
     * @param id 需要删除部位的id
     * @return
     */
    @ApiOperation(value = "删除项目", notes = "删除项目")
    @ApiImplicitParam(name = "id", value = "需要删除的项目id", required = true, dataType = "String", paramType = "path")
    @RequestMapping(value = "/deleteProject/{id}", method = RequestMethod.PUT)
    public ReturnJson<Map<String, Object>> deleteProject(@PathVariable String id) {
        ReturnJson returnJson = null;
        try {
            if (CommonUtil.ifNotEmpty(id)) {
                proService.deleteProjectById(id);
                returnJson = ReturnJson.ok(200);
            } else {
                returnJson.setErrorMsg("id为null !!!");
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

    @ApiOperation(value = "根据项目id获取项目成员", notes = "根据项目id获取项目成员")
    @ApiResponses({@ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "projUid", value = "项目id", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "search", value = "搜索框", dataType = "String", paramType = "query")
    })
    @RequestMapping(value = {"getMemberByProjUid"}, method = RequestMethod.GET)
    @ResponseBody
    public ReturnJson<Map<String, Object>> getMemberByProjUid(HttpServletRequest request, String projUid, String search) {
        ReturnJson returnJson = null;
        try {
        	String dbType = CommonUtil.getDatabaseType();	//获取数据库类型
            if (CommonUtil.ifNotEmpty(projUid)) {
//                Map<String, Object> principal2 = new HashMap<>();
//                if (!CommonUtil.ifNotEmpty(search)){
//                    principal2 = proService.getMemberByProjUid(projUid, search, dbType);
//                }else{
//                    List<Map<String, Object>> list = proService.getMemberByProjUidAndSearch(projUid, search, dbType);
//                    principal2.put("member",list);
//                }
                // 查询项目负责人
                Map<String, Object> principal = proService.getMemberByProjUid(projUid, search, dbType);
                
                // 查询项目成员
                List<Map<String, Object>> list = proService.getMemberByProjUidAndSearch(projUid, search, dbType);
                if(principal != null) {
                	principal.put("member", list);
                }else {
                	principal = new HashMap<String, Object>();
                	principal.put("member", list);
                }
                
                returnJson = ReturnJson.ok(principal);
            } else {
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


    @ApiOperation(value = "查询最新项目", notes = "查询最新项目")
    @ApiResponses({@ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
    @ApiImplicitParam(name = "companyUid", value = "公司id", required = true, dataType = "String", paramType = "query")
    @RequestMapping(value = {"getNewestProj"}, method = RequestMethod.GET)
    @ResponseBody
    public ReturnJson<Map<String, Object>> getNewestProj(HttpServletRequest request, String userUid, String companyUid) {
        Map<String, Object> result = new HashMap<String, Object>();
        ReturnJson returnJson = null;
        try {
        	String dbType = CommonUtil.getDatabaseType();	//获取数据库类型
            if (!CommonUtil.ifNotEmpty(companyUid)) {
                companyUid = SessionUtil.getCompanyUid();
            }
            if (!CommonUtil.ifNotEmpty(userUid)) {
                userUid = SessionUtil.getUserUid();
            }
            boolean isCompanyAdmin = RoleUtil.isCompanyAdmin();

            Map<String, Object> data = proService.getNewestProj(userUid, companyUid, isCompanyAdmin, dbType);
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


    @ApiOperation(value = "根据多个用户的id查询用户职位", notes = "根据多个用户的id查询用户职位")
    @ApiResponses({@ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
    @ApiImplicitParam(name = "ids", value = "用户id", required = true, dataType = "String", paramType = "query")
    @RequestMapping(value = {"getPositionByUserIds"}, method = RequestMethod.GET)
    @ResponseBody
    public ReturnJson<Map<String, Object>> getPositionByUserIds(String ids) {
        ReturnJson returnJson = null;
        try {
            if (CommonUtil.ifNotEmpty(ids)) {
                String[] idss = ids.split(",");
                List<Map<String, Object>> position = proService.getPositionByUserIds(idss);
                returnJson = ReturnJson.ok(position);
            } else {
                returnJson.setErrorMsg("用户ids为null !!!");
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


    @ApiOperation(value = "编辑后删除之前的项目头像", notes = "编辑后删除之前的项目头像")
    @ApiResponses({@ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "fileId", value = "文件id", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "projUid", value = "项目id", required = true, dataType = "String", paramType = "query")
    })
    @RequestMapping(value = {"deleteProjHeadPortrait"}, method = RequestMethod.DELETE)
    @ResponseBody
    public ReturnJson<Map<String, Object>> deleteProjHeadPortrait(String fileId, String projUid) {
        ReturnJson returnJson = null;
        try {
            if (CommonUtil.ifNotEmpty(fileId) && CommonUtil.ifNotEmpty(projUid)) {
                proService.deleteProjHeadPortrait(fileId, projUid);
                returnJson = ReturnJson.ok();
            } else {
                returnJson.setErrorMsg("文件id或项目id为null !!!");
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

//=========================		班组		==============================	

    /**
     * 根据当前用户查询班组列表
     *
     * @param search     搜索框
     * @param pageSize   显示多少条
     * @param pageIndex  从第几条显示
     * @param userUid
     * @param companyUid
     * @return
     */
    @ApiOperation(value = "查询班组列表", notes = "查询班组列表")
    @ApiResponses({@ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageSize", value = "每页显示大小", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageIndex", value = "", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "userUid", value = "用户id", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "projUid", value = "项目id", dataType = "String", required = true, paramType = "query"),
            @ApiImplicitParam(name = "search", value = "搜索框", dataType = "String", paramType = "query")
    })
    @RequestMapping(value = {"searchTreamGroup"}, method = RequestMethod.GET)
    public ReturnPageJson<List<Map<String, Object>>> searchTreamGroup(String search, Integer pageSize, Integer pageIndex, String userUid, String companyUid, String projUid) {
        ReturnPageJson returnJson = null;
        try {
            returnJson = new ReturnPageJson();
            boolean isCompanyAdmin = RoleUtil.isCompanyAdmin();
            if (!CommonUtil.ifNotEmpty(companyUid)) {
                companyUid = SessionUtil.getCompanyUid();
            }
            if (!CommonUtil.ifNotEmpty(userUid)) {
                userUid = SessionUtil.getUserUid();
            }
            int start = (pageIndex - 1) * pageSize + 1;
            int end = pageIndex * pageSize;
            if (!CommonUtil.ifNotEmpty(search)) {
                search = null;
            }
            if (projUid != null) {
                List<Map<String, Object>> data = proService.searchTreamGroup(companyUid, userUid, search, start, end, isCompanyAdmin, projUid);
//				if(data != null && data.size()<pageSize) {
//					for(Map<String, Object> map : data) {
//						String principal = (String)map.get("PRINCIPAL_");
//						if(CommonUtil.ifNotEmpty(principal)) {
//							String[] userIds = principal.split(",");
//							List<Map<String, Object>> principalUserList = proService.searchUser(userIds);
//							map.put("principalUserList", principalUserList);
//						}
//					}
//				}
                returnJson.setData(data);
                returnJson.setTotalCount(data.size());
            } else {
                returnJson.setErrorMsg("项目id为null !!!");
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
     * 根据id查询班组信息
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "根据id查询班组信息", notes = "根据id查询班组信息")
    @ApiResponses({@ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
    @ApiImplicitParam(name = "id", value = "主键id", required = true, dataType = "String", paramType = "query")
    @RequestMapping(value = {"searchTreamGroupById"}, method = RequestMethod.GET)
    @ResponseBody
    public ReturnJson<Map<String, Object>> searchTreamGroupById(String id) {
        ReturnJson returnJson = null;
        try {
            if (CommonUtil.ifNotEmpty(id)) {
                Map<String, Object> data = proService.searchTreamGroupById(id);
//				String principal = (String) data.get("PRINCIPAL_");
//				if(CommonUtil.ifNotEmpty(principal)) {
//					String[] principal2 = principal.split(",");
//					List<Map<String, Object>> userList = proService.searchUser(principal2);
//					data.put("child", userList);
//				}
                returnJson = ReturnJson.ok(data);
            } else {
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


    @ApiOperation(value = "删除班组", notes = "删除班组")
    @ApiImplicitParam(name = "id", value = "班组ID", required = true, dataType = "String", paramType = "path")
    @RequestMapping(value = {"deleteTreamGroup"}, method = RequestMethod.POST)
    public ReturnJson deleteTreamGroup(String id) {
        ReturnJson returnJson = null;
        try {
            if (CommonUtil.ifNotEmpty(id)) {
                proService.deleteTreamGroup(id);
                returnJson = ReturnJson.ok(200);
            } else {
                returnJson.setErrorMsg("id为null !!!");
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

// ======================================		施工部位	============================================

    /**
     * 根据项目id查询部位列表
     *
     * @param projUid    项目id
     * @param userUid
     * @param search     搜索框
     * @param companyUid
     * @param pageSize   显示多少条
     * @param pageIndex  从第几条显示
     * @return
     */
    @ApiOperation(value = "查询施工部位列表", notes = "查询施工部位列表")
    @ApiResponses({@ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "projUid", value = "项目id", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "parentUid", value = "上级id", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示大小", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageIndex", value = "", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "search", value = "搜索框", dataType = "String", paramType = "query")
    })
    @RequestMapping(value = {"searchWorkPartList"}, method = RequestMethod.GET)
    public ReturnPageJson<List<Map<String, Object>>> searchWorkPartList(HttpServletRequest request, String projUid, String userUid, String search, String companyUid, int pageSize, int pageIndex, String parentUid) {
        ReturnPageJson returnJson = null;
        try {
        	String dbType = CommonUtil.getDatabaseType();	//获取数据库类型
            boolean isCompanyAdmin = RoleUtil.isCompanyAdmin();
            if (!CommonUtil.ifNotEmpty(companyUid)) {
                companyUid = SessionUtil.getCompanyUid();
            }

            if (!CommonUtil.ifNotEmpty(search)) {
                search = null;
            }

            returnJson = proService.searchWorkPartList(projUid, userUid, companyUid, search, pageIndex, pageSize, isCompanyAdmin, parentUid, dbType);
            /*for (Map<String, Object> map : data) {
                String id = (String) map.get("id");
                List<Map<String, Object>> list = proService.searchWorkPartList(projUid, userUid, companyUid, search, start, end, isCompanyAdmin, id, dbType);
                if (list.size() > 0 && list != null) {
                    map.put("flag", true);
                } else {
                    map.put("flag", false);
                }
            }*/

            /*if (CommonUtil.ifNotEmpty(projUid)) {

                returnJson = new ReturnPageJson();
                returnJson.setData(data);
                returnJson.setTotalCount(data.size());
            } else {
                returnJson.setErrorMsg("项目id为 null !!!");
            }*/


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
     * 根据部位id查询下级部位
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "根据部位id查询下级部位", notes = "根据部位id查询下级部位")
    @ApiResponses({@ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
    @ApiImplicitParam(name = "id", value = "上级id", required = true, dataType = "String", paramType = "query")
    @RequestMapping(value = {"searchWorkPartNext"}, method = RequestMethod.GET)
    @ResponseBody
    public ReturnJson<Map<String, Object>> searchWorkPartNext(String id) {    //根据部位id查询下级部位parentUid
        ReturnJson returnJson = null;
        try {

            List<Map<String, Object>> data = proService.searchWorkPartNext(id);
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
     * 根据id查询部位详情
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "根据id查询部位详情", notes = "根据id查询部位详情")
    @ApiResponses({@ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
    @ApiImplicitParam(name = "id", value = "主键id", required = true, dataType = "String", paramType = "query")
    @RequestMapping(value = {"searchWorkPartDetails"}, method = RequestMethod.GET)
    @ResponseBody
    public ReturnJson<Map<String, Object>> searchWorkPartDetails(String id) {    //根据部位id查询下级部位parentUid
        ReturnJson returnJson = null;
        try {

            List<Map<String, Object>> data = proService.searchWorkPartDetails(id);
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
     * 返回上一级
     *
     * @param superiorId 上级id
     * @param projUid    项目id
     * @return
     */
    @ApiOperation(value = "(施工部位)返回上一级", notes = "(施工部位)返回上一级")
    @ApiResponses({@ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "superiorId", value = "上级id", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "projUid", value = "项目id", required = true, dataType = "String", paramType = "query")
    })
    @RequestMapping(value = {"returnSuperior"}, method = RequestMethod.GET)
    public ReturnJson<Map<String, Object>> returnSuperior(String superiorId, String projUid) {
        ReturnJson returnJson = null;
        try {

            List<Map<String, Object>> data = proService.returnSuperior(superiorId, projUid);
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
     * 删除部位及所有下级
     *
     * @param id 需要删除部位的id
     * @return
     */
    @ApiOperation(value = "删除部位及所有下级", notes = "删除部位及所有下级")
    @ApiResponses({@ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
    @ApiImplicitParam(name = "id", value = "需要删除部位的id", required = true, dataType = "String", paramType = "path")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ReturnJson<Map<String, Object>> delete(@PathVariable String id) {
        ReturnJson returnJson = null;
        try {
            proService.deletePartById(id);
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
     * 根据id删除部位
     *
     * @return
     */
    @ApiOperation(value = "根据id删除部位", notes = "根据id删除部位")
    @ApiResponses({@ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
    @ApiImplicitParam(name = "id", value = "主键id", required = true, dataType = "String", paramType = "query")
    @RequestMapping(value = {"/{id}"}, method = RequestMethod.DELETE)
    @ResponseBody
    public ReturnJson<Map<String, Object>> deleteDrawingById(@PathVariable String id) {
        ReturnJson returnJson = null;
        try {
            proService.deleteDrawingById(id);
            returnJson = ReturnJson.ok(200);
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


// =========================	施工日志	================================	


    @ApiOperation(value = "查询日志列表", notes = "查询日志列表")
    @ApiResponses({@ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "projUid", value = "项目id", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示大小", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageIndex", value = "", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "type", value = "1:全部; 2:未读, 3:我的, 4:与我相关", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "search", value = "搜索框", dataType = "String", paramType = "query")
    })
    @RequestMapping(value = {"searchWorkLogList"}, method = RequestMethod.GET)
    public ReturnPageJson<List<Map<String, Object>>> searchWorkLogList(String projUid, String userUid, String search, String companyUid, int pageSize, int pageIndex, Integer type,String startDate,String endDate) {
        ReturnPageJson returnJson = null;
        try {


            if (!CommonUtil.ifNotEmpty(companyUid)) {
                companyUid = SessionUtil.getCompanyUid();
            }
            if (!CommonUtil.ifNotEmpty(userUid)) {
                userUid = SessionUtil.getUserUid();
            }
            if (!CommonUtil.ifNotEmpty(search)) {
                search = null;
            }
            if (CommonUtil.ifNotEmpty(projUid)) {
                List<Map<String, Object>> data = proService.searchWorkLogList(projUid, userUid, companyUid, search, pageIndex, pageSize, type, startDate,endDate);
                returnJson = new ReturnPageJson();
                returnJson.setData(data);
                /*if(data.size() > 0) {
                	if(dbType.equals("mysql")) {
                		returnJson.setTotalCount((Long) data.get(0).get("totalCount"));
                	}else {
                		returnJson.setTotalCount((Integer) data.get(0).get("totalCount"));
                	}
                }*/
                returnJson.setTotalCount(data.size()==0? 0:Integer.valueOf(String.valueOf(data.get(0).get("totalCount"))));
            } else {
                returnJson.setErrorMsg("项目id为null !!!");
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


    @ApiOperation(value = "根据id查询日志详情", notes = "根据id查询日志详情")
    @ApiResponses({@ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
    @ApiImplicitParam(name = "id", value = "主键id", required = true, dataType = "String", paramType = "query")
    @RequestMapping(value = {"searchLogById"}, method = RequestMethod.GET)
    @ResponseBody
    public ReturnJson<Map<String, Object>> searchLogById(String id, String userUid) {
        ReturnJson returnJson = null;
        try {
        	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        	String dbType = CommonUtil.getDatabaseType();	//获取数据库类型
            if (!CommonUtil.ifNotEmpty(userUid)) {
                userUid = SessionUtil.getUserUid();
            }
            messageService.insertLookUser(id, userUid);

            Map<String, Object> data = proService.searchLogById(id, userUid, dbType);
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


    @ApiOperation(value = "根据id删除日志", notes = "根据id删除日志")
    @ApiResponses({@ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
    @ApiImplicitParam(name = "id", value = "主键id", required = true, dataType = "String", paramType = "query")
    @RequestMapping(value = "/log/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public ReturnJson<Map<String, Object>> deleteLogById(@PathVariable String id) {
        ReturnJson returnJson = null;
        try {
            proService.deleteLogById(id);
            returnJson = ReturnJson.ok(200);
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

//	===============================		签证	===========================================	

    @ApiOperation(value = "查询施工签证列表", notes = "查询施工签证列表")
    @ApiResponses({@ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "projUid", value = "项目id", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示大小", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageIndex", value = "", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "type", value = "1:全部; 2:未读, 3:我的, 4:与我相关", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "search", value = "搜索框", dataType = "String", paramType = "query")
    })
    @RequestMapping(value = {"searchVisaList"}, method = RequestMethod.GET)
    public ReturnPageJson<List<Map<String, Object>>> searchVisaList(String projUid, String userUid, String search, String companyUid, int pageSize, int pageIndex, String type) {
        ReturnPageJson returnJson = null;
        try {
            boolean isCompanyAdmin = RoleUtil.isCompanyAdmin();
            if (!CommonUtil.ifNotEmpty(companyUid)) {
                companyUid = SessionUtil.getCompanyUid();
            }
            if (!CommonUtil.ifNotEmpty(userUid)) {
                userUid = SessionUtil.getUserUid();
            }
            int start = (pageIndex - 1) * pageSize + 1;
            int end = pageIndex * pageSize;
            if (!CommonUtil.ifNotEmpty(search)) {
                search = null;
            }
            List<Map<String, Object>> data = proService.searchVisaList(projUid, userUid, companyUid, search, start, end, isCompanyAdmin, type);
            returnJson = new ReturnPageJson();
            returnJson.setData(data);
            returnJson.setTotalCount(data.size());
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

    @ApiOperation(value = "根据id查询施工签证", notes = "根据id查询施工签证")
    @ApiResponses({@ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
    @ApiImplicitParam(name = "id", value = "主键id", required = true, dataType = "String", paramType = "query")
    @RequestMapping(value = {"searchvisaById"}, method = RequestMethod.GET)
    @ResponseBody
    public ReturnJson<Map<String, Object>> searchvisaById(String id, String userUid) {
        ReturnJson returnJson = null;
        try {
            if (!CommonUtil.ifNotEmpty(userUid)) {
                userUid = SessionUtil.getUserUid();
            }
            messageService.insertLookUser(id, userUid);

            Map<String, Object> data = proService.searchvisaById(id, userUid);
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


    @ApiOperation(value = "根据id删除施工签证", notes = "根据id删除施工签证")
    @ApiResponses({@ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
    @ApiImplicitParam(name = "id", value = "主键id", required = true, dataType = "String", paramType = "query")
    @RequestMapping(value = "/visa/{id}", method = RequestMethod.DELETE)
    public ReturnJson<Map<String, Object>> deleteVisaById(@PathVariable String id) {
        ReturnJson returnJson = null;
        try {
            proService.deleteVisaById(id);
            returnJson = ReturnJson.ok(200);
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

//	===============================	  图纸	===========================================	

    @ApiOperation(value = "根据项目id查询图纸列表", notes = "根据项目id查询图纸列表")
    @ApiResponses({@ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "projUid", value = "项目id", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示大小", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageIndex", value = "", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "search", value = "搜索框", dataType = "String", paramType = "query")
    })
    @RequestMapping(value = {"searchDrawingList"}, method = RequestMethod.GET)
    public ReturnPageJson<List<Map<String, Object>>> searchDrawingList(String projUid, String userUid, String search, String companyUid, int pageSize, int pageIndex) {
        ReturnPageJson returnJson = null;
        try {
            boolean isCompanyAdmin = RoleUtil.isCompanyAdmin();
            if (!CommonUtil.ifNotEmpty(companyUid)) {
                companyUid = SessionUtil.getCompanyUid();
            }
            if (!CommonUtil.ifNotEmpty(userUid)) {
                userUid = SessionUtil.getUserUid();
            }

            int start = (pageIndex - 1) * pageSize + 1;
            int end = pageIndex * pageSize;

            List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
            if (CommonUtil.ifNotEmpty(projUid)) {

                data = proService.searchDrawingList(projUid, userUid, companyUid, search, start, end, isCompanyAdmin);
            } else {
                returnJson.setData("项目id为null !!!");
            }

            returnJson = new ReturnPageJson();
            returnJson.setData(data);
            returnJson.setTotalCount(data.size());
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
     * 根据id查询图纸详情
     *
     * @return
     */
    @ApiOperation(value = "根据id查询图纸详情", notes = "根据id查询图纸详情")
    @ApiResponses({@ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
    @ApiImplicitParam(name = "id", value = "主键id", required = true, dataType = "String", paramType = "query")
    @RequestMapping(value = {"searchDrawingById"}, method = RequestMethod.GET)
    @ResponseBody
    public ReturnJson<Map<String, Object>> searchDrawingById(String id) {
        ReturnJson returnJson = null;
        try {

            Map<String, Object> data = proService.searchDrawingById(id);
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


//	===============================	  拍照	===========================================	

    @ApiOperation(value = "根据项目id查询拍照列表", notes = "根据项目id查询拍照列表")
    @ApiResponses({@ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "projUid", value = "项目id", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示大小", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageIndex", value = "", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "search", value = "搜索框", dataType = "String", paramType = "query")
    })
    @RequestMapping(value = {"searchPhotographList"}, method = RequestMethod.GET)
    public ReturnPageJson<Map<String, Object>> searchPhotographList(String projUid, String userUid, String search, String companyUid, int pageSize, int pageIndex) {
        ReturnPageJson returnJson = null;

        Map<String, Object> result = new HashMap<String, Object>();
        try {
            boolean isCompanyAdmin = RoleUtil.isCompanyAdmin();
            if (!CommonUtil.ifNotEmpty(companyUid)) {
                companyUid = SessionUtil.getCompanyUid();
            }
            if (!CommonUtil.ifNotEmpty(userUid)) {
                userUid = SessionUtil.getUserUid();
            }

            int start = (pageIndex - 1) * pageSize + 1;
            int end = pageIndex * pageSize;

            List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
            Map<String, List<Map<String, Object>>> groupData = new HashMap<String, List<Map<String, Object>>>();

            List<Map<String, List<Map<String, Object>>>> groupData2 = new ArrayList<Map<String, List<Map<String, Object>>>>();

            List<String> timeData = new ArrayList<String>();

            if (CommonUtil.ifNotEmpty(projUid)) {

                data = proService.searchPhotographList(projUid, userUid, companyUid, search, start, end, isCompanyAdmin);
                for (Map<String, Object> map : data) {

                    if (timeData.size() == 0 || !timeData.contains(map.get("createDate"))) {
                        timeData.add((String) map.get("createDate"));
                    }

                }
				/*for(Map<String, Object> map : data) {
					if(groupData2.contains(groupData.get("createDate"))) {
						groupData.get(map.get("createDate")).add(map);
						groupData2.add(groupData);
					}
					if(groupData.containsKey(map.get("createDate")) ) {
						
						groupData.get(map.get("createDate")).add(map);
						groupData2.add(groupData);
					}else {
						Map<String, List<Map<String, Object>>> groupData3 = new HashMap<String, List<Map<String, Object>>>();
						
						List<Map<String, Object>> cildr = new ArrayList<>();
						cildr.add(map);
						groupData3.put((String) map.get("createDate"), cildr);
						
						groupData2.add(groupData3);
					}
				}*/
            } else {
                returnJson.setErrorMsg("项目id为null !!!");
            }
            result.put("Data", data);
            result.put("timeData", timeData);
            returnJson = new ReturnPageJson();
            returnJson.setData(result);
            returnJson.setTotalCount(data.size());
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


    @ApiOperation(value = "根据id现场拍照", notes = "根据id现场拍照")
    @ApiResponses({@ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
    @ApiImplicitParam(name = "id", value = "主键id", required = true, dataType = "String", paramType = "query")
    @RequestMapping(value = "/photograph/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public ReturnJson<Map<String, Object>> deletePhotographById(@PathVariable String id) {
        ReturnJson returnJson = null;
        try {
            if (CommonUtil.ifNotEmpty(id)) {
                proService.deletePhotographById(id);
                returnJson = ReturnJson.ok();
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


//	===============================	  工作汇报	===========================================		

    @ApiOperation(value = "查询工作汇报列表", notes = "查询工作汇报列表")
    @ApiResponses({@ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "projUid", value = "项目id", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示大小", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageIndex", value = "", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "dataType", value = "1:汇报给我  2:我的汇报", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "type", value = "日报, 周报, 月报", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "search", value = "搜索框", dataType = "String", paramType = "query")
    })
    @RequestMapping(value = {"searchReportList"}, method = RequestMethod.GET)
    public ReturnPageJson<List<Map<String, Object>>> searchReportList(String projUid, String userUid, String search, String companyUid, int pageSize, int pageIndex, String type, int dataType,String startDate,String endDate) {
        ReturnPageJson returnJson = null;

        try {
            if (!CommonUtil.ifNotEmpty(companyUid)) {
                companyUid = SessionUtil.getCompanyUid();
            }
            if (!CommonUtil.ifNotEmpty(userUid)) {
                userUid = SessionUtil.getUserUid();
            }

            int start = (pageIndex - 1) * pageSize + 1;
            int end = pageIndex * pageSize;
            if (!CommonUtil.ifNotEmpty(search)) {
                search = null;
            }
            returnJson = proService.searchReportList(projUid, userUid, companyUid, search, start, end, type, dataType, startDate,endDate);

            /*returnJson = new ReturnPageJson();
            returnJson.setData(data);
            returnJson.setTotalCount(data.size()==0? 0:(Integer)data.get(0).get("totalCount"));*/
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
     * 根据id工作汇报详情
     *
     * @param id 主键id
     * @return
     */
    @ApiOperation(value = "根据id查询图纸详情", notes = "根据id查询图纸详情")
    @ApiResponses({@ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
    @ApiImplicitParam(name = "id", value = "主键id", required = true, dataType = "String", paramType = "query")
    @RequestMapping(value = {"reportDetailsById"}, method = RequestMethod.GET)
    @ResponseBody
    public ReturnJson<Map<String, Object>> reportDetailsById(String id, String userUid) {
        ReturnJson returnJson = null;
        String dbType = CommonUtil.getDatabaseType();
        try {
            if (!CommonUtil.ifNotEmpty(userUid)) {
                userUid = SessionUtil.getUserUid();
            }
            Map<String, Object> data = proService.reportDetailsById(id, dbType, userUid);
            messageService.insertLookUser(id, userUid);
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
     * 更新月报审批状态
     * @param id
     * @param status
     * @return
     */
    @ApiOperation(value = "更新月报审批状态", notes = "更新月报审批状态")
    @ApiResponses({@ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
    @ApiImplicitParam(name = "id", value = "主键id", required = true, dataType = "String", paramType = "query")
    @RequestMapping(value = {"/{id}/{status}"}, method = RequestMethod.PUT)
    @ResponseBody
    public ReturnJson<Map<String, Object>> updateReportStatusById(@PathVariable String id, @PathVariable Integer status) {
    	ReturnJson returnJson = null;
    	String dbType = CommonUtil.getDatabaseType();
    	try {
    		proService.updateReportStatusById(id, status);
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
     * 查询当天日报检查数据
     * @param id 主键id
     * @return
     */
    @ApiOperation(value = "查询当天日报检查数据", notes = "查询当天日报检查数据")
    @ApiResponses({@ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
    @ApiImplicitParams({
        @ApiImplicitParam(name = "projUid", value = "项目id", required = true, dataType = "String", paramType = "query"), 
        @ApiImplicitParam(name = "id", value = "id", dataType = "String", paramType = "query")
    })
    @RequestMapping(value = {"searchReportInspect"}, method = RequestMethod.GET)
    @ResponseBody
    public ReturnJson<Map<String, Object>> searchReportInspect(String projUid, String id, String dataDate) {
    	ReturnJson returnJson = null;
    	try {

    		//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    		Map<String, Object> data = proService.searchReportInspect(projUid, id, dataDate);
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
     * 根据id删除日报问题统计
     * @param id 主键id
     * @return
     */
    @ApiOperation(value = "根据id删除日报问题统计", notes = "根据id删除日报问题统计")
    @ApiResponses({@ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
    @ApiImplicitParam(name = "id", value = "主键id", required = true, dataType = "String", paramType = "query")
    @RequestMapping(value = {"/reportIssue/{id}"}, method = RequestMethod.DELETE)
    @ResponseBody
    public ReturnJson<Map<String, Object>> deleteReportIssueById(@PathVariable String id) {
        ReturnJson returnJson = null;
        try {
            if (CommonUtil.ifNotEmpty(id)) {
                proService.deleteReportIssueById(id);    //未完
                returnJson = ReturnJson.ok();
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
     * 根据id删除工作汇报
     *
     * @param id 主键id
     * @return
     */
    @ApiOperation(value = "根据id删除汇报", notes = "根据id删除汇报")
    @ApiResponses({@ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
    @ApiImplicitParam(name = "id", value = "主键id", required = true, dataType = "String", paramType = "query")
    @RequestMapping(value = {"/report/{id}"}, method = RequestMethod.DELETE)
    @ResponseBody
    public ReturnJson<Map<String, Object>> deleteReportById(@PathVariable String id) {
        ReturnJson returnJson = null;
        try {
            if (CommonUtil.ifNotEmpty(id)) {
                proService.deleteReportById(id);    //未完
                returnJson = ReturnJson.ok();
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
     * 获取当年周开始结束时间
     * @author 车斯剑
     * @date 2017年8月30日上午9:36:30
     * @return
     */
    @ApiOperation(value = "获取当年周开始结束时间", notes = "获取当年周开始结束时间")
    @ApiResponses({@ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
    @ApiImplicitParam(name = "id", value = "主键id", required = true, dataType = "String", paramType = "query")
    @RequestMapping(value = {"getWeekDates"}, method = RequestMethod.GET)
    @ResponseBody
    public ReturnPageJson<Map<String, Object>> getWeekDates() {
        ReturnPageJson returnJson = null;
        try {
            List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
            Map<String,Object> result = new HashMap<String, Object>();
            //开始时间
            Calendar cal1 = Calendar.getInstance();
            int weekNum = cal1.get(Calendar.WEEK_OF_YEAR);
            for(int i=0;i<53;i++){
                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                Map<String,Object> week = new HashMap<String, Object>();
                cal.set(Calendar.WEEK_OF_YEAR, i);
                String month =(cal.get(Calendar.MONTH) + 1)<10? "0"+(cal.get(Calendar.MONTH) + 1):(cal.get(Calendar.MONTH) + 1)+"";
                String weekDay =cal.get(Calendar.DAY_OF_MONTH)<10?  "0"+cal.get(Calendar.DAY_OF_MONTH):cal.get(Calendar.DAY_OF_MONTH)+"";
                String startDate = cal.get(Calendar.YEAR) + "-" + month + "-" + weekDay;
                cal.add(Calendar.DAY_OF_WEEK, 6);
                String month2 =(cal.get(Calendar.MONTH) + 1)<10? "0"+(cal.get(Calendar.MONTH) + 1):(cal.get(Calendar.MONTH) + 1)+"";
                String weekDay2 =cal.get(Calendar.DAY_OF_MONTH)<10?  "0"+cal.get(Calendar.DAY_OF_MONTH):cal.get(Calendar.DAY_OF_MONTH)+"";
                String endDate = cal.get(Calendar.YEAR) + "-" + month2 + "-" + weekDay2;
                week.put("key", startDate+"至"+endDate);
                week.put("value", startDate+"至"+endDate);
                if(i==weekNum){
                    result.put("currentWeek", startDate+"至"+endDate);
                }
                data.add(week);
            }
            result.put("weekDates", data);
            returnJson = new ReturnPageJson();
            returnJson.setData(result);
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

//	===============================	  首页	===========================================			

    /**
     * 根据项目id首页信息
     *
     * @return
     */
    @ApiOperation(value = "根据项目id首页信息", notes = "根据项目id首页信息")
    @ApiResponses({@ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
    @ApiImplicitParam(name = "projUid", value = "项目id", required = true, dataType = "String", paramType = "query")
    @RequestMapping(value = {"searchHomeByProjUid"}, method = RequestMethod.GET)
    @ResponseBody
    public ReturnJson<Map<String, Object>> searchHomeByProjUid(String projUid) {
        ReturnJson returnJson = null;
        try {
        	String dbType = CommonUtil.getDatabaseType();	//获取数据库类型
            Map<String, Object> data = new HashMap<String, Object>();


                Map<String, Object> realMap = new HashMap<String, Object>();
                /**实名制end**/

                /** 进度start */
                Map<String, Object> info = proService.searchScheduleInfo(projUid, dbType);
                data.put("info", info);
                /*if (CommonUtil.ifNotEmpty(info) && info != null) {
                    Date start = (Date) info.get("planStart");
                    Date end = (Date) info.get("planEnd");
                    if (start!=null&&null!=end&&new Date().getTime() > start.getTime() && new Date().getTime() < end.getTime()) {
                        info.put("flag", true);
                    } else {
                        info.put("flag", false);
                    }
                    data.put("info", info);
                }*/
                /** 进度end */


                /** 新闻公告start */
                List<Map<String, Object>> newsList = proService.searchHomeNews(projUid, dbType);
                if (newsList.size() > 0) {
                    data.put("newsList", newsList);
                }

                data.put("real", realMap);
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
     * 获取项目进度
     * @param projUid
     * @return
     */
    @ApiOperation(value = "获取项目进度", notes = "获取项目进度")
    @ApiResponses({@ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
    @ApiImplicitParam(name = "projUid", value = "项目Id", dataType = "String", paramType = "query")
    @RequestMapping(value = {"projSchedule"}, method = RequestMethod.GET)
    @ResponseBody
    public ReturnJson<Map<String, Object>> getScheduleInfo(String projUid) {
        ReturnJson returnJson = null;
        try {
            String dbType = CommonUtil.getDatabaseType();	//获取数据库类型
            Map<String, Object> data = proService.searchScheduleInfo(projUid, dbType);
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



    private double formatDecimal(double number, int count) {
        BigDecimal bg = new BigDecimal(number);
        double doubleValue = bg.setScale(count, BigDecimal.ROUND_HALF_UP).doubleValue();
        return doubleValue;
    }
    
    
    /**
     * 获取建设单位统一社会信用代码
     * @param request
     * @return
     */
    @ApiOperation(value = "获取建设单位统一社会信用代码", notes = "获取建设单位统一社会信用代码")
    @ApiResponses({@ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
    @ApiImplicitParam(name = "companyUid", value = "项目Id", dataType = "String", paramType = "query")
    @RequestMapping(value = {"getSocietyCode"}, method = RequestMethod.GET)
    @ResponseBody
    public ReturnJson<Map<String, Object>> getSocietyCode(HttpServletRequest request, String companyUid) {
        Map<String, Object> result = new HashMap<String, Object>();
        ReturnJson returnJson = null;
        try {
        	if (!CommonUtil.ifNotEmpty(companyUid)) {
                companyUid = SessionUtil.getCompanyUid();
            }
           
            Map<String, Object> data = proService.getSocietyCode(companyUid);
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
}
