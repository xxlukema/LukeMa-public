package com.learn.spring.rest;

import java.util.List;

public class MBSError {
	private String status;
	private String rootCause;
	private String requestUri;
	private List<MbsErrorsDetail> errors;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRootCause() {
		return rootCause;
	}
	public void setRootCause(String rootCause) {
		this.rootCause = rootCause;
	}
	public String getRequestUri() {
		return requestUri;
	}
	public void setRequestUri(String requestUri) {
		this.requestUri = requestUri;
	}
	public List<MbsErrorsDetail> getErrors() {
		return errors;
	}
	public void setErrors(List<MbsErrorsDetail> errors) {
		this.errors = errors;
	}
	

}
