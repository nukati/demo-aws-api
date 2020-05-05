package com.demo.api;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("aws")
public class AwsProperties {

    private String accessKey;

    private String secretKey;
    
	private Float similarityThreshold;

	public String getAccessKey() {
		return accessKey;
	}

	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	public Float getSimilarityThreshold() {
		return similarityThreshold;
	}

	public void setSimilarityThreshold(Float similarityThreshold) {
		this.similarityThreshold = similarityThreshold;
	}
    
    
}
