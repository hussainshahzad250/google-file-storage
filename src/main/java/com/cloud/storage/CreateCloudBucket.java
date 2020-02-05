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
package com.cloud.storage;

import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.BucketInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

public class CreateCloudBucket {
	public static void main(String... args) throws Exception {

		// Instantiates a client
		Storage storage = StorageOptions.getDefaultInstance().getService();

		String bucketName = "hrms-loandost";
		createBucket(storage, bucketName);
	}

	/**
	 * CREATES THE NEW BUCKET
	 * 
	 * @param storage
	 * @param bucketName
	 */
	private static void createBucket(Storage storage, String bucketName) {
		Bucket bucket = storage.create(BucketInfo.of(bucketName));
		System.out.printf("Bucket %s created.%n", bucket.getName());
	}
}