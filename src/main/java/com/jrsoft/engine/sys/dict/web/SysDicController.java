package com.jrsoft.engine.sys.dict.web;

import com.jrsoft.engine.base.domain.sys.DicTreeNode;
import com.jrsoft.engine.base.model.ReturnJson;
import com.jrsoft.engine.base.web.BaseController;
import com.jrsoft.engine.common.utils.CommonUtil;
import com.jrsoft.engine.exception.EngineException;
import com.jrsoft.engine.exception.EngineIllegalArgumentException;
import com.jrsoft.engine.service.PfDicTreeService;
import com.jrsoft.engine.sys.dict.domin.DicEntity;
import com.jrsoft.engine.sys.dict.service.DicEntityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 系统数据字典
 *
 * @author Blueeyedboy
 * @create 2018-12-01 11:03 AM
 **/
@Api(value = "SysDicController", description = "系统数据字典接口")
@RestController("SysDicController")
@RequestMapping(value = "/api_v1/sys/tree")
public class SysDicController extends BaseController {

	/*@Autowired
	private PfDicTreeService sysTreeService;*/
    @Autowired
	private DicEntityService dicEntityService;

	/*@ApiOperation(value="查询系统树", notes="查询系统常量列表")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "parentId", value = "上级节点", required = true, dataType = "String"),
			@ApiImplicitParam(name = "type", value = "类型，1表示系统数据字典树，2表示业务数据字典树,不传参表示查询所有",  dataType = "int"),
			@ApiImplicitParam(name = "enable", value = "1表示启用",  dataType = "int"),
			@ApiImplicitParam(name = "ifContainChild", value = "是否递归包含子节点",  dataType = "boolean")
	})
	@GetMapping("")
	public ReturnJson<List<DicTreeNode>> findAll(String parentId, Integer type, Integer enable, Boolean ifContainChild) {

		ReturnJson returnJson = null;
		try{
			List<DicTreeNode> list = null;
			if(ifContainChild != null && ifContainChild){
				list = sysTreeService.findTreeDeep(parentId,type,enable);
			}else{
				list = sysTreeService.findTree(parentId,type,enable);
			}
			returnJson = new ReturnJson();
			returnJson.setData(list);
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

	/*@ApiOperation(value="查询单个树", notes="查询单个树")
	@ApiImplicitParam(name = "id", value = "常量主键", required = true, dataType = "String",paramType = "path")
	@GetMapping("/{id}")
	public ReturnJson<PfSysTreeEntity> get(@PathVariable String id) {
		ReturnJson returnJson = null;
		try{
			PfSysTreeEntity entity = sysTreeService.get(id);
			if(entity != null){
				if(CommonUtil.ifNotEmpty(entity.getParentId())){
					PfSysTreeEntity parent = sysTreeService.get(entity.getParentId());
					if(parent != null){
						entity.setParentText(parent.getText());
					}
				}
				returnJson = ReturnJson.ok(entity);
			}else{
				throw new EngineIllegalArgumentException("can not find data");
			}

		}catch (EngineException e){
			e.printStackTrace();
			returnJson = ReturnJson.error(e.getMessage());
		}catch (Exception e){
			e.printStackTrace();
			returnJson = ReturnJson.error(EngineException.ERRCODE_EXCEPTION,e.getMessage());
		}finally {
			return returnJson;
		}
	}*/

	@ApiOperation(value="创建树节点", notes="创建树节点")
	@ApiImplicitParam(name = "entity", value = "树节点详细实体", required = true, dataType = "PfSysTreeEntity")
	@PostMapping("")
	public ReturnJson<DicEntity> post(@RequestBody DicEntity entity) {
		ReturnJson returnJson = null;
		try{
            DicEntity entity1 = dicEntityService.getByDicId(entity.getDicId());
			if(entity1 == null){
                dicEntityService.save(entity);
			}else{
				throw new EngineIllegalArgumentException("duplicate code");
			}
			returnJson = ReturnJson.ok(entity);
		}catch (EngineException e){
			e.printStackTrace();
			returnJson = ReturnJson.error(e.getMessage());
		}catch (Exception e){
			e.printStackTrace();
			returnJson = ReturnJson.error(EngineException.ERRCODE_EXCEPTION,e.getMessage());
		}finally {
			return returnJson;
		}
	}
    /**
	@ApiOperation(value="修改树节点", notes="修改树节点")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "树节点主键", required = true, dataType = "String",paramType = "path"),
			@ApiImplicitParam(name = "entity", value = "树节点详细实体", required = true, dataType = "PfSysTreeEntity")
	})
	@PutMapping("/{id}")
	public ReturnJson<PfSysTreeEntity> put(@PathVariable String id, @RequestBody PfSysTreeEntity entity) {
		ReturnJson returnJson = null;
		try{
			entity.setId(id);
			PfSysTreeEntity entity1 = sysTreeService.findByCode(entity.getCode());
			if(entity1 != null){
				if(entity1.getId().equals(entity.getId())){
					sysTreeService.update(entity);
				}else{
					throw new EngineIllegalArgumentException("duplicate key");
				}
			}else{
				sysTreeService.update(entity);

			}
			returnJson = ReturnJson.ok(entity);
		}catch (EngineException e){
			e.printStackTrace();
			returnJson = ReturnJson.error(e.getMessage());
		}catch (Exception e){
			e.printStackTrace();
			returnJson = ReturnJson.error(EngineException.ERRCODE_EXCEPTION,e.getMessage());
		}finally {
			return returnJson;
		}
	}

	@ApiOperation(value="删除常量", notes="删除常量")
	@ApiImplicitParam(name = "id", value = "常量主键", required = true, dataType = "String",paramType = "path")
	@DeleteMapping("/{id}")
	public ReturnJson delete(@PathVariable String id) {
		ReturnJson returnJson = null;
		try{
			sysTreeService.deleteByPrimaryKey(id);
			returnJson = ReturnJson.ok(id);
		}catch (EngineException e){
			e.printStackTrace();
			returnJson = ReturnJson.error(e.getMessage());
		}catch (Exception e){
			e.printStackTrace();
			returnJson = ReturnJson.error(EngineException.ERRCODE_EXCEPTION,e.getMessage());
		}finally {
			return returnJson;
		}
	}*/


}
