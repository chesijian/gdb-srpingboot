package com.jrsoft.engine.exception;

public class EngineException extends RuntimeException {
	public static int ERRCODE_EXCEPTION = 500;
	public static int ERRCODE_ENGINE = 500001;
	public static int ERRCODE_ILLEGALARG = 500002;
	public static int ERRCODE_JSONPRASE = 500003;
	public static int ERRCODE_JWT_EXPIRED = 500004;
	public static int ERRCODE_JWT_MALFORMMED = 500005;
	public static int ERRCODE_ILLEGAL_CHARACTERS = 500006;
	public static int ERRCODE_UNAUTH = 403001;
	public static int ERRCODE_UNAUTH_INVALID = 403002;
	public static int ERRCODE_UNAUTH_FORBIDDEN = 403003;
	public static int ERRCODE_UNAUTH_TOKEN_NOTFIND = 403004;//在redis没有找到token
	public static int ERRCODE_LICENSE_NOT_ADD_USER = 403901;
	public static int ERRCODE_NOT_FIND = 404001;
	public static int ERRCODE_ILLEGALARG_LICENSE = 403100;

	public  int errcode = ERRCODE_ENGINE;

	/**
	 * @description
	 * @author 大雄
	 * @date 2017年1月18日下午1:49:32
	 */
	private static final long serialVersionUID = 1L;
	public EngineException(String message, Throwable cause) {
	    super(message, cause);
	  }

	  public EngineException(String message) {
	    super(message);
	  }
	public EngineException(String message, int errcode) {
		super(message);
		this.errcode = errcode;
	}


	public int getErrcode() {
		return errcode;
	}

	public void setErrcode(int errcode) {
		this.errcode = errcode;
	}
}
