package com.jrsoft.engine.web;/**
 * Created by chesijian on 2021/6/19.
 */

import com.jrsoft.engine.base.domain.sys.PfMenu;
import com.jrsoft.engine.base.model.ReturnJson;
import com.jrsoft.engine.common.utils.CommonUtil;
import com.jrsoft.engine.exception.EngineException;
import com.jrsoft.engine.exception.EngineIllegalArgumentException;
import com.jrsoft.engine.service.MenuService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName MenuController
 * @Description TODO
 * @Author chesijian
 * @Date 2021/6/19 22:10
 * @Version 1.0
 */
@RestController("MenuController")
@RequestMapping(value = "/api_v1/org")
public class MenuController {

    @Autowired
    private MenuService menuService;

    /**
     *
     * 创建菜单
     * @author Blueeyedboy
     * @create 2017/7/5 17:56
     **/
    @ApiOperation(value="创建菜单", notes="创建菜单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", required = true, dataType = "String",paramType = "path"),
            @ApiImplicitParam(name = "entity", value = "详细实体", required = true, dataType = "PfMenuPo",paramType = "body")
    })
    @RequestMapping(value="/menus", method= RequestMethod.POST)
    public ReturnJson postMenu(@RequestBody PfMenu entity) {
        ReturnJson returnJson = null;
        try{

            if(!CommonUtil.ifNotEmpty(entity.getTitle())){
                throw new EngineIllegalArgumentException("菜单名称不能为空");
            }
            if(!CommonUtil.ifNotEmpty(entity.isEnable())){
                entity.setEnable(true);
            }
            menuService.insertMenu(entity);
            // 修改菜单时清空url数据
//            clearCache();
            returnJson = ReturnJson.ok(entity.getId());
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

    @ApiOperation(value="查询菜单", notes="根据主键查询菜单")
    @ApiImplicitParam(name = "id", value = "主键", required = true, dataType = "String",paramType = "path")
    @GetMapping("/menus/{id}")
    public ReturnJson<PfMenu> getMenu(@PathVariable String id) {
        ReturnJson returnJson = null;
        try{
            PfMenu menu = menuService.getMenuById(id);
            returnJson = ReturnJson.ok(menu);
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
