package com.demo.api.service;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.List;

import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.model.BoundingBox;
import com.amazonaws.services.rekognition.model.CompareFacesMatch;
import com.amazonaws.services.rekognition.model.CompareFacesRequest;
import com.amazonaws.services.rekognition.model.CompareFacesResult;
import com.amazonaws.services.rekognition.model.ComparedFace;
import com.amazonaws.services.rekognition.model.DetectModerationLabelsRequest;
import com.amazonaws.services.rekognition.model.DetectModerationLabelsResult;
import com.amazonaws.services.rekognition.model.Image;
import com.demo.api.AwsProperties;
import com.demo.api.AwsRekognitionConfiguration;
import com.demo.api.model.AwsRekognitionRestRequest;
import com.demo.api.model.AwsRekognitionRestResponse;

@Service
public class AwsRekognitionService {

	private AmazonRekognition client;

	@Autowired
	private AwsProperties appProperties;

	private static final Logger logger = LoggerFactory.getLogger(AwsRekognitionService.class);

	public AwsRekognitionService(AmazonRekognition client) {
		this.client = client;
	}

	public DetectModerationLabelsResult detectModerationLabels(MultipartFile imageToCheck) throws IOException {
		DetectModerationLabelsRequest request = new DetectModerationLabelsRequest()
				.withImage(new Image().withBytes(ByteBuffer.wrap(imageToCheck.getBytes())));

		return client.detectModerationLabels(request);
	}

	public AwsRekognitionRestResponse compareFaces(AwsRekognitionRestRequest param) throws IOException {
		AwsRekognitionRestResponse awsresponse = new AwsRekognitionRestResponse();
		float similarResult = 0;
		byte[] imageSourceByte = Base64.decodeBase64(param.getImageSource());
		byte[] imageTargetByte = Base64.decodeBase64(param.getImageTarget());

		ByteBuffer sourceImageBytes = ByteBuffer.wrap(imageSourceByte);
		/*
		 * try (InputStream inputStream = new FileInputStream(new File(sourceImage))) {
		 * sourceImageBytes = ByteBuffer.wrap(IOUtils.toByteArray(inputStream)); }
		 */

		ByteBuffer targetImageBytes = ByteBuffer.wrap(imageTargetByte);
		/*
		 * try (InputStream inputStream = new FileInputStream(new File(targetImage))) {
		 * targetImageBytes = ByteBuffer.wrap(IOUtils.toByteArray(inputStream)); }
		 */

		AwsRekognitionConfiguration config = new AwsRekognitionConfiguration();
		AmazonRekognition rekognitionClient = config.amazonRekognition();
		CompareFacesRequest request1 = new CompareFacesRequest()
				.withSourceImage(new Image().withBytes(sourceImageBytes))
				.withTargetImage(new Image().withBytes(targetImageBytes))
				.withSimilarityThreshold(appProperties.getSimilarityThreshold());

		// Call operation
		CompareFacesResult compareFacesResult = rekognitionClient.compareFaces(request1);

		// Display results
		List<CompareFacesMatch> faceDetails = compareFacesResult.getFaceMatches();
		if (faceDetails != null) {
			for (CompareFacesMatch match : faceDetails) {
				ComparedFace face = match.getFace();
				BoundingBox position = face.getBoundingBox();
				logger.info("Face at " + position.getLeft().toString() + " " + position.getTop() + " matches with "
						+ match.getSimilarity().toString() + "% confidence.");
				similarResult = match.getSimilarity();
			}
		}
		List<ComparedFace> uncompared = compareFacesResult.getUnmatchedFaces();
		if (uncompared != null && uncompared.size() > 0) {
			logger.info("There was " + uncompared.size() + " face(s) that did not match");
			awsresponse.setUncompared(uncompared.size());
		}
		awsresponse.setSimilarity(similarResult);
		return awsresponse;
	}

	public AwsProperties getAppProperties() {
		return appProperties;
	}

	public void setAppProperties(AwsProperties appProperties) {
		this.appProperties = appProperties;
	}

}
