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
package com.cloud.controller;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.io.FileUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cloud.dto.FileResponse;
import com.cloud.exception.MyAppException;

@RestController
public class TestController {

	@GetMapping(value = "listFolder")
	public ResponseEntity<Object> listFolder(@RequestParam(name = "employeeId", required = true) String employeeId)
			throws IOException {
		Path path = Paths.get("D:\\NACH_FILE\\satin\\employees\\" + employeeId);
		if (!Files.isDirectory(path)) {
			throw new MyAppException("Employee doesn't exist", HttpStatus.NOT_FOUND);
		}
		Stream<Path> walk = null;
		try {
			walk = Files.walk(path);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(
				walk.filter(Files::isDirectory).map(o -> toResponse(o)).collect(Collectors.toList()), HttpStatus.OK);
	}

	@GetMapping(value = "listFile")
	public ResponseEntity<Object> listFile(@RequestParam(name = "employeeId", required = true) String employeeId)
			throws IOException {
		Path path = Paths.get("D:\\NACH_FILE\\satin\\employees\\" + employeeId);
		if (!Files.isDirectory(path)) {
			throw new MyAppException("Employee doesn't exist", HttpStatus.NOT_FOUND);
		}
		Stream<Path> walk = null;
		try {
			walk = Files.walk(path);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(
				walk.filter(Files::isRegularFile).map(o -> toResponse(o)).collect(Collectors.toList()), HttpStatus.OK);
	}

	private FileResponse toResponse(Path o) {
		FileResponse fileResponse = new FileResponse();
		Path fileName = o.toAbsolutePath();
		fileResponse.fileName = fileName.toString();
		return fileResponse;
	}

	@GetMapping(value = "/download/customers.pdf")
	public ResponseEntity<InputStreamResource> excelCustomersReport() throws IOException {
		Path path = Paths.get("D:\\NACH_FILE\\satin\\employees\\58066\\2020\\increment-letter\\incrementletter.pdf");
		File file = path.toFile();
		ByteArrayInputStream in = retrieveByteArrayInputStream(file);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "attachment; filename=customers.pdf");
		return ResponseEntity.ok().headers(headers).body(new InputStreamResource(in));
	}

	public static ByteArrayInputStream retrieveByteArrayInputStream(File file) throws IOException {
		return new ByteArrayInputStream(FileUtils.readFileToByteArray(file));
	}
}
