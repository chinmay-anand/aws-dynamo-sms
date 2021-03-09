/**
 * Copyright Chinmay Anand. All Rights Reserved.
 * A Java class that represents the controller for this application.
 * It handles HTTP requests and returns a view. In this example note that 
 * notice the **@Autowired** annotation that creates a managed Spring bean. 
 * The following Java code represents this class.
 * 
 * @author Chinmay
 *
 */

package com.chinmay.aws_dynamo_sms.handlingformsubmission;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class GreetingController {
	
	@Autowired private DynamoDBEnhanced dde;
	@Autowired private PublishTextSMS msg;
	
	@GetMapping("/")
	public String greetingFrom(Model model) {
		model.addAttribute("greeting", new Greeting());
		return "Greetings from Chinmay";
	}
	
	@PostMapping("/greeting")
	public String greetingSubmit(@ModelAttribute Greeting greeting) {
		
		//Persist submitted data into DynamoDB table using the enhanced client
		dde.injectDynamoItem(greeting); 
		
		//Send a mobile notification
		String phNo = "+919885579819"; //Remove from here and pass from UI
		msg.sendMessage(greeting.getId(), phNo);
		
		return "result";
	}
}
