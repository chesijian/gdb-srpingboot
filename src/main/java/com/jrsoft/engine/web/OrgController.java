package com.jrsoft.engine.web;/**
 * Created by chesijian on 2021/6/16.
 */

import com.jrsoft.engine.base.domain.org.OrgTreeNode;
import com.jrsoft.engine.base.domain.sys.PfMenu;
import com.jrsoft.engine.base.model.ReturnJson;
import com.jrsoft.engine.base.model.ReturnPageJson;
import com.jrsoft.engine.base.web.BaseController;
import com.jrsoft.engine.common.query.UserQuery;
import com.jrsoft.engine.common.utils.CommonUtil;
import com.jrsoft.engine.common.utils.SessionUtil;
import com.jrsoft.engine.exception.EngineException;
import com.jrsoft.engine.model.org.OrgPosition;
import com.jrsoft.engine.model.org.OrgUser;
import com.jrsoft.engine.service.MenuService;
import com.jrsoft.engine.service.OrgManagerServiceI;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName OrgController
 * @Description TODO
 * @Author chesijian
 * @Date 2021/6/16 11:56
 * @Version 1.0
 */
@RestController("ApiOrgController")
@RequestMapping(value = "/api_v1/org")
public class OrgController extends BaseController {

    @Autowired
    private OrgManagerServiceI orgManagerService;



    @ApiOperation(value="获取组织", notes="获取组织")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "companyUid", value = "组织主键,如果为空默认当前登录人的组织",  dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "type", value = "all表示部门和员工，depart表示只有部门,position表示岗位，user表示用户",  dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "projType", value = "查询键，可以是用户编码，用户名称",  dataType = "String",paramType = "query")
    })
    @RequestMapping(value="", method= RequestMethod.GET)
    public ReturnJson<List<OrgUser>> getOrg(String type, String projType) {
        ReturnJson returnJson = new ReturnJson();
        try{
            OrgTreeNode newRoot = orgManagerService.getOrg(type,projType);
            returnJson = ReturnJson.ok(newRoot);
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

    @ApiOperation(value="根据部门分页获取用户", notes="根据部门主键分页获取用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "companyUid", value = "组织主键,如果为空默认当前登录人的组织",  dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "departUid", value = "部门主键如果为空默认查询当前登录人的组织下所有员工",  dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "positionUid", value = "岗位主键",  dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "roleUid", value = "角色主键",  dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "search", value = "查询键，可以是用户编码，用户名称",  dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "start", value = "分页开始位置", required = true,dataType = "int",paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示大小",required = true,  dataType = "int",paramType = "query"),
            @ApiImplicitParam(name = "ifContainChild", value = "是否递归查找子部门的用户，默认是false",  dataType = "boolean",paramType = "query",defaultValue = "false"),
            @ApiImplicitParam(name = "containQuit", value = "是否包含离职人员，默认是true",  dataType = "boolean",paramType = "query",defaultValue = "false"),
            @ApiImplicitParam(name = "ifQueryPosition", value = "是否查询岗位信息",  dataType = "boolean",paramType = "query")
    })
    @RequestMapping(value="/users", method= RequestMethod.GET)
    public ReturnPageJson<List<OrgUser>> getUsers(UserQuery userQuery) {
        ReturnPageJson returnJson = new ReturnPageJson();
        try{
            long totalCount=orgManagerService.queryUserTotalCount(userQuery);
            returnJson.setTotalCount(totalCount);
            List<OrgUser> userList = orgManagerService.getUserList(userQuery);
            //查找岗位
            if(userQuery.isIfQueryPosition()){
                List<OrgUser> newUserList = new ArrayList<OrgUser>();
                if(CommonUtil.ifNotEmptyList(userList)){
                    for(OrgUser user : userList){
                        List<OrgPosition> positionList = orgManagerService.queryUserPositionById(user.getId());
                        StringBuilder positionSb = new StringBuilder();
                        StringBuilder departSb = new StringBuilder();
                        if(CommonUtil.ifNotEmptyList(positionList)){
                            for(OrgPosition orgPosition : positionList){
                                positionSb.append(",").append(orgPosition.getPositionName());
                                departSb.append(",").append(orgPosition.getDepartName());
                            }
                            positionSb.deleteCharAt(0);
                            departSb.deleteCharAt(0);
                            user.setPositionName(positionSb.toString());
                            user.setDepartName(departSb.toString());
                        }
                        newUserList.add(user);
                    }
                }

                returnJson.setData(newUserList);
            }else{

                returnJson.setData(userList);
            }
        }catch (EngineException e){
            e.printStackTrace();
            returnJson = new ReturnPageJson(e.errcode,e.getMessage());
        }catch (Exception e){
            e.printStackTrace();
            returnJson = new ReturnPageJson(EngineException.ERRCODE_EXCEPTION,e.getMessage());
        }finally {
            return (ReturnPageJson)returnJson;
        }
    }

    @ApiOperation(value="查询菜单树", notes="查询菜单树")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "parentId", value = "上级菜单主键", required = true, dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "isFilter", value = "是否过滤角色，默认false", required = true, dataType = "boolean",paramType = "query"),
            @ApiImplicitParam(name = "isDeep", value = "是否递归获取菜单，默认false",  dataType = "boolean",paramType = "query"),
            @ApiImplicitParam(name = "isContainDisabled", value = "是否包含未启用，默认false",  dataType = "boolean",paramType = "query"),
            @ApiImplicitParam(name = "isContainAuth", value = "是否包含权限，默认false",  dataType = "boolean",paramType = "query"),
            @ApiImplicitParam(name = "defaultAuth", value = "菜单没有关联pf_auth时是否默认增删改查导出按钮权限，默认false, 只有ifContainAuth为true时有效",  dataType = "boolean",paramType = "query")
    })
    @GetMapping("/menus")
    public Object getAllMenus( boolean isFilter, String  parentId, boolean isDeep,boolean isContainAuth,boolean isContainDisabled, boolean defaultAuth) {
        //新增部门
        ReturnJson returnJson = null;
        try {
            List<PfMenu> data = orgManagerService.findAllMenu(parentId);

            returnJson = new ReturnJson(data);
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
