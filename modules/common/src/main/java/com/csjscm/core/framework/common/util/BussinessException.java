package com.csjscm.core.framework.common.util;

/**
 * 自定义异常类
 */
public class BussinessException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * 响应状态
	 */
	private String code;

	public BussinessException() {
		super();
	}
	
	
	public BussinessException(String message) {
		super(message);
	}
	
	/**
	 * 构造函数
	 * @param code
	 * @param message
	 * @param
	 */
	public BussinessException(String code, String message) {
		super(message);
		this.code = code;
	}
	
	/**
	 * 获取 响应状态
	 * @return 
	 */
	public String getCode() {
		return code;
	}

	/**
	 * 设置 响应状态
	 * @param 
	 */
	public void setCode(String code) {
		this.code = code;
	}
	
}
