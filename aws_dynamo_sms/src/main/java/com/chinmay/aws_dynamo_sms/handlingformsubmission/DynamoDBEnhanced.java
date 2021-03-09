/**
 * Copyright Chinmay Anand. All Rights Reserved.
 * A Java class that injects data into a DynamoDB table by using the DynamoDB enhanced client API.
 * @author Chinmay
 *
 */

package com.chinmay.aws_dynamo_sms.handlingformsubmission;

import static software.amazon.awssdk.enhanced.dynamodb.mapper.StaticAttributeTags.primaryPartitionKey;

import org.springframework.stereotype.Component;

import software.amazon.awssdk.auth.credentials.EnvironmentVariableCredentialsProvider;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.mapper.StaticTableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.PutItemEnhancedRequest;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.ProvisionedThroughput;

@Component("DynamoDBEnhanced")
public class DynamoDBEnhanced {
	
	//Set max no of reads or writes to 50, beyond which DynamoDB throws ThrottlingException
	private final ProvisionedThroughput DEFAULT_PROVISIONED_THROUGHPUT =
            ProvisionedThroughput.builder()
                    .readCapacityUnits(50L) //The maximum number of strongly consistent reads consumed per second limited
                    .writeCapacityUnits(50L) //The maximum number of writes consumed per second limited
                    .build();
	//Where should I use this "DEFAULT_PROVISIONED_THROUGHPUT"
	
	//Define an inner GreetingItem class that will hold a record to be inserted into DynamoDB
	public class GreetingItems {
		//Set up Data Member that corresponds to the columns in the Work table.
		private String id;      //idblog
		private String name;    //author
		private String message; //body
		private String title;   //title
		public GreetingItems() {	}
		public String getId() { return id; }
		public void setId(String id) { this.id = id; }
		public String getName() { return name; }
		public void setName(String name) { this.name = name; }
		public String getMessage() { return message; }
		public void setMessage(String message) { this.message = message; }
		public String getTitle() { return title; }
		public void setTitle(String title) { this.title = title; }
	}
	
	//Define a TableSchema variable to hold the schema of the table with getter and setters declared
	private final TableSchema<GreetingItems> GREETINGS_TABLE_SCHEMA =
		      StaticTableSchema.builder(GreetingItems.class)
		        .newItemSupplier(GreetingItems::new)
		        .addAttribute(String.class, a -> a.name("idblog")
		                                          .getter(GreetingItems::getId)
		                                          .setter(GreetingItems::setId)
		                                          .tags(primaryPartitionKey()))
		        .addAttribute(String.class, a -> a.name("author")
		                                          .getter(GreetingItems::getName)
		                                          .setter(GreetingItems::setName))
		        .addAttribute(String.class, a -> a.name("body")
		                                           .getter(GreetingItems::getMessage)
		                                           .setter(GreetingItems::setMessage))
		        .addAttribute(String.class, a -> a.name("title")
		                                           .getter(GreetingItems::getTitle)
		                                           .setter(GreetingItems::setTitle))
		        .build();
	
	//This is the main method of the class that
	//Uses the Enhanced Client to inject a new post into a DynamoDB table
	public void injectDynamoItem(Greeting item) {
		//TBD:Insert code to save the Greeting record into AWS DynamoDB as a record
		Region region = Region.AF_SOUTH_1; //Mumbai region
		DynamoDbClient ddb = DynamoDbClient.builder()
				.region(region)
				.credentialsProvider(EnvironmentVariableCredentialsProvider.create())
				.build();
		
		try {
			DynamoDbEnhancedClient enhancedClient = DynamoDbEnhancedClient.builder()
					.dynamoDbClient(ddb)
					.build();
			
			//Create a DynamoDbTable object
			DynamoDbTable<GreetingItems> mappedTable = enhancedClient.table("GreetingTable", GREETINGS_TABLE_SCHEMA);
			
			//Prepare a record from the "item" Greeting object passed from html to injectDynamoItem() method call in controller
			GreetingItems gi = new GreetingItems();
			gi.setId(item.getId());
			gi.setName(item.getName());
			gi.setName(item.getName());
			gi.setTitle(item.getTitle());
			
			//Prepare a put request object to be passed into DynamoDbTable prepared through enhancedClient
			PutItemEnhancedRequest<GreetingItems> enReq = 
					PutItemEnhancedRequest.builder(GreetingItems.class)
						.item(gi)
						.build();
			
			//Write to dynamo db using the mapped table from enhanced client
			mappedTable.putItem(enReq);
					
		}catch (Exception e) {
			// TODO: handle exception
			e.getStackTrace();
		}
	}
	
	/**
	 * TBD: EXAMPLE OF LAMBDA FUNCTTION IN JDK 8 (My Theory - check with LinkedIn Course)
	 * ====================================
	 * String IntToStr(int num){return Integer.toString(num);}
	 * String IntToStr = (num) -> Integer.toString(num);
	 * String IntToStr = Integer::toString
	 * 
	 * "IntToStr.apply(123)" This method call will return string "123"
	 */

}
