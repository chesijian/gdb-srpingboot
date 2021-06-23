package com.jrsoft.business.modules.statistics.export;/**
 * Created by chesijian on 2021/4/20.
 */

import com.jrsoft.business.common.domain.ExcelTitle;
import com.jrsoft.business.modules.statistics.dao.StatisticsDao;
import com.jrsoft.business.modules.statistics.domain.ExportStrategy;
import com.jrsoft.business.modules.statistics.domain.SheetData;
import com.jrsoft.engine.common.utils.SessionUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @ClassName CommExport
 * @Description 通用报表导出
 * @Author chesijian
 * @Date 2021/4/20 9:13
 * @Version 1.0
 */
public class CommExport implements ExportStrategy {

    private StatisticsDao statisticsDao;

    @Override
    public SheetData process(String fileName, String dataType, String params) {
        String companyUid = SessionUtil.getCompanyUid();
        List<Map<String, Object>> data = statisticsDao.getCheckNumCollect(companyUid,null,dataType,0,1000,null,null);
        List<ExcelTitle> titleList  = new ArrayList<>();
        titleList.add(new ExcelTitle(50 * 256,"项目名称","projName"));
        titleList.add(new ExcelTitle("检查项数量","checkNum"));
        titleList.add(new ExcelTitle("合格项数量","passNum"));
        titleList.add(new ExcelTitle("问题项","problemNum"));
        titleList.add(new ExcelTitle("未整改数量","waitingRectifyNum"));
        titleList.add(new ExcelTitle("已整改数量","hadRectifyNum"));
        titleList.add(new ExcelTitle("已复检数量","finishNum"));
        titleList.add(new ExcelTitle("未复检数量","waitingReviewNum"));
        SheetData sheet = new SheetData();
        sheet.setDataList(data);
        sheet.setSheetName(fileName);
        sheet.setTitles(titleList);
        sheet.setDataType(dataType);
        return sheet;
    }
}
