package com.cloud.config;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.common.collect.Lists;

@Configuration
@Component
public class GoogleStorageConfig {

	@Bean
	public Storage initializeClient() throws IOException {
		ClassPathResource resource = new ClassPathResource("alok.json");
		GoogleCredentials credentials = GoogleCredentials.fromStream(resource.getInputStream())
				.createScoped(Lists.newArrayList("https://www.googleapis.com/auth/cloud-platform"));
		return StorageOptions.newBuilder().setCredentials(credentials).build().getService();
	}
}
