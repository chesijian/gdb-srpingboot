package com.jrsoft.business.modules.construction.domain;


import com.jrsoft.engine.base.domain.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 巡检
 *
 * @author Blueeyedboy
 * @create 2018-10-12 17:21
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
/*@Entity
@Table(name = "T_POLLING_ITEM")*/
public class Polling extends BaseEntity {

	@ApiModelProperty(value="项目id",name="projUid",required=true,dataType="String")
//	@Column(name = "PROJ_UID_", length = 36)
	private String projUid;

	@ApiModelProperty(value="巡检类型",name="pollingType",required=true,dataType="String")
//	@Column(name = "POLLING_TYPE_", length = 50)
	private String pollingType;

	@ApiModelProperty(value="巡检名称",name="name",required=true,dataType="String")
//	@Column(name = "NAME_", length = 50)
	private String name;

	@ApiModelProperty(value="巡检日期",name="pollingDate",required=true,dataType="String")
//	@Column(name = "POLLING_DATE_")
	private String pollingDate;

	@ApiModelProperty(value="关联的巡检项",name="inspectStr",required=true,dataType="String")
//	@Column(name = "INSPECT_STR_")
	private String inspectStr;

	@ApiModelProperty(value="排序",name="sort",required=true,dataType="int")
//	@Column(name = "SORT_")
	private Integer sort;

	@ApiModelProperty(value="得分",name="score",required=true,dataType="Double")
//	@Column(name = "SCORE_")
	private Double score;

}