package com.jrsoft.engine.impl.persistence.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.jrsoft.engine.common.utils.CommonUtil;
import com.jrsoft.engine.common.utils.SessionUtil;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * 所有实体类的基类
 * @description
 * @author 大雄
 * @date 2017年3月31日下午2:51:43
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

public class BaseModelEntity implements Serializable {
	/**
	 * @description
	 * @author 大雄
	 * @date 2017年3月31日下午2:43:44
	 */
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value="主键",name="id",hidden = true)
	protected String id;
	@ApiModelProperty(value="组织主键",name="company",hidden = true)
	protected String company;
	@ApiModelProperty(value="子组织主键",name="subCompany",hidden = true)
	protected String subCompany;
	@ApiModelProperty(value="创建人",name="createUser",hidden = true)
	protected String createUser;
	@ApiModelProperty(value="修改人",name="updateUser",hidden = true)
	protected String updateUser;
	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value="创建时间",name="createTime",hidden = true)
	protected Date createTime;
	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value="修改时间",name="updateTime",hidden = true)
	protected Date updateTime;

	public BaseModelEntity(){
		//setId(CommonUtil.getUUID());
	}

	public String getId() {
		//System.out.println("-----------------"+this.id);
		return this.id;
	}


	public void setId(String id) {
		this.id = id;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getSubCompany() {
		return subCompany;
	}
	public void setSubCompany(String subCompany) {
		this.subCompany = subCompany;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}


	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public void doBeforeInsert(){

		if(!CommonUtil.ifNotEmpty(this.id)){
			this.id = CommonUtil.getUUID();
		}
		if(createTime == null){
			this.createTime = CommonUtil.getDate();
		}
		if(!CommonUtil.ifNotEmpty(this.createUser)){
			this.createUser = SessionUtil.getUserUid();
		}
		if(updateTime == null){
			this.updateTime = CommonUtil.getDate();
		}
		if(!CommonUtil.ifNotEmpty(updateUser)){
			this.updateUser = SessionUtil.getUserUid();
		}
		//System.out.println(SessionUtil.getCompanyUid()+"---this.subCompany--------"+SessionUtil.getSubCompanyUid());
		if(!CommonUtil.ifNotEmpty(this.subCompany)){
			this.subCompany = SessionUtil.getSubCompanyUid();
		}
		/**/
		if(!CommonUtil.ifNotEmpty(company)){
			this.company = SessionUtil.getCompanyUid();
		}
		//System.out.println("his.id-------"+this.id);

	}

	public void doBeforeUpdate(){


		this.updateTime = CommonUtil.getDate();

		if(!CommonUtil.ifNotEmpty(updateUser)){
			this.updateUser = SessionUtil.getUserUid();
		}
	}
	@JsonIgnore
	public void setPersistenState(){

	}




}
