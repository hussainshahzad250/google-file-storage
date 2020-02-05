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

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

/**
 * @author shahzad.hussain
 *
 */
public class DownloadFile {

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		download();
	}

	private static void download() throws IOException {
		String bucketName = "hrms-loandost";
		String srcFilename = "hello";

//		Path destFilePath = Paths.get("C:\\Users\\Shahzad.Hussain\\Downloads\\Hibernate\\hello.txt");
		Path destFilePath = Paths.get("C:\\Users\\Shahzad.Hussain\\Downloads\\Hibernate\\hello.jpg");
		File file = destFilePath.toFile();
		if (!file.exists()) {
			file.createNewFile();
			System.out.println("file got created....");
		}

		Storage storage = StorageOptions.getDefaultInstance().getService();
		Blob blob = storage.get(BlobId.of(bucketName, srcFilename));
		if (blob != null) {
			System.out.println(blob.getContentType());
			blob.downloadTo(destFilePath);
		}
		System.out.println("done");

	}

}
