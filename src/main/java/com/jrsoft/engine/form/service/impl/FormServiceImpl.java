package com.jrsoft.engine.form.service.impl;

import com.jrsoft.engine.base.service.BaseServiceImpl;
import com.jrsoft.engine.common.utils.CommonUtil;
import com.jrsoft.engine.form.domain.FormBasic;
import com.jrsoft.engine.form.domain.FormBasicInfo;
import com.jrsoft.engine.form.domain.FormColumn;
import com.jrsoft.engine.form.repository.FormRepository;
import com.jrsoft.engine.form.service.FormService;

import com.jrsoft.engine.sys.dict.domin.DicEntity;
import com.jrsoft.engine.sys.dict.domin.DicItemEntity;
import com.jrsoft.engine.sys.dict.repository.DicEntityRepository;
import com.jrsoft.engine.sys.dict.repository.DicItemEntityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class FormServiceImpl extends BaseServiceImpl<FormBasic, FormRepository> implements FormService {

    private static Logger log = LoggerFactory.getLogger(FormServiceImpl.class);

    @Autowired
    private FormRepository formRepository;

    @Autowired
    private DicEntityRepository dicEntityRepository;

    @Autowired
    private DicItemEntityRepository dicItemEntityRepository;
    /*@Autowired
    private FormDao formDao;

    @Autowired
    private FormInfoDao formInfoDao;

    @Autowired
    private SysMessageService sysMessageService;

    @Autowired
    private FormInfoRepository formInfoRepository;



    @Autowired
    private FormColumnDao formColumnDao;

    @Autowired
    private FormColumnRepository formColumnRepository;

    @Autowired
    private PfSqlServiceI pfSqlServiceI;

    @Autowired
    private SysFormEventService sysFormEventService;

    @Autowired
    private PfFormServiceI pfFormServiceI;

    @Autowired
    private FormPrintRepository formPrintRepository;

    @Autowired
    private FormCodeRepository formCodeRepository;

    @Autowired
    private FormExcelRepository formExcelRepository;

    @Autowired
    private FormExcelInfoRepository formExcelInfoRepository;

    @Autowired
    private OrgMenusDao orgMenusDao;



    */

    private String TITLE = "新建审批"; //首页自定义表单标题

    @Override
    public FormBasic findByFormKey(String formKey) {
        Date startTime = new Date();
        // 读取是否缓存配置
        String enable = "false";//PfSysConfCache.getSysConfVal("sys.form.config.cache.enable");
        FormBasic formBasic = null;
        if(CommonUtil.ifNotEmpty(enable) && enable.equals("true")){
            //formBasic = FormBasicUtil.getForm(formKey);// 先从redis获取配置数据
        }
        // 缓存保存错误 先注释掉
        if (CommonUtil.ifNotEmpty(formBasic)) { // 如果redis中有直接返回
            formBasic.getBackFillMap();
            return formBasic;
        }
        formBasic = formRepository.findByFormKey(formKey); // 先通过formkey查寻

        if (!CommonUtil.ifNotEmpty(formBasic)) { // 如果通过formkey查不到 则将formKey当中主键再查一遍
            formBasic = formRepository.getOne(formKey);
        }

        if (!CommonUtil.ifNotEmpty(formBasic)) { // 都查不到
            return  null;
        }

        List<FormBasicInfo> formBasicInfoList = null;//formInfoRepository.findByFormId(formBasic.getId());


        List<FormColumn> formColumnList = null;//formColumnRepository.findByFormIdOrderBySort(formBasic.getId());

        //存放字典
        Map<String, DicEntity> dicEntityMap = new HashMap<>();
        Map<String, List<DicItemEntity>> dicEntityList = new HashMap<>();
        // 遍历查询关联数据字典
        if(CommonUtil.ifNotEmptyList(formColumnList)){
            for (int i = 0; i < formColumnList.size(); i++) {
                FormColumn formColumn = formColumnList.get(i);
                if(CommonUtil.ifNotEmpty(formColumn.getAttr()) && "select-dic".equalsIgnoreCase(formColumn.getWidgetType())){
                    DicEntity dicEntity = null;
                    // 先判断是否已经查询
                    if (CommonUtil.ifNotEmpty(dicEntityMap.get(formColumn.getAttr()))){
                        dicEntity = dicEntityMap.get(formColumn.getAttr());
                    } else {
                        dicEntity = dicEntityRepository.getByDicId(formColumn.getAttr());
                        // 查询明细列
                        if (CommonUtil.ifNotEmpty(dicEntity)){
                            List<DicItemEntity> pfDicItemEntityList = null;
                            if (CommonUtil.ifNotEmpty(dicEntityList.get(formColumn.getAttr()))){
                                pfDicItemEntityList = dicEntityList.get(formColumn.getAttr());
                            } else {
                                pfDicItemEntityList = dicItemEntityRepository.findAllByParentId(dicEntity.getId());
                                dicEntityList.put(formColumn.getAttr(),pfDicItemEntityList);
                            }
                            dicEntity.setChildren(pfDicItemEntityList);
                        }
                        dicEntityMap.put(formColumn.getAttr(), dicEntity);
                    }
                    formColumn.setDicEntity(dicEntity);
                }
            }
        }
        // 查询事件
        /*List<SysFormEventEntity> events = sysFormEventService.findByFormId(formBasic.getId());
        formBasic.setEvents(events);

        Map<String,List<SysFormEventEntity>> eventsMap = new HashMap<>();
        if(events != null && events.size()>0){
            for(SysFormEventEntity event : events){
                List<SysFormEventEntity> data = eventsMap.get(event.getFormInfoId());
                if(data == null){
                    data = new ArrayList<>();
                    eventsMap.put(event.getFormInfoId(),data);
                }
                if (event.getApiId() != null){
                    SysMessageEntity sysMessageEntity = sysMessageService.get(event.getApiId());
                    if(sysMessageEntity != null){
                        event.setApiConfig(sysMessageEntity);
                    }
                }
                data.add(event);
            }
        }
        // 先找到所有子表
        List<FormBasicInfo> formBasicInfoList3 = new ArrayList<FormBasicInfo>();
        for (int i = 0; i < formBasicInfoList.size(); i++) {
            FormBasicInfo formBasicInfo = formBasicInfoList.get(i);
            if (3 == formBasicInfo.getType()) {
                formBasicInfo.setEvents(eventsMap.get(formBasicInfo.getId()));
                List<FormColumn> columns = new ArrayList<FormColumn>();
                for (int j = 0; j < formColumnList.size(); j++) {
                    FormColumn formColumn = formColumnList.get(j);
                    if (formBasicInfo.getId().equals(formColumn.getParentId())) {
                        columns.add(formColumn);
                    }
                }
                formBasicInfo.setQueryColumns(columns);
                formBasicInfo.setEvents(eventsMap.get(formBasicInfo.getId()));
                formBasicInfo.setFormExcelList(this.getExcel(formBasicInfo.getId()));
            }
            formBasicInfoList3.add(formBasicInfo);
        }

        List<FormBasicInfo> formBasicInfoList2 = new ArrayList<FormBasicInfo>();
        for (int i = 0; i < formBasicInfoList.size(); i++) {
            FormBasicInfo formBasicInfo = formBasicInfoList.get(i);
            if (3 != formBasicInfo.getType()) {
                formBasicInfo.setEvents(eventsMap.get(formBasicInfo.getId()));
                List<FormColumn> formColumns = new ArrayList<FormColumn>();
                List<FormColumn> queryColumns = new ArrayList<FormColumn>();
                for (int j = 0; j < formColumnList.size(); j++) {
                    FormColumn formColumn = formColumnList.get(j);
                    if (formBasicInfo.getId().equals(formColumn.getParentId())) {
                        if ("sub-form".equals(formColumn.getWidgetType()) && 1 == formColumn.getType()) {
                            for (int k = 0; k < formBasicInfoList3.size(); k++) {
                                FormBasicInfo formBasicInfo2 = formBasicInfoList3.get(k);
                                if (formBasicInfo2.getId().equals(formColumn.getId())){
                                    formColumn.setConfig(formBasicInfo2);
                                }
                            }
                        }
                        if (1 == formColumn.getType()) {
                            // 判断组装apiTitle
                            if(CommonUtil.ifNotEmpty(formColumn.getSourceType()) && formColumn.getSourceType().equals("api") && CommonUtil.ifNotEmpty(formColumn.getSourceAttr())){
                                List<SysMessageEntity> apiConfigList = new ArrayList<>();
                                String[] attrArr = formColumn.getSourceAttr().split(",");
                                for(String apiId : attrArr){
                                    SysMessageEntity sysMessageEntity = sysMessageService.get(apiId);
                                    if(sysMessageEntity != null){
                                        apiConfigList.add(sysMessageEntity);
                                    }
                                }
                                formColumn.setApiConfig(apiConfigList);

                            }
                            formColumns.add(formColumn);
                        }
                        if (2 == formColumn.getType()) {
                            // 判断组装apiTitle
                            if(CommonUtil.ifNotEmpty(formColumn.getSourceType()) && formColumn.getSourceType().equals("api") && CommonUtil.ifNotEmpty(formColumn.getSourceAttr())){
                                List<SysMessageEntity> apiConfigList = new ArrayList<>();
                                String[] attrArr = formColumn.getSourceAttr().split(",");
                                for(String apiId : attrArr){
                                    SysMessageEntity sysMessageEntity = sysMessageService.get(apiId);
                                    if(sysMessageEntity != null){
                                        apiConfigList.add(sysMessageEntity);
                                    }
                                }
                                formColumn.setApiConfig(apiConfigList);
                            }
                            queryColumns.add(formColumn);
                        }
                    }
                }
                formBasicInfo.setFormColumns(formColumns);
                formBasicInfo.setQueryColumns(queryColumns);
                formBasicInfo.setFormExcelList(this.getExcel(formBasicInfo.getId()));
                formBasicInfoList2.add(formBasicInfo);

                // 判断组装apiTitle
                if(CommonUtil.ifNotEmpty(formBasicInfo.getApi())){
                    SysMessageEntity sysMessageEntity = sysMessageService.get(formBasicInfo.getApi());
                    if(sysMessageEntity != null){
                        formBasicInfo.setApiConfig(sysMessageEntity);
                    }
                }
            }
        }
        if (CommonUtil.ifNotEmpty(formBasic)) {
            FormCode formCode = formCodeRepository.findByFormKey(formBasic.getFormKey());
            formBasic.setFormCode(formCode);
            formBasic.setForms(formBasicInfoList2);
            FormBasicUtil.putForm(formKey, formBasic);
        }*/
        return formBasic;
    }

    @Override
    public FormBasic getAuthsByFormKey(String formKey) {
        return null;
    }

    @Override
    public FormBasic getAuths(FormBasic formBasic) {
        return null;
    }

    @Override
    public String getFlowImg(String parentId) {
        return null;
    }

    @Override
    public Map<String, String> getFlowImgs(List<String> parentIds) {
        return null;
    }

    @Override
    public String getAvatar(String id) {
        return null;
    }

    @Override
    public String getRedisConig() {
        return null;
    }

    @Override
    public String getFormPath(String formKey) {
        return null;
    }

    @Override
    public List<Map<String, Object>> getMenus(String parentId) {
        return null;
    }

    @Override
    public void createCode(Map<String, Object> params) {

    }

    @Override
    public Map<String, Object> syncIms(Map<String, Object> params) {
        return null;
    }

    // 获取编码格式
    public static String getEncoding(String str) {
        String encoding = "GBK";
        try {
            String ISO8859 = new String(str.toString().getBytes("ISO-8859-1"));
            String GB2312 =  new String(str.toString().getBytes("GB2312"));
            String GBK = new String(str.toString().getBytes("GBK"));
            String UTF8 = new String(str.toString().getBytes("UTF-8"));
            String Unicode = new String(str.toString().getBytes("Unicode"));
            if(str.equals(ISO8859)){
                encoding = "ISO-8859-1";
            }else if(str.equals(Unicode)){
                encoding = "Unicode";
            }else if(str.equals(GB2312)){
                encoding = "GB2312";
            }else if(str.equals(GBK)){
                encoding = "GBK";
            }else if(str.equals(UTF8)){
                encoding = "UTF-8";
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            return "GBK";
        }
        return  encoding;
    }


}