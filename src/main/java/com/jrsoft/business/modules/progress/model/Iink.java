package com.jrsoft.business.modules.progress.model;


import com.jrsoft.engine.base.domain.BaseEntity;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@AllArgsConstructor
@NoArgsConstructor
//@Entity(name = "GA_LINK")
public class Iink extends BaseEntity{
	
	
	@ApiModelProperty(value="进度id",name="code", dataType="String")
//	@Column(name = "CODE_", length = 36)
	private String code;		//CODE_
	
	@ApiModelProperty(value="来源",name="source", dataType="String")
//	@Column(name = "SOURCE_", length = 36)
	private String source;	//来源
	
	@ApiModelProperty(value="目标",name="target", dataType="String")
//	@Column(name = "TARGET_", length = 36)
	private String target;	//目标
	
	@ApiModelProperty(value="",name="type", dataType="Integer")
//	@Column(name = "TYPE_" )
	private String type;	
	
	@ApiModelProperty(value="",name="days", dataType="Double")
//	@Column(name = "DAYS_" )
	private String days;	//天数
	
	@ApiModelProperty(value="",name="scheduleUid", dataType="String")
//	@Column(name = "SCHEDULE_UID_", length = 36)
	private String scheduleUid;	
	
	@ApiModelProperty(value="",name="targetUid", dataType="String")
//	@Column(name = "TARGET_UID_", length = 36)
	private String targetUid;	
	
	@ApiModelProperty(value="",name="sourceUid", dataType="String")
//	@Column(name = "SOURCE_UID_", length = 36)
	private String sourceUid;	
	
	@ApiModelProperty(value="",name="projUid", dataType="String")
//	@Column(name = "PROJ_UID_", length = 36)
	private String projUid;	
	
	
	/*private String createUser;	//创建人
	private String createTime;	//创建时间
	private String updateUser;	//更新人
	private String updateTime;	//更新时间
	private String company;	//公司id
*/	
	
}
