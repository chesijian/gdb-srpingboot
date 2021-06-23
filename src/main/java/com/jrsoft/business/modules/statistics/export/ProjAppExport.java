package com.jrsoft.business.modules.statistics.export;/**
 * Created by chesijian on 2021/4/19.
 */

import com.jrsoft.business.common.domain.ExcelTitle;
import com.jrsoft.business.modules.statistics.dao.StatisticsDao;
import com.jrsoft.business.modules.statistics.domain.ExportStrategy;
import com.jrsoft.business.modules.statistics.domain.SheetData;
import com.jrsoft.business.modules.statistics.query.ProjApplicationQuery;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @ClassName ProjAppExport
 * @Description 项目应用报表导出
 * @Author chesijian
 * @Date 2021/4/19 15:08
 * @Version 1.0
 */
public class ProjAppExport implements ExportStrategy {


    @Autowired
    private StatisticsDao statisticsDao;
    @Override
    public SheetData process(String fileName, String dataType, String params) {
//        StatisticsDao statisticsDao = ServiceHelper.getBean(StatisticsDao.class);
        //ProjApplicationQuery query = CommonUtil.parseJson(params,ProjApplicationQuery.class);
        String[] paramArr = params.split(",");
        ProjApplicationQuery query = new ProjApplicationQuery();
        if(!"@".equals(paramArr[0])){
            query.setDistrict(paramArr[0]);
        }
        if(!"@".equals(paramArr[1])){
            query.setProjType(paramArr[1]);
        }
        if(!"@".equals(paramArr[2])){
            query.setYear(paramArr[2]);
        }
        query.setPageSize(1000);
        List<ExcelTitle> titleList  = new ArrayList<>();
        List<Map<String, Object>> data = statisticsDao.getProjApplication(query);
        titleList.add(new ExcelTitle(35 * 256,"项目名称","projName"));
        titleList.add(new ExcelTitle("项目状态","statusName"));
        titleList.add(new ExcelTitle("片区","district"));
        titleList.add(new ExcelTitle("预估变更金额","changeAmount"));
        titleList.add(new ExcelTitle("审定后变更金额","amountAfterApproval"));
        titleList.add(new ExcelTitle("项目性质","classify"));
        titleList.add(new ExcelTitle("项目经理","principalName"));
        titleList.add(new ExcelTitle("楼栋管理","partsNum"));
        titleList.add(new ExcelTitle("项目计划","planNum"));
        titleList.add(new ExcelTitle("质量检查","qualityNum"));
        titleList.add(new ExcelTitle("安全检查","safetyNum"));
        titleList.add(new ExcelTitle("工序检查","procedureNum"));
        titleList.add(new ExcelTitle("实测实量","measureNum"));
        titleList.add(new ExcelTitle("项目日报","dailyNum"));
        titleList.add(new ExcelTitle("项目周报","weekReportNum"));
        titleList.add(new ExcelTitle("项目月报","monthReportNum"));
        titleList.add(new ExcelTitle("项目会议","meetingNum"));
        titleList.add(new ExcelTitle("合计","totalNum"));
        SheetData sheet = new SheetData();
        sheet.setDataList(data);
        sheet.setSheetName(fileName);
        sheet.setTitles(titleList);
        sheet.setDataType(dataType);
        return sheet;
    }
}
