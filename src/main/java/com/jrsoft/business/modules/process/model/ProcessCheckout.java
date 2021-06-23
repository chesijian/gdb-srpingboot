package com.jrsoft.business.modules.process.model;


import com.jrsoft.engine.base.domain.BaseEntity;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
//@Entity(name = "T_PROCESS_CHECKOUT")
public class ProcessCheckout extends BaseEntity{
	
	@ApiModelProperty(value="编码",name="code", dataType="String")
//	@Column(name = "CODE_")
	private String code;
	
	@ApiModelProperty(value="检验批名称",name="name", dataType="String")
//	@Column(name = "NAME_")
	private String name;
	
	@ApiModelProperty(value="区域名称",name="areaFullName", dataType="String")
//	@Column(name = "AREA_FULL_NAME_")
	private String areaFullName;
	
	@ApiModelProperty(value="说明",name="remark", dataType="String")
//	@Column(name = "REMARK_")
	private String remark;
	
	@ApiModelProperty(value="工序数",name="processCount", dataType="String")
//	@Column(name = "PROCESS_COUNT_")
	private Integer processCount;
	
	@ApiModelProperty(value="任务数",name="taskCount", dataType="String")
//	@Column(name = "TASK_COUNT_")
	private Integer taskCount;
	
	
	@ApiModelProperty(value="问题及记录数",name="issueCount", dataType="String")
//	@Column(name = "ISSUE_COUNT_")
	private Integer issueCount;
	
	
	@ApiModelProperty(value="排序",name="sort", dataType="int")
//	@Column(name = "SORT_")
	private Integer sort;
	
	@ApiModelProperty(value="项目id",name="projUid", dataType="String")
//	@Column(name = "PROJ_UID_")
	private String projUid;
	
	
	@ApiModelProperty(value="区域id",name="partUid", dataType="String")
//	@Column(name = "PART_UID_")
	private String partUid;
	
	
	@ApiModelProperty(value="区域上级id",name="parentUid", dataType="String")
//	@Column(name = "PARENT_UID_")
	private String parentUid;
	
	
}
