package com.jrsoft.business.modules.project.query;/**
 * Created by chesijian on 2021/6/9.
 */

import com.jrsoft.engine.base.query.BaseQuery;
import lombok.Data;

/**
 * @ClassName ProjectQuery
 * @Description TODO
 * @Author chesijian
 * @Date 2021/6/9 15:40
 * @Version 1.0
 */
@Data
public class ProjectQuery extends BaseQuery {

    private String projStatus;//项目状态
}
