package com.lyh.ccqutil.model;

import java.io.Serializable;

import com.lyh.ccqutil.model.utils.ErrorCode;

public class Response<T> implements Serializable {

	private static final long serialVersionUID = 6406375278952464788L;
	private T detailInfo;
	private String message;
	private String errorCode;

	public Response(T detail) {
		if (detail == null) {
			setCode(ErrorCode.NOT_FOUND_RESULT);
		} else {
			setCode(ErrorCode.SUCCESS);
		}
		this.detailInfo = detail;
	}

	public void setCode(ErrorCode errorCode) {
		this.errorCode = errorCode.getCode();
		this.message = errorCode.getMessage();
	}

	public Response() {
	}

	public Response(ErrorCode errorCode) {
		setCode(errorCode);
	}

	/**
	 * @return the detailInfo
	 */
	public T getDetailInfo() {
		return detailInfo;
	}

	/**
	 * @param detailInfo
	 *            the detailInfo to set
	 */
	public void setDetailInfo(T detailInfo) {
		if (detailInfo == null) {
			setCode(ErrorCode.NOT_FOUND_RESULT);
		} else {
			setCode(ErrorCode.SUCCESS);
		}
		this.detailInfo = detailInfo;
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

	/**
	 * @return the errorCode
	 */
	public String getErrorCode() {
		return errorCode;
	}

	/**
	 * @param errorCode
	 *            the errorCode to set
	 */
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
}
