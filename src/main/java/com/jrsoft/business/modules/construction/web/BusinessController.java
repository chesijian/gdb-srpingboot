package com.jrsoft.business.modules.construction.web;


import com.jrsoft.business.modules.construction.domain.FileCatalog;
import com.jrsoft.business.modules.construction.service.BusinessService;
import com.jrsoft.business.modules.construction.service.MeasuredService;
import com.jrsoft.engine.base.model.ReturnJson;
import com.jrsoft.engine.base.model.ReturnPageJson;
import com.jrsoft.engine.common.utils.CommonUtil;
import com.jrsoft.engine.common.utils.SessionUtil;

import com.jrsoft.engine.exception.EngineException;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 *业务接口
 * @author chesj
 *
 */
@Api(value = "RealNameSystemController", description = "业务接口")
@RestController
@RequestMapping(value = "/api_v1/construction/business")
public class BusinessController {


	@Autowired
	private MeasuredService measuredService;

	@Autowired
	private BusinessService businessService;


	/**
	 * 获取企业信息
	 * @param request
	 * @param companyUid
	 * @return
	 */
	@ApiOperation(value="获取企业信息", notes="获取企业信息")
	@ApiResponses({ @ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
	@ApiImplicitParam(name = "projUid", value = "项目Id", required = true, dataType = "String",paramType = "query")
	@RequestMapping(value={"corpInfo"}, method= RequestMethod.GET)
	@ResponseBody
	public ReturnPageJson<List<Map<String, Object>>> getCorpInfo(HttpServletRequest request, String companyUid){
		ReturnPageJson returnJson = null;
		try {
			if(!CommonUtil.ifNotEmpty(companyUid)){
				companyUid = SessionUtil.getCompanyUid();
			}
			Map<String, Object> data = measuredService.getCorpInfo(companyUid);
			returnJson = new ReturnPageJson();
			returnJson.setData(data);
		} catch (EngineException e){
			e.printStackTrace();
			returnJson = new ReturnPageJson(EngineException.ERRCODE_EXCEPTION,e.getMessage());
		}catch (Exception e){
			e.printStackTrace();
			returnJson = new ReturnPageJson(EngineException.ERRCODE_EXCEPTION,e.getMessage());
		}finally {
			return returnJson;
		}
	}
	/**
	 * 获取采购清单数据
	 * @param request
	 * @param projUid
	 * @return
	 */
	@ApiOperation(value="获取采购清单数据", notes="获取采购清单数据")
	@ApiResponses({ @ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
	@ApiImplicitParam(name = "projUid", value = "项目Id", required = true, dataType = "String",paramType = "query")
	@RequestMapping(value={"purchaseOrders"}, method= RequestMethod.GET)
	@ResponseBody
	public ReturnPageJson<List<Map<String, Object>>> statistictDetails(HttpServletRequest request, String projUid){
		ReturnPageJson returnJson = null;
		try {
			List<Map<String, Object>> data = businessService.getPurchaseList(projUid);
			returnJson = new ReturnPageJson();
			returnJson.setData(data);
		} catch (EngineException e){
			e.printStackTrace();
			returnJson = new ReturnPageJson(EngineException.ERRCODE_EXCEPTION,e.getMessage());
		}catch (Exception e){
			e.printStackTrace();
			returnJson = new ReturnPageJson(EngineException.ERRCODE_EXCEPTION,e.getMessage());
		}finally {
			return returnJson;
		}
	}

	/**
	 * 获取采购清单数据
	 * @param request
	 * @param orderCode
	 * @return
	 */
	@ApiOperation(value="获取采购清单明细数据", notes="获取采购清单明细数据")
	@ApiResponses({ @ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
	@ApiImplicitParam(name = "orderCode", value = "订单编号", required = true, dataType = "String",paramType = "query")
	@RequestMapping(value={"purchaseDet"}, method= RequestMethod.GET)
	@ResponseBody
	public ReturnPageJson<List<Map<String, Object>>> getPurchaseDet(HttpServletRequest request, String orderCode,Integer pageIndex, Integer pageSize,String search){
		ReturnPageJson returnJson = null;
		try {
			int start = (pageIndex-1)*pageSize;
			List<Map<String, Object>> data = businessService.getPurchaseDet(orderCode,search,start,pageSize);
			returnJson = new ReturnPageJson();
			returnJson.setData(data);
		} catch (EngineException e){
			e.printStackTrace();
			returnJson = new ReturnPageJson(EngineException.ERRCODE_EXCEPTION,e.getMessage());
		}catch (Exception e){
			e.printStackTrace();
			returnJson = new ReturnPageJson(EngineException.ERRCODE_EXCEPTION,e.getMessage());
		}finally {
			return returnJson;
		}
	}

	/**
	 * 导入文档目录模板
	 * @author 车斯剑
	 * @date 2019年7月11日下午5:42:59
	 * @param list
	 * @return
	 */
	@ApiOperation(value="导入文档目录模板", notes="导入文档目录模板")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "checkItem", value = "检查项实体", required = true, dataType = "CheckItem")
	})
	@RequestMapping(value="/CatalogTemplate/{parentId}", method= RequestMethod.POST)
	public ReturnJson insertCheckItem(@RequestBody List<FileCatalog> list, @PathVariable String parentId){
		ReturnJson returnJson = null;
		try{
			businessService.importCatalogTemplate(list, parentId);
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
