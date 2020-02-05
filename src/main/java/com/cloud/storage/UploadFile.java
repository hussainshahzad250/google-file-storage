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

/**
 * @author shahzad.hussain
 *
 */
import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

/** A snippet for Google Cloud Storage showing how to create a blob. */
public class UploadFile {

	public static void main(String... args) throws FileNotFoundException {
//		upload1();
		uploadFile();
	}

	private static void upload1() {
		Storage storage = StorageOptions.getDefaultInstance().getService();
		BlobId blobId = BlobId.of("hrms-loandost", "hello");
		BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("text/plain").build();
		Blob blob = storage.create(blobInfo, "Hello, Cloud Storage modified!".getBytes(UTF_8));

		String selfLink = blob.getSelfLink();
		System.out.println(selfLink);
		System.out.println(blob.getName());
	}

	private static void uploadFile() throws FileNotFoundException {

		Storage storage = StorageOptions.getDefaultInstance().getService();
		BlobId blobId = BlobId.of("hrms-loandost", "hello");
		BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("text/plain").build();

		Path file = Paths.get("D:\\CRM\\Request\\940713-nach.jpeg");

		InputStream testFile = new FileInputStream(file.toFile());
		Blob blob = storage.create(blobInfo, testFile);
		System.out.println(blob.getContentType());
		System.out.println(blob.getMediaLink());

	}
}