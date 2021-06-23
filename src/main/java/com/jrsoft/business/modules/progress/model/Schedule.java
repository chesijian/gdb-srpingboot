package com.jrsoft.business.modules.progress.model;


import com.jrsoft.engine.base.domain.BaseEntity;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
//@Entity(name = "GA_SCHEDULE")
public class Schedule extends BaseEntity{
	
	
	@ApiModelProperty(value="标题",name="title", dataType="String")
//	@Column(name = "TITLE_")
	private String title;
	
	
	@ApiModelProperty(value="计划开始日期",name="startDate", dataType="String")
//	@Column(name = "START_DATE_")
	private String startDate;
	
	@ApiModelProperty(value="计划完成日期",name="endDate", dataType="String")
//	@Column(name = "END_DATE_")
	private String endDate;
	
	@ApiModelProperty(value="计划工期",name="duration", dataType="String")
//	@Column(name = "DURATION_")
	private String duration;
	
	@ApiModelProperty(value="实际开始日期",name="actStartDate", dataType="String")
//	@Column(name = "ACT_START_DATE_")
	private String actStartDate;
	
	
	@ApiModelProperty(value="实际完成日期",name="actEndDate", dataType="String")
//	@Column(name = "ACT_END_DATE_")
	private String actEndDate;
	
	@ApiModelProperty(value="工期",name="actDuration", dataType="String")
//	@Column(name = "ACT_DURATION_")
	private String actDuration;
	
	@ApiModelProperty(value="进度",name="progress", dataType="String")
//	@Column(name = "PROGRESS_")
	private String progress;
	
	@ApiModelProperty(value="项目id",name="projUid", dataType="String")
//	@Column(name = "PROJ_UID_")
	private String projUid;
	
	@ApiModelProperty(value="版本",name="version", dataType="String")
//	@Column(name = "VERSION_")
	private String version;
	
	@ApiModelProperty(value="是否启用，1启用，其他未启用",name="enable", dataType="String")
//	@Column(name = "ENABLE_")
	private Integer enable;
	
	@ApiModelProperty(value="备注",name="remark", dataType="String")
//	@Column(name = "REMARK_")
	private String remark;

	@ApiModelProperty(value="父id",name="parentId", dataType="String")
//	@Column(name = "PARENT_ID_")
	private String parentId;

	@ApiModelProperty(value="调整原因",name="adjustReason", dataType="String")
//	@Column(name = "ADJUST_REASON_")
	private String adjustReason;
}
