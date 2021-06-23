package com.jrsoft.engine.base.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 返回分页总数
 *
 * @author Blueeyedboy
 * @create 2018-10-23 9:24
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class ReturnPageJson<T> extends ReturnJson<T> {

	public ReturnPageJson(){
		super();
	}

	public ReturnPageJson(int status,String errorMsg){
		super(status,errorMsg);
	}

	@ApiModelProperty(value="当返回分页总数",name="totalCount",required=true,dataType="object")
	private long totalCount;
}
