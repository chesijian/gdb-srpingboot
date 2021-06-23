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
 * @ClassName RecordAreaExport
 * @Description 问题楼栋分布报表导出
 * @Author chesijian
 * @Date 2021/4/19 21:40
 * @Version 1.0
 */
public class RecordAreaExport implements ExportStrategy {

    @Autowired
    private StatisticsDao statisticsDao;

    @Override
    public SheetData process(String fileName, String dataType, String params) {
        String companyUid = SessionUtil.getCompanyUid();
        List<Map<String, Object>> data = statisticsDao.getAreaProblemCollect(companyUid,null,0,1000);
        List<ExcelTitle> titleList  = new ArrayList<>();
        titleList.add(new ExcelTitle(35 * 256,"项目名称","projName"));
        titleList.add(new ExcelTitle("类型","checkType"));
        titleList.add(new ExcelTitle("办公楼","officNum"));
        titleList.add(new ExcelTitle("仓库楼","warehouseNum"));
        titleList.add(new ExcelTitle("分拣中心","edcsNum"));
        titleList.add(new ExcelTitle("综合楼","complexNum"));
        titleList.add(new ExcelTitle("中转站","transferNum"));
        SheetData sheet = new SheetData();
        sheet.setDataList(data);
        sheet.setSheetName(fileName);
        sheet.setTitles(titleList);
        sheet.setDataType(dataType);
        return sheet;
    }
}
