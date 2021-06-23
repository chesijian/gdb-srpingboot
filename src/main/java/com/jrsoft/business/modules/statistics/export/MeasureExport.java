package com.jrsoft.business.modules.statistics.export;/**
 * Created by chesijian on 2021/4/19.
 */

import com.jrsoft.business.common.domain.ExcelTitle;
import com.jrsoft.business.modules.statistics.dao.StatisticsDao;
import com.jrsoft.business.modules.statistics.domain.ExportStrategy;
import com.jrsoft.business.modules.statistics.domain.SheetData;
import com.jrsoft.engine.common.utils.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @ClassName MeasureExport
 * @Description 实测实量报表导出
 * @Author chesijian
 * @Date 2021/4/19 11:47
 * @Version 1.0
 */
@Service
public class MeasureExport implements ExportStrategy {

    @Autowired
    private StatisticsDao statisticsDao;

    @Override
    public SheetData process(String fileName, String dataType, String params) {
        List<ExcelTitle> titleList  = new ArrayList<>();
        String companyUid = SessionUtil.getCompanyUid();
        //StatisticsDao statisticsDao = ServiceHelper.getBean(StatisticsDao.class);
        List<Map<String, Object>> data = statisticsDao.getMeasureNumCollect(companyUid,null,null,null,0,1000);
        titleList.add(new ExcelTitle(35 * 256,"项目名称","projName"));
        titleList.add(new ExcelTitle("测量点","totalNum"));
        titleList.add(new ExcelTitle("已测点","hadCheckNum"));
        titleList.add(new ExcelTitle("合格点","passNum"));
        titleList.add(new ExcelTitle("问题点","problemNum"));
        titleList.add(new ExcelTitle("合格率","passPercent"));
        titleList.add(new ExcelTitle("已整改","hasRectifyNum"));
        titleList.add(new ExcelTitle("整改完成率","finishPercent"));
        SheetData sheet = new SheetData();
        sheet.setDataList(data);
        sheet.setSheetName(fileName);
        sheet.setTitles(titleList);
        sheet.setDataType(dataType);
        return sheet;
    }
}
