package com.cloud.exception;

import org.springframework.http.HttpStatus;

/**
 * 
 * @author shahzad.hussain
 *
 */
public class MyAppException extends RuntimeException {

	private static final long serialVersionUID = -2109770668634776728L;

	public int code;
	public String message;
	public HttpStatus httpStatus;
	public Object responseObject;

	public MyAppException(String msg) {
		super(msg);
	}

	public MyAppException(String message, HttpStatus httpStatus) {
		this.message = message;
		this.code = httpStatus.value();
		this.httpStatus = httpStatus;
	}

	public MyAppException(String message, HttpStatus httpStatus, Object responseObject) {
		this.message = message;
		this.code = httpStatus.value();
		this.httpStatus = httpStatus;
		this.responseObject = responseObject;
	}

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}
}
