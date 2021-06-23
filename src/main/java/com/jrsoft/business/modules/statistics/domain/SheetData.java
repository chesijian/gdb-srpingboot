package com.jrsoft.business.modules.statistics.domain;/**
 * Created by chesijian on 2020/1/14.
 */

import com.jrsoft.business.common.domain.ExcelTitle;
import com.jrsoft.business.modules.progress.model.Task;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @ClassName SheetData
 * @Description Excel表
 * @Author chesijian
 * @Date 2020/1/14 14:15
 * @Version 1.0
 */
@Data
public class SheetData {
    protected List<Map<String,Object>> dataList;
    protected String sheetName;
    private String dataType;
    private List<ExcelTitle> titles;
    private List<Task> datas;

    /*public SheetData(List<Map<String, Object>> dataList) {
        this.dataList = dataList;
    }*/


}
