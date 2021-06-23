package com.jrsoft.business.modules.statistics.domain;

/**
 * Created by chesijian on 2021/4/19.
 */
public interface ExportStrategy {
    SheetData process(String fileName, String dataType, String params);
}
