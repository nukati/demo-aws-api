package com.demo.api.model;

public class AwsRekognitionRestResponse {
	private String status;
	private String message;
	private Float similarity;
	private int uncompared;

	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Float getSimilarity() {
		return similarity;
	}
	public void setSimilarity(Float similarity) {
		this.similarity = similarity;
	}
	public int getUncompared() {
		return uncompared;
	}
	public void setUncompared(int uncompared) {
		this.uncompared = uncompared;
	}
	
}
