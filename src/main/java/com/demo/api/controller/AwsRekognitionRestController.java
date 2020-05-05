package com.demo.api.controller;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.demo.api.model.AwsRekognitionRestRequest;
import com.demo.api.model.AwsRekognitionRestResponse;
import com.demo.api.service.AwsRekognitionService;

@RestController
@RequestMapping("/api")
public class AwsRekognitionRestController {
	
    private AwsRekognitionService awsRekognitionService;
    
    private static final Logger logger = LoggerFactory.getLogger(AwsRekognitionRestController.class);

    public AwsRekognitionRestController(AwsRekognitionService awsRekognitionService) {
        this.awsRekognitionService = awsRekognitionService;
    }

    @PostMapping("/images/moderation")
    public Object detectModerationLabels(@RequestParam MultipartFile image) throws IOException {
        return ResponseEntity.ok(awsRekognitionService.detectModerationLabels(image));
    }

    @PostMapping("/images/compareface")
    public Object compareFace(@RequestBody AwsRekognitionRestRequest param)  {
    	AwsRekognitionRestResponse response = null;
    	try {
    		logger.info("Image Source : " +param.getImageSource());
    		logger.info("Image Target : " +param.getImageTarget());
    		response = awsRekognitionService.compareFaces(param);
	    
    	}catch(Exception io) {
    		if (param.getImageSource()==null || param.getImageSource().isEmpty() ) {
    	        return ResponseEntity.badRequest()
    	            .body("Image source is null");
    	    }
    		
    		if (param.getImageTarget() ==null || param.getImageTarget().isEmpty() ) {
    	        return ResponseEntity.badRequest()
    	            .body("Image target is null");
    	    }
    		
    		return ResponseEntity.badRequest()
    	            .body("Exception : Unable connect AWS");
    	}
    	 return ResponseEntity.ok().body(response);
    }
}
