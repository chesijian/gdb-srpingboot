package com.jrsoft.engine.form.service;

import com.jrsoft.engine.base.model.ReturnJson;
import com.jrsoft.engine.base.model.ReturnPageJson;
import com.jrsoft.engine.base.service.BaseService;
import com.jrsoft.engine.form.domain.FormBasic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 表单service
 **/
public interface FormService extends BaseService<FormBasic> {
    FormBasic findByFormKey(String formKey);
    //ReturnPageJson findDataList(ParamInfo params);
    //List<Map<String, Object>>  findTreeDataList(ParamInfo params);
    //Map<String, Object>   findData(ParamInfo params);
    //FormBasicInfo getById(String id);

    /**报表模板数据库表下拉
     * @param formPrint
     * @return
     */
    //List<FormPrint> getReport(FormPrint formPrint);

    /**
     * 获取数据库表下拉
     * @param queryInfo
     * @return
     */
   // List<Map<String,Object>> findSqlDicData(QueryInfo queryInfo);

    /**
     * 根据注册接口获取数据，主要用于主表
     * @param apiId
     * @param params
     * @return
     */
    /*List<Map<String,Object>> executeApi(WfProcess process, String apiId, String key, HashMap<String, Object> params);

    public ReturnJson executeApiData(WfProcess process, String apiId, String key, HashMap<String, Object> params);

    List<FormExcel> getExcel(String formInfoId);

    List<Map<String,Object>> getUserList(ParamInfo params);

    void editTree(ParamInfo params);*/

    //List<Map<String, Object>> findAddressTree(ParamInfo params);

    FormBasic getAuthsByFormKey(String formKey);

    FormBasic getAuths(FormBasic formBasic);

    String getFlowImg(String parentId);

    Map<String, String>getFlowImgs(List<String> parentIds);

    String getAvatar(String id);

    String getRedisConig();

    //List<OrgMenus> getFormPath(String formKey, String formInfoId);
    String getFormPath(String formKey);

    List<Map<String, Object>> getMenus(String parentId);

    void createCode(Map<String, Object> params);

    Map<String, Object> syncIms(Map<String, Object> params);

    /**
     * 顶部工具栏按钮选人，选角色回调批量授权
     * @param params
     */
    //void saveSelectAuth(SelectBatchAuthParams params);
}
