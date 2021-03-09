package com.chinmay.aws_dynamo_sms.handlingformsubmission;

import org.springframework.stereotype.Component;

import software.amazon.awssdk.services.dynamodb.model.ProvisionedThroughput;

/**
 * A Java class that injects data into a DynamoDB table by using the DynamoDB enhanced client API.
 * @author Chinmay
 *
 */

@Component("DynamoDBEnhanced")
public class DynamoDBEnhanced {
	
	private final ProvisionedThroughput DEFAULT_PROVISIONED_THROUGHPUT =
            ProvisionedThroughput.builder()
                    .readCapacityUnits(50L)
                    .writeCapacityUnits(50L)
                    .build();
	

}
