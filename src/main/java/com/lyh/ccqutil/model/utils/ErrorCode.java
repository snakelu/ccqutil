package com.lyh.ccqutil.model.utils;

public enum ErrorCode {

	SUCCESS("0000", "成功"), PARAM_ERROR("1001", "参数错误"), REQUEST_INVALID("1002", "请求失效"), REQUEST_TIMEOUT("1003",
			"请求超时"), TOKEN_INVALID("1004", "token无效"), NOT_FOUND_RESULT("2001", "找不到结果"), FILE_FOMATTER_ERROR("3001",
					"文件格式错误"), SYSTEM_EXCEPTION("9999", "系统异常");

	private ErrorCode(String code, String message) {
		this.code = code;
		this.message = message;
	}

	private String code;

	private String message;

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code
	 *            the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message
	 *            the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
}
