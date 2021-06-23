package com.jrsoft.business.modules.statistics.query;/**
 * Created by chesijian on 2021/3/24.
 */

import com.jrsoft.engine.base.query.BaseQuery;
import lombok.Data;

/**
 * @ClassName 项目应用查询对象
 * @Description TODO
 * @Author chesijian
 * @Date 2021/3/24 11:34
 * @Version 1.0
 */
@Data
public class ProjApplicationQuery extends BaseQuery {
    private String district;//片区
    private String projType;//项目类型
    private String year;
    private String quarter;
    private String month;
}
