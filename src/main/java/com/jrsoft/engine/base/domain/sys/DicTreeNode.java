package com.jrsoft.engine.base.domain.sys;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author Blueeyedboy
 * @create 2019-02-15 10:15 AM
 **/
@Data
public class DicTreeNode implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id;

	@ApiModelProperty(value="标题",name="text",required=true,dataType="String")
	private String text;

	@ApiModelProperty(value="编码",name="code",required=true,dataType="String")
	private String code;

	@ApiModelProperty(value="图标",name="icon",dataType="String")
	private String icon;

	@ApiModelProperty(value="是否叶子,true是叶子",name="leaf",dataType="boolean")
	private Boolean leaf;

	@ApiModelProperty(value="类型，1表示系统数据字典树，2表示业务数据字典树",name="type",required=true,dataType="String")
	private Integer type;

	@ApiModelProperty(value="是否启用，1表示启用，0表示未启用",name="enable",required=true,dataType="int")
	private Integer enable;

	@ApiModelProperty(value="排序",name="sort",required=true,dataType="float")
	private Double sort;

	@ApiModelProperty(value="父节点",name="parentId",dataType="String")
	private String parentId;

	@ApiModelProperty(value="父节点中文",name="parentText",dataType="String")
	private String parentText;

	@ApiModelProperty(value="备注",name="remark",dataType="String")
	private String remark;

	@ApiModelProperty(value="子节点",name="children")
	List<DicTreeNode> children;
}
