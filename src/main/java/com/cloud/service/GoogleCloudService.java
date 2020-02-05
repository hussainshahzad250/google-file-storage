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
import java.util.List;

import com.cloud.dto.FileResponse;
import com.google.cloud.storage.Bucket;

/**
 * @author shahzad.hussain
 *
 */
public interface GoogleCloudService {

	String createBucket(String bucketName) throws IOException;

	List<FileResponse> listingBuckets() throws IOException;

	Bucket getBucket(String bucketName) throws IOException;

	String uploadFile(String bucketName, String fileName, InputStream inputStream, String fileType) throws IOException;

	String uploadFileToFolder(String bucketName, String folderName, String fileName, InputStream inputStream,
			String contentType, String fileCategory) throws IOException;

	byte[] downloadFile(String bucketName, String fileName) throws IOException;

	byte[] downloadFileFromFolder(String bucketName, String fileName, String folderName) throws IOException;

	List<FileResponse> fetchBucketFolder(String bucketName, String userDirectory) throws IOException;

	void moveFile(String sourceBucket, String destBucket) throws IOException;
}
