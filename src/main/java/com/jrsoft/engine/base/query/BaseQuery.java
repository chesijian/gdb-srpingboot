package com.jrsoft.engine.base.query;/**
 * Created by chesijian on 2021/3/18.
 */

import com.jrsoft.engine.common.utils.RoleUtil;
import com.jrsoft.engine.common.utils.SessionUtil;
import lombok.Data;

/**
 * @ClassName BaseQuery
 * @Description TODO
 * @Author chesijian
 * @Date 2021/3/18 10:24
 * @Version 1.0
 */
@Data
public class BaseQuery {
    private Integer pageIndex =1;
    private Integer pageSize=10;
    private String search;

    public String getCompanyUid() {
        return SessionUtil.getCompanyUid();
    }
    public String getUserUid(){
        return SessionUtil.getUserUid();
    }
    public String getUserName(){
        return SessionUtil.getUserName();
    }
    public Integer getStart() {
        return (pageIndex-1)*pageSize;
    }

    public boolean getIsCompanyAdmin() {
        boolean isCompanyAdmin = RoleUtil.isCompanyAdmin();
        return isCompanyAdmin;
    }


}
