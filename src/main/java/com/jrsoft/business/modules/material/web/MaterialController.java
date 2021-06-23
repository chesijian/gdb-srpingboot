package com.jrsoft.business.modules.material.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.jrsoft.engine.exception.EngineException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.jrsoft.business.modules.material.model.Material;
import com.jrsoft.business.modules.material.service.MaterialServiceI;
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

@Api(value = "material", description = "物料")
@RestController
@RequestMapping(value = "/api_v1/business/material")
public class MaterialController {
	
	@Autowired 
	private MaterialServiceI materialServiceI;
	
	
	
	@ApiOperation(value="物料列表", notes="根据项目id查询物料列表")
	@ApiResponses({ @ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
	@ApiImplicitParams({
		@ApiImplicitParam(name = "projUid", value = "项目id", required = true, dataType = "String",paramType = "query"),
		@ApiImplicitParam(name = "pageSize", value = "每页显示大小",required = true,  dataType = "int",paramType = "query"),
		@ApiImplicitParam(name = "pageIndex", value = "",required = true,  dataType = "int",paramType = "query"),
		@ApiImplicitParam(name = "status", value = "0:待检, 1:已检",required = true,  dataType = "int",paramType = "query"),
		@ApiImplicitParam(name = "search", value = "搜索框", dataType = "String",paramType = "query") 
	})
	@RequestMapping(value = "searchMaterialList", method= RequestMethod.GET)
	@ResponseBody
	public ReturnJson<List<Map<String, Object>>> searchMeetingRoomtList(String projUid, int pageSize, int pageIndex, 
			String search, String userUid, String companyUid, Integer status){
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
			List<Map<String, Object>> data = materialServiceI.searchMaterialList(projUid , start, end, search, isCompanyAdmin, userUid, companyUid, status);
			returnJson = new ReturnPageJson();
			if(data.size() > 0) {
				returnJson.setTotalCount((Long)data.get(0).get("totalCount"));
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
	
	
	@ApiOperation(value="编辑物料", notes="根据id查询物料")
	@ApiResponses({ @ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id", value = "id", dataType = "String",paramType = "query")
	})
	@RequestMapping(value={"/{id}"}, method= RequestMethod.GET)
	public ReturnJson<Map<String, Object>> edit(@PathVariable String id) {
        ReturnJson returnJson = null;
        try {
        	Map<String, Object> data = materialServiceI.searchMaterialById(id);
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
	
	
	@ApiOperation(value="删除物料", notes="根据id删除物料")
	@ApiImplicitParam(name = "id", value = "ID", required = true, dataType = "String",paramType = "path")
	@RequestMapping(value="/{id}", method= RequestMethod.DELETE)
	public ReturnJson delete(@PathVariable String id) {
		ReturnJson returnJson = null;
		try{
			materialServiceI.delete(id);
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
	
	
	@ApiOperation(value="查询物质库", notes="新增时查询物质库")
	@ApiResponses({ @ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
	@ApiImplicitParams({
		@ApiImplicitParam(name = "companyUid", value = "公司id", dataType = "String",paramType = "query")
	})
	@RequestMapping(value={"/materialLibrary"}, method= RequestMethod.GET)
	public ReturnJson<Map<String, Object>> materialLibrary(String companyUid,String search, String projUid) {
		ReturnJson returnJson = null;
		try {
			if(!CommonUtil.ifNotEmpty(companyUid)){
				companyUid = SessionUtil.getCompanyUid();
			}
			List<Map<String, Object>> data = materialServiceI.materialLibrary(companyUid,search,projUid);
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
	
	
	@ApiOperation(value="查询检查项", notes="根据物资id查询检查项")
	@ApiResponses({ @ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id", value = "物资关联检查项的id", dataType = "String",paramType = "query"),
		@ApiImplicitParam(name = "projUid", value = "项目id", dataType = "String",paramType = "query")
	})
	@RequestMapping(value={"/searchInspectByMaterialId"}, method= RequestMethod.GET)
	public ReturnJson<Map<String, Object>> searchInspectByMaterialId(String id, String projUid) {
		ReturnJson returnJson = null;
		try {
			List<Map<String, Object>> data = materialServiceI.searchInspectByMaterialId(id, projUid);
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
	
	
	@ApiOperation(value="批量更新物料检查项状态", notes="批量更新物料检查项状态")
	@ApiResponses({ @ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id", value = "物资关联检查项的id", dataType = "String",paramType = "query"),
		@ApiImplicitParam(name = "ids", value = "检查项ids", dataType = "String",paramType = "query")
	})
	@RequestMapping(value={"/updateInspectByMaterialId"}, method= RequestMethod.GET)
	public ReturnJson<Map<String, Object>> updateInspectByMaterialId(String id, String ids) {
		ReturnJson returnJson = null;
		try {
			materialServiceI.updateInspectByMaterialId(id, ids);
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
	
	
	@ApiOperation(value="编辑物资库", notes="编辑物资库")
	@ApiResponses({ @ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id", value = "id", dataType = "String",paramType = "query")
	})
	@RequestMapping(value={"/editMaterials"}, method= RequestMethod.GET)
	public ReturnJson<Map<String, Object>> editMaterials(String id) {
		ReturnJson returnJson = null;
		try {
			Map<String, Object> data = materialServiceI.editMaterials(id);
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
	
	
	@ApiOperation(value="删除物资库", notes="删除物资库")
	@ApiResponses({ @ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id", value = "id", dataType = "String",paramType = "query")
	})
	@RequestMapping(value={"/deleteMaterials/{id}"}, method= RequestMethod.DELETE)
	public ReturnJson<Map<String, Object>> deleteMaterials(@PathVariable String id) {
		ReturnJson returnJson = null;
		try {
			materialServiceI.deleteMaterials(id);
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
	
	
	@ApiOperation(value="查询物资条形码", notes="根据条形码id查询物资库")
	@ApiResponses({ @ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
	@ApiImplicitParams({
		@ApiImplicitParam(name = "barCode", value = "barCode", dataType = "String",paramType = "query")
	})
	@RequestMapping(value={"/material/{barCode}"}, method= RequestMethod.GET)
	public ReturnJson<Map<String, Object>> searchMaterialBarCode(@PathVariable String barCode) {
		ReturnJson returnJson = null;
		try {
			Map<String, Object> data = materialServiceI.searchMaterialBarCode(barCode);
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
	
	
	@ApiOperation(value="获取过磅信息", notes="获取过磅信息")
	@ApiResponses({ @ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
	@ApiImplicitParam(name = "material", value = "过磅实体", required = true, dataType = "Material")
	@RequestMapping(value={"/searchMaterialWeigh"}, method= RequestMethod.POST)
	public ReturnJson<Material> searchMaterialWeigh(@RequestBody Material material) {
		ReturnJson returnJson = null;
		try {
			if(material != null) {
				//materialServiceI.save(material);	//保存
			}
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
	
	
	@ApiOperation(value="过磅", notes="查询过磅信息列表")
	@ApiResponses({ @ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
	@ApiImplicitParams({
		@ApiImplicitParam(name = "pageSize", value = "每页显示大小",required = true,  dataType = "int",paramType = "query"),
		@ApiImplicitParam(name = "pageIndex", value = "",required = true,  dataType = "int",paramType = "query"),
		@ApiImplicitParam(name = "status", value = "状态: 0:待检; 1:已检", dataType = "int",paramType = "query"),
		@ApiImplicitParam(name = "search", value = "搜索框", dataType = "String",paramType = "query") 
	})
	@RequestMapping(value = "getMaterialWeighList", method= RequestMethod.GET)
	@ResponseBody
	public ReturnJson<List<Map<String, Object>>> getMaterialWeighList(int pageSize, int pageIndex, int status,
			String search, String userUid, String companyUid ){
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
			List<Map<String, Object>> data = materialServiceI.searchMaterialWeigh(start, end, search, userUid, companyUid, status);
			returnJson = new ReturnPageJson();
			if(data.size() > 0) {
				returnJson.setTotalCount((Long)data.get(0).get("totalCount"));
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
}
