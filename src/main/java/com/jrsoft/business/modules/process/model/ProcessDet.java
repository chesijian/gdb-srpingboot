package com.jrsoft.business.modules.process.model;


import com.jrsoft.engine.base.domain.BaseEntity;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
//@Entity(name = "T_PROCESS_DET")
public class ProcessDet extends BaseEntity{
	
	@ApiModelProperty(value="检查项id",name="code", dataType="String")
//	@Column(name = "PROCESS_ID_")
	private String processId;
	
	@ApiModelProperty(value="检查项id",name="code", dataType="String")
//	@Column(name = "PROCESS_TASK_ID_")
	private String processTaskId;
	
	@ApiModelProperty(value="检查项id",name="code", dataType="String")
//	@Column(name = "STATUS_")
	private String status;
	
	
}
