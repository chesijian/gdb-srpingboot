package com.jrsoft.business.modules.process.model;


import com.jrsoft.engine.base.domain.BaseEntity;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
//@Entity(name = "T_PROCESS_TASK")
public class ProcessTask extends BaseEntity{
	
	
	@ApiModelProperty(value="编码",name="code", dataType="String")
//	@Column(name = "CODE_")
	private String code;
	
	
	@ApiModelProperty(value="项目id",name="projUid", dataType="String")
//	@Column(name = "PROJ_UID_")
	private String projUid;
	
	
	@ApiModelProperty(value="检查状态",name="status", dataType="String")
//	@Column(name = "STATUS_")
	private Integer status;
	
	
	@ApiModelProperty(value="区域id",name="areaUid", dataType="String")
//	@Column(name = "AREA_UID_")
	private String areaUid;
	
	
	@ApiModelProperty(value="区域名称",name="areaFullName", dataType="String")
//	@Column(name = "AREA_FULL_NAME_")
	private String areaFullName;
	
	
	@ApiModelProperty(value="检验批id",name="checkoutUid", dataType="String")
//	@Column(name = "CHECKOUT_UID_")
	private String checkoutUid;
	
	
	@ApiModelProperty(value="检验批名称",name="checkoutName", dataType="String")
//	@Column(name = "CHECKOUT_NAME_")
	private String checkoutName;
	
	
	@ApiModelProperty(value="工序id",name="processUid", dataType="String")
//	@Column(name = "PROCESS_UID_")
	private String processUid;
	
	
	@ApiModelProperty(value="工序名称",name="processName", dataType="String")
//	@Column(name = "PROCESS_NAME_")
	private String processName;
	
	
	@ApiModelProperty(value="负责人",name="principal", dataType="String")
//	@Column(name = "PRINCIPAL_")
	private String principal;
	
	
	@ApiModelProperty(value="检查人",name="inspect", dataType="String")
//	@Column(name = "INSPECT_")
	private String inspect;
	
	
	@ApiModelProperty(value="抽查人",name="spot", dataType="String")
//	@Column(name = "SPOT_")
	private String spot;
	
	
	@ApiModelProperty(value="施工状态(0:施工未开始; 1:施工中; 2:报验; 3:待检验; 4:已完成; 5:需返工)",name="constructionStatus", dataType="String")
//	@Column(name = "CONSTRUCTION_STATUS_")
	private Integer constructionStatus;
	
	
	@ApiModelProperty(value="1:启用; 0:禁用",name="enable", dataType="String")
//	@Column(name = "ENABLE_")
	private Integer enable;
	
	
	@ApiModelProperty(value="问题数",name="issueCount", dataType="String")
//	@Column(name = "ISSUE_COUNT_")
	private Integer issueCount;
	
	
	@ApiModelProperty(value="排序",name="sort", dataType="String")
//	@Column(name = "SORT_")
	private Integer sort;
	
	
	@ApiModelProperty(value="开始时间",name="startDate", dataType="String")
//	@Column(name = "START_DATE_")
	private String startDate;
	
	
	@ApiModelProperty(value="结束时间",name="endDate", dataType="String")
//	@Column(name = "END_DATE_")
	private String endDate;

	@ApiModelProperty(value="检查类型",name="sort", dataType="String")
//	@Column(name = "INSPECT_TYPE_")
	private Integer inspectType;
	
}
