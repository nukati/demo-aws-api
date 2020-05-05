package com.demo.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;
import com.demo.api.controller.AwsRekognitionRestController;

@Configuration
public class AwsRekognitionConfiguration {
	
	@Autowired
    private AwsProperties appProperties;
	
	private static final Logger logger = LoggerFactory.getLogger(AwsRekognitionRestController.class);
	
    @Bean
    public AmazonRekognition amazonRekognition() {
    	logger.info("AccessKey: " +appProperties.getAccessKey());
    	logger.info("SecretKey : " +appProperties.getSecretKey());
    	logger.info("Regions : " +Regions.US_EAST_1);
        BasicAWSCredentials credentials = new BasicAWSCredentials(appProperties.getAccessKey() ,appProperties.getSecretKey());
        return AmazonRekognitionClientBuilder
                .standard()
                .withRegion(Regions.US_EAST_1)
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .build();
    }

}
