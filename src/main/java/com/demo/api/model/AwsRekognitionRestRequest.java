package com.demo.api.model;

public class AwsRekognitionRestRequest {
	private long id;
	private String imageSource;
	private String imageTarget;
	public AwsRekognitionRestRequest(long id, String similarityThreshold, String imageSource, String imageTarget) {
		super();
		this.id = id;
		this.imageSource = imageSource;
		this.imageTarget = imageTarget;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getImageSource() {
		return imageSource;
	}
	public void setImageSource(String imageSource) {
		this.imageSource = imageSource;
	}
	public String getImageTarget() {
		return imageTarget;
	}
	public void setImageTarget(String imageTarget) {
		this.imageTarget = imageTarget;
	}
	
}
