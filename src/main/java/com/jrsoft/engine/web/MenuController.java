package com.jrsoft.engine.web;/**
 * Created by chesijian on 2021/6/19.
 */

import com.jrsoft.engine.base.domain.sys.PfMenu;
import com.jrsoft.engine.base.model.ReturnJson;
import com.jrsoft.engine.common.utils.CommonUtil;
import com.jrsoft.engine.exception.EngineException;
import com.jrsoft.engine.service.MenuService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
