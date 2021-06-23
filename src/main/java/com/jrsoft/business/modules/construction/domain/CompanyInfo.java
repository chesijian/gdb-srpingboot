package com.jrsoft.business.modules.construction.domain;

import com.jrsoft.engine.base.domain.BaseEntity;
import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.util.List;

/**
 * Created by chesijian on 2018/10/27.
 */
@Data
@Alias("CompanyInfo")
public class CompanyInfo extends BaseEntity {
    String name;
    String mobileNo;
    String userName;
    String parentUid;
    String userId;
    String pwd;
    String verCode;
    int projType;
    String projTypeName;
    String typeName;
    int minSample;

    //关联巡检任务用到的属性
    String uid;
    String pollingUid;//巡检任务id
    Double score;//得分
    Integer gradeStatus;//评分状态
    String rootParentUid;

}
