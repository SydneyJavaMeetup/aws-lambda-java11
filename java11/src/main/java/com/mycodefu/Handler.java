package com.mycodefu;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicInteger;

import static com.mycodefu.JsonSerialization.objectMapper;
import static com.mycodefu.JsonSerialization.prettyObjectWriter;

public class Handler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
	private static final AtomicInteger executionCount = new AtomicInteger(0);
	private static final DynamoDBDataAccess dynamoDBDataAccess = new DynamoDBDataAccess("LanguageTest");

	@Override
	public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent input, Context context) {
		DebugOutput debugOutput = null;
		try {
			boolean DEBUG = input.getQueryStringParameters() != null
					&& "true".equalsIgnoreCase(input.getQueryStringParameters().get("debug"));
			if (DEBUG) {
				context.getLogger().log(String.format("received: \n%s", prettyObjectWriter.writeValueAsString(input)));

				RuntimeMXBean runtimeMxBean = ManagementFactory.getRuntimeMXBean();
				debugOutput = new DebugOutput(input, runtimeMxBean.getInputArguments());
			}
		} catch (JsonProcessingException ignored) {}

		String group = (input.getQueryStringParameters() != null && input.getQueryStringParameters().containsKey("group")) ? input.getQueryStringParameters().get("group") : "DefaultGroup";
		String value = (input.getQueryStringParameters() != null && input.getQueryStringParameters().containsKey("value")) ? input.getQueryStringParameters().get("value") : "DefaultValue";

		//write and read back from DynamoDB
		long startTime = System.nanoTime();
		String id = dynamoDBDataAccess.createRecord(group, value);
		String valueReadBack = dynamoDBDataAccess.readRecordValue(group, id);
		long endTime = System.nanoTime();
		long duration = (endTime - startTime) / 1_000_000;

		String message = String.format("Java 11 Lambda wrote and read group '%s', value '%s' in %dms!", group, valueReadBack, duration);

		Response response = new Response(executionCount.getAndIncrement(), message, debugOutput);
		try {
			String responseString = objectMapper.writeValueAsString(response);
			APIGatewayProxyResponseEvent responseEvent = new APIGatewayProxyResponseEvent();
			responseEvent.setHeaders(Collections.singletonMap("Content-Type", "application/json"));
			responseEvent.setBody(responseString);
			responseEvent.setStatusCode(200);
			responseEvent.setIsBase64Encoded(false);
			return responseEvent;

		} catch (JsonProcessingException e) {
			APIGatewayProxyResponseEvent responseEvent = new APIGatewayProxyResponseEvent();
			responseEvent.setBody(String.format("Unable to serialize response:\n%s", e));
			responseEvent.setStatusCode(500);
			responseEvent.setIsBase64Encoded(false);
			return responseEvent;
		}
	}
}
