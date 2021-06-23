package com.jrsoft.engine.exception;

/**
 * 
 * @description
 * @author 大雄
 * @date 2017年1月18日下午3:29:10
 */
public class EngineIllegalArgumentException extends EngineException {
	/**
	 * @description
	 * @author 大雄
	 * @date 2017年1月18日下午3:29:12
	 */
	private static final long serialVersionUID = 1L;
	public EngineIllegalArgumentException(String message) {
		super(message,EngineException.ERRCODE_ILLEGALARG);
		// TODO Auto-generated constructor stub
	}
	public EngineIllegalArgumentException(String message,int errcode) {
		super(message);
		this.errcode = EngineException.ERRCODE_ILLEGALARG;
		// TODO Auto-generated constructor stub
	}
	public EngineIllegalArgumentException(String message, Throwable cause) {
	    super(message, cause);
	  }
}
