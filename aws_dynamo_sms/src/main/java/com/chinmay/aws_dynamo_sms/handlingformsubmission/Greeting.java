/**
 * Copyright Chinmay Anand. All Rights Reserved.
 * A Java class that represents the model for the application.
 * @author Chinmay
 *
 */

package com.chinmay.aws_dynamo_sms.handlingformsubmission;

public class Greeting {
	
	private String id;
	private String body;
	private String name;
	private String title;
	//We use exactly the same attribute names in the ui (chin_greeting.html) thymeleaf template
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

}
