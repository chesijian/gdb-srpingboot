package com.jrsoft.business.modules.fileManage.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jrsoft.engine.exception.EngineException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.jrsoft.business.modules.fileManage.service.FileManageServiceI;
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

@Api(value = "fileManage", description = "文档管理")
@RestController
@RequestMapping(value = "/api_v1/business/fileManage")
public class FileManageController {
	
	@Autowired 
	private FileManageServiceI fileManageServiceI;

	
	
	/**
	 * 根据项目id查询文件文件夹
	 * @param parentUid
	 * @param userUid
	 * @param companyUid
	 * @param search
	 * @return
	 */
	@ApiOperation(value="根据项目id查询文档", notes="根据项目id查询文档")
	@ApiResponses({ @ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
	@ApiImplicitParams({
		@ApiImplicitParam(name = "projUid", value = "项目id", required = true, dataType = "String",paramType = "query"),
		@ApiImplicitParam(name = "parentUid", value = "上级id", required = true, dataType = "String",paramType = "query"),
		@ApiImplicitParam(name = "search", value = "搜索框", dataType = "String",paramType = "query"),
	})
	@RequestMapping(value = "searchByParentUid", method= RequestMethod.GET)
	@ResponseBody
	public ReturnJson<Map<String, Object>> searchByProjUid(String projUid, String parentUid, String userUid, String companyUid, String search){
		ReturnJson returnJson = null;
		try{
			Map<String, Object> result = new HashMap<String, Object>();
			if(!CommonUtil.ifNotEmpty(companyUid)){
				companyUid = SessionUtil.getCompanyUid();
			}
			if(!CommonUtil.ifNotEmpty(userUid)){
				userUid = SessionUtil.getUserUid();
			}
			boolean isCompanyAdmin = RoleUtil.isCompanyAdmin();
			List<Map<String, Object>> data = fileManageServiceI.searchBypRrojectId(projUid, parentUid, userUid, companyUid, isCompanyAdmin, search);
			
			List<Map<String, Object>> data2 = fileManageServiceI.searchFileBypRrojectId(projUid, parentUid, userUid, companyUid, isCompanyAdmin, search);
			result.put("folder", data);
			result.put("file", data2);
			returnJson = ReturnJson.ok(result);
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
	 * Xu
	 * 移动文件夹
	 * @return
	 */
	@ApiOperation(value="移动文件夹", notes="移动文件夹")
	@ApiResponses({ @ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
	@ApiImplicitParams({
		@ApiImplicitParam(name = "checkedId", value = "选中的文件id", required = true, dataType = "String",paramType = "query"),
		@ApiImplicitParam(name = "moveId", value = "被移动的id", required = true, dataType = "String",paramType = "query"),
	})
	@RequestMapping(value = "moveFolder", method= RequestMethod.GET)
	@ResponseBody
	public ReturnJson<Map<String, Object>> moveFolder(String checkedId, String moveId){
		ReturnJson returnJson = null;
		try{
			Map<String, Object> params = new HashMap<String, Object>();
			Boolean flag = fileManageServiceI.moveFolder(checkedId, moveId);
			if(flag) {
				params.put("success", "success");
			}else {
				params.put("error", "error");
			}
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
	
	
	
	/**
	 * Xu
	 * 删除文件夹和下级
	 * @return
	 */
	@ApiOperation(value="删除文件夹和下级", notes="删除文件夹和下级")
	@ApiResponses({ @ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id", value = "主键id", required = true, dataType = "String",paramType = "query")
	})
	@RequestMapping(value={"/{id}"}, method= RequestMethod.DELETE)
	@ResponseBody
	public ReturnJson<Map<String, Object>> deleteFile(@PathVariable String id){
		ReturnJson returnJson = null;
		try{
			/*boolean isCompanyAdmin = RoleUtil.isCompanyAdmin();
			List<Map<String, Object>> result = fileManageServiceI.subordinateFiles(arrId, userUid, isCompanyAdmin);
			if(result!=null&&result.size()>0){
				params.put("status", "hadChildren");
			}else{
				fileManageServiceI.deleteTop(arrId);
				params.put("status", "success");
			}*/
			
			fileManageServiceI.deleteTop(id);
			returnJson = ReturnJson.ok(id);
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
	 * Xu
	 * 重命名文件夹
	 * @param name
	 * @param id
	 */
	@ApiOperation(value="重命名文件夹", notes="重命名文件夹")
	@ApiResponses({ @ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id", value = "id", required = true, dataType = "String",paramType = "query"),
		@ApiImplicitParam(name = "name", value = "新名称", required = true, dataType = "String",paramType = "query"),
	})
	@RequestMapping(value = "updateFileName", method= RequestMethod.GET)
	@ResponseBody
	public ReturnJson<Map<String, Object>> updateFileName(String name, String id) {
		
		ReturnJson returnJson = null;
		try{
			Map<String, Object> data = null;
			if(CommonUtil.ifNotEmpty(id)) {
				 data = fileManageServiceI.updateFileName(name, id);
			}
			
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
	 * 检验当前目录是否有重复的文件夹名称
	 * @param parentUid 上级id
	 * @return
	 */
	@ApiOperation(value="检验当前目录是否有重复的文件夹名称", notes="检验当前目录是否有重复的文件夹名称")
	@ApiResponses({ @ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
	@ApiImplicitParams({
		@ApiImplicitParam(name = "parentUid", value = "上级id", required = true, dataType = "String",paramType = "query"),
		@ApiImplicitParam(name = "projUid", value = "项目id", required = true, dataType = "String",paramType = "query"),
		@ApiImplicitParam(name = "fileName", value = "文件名称", dataType = "String",paramType = "query"),
	})
	@RequestMapping(value = "/checkFileName", method= RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> checkFileName(String parentUid, String companyUid, String fileName, String projUid){
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			if(!CommonUtil.ifNotEmpty(companyUid)) {
				companyUid = SessionUtil.getCompanyUid();
			}
			int count = fileManageServiceI.checkFileName(parentUid, companyUid, fileName, projUid);
			
			result.put("count", count);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	
	/**
	 * 检验移动功能不能移动到自己的下级
	 * @param parentUid 上级id
	 * @param id 需要移动的id
	 * @return
	 */
	@ApiOperation(value="检验当前目录是否有重复的文件夹名称", notes="检验当前目录是否有重复的文件夹名称")
	@ApiResponses({ @ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
	@ApiImplicitParams({
		@ApiImplicitParam(name = "parentUid", value = "上级id", required = true, dataType = "String",paramType = "query"),
		@ApiImplicitParam(name = "id", value = "需要移动的id", dataType = "String",paramType = "query")
	})
	@RequestMapping(value = "/checkMoveNoOneself", method= RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> checkMoveNoOneself(String parentUid, String id){
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			List<Map<String, Object>> data = fileManageServiceI.checkMoveNoOneself(parentUid, id);
			
			result.put("data", data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	/**
	 * 编辑文件夹(目录)
	 * @param id 主键id
	 * @return
	 */
	@ApiOperation(value="编辑文件夹(目录)", notes="编辑文件夹(目录)")
	@ApiResponses({ @ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id", value = "目录id", required = true, dataType = "String",paramType = "query")
	})
	@RequestMapping(value = "/updateFolder", method= RequestMethod.GET)
	@ResponseBody
	public ReturnJson<Map<String, Object>> updateFolder(String id){
		ReturnJson returnJson = null;
		try{
			String dbType = CommonUtil.getDatabaseType();	//获取数据库类型
			Map<String, Object> data = fileManageServiceI.updateFolder(id, dbType);
			returnJson = new ReturnPageJson();
			returnJson.setData(data);
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
	 * 编辑文件
	 * @param id 主键id
	 * @return
	 */
	@ApiOperation(value="编辑文件", notes="编辑文件")
	@ApiResponses({ @ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id", value = "文件id", required = true, dataType = "String",paramType = "query")
	})
	@RequestMapping(value = "/updateFile", method= RequestMethod.GET)
	@ResponseBody
	public ReturnJson<Map<String, Object>> updateFile(String id){
		ReturnJson returnJson = null;
		try{
			Map<String, Object> data = fileManageServiceI.updateFile(id);
			returnJson = new ReturnPageJson();
			returnJson.setData(data);
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
	 * 移动文件
	 * @param parentUid 上级id
	 * @param id 需要移动的id
	 * @return
	 */
	@ApiOperation(value="移动文件", notes="移动文件")
	@ApiResponses({ @ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
	@ApiImplicitParams({
		@ApiImplicitParam(name = "checkedId", value = "选中的文件id", required = true, dataType = "String",paramType = "query"),
		@ApiImplicitParam(name = "moveId", value = "被移动的id", required = true, dataType = "String",paramType = "query"),
	})
	@RequestMapping(value = "/moveFile", method= RequestMethod.GET)
	@ResponseBody
	public ReturnJson<Map<String, Object>> moveFile(String checkedId, String moveId){
		ReturnJson returnJson = null;
		try{
			fileManageServiceI.moveFile(checkedId, moveId);
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
	 * 删除文件
	 * @param id 主键id
	 * @return
	 */
	@ApiOperation(value="删除文件", notes="删除文件")
	@ApiResponses({ @ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
	@ApiImplicitParams({
		@ApiImplicitParam(name = "ids", value = "主键id", required = true, dataType = "String",paramType = "query"),
	})
	@RequestMapping(value = "/file/ids", method= RequestMethod.DELETE)
	@ResponseBody
	public ReturnJson<Map<String, Object>> removeFile(String ids){
		ReturnJson returnJson = null;
		try{
			String[] idss = ids.split(",");
			fileManageServiceI.removeFile(idss);
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
	 * 删除目录
	 * @param id 主键id
	 * @return
	 */
	@ApiOperation(value="删除目录", notes="删除目录")
	@ApiResponses({ @ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
	@ApiImplicitParams({
		@ApiImplicitParam(name = "ids", value = "主键id", required = true, dataType = "String",paramType = "query"),
	})
	@RequestMapping(value = "/catalogue/{ids}", method= RequestMethod.DELETE)
	@ResponseBody
	public ReturnJson<Map<String, Object>> removeCatalogue(@PathVariable String ids){
		ReturnJson returnJson = null;
		try{
			String[] idss = ids.split(",");
			fileManageServiceI.removeCatalogue(idss);
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
	 * 编辑目录
	 * @param id 
	 * @return
	 */
	@ApiOperation(value="编辑目录", notes="编辑目录")
	@ApiResponses({ @ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id", value = "id", required = true, dataType = "String",paramType = "query"),
	})
	@RequestMapping(value = "/catalogue/{id}", method= RequestMethod.GET)
	@ResponseBody
	public ReturnJson<Map<String, Object>> catalogue(@PathVariable String id){
		ReturnJson returnJson = null;
		try{
			Map<String, Object> data = fileManageServiceI.searchCatalogueById(id);
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
	
	
	
	@ApiOperation(value="PC遍历递归文件夹", notes="PC遍历递归文件夹")
	@ApiResponses({ @ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
	@ApiImplicitParams({
		@ApiImplicitParam(name = "projUid", value = "项目id", required = true, dataType = "String",paramType = "query"),
		@ApiImplicitParam(name = "parentUid", value = "上级id", required = true, dataType = "String",paramType = "query"),
	})
	@RequestMapping(value = "searchFolderRecursion", method= RequestMethod.GET)
	@ResponseBody
	public ReturnJson<List<Map<String, Object>>> searchFolderRecursion(HttpServletRequest request,String projUid, String parentUid, String userUid, String companyUid){
		ReturnJson returnJson = null;
		try{
			if(!CommonUtil.ifNotEmpty(companyUid)){
				companyUid = SessionUtil.getCompanyUid();
			}
			if(!CommonUtil.ifNotEmpty(userUid)){
				userUid = SessionUtil.getUserUid();
			}
			boolean isCompanyAdmin = RoleUtil.isCompanyAdmin();
			List<Map<String, Object>> data = fileManageServiceI.searchFolderRecursion(projUid, parentUid, userUid, companyUid, isCompanyAdmin);
			returnJson = new ReturnPageJson();
			returnJson.setData(data);
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
	
	
	
	@ApiOperation(value="PC根据文件夹id查询文件", notes="PC根据文件夹id查询文件")
	@ApiResponses({ @ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id", value = "文件夹id", required = true, dataType = "String",paramType = "query"),
		@ApiImplicitParam(name = "projUid", value = "项目id", required = true, dataType = "String",paramType = "query"),
		@ApiImplicitParam(name = "pageSize", value = "每页显示大小",required = true,  dataType = "int",paramType = "query"),
		@ApiImplicitParam(name = "pageIndex", value = "",required = true,  dataType = "int",paramType = "query"),
		@ApiImplicitParam(name = "search", value = "搜索框", dataType = "String",paramType = "query"),
	})
	@RequestMapping(value = "searchAccessoryByFolderId", method= RequestMethod.GET)
	@ResponseBody
	public ReturnJson<List<Map<String, Object>>> searchAccessoryByFolderId(String id, String projUid, int pageSize, int pageIndex, String search, String parentUid, String userUid, String companyUid){
		ReturnPageJson returnJson = null;
		try{

			if(!CommonUtil.ifNotEmpty(companyUid)){
				companyUid = SessionUtil.getCompanyUid();
			}
			if(!CommonUtil.ifNotEmpty(userUid)){
				userUid = SessionUtil.getUserUid();
			}
			boolean isCompanyAdmin = RoleUtil.isCompanyAdmin();

        	int end = pageIndex * pageSize;
			if(!CommonUtil.ifNotEmpty(search)){
				search = null;
			}
			returnJson = fileManageServiceI.searchAccessoryByFolderId(projUid , id, pageIndex, pageSize, search, isCompanyAdmin, userUid, companyUid);

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
	 * 导入文档模板
	 * @param parentUid 上级id
	 * @param documentIds 文档ids
	 * @return
	 */
	@ApiOperation(value="导入文档模板", notes="导入文档模板")
	@ApiResponses({ @ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
	@ApiImplicitParams({
		@ApiImplicitParam(name = "projUid", value = "项目id", required = true, dataType = "String",paramType = "query"),
		@ApiImplicitParam(name = "parentUid", value = "上级id", required = true, dataType = "String",paramType = "query"),
		@ApiImplicitParam(name = "catalogueId", value = "文档id", required = true, dataType = "String",paramType = "query"),
	})
	@RequestMapping(value = "/importDocument", method= RequestMethod.GET)
	@ResponseBody
	public ReturnJson<Map<String, Object>> importDocument(String projUid, String parentUid, String catalogueId){
		ReturnJson returnJson = null;
		try{
			if(catalogueId != null && catalogueId != "" && projUid != null && projUid != "") {
				fileManageServiceI.searchByCatalogue(catalogueId, projUid, parentUid);
			}
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
	 * 查询目录模板
	 * @param parentUid 上级id
	 * @return
	 */
	@ApiOperation(value="查询目录模板", notes="查询目录模板")
	@ApiResponses({ @ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
	@ApiImplicitParams({
		@ApiImplicitParam(name = "parentUid", value = "上级id", required = true, dataType = "String",paramType = "query") 
	})
	@RequestMapping(value = "/searchDirectoryList", method= RequestMethod.GET)
	@ResponseBody
	public ReturnJson<Map<String, Object>> searchDirectoryList(String parentUid,String companyUid){
		ReturnJson returnJson = null;
		try{
			if(!CommonUtil.ifNotEmpty(companyUid)) {
				companyUid = SessionUtil.getCompanyUid();
			}
			List<Map<String, Object>> listMap = fileManageServiceI.searchDirectoryList(parentUid,companyUid);
			returnJson = ReturnJson.ok(listMap);
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


	@ApiOperation(value="查询目录模板树", notes="查询目录模板树")
	@ApiResponses({ @ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
	@ApiImplicitParams({
			@ApiImplicitParam(name = "parentUid", value = "上级id", required = true, dataType = "String",paramType = "query")
	})
	@RequestMapping(value = "/documentDirectoryTemplate", method= RequestMethod.GET)
	@ResponseBody
	public ReturnJson<Map<String, Object>> getDocumentDirectoryTemplate(){
		ReturnJson returnJson = null;
		try{

			List<Map<String, Object>> listMap = fileManageServiceI.getDocumentDirectoryTemplate("root");
			returnJson = ReturnJson.ok(listMap);
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
