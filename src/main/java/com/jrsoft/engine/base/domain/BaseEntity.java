package com.jrsoft.engine.base.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.jrsoft.engine.common.utils.CommonUtil;
import com.jrsoft.engine.common.utils.SessionUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;


/**
 * 所有实体的基类
 *
 * @author Blueeyedboy
 * @create 2018-10-13 15:51
 **/
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//@ApiModel(value="实体基类")
public class BaseEntity implements Serializable {

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



	public void doBeforeInsert(){

		if(!CommonUtil.ifNotEmpty(this.id)){
			this.id = CommonUtil.getUUID();
			this.setId(id);
		}
		if(createTime == null){
			this.createTime = CommonUtil.getDate();
			this.setCreateTime(createTime);
		}
		if(!CommonUtil.ifNotEmpty(this.createUser)){
			this.createUser = SessionUtil.getUserUid();
			this.setCreateUser(createUser);
		}
		if(updateTime == null){
			this.updateTime = CommonUtil.getDate();
			this.setUpdateTime(updateTime);
		}
		if(!CommonUtil.ifNotEmpty(updateUser)){
			this.updateUser = SessionUtil.getUserUid();
			this.setUpdateUser(updateUser);
		}


	}

	public void doBeforeUpdate(){


		this.updateTime = CommonUtil.getDate();
		this.setUpdateTime(updateTime);

		if(!CommonUtil.ifNotEmpty(updateUser)){
			this.updateUser = SessionUtil.getUserUid();
			this.setUpdateUser(updateUser);
		}
	}


}
