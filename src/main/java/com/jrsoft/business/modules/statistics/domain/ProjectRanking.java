package com.jrsoft.business.modules.statistics.domain;


import com.jrsoft.engine.base.domain.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 项目排行对象
 *
 * @author 车斯剑
 * @create 2019-11-12 17:21
 **/
@Getter
@Setter
public class ProjectRanking extends BaseEntity {

	@ApiModelProperty("项目名称")
	private String projName;
	@ApiModelProperty("总问题数")
	private String totalNum;
	@ApiModelProperty("完成数")
	private String finishNum;
	@ApiModelProperty("整改完结率")
	private Double percent;
	@ApiModelProperty("检查率")
	private Double checkPercent;
	@ApiModelProperty("合格率")
	private Double passPercent;
	@ApiModelProperty("工作率")
	private Double workPercent;
	private Double finishPercent;

}