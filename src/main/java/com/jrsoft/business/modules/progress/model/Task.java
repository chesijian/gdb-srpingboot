package com.jrsoft.business.modules.progress.model;

import java.util.List;
import java.util.Map;


import com.jrsoft.engine.base.domain.BaseEntity;

import com.jrsoft.engine.common.utils.CommonUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * 进度任务实体
 * @author eric
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "GA_TASK")
public class Task extends BaseEntity{
	

	@ApiModelProperty(value="进度id",name="code", dataType="String")
	@Column(name = "CODE_", length = 36)
	private String code;		//CODE_
	
	@ApiModelProperty(value="名称",name="text", dataType="String")
	@Column(name = "TEXT_", length = 200)
	private String text;		
	
	@ApiModelProperty(value="计划开始日期",name="startDate", dataType="Date")
	@Column(name = "START_DATE_")
	private String startDate;	//
	
	@ApiModelProperty(value="计划结束日期",name="endDate", dataType="Date")
//	@Column(name = "END_DATE_")
	private String endDate;	
	
	@ApiModelProperty(value="计划工期",name="duration", dataType="Double")
//	@Column(name = "DURATION_")
	private String duration;	
	
	@ApiModelProperty(value="实际开始日期",name="actStartDate", dataType="Date")
//	@Column(name = "ACT_START_DATE_")
	private String actStartDate;	
	
	@ApiModelProperty(value="实际结束日期",name="actEndDate", dataType="Date")
//	@Column(name = "ACT_END_DATE_")
	private String actEndDate;	
	
	@ApiModelProperty(value="实际结束工期",name="actDuration", dataType="Double")
//	@Column(name = "ACT_DURATION_")
	private String actDuration;
	
	@ApiModelProperty(value="进度",name="progress", dataType="Double")
//	@Column(name = "PROGRESS_")
	private String progress;	
	
	
	@ApiModelProperty(value="父节点id",name="parent", dataType="String")
//	@Column(name = "PARENT_")
	private String parent; 
	
	
	@ApiModelProperty(value="父节点主键",name="parentUid", dataType="String")
//	@Column(name = "PARENT_UID_")
	private String parentUid; 
	
	
	@ApiModelProperty(value="项目id",name="projUid", dataType="String")
//	@Column(name = "PROJ_UID_")
	private String projUid;	
	
	
	@ApiModelProperty(value="进度主键",name="scheduleUid", dataType="String")
//	@Column(name = "SCHEDULE_UID_")
	private String scheduleUid;
	
	
	@ApiModelProperty(value="备注(进度成果)",name="remark", dataType="String")
//	@Column(name = "REMARK_")
	private String remark;	
	
	
	@ApiModelProperty(value="审批人",name="approver", dataType="String")
//	@Column(name = "APPROVER_")
	private String approver;	
	
	@ApiModelProperty(value="审批名称",name="approverName", dataType="String")
//	@Column(name = "APPROVER_NAME_")
	private String approverName;	
	
	@ApiModelProperty(value="负责人",name="assignee", dataType="String")
//	@Column(name = "ASSIGNEE_")
	private String assignee;	
	
	@ApiModelProperty(value="负责人名称",name="assigneeName", dataType="String")
//	@Column(name = "ASSIGNEE_NAME_")
	private String assigneeName;	
	
	
	@ApiModelProperty(value="1表示工作日，2表示节假日",name="type", dataType="String")
//	@Column(name = "TYPE_")
	private String type;	
	
	
	/*private String createUser;	//创建人
	private String createTime;	//创建时间
	private String updateUser;	//更新人
	private String updateTime;	//更新时间
	private String company;	//公司id
*/	
	@ApiModelProperty(value="审批状态",name="status", dataType="String")
//	@Column(name = "STATUS_")
	private Integer status;	//审批状态: 1:通过; 2:不通过
	
	@ApiModelProperty(value="排序",name="sort", dataType="Integer")
//	@Column(name = "SORT_")
	private Integer sort;
	
	
	@ApiModelProperty(value="保存工序任务全名称",name="sort", dataType="Integer")
//	@Column(name = "FULL_NAME_")
	private String fullName;

	@ApiModelProperty(value="排序",name="sort", dataType="String")
//	@Column(name = "LEAF_")
	private Boolean leaf;

	@ApiModelProperty(value="任务编码",name="coding", dataType="String")
//	@Column(name = "CODING_")
	private String coding;

	@ApiModelProperty(value="任务类型",name="taskType", dataType="String")
//	@Column(name = "TASK_TYPE_")
	private String taskType;


	@ApiModelProperty(value="任务专业",name="taskSpecialty", dataType="String")
//	@Column(name = "TASK_SPECIALTY_")
	private String taskSpecialty;

	@ApiModelProperty(value="任务标准",name="taskStandard", dataType="String")
//	@Column(name = "TASK_STANDARD_")
	private String taskStandard;

	@ApiModelProperty(value="权重比例",name="taskStandard", dataType="String")
//	@Column(name = "TASK_WEIGHT_")
	private String taskWeight;

	@ApiModelProperty(value="延期因素",name="taskStandard", dataType="String")
//	@Column(name = "DELAY_FACTORS_")
	private String delayFactors;

	@ApiModelProperty(value="问题风险",name="riskProblem", dataType="String")
//	@Column(name = "RISK_PROBLEM_")
	private String riskProblem;

	@ApiModelProperty(value="风险应对措施",name="riskCounterMeasure", dataType="String")
//	@Column(name = "RISK_COUNTER_MEASURE_")
	private String riskCounterMeasure;

	@ApiModelProperty(value="进度状态",name="riskCounterMeasure", dataType="String")
//	@Column(name = "PROGRESS_STATUS_")
	private String progressStatus;

	@ApiModelProperty(value="是否待关注",name="stayFocused", dataType="Integer")
//	@Column(name = "STAY_FOCUSED_")
	private Integer stayFocused;

	@ApiModelProperty(value="职能",name="function", dataType="String")
//	@Column(name = "FUNCTION_")
	private String function;

	@ApiModelProperty(value="最后上报时间",name="function", dataType="String")
//	@Column(name = "LAST_REPORT_DATE_")
	private String lastReportDate;

	@ApiModelProperty(value="审批意见",name="function", dataType="String")
//	@Column(name = "APPROVAL_OPINION_")
	private String approvalOpinion;

//	@Transient
	private List<Map<String, Object>> record;

//	@Transient
	private String parentPath;

//	@Transient
	private String projStatus;//项目状态

//	@Transient
	private int delayDays;

//	@Transient
	private String projName;//项目名称

//	@Transient
	private String projCode;//项目编号

	//工期偏差
//	@Transient
	private String offsetDuration;

	public String getStatusName() {
		if(status==null){
			return "未审";
		}else if(status==1){
			return "通过";
		}else if(status==2){
			return "不通过";
		}else{
			return "未审";
		}
	}

	public String getTaskStatusName() {
		if("停工".equals(projStatus)&&!"100".equals(progress)){
			return "暂停中";
		}else if(CommonUtil.ifNotEmpty(actEndDate)){
			if(delayDays==0){
				return "正常";
			}else if(delayDays<0){
				return "提前";
			}else if(delayDays>0&&delayDays<=7){
				return "可控";
			}else{
				return "延误";
			}
		}else{
			Integer delayStart=startDate.compareTo(CommonUtil.getDateStr());
			if(delayStart>=0){
				return "未开始";
			}else if(delayDays<=0){
				return "正常";
			}else if(delayDays>0&&delayDays<=7){
				return "可控";
			}else{
				return "延误";
			}
		}

	}
}
