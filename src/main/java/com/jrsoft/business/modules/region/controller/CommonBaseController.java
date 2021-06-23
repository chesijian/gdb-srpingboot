package com.jrsoft.business.modules.region.controller;

import com.jrsoft.business.modules.region.model.AreaVO;
import com.jrsoft.business.modules.region.service.ICommonService;
import com.jrsoft.engine.base.web.BaseController;
import com.jrsoft.engine.base.model.ReturnJson;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 通用服务管理
 * Created by zcf on 2018/10/25.
 */
@Api(value = "CommonBaseController", description = "通用服务管理")
@RestController
@RequestMapping("/business/commonBase")
public class CommonBaseController extends BaseController {
    @Autowired
    private ICommonService commonService;

    @ApiOperation(value="查询省级信息", notes="查询省级信息")
    @RequestMapping(value = "/getProvinceList",method = RequestMethod.GET)
    public ReturnJson<List<AreaVO>> getProvinceList(){
        ReturnJson returnJson = null;
        try{
            List<AreaVO> provinceList = commonService.getProvinceList();
            returnJson = returnJson.ok(provinceList);
        }catch (Exception e){
            e.printStackTrace();
            returnJson = returnJson.error(e.getMessage());
        }

        return returnJson;
    }

    @ApiOperation(value="查询市级信息", notes="查询市级信息")
    @RequestMapping(value = "/getCityByParentId",method = RequestMethod.GET)
    public ReturnJson<List<AreaVO>> getCityByParentId(Integer parentId){
        ReturnJson returnJson = null;
        try{
            if(null != parentId){
                List<AreaVO> cityList = commonService.getCityByParentId(parentId);
                returnJson = returnJson.ok(cityList);
            }else{
                returnJson = returnJson.ok(null);
            }
        }catch (Exception e){
            e.printStackTrace();
            returnJson = returnJson.error(e.getMessage());
        }

        return returnJson;
    }

    @ApiOperation(value="查询县级信息", notes="查询县级信息")
    @RequestMapping(value = "/getCountyByParentId",method = RequestMethod.GET)
    public ReturnJson<List<AreaVO>> getCountyByParentId(Integer parentId){
        ReturnJson returnJson = null;
        try{
            List<AreaVO> countyList = commonService.getCountyByParentId(parentId);
            returnJson = returnJson.ok(countyList);
        }catch (Exception e){
            e.printStackTrace();
            returnJson = returnJson.error(e.getMessage());
        }

        return returnJson;
    }
}
