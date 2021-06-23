package com.jrsoft.business.modules.construction.domain;

import com.jrsoft.engine.impl.persistence.entity.BaseModelEntity;

import java.util.List;

/**
 * Created by chesijian on 2018/10/27.
 */
public class ExportField extends BaseModelEntity {
    Integer row;
    Integer line;
    Integer cols;
    String field;
    String fileName;

    public Integer getRow() {
        return row;
    }

    public void setRow(Integer row) {
        this.row = row;
    }

    public Integer getLine() {
        return line;
    }

    public void setLine(Integer line) {
        this.line = line;
    }

    public Integer getCols() {
        return cols;
    }

    public void setCols(Integer cols) {
        this.cols = cols;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
