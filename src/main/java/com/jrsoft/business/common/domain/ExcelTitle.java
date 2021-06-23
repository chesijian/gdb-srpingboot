package com.jrsoft.business.common.domain;

import com.jrsoft.engine.base.domain.BaseEntity;
import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.util.List;

/**
 * Created by chesijian on 2018/10/27.
 */
@Data
@Alias("ExcelTitle")
public class ExcelTitle extends BaseEntity {
    Integer line; //行号
    Integer width; //宽
    String title; //标题
    int sort;
    boolean ifTotal;
    String field; //字段名称

    public ExcelTitle(String title, String field) {
        this.title = title;
        this.field = field;
    }

    public ExcelTitle(Integer width, String title, String field) {
        this.width = width;
        this.title = title;
        this.field = field;
    }
}
