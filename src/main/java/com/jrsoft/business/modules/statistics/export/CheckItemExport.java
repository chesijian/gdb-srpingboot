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
 * @ClassName CheckItemExport
 * @Description 问题检查项报表导出
 * @Author chesijian
 * @Date 2021/4/19 21:33
 * @Version 1.0
 */
public class CheckItemExport implements ExportStrategy {

    @Autowired
    private StatisticsDao statisticsDao;

    @Override
    public SheetData process(String fileName, String dataType, String params) {
        String companyUid = SessionUtil.getCompanyUid();
        List<Map<String, Object>> data = statisticsDao.getCheckItemCollect(companyUid,null,0,1000);
        List<ExcelTitle> titleList  = new ArrayList<>();
        titleList.add(new ExcelTitle(35 * 256,"项目名称","projName"));
        titleList.add(new ExcelTitle("类型","checkType"));
        titleList.add(new ExcelTitle("计划检查","planNum"));
        titleList.add(new ExcelTitle("实际检查","checkNum"));
        titleList.add(new ExcelTitle("合格项","passNum"));
        titleList.add(new ExcelTitle("整改项","rectifyNum"));
        titleList.add(new ExcelTitle("已整改","hadRectifyNum"));
        titleList.add(new ExcelTitle("未整改","notRectifyNum"));
        titleList.add(new ExcelTitle("已复查","hadReviewNum"));
        titleList.add(new ExcelTitle("未复查","waitingReviewNum"));
        SheetData sheet = new SheetData();
        sheet.setDataList(data);
        sheet.setSheetName(fileName);
        sheet.setTitles(titleList);
        sheet.setDataType(dataType);
        return sheet;
    }
}
