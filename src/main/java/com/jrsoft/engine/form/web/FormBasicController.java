package com.jrsoft.engine.form.web;


import com.jrsoft.engine.base.model.ReturnJson;
import com.jrsoft.engine.base.model.ReturnPageJson;
import com.jrsoft.engine.common.utils.CommonUtil;
import com.jrsoft.engine.common.utils.RoleUtil;
import com.jrsoft.engine.common.utils.SessionUtil;

import com.jrsoft.engine.exception.EngineException;
import com.jrsoft.engine.form.domain.FormBasic;
import com.jrsoft.engine.form.service.FormService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 获取表单配置	GET	/{formKey}/config
 * 获取表单列表数据 POST /data_list
 * 获取表单树表数据 POST /tree_data
 * 获取表单主表数据 POST /data
 */
@Api(value = "FormController", description = "表单管理接口")
@RestController
@RequestMapping(value = "/api_v1/form")
public class FormBasicController {

    @Autowired
    private FormService formService;


    /*@Autowired
    private PfSqlServiceI sqlService;

    @Autowired
    private FormInfoService formInfoService;

    @Autowired
    private FormColumnService formColumnService;

	@Autowired
    private TreeService treeService;

    @Autowired
	private BpmConfFormAuthService bpmConfFormAuthService;*/


    @ApiOperation(value="获取所有表单，根据模块分组", notes="获取所有表单，根据模块分组")
    @RequestMapping(value="", method= RequestMethod.GET)
    public ReturnJson<List<FormBasic>> getAll() {
        ReturnJson returnJson = null;
        try{
            List<FormBasic> data = null;//formService.findAll();
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

    @ApiOperation(value="获取表单配置", notes="根据formKey来获取表单配置")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "formKey", value = "FORM_KEY_", required = true, dataType = "String",paramType = "path"),
            @ApiImplicitParam(name = "procDefId", value = "流程定义id，流程过程获取权限",  dataType = "string",paramType = "query"),
            @ApiImplicitParam(name = "actId", value = "流程环节id", dataType = "string",paramType = "query")
    })
    @RequestMapping(value="/{formKey}/config", method= RequestMethod.GET)
    public ReturnJson<FormBasic> getConfig(@PathVariable String formKey, String procDefId, String actId) {
        ReturnJson returnJson = null;
        try{
            FormBasic formBasic = formService.findByFormKey(formKey);
            if (CommonUtil.ifNotEmpty(formBasic)){
                if(!RoleUtil.isAdmin() && !RoleUtil.isCompanyAdmin()){
                    formBasic =  formService.getAuths(formBasic);
                }
                // 直接获取流程过程中表单权限
                // if(CommonUtil.ifNotEmpty(procDefId) && CommonUtil.ifNotEmpty(actId)){
                //     查找当前节点对应的表单权限
                //     Map<String, Map<String, Integer>> formAuth = bpmConfFormAuthService.getAuthConfig(formKey,procDefId,actId);
                //    formBasic.setWfFormAuth(formAuth);
                // }
            }
            returnJson = ReturnJson.ok(formBasic);
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

    /*@ApiOperation(value="获取表单列配置", notes="获取表单列配置")
    @ApiImplicitParam(name = "formId", value = "formId", required = true, dataType = "String",paramType = "path")
    @RequestMapping(value="/{formId}/form/columns", method= RequestMethod.GET)
    public ReturnJson<List<FormColumn>> getFormColumns(@PathVariable String formId) {
        ReturnJson returnJson = null;
        try{
            List<FormColumn> formBasic = formColumnService.findColumnByFormId(formId, 1);
            returnJson = ReturnJson.ok(formBasic);
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

    @ApiOperation(value="获取表单列表数据", notes="获取表单列表")
    @ApiResponses({ @ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
    @ApiImplicitParam(name = "params", value = "查询实体", required = true, dataType = "ParamInfo")
    @PostMapping("/data_list")
    public ReturnPageJson<List<Map<String, Object>>> getDataList(@RequestBody ParamInfo params) {
        ReturnPageJson returnJson = null;
        try{
            SessionUtil.getCurrentLog().setKey(params.getFormKey());
            returnJson = new ReturnPageJson();
            returnJson =  formService.findDataList(params);
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

    @ApiOperation(value="获取树表单列表数据", notes="获取树表单列表")
    @ApiResponses({ @ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
    @ApiImplicitParam(name = "params", value = "查询实体", required = true, dataType = "ParamInfo")
    @PostMapping("/tree_data")
    public ReturnJson<List<Map<String, Object>>> getTreeData(@RequestBody ParamInfo params) {
        ReturnJson returnJson = null;
        try{
            SessionUtil.getCurrentLog().setKey(params.getFormKey());
            List<Map<String, Object>> dataList =  formService.findTreeDataList(params);
            returnJson = ReturnJson.ok(dataList);
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

    @ApiOperation(value="保存表单配置", notes="保存表单配置")
    @ApiResponses({ @ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
    @ApiImplicitParam(name = "formBasic", value = "表单配置实体", required = true, dataType = "FormBasic")
    @RequestMapping(value="/{formKey}/config", method= RequestMethod.POST)
    public ReturnJson<List<FormBasicInfo>> saveFormInfo(@RequestBody FormBasic formBasic, @PathVariable String formKey) {
        ReturnJson returnJson = null;
        try{
            SessionUtil.getCurrentLog().setKey(formKey);

            SessionUtil.getCurrentLog().setTitle("[保存表单配置]-" + formBasic.getTitle());
            formBasic.setFormKey(formKey);
            formInfoService.saveFormInfo(formBasic);
            returnJson = ReturnJson.ok(formKey);
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

    @ApiOperation(value="保存新表单配置", notes="保存新表单配置")
    @ApiResponses({ @ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
    @ApiImplicitParam(name = "formBasic", value = "新表单配置实体", required = true, dataType = "FormBasic")
    @RequestMapping(value="/{formKey}/new/config", method= RequestMethod.POST)
    public ReturnJson<List<FormBasicInfo>> saveNewFormInfo(@RequestBody FormBasic formBasic, @PathVariable String formKey) {
        ReturnJson returnJson = null;
        try{
            SessionUtil.getCurrentLog().setKey(formKey);

            SessionUtil.getCurrentLog().setTitle("[保存表单配置]-" + formBasic.getTitle());
            formBasic.setFormKey(formKey);
            formInfoService.saveNewFormInfo(formBasic);
            returnJson = ReturnJson.ok(formKey);
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

    @ApiOperation(value="copy表单配置", notes="copy表单配置")
    @ApiResponses({ @ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
    @RequestMapping(value="/{formKey}/copy", method= RequestMethod.GET)
    public ReturnJson<List<FormBasicInfo>> saveFormCopy(@PathVariable String formKey) {
        ReturnJson returnJson = null;
        try{
            SessionUtil.getCurrentLog().setKey(formKey);
            formInfoService.saveFormCopy(formKey);
            returnJson = ReturnJson.ok(formKey);
        }catch (EngineException e){
            e.printStackTrace();
            returnJson = ReturnJson.error(e.errcode,e.getMessage());
        }catch (Exception e){
            e.printStackTrace();
            returnJson = ReturnJson.error(EngineException.ERRCODE_EXCEPTION,e.getMessage());
        }finally {
            return returnJson;
        }
    }*/

    /*@ApiOperation(value="同步表单配置", notes="同步表单配置")
    @ApiResponses({ @ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
    @RequestMapping(value="/{formKey}/sync", method= RequestMethod.GET)
    public ReturnJson<List<FormBasicInfo>> syncForm(@PathVariable String formKey) {
        ReturnJson returnJson = null;
        try{
            formInfoService.syncForm(formKey);
            SessionUtil.getCurrentLog().setKey(formKey);
            returnJson = ReturnJson.ok(formKey);
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

	@ApiOperation(value="导出表单", notes="导出表单")
	@ApiResponses({ @ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
	@RequestMapping(value="/{formKey}/export", method= RequestMethod.GET)
	public ReturnJson exportForm(HttpServletRequest request, HttpServletResponse response, @PathVariable String formKey) {
		ReturnJson returnJson = null;
		try{
			SysAppModule appModule = AppUtil.exportForm(formKey);
			String id = appModule.getId();
			String fileName = appModule.getFileName();
			InputStream in = null;
			BufferedOutputStream bout = null;
			ReturnJson var9;
			try {
				try {
					if(!CommonUtil.ifNotEmpty(id)) {
						throw new EngineException("id不能为空！");
					}

					PfFormServiceI pfFormService = (PfFormServiceI) ServiceHelper.getBean(PfFormServiceI.class);
					PfAttachment attachment = new PfAttachment();
					attachment.setFileName(fileName);
					attachment.setId(id);
					attachment.setThumb(false);
					in = pfFormService.getTempInputStream(attachment);

					if(in != null) {
						fileName = URLDecoder.decode(attachment.getFileName(), "utf-8");
						System.out.println("----fileName-----"+fileName);
						response.setContentType("application/x-download;charset=UTF-8");
						response.addHeader("Content-Disposition", "attachment;filename=\"" + new String(fileName.getBytes("utf-8"), "iso8859-1") + "\"");
						bout = new BufferedOutputStream(response.getOutputStream());
						IOUtils.copy(in, bout);

						try {
							IOUtils.copy(in, bout);
						} catch (Exception var15) {
							var15.printStackTrace();
						}
						return ReturnJson.ok();
					}

					var9 = ReturnJson.error("找不到文件");
				} catch (EngineException var16) {
					var9 = ReturnJson.error(var16.errcode, var16.getMessage());
					return var9;
				} catch (Exception var17) {
					var9 = ReturnJson.error(EngineException.ERRCODE_EXCEPTION, var17.getMessage());
					return var9;
				}
			} finally {
				IOUtils.closeQuietly(in);
				IOUtils.closeQuietly(bout);
			}

			return var9;

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

    @ApiOperation(value="上传表单", notes="上传表单")
    @ApiResponses({ @ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
    @RequestMapping(value="/upload", method= RequestMethod.POST)
    public ReturnJson uploadForm(HttpServletRequest request, HttpServletResponse response, @RequestBody UploadFormParams params) {
        ReturnJson returnJson = null;
        try{
            AppUtil.installForm(params.getId());
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

    @ApiOperation(value="获取表单主表数据", notes="获取表单主表数据")
    @ApiResponses({ @ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
    @ApiImplicitParam(name = "params", value = "查询实体", required = true, dataType = "ParamInfo")
    @PostMapping("/data")
    public ReturnJson<Map<String, Object>> getData(@RequestBody ParamInfo params) {
        ReturnJson returnJson = null;
        try{
            SessionUtil.getCurrentLog().setKey(params.getFormKey());
            Map<String, Object> dataList =  formService.findData(params);
            returnJson = ReturnJson.ok(dataList);
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

    @ApiOperation(value="获取数据库表下拉", notes="获取数据库表下拉")
    @ApiResponses({ @ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "formId", value = "表单formId 和formKey 二选一",  dataType = "string",paramType = "query"),
            @ApiImplicitParam(name = "formKey", value = "表单key 和formId 二选一",  dataType = "string",paramType = "query"),
            @ApiImplicitParam(name = "columnId", value = "列主键", required = true,dataType = "string",paramType = "query")
    })
    @PostMapping("/dic/sql/data")
    public ReturnJson<List<Map<String, Object>>> getSqlDicData(@RequestBody QueryInfo queryInfo){
        ReturnJson returnJson = null;
        try{
            List<Map<String, Object>> dataList =  formService.findSqlDicData(queryInfo);
            returnJson = ReturnJson.ok(dataList);
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

    @ApiOperation(value="获取数据库表字段信息", notes="获取数据库表字段信息")
    @ApiResponses({ @ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tableName", value = "数据库表",  dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "pageIndex", value = "第几页", required = true,dataType = "int",paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示大小",required = true,  dataType = "int",paramType = "query")
    })
    @PostMapping("/sql_columns")
    public ReturnPageJson<List<Map<String,Object>>> getSqlColumnsByTable(@RequestBody QueryParams params) {
        ReturnPageJson returnJson = null;
        try{
            returnJson = new ReturnPageJson();
            String tableName = "";
            if(params.getQueryParams() != null){
                for(int i= 0;i<params.getQueryParams().size();i++){
                    SearchParam item = params.getQueryParams().get(i);
                    if(item.getKey().equals("tableName")){
                        tableName = item.getValue();
                        break;
                    }
                    if(item.getKey().equals("TABLE_NAME_")){
                        tableName = item.getValue();
                        break;
                    }
                }
            }

            returnJson.setData(sqlService.getSqlColumnsByTable(tableName,params.getPageIndex(),params.getPageSize()));
            returnJson.setTotalCount(sqlService.getSqlColumnsCountByTable(tableName));
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


    @ApiOperation(value="根据接口配置获取数据", notes="根据接口配置获取数据")
    @ApiResponses({ @ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "apiId", value = "注册接口主键",  dataType = "string",paramType = "path"),
            @ApiImplicitParam(name = "params", value = "参数",  dataType = "HashMap")
    })
    @PostMapping("/api/{apiId}/data")
    public ReturnJson<List<Map<String, Object>>> executeApiData(@PathVariable String apiId , @RequestBody HashMap<String,Object> params) {
        ReturnJson returnJson = null;
        try{
            WfProcess process = new WfProcess();
            if(params.containsKey("formKey")){
                process.setFormKey(String.valueOf(params.get("formKey")));
            }

            List<Map<String, Object>> dataList =  formService.executeApi(process,apiId, null, params);
            returnJson = ReturnJson.ok(dataList);
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


    @ApiOperation(value="根据api注册接口执行", notes="根据api注册接口执行")
    @ApiResponses({ @ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "params", value = "参数",  dataType = "ApiExecuteParams")
    })
    @PostMapping("/api/execute")
    public ReturnJson executeApiData(@RequestBody ApiExecuteParams params) {
        ReturnJson returnJson = null;
        try{
            WfProcess process = new WfProcess();
            process.setFormKey(params.getFormKey());
//            System.out.println("------------------"+ CommonUtil.objToJson(params));
            returnJson =  formService.executeApiData(process,params.getApiId(), params.getApiKey(), params.getParams());
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

    @ApiOperation(value="通过parentId获取报表模板", notes="通过parentId获取报表模板")
    @ApiResponses({ @ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
    @ApiImplicitParam(name = "formPrint", value = "查询实体", required = true, dataType = "FormPrint")
    @RequestMapping(value="/report", method= RequestMethod.POST)
    public ReturnJson<List<FormPrint>> getReport(@RequestBody FormPrint formPrint) {
        ReturnJson returnJson = null;
        try{
            returnJson = ReturnJson.ok(formService.getReport(formPrint));
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

    @ApiOperation(value="通过formInfoId获取导入模板", notes="通过formInfoId获取导入模板")
    @ApiResponses({ @ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
    @ApiImplicitParam(name = "表单id", value = "formInfoId", required = true, dataType = "String",paramType = "path")
    @RequestMapping(value="/{formInfoId}/excel", method= RequestMethod.GET)
    public ReturnJson<List<FormExcel>> getReport(@PathVariable String formInfoId) {
        ReturnJson returnJson = null;
        try{
            returnJson = ReturnJson.ok(formService.getExcel(formInfoId));
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

    @ApiOperation(value="获取用户列表", notes="获取用户列表")
    @ApiResponses({ @ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
    @ApiImplicitParam(name = "params", value = "查询实体", required = true, dataType = "ParamInfo")
    @RequestMapping(value="/orgs", method= RequestMethod.POST)
    public ReturnJson<Map<String, Object>> getUserList(@RequestBody ParamInfo params) {
        ReturnJson returnJson = null;
        try{
            returnJson = ReturnJson.ok(formService.getUserList(params));
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


	@ApiOperation(value="拷贝树表数据", notes="拷贝树表数据")
	@ApiResponses({ @ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
	@ApiImplicitParams(
			@ApiImplicitParam(name = "params", value = "参数",  dataType = "CopyParams")
	)
	@PostMapping("/copy/data")
	public ReturnJson<Void> copyData(@RequestBody CopyParams params) {
		ReturnJson returnJson = null;
		try{
			treeService.copy(params);
			returnJson = ReturnJson.ok();
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
*/
	/*@ApiOperation(value="拖拉拽修改树", notes="拖拉拽修改树")
    @ApiResponses({ @ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
    @ApiImplicitParam(name = "params", value = "查询实体", required = true, dataType = "ParamInfo")
    @RequestMapping(value="/edit_tree", method= RequestMethod.POST)
    public ReturnJson<ParamInfo> editTree(@RequestBody ParamInfo params) {
        ReturnJson returnJson = null;
        try{
            formService.editTree(params);
            returnJson = ReturnJson.ok(params);
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

    @ApiOperation(value="获取address树", notes="获取address树")
    @ApiResponses({ @ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
    @ApiImplicitParam(name = "params", value = "查询实体", required = true, dataType = "ParamInfo")
    @RequestMapping(value="/address_tree", method= RequestMethod.POST)
    public ReturnJson<List<Map<String, Object>>> getAddressTree(@RequestBody ParamInfo params) {
        ReturnJson returnJson = null;
        try{
            SessionUtil.getCurrentLog().setKey(params.getFormKey());
            List<Map<String, Object>> dataList =  formService.findAddressTree(params);
            returnJson = ReturnJson.ok(dataList);
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

    @ApiOperation(value="获取权限数据", notes="通过formKey获取权限数据")
    @ApiResponses({ @ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
    @RequestMapping(value="/{formKey}/auths", method= RequestMethod.GET)
    public ReturnJson<FormBasic> getAuthsByFormKey(@PathVariable String formKey) {
        ReturnJson returnJson = null;
        try{
            FormBasic formBasic = formService.getAuthsByFormKey(formKey);
            returnJson = ReturnJson.ok(formBasic);
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

    @ApiOperation(value="获取流程签名二进制文件", notes="获取流程图标")
    @ApiResponses({ @ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
    @RequestMapping(value="/flow/imgs", method= RequestMethod.POST)
    public ReturnJson<Map<String, String>> getFlowImgs(@RequestBody ParamInfo ParamInfo) {
        ReturnJson returnJson = null;
        try{
            List<String> parentIds = ParamInfo.getParentIds();
            Map<String, String> str = formService.getFlowImgs(parentIds);
            returnJson = ReturnJson.ok(str);
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

    @ApiOperation(value="获取数据库缓存配置", notes="获取数据库缓存配置")
    @ApiResponses({ @ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
    @RequestMapping(value="/redis/config", method= RequestMethod.GET)
    public ReturnJson<String> getRedisConig() {
        ReturnJson returnJson = null;
        try{
            String config = formService.getRedisConig();
            returnJson = ReturnJson.ok(config);
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

    @ApiOperation(value="获取表单对应的移动端地址", notes="移动端待办，待阅等打开根据formKey获取表单对应的移动端地址")
    @ApiImplicitParam(name = "formKey", value = "formKey",  dataType = "String",paramType = "path")
    @RequestMapping(value="/path", method= RequestMethod.GET)
    public ReturnJson<List<OrgMenus>> getFormPath(String formKey) {
        ReturnJson returnJson = null;
        try{
            List<OrgMenus> data = formService.getFormPath(formKey, null);
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
    }*/


    @ApiOperation(
            value = "查询菜单树",
            notes = "查询菜单树"
    )
    @ApiImplicitParams({@ApiImplicitParam(
            name = "parentId",
            value = "上级菜单主键",
            required = true,
            dataType = "String",
            paramType = "query"
    ), @ApiImplicitParam(
            name = "isFilter",
            value = "是否过滤角色，默认false",
            required = true,
            dataType = "boolean",
            paramType = "query"
    ), @ApiImplicitParam(
            name = "isDeep",
            value = "是否递归获取菜单，默认false",
            dataType = "boolean",
            paramType = "query"
    ), @ApiImplicitParam(
            name = "isContainAuth",
            value = "是否包含权限，默认false",
            dataType = "boolean",
            paramType = "query"
    )})
    @GetMapping({"/menus"})
    public Object getAllMenus(String parentId) {
        ReturnJson returnJson = null;
        try {
            List<Map<String, Object>> data = formService.getMenus(parentId);
            returnJson = new ReturnJson(data);
            return returnJson;
        } catch (EngineException var11) {
            var11.printStackTrace();
            returnJson = ReturnJson.error(var11.errcode, var11.getMessage());
            return returnJson;
        } catch (Exception var12) {
            var12.printStackTrace();
            returnJson = ReturnJson.error(EngineException.ERRCODE_EXCEPTION, var12.getMessage());
            return returnJson;
        } finally {
            ;
        }
    }

    @ApiOperation(value="单号生成接口", notes="单号生成接口")
    @ApiResponses({ @ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
    @RequestMapping(value="/simple/sn", method= RequestMethod.POST)
    public ReturnJson<Map<String, String>> createCode(@RequestBody Map<String, Object> params) {
        ReturnJson returnJson = null;
        try{
            formService.createCode(params);
            returnJson = ReturnJson.ok(params);
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

    /*@ApiOperation(value="企业头像接口", notes="企业头像接口")
    @ApiResponses({ @ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
    @RequestMapping(value="/logo/img", method= RequestMethod.POST)
    public ReturnJson<String> getAvatar(@RequestBody ParamInfo ParamInfo) {
        ReturnJson returnJson = null;
        try{
            String id = ParamInfo.getId();
            String str = formService.getAvatar(id);
            returnJson = ReturnJson.ok(str);
        }catch (EngineException e){
            e.printStackTrace();
            returnJson = ReturnJson.error(e.errcode,e.getMessage());
        }catch (Exception e){
            e.printStackTrace();
            returnJson = ReturnJson.error(EngineException.ERRCODE_EXCEPTION,e.getMessage());
        }finally {
            return returnJson;
        }
    }*/

    @ApiOperation(value="同步材料预算数据", notes="同步材料预算数据")
    @ApiResponses({ @ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
    @ApiImplicitParams(
            @ApiImplicitParam(name = "params", value = "参数",  dataType = "Map")
    )
    @PostMapping("/sync/imsbudget")
    public ReturnJson<Map<String, Object>> syncIms(@RequestBody Map<String, Object> params) {
        ReturnJson returnJson = null;
        try{
            returnJson = ReturnJson.ok(formService.syncIms(params));
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

    /*@ApiOperation(value="顶部工具栏选人或者选角批量授权", notes="顶部工具栏选人或者选角批量授权")
    @ApiResponses({ @ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
    @ApiImplicitParams(
            @ApiImplicitParam(name = "params", value = "参数",  dataType = "SelectBatchAuthParams")
    )
    @PostMapping("/select/batch/auth")
    public ReturnJson<Void> batchAuth(@RequestBody SelectBatchAuthParams params) {
        ReturnJson returnJson = null;
        try{

            formService.saveSelectAuth(params);

            returnJson = ReturnJson.ok();
        }catch (EngineException e){
            e.printStackTrace();
            returnJson = new ReturnJson(e.errcode,e.getMessage());
        }catch (Exception e){
            e.printStackTrace();
            returnJson = new ReturnJson(EngineException.ERRCODE_EXCEPTION,e.getMessage());
        }finally {
            return returnJson;
        }
    }*/
}
