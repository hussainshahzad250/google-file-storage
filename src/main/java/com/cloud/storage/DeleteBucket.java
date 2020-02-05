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

import com.google.cloud.storage.Acl.User;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

public class DeleteBucket {

	public static void main(String[] args) {
		boolean deleteBucketAcl = deleteBucketAcl("hrms-loandost");
		System.out.println(deleteBucketAcl);

	}

	public static boolean deleteBucketAcl(String bucketName) {
		Storage storage = StorageOptions.getDefaultInstance().getService();

		boolean deleted = storage.deleteAcl(bucketName, User.ofAllAuthenticatedUsers());
		if (deleted) {
			System.out.println("File deleted");
		} else {
			System.out.println("FIle not deleted");
		}
		return deleted;
	}

}
