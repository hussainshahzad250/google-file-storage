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

package com.cloud.exception;

import java.io.EOFException;
import java.io.FileNotFoundException;

import javax.net.ssl.SSLHandshakeException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.cloud.storage.StorageException;

/**
 * 
 * @author shahzad.hussain
 *
 */
@ControllerAdvice
public class GlobalExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	private static final String ERROR_MSG = "Something went Wrong, try Later";

	@ExceptionHandler(value = MyAppException.class)
	@ResponseBody
	public ResponseEntity<Response> handleMyAppException(MyAppException ex) {
		logger.info("MyApp Exception occurs => {}", ex);
		return new ResponseEntity<>(new Response(ex.message, ex.getHttpStatus()), ex.getHttpStatus());
	}

	@ExceptionHandler(value = FileNotFoundException.class)
	@ResponseBody
	public ResponseEntity<Object> handleFileNotFoundException(FileNotFoundException ex) {
		logger.info("FileNotFound Exception occurs => {}", ex);
		return new ResponseEntity<>(new Response("File does not exist", HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(value = StorageException.class)
	@ResponseBody
	public ResponseEntity<Object> handleFileNotFoundException(StorageException ex) {
		logger.info("StorageException occurs => {}", ex);
		return new ResponseEntity<>(new Response(ex.getMessage(), HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
	@ResponseBody
	public ResponseEntity<Object> handleFileNotFoundException(HttpRequestMethodNotSupportedException ex) {
		logger.info("HttpRequestMethodNotSupportedException occurs => {}", ex);
		return new ResponseEntity<>(new Response(ex.getMessage(), HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = MissingServletRequestParameterException.class)
	@ResponseBody
	public ResponseEntity<Object> handleFileNotFoundException(MissingServletRequestParameterException ex) {
		logger.info("MissingServletRequestParameterException Exception occurs => {}", ex);
		return new ResponseEntity<>(new Response(ex.getMessage(), HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = SSLHandshakeException.class)
	@ResponseBody
	public ResponseEntity<Response> handleSSLHandshakeException(SSLHandshakeException ex) {
		logger.info("SSLHandshake Exception occurs => {}", ex);
		return new ResponseEntity<>(new Response(ERROR_MSG, HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = EOFException.class)
	@ResponseBody
	public ResponseEntity<Object> handleEOFException(EOFException ex) {
		logger.info("EOF Exception occurs => {}", ex);
		return new ResponseEntity<>(new Response(ERROR_MSG, HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handlerException(Exception ex) {
		logger.error("Exception  Occured-:{}", ex);
		return new ResponseEntity<>(new Response(ERROR_MSG, HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
	}

}