/**
 * Copyright Chinmay Anand. All Rights Reserved.
 * A Java class that sends a text message.
 * @author Chinmay
 *
 */


package com.chinmay.aws_dynamo_sms.handlingformsubmission;

import org.springframework.stereotype.Component;

import software.amazon.awssdk.auth.credentials.EnvironmentVariableCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;
import software.amazon.awssdk.services.sns.model.PublishResponse;
import software.amazon.awssdk.services.sns.model.SnsException;

@Component("PublishTextSMS")
public class PublishTextSMS {
	/**
	 * @param id - The id of the updated database record.
	 * @param phoneNumber - The number where the confirmation sms will be sent.
	 * Controller will call this method to send the confirmation message once data has
	 * been inserted into the database.
	 */
	public void sendMessage(String id, String phoneNumber) {
		String message = "A new item with ID value "+ id +" was added to the DynamoDB table";
		Region region = Region.AP_SOUTH_1; //for Mumbai region

		//SNS Client which will publish our request message
		SnsClient snsClient = SnsClient.builder()
				.region(region)
				.credentialsProvider(EnvironmentVariableCredentialsProvider.create())
				.build();
		
		//The request that is to be published, contains message txt and phone number
		PublishRequest request = PublishRequest.builder()
				.message(message)
				.phoneNumber(phoneNumber) //we can publish either to a phone number or to a topic
				.build();
		
		try {
			PublishResponse result = snsClient.publish(request);
		}catch (SnsException e) {
			System.err.println(e.awsErrorDetails().errorMessage());
			System.exit(1); //ANy non-zero or negative for unsuccessful
		}
	}
}
