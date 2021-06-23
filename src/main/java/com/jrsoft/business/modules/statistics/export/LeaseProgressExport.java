package com.jrsoft.business.modules.statistics.export;/**
 * Created by chesijian on 2021/4/19.
 */

import com.jrsoft.business.common.domain.ExcelTitle;
import com.jrsoft.business.modules.progress.dao.ProgressDao;
import com.jrsoft.business.modules.progress.query.ScheduleQuery;
import com.jrsoft.business.modules.statistics.domain.ExportStrategy;
import com.jrsoft.business.modules.statistics.domain.SheetData;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @ClassName LeaseProgressExport
 * @Description 进度汇总报表导出
 * @Author chesijian
 * @Date 2021/4/19 18:14
 * @Version 1.0
 */
public class LeaseProgressExport implements ExportStrategy {
    @Autowired
    private ProgressDao progressDao;

    @Override
    public SheetData process(String fileName, String dataType, String params) {

        String[] paramArr = params.split(",");
        //ScheduleQuery query = CommonUtil.parseJson(params,ScheduleQuery.class);
        ScheduleQuery query = new ScheduleQuery();
        query.setPageSize(1000);
        if(!"@".equals(paramArr[0])){
            query.setDistrict(paramArr[0]);
        }
        if(!"@".equals(paramArr[1])){
            query.setProjType(paramArr[1]);
        }
        if(!"@".equals(paramArr[2])){
            query.setProjUids(paramArr[2]);
        }
        query.setPageSize(1000);
        List<Map<String, Object>> data = progressDao.getReportForLeaseCollect(query);
        List<ExcelTitle> titleList  = new ArrayList<>();
        titleList.add(new ExcelTitle("项目编号","XMBH_"));
        titleList.add(new ExcelTitle(50 * 256,"项目名称","XMMC_"));
        titleList.add(new ExcelTitle("项目类型","CLASSIFY_"));
        titleList.add(new ExcelTitle("场地类型","SITE_TYPE_"));
        titleList.add(new ExcelTitle("区域","PROJ_AREA_"));
        titleList.add(new ExcelTitle("城市","CITY_"));
        titleList.add(new ExcelTitle("场地属性","SITE_PROPERTY_"));
        titleList.add(new ExcelTitle("建筑面积(m2)","COVERED_AREA_"));
        titleList.add(new ExcelTitle("设计","DESIGN_"));
        titleList.add(new ExcelTitle("成本","COST_"));
        titleList.add(new ExcelTitle("采购","PURCHASE_"));
        titleList.add(new ExcelTitle("工程项目经理","PM_"));
        titleList.add(new ExcelTitle("业务需求接口人","BUSINESS_REQUIREMENTS_"));
        titleList.add(new ExcelTitle("计划开始日期","START_DATE_"));
        titleList.add(new ExcelTitle("计划完成日期","END_DATE_"));
        titleList.add(new ExcelTitle("实际开始日期","ACT_START_DATE_"));
        titleList.add(new ExcelTitle("实际完成日期","ACT_END_DATE_"));
        titleList.add(new ExcelTitle("延误天数","delayDays"));
        titleList.add(new ExcelTitle("进度状态","progressStatus"));
        SheetData sheet = new SheetData();
        sheet.setDataList(data);
        sheet.setSheetName(fileName);
        sheet.setTitles(titleList);
        sheet.setDataType(dataType);
        return sheet;
    }
}
