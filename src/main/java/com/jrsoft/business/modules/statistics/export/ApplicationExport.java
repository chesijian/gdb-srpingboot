package com.jrsoft.business.modules.statistics.export;/**
 * Created by chesijian on 2021/4/19.
 */

import com.jrsoft.business.common.domain.ExcelTitle;
import com.jrsoft.business.modules.statistics.dao.StatisticsDao;
import com.jrsoft.business.modules.statistics.domain.ExportStrategy;
import com.jrsoft.business.modules.statistics.domain.SheetData;
import com.jrsoft.engine.common.utils.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName ApplicationExport
 * @Description 应用统计报表导出
 * @Author chesijian
 * @Date 2021/4/19 15:15
 * @Version 1.0
 */
public class ApplicationExport implements ExportStrategy {

    @Autowired
    private StatisticsDao statisticsDao;

    @Override
    public SheetData process(String fileName, String dataType, String params) {
        List<Map<String, Object>> data =new ArrayList<>();
        List<ExcelTitle> titleList  = new ArrayList<>();
        String companyUid = SessionUtil.getCompanyUid();
        Map<String,Object> application = statisticsDao.getApplicationNums(companyUid);
        Map<String,Object> project = new HashMap<>();
        project.put("name","项目登记");
        project.put("totalNum",application.get("projNum"));
        project.put("weekNum",application.get("projWeekNum"));
        data.add(project);
        Map<String,Object> news = new HashMap<>();
        news.put("name","新闻公告");
        news.put("totalNum",application.get("newsNum"));
        news.put("weekNum",application.get("newsWeekNum"));
        data.add(news);
        Map<String,Object> part = new HashMap<>();
        part.put("name","楼栋管理");
        part.put("totalNum",application.get("partsNum"));
        part.put("weekNum",application.get("partsWeekNum"));
        data.add(part);
        Map<String,Object> drawing = new HashMap<>();
        drawing.put("name","楼栋图纸");
        drawing.put("totalNum",application.get("drawingNum"));
        drawing.put("weekNum",application.get("drawingWeekNum"));
        data.add(drawing);
        Map<String,Object> daily = new HashMap<>();
        daily.put("name","项目日报");
        daily.put("totalNum",application.get("dailyNum"));
        daily.put("weekNum",application.get("dailyWeekNum"));
        data.add(daily);
        Map<String,Object> report = new HashMap<>();
        report.put("name","项目周月报");
        report.put("totalNum",application.get("reportNum"));
        report.put("weekNum",application.get("reportWeekNum"));
        data.add(report);
        Map<String,Object> meetingNum = new HashMap<>();
        meetingNum.put("name","会议纪要");
        meetingNum.put("totalNum",application.get("meetingNum"));
        meetingNum.put("weekNum",application.get("weekNum"));
        data.add(meetingNum);
        Map<String,Object> meetingTaskNum = new HashMap<>();
        meetingTaskNum.put("name","会议任务");
        meetingTaskNum.put("totalNum",application.get("meetingTaskNum"));
        meetingTaskNum.put("weekNum",application.get("meetingTaskWeekNum"));
        data.add(meetingTaskNum);
        Map<String,Object> fileNum = new HashMap<>();
        fileNum.put("name","工程文档上传");
        fileNum.put("totalNum",application.get("fileNum"));
        fileNum.put("weekNum",application.get("fileWeekNum"));
        data.add(fileNum);
        Map<String,Object> gaTask = new HashMap<>();
        gaTask.put("name","进度计划");
        gaTask.put("totalNum",application.get("gaTaskNum"));
        gaTask.put("weekNum",application.get("gaTaskWeekNum"));
        data.add(gaTask);
        Map<String,Object> taskReport = new HashMap<>();
        taskReport.put("name","进度上报");
        taskReport.put("totalNum",application.get("taskReportNum"));
        taskReport.put("weekNum",application.get("taskReportWeekNum"));
        data.add(taskReport);
        Map<String,Object> inspectionPlan = new HashMap<>();
        inspectionPlan.put("name","质量计划");
        inspectionPlan.put("totalNum",application.get("inspectionPlanNum"));
        inspectionPlan.put("weekNum",application.get("inspectionPlanWeekNum"));
        data.add(inspectionPlan);
        Map<String,Object> quality = new HashMap<>();
        quality.put("name","质量检查");
        quality.put("totalNum",application.get("qualityNum"));
        quality.put("weekNum",application.get("qualityWeekNum"));
        data.add(quality);
        Map<String,Object> safety = new HashMap<>();
        safety.put("name","安全检查");
        safety.put("totalNum",application.get("safetyNum"));
        safety.put("weekNum",application.get("safetyWeekNum"));
        data.add(safety);
        Map<String,Object> checkout = new HashMap<>();
        checkout.put("name","工序检验批");
        checkout.put("totalNum",application.get("checkoutNum"));
        checkout.put("weekNum",application.get("checkoutWeekNum"));
        data.add(checkout);
        Map<String,Object> processTask = new HashMap<>();
        processTask.put("name","工序检验批");
        processTask.put("totalNum",application.get("processTaskNum"));
        processTask.put("weekNum",application.get("processTaskWeekNum"));
        data.add(processTask);
        Map<String,Object> processTaskFins = new HashMap<>();
        processTaskFins.put("name","工序报验");
        processTaskFins.put("totalNum",application.get("processTaskFinshNum"));
        processTaskFins.put("weekNum",application.get("processTaskFinshWeekNum"));
        data.add(processTaskFins);
        Map<String,Object> procedure = new HashMap<>();
        procedure.put("name","工序检查");
        procedure.put("totalNum",application.get("procedureNum"));
        procedure.put("weekNum",application.get("procedureWeekNum"));
        data.add(procedure);
        Map<String,Object> measurePlan = new HashMap<>();
        measurePlan.put("name","实测计划");
        measurePlan.put("totalNum",application.get("measurePlanNum"));
        measurePlan.put("weekNum",application.get("measurePlanWeekNum"));
        data.add(measurePlan);
        Map<String,Object> measure = new HashMap<>();
        measure.put("name","实测检查");
        measure.put("totalNum",application.get("measureNum"));
        measure.put("weekNum",application.get("measureWeekNum"));
        data.add(measure);
        Map<String,Object> measureValue = new HashMap<>();
        measureValue.put("name","实测测量值");
        measureValue.put("totalNum",application.get("measureValueNum"));
        measureValue.put("weekNum",application.get("measureValueWeekNum"));
        data.add(measureValue);
        Map<String,Object> material = new HashMap<>();
        material.put("name","物料合同");
        material.put("totalNum",application.get("materialNum"));
        material.put("weekNum",application.get("materialWeekNum"));
        data.add(material);
        Map<String,Object> inStore = new HashMap<>();
        inStore.put("name","物料验收入库");
        inStore.put("totalNum",application.get("inStoreNum"));
        inStore.put("weekNum",application.get("inStoreWeekNum"));
        data.add(inStore);
        Map<String,Object> contactsheet = new HashMap<>();
        contactsheet.put("name","工作联系单");
        contactsheet.put("totalNum",application.get("contactsheetNum"));
        contactsheet.put("weekNum",application.get("contactsheetWeekNum"));
        data.add(contactsheet);
        titleList.add(new ExcelTitle("菜单模块","name"));
        titleList.add(new ExcelTitle("累计数据记录量(条)","totalNum"));
        titleList.add(new ExcelTitle("本周新增数据记录量(条)","weekNum"));
        SheetData sheet = new SheetData();
        sheet.setDataList(data);
        sheet.setSheetName(fileName);
        sheet.setTitles(titleList);
        sheet.setDataType(dataType);
        return sheet;
    }
}
