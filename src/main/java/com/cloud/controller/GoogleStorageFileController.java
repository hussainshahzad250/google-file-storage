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
import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cloud.dto.FileResponse;
import com.cloud.exception.Response;
import com.cloud.service.GoogleCloudService;

/**
 * 
 * @author shahzad.hussain
 *
 */
@RestController
public class GoogleStorageFileController {

	private static final Logger logger = LoggerFactory.getLogger(GoogleStorageFileController.class);

	private static final String BUCKET_NAME = "satin-hrms";
	private static final String SATIN_HR_DOCS = "hr-temp";

	@Autowired
	private GoogleCloudService googleCloudService;

	@PostMapping(value = "/createBucket")
	public ResponseEntity<Object> createBucket(@RequestParam("bucketName") String bucketName) throws IOException {
		String bucket = googleCloudService.createBucket(bucketName);
		return new ResponseEntity<>(new Response("Bucket has been created", new FileResponse(bucket), HttpStatus.OK),
				HttpStatus.OK);
	}

	@GetMapping(value = "/listingBucket")
	public ResponseEntity<Object> listingBucket() throws IOException {
		List<FileResponse> listingBuckets = googleCloudService.listingBuckets();
		return new ResponseEntity<>(listingBuckets, HttpStatus.OK);
	}

	@PostMapping(value = "/uploadFile")
	public ResponseEntity<Object> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
		if (file.isEmpty())
			return new ResponseEntity<>(new Response("Please select attachement file", HttpStatus.BAD_REQUEST),
					HttpStatus.BAD_REQUEST);
		String uploadFile = googleCloudService.uploadFile(BUCKET_NAME, file.getOriginalFilename(),
				file.getInputStream(), file.getContentType());
		return new ResponseEntity<>(uploadFile, HttpStatus.OK);
	}

	@PostMapping(value = "/uploadFileToFolder")
	public ResponseEntity<Object> uploadFileToFolder(@RequestParam("file") MultipartFile file,
			@RequestParam("userId") String userId, @RequestParam("fileType") String fileCategory) throws IOException {
		if (file.isEmpty())
			return new ResponseEntity<>(new Response("Please select attachement file", HttpStatus.BAD_REQUEST),
					HttpStatus.BAD_REQUEST);
		String uploadFileToFolder = googleCloudService.uploadFileToFolder(BUCKET_NAME, userId,
				file.getOriginalFilename(), file.getInputStream(), file.getContentType(), fileCategory);
		return new ResponseEntity<>(uploadFileToFolder, HttpStatus.OK);
	}

	@GetMapping(value = "/downloadFile")
	public ResponseEntity<Object> downloadFile(@RequestParam("fileName") String fileName) throws IOException {
		byte[] media = googleCloudService.downloadFile(BUCKET_NAME, fileName);
		ByteArrayInputStream in = new ByteArrayInputStream(media);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "attachment; filename=" + fileName);
		return ResponseEntity.ok().headers(headers).body(new InputStreamResource(in));
	}

	@GetMapping(value = "/downloadFileFromFolder")
	public ResponseEntity<Object> downloadFileFromFolder(@RequestParam("fileName") String fileName,
			@RequestParam("folderName") String folderName) throws IOException {
		byte[] media = googleCloudService.downloadFileFromFolder(BUCKET_NAME, fileName, folderName);
		ByteArrayInputStream in = new ByteArrayInputStream(media);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "attachment; filename=" + fileName);
		return ResponseEntity.ok().headers(headers).body(new InputStreamResource(in));
	}

	@GetMapping(value = "/fetchUserData")
	public ResponseEntity<Object> fetchUserData(@RequestParam("userId") String userId) throws IOException {
		List<FileResponse> fetchBucketData = googleCloudService.fetchBucketFolder(BUCKET_NAME, userId);
		if (CollectionUtils.isEmpty(fetchBucketData))
			return new ResponseEntity<>(new Response("Details not Found", HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
		return new ResponseEntity<>(fetchBucketData, HttpStatus.OK);
	}

	@GetMapping(value = "/move")
	public ResponseEntity<Object> move() throws IOException {
		googleCloudService.moveFile(SATIN_HR_DOCS, BUCKET_NAME);
		return new ResponseEntity<>("OK", HttpStatus.OK);
	}
}
