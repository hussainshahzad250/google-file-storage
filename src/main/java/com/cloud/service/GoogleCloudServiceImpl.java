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
package com.cloud.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.Channels;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.cloud.config.GoogleStorageConfig;
import com.cloud.dto.FileResponse;
import com.cloud.exception.MyAppException;
import com.google.api.gax.paging.Page;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.BucketInfo;
import com.google.cloud.storage.CopyWriter;
import com.google.cloud.storage.Storage;

/**
 * @author shahzad.hussain
 *
 */
@Service
class GoogleCloudServiceImpl implements GoogleCloudService {

	private static final Logger logger = LoggerFactory.getLogger(GoogleCloudServiceImpl.class);

	private static final String SEPARATOR = "/";

	private static final String FORM_16 = "FORM_16";
	private static final String ID_CARD = "ID_CARD";
	private static final String SALARY_SLIP = "SALARY_SLIP";
	private static final String APPRAISAL_LETTER = "APPRAISAL_LETTER";
	private static final String EDUCATION_DETAILS = "EDUCATION_DETAILS";
	private static final String EXPERIENCE_LETTER = "EXPERIENCE_LETTER";

	@Autowired
	private GoogleStorageConfig config;

	@Override
	public String createBucket(String bucketName) throws IOException {
		try {
			Storage storage = config.initializeClient();
			Bucket bucket = storage.create(BucketInfo.of(bucketName));
			logger.info("Bucket %s created.", bucket.getName());
			return bucket.getName();
		} catch (Exception e) {
			logger.error("Exception occurs {}", e);
			throw new MyAppException("Invalid bucket name?", HttpStatus.BAD_REQUEST);
		}
	}

	@Override
	public List<FileResponse> listingBuckets() throws IOException {
		Storage storage = config.initializeClient();
		Page<Bucket> buckets = storage.list();
		List<FileResponse> response = new ArrayList<>();
		for (Bucket bucket : buckets.iterateAll()) {
			logger.debug("Bucket Name => {} ", bucket.getName());
			response.add(new FileResponse(bucket.getName()));
		}
		return response;
	}

	@Override
	public Bucket getBucket(String bucketName) throws IOException {
		Storage storage = config.initializeClient();
		Bucket bucket = storage.get(bucketName);
		if (bucket == null)
			throw new MyAppException("Bucket doesn't exist:", HttpStatus.NOT_FOUND);
		return bucket;
	}

	@Override
	public String uploadFile(String bucketName, String fileName, InputStream inputStream, String contentType)
			throws IOException {
		Bucket bucket = getBucket(bucketName);
		Blob blob = bucket.create(fileName, inputStream, contentType);
		logger.info("Blob :" + blob);
		return blob.getSelfLink();
	}

	@Override
	public String uploadFileToFolder(String bucketName, String userId, String fileName, InputStream inputStream,
			String contentType, String fileCategory) throws IOException {
		Bucket bucket = getBucket(bucketName);
		if (SALARY_SLIP.equalsIgnoreCase(fileCategory) || APPRAISAL_LETTER.equalsIgnoreCase(fileCategory)
				|| FORM_16.equalsIgnoreCase(fileCategory)) {
			fileName = userId + SEPARATOR + LocalDate.now().getYear() + SEPARATOR + fileName;
		} else if (EDUCATION_DETAILS.equalsIgnoreCase(fileCategory)) {
			fileName = userId + SEPARATOR + fileCategory + SEPARATOR + fileName;
		} else if (EXPERIENCE_LETTER.equalsIgnoreCase(fileCategory)) {
			fileName = userId + SEPARATOR + fileCategory + SEPARATOR + fileName;
		} else if (ID_CARD.equalsIgnoreCase(fileCategory)) {
			fileName = userId + SEPARATOR + fileCategory + SEPARATOR + fileName;
		} else
			fileName = userId + SEPARATOR + fileName;
		Blob blob = bucket.create(fileName, inputStream, contentType);
		logger.info("Blob Link:" + blob.getMediaLink());
		return blob.getSelfLink();
	}

	@Override
	public byte[] downloadFile(String bucketName, String fileName) throws IOException {
		Storage storage = config.initializeClient();
		Blob blob = storage.get(BlobId.of(bucketName, fileName));
		if (blob == null)
			throw new MyAppException("file doesn't exist:", HttpStatus.NOT_FOUND);
		logger.debug("Content Type => {}", blob.getContentType());
		InputStream inputStream = Channels.newInputStream(blob.reader());
		return IOUtils.toByteArray(inputStream);
	}

	@Override
	public byte[] downloadFileFromFolder(String bucketName, String fileName, String folderName) throws IOException {
		Storage storage = config.initializeClient();
		Blob blob = storage.get(BlobId.of(bucketName, folderName + SEPARATOR + fileName));
		if (blob != null) {
			logger.info(blob.getContentType());
			InputStream inputStream = Channels.newInputStream(blob.reader());
			return IOUtils.toByteArray(inputStream);
		}
		return null;
	}

	@Override
	public List<FileResponse> fetchBucketFolder(String bucketName, String userDirectory) throws IOException {
		Storage storage = config.initializeClient();
		Page<Blob> blobs = storage.list(bucketName);
		if (blobs == null)
			throw new MyAppException("User not Found", HttpStatus.NOT_FOUND);
		List<FileResponse> response = new ArrayList<>();
		for (Blob blob : blobs.iterateAll()) {
			if (!StringUtils.isEmpty(userDirectory) && blob.getName().startsWith(userDirectory)) {
				response.add(new FileResponse(blob.getName(), blob.getSelfLink()));
			}
		}
		return response;
	}

	@Override
	public void moveFile(String sourceBucket, String destBucket) throws IOException {
		Storage storage = config.initializeClient();
		Page<Blob> blobs = storage.list(sourceBucket);
		if (blobs != null) {
			LocalDate date = LocalDate.now();
			for (Blob blob : blobs.iterateAll()) {
				String fileName = blob.getName();
				String dir = blob.getName().substring(0, fileName.indexOf("-"));
				CopyWriter copyWriter = blob.copyTo(destBucket,
						dir + SEPARATOR + date.getYear() + SEPARATOR + fileName);
				Blob movedFile = copyWriter.getResult();
				logger.debug("file {} moved to {}" + movedFile.getName(), destBucket);
				if (blob.delete()) {
					logger.debug("File {} deleted from directory {}", fileName, sourceBucket);
				}
			}
		}
	}
}
