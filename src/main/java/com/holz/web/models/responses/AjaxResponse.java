package com.holz.web.models.responses;

public class AjaxResponse {
	private boolean success;
	private String message;
	private Object object;
	
	public AjaxResponse(boolean success) {
		this.success = success;
	}
	
	public AjaxResponse(boolean success, String message) {
		this.success = success;
		this.message = message;
	}
	
	public AjaxResponse(boolean success, Object object) {
		this.success = success;
		this.object = object;
	}
	
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getObject() {
		return object;
	}
	public void setObject(Object object) {
		this.object = object;
	}
}
