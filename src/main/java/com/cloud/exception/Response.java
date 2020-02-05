/*************************************************************************
* 
* SATIN CREDITCARE NETWORK LIMITED CONFIDENTIAL
* __________________
* 
*  [2018] SATIN CREDITCARE NETWORK LIMITED
*  All Rights Reserved.
* 
* NOTICE:  All information contained herein is, and remains the property of SATIN CREDITCARE NETWORK LIMITED, and
* The intellectual and technical concepts contained herein are proprietary to SATIN CREDITCARE NETWORK LIMITED
* and may be covered by India and Foreign Patents, patents in process, and are protected by trade secret or copyright law.
* Dissemination of this information or reproduction of this material is strictly forbidden unless prior written permission
* is obtained from SATIN CREDITCARE NETWORK LIMITED.
*/
/**
 * 
 */
package com.cloud.exception;

import org.springframework.http.HttpStatus;

/**
 * @author shahzad.hussain
 *
 */
public class Response {

	private HttpStatus status;
	private int code;
	private String message;
	private Object response;

	public Response() {
	}

	public Response(String message, HttpStatus status) {
		this.status = status;
		this.code = status.value();
		this.message = message;
	}

	public Response(String message, Object response, HttpStatus status) {
		this.status = status;
		this.code = status.value();
		this.message = message;
		this.response = response;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public Object getResponse() {
		return response;
	}

}
