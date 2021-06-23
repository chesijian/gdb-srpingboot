package com.jrsoft.business.modules.construction.domain;

import com.jrsoft.engine.base.domain.BaseEntity;
import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.util.List;

/**
 * Created by chesijian on 2018/10/27.
 */
@Data
@Alias("CheckItem")
public class CheckItem extends BaseEntity {
    String id;
    String itemNo;
    String sign;
    String itemName;
    String type;
    String checkRequire;
    String parentUid;
    String pId;
    String projUid;
    String formType;
    List<CheckItem> children;
    int sort;
    boolean flag;
    boolean leaf;
    int totalCount;
    String lb;
    int projType;
    String projTypeName;
    String typeName;
    int minSample;
    String projectType;
    String yeTai;

    //关联巡检任务用到的属性
    String uid;
    String pollingUid;//巡检任务id
    Double score;//得分
    Integer gradeStatus;//评分状态
    String rootParentUid;

}
