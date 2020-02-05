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
package com.cloud.storage;

import java.util.Map;

import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.Storage.BucketGetOption;
import com.google.cloud.storage.StorageOptions;

/**
 * @author shahzad.hussain
 *
 */
public class BucketInfo {

	public static void main(String[] args) {
		Storage storage = StorageOptions.getDefaultInstance().getService();

		// The name of a bucket, e.g. "my-bucket"
		String bucketName = "hrms-satin";

		// Select all fields
		// Fields can be selected individually e.g. Storage.BucketField.NAME
		Bucket bucket = storage.get(bucketName, BucketGetOption.fields(Storage.BucketField.values()));

		// Print bucket metadata
		System.out.println("BucketName: " + bucket.getName());
		System.out.println("DefaultEventBasedHold: " + bucket.getDefaultEventBasedHold());
		System.out.println("DefaultKmsKeyName: " + bucket.getDefaultKmsKeyName());
		System.out.println("Id: " + bucket.getGeneratedId());
		System.out.println("IndexPage: " + bucket.getIndexPage());
		System.out.println("Location: " + bucket.getLocation());
		System.out.println("LocationType: " + bucket.getLocationType());
		System.out.println("Metageneration: " + bucket.getMetageneration());
		System.out.println("NotFoundPage: " + bucket.getNotFoundPage());
		System.out.println("RetentionEffectiveTime: " + bucket.getRetentionEffectiveTime());
		System.out.println("RetentionPeriod: " + bucket.getRetentionPeriod());
		System.out.println("RetentionPolicyIsLocked: " + bucket.retentionPolicyIsLocked());
		System.out.println("RequesterPays: " + bucket.requesterPays());
		System.out.println("SelfLink: " + bucket.getSelfLink());
		System.out.println("StorageClass: " + bucket.getStorageClass().name());
		System.out.println("TimeCreated: " + bucket.getCreateTime());
		System.out.println("VersioningEnabled: " + bucket.versioningEnabled());
		if (bucket.getLabels() != null) {
			System.out.println("\n\n\nLabels:");
			for (Map.Entry<String, String> label : bucket.getLabels().entrySet()) {
				System.out.println(label.getKey() + "=" + label.getValue());
			}
		}
	}
}
