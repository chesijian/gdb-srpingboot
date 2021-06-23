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
import java.util.List;
import java.util.Map;

/**
 * @ClassName ConferenceTaskExport
 * @Description 任务跟进报表导出
 * @Author chesijian
 * @Date 2021/4/19 14:19
 * @Version 1.0
 */
public class ConferenceTaskExport implements ExportStrategy {

    @Autowired
    private StatisticsDao statisticsDao;;
    @Override
    public SheetData process(String fileName, String dataType, String params) {
        List<ExcelTitle> titleList  = new ArrayList<>();
        String companyUid = SessionUtil.getCompanyUid();
        List<Map<String, Object>> data = statisticsDao.getTaskCollect(companyUid,null,null,0,1000,"");
        titleList.add(new ExcelTitle(35 * 256,"项目名称","projName"));
        titleList.add(new ExcelTitle(40 * 256,"会议主题","meetingTitle"));
        titleList.add(new ExcelTitle(40 * 256,"任务主题","subject"));
        titleList.add(new ExcelTitle("负责人","principalName"));
        titleList.add(new ExcelTitle("执行情况","statusName"));
        titleList.add(new ExcelTitle("完成期限","endDate"));
        titleList.add(new ExcelTitle("实际完成","actFinishDate"));
        SheetData sheet = new SheetData();
        sheet.setDataList(data);
        sheet.setSheetName(fileName);
        sheet.setTitles(titleList);
        sheet.setDataType(dataType);
        return sheet;
    }
}
