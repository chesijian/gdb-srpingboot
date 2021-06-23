package com.jrsoft.engine.model;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @description
 * @author 大雄
 * @date 2017年3月31日下午3:23:05
 */
public interface BaseModel extends Serializable{

	public static final long serialVersionUID = 1L;

	@ApiModelProperty(value="主键",name="id",hidden = true)
	public String getId() ;
	public void setId(String id) ;
	@ApiModelProperty(value="组织主键",name="company",hidden = true)
	 public String getCompany();
		public void setCompany(String company) ;
	@ApiModelProperty(value="子组织主键",name="subCompany",hidden = true)
		public String getSubCompany();

		public void setSubCompany(String subCompany);

	@ApiModelProperty(value="创建人",name="createUser",hidden = true)
	public String getCreateUser();

	public void setCreateUser(String createUser);

	@ApiModelProperty(value="修改人",name="updateUser",hidden = true)
	public String getUpdateUser();

	public void setUpdateUser(String updateUser);

	@ApiModelProperty(value="创建时间",name="createTime",hidden = true)
	public Date getCreateTime();

	public void setCreateTime(Date createTime);

	@ApiModelProperty(value="修改时间",name="updateTime",hidden = true)
	public Date getUpdateTime();

	public void setUpdateTime(Date updateTime);

	public void doBeforeInsert();
	public void doBeforeUpdate();





}
