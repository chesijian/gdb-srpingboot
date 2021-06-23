package com.jrsoft.business.modules.process.model;

import java.io.Serializable;


import com.jrsoft.engine.base.domain.BaseEntity;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
//@Entity(name = "T_WORk_PART")
public class Area extends BaseEntity{
	
	@ApiModelProperty(value="名称",name="name", dataType="String")
//	@Column(name = "NAME_")
	private String name;
	
}
