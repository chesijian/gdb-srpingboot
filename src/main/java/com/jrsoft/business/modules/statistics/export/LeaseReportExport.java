package com.jrsoft.business.modules.statistics.export;/**
 * Created by chesijian on 2021/4/19.
 */

import com.jrsoft.business.common.domain.ExcelTitle;
import com.jrsoft.business.modules.progress.dao.ProgressDao;
import com.jrsoft.business.modules.progress.model.Task;
import com.jrsoft.business.modules.progress.query.ScheduleQuery;
import com.jrsoft.business.modules.statistics.domain.ExportStrategy;
import com.jrsoft.business.modules.statistics.domain.SheetData;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName LeaseReportExport
 * @Description 进度明细报表导出
 * @Author chesijian
 * @Date 2021/4/19 18:36
 * @Version 1.0
 */
public class LeaseReportExport implements ExportStrategy {

    @Autowired
    private ProgressDao progressDao;

    @Override
    public SheetData process(String fileName, String dataType, String params) {
        String[] paramArr = params.split(",");
        //ScheduleQuery query = CommonUtil.parseJson(params,ScheduleQuery.class);
        ScheduleQuery query = new ScheduleQuery();
        query.setPageSize(10000);
        if(!"@".equals(paramArr[0])){
            query.setAssignee(paramArr[0]);
        }
        if(!"@".equals(paramArr[1])){
            query.setStatus(paramArr[1]);
        }
        if(!"@".equals(paramArr[2])){
            query.setProjType(paramArr[2]);
        }
        if(!"@".equals(paramArr[3])){
            query.setProjUids(paramArr[3]);
        }
        if(!"@".equals(paramArr[4])){
            query.setStartDate(paramArr[4]);
        }
        if(!"@".equals(paramArr[5])){
            query.setEndDate(paramArr[5]);
        }

//        List<LeaseReport> dataList = progressDao.getReportForLease(query);
        List<Task> data = progressDao.getDetsForProgress(query);
        List<ExcelTitle> titleList  = new ArrayList<>();

        titleList.add(new ExcelTitle("项目编号","projCode"));
        titleList.add(new ExcelTitle(50 * 256,"项目名称","projName"));
        titleList.add(new ExcelTitle(50 * 256,"任务名称","text"));
        titleList.add(new ExcelTitle("负责人","assigneeName"));
        titleList.add(new ExcelTitle("职能","function"));
        titleList.add(new ExcelTitle("汇报时间","lastReportDate"));
        titleList.add(new ExcelTitle("是否通过审核","statusName"));
        titleList.add(new ExcelTitle("计划开始日期","startDate"));
        titleList.add(new ExcelTitle("计划完成日期","endDate"));
        titleList.add(new ExcelTitle("实际开始日期","actStartDate"));
        titleList.add(new ExcelTitle("实际完成日期","actEndDate"));
        titleList.add(new ExcelTitle("计划工期(天)","duration"));
        titleList.add(new ExcelTitle("实际工期(天)","actDuration"));
        titleList.add(new ExcelTitle("延误天数","delayDays"));
        titleList.add(new ExcelTitle("进度状态","taskStatusName"));
        titleList.add(new ExcelTitle("工期偏差(天)","offsetDuration"));
        titleList.add(new ExcelTitle("进度成果","remark"));
        titleList.add(new ExcelTitle("问题风险","riskProblem"));
        titleList.add(new ExcelTitle("风险应对措施","riskCounterMeasure"));
        SheetData sheet = new SheetData();
        sheet.setDatas(data);
        sheet.setSheetName(fileName);
        sheet.setTitles(titleList);
        sheet.setDataType(dataType);
        return sheet;
    }
}
