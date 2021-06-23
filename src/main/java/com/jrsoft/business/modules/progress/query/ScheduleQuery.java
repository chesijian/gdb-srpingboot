package com.jrsoft.business.modules.progress.query;/**
 * Created by chesijian on 2021/3/18.
 */

import com.jrsoft.engine.base.query.BaseQuery;
import com.jrsoft.engine.common.utils.CommonUtil;
import lombok.Data;

/**
 * @ClassName ScheduleQuery
 * @Description TODO
 * @Author chesijian
 * @Date 2021/3/18 10:36
 * @Version 1.0
 */
@Data
public class ScheduleQuery extends BaseQuery {
    private String projUids;//项目id集合
    private String projType;//项目类型
    private String district;//片区
    private String assignee;//负责人
    private String status;//进度状态
    private String startDate;//计划开始
    private String endDate;//计划完工

    //项目id集合
    public String[] getProjIds() {
        if(CommonUtil.ifNotEmpty(projUids)){
            return projUids.split(",");
        }else{
            return null;
        }
    }
}
