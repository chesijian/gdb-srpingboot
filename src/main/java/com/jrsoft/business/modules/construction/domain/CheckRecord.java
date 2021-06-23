package com.jrsoft.business.modules.construction.domain;

import com.jrsoft.engine.base.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by chesijian on 2018/10/27.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
//@Entity(name = "T_INSPECT_RECORD")
public class CheckRecord extends BaseEntity {
    private String projUid;
    private String sn;
    private String inspectItemUid;
    private String inspectPartUid;
    private String inspectDrawingUid;
    private String inspectItemName;
    private String inspectPartName;
    private String rectifyPrincipalUid;
    private String desc;
    private String checkType;
    private String dataType;
    private String formType;
    private String projType;//项目类型
    private String measureMarker;
    private String measureUid; //测量点
    private String measureValue; //实测值
    private String checkRequire; //规范要求
    private String lb; //类别
    private String plandetUid; //计划明细id
    private String planUid; //计划id
    private Integer minSample;//最小抽样
    private Float designValue;//设计值
    private Integer status; //状态
    private Integer sort;






}
