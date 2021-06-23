package com.jrsoft.engine.common.query;/**
 * Created by chesijian on 2021/6/17.
 */

import com.jrsoft.engine.base.query.BaseQuery;
import lombok.Data;

/**
 * @ClassName UserQuery
 * @Description 用户查询对象
 * @Author chesijian
 * @Date 2021/6/17 13:54
 * @Version 1.0
 */
@Data
public class UserQuery extends BaseQuery{

    private String departUid;//部门主键如果为空默认查询当前登录人的组织下所有员工
    private String positionUid;//岗位主键
    private String roleUid;//角色主键
    private boolean ifContainChild = false;//是否递归查找子部门的用户，默认是false
    private boolean ifQueryPosition = true;//是否查询岗位信息
    private Boolean containQuit=true;//是否包含离职人员，默认是true
}
