package com.jrsoft.engine.base.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;

/**
 * ${DESCRIPTION}
 *
 * @author Blueeyedboy
 * @create 2018-10-13 17:27
 **/
@Data
@AllArgsConstructor
@ApiModel(value="ReturnJson")
public class ReturnJson<T> implements Serializable{

	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value="返回状态",name="status",required=true,dataType="int",example="成功：200,无权限：403001，权限不合法：403002，访问被拒绝：403003，系统异常：500，自定义异常：500001，不合法参数：500002，json解析错误：500003，token超时：500004，token解析错误：500005，非法字符：500006")
	private int status;
	@ApiModelProperty(value="当返回状态不是200时，错误消息",name="errorMsg",required=true,dataType="int")
	private String errorMsg;
	@ApiModelProperty(value="当返回状态是200时，返回的数据",name="data",required=true,dataType="object")
	private T data;
	@ApiModelProperty(value="请求地址",name="path",dataType="String")
	private String uri;


	public  ReturnJson(){
		this.status = HttpServletResponse.SC_OK;
	}


	public  ReturnJson(int status,String errorMsg){
		super();
		this.status = status;
		this.errorMsg = errorMsg;
	}

	public  ReturnJson(int status,String errorMsg,T data){
		super();
		this.status = status;
		this.errorMsg = errorMsg;
		this.data = data;
	}

	public  ReturnJson(T data){
		super();
		this.status = HttpServletResponse.SC_OK;
		this.data = data;
	}

	public static ReturnJson error(String errorMsg){
		ReturnJson j = new ReturnJson(500,errorMsg);
		return j;
	}

	public static ReturnJson error(String errorMsg, Object data){
		ReturnJson j = new ReturnJson(500,errorMsg,data);
		return j;
	}

	public static ReturnJson error(String errorMsg, Object data, String uri){
		ReturnJson j = new ReturnJson(500,errorMsg,data,uri);
		return j;
	}

	public static ReturnJson error(int status, String errorMsg){
		ReturnJson j = new ReturnJson(status,errorMsg);
		return j;
	}


	public static ReturnJson error(int status, String errorMsg, Object data){
		ReturnJson j = new ReturnJson(status,errorMsg,data);
		return j;
	}

	public static ReturnJson error(int status, String errorMsg, Object data, String uri){
		ReturnJson j = new ReturnJson(status,errorMsg,data,uri);
		return j;
	}
	public static  <T> ReturnJson<T> ok(T data){
		ReturnJson<T> j = new ReturnJson<>( HttpServletResponse.SC_OK,data);
		return j;
	}

	@SuppressWarnings("unchecked")
	public static <T> T cast(Object obj) {
		return (T) obj;
	}

	public static ReturnJson ok(){
		ReturnJson j = new ReturnJson( HttpServletResponse.SC_OK,null);
		return j;
	}

	public ReturnJson(int status,T data){
		this.status = status;
		this.data = data;
	}


}
